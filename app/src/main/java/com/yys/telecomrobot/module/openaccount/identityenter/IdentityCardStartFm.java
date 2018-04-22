package com.yys.telecomrobot.module.openaccount.identityenter;

import android.os.Bundle;
import android.widget.TextView;

import com.yys.telecomrobot.R;
import com.yys.telecomrobot.app.G;
import com.yys.telecomrobot.app.MessageConstant;
import com.yys.telecomrobot.module.openaccount.OpenaccountFm;
import com.yys.telecomrobot.module.openaccount.liveness.LivenessFm;

import butterknife.BindView;

/**
 * Created by yltang3 on 2017/11/20.
 *
 * 身份录入
 */

public class IdentityCardStartFm extends OpenaccountFm implements IdentityStartView {

    String mUuid;
    String[] mIdcards;
    byte[] mImgs;
    String mPhone;

    IdentityStartPresenter mIdentityStartPresenter;

    @BindView(R.id.tv_time_limit)
    TextView mTimeLimitTv;

    @Override
    public int getLayoutRes() {
        return R.layout.fly_openacc_identitycard;
    }

    @Override
    public void initView() {
        speak(MessageConstant.PUT_YOUR_IDCARD);
        mIdentityStartPresenter.brushIdcard();
    }

    @Override
    protected void managerArguments() {
        mPhone = getArguments().getString(G.PHONE);
    }

    @Override
    protected void initData() {
        mIdentityStartPresenter = new IdentityStartPresenterImp(this, getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        mIdentityStartPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mIdentityStartPresenter.onPause();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTimeLimitTv.setText(millisUntilFinished / 1000 + "");
    }

    @Override
    public void onFinish() {
        mIdentityStartPresenter.stopBrushCard();
        enterMainAct();
    }

    @Override
    public void onFail() {
        enterMainAct();
    }

    @Override
    public void brushcardSucc(String[] idcards, byte[] imgs) {
        mIdcards = idcards;
        mImgs = imgs;
        showProgress("正在进行联网授权...");
    }

    @Override
    public void netWorkSucc(String uuid) {
        mUuid = uuid;
        hideProgress();
        toLivenessFm();
    }

    /**
     * 跳转至活体监测界面
     */
    private void toLivenessFm() {
        LivenessFm fragment = new LivenessFm();
        Bundle bundle = new Bundle();
        bundle.putString(G.UUID, mUuid);
        bundle.putStringArray(G.IDCARDINFO, mIdcards);
        bundle.putByteArray(G.IMGS, mImgs);
        bundle.putString(G.PHONE, mPhone);
        fragment.setArguments(bundle);
        getCustomActivity().replaceFragment(R.id.fly_content, fragment);
    }

    @Override
    public void netWorkFail() {
        hideProgress();
        speak(MessageConstant.NET_WORK_FAIL);
        enterMainAct();
    }
}
