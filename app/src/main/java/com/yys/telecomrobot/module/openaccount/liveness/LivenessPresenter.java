package com.yys.telecomrobot.module.openaccount.liveness;

import java.io.File;

/**
 * Created by yltang3 on 2017/11/21.
 */

public interface LivenessPresenter {

    /**
     * 进行face++网络人证比对
     * @param files
     * @param mUuid
     */
    void comparisonFaceNet(File[] files, String mUuid);
}
