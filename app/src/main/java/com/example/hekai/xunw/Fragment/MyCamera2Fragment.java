package com.example.hekai.xunw.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.util.Range;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hekai.xunw.R;
import com.example.hekai.xunw.camera2.CompareSizeByArea;
import com.example.hekai.xunw.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/20
 **/
public class MyCamera2Fragment extends DialogFragment implements View.OnClickListener {
    private View view;


    @BindView(R.id.cameraPreview)
    TextureView mTextureView;

    @BindView(R.id.iv_show_camera2)
    ImageView mThumbnail;

    @BindView(R.id.takePictureImageButton)
    ImageView takePictureImageButton;

    public MyCamera2Fragment() {

    }

    private static final int SETIMAGE = 1;
    private static final int MOVE_FOCK = 2;
    /**
     * 最大预览宽度
     * Max preview width that is guaranteed by Camera2 API
     */
    private static final int MAX_PREVIEW_WIDTH = 1920;

    /**
     * 最大预览高度
     * Max preview height that is guaranteed by Camera2 API
     */
    private static final int MAX_PREVIEW_HEIGHT = 1080;

    Button mButton;
    Handler mHandler;
    Handler mUIHandler;
    ImageReader mImageReader;
    CaptureRequest.Builder mPreViewBuilder;
    CameraCaptureSession mCameraSession;
    CameraCharacteristics mCameraCharacteristics;
    private CameraDevice mCameraDevice;

    /**
     * 相机会话的监听器，通过他得到mCameraSession对象，这个对象可以用来发送预览和拍照请求
     */
    private CameraCaptureSession.StateCallback mSessionStateCallBack = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            if (null == mCameraDevice){
                return;
            }
            try {
                mCameraSession = cameraCaptureSession;
                cameraCaptureSession.setRepeatingRequest(mPreViewBuilder.build(), null, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            ToastUtil.debug("CameraCaptureSession.StateCallback  Failed!");
        }
    };

    private Surface surface;
    /**
     * 打开相机时候的监听器，通过他可以得到相机实例，这个实例可以创建请求建造者
     */
    private CameraDevice.StateCallback cameraOpenCallBack = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            mCameraDevice = cameraDevice;
            try {
                mPreViewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                SurfaceTexture texture = mTextureView.getSurfaceTexture();
                texture.setDefaultBufferSize(mPreViewSize.getWidth(), mPreViewSize.getHeight());
                surface = new Surface(texture);
                mPreViewBuilder.addTarget(surface);
                mCameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()), mSessionStateCallBack, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            if (null != cameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }

