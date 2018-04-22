
package com.yys.telecomrobot.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.yys.telecomrobot.R;


/**
 * Created by lidaofu on 2015/8/15.
 * eamil lidaofu_zlx@163.com
 */
public class CusProgressDialog extends Dialog {

    private TextView tv_progress;
    private AVLoadingIndicatorView mAvLoad;
    private Context context;

    public CusProgressDialog(Context context) {
        super(context);
        initView(context);
    }


    public CusProgressDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    protected CusProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            this.getWindow().getDecorView().setSystemUiVisibility(5894);
        }
    }

    private void initView(Context context) {
        this.context = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置无标题
        getWindow().setBackgroundDrawable(new ColorDrawable());//设置无背景边框
        this.setCancelable(false);
        setContentView(R.layout.progress_dialog);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        mAvLoad = (AVLoadingIndicatorView) findViewById(R.id.image);
    }

    public void setProgressText(String text) {
        tv_progress.setText(text);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void show() {
        super.show();
        mAvLoad.smoothToShow();
    }

    @Override
    public void hide() {
        super.hide();
        mAvLoad.smoothToHide();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mAvLoad.smoothToHide();
    }
}