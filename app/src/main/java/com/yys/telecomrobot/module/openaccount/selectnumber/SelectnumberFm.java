package com.yys.telecomrobot.module.openaccount.selectnumber;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.yys.telecomrobot.R;
import com.yys.telecomrobot.app.G;
import com.yys.telecomrobot.app.MessageConstant;
import com.yys.telecomrobot.base.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.yys.telecomrobot.model.SelectnumInfo;
import com.yys.telecomrobot.module.openaccount.OpenaccountFm;
import com.yys.telecomrobot.module.openaccount.identityenter.IdentityCardStartFm;
import com.yys.telecomrobot.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by yltang3 on 2017/11/20.
 */

public class SelectnumberFm extends OpenaccountFm {

    List<SelectnumInfo> mSelectnumDatas;    // 数据
    String mCheckPhone; // 选中的号码

    @BindView(R.id.recy_nums)
    RecyclerView mNumRecy;

    @OnClick(R.id.btn_sure)
    public void fun(View view) {
        if(TextUtils.isEmpty(mCheckPhone)) {
            speak(MessageConstant.UN_SELECT_NUMBER);
            return;
        }

        // 不为null
        toIdentityStartFm();
    }

    /**
     * 跳转至开始刷身份证界面
     */
    private void toIdentityStartFm() {
        IdentityCardStartFm fragment = new IdentityCardStartFm();
        Bundle bundle = new Bundle();
        bundle.putString(G.PHONE, mCheckPhone);
        fragment.setArguments(bundle);
        getCustomActivity().replaceFragment(R.id.fly_content, fragment);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fly_openacc_selectnum;
    }

    @Override
    protected void initData() {
        // 初始化数据，全未选中
        mSelectnumDatas = new ArrayList<>();
        for (String temp : G.numbers) {
            mSelectnumDatas.add(new SelectnumInfo(false, temp));
        }
    }

    @Override
    public void initView() {
        getCustomActivity().setCurrentPage(1);  // 设置头部标签
        speak(MessageConstant.CHOOSE_YOUR_NUMBER);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 4, LinearLayoutManager.HORIZONTAL, false);
        mNumRecy.setLayoutManager(manager);
        final SelectnumAdapter adapter = new SelectnumAdapter(getContext(), mSelectnumDatas);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtils.i(G.TAG, "当前选择的条目是：" + position);
                mCheckPhone = adapter.getItemData(position).getPhone(); // 当前选中的手机号码
                getDataByCheck(position);
                adapter.replaceList(mSelectnumDatas);
            }
        });
        mNumRecy.setAdapter(adapter);
    }

    /** 根据位置获取数据 */
    private void getDataByCheck(int position) {
        if(null == mSelectnumDatas) return;

        List<SelectnumInfo> nums = new ArrayList<>();
        SelectnumInfo temp;
        for (int i = 0; i < mSelectnumDatas.size(); i++) {
            String phone = mSelectnumDatas.get(i).getPhone();
            if(position == i) {
                temp = new SelectnumInfo(true, phone);
            } else {
                temp = new SelectnumInfo(false, phone);
            }
            nums.add(temp);
        }
        mSelectnumDatas = nums;
    }

    @Override
    protected void managerArguments() {

    }
}
