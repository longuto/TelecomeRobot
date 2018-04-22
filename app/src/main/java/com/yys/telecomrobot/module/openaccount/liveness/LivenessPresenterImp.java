package com.yys.telecomrobot.module.openaccount.liveness;

import com.yys.telecomrobot.app.G;
import com.yys.telecomrobot.app.NetAddressConstant;
import com.yys.telecomrobot.utils.http.OkHttpRequestCallback;
import com.yys.telecomrobot.utils.http.OkHttpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yltang3 on 2017/11/21.
 */

public class LivenessPresenterImp implements LivenessPresenter {

    LivenessView mLivenessView;

    public LivenessPresenterImp(LivenessView view) {
        this.mLivenessView = view;
    }

    @Override
    public void comparisonFaceNet(File[] files, String uuid) {
        Map<String, String> map = new HashMap<>();
        map.put("api_key", G.api_key);
        map.put("api_secret", G.api_secret);
        map.put("comparison_type", "0");
        map.put("face_image_type", "raw_image");
        map.put("uuid", uuid);
        OkHttpUtil.upload(NetAddressConstant.FACEVERIFLy, map, files, okHttpRequestCallback);
    }

    OkHttpRequestCallback okHttpRequestCallback = new OkHttpRequestCallback() {
        @Override
        public void onSuccess(String result) {
            mLivenessView.faceNetOnSucc(result);
        }

        @Override
        public void onFailure(String error) {
            mLivenessView.faceNetOnFail(error);
        }
    };
}
