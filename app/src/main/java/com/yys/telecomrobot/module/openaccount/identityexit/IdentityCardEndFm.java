package com.yys.telecomrobot.module.openaccount.identityexit;

import android.widget.TextView;

import com.yys.telecomrobot.R;
import com.yys.telecomrobot.app.G;
import com.yys.telecomrobot.app.MessageConstant;
import com.yys.telecomrobot.module.openaccount.OpenaccountFm;
import com.yys.telecomrobot.utils.LogUtils;

import butterknife.BindView;

/**
 * Created by yltang3 on 2017/11/22.
 */

public class IdentityCardEndFm extends OpenaccountFm implements IdentityEndView {

    String mCertcode;   // 传递过来的身份证号码

    IdentityEndPresenter mIdentityEndPresenter;

    @BindView(R.id.tv_time_limit)
    TextView mTimeLimitTv;

    @Override
    public int getLayoutRes() {
        return R.layout.fly_openacc_identity_exit;
    }

    @Override
    public void initView() {
        getCustomActivity().setCurrentPage(3);  // 设置头部标签

        speak(MessageConstant.PUT_YOUR_IDCARD);
        mIdentityEndPresenter.brushIdcard();
    }

    @Override
    protected void managerArguments() {
        mCertcode = getArguments().getString(G.IDCARDNUMBER);
        LogUtils.i(G.TAG, "当前身份证号码为：" + mCertcode);
    }

    @Override
    protected void initData() {
        mIdentityEndPresenter = new IdentityEndPresenterImp(this, getContext(), mCertcode);
    }

    @Override
    public void onResume() {
        super.onResume();
        mIdentityEndPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mIdentityEndPresenter.onPause();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTimeLimitTv.setText(millisUntilFinished / 1000 + "");
    }

    @Override
    public void onFinish() {
        mIdentityEndPresenter.stopBrushCard();
        enterMainAct();
    }

    @Override
    public void onFail() {
        enterMainAct();
    }

    @Override
    public void brushcardSucc() {
        speak(MessageConstant.EXIT_IDCARD_GETYOURCARD);
        enterMainAct();
    }

    @Override
    public void brushcardFail() {
        speak(MessageConstant.EXIT_IDCARD_NOYOURSELF);
        enterMainAct();
    }
}
