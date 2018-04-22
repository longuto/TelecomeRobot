package com.yys.telecomrobot.module.openaccount.packdetail;

import android.view.View;
import android.widget.ImageView;

import com.yys.telecomrobot.R;
import com.yys.telecomrobot.app.G;
import com.yys.telecomrobot.app.MessageConstant;
import com.yys.telecomrobot.module.openaccount.OpenaccountFm;
import com.yys.telecomrobot.module.openaccount.selectnumber.SelectnumberFm;
import com.yys.telecomrobot.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yltang3 on 2017/11/20.
 */

public class PackDetailFm extends OpenaccountFm {

    @BindView(R.id.iv_packdetail)
    ImageView mPackDetailIv;

    @OnClick({R.id.btn_back, R.id.btn_next})
    public void functions(View view) {
        switch (view.getId()) {
            case R.id.btn_back: // 返回
                enterMainAct();
                break;
            case R.id.btn_next: // 办理
                toSelectNumFm();
                break;
            default:
                break;
        }
    }

    /** 跳转选择号码Fragment */
    private void toSelectNumFm() {
        SelectnumberFm fragment = new SelectnumberFm();
        getCustomActivity().replaceFragment(R.id.fly_content, fragment);
    }

    private String openaccPack; // 套餐类型

    @Override
    public int getLayoutRes() {
        return R.layout.fly_openacc_packdetail;
    }

    @Override
    public void initView() {
        switch (openaccPack) {
            case G.OPENACCOUNTSUPERDAY: // 超级日租卡
                speak(MessageConstant.ENTER_PACK_SUPERDAY);
                mPackDetailIv.setBackgroundResource(R.drawable.openacc_packdetail_superday);
                break;
            case G.OPENACCOUNTENJOY99:  // 乐享99
                speak(MessageConstant.ENTER_PACK_ENJOY99);
                mPackDetailIv.setBackgroundResource(R.drawable.openacc_packdetail_enjoy99);
                break;
            case G.OPENACCOUNTENJOY199: // 乐享199
                speak(MessageConstant.ENTER_PACK_ENJOY199);
                mPackDetailIv.setBackgroundResource(R.drawable.openacc_packdetail_enjoy199);
                break;
            default:
                break;
        }
    }

    @Override
    protected void managerArguments() {
        openaccPack = getArguments().getString(G.OPENACCOUNTPACK);
        LogUtils.i(G.TAG, "获取到的套餐类型是：" + openaccPack);
    }

    @Override
    protected void initData() {

    }
}
