package com.yys.telecomrobot.model;

import com.megvii.livenessdetection.DetectionFrame;

import java.io.Serializable;

/**
 * Created by yltang3 on 2017/7/25.
 */

public class DetectionFrameInfo implements Serializable{

    private DetectionFrame detectionFrame;  // face++的图片质量
    private float faceQuality;  // 图片质量

    public DetectionFrame getDetectionFrame() {
        return detectionFrame;
    }

    public void setDetectionFrame(DetectionFrame detectionFrame) {
        this.detectionFrame = detectionFrame;
    }

    public float getFaceQuality() {
        return faceQuality;
    }

    public void setFaceQuality(float faceQuality) {
        this.faceQuality = faceQuality;
    }
}
