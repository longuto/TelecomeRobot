package com.yys.telecomrobot.catface;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yys.telecomrobot.R;
import com.yys.telecomrobot.module.openaccount.OpenaccountActivity;
import com.yys.telecomrobot.module.openaccount.identityexit.IdentityCardEndFm;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/17/017.
 */

public class SignFm extends Fragment {


    @BindView(R.id.sv)  SignView sv;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_sign, container, false);
        ButterKnife.bind(this, view);
        ((OpenaccountActivity) getActivity()).setCurrentPage(2);
        return view;
    }


    @OnClick({R.id.bt_resign, R.id.bt_submit}) void event(View view) {
        switch (view.getId()) {
            case R.id.bt_resign:
                sv.clear();
                break;

            case R.id.bt_submit:
                if (sv.getTouched()) {
                    try {
                        sv.save("/sdcard/mobileRobot/sign.png", true, 10);
                        Fragment fragment = new IdentityCardEndFm();
                        fragment.setArguments(getArguments());
                        ((OpenaccountActivity) getActivity()).replaceFragment(R.id.fly_content, fragment);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "您没有签名~", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    @Override public void onStop() {
        super.onStop();
        sv.clear();
    }
}
