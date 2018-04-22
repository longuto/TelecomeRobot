package com.yys.telecomrobot.module.openaccount.liveness;

import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.megvii.livenessdetection.DetectionConfig;
import com.megvii.livenessdetection.DetectionFrame;
import com.megvii.livenessdetection.Detector;
import com.megvii.livenessdetection.FaceQualityManager;
import com.megvii.livenessdetection.bean.FaceInfo;
import com.sam.sdticreader.WltDec;
import com.yys.telecomrobot.R;
import com.yys.telecomrobot.app.G;
import com.yys.telecomrobot.app.MessageConstant;
import com.yys.telecomrobot.catface.PaperFm;
import com.yys.telecomrobot.livenessLib.util.ConUtil;
import com.yys.telecomrobot.livenessLib.util.ICamera;
import com.yys.telecomrobot.livenessLib.util.Screen;
import com.yys.telecomrobot.livenessLib.view.FaceMask;
import com.yys.telecomrobot.model.DetectionFrameInfo;
import com.yys.telecomrobot.model.FaceIdInfo;
import com.yys.telecomrobot.model.NopaperInfo;
import com.yys.telecomrobot.module.openaccount.OpenaccountFm;
import com.yys.telecomrobot.utils.ByteimgUtils;
import com.yys.telecomrobot.utils.GsonUtils;
import com.yys.telecomrobot.utils.LogUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yys.telecomrobot.app.G.CAMERAID;
import static com.yys.telecomrobot.app.G.IMAGE;
import static com.yys.telecomrobot.app.G.IMAGE_REF1;
import static com.yys.telecomrobot.app.G.TELECOM_OPENACC_IMAGE;
import static com.yys.telecomrobot.app.G.TELECOM_OPENACC_IMAGE_REF1;

/**
 * Created by yltang3 on 2017/11/20.
 */

public class LivenessFm extends OpenaccountFm implements TextureView.SurfaceTextureListener, Detector.DetectionListener, Camera.PreviewCallback, LivenessView {

    LivenessPresenter mLivenessPresenter;

    String mUuid;
    String[] mIdcards;
    byte[] mImgs;
    String mPhone;

    ICamera mICamera;   // face++照相机
    Detector mDetector;
    FaceQualityManager mFaceQualityManager;
    List<DetectionFrameInfo> mDetectionFrameInfos;  // 获取所有合格照片的集合
    boolean isHandleStart;// 是否开始检测

    @BindView(R.id.textureView) TextureView mTextureView;
    @BindView(R.id.promptText) TextView mPromptText;
    @BindView(R.id.faceMask) FaceMask mFaceMask;
    @BindView(R.id.btn_takephone) Button mTakephoneBtn;

    @OnClick(R.id.btn_takephone)
    public void takephone(View view) {
        if(null != mDetector) {
            mDetector.release();
        }
        mFaceMask.setFaceInfo(null);
        // 保存拍照时图片数据
        DetectionFrame temp = mDetectionFrame;
        int width = temp.getImageWidth();
        int height = temp.getImageHeight();
        mTakeImageData = temp.getImageData(null, false, 90, width > height ? width : height, false, false, 0);

        comparison();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fly_openacc_liveness;
    }

    @Override
    public void initView() {
        mTextureView.setSurfaceTextureListener(this);
    }

    @Override
    protected void managerArguments() {
        mPhone = getArguments().getString(G.PHONE);
    }

    @Override
    protected void initData() {
        mUuid = getArguments().getString(G.UUID);
        mIdcards = getArguments().getStringArray(G.IDCARDINFO);
        mImgs = getArguments().getByteArray(G.IMGS);

        mLivenessPresenter = new LivenessPresenterImp(this);
        initLiveness();
    }

