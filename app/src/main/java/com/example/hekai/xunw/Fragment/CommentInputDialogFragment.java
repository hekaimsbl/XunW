package com.example.hekai.xunw.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hekai.xunw.R;
import com.example.hekai.xunw.bean.EventBusMessage;
import com.example.hekai.xunw.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/27
 **/
public class CommentInputDialogFragment extends DialogFragment {
    private View view;
    public CommentInputDialogFragment(){

    }

    @BindView(R.id.bt_commit)
    TextView tv_commit;

    @BindView(R.id.et_input)
    EditText et_input;

    @BindColor(R.color.comment_like_pressed_red)
    int colorRed;
    @BindColor(R.color.black)
    int colorBlack;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_comment_input,null);
        }
        ButterKnife.bind(this,view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CommentInputDialogFragment.this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        toUpload();
        return view;
    }

    private void toUpload() {
        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (("").equals(s.toString())){
                    tv_commit.setTextColor(colorBlack);
                }else{
                    tv_commit.setTextColor(colorRed);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_commit.setOnClickListener(v -> {
            String comment = et_input.getText().toString();
            if (("").equals(comment)){
                ToastUtil.showMsg("评论不能为空");
            }else {
                EventBus.getDefault().post(new EventBusMessage(comment));
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), (int) (dm.widthPixels * 0.65));
        }
    }
}
