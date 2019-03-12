package com.example.hekai.xunw.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.activity.FoodDetailsActivity;
import com.example.hekai.xunw.bean.Food;
import com.example.hekai.xunw.utils.FlagUtils;
import com.example.hekai.xunw.utils.TimeUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2018/12/28
 **/
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    ArrayList<Food> foods;
    Context context;

    public FoodAdapter(Context context,ArrayList<Food> foods){
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_card,viewGroup,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i) {
        Food food = foods.get(i);

        Glide.with(context)
                .load(food.getImgUrl())
                .thumbnail(0.5f)
                .into(foodViewHolder.imageView);

        foodViewHolder.foodTitleText.setText(food.getTitle());
        foodViewHolder.timeText.setText(TimeUtils.utilDateToString(food.getTime()));
        foodViewHolder.authorText.setText(food.getAuthor());
        foodViewHolder.distanceText.setText(String.valueOf(food.getDistance())+"km");
        foodViewHolder.likesText.setText(String.valueOf(food.getLikes()));
        foodViewHolder.tipsText.setText(food.getTips());

        foodViewHolder.textLinearLayout.setOnClickListener((view)->{
            Intent intent = new Intent(context, FoodDetailsActivity.class);
            intent.putExtra(FlagUtils.FOOD_ID,food.getFoodId()+"");
            context.startActivity(intent);
        });

        foodViewHolder.cardView.setOnClickListener((view)->{
            Intent intent2 = new Intent(context, FoodDetailsActivity.class);
            intent2.putExtra(FlagUtils.FOOD_ID,food.getFoodId()+"");
            context.startActivity(intent2);
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
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

        private FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