        @Override
        public void onError(CameraDevice cameraDevice, int i) {
        }
    };

    /**
     *
     */
    private View.OnClickListener takePictureOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCameraDevice == null) {
                return;
            }
            // 创建拍照需要的CaptureRequest.Builder
            final CaptureRequest.Builder captureRequestBuilder;
            try {
                captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                // 将imageReader的surface作为CaptureRequest.Builder的目标
                captureRequestBuilder.addTarget(surface);
                // 自动对焦
                captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                // 自动曝光
                captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                // 获取手机方向
                int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
                // 根据设备方向计算设置照片的方向
                captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, 90);
                //拍照
                CaptureRequest mCaptureRequest = captureRequestBuilder.build();
                mCameraSession.capture(mCaptureRequest, null, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 图片在这保存
     */
    private ImageReader.OnImageAvailableListener onImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader imageReader) {
            mCameraDevice.close();

            mHandler.post(new ImageSaver(imageReader.acquireNextImage()));
        }
    };

    private Size mPreViewSize;
    private Rect maxZoomrect;
    private int maxRealRadio;
    private Integer mSensorOrientation;

    /**
     * 预览图显示控件的监听器，可以监听这个surface的状态
     */
    private TextureView.SurfaceTextureListener mSurfaceTextListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            HandlerThread thread = new HandlerThread("Camera2");
            thread.start();
            mHandler = new Handler(thread.getLooper());
            CameraManager manager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
            String cameraid = CameraCharacteristics.LENS_FACING_FRONT + "";
            try {
                mCameraCharacteristics = manager.getCameraCharacteristics(cameraid);

                //画面传感器的面积，单位是像素。
                maxZoomrect = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
                //最大的数字缩放
                maxRealRadio = mCameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM).intValue();
                picRect = new Rect(maxZoomrect);

                StreamConfigurationMap map = mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                Size largest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new CompareSizeByArea());
                mPreViewSize = map.getOutputSizes(SurfaceTexture.class)[0];
                choosePreSize(i, i1, map, largest);
                mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), ImageFormat.JPEG, 5);
                mImageReader.setOnImageAvailableListener(onImageAvailableListener, mHandler);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //requestCameraPermission();
                    return;
                }
                manager.openCamera(cameraid, cameraOpenCallBack, mHandler);
                //设置点击拍照的监听
                takePictureImageButton.setOnClickListener(takePictureOnclickListener);
                takePictureImageButton.setOnTouchListener(onTouchListener);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            if(null != mCameraDevice){
                mCameraDevice.close();
                MyCamera2Fragment.this.mCameraDevice = null;
            }
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }


        private void choosePreSize(int i, int i1, StreamConfigurationMap map, Size largest) {
            int displayRotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            //noinspection ConstantConditions
            mSensorOrientation = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            boolean swappedDimensions = false;
            switch (displayRotation) {
                case Surface.ROTATION_0:
                case Surface.ROTATION_180:
                    if (mSensorOrientation == 90 || mSensorOrientation == 270) {
                        swappedDimensions = true;
                    }
                    break;
                case Surface.ROTATION_90:
                case Surface.ROTATION_270:
                    if (mSensorOrientation == 0 || mSensorOrientation == 180) {
                        swappedDimensions = true;
                    }
                    break;
                default:
                    Logger.d("Display rotation is invalid: " + displayRotation);
            }
            android.graphics.Point displaySize = new android.graphics.Point();
            getActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
            int rotatedPreviewWidth = i;
            int rotatedPreviewHeight = i1;
            int maxPreviewWidth = displaySize.x;
            int maxPreviewHeight = displaySize.y;

            if (swappedDimensions) {
                rotatedPreviewWidth = i1;
                rotatedPreviewHeight = i;
                maxPreviewWidth = displaySize.y;
                maxPreviewHeight = displaySize.x;
            }

            if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                maxPreviewWidth = MAX_PREVIEW_WIDTH;
            }

            if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                maxPreviewHeight = MAX_PREVIEW_HEIGHT;
            }
            mPreViewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth, maxPreviewHeight, largest);
        }

        private Size chooseOptimalSize(Size[] choices, int textureViewWidth,
                                       int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {

            // Collect the supported resolutions that are at least as big as the preview Surface
            List<Size> bigEnough = new ArrayList<>();
            // Collect the supported resolutions that are smaller than the preview Surface
            List<Size> notBigEnough = new ArrayList<>();
            int w = aspectRatio.getWidth();
            int h = aspectRatio.getHeight();
            for (Size option : choices) {
                if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
                        option.getHeight() == option.getWidth() * 3 / 4) {
                    if (option.getWidth() >= textureViewWidth &&
                            option.getHeight() >= textureViewHeight) {
                        bigEnough.add(option);
                    } else {
                        notBigEnough.add(option);
                    }
                }
            }

            // Pick the smallest of those big enough. If there is no one big enough, pick the
            // largest of those not big enough.
            if (bigEnough.size() > 0) {
                return Collections.min(bigEnough, new CompareSizeByArea());
            } else if (notBigEnough.size() > 0) {
                return Collections.max(notBigEnough, new CompareSizeByArea());
            } else {
                Logger.d("Couldn't find any suitable preview size");
                return choices[0];
            }
        }
    };



    /**
     * 拍照 {@link android.widget.ImageButton} 点击拍照
     */
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    try {
                        mCameraSession.setRepeatingRequest(initDngBuilder().build(), null, mHandler);
                    } catch (CameraAccessException e) {
                        ToastUtil.showMsg("请求相机权限被拒绝");
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    try {
                        updateCameraPreviewSession();
                    } catch (CameraAccessException e) {
                        ToastUtil.showMsg("请求相机权限被拒绝");
                    }
                    break;
                default:
            }
            return true;
        }
    };

    /**
     * 设置连拍的参数
     *
     * @return
     */
    private CaptureRequest.Builder initDngBuilder() {
        CaptureRequest.Builder captureBuilder = null;
        try {
            captureBuilder = mCameraSession.getDevice().createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            captureBuilder.addTarget(mImageReader.getSurface());
            captureBuilder.addTarget(surface);
            // Required for RAW capture
            captureBuilder.set(CaptureRequest.STATISTICS_LENS_SHADING_MAP_MODE, CaptureRequest.STATISTICS_LENS_SHADING_MAP_MODE_ON);
            captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_OFF);
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            captureBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, (long) ((214735991 - 13231) / 2));
            captureBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, 0);
            captureBuilder.set(CaptureRequest.SENSOR_SENSITIVITY, (10000 - 100) / 2);//设置 ISO，感光度
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, 90);
            //设置每秒30帧
            CameraManager cacreateCaptureSessionmeraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
            String cameraid = CameraCharacteristics.LENS_FACING_FRONT + "";
            CameraCharacteristics cameraCharacteristics = cacreateCaptureSessionmeraManager.getCameraCharacteristics(cameraid);
            Range<Integer> fps[] = cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            captureBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, fps[fps.length - 1]);
        } catch (CameraAccessException e) {
            Toast.makeText(getActivity(), "请求相机权限被拒绝", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "打开相机失败", Toast.LENGTH_SHORT).show();
        }
        return captureBuilder;
    }

    /**
     * 更新 相机预览 会话
     *
     * @throws CameraAccessException
     */
    private void updateCameraPreviewSession() throws CameraAccessException {
        mPreViewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
        //打开闪光灯
        mPreViewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
        mCameraSession.setRepeatingRequest(mPreViewBuilder.build(), null, mHandler);
    }

    /**
     * 相机缩放监听
     */
    private View.OnTouchListener textureOnTouchListener = new View.OnTouchListener() {
        //时时当前的zoom
        public double zoom;
        // 0<缩放比<mCameraCharacteristics.get(CameraCharacteristics
        // .SCALER_AVAILABLE_MAX_DIGITAL_ZOOM).intValue();
        //上次缩放前的zoom
        public double lastzoom;
        //两个手刚一起碰到手机屏幕的距离
        public double lenth;
        int count;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    count = 1;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (count >= 2) {
                        float x1 = event.getX(0);
                        float y1 = event.getY(0);
                        float x2 = event.getX(1);
                        float y2 = event.getY(1);
                        float x = x1 - x2;
                        float y = y1 - y2;
                        Double lenthRec = Math.sqrt(x * x + y * y) - lenth;
                        Double viewLenth = Math.sqrt(v.getWidth() * v.getWidth() + v.getHeight() * v.getHeight());
                        zoom = ((lenthRec / viewLenth) * maxRealRadio) + lastzoom;
                        picRect.top = (int) (maxZoomrect.top / (zoom));
                        picRect.left = (int) (maxZoomrect.left / (zoom));
                        picRect.right = (int) (maxZoomrect.right / (zoom));
                        picRect.bottom = (int) (maxZoomrect.bottom / (zoom));
                        Message.obtain(mUIHandler, MOVE_FOCK).sendToTarget();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    count = 0;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    count++;
                    if (count == 2) {
                        float x1 = event.getX(0);
                        float y1 = event.getY(0);
                        float x2 = event.getX(1);
                        float y2 = event.getY(1);
                        float x = x1 - x2;
                        float y = y1 - y2;
                        lenth = Math.sqrt(x * x + y * y);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    count--;
                    if (count < 2) {
                        lastzoom = zoom;
                    }
                    break;
                default:
            }
            return true;
        }
    };
    //相机缩放相关
    private Rect picRect;

    /**
     * 图片保存服务
     */
    private class ImageSaver implements Runnable {
        Image reader;

        public ImageSaver(Image reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            /*if (mTextureView.getVisibility() == View.VISIBLE){
                mTextureView.setVisibility(View.GONE);
            }
            if (mThumbnail.getVisibility() == View.GONE){
                mThumbnail.setVisibility(View.VISIBLE);
            }*/
            Logger.d("正在保存图片");
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsoluteFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                ByteBuffer buffer = reader.getPlanes()[0].getBuffer();
                byte[] buff = new byte[buffer.remaining()];
                buffer.get(buff);
                BitmapFactory.Options ontain = new BitmapFactory.Options();
                ontain.inSampleSize = 50;
                Bitmap bm = BitmapFactory.decodeByteArray(buff, 0, buff.length, ontain);
                Message.obtain(mUIHandler, SETIMAGE, bm).sendToTarget();
                outputStream.write(buff);
                Logger.d("保存图片完成");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class InnerCallBack implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case SETIMAGE:
                    if (mTextureView.getVisibility() == View.VISIBLE){
                        mTextureView.setVisibility(View.GONE);
                    }
                    if (mThumbnail.getVisibility() == View.GONE){
                        mThumbnail.setVisibility(View.VISIBLE);
                    }
                    Bitmap bm = (Bitmap) message.obj;
                    mThumbnail.setImageBitmap(bm);
                    break;
                case MOVE_FOCK:
                    mPreViewBuilder.set(CaptureRequest.SCALER_CROP_REGION, picRect);
                    try {
                        mCameraSession.setRepeatingRequest(mPreViewBuilder.build(), null, mHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }
            return false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏,包括状态栏
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_camera2, container, false);
            ButterKnife.bind(this, view);
        }
        mUIHandler = new Handler(new InnerCallBack());
        bindView();
        return view;
    }

    private void bindView() {
        view.findViewById(R.id.takePictureImageButton).setOnClickListener(this);
        //mTextureView = view.findViewById(R.id.cameraPreview);
        mTextureView.setSurfaceTextureListener(mSurfaceTextListener);
        mTextureView.setOnTouchListener(textureOnTouchListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takePictureImageButton:
                ToastUtil.showMsg("takePictureImageButton");
                break;
            default:
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCameraSession != null) {
            mCameraSession.getDevice().close();
            mCameraSession.close();
        }
    }
}

