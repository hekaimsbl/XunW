package com.example.hekai.xunw.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.hekai.xunw.Fragment.RichEditorDialogFragment;
import com.example.hekai.xunw.Interface.PermissionListener;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.bean.EventBusMessage;
import com.example.hekai.xunw.bean.Food;
import com.example.hekai.xunw.services.FoodApi;
import com.example.hekai.xunw.utils.ApiResult;
import com.example.hekai.xunw.utils.BaseActivity;
import com.example.hekai.xunw.utils.KeyboardUtils;
import com.example.hekai.xunw.utils.Messages;
import com.example.hekai.xunw.utils.NetTool;
import com.example.hekai.xunw.utils.SharedPreferenceUtil;
import com.example.hekai.xunw.utils.ToastUtil;
import com.example.hekai.xunw.utils.UserInfo;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PublishActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener {

    private static final int TAKE_PHOTO = 0x001;
    private static final int CHOOSE_PHOTO = 0x002;
    private static final int CROP_PHOTO = 0x003;

    private static String imgPathOri;
    private static String imgPathCrop;

    private Uri imgUriOri;
    private Uri imgUriCrop;

    @BindView(R.id.tev)
    TextView tev;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindString(R.string.publishActivity_title)
    String title;

    @BindView(R.id.pub_address)
    TextView pubAddress;

    @BindView(R.id.pub_layout_address)
    RelativeLayout pubLayoutAddress;

    @BindView(R.id.pub_takePhoto_layout)
    RelativeLayout pubTakePhotoLayout;

    @BindView(R.id.pub_layout_writerFile)
    RelativeLayout pubWriterFile;

    @BindView(R.id.pub_layout_media)
    LinearLayout layoutMedia;

    @BindView(R.id.pub_layout_images)
    LinearLayout layoutImageView;

    @BindView(R.id.et_publish_title)
    EditText et_title;
    @BindView(R.id.et_pub_phone)
    EditText et_phone;
    @BindView(R.id.et_pub_businessTime)
    EditText et_businessTime;
    @BindView(R.id.et_pub_tag)
    EditText et_tag;

    /**
     * 需要的权限
     */
    private String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,};

    /**
     * 单次定位客户端
     */
    private AMapLocationClient locationClientSingle = null;

    private List<String> imageUrls = new ArrayList<>();

    private Food foodUpload = new Food();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        init();
        requestPermission();
        setToolbar();
        singleLocation();
        onClickedListener();
    }

    private void onClickedListener() {
        pubWriterFile.setOnClickListener(v -> {
            writerHtmlFile();
        });


        pubTakePhotoLayout.setOnClickListener(v -> {
            ToastUtil.show("layout clicked");
            takePhotoLayoutClicked();
        });

        /*pubLayoutAddress.setOnClickListener(v2 -> {
            ToastUtil.show("address clicked");
            locationLayoutClicked();
        });*/
    }

    private void writerHtmlFile() {
        RichEditorDialogFragment richEditorDialogFragment = new RichEditorDialogFragment();
        richEditorDialogFragment.show(getSupportFragmentManager(), "");
    }

    private void requestPermission() {
        requestRunPermission(PERMISSIONS, new PermissionListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(List<String> deniedPermission) {

            }
        });
    }

    /**
     * 单次定位
     */
    private void singleLocation() {
        if (null == locationClientSingle) {
            locationClientSingle = new AMapLocationClient(this.getApplicationContext());
        }

        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        locationClientOption.setOnceLocation(true);

        //地址信息
        locationClientOption.setNeedAddress(true);
        locationClientOption.setLocationCacheEnable(false);
        locationClientSingle.setLocationOption(locationClientOption);
        locationClientSingle.setLocationListener(locationSingleListener);
        locationClientSingle.startLocation();
    }

    AMapLocationListener locationSingleListener = aMapLocation -> {
        StringBuffer sb = new StringBuffer();
        if (null == aMapLocation) {
            pubAddress.setText("定位失败~");
        } else {
            //定位成功
            if (aMapLocation.getErrorCode() == 0) {
                sb.append(aMapLocation.getCity());
                sb.append(aMapLocation.getDistrict());
                sb.append(aMapLocation.getStreet());
                sb.append(aMapLocation.getStreetNum());
                pubAddress.setText("当前地址:" + sb.toString());
                foodUpload.setAddress(sb.toString());
                foodUpload.setLatitude(aMapLocation.getLatitude());
                foodUpload.setLongitude(aMapLocation.getLongitude());
                foodUpload.setCityCode(aMapLocation.getCityCode());
            } else {
                //定位失败错误信息
                sb.append(aMapLocation.getErrorCode());
                Logger.d("定位失败信息：" + sb.toString());
            }
        }
    };


    private void setToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(title);
        }
    }


    private void init() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        RichText.initCacheDir(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.toolbar_publish_done:
                readyToUpload();
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusMessage msg) {
        foodUpload.setContent(msg.getMsg());
        RichText.fromHtml(msg.getMsg()).into(tev);
    }

    /**
     * 开始上传
     */
    private void readyToUpload() {
        if (("").equals(et_title.getText().toString()) | ("").equals(et_businessTime.getText().toString()) | ("").equals(et_phone.getText().toString()) | ("").equals(et_tag.getText().toString()) | null == foodUpload.getContent()) {
            ToastUtil.showMsg("请完成信息填写");
        } else if (("").equals(foodUpload.getAddress())) {
            ToastUtil.showMsg("请等待定位完成...");
        } else {
            foodUpload.setTitle(et_title.getText().toString());
            foodUpload.setBusinessTime(et_businessTime.getText().toString());
            foodUpload.setPhoneNumber(et_phone.getText().toString());

            List<String> tags = new ArrayList<>();
            String tagString = et_tag.getText().toString();
            if (tagString.indexOf(" ") > 0) {
                tags = Arrays.asList(tagString.split(" "));
            } else {
                tags.add(tagString);
            }
            foodUpload.setTags(tags);

            String id = SharedPreferenceUtil.getString(UserInfo.USER_FILENAME, UserInfo.USER_NAME, "", this);
            foodUpload.setAuthorId(id);

            /**
             * city_code
             * longitude
             * latitude
             * imageUrls
             */

            Gson gson = new Gson();

            //实体类处理
            String value = gson.toJson(foodUpload);
            Logger.d("上传实体类Json:" + value);
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);

            //图片处理
            MultipartBody.Builder mBuilder = new MultipartBody.Builder();

            if (!imageUrls.isEmpty()) {
                for (String path : imageUrls) {
                    String type = path.substring(path.lastIndexOf(".") + 1);
                    File file = new File(path);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/" + type), file);
                    mBuilder.addFormDataPart("pictures", file.getName(), requestFile);
                }

            }
            //图片处理 end

            Observable<ApiResult<String>> upload = NetTool.getInstance(NetTool.HOST_LOCAL_URL_ONE_NET).getApiService(FoodApi.class).upload(requestBody, mBuilder.build());
            upload.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(text -> showProgressBar()).subscribe(new Observer<ApiResult<String>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ApiResult<String> stringApiResult) {
                    hideProgressBar();
                    if (stringApiResult.getStatus() == Messages.RESULT_OK) {
                        ToastUtil.showMsg("上传成功...");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtil.showMsg("上传失败...");
                }

                @Override
                public void onComplete() {

                }
            });


        }
    }

    public List<MultipartBody.Part> filesToMultipartBodyParts(List<String> imageUrls) {
        List<MultipartBody.Part> parts = new ArrayList<>(imageUrls.size());
        for (String path : imageUrls) {
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    public static MultipartBody filesToMultipartBody(List<String> imageUrls) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (String imagePath : imageUrls) {
            File file = new File(imagePath);
            String suffixName = imagePath.substring(imagePath.lastIndexOf("."));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            ;
            if ("jpg".equals(suffixName)) {
                requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            } else if ("png".equals(suffixName)) {
                requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            } else if ("gif".equals(suffixName)) {
                requestBody = RequestBody.create(MediaType.parse("image/gif"), file);
            }
            builder.addFormDataPart("file", file.getName(), requestBody);

        }

        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    private void showProgressBar() {
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    private void takePhotoLayoutClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_choose_photo, null);

        //拍照
        TextView tvTake = v.findViewById(R.id.dialog_choosePhoto_take);

        //相册选择
        TextView tvChoose = v.findViewById(R.id.dialog_choosePhoto_choose);

        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setGravity(Gravity.CENTER);

        tvTake.setOnClickListener(v1 -> {
            requestTakePhotoPermission();
            dialog.dismiss();
        });

        tvChoose.setOnClickListener(v1 -> {
            requestAlbumPermission();
            dialog.dismiss();
        });

    }

    private void requestTakePhotoPermission() {
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        requestRunPermission(PERMISSIONS, new PermissionListener() {
            @Override
            public void onGranted() {
                takePhoto();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {

            }
        });
    }

    private void requestAlbumPermission() {
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestRunPermission(PERMISSIONS, new PermissionListener() {
            @Override
            public void onGranted() {
                openAlbum();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {

            }
        });
    }

    private void takePhoto() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File oriPhotoFile = null;
        try {
            oriPhotoFile = createOriImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (oriPhotoFile != null) {
            imgUriOri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", oriPhotoFile);

            takePic.putExtra(MediaStore.EXTRA_OUTPUT, imgUriOri);
            startActivityForResult(takePic, TAKE_PHOTO);
        }
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /**
     * 位置选择
     */
    private void locationLayoutClicked() {
        PoiSearch.Query query = new PoiSearch.Query(null, "190100", "0731");
        query.setPageSize(10);
        query.setPageNum(1);

        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(112.920481, 28.204378), 300));
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    Logger.d(imgUriOri);
                    corpPhoto(imgUriOri);
                    break;
                case CHOOSE_PHOTO:
                    Uri imgUriSel = data.getData();
                    String imgPathSel = handleImageC(imgUriSel);
                    imgUriSel = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", new File(imgPathSel));
                    corpPhoto(imgUriSel);
                    break;
                case CROP_PHOTO:
                    handlerPhoto(imgPathCrop);
                    break;
                default:
            }
        }
    }

    private void corpPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        File cropPhotoFile = null;
        try {
            cropPhotoFile = createCropImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (cropPhotoFile != null) {
            imgUriCrop = Uri.fromFile(cropPhotoFile);

            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", true);
            intent.putExtra("aspectX", 2);
            intent.putExtra("aspectY", 1);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriCrop);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, CROP_PHOTO);
        }
    }

    private void handlerPhoto(String photoPath) {
        if (null != photoPath) {
            imageUrls.add(photoPath);
            //设置宽度为指定大小宽度,要相应的dp转化为px
            Resources r = getResources();
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, r.getDisplayMetrics());

            RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
            imageViewParams.setMargins(0, 5, 0, 5);

            //layoutImageView.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(BitmapFactory.decodeFile(photoPath));

            ImageView clearImage = new ImageView(this);
            clearImage.setImageResource(R.drawable.ic_close_black_24dp);
            int clearImageH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
            RelativeLayout.LayoutParams clearImageParams = new RelativeLayout.LayoutParams(clearImageH, clearImageH);
            clearImageParams.addRule(RelativeLayout.ALIGN_PARENT_END);

            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.addView(imageView, imageViewParams);
            relativeLayout.addView(clearImage, clearImageParams);

            layoutImageView.addView(relativeLayout, imageViewParams);

            clearImage.setOnClickListener((v) -> {
                layoutImageView.removeView(relativeLayout);
                imageUrls.remove(photoPath);
            });

        }
    }

    /**
     * 处理7.0以上文件路径问题re
     *
     * @param uri
     * @return
     */
    private String handleImageC(Uri uri) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);

                //Uri.parse("content://downloads/public_downloads"),
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        Logger.d("imagePath" + imagePath);
        return imagePath;
        //handlerPhoto(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        Logger.d("path:" + path);
        return path;
    }

    /**
     * 创建裁剪图像保存的文件
     *
     * @return
     * @throws IOException
     */
    private File createCropImageFile() throws IOException {
        String imgNameCrop = "HomePic_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pictureDirCrop = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/CropPicture");
        if (!pictureDirCrop.exists()) {
            pictureDirCrop.mkdirs();
        }
        File image = File.createTempFile(imgNameCrop, ".jpg", pictureDirCrop);
        imgPathCrop = image.getAbsolutePath();
        return image;
    }

    /**
     * 创建原图像保存的文件
     *
     * @return
     * @throws IOException
     */
    private File createOriImageFile() throws IOException {
        String imgNameOri = "HomePic_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pictureDirOri = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/OriPicture");
        if (!pictureDirOri.exists()) {
            pictureDirOri.mkdirs();
        }
        File image = File.createTempFile(imgNameOri, ".jpg", pictureDirOri);
        imgPathOri = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 0) {
            if (poiResult != null && poiResult.getQuery() != null) {
                //if (poiResult.getQuery().queryEquals(query))
                Logger.d("poiResult：" + poiResult);
                List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
                List<PoiItem> items = poiResult.getPois();
                for (int j = 0; j < items.size(); j++) {
                    Logger.d("items：" + items.get(j).getCityName());
                }

                for (int d = 0; d < suggestionCities.size(); d++) {
                    Logger.d("items5：" + suggestionCities.get(d).getCityName());
                }
            } else {
                Logger.d("no result");
            }
        } else if (i == 27) {
            ToastUtil.showMsg("error_network");
        } else if (i == 32) {
            ToastUtil.showMsg("error_key");
        } else {
            ToastUtil.showMsg("error_other");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                KeyboardUtils.hideKeyboard(ev, view, this);
                break;
            default:
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_publish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        RichText.clear(this);
    }
}
