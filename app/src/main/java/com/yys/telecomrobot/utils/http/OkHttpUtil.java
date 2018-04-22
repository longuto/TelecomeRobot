package com.yys.telecomrobot.utils.http;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import com.yys.telecomrobot.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Description OkHttp工具类 实现get、post、upload和download网络操作
 * @author hfwei
 * @version 1.0
 */
public class OkHttpUtil {

    private final static String TAG = OkHttpUtil.class.getSimpleName();

    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    private static OkHttpHandler mOkHttpHandler = new OkHttpHandler(Looper.getMainLooper());

    /**
     * get
     * @param url 请求地址
     * @param okHttpRequestCallback 回调
     */
    public static void get(final String url, final OkHttpRequestCallback okHttpRequestCallback) {
        LogUtils.i(TAG, "get");
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(e.toString(), false, okHttpRequestCallback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        sendMessage(response.body().string(), true, okHttpRequestCallback);
                    } else {
                        sendMessage("errorCode:" + response.code(), false, okHttpRequestCallback);
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            sendMessage(e.toString(), false, okHttpRequestCallback);
            e.printStackTrace();
        }
    }

    /**
     * get
     * @param url 请求地址
     * @param json json字符串
     * @param okHttpRequestCallback 回调
     */
    public static void get(final String url, final String json, final OkHttpRequestCallback okHttpRequestCallback) {
        LogUtils.i(TAG, "get");
        String requestUrl = null;
        try {
            requestUrl = url + "?jsonStr=" + URLEncoder.encode(json, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            sendMessage("errorCode:" + "UnsupportedEncodingException", false, okHttpRequestCallback);
        }
        try {
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(e.toString(), false, okHttpRequestCallback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        sendMessage(response.body().string(), true, okHttpRequestCallback);
                    } else {
                        sendMessage("errorCode:" + response.code(), false, okHttpRequestCallback);
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            sendMessage(e.toString(), false, okHttpRequestCallback);
            e.printStackTrace();
        }
    }

    /**
     * get
     * @param url 请求地址
     * @param params 参数
     * @param okHttpRequestCallback 回调
     */
    public static void get(final String url, Map<String, String> params, final OkHttpRequestCallback okHttpRequestCallback) {
        LogUtils.i(TAG, "get");
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append("?");
        int pos = 0;
        for (String key : params.keySet()) {
            if (pos > 0) {
                builder.append("&");
            }
            builder.append(String.format("%s=%s", key, params.get(key)));
            pos++;
        }
        try {
            Request request = new Request.Builder()
                    .url(builder.toString())
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(e.toString(), false, okHttpRequestCallback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        sendMessage(response.body().string(), true, okHttpRequestCallback);
                    } else {
                        sendMessage("errorCode:" + response.code(), false, okHttpRequestCallback);
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            sendMessage(e.toString(), false, okHttpRequestCallback);
            e.printStackTrace();
        }
    }

    /**
     * post
     * @param url 请求地址
     * @param okHttpRequestCallback 回调
     */
    public static void post(String url, final OkHttpRequestCallback okHttpRequestCallback) {
        LogUtils.i(TAG, "post");
        try {
            FormBody.Builder builder = new FormBody.Builder();
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(e.toString(), false, okHttpRequestCallback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        sendMessage(response.body().string(), true, okHttpRequestCallback);
                    } else {
                        sendMessage("errorCode:" + response.code(), false, okHttpRequestCallback);
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            sendMessage(e.toString(), false, okHttpRequestCallback);
            e.printStackTrace();
        }
    }

    /**
     * post
     * @param url 请求地址
     * @param params 参数
     * @param okHttpRequestCallback 回调
     */
    public static void post(final String url, final Map<String, String> params, final OkHttpRequestCallback okHttpRequestCallback) {
        LogUtils.i(TAG, "post");
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(e.toString(), false, okHttpRequestCallback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        sendMessage(response.body().string(), true, okHttpRequestCallback);
                    } else {
                        sendMessage("errorCode:" + response.code(), false, okHttpRequestCallback);
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            sendMessage(e.toString(), false, okHttpRequestCallback);
            e.printStackTrace();
        }
    }

    /**
     * post
     * @param url 请求地址
     * @param json json字符串
     * @param okHttpRequestCallback 回调
     */
    public static void post(final String url, final String json, final OkHttpRequestCallback okHttpRequestCallback) {
        LogUtils.i(TAG, "post");
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(e.toString(), false, okHttpRequestCallback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        sendMessage(response.body().string(), true, okHttpRequestCallback);
                    } else {
                        sendMessage("errorCode:" + response.code(), false, okHttpRequestCallback);
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            sendMessage(e.toString(), false, okHttpRequestCallback);
            e.printStackTrace();
        }
    }

    /**
     * upload
     * @param url 请求地址
     * @param params 参数
     * @param files 上传的文件
     * @param okHttpRequestCallback 回调
     */
    public static void upload(final String url, final Map<String, String> params, final File[] files, final OkHttpRequestCallback okHttpRequestCallback) {
        LogUtils.i(TAG, "upload");
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));
            }
            for (File file : files) {
                String fileName = file.getName();
                int index = fileName.lastIndexOf('.');
                fileName = fileName.substring(0, index);
                LogUtils.i(TAG, "fileName--->" + fileName);
                if(fileName.endsWith("image_ref1")) {
                    fileName = "image_ref1";
                }
                if(fileName.endsWith("image_best")) {
                    fileName = "image_best";
                }
                if(fileName.endsWith("image")) {
                    fileName = "image";
                }
                builder.addFormDataPart(fileName, file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            }
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(e.toString(), false, okHttpRequestCallback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        sendMessage(response.body().string(), true, okHttpRequestCallback);
                    } else {
                        sendMessage("errorCode:" + response.body().string(), false, okHttpRequestCallback);
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            sendMessage(e.toString(), false, okHttpRequestCallback);
            e.printStackTrace();
        }
    }

    public static void uploadFile(final String url, final Map<String, String> params, final File file, final OkHttpRequestCallback okHttpRequestCallback) {
        LogUtils.i(TAG, "upload");
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));
            }

