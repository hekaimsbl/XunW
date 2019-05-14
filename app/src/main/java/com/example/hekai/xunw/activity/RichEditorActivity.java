package com.example.hekai.xunw.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.hekai.xunw.Fragment.RichEditorFragment;
import com.example.hekai.xunw.R;

import butterknife.ButterKnife;

public class RichEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_editor);
        init();
        addLayout();
    }

    private void addLayout() {
        FrameLayout contentView = (FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup viewGroup = (ViewGroup) contentView.getChildAt(0);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        FrameLayout fragment = new FrameLayout(this);
        fragment.setId(R.id.frameLayout_1);
        viewGroup.addView(fragment,params);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        RichEditorFragment richEditorFragment = new RichEditorFragment();
        transaction.add(fragment.getId(),richEditorFragment);
        transaction.commit();
    }

    public void init() {
        ButterKnife.bind(this);
    }
}
