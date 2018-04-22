package com.yys.telecomrobot.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.yys.telecomrobot.widget.CusProgressDialog;

import butterknife.ButterKnife;

/**
 * Created by yltang3 on 2017/11/16.
 *
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    private CusProgressDialog mProgressDia;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initBase();
        initAbility();
        initPresenter();
        initView(savedInstanceState);
    }

    /** 加载能力 */
    protected abstract void initAbility();

    /** 初始化控制中心 */
    protected abstract void initPresenter();

    /** 初始化视图 */
    protected abstract void initView(Bundle savedInstanceState);

    /** 设置当前的布局Id */
    protected abstract int getLayoutId();

    /**
     * 初始化基本操作
     */
    private void initBase() {
        ButterKnife.bind(this);
        mProgressDia = new CusProgressDialog(this);
    }

    @Override
    public void showProgress(String content) {
        if (mProgressDia != null) {
            if (!TextUtils.isEmpty(content)) {
                mProgressDia.setProgressText(content);
            }
            mProgressDia.show();
        }
    }

    @Override
    public void hideProgress() {
        if(mProgressDia != null) {
            mProgressDia.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mProgressDia != null) {
            mProgressDia.dismiss();
            mProgressDia = null;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void close() {
        finish();
    }

    //--------------------------Fragment相关--------------------------//
    /**
     * 获取Fragment管理器
     *
     * @return
     */
    public FragmentManager getBaseFragmentManager() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        return fragmentManager;
    }

    /**
     * 获取Fragment事务管理
     *
     * @return
     */
    public FragmentTransaction getFragmentTransaction() {
        return getBaseFragmentManager().beginTransaction();
    }

    /**
     * 替换一个Fragment
     *
     * @param res
     * @param fragment
     */
    public void replaceFragment(int res, Fragment fragment) {
        replaceFragment(res, fragment, false);
    }

    /**
     * 替换一个Fragment并设置是否加入回退栈
     *
     * @param res
     * @param fragment
     * @param isAddToBackStack
     */
    public void replaceFragment(int res, Fragment fragment, boolean isAddToBackStack) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.replace(res, fragment);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

    }

    /**
     * 添加一个Fragment
     *
     * @param res
     * @param fragment
     */
    public void addFragment(int res, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(res, fragment);
        fragmentTransaction.commit();
    }
    public void addFragment(int res, Fragment fragment,String tag) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(res, fragment,tag);
        fragmentTransaction.commit();
    }

    /**
     * 移除一个Fragment
     *
     * @param fragment
     */
    public void removeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 显示一个Fragment
     *
     * @param fragment
     */
    public void showFragment(Fragment fragment) {
        if (fragment.isHidden()) {
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * 隐藏一个Fragment
     *
     * @param fragment
     */
    public void hideFragment(Fragment fragment) {
        if (!fragment.isHidden()) {
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            this.getWindow().getDecorView().setSystemUiVisibility(5894);
        }
    }
}