            if (null != file) {
                builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            }



            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(e.toString(), false, okHttpRequestCallback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        sendMessage(response.body().string(), true, okHttpRequestCallback);
                    } else {
                        sendMessage("errorCode:" + response.body().string(), false, okHttpRequestCallback);
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            sendMessage(e.toString(), false, okHttpRequestCallback);
            e.printStackTrace();
        }
    }

    /**
     * download
     * @param url 请求地址
     * @param filePath 保存下载文件的路劲
     * @param fileName 保存下载文件的名称
     * @param md5 进行文件校验的md5值，如果不需要进行文件校验，设为null
     * @param okHttpRequestCallback 回调
     */
    public static void download(final String url, final String filePath, final String fileName, final String md5, final OkHttpRequestCallback okHttpRequestCallback) {
        LogUtils.i(TAG, "download");
        try {
            final Request request = new Request.Builder()
                    .url(url)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(e.toString(), false, okHttpRequestCallback);
                }

                @Override
                public void onResponse(Call call, Response response) {
                    LogUtils.i(TAG, "contentLength:" + response.body().contentLength());
                    FileOutputStream fos = null;
                    InputStream is = null;
                    try {
                        File file = new File(filePath);
                        if (!file.exists()) {
                            boolean result = file.mkdir();
                            if (!result) {
                                LogUtils.i(TAG, "create folder failure");
                                return;
                            }
                        }
                        file = new File(filePath, fileName);
                        if (!file.exists()) {
                            boolean result = file.createNewFile();
                            if (!result) {
                                LogUtils.i(TAG, "create file failure");
                                return;
                            }
                        }
                        fos = new FileOutputStream(file);
                        is = response.body().byteStream();
                        byte[] buffer = new byte[2048];
                        int len;
                        while (-1 != (len = is.read(buffer))) {
                            fos.write(buffer, 0, len);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != fos) {
                                fos.close();
                            }
                            if (null != is) {
                                is.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (null == md5 || md5.equals(FileCrypt.fileToMD5(filePath + fileName))) {
                        sendMessage(filePath + fileName, true, okHttpRequestCallback);
                    } else {
                        LogUtils.i(TAG, "download file failure");
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            sendMessage(e.toString(), false, okHttpRequestCallback);
            e.printStackTrace();
        }
    }

    private static void sendMessage(String result, boolean requestSuccess, OkHttpRequestCallback okHttpRequestCallback) {
        Message message = Message.obtain();
        message.what = requestSuccess ? OkHttpHandler.MESSAGE_REQUEST_SUCCESS : OkHttpHandler.MESSAGE_REQUEST_FAILURE;
        Bundle bundle = new Bundle();
        bundle.putString("result", result);
        message.obj = okHttpRequestCallback;
        message.setData(bundle);
        mOkHttpHandler.sendMessage(message);
    }

    /**
     * post
     * @param url 请求地址
     * @param params 参数
     * @return Response
     */
    public static Response post(String url, Map<String, String> params) {
        LogUtils.i(TAG, "post");
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        //加密报文
        LogUtils.i(TAG, "passs------" + url + "===" + params);

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
            return response;
        } catch (IOException e) {
            return null;
        }
    }


}
