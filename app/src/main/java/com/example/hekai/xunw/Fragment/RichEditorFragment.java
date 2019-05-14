package com.example.hekai.xunw.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hekai.xunw.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.richeditor.RichEditor;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/19
 **/
public class RichEditorFragment extends Fragment{
    private View view;
    private boolean isTextBackgroundColorChanged;
    private boolean isTextColorChanged;
    @BindView(R.id.richEditor)
    RichEditor richEditor;

    public RichEditorFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_rich_editor, container, false);
            ButterKnife.bind(this, view);
        }
        view = course(view);
        return view;
    }

    public View course(View cFragment){

        richEditor.setEditorHeight(200);
        richEditor.setFontSize(22);
        richEditor.setPadding(10,10,10,10);
        richEditor.setPlaceholder("请开始吧...");

        String html = richEditor.getHtml();

        cFragment.findViewById(R.id.action_redo).setOnClickListener(v->richEditor.redo());
        cFragment.findViewById(R.id.action_redo).setOnClickListener(v -> richEditor.undo());
        cFragment.findViewById(R.id.action_undo).setOnClickListener(v->richEditor.undo());
        cFragment.findViewById(R.id.action_bold).setOnClickListener(v->richEditor.setBold());
        cFragment.findViewById(R.id.action_italic).setOnClickListener(v->richEditor.setItalic());
        cFragment.findViewById(R.id.action_strike_through).setOnClickListener(v->richEditor.setStrikeThrough());
        cFragment.findViewById(R.id.action_underline).setOnClickListener(v->richEditor.setUnderline());
        cFragment.findViewById(R.id.action_indent).setOnClickListener(v -> richEditor.setIndent());
        cFragment.findViewById(R.id.action_heading1).setOnClickListener(v->richEditor.setHeading(1));
        cFragment.findViewById(R.id.action_heading2).setOnClickListener(v->richEditor.setHeading(2));
        cFragment.findViewById(R.id.action_heading3).setOnClickListener(v->richEditor.setHeading(3));
        cFragment.findViewById(R.id.action_heading4).setOnClickListener(v->richEditor.setHeading(4));
        cFragment.findViewById(R.id.action_heading5).setOnClickListener(v->richEditor.setHeading(5));
        cFragment.findViewById(R.id.action_heading6).setOnClickListener(v->richEditor.setHeading(6));
        cFragment.findViewById(R.id.action_align_center).setOnClickListener(v -> richEditor.setAlignCenter());

        cFragment.findViewById(R.id.action_bg_color).setOnClickListener((v -> {
            richEditor.setTextBackgroundColor(isTextBackgroundColorChanged ? Color.TRANSPARENT : Color.YELLOW);
            isTextBackgroundColorChanged = !isTextBackgroundColorChanged;
        }));

        cFragment.findViewById(R.id.action_text_color).setOnClickListener(v -> {
            richEditor.setTextColor(isTextColorChanged ? Color.BLACK : Color.RED);
            isTextColorChanged = !isTextColorChanged;
        });

        cFragment.findViewById(R.id.action_link).setOnClickListener(v -> {
            richEditor.insertLink("link","link name");
        });

        cFragment.findViewById(R.id.action_image).setOnClickListener(v -> {
            richEditor.insertImage("https://www.codeproject.com/KB/WPF/773386/ImageButton_Sample.png","hello");
        });

        return cFragment;
    }
}