    /**
     * 初始化活体检测器
     */
    private void initLiveness() {
        Screen.initialize(getContext());
        mICamera = new ICamera();
        mDetectionFrameInfos = new ArrayList<>();

        // 初始化活体检测器
        DetectionConfig config = new DetectionConfig.Builder().build();
        mDetector = new Detector(getContext(), config);
        boolean initSuccess = mDetector.init(getContext(), ConUtil.readModel(getContext()), "");
        if (!initSuccess) {
            LogUtils.i(G.TAG, "初始化检测器失败");
            speak(MessageConstant.DETECTOR_FAIL);
            enterMainAct();
        } else {
            LogUtils.i(G.TAG, "初始化检测器成功");
        }
    }

    boolean isNoFirst;

    @Override
    public void onResume() {
        LogUtils.i(G.TAG, "onResume");
        super.onResume();

        if(!isNoFirst) {
            isNoFirst = true;
            isHandleStart = false;

            Camera.getNumberOfCameras();
            // 打开照相机
            Camera mCamera = mICamera.openCamera(getCustomActivity(), CAMERAID);
            if (mCamera != null) {
                LogUtils.i(G.TAG, "摄像头打开成功，CameraId为：" + CAMERAID);
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(CAMERAID, cameraInfo);
                LogUtils.i(G.TAG, "cameraInfo.facing为：" + cameraInfo.facing);
                mFaceMask.setFrontal(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK);
//			// 获取到相机分辨率对应的显示大小，并把这个值复制给camerapreview
                RelativeLayout.LayoutParams layout_params = mICamera.getLayoutParam();
                layout_params.addRule(RelativeLayout.CENTER_IN_PARENT);
//                mTextureView.setLayoutParams(layout_params);
//                mFaceMask.setLayoutParams(layout_params);
                // 初始化人脸质量检测管理类
                mFaceQualityManager = new FaceQualityManager(1 - 0.5f, 0.5f);
            } else {
                LogUtils.i(G.TAG, "摄像头打开失败");
                speak(MessageConstant.LIVENESS_CAMERA_ERROR);
                enterMainAct();
            }
        }
    }

