package com.example.hekai.xunw.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hekai.xunw.Interface.LoadMoreDataListener;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.activity.DelicacyDetailsActivity;
import com.example.hekai.xunw.activity.FoodDetailsActivity;
import com.example.hekai.xunw.bean.Food;
import com.example.hekai.xunw.utils.FlagUtils;
import com.example.hekai.xunw.utils.NetTool;
import com.example.hekai.xunw.utils.TimeUtils;
import com.example.hekai.xunw.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2018/12/28
 **/
public class FoodRecyclerViewAdapter extends RecyclerView.Adapter {
    /**
     * Item Type
     */
    public static final int TYPE_ITEM = 0;
    /**
     * 底部视图Type
     */
    public static final int TYPE_FOOT = 1;

    private LayoutInflater inflater;
    private List<Food> foods;
    private Context context;
    private RecyclerView mRecyclerView;
    private boolean isLoading;
    private int totalItemCount;
    private int lastVisibleItemPosition;

    private int visibleThreshold = 5;

    public FoodRecyclerViewAdapter(Context context,RecyclerView recyclerView){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mRecyclerView = recyclerView;

        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //mRecyclerView添加滑动事件监听
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                        //此时是刷新状态
                        if (mMoreDataListener != null){
                            mMoreDataListener.loadMoreData();}
                        isLoading = true;
                    }
                }
            });
        }
    }

    public void setLoaded(){
        isLoading = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder;
        if (i == TYPE_ITEM){
            holder = new FoodRecyclerViewHolder(inflater.inflate(R.layout.item_card,viewGroup,false));

        }else {
            holder = new RecyclerViewFooterHolder(inflater.inflate(R.layout.item_recyclerview_footer,viewGroup,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof FoodRecyclerViewHolder){
            FoodRecyclerViewHolder foodViewHolder = (FoodRecyclerViewHolder) viewHolder;
            Food food = foods.get(i);

            if (!food.getImageUrls().isEmpty()){
                Glide.with(context)
                        .load(NetTool.HOST_URL_ONE_IMAGE + food.getImageUrls().get(0))
                        .thumbnail(0.5f)
                        .into(foodViewHolder.imageView);
            }else {
                Glide.with(context)
                        .load(R.drawable.default_img)
                        .thumbnail(0.5f)
                        .into(foodViewHolder.imageView);
                //foodViewHolder.imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_image_black_24dp));
            }


            foodViewHolder.foodTitleText.setText(food.getTitle());
            foodViewHolder.timeText.setText(TimeUtils.utilDateToString(food.getDate()));
            foodViewHolder.authorText.setText(food.getUserName());
            if (food.getDistance() == null){
                foodViewHolder.distanceText.setText("0km");

            }else {
                foodViewHolder.distanceText.setText((food.getDistance()+"km"));
            }
            foodViewHolder.likesText.setText((food.getRecommendNumber() + " 推荐"));

            StringBuffer sb = new StringBuffer();
            if (food.getTags()!=null){
                for (String tag : food.getTags()) {
                    sb.append("#");
                    sb.append(tag);
                    sb.append("\t");
                }
                foodViewHolder.tipsText.setText(sb.toString());
            }

           foodViewHolder.cardView.setOnClickListener((view)->{
                Intent intent2 = new Intent(context, DelicacyDetailsActivity.class);
                intent2.putExtra(FlagUtils.FOOD_ID,food.getFoodId());
                context.startActivity(intent2);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return foods.get(position) != null ? TYPE_ITEM : TYPE_FOOT;
    }

    @Override
    public int getItemCount() {
        return foods == null ? 0 : foods.size();
    }

    public class FoodRecyclerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_card)
        CardView cardView;

        @BindView(R.id.food_image)
        ImageView imageView;

        @BindView(R.id.food_title)
        TextView foodTitleText;

        @BindView(R.id.text_layout)
        LinearLayout textLinearLayout;

        @BindView(R.id.food_time)
        TextView timeText;

        @BindView(R.id.food_author)
        TextView authorText;

        @BindView(R.id.distance)
        TextView distanceText;

        @BindView(R.id.likes)
        TextView likesText;

        @BindView(R.id.tips)
        TextView tipsText;

        private FoodRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(v -> {
                ToastUtil.showMsg("item" );
            });
        }
    }

    public class RecyclerViewFooterHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.pb)
        ProgressBar pb;

        public RecyclerViewFooterHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setData(List<Food> data){
        foods = data;
    }

    private LoadMoreDataListener mMoreDataListener;

    public void setOnMoreDataLoadListener(LoadMoreDataListener onMoreDataLoadListener){
        mMoreDataListener = onMoreDataLoadListener;
    }

}
