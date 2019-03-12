package com.example.hekai.xunw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hekai.xunw.R;

import java.util.List;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2018/12/26
 **/
public class MainListAdapter extends BaseAdapter {
    List<String> lists;
    Context context;

    public MainListAdapter(List<String> lists,Context context){
        this.lists = lists;
        this.context = context;
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_list,null);
        TextView textView = convertView.findViewById(R.id.text_listItem);
        textView.setText(lists.get(position));
        return convertView;
    }
}