    @Override
    public void onPause() {
        LogUtils.i(G.TAG, "onPause");
        super.onPause();
        mICamera.closeCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDetector != null)
            mDetector.release();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        LogUtils.i(G.TAG, "onPreviewFrame");
        Camera.Size previewsize = camera.getParameters().getPreviewSize();
        // 活体检测器检测
        mDetector.doDetection(data, previewsize.width, previewsize.height, 360 - mICamera.getCameraAngle(getCustomActivity()));
    }

    private boolean mHasSurface = false;

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        LogUtils.i(G.TAG, "onSurfaceTextureAvailable");
        mHasSurface = true;
        doPreview();

        // 添加活体检测回调 （本Activity继承了DetectionListener）
        mDetector.setDetectionListener(this);
        // 添加相机预览回调（本Activity继承了PreviewCallback）
        mICamera.actionDetect(this);
    }

    /** 打开预览 */
    private void doPreview() {
        if (!mHasSurface)
            return;
        LogUtils.i(G.TAG, "打开预览相机预览");
        mICamera.startPreview(mTextureView.getSurfaceTexture());
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        LogUtils.i(G.TAG, "onSurfaceTextureSizeChanged");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        LogUtils.i(G.TAG, "onSurfaceTextureDestroyed");
        mHasSurface = false;
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        LogUtils.i(G.TAG, "onSurfaceTextureUpdated");
    }

    @Override
    public Detector.DetectionType onDetectionSuccess(DetectionFrame detectionFrame) {
        LogUtils.i(G.TAG, "onDetectionSuccess");
        mFaceMask.setFaceInfo(null);
        return Detector.DetectionType.DONE;
    }

    @Override
    public void onDetectionFailed(Detector.DetectionFailedType detectionFailedType) {
        LogUtils.i(G.TAG, "onDetectionFailed");
    }

    int mFailFrame = 0;

    @Override
    public void onFrameDetected(long l, DetectionFrame detectionFrame) {
        LogUtils.i(G.TAG, "onFrameDetected");
        //		if (sensorUtil.isVertical()) {
//			faceOcclusion(detectionFrame);
//			handleNotPass(timeout);
//			mFaceMask.setFaceInfo(detectionFrame);
//		} else {
//			if (sensorUtil.Y == 0)
//				promptText.setText("请打开手机读取运动数据权限");
//			else
//				promptText.setText("请竖直握紧手机");
//		}
        faceOcclusion(detectionFrame);
        mFaceMask.setFaceInfo(detectionFrame);
    }

    /**
     * 照镜子环节
     * 流程：time1,先从返回的DetectionFrame中获取FaceInfo。在FaceInfo中可以先判断这张照片上的人脸是否有被遮挡的状况
     * ，入股有直接return
     * time2,如果没有遮挡就把SDK返回的DetectionFramed传入人脸质量检测管理类mFaceQualityManager中获取FaceQualityErrorType的list
     * time3.通过返回的list来判断这张照片上的人脸是否合格。
     * 如果返回list为空或list中FaceQualityErrorType的对象数量为0则表示这张照片合格开始进行活体检测
     */
    private void faceOcclusion(DetectionFrame detectionFrame) {
        mFailFrame++;
        if (detectionFrame != null) {
            FaceInfo faceInfo = detectionFrame.getFaceInfo();
            if (faceInfo != null) {
                if (faceInfo.eyeLeftOcclusion > 0.5 || faceInfo.eyeRightOcclusion > 0.5) {
                    if (mFailFrame > 10) {
                        mFailFrame = 0;
                        mPromptText.setTextColor(Color.RED);
                        mPromptText.setText("请勿用手遮挡眼睛");
                    }
                    return;
                }
                if (faceInfo.mouthOcclusion > 0.5) {
                    if (mFailFrame > 10) {
                        mFailFrame = 0;
                        mPromptText.setTextColor(Color.RED);
                        mPromptText.setText("请勿用手遮挡嘴巴");
                    }
                    return;
                }
//                boolean faceTooLarge = faceInfo.faceTooLarge;
//                mIDetection.checkFaceTooLarge(faceTooLarge);
            }
        }
//		byte[] bbb = detectionFrame.getYUVData();
        // 从人脸质量检测管理类中获取错误类型list
        faceInfoChecker(mFaceQualityManager.feedFrame(detectionFrame), detectionFrame);
    }

    private boolean isAddDetectionFrame;
    DetectionFrame mDetectionFrame;
    private byte[] mTakeImageData;  // 拍照时保存的图片二进制数据

    private void faceInfoChecker(List<FaceQualityManager.FaceQualityErrorType> errorTypeList, DetectionFrame detectionFrame) {
        mDetectionFrame = detectionFrame;   // 实时更新
        if (errorTypeList == null || errorTypeList.size() == 0) {
            mPromptText.setTextColor(Color.GREEN);
            mPromptText.setText("正在储存当前人脸照片");
            // 存储照片,活体质量正常的图片
            addDetectionFrame(detectionFrame);

            // 激活拍照按钮
            activeTakePhoBtn();
        } else {
            String infoStr = "";
            FaceQualityManager.FaceQualityErrorType errorType = errorTypeList.get(0);
            if (errorType == FaceQualityManager.FaceQualityErrorType.FACE_NOT_FOUND) {
                infoStr = "请让我看到您的正脸";
            } else if (errorType == FaceQualityManager.FaceQualityErrorType.FACE_POS_DEVIATED) {
                infoStr = "请让我看到您的正脸";
            } else if (errorType == FaceQualityManager.FaceQualityErrorType.FACE_NONINTEGRITY) {
                infoStr = "请让我看到您的正脸";
            } else if (errorType == FaceQualityManager.FaceQualityErrorType.FACE_TOO_DARK) {
                infoStr = "请让光线再亮点";
            } else if (errorType == FaceQualityManager.FaceQualityErrorType.FACE_TOO_BRIGHT) {
                infoStr = "请让光线再暗点";
            } else if (errorType == FaceQualityManager.FaceQualityErrorType.FACE_TOO_SMALL) {
                infoStr = "请再靠近一些";
            } else if (errorType == FaceQualityManager.FaceQualityErrorType.FACE_TOO_LARGE) {
                infoStr = "请再离远一些";
            } else if (errorType == FaceQualityManager.FaceQualityErrorType.FACE_TOO_BLURRY) {
                infoStr = "请避免侧光和背光";
            } else if (errorType == FaceQualityManager.FaceQualityErrorType.FACE_OUT_OF_RECT) {
                infoStr = "请保持脸在人脸框中";
            }

            if (mFailFrame > 10) {
                mFailFrame = 0;
                mPromptText.setTextColor(Color.RED);
                mPromptText.setText(infoStr);
            }
        }
    }

    /** 添加活体正常的图片*/
    private void addDetectionFrame(DetectionFrame detectionFrame) {
        if(!isAddDetectionFrame && mDetectionFrameInfos.size() <= 10) { // 添加对象限制
            DetectionFrameInfo info = new DetectionFrameInfo();
            info.setDetectionFrame(detectionFrame);
            info.setFaceQuality(detectionFrame.getFaceQuality());
            mDetectionFrameInfos.add(info);
        }
    }

    /** 激活拍照按钮 */
    private void activeTakePhoBtn() {
        if(isHandleStart) {
            return;
        }
        isHandleStart = true;
        LogUtils.i(G.TAG, "存储到人脸并激活拍照按钮");
        speak(MessageConstant.LIVENESS_MOVE_FACEANDCARD);
        mTakephoneBtn.setEnabled(true);
    }

    private void comparison() {
        LogUtils.i(G.TAG, "进行人证比对");
        DetectionFrameInfo bestDetectionFrameInfo = getBestDetectionFrame();    // 获取最好的DetectionFrameInfo
        isAddDetectionFrame = true;
        mDetectionFrameInfos = null;    // 清空数据
        if(bestDetectionFrameInfo.getFaceQuality() > 20) {  // 照片可以用去人证对比
            LogUtils.i(G.TAG, "最好一张照片的质量为：" + bestDetectionFrameInfo.getFaceQuality());
            byte[] croppedFaceImageData = bestDetectionFrameInfo.getDetectionFrame().getCroppedFaceImageData();

            String currTimestamp = getCurrTimestamp();  // 保证2张照片的时间戳一致
            TELECOM_OPENACC_IMAGE_REF1 = G.OPENACC_IMAGE_START + currTimestamp + "_" + IMAGE_REF1;
            File fileIdcard = new File(Environment.getExternalStorageDirectory(), TELECOM_OPENACC_IMAGE_REF1);
            LogUtils.i(G.TAG, "身份证图片路径:" + fileIdcard.getAbsolutePath());
            ByteimgUtils.getFileFromBytes(new WltDec().decodeToBitmap(mImgs), fileIdcard.getAbsolutePath());
            TELECOM_OPENACC_IMAGE = G.OPENACC_IMAGE_START + currTimestamp + "_" + IMAGE;
            File fileImage = new File(Environment.getExternalStorageDirectory(), TELECOM_OPENACC_IMAGE);
            LogUtils.i(G.TAG, "活体保存的图片路径:" + fileImage.getAbsolutePath());
            ByteimgUtils.getFileFromBytes(croppedFaceImageData, fileImage.getAbsolutePath());
            File[] files = new File[2];
            files[0] = fileIdcard;
            files[1] = fileImage;

            // 测试走法
//            toNopaper();

            // 正式走法
            showProgress("正在进行人证比对...");
            mLivenessPresenter.comparisonFaceNet(files, mUuid);
        } else {
            LogUtils.i(G.TAG, "最好的一张照片质量为(<20，不可用于人证比对)：" + bestDetectionFrameInfo.getFaceQuality());
            speak(MessageConstant.PICNOTCOMPARE);
            enterMainAct();
        }
    }

    /** 获取最好的DetectionFrame */
    private DetectionFrameInfo getBestDetectionFrame() {
        DetectionFrameInfo detectionFrameInfo = new DetectionFrameInfo();
        for (int i = 0; i < mDetectionFrameInfos.size(); i++) {
            LogUtils.i(G.TAG, "每一帧图片的质量为：" + mDetectionFrameInfos.get(i).getFaceQuality());
            if(mDetectionFrameInfos.get(i).getFaceQuality() > detectionFrameInfo.getFaceQuality()) {
                detectionFrameInfo = mDetectionFrameInfos.get(i);
            }
        }
        return detectionFrameInfo;
    }

    /**
     * 获取当前时间戳
     */
    private String getCurrTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * 无纸化时间戳
     * @return
     */
    private String getNopaperTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date());
    }

    @Override
    public void faceNetOnSucc(String result) {
        LogUtils.i(G.TAG, "face++OnSucc + " + result);
        hideProgress();
        FaceIdInfo info = (FaceIdInfo) GsonUtils.fromJson(result, FaceIdInfo.class);
        if (info != null) {
            FaceIdInfo.ResultRef1Bean result_ref1 = info.getResult_ref1();
            double confidence = result_ref1.getConfidence();  // 置信度
            LogUtils.i(G.TAG, "人证比对的置信度为：" + confidence);
            FaceIdInfo.ResultRef1Bean.ThresholdsBean thresholds = result_ref1.getThresholds();
            double e5 = thresholds.get_$1e5();      // 最高阈值
            if (confidence >= e5) {   // 人证对比成功
                File fileImage = new File(Environment.getExternalStorageDirectory(), G.TELECOM_OPENACC_IMAGE);
                LogUtils.i(G.TAG, "更换活体图片为点击拍照时的照片:" + fileImage.getAbsolutePath());
                ByteimgUtils.getFileFromBytes(mTakeImageData, fileImage.getAbsolutePath());

                LogUtils.i(G.TAG, "face++人证比对成功，跳转nopaper");
                // 跳转无纸化
                toNopaper();
            } else {
                LogUtils.i(G.TAG, "face++人证比对失败");
                speak(MessageConstant.FACE_NOT_YOURSELF);
                enterMainAct();
            }
        } else {
            LogUtils.i(G.TAG, "face++解析失败");
            speak(MessageConstant.FACE_SERVICE_JSONNULL);
            enterMainAct();
        }
    }

    private void toNopaper() {
        PaperFm fragment = new PaperFm();
        Bundle bundle = new Bundle();
        bundle.putString(PaperFm.PDF_INFO, getPaperPama());
        bundle.putString(G.IDCARDNUMBER, mIdcards[5]);
        fragment.setArguments(bundle);
        getCustomActivity().replaceFragment(R.id.fly_content, fragment);
    }

    /**
     * 获取无纸化入参
     * @return
     */
    private String getPaperPama() {
        NopaperInfo info = new NopaperInfo();
        info.setOrder("73211091092918391");
        info.setDate(getNopaperTimestamp());
        info.setUsername(mIdcards[0]);
        info.setPassword("107372");
        info.setPhone(mPhone);
        info.setContactPhone(mPhone);
        String temp = GsonUtils.toJson(info);
        LogUtils.i(G.TAG, "无纸化的入参是：" + temp);
        return temp;
    }

    @Override
    public void faceNetOnFail(String error) {
        LogUtils.i(G.TAG, "face++OnFail + " + error);
        hideProgress();
        speak(MessageConstant.FACE_SERVICE_FAIL);
        enterMainAct();
    }
}
