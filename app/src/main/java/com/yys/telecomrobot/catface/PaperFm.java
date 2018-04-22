package com.yys.telecomrobot.catface;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yys.telecomrobot.R;
import com.yys.telecomrobot.app.MessageConstant;
import com.yys.telecomrobot.control.tts.TtsSingleton;
import com.yys.telecomrobot.module.openaccount.OpenaccountActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyh
 */
/*
{"order": "73211091092918391","date":"2017年8月31日","username":"邱隘镇","password":"107372","phone":"12345678901","contactPhone":"12121212121"}
*/
public class PaperFm extends Fragment {
    public static final String PDF_INFO = "PDF_INFO";
    TtsSingleton mTtsInstance;

    @BindView(R.id.wv) WebView wv;


    // 跳转至签名
    @OnClick(R.id.bt_ok) void ok() {
        Fragment fragment = new SignFm();
        fragment.setArguments(getArguments());
        ((OpenaccountActivity) getActivity()).replaceFragment(R.id.fly_content, fragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTtsInstance = TtsSingleton.getInstance();
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_paper, container, false);
        ButterKnife.bind(this, view);
        mTtsInstance.startSynthesizer(MessageConstant.READ_NOPAPER);
        init();
        return view;
    }

    private void init() {
        ((OpenaccountActivity) getActivity()).setCurrentPage(2);

        getPdfInfo();
        loadWebView();
    }

    String pdfInfo = "";

    void getPdfInfo() {
        System.out.println("catface getPdfInfo: " + getArguments().getString(PDF_INFO));
        pdfInfo = (null == getArguments().getString(PDF_INFO)? "" : getArguments().getString(PDF_INFO));
    }


    @SuppressLint("AddJavascriptInterface") void loadWebView() {
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("file:///android_asset/www/index.html");
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDefaultTextEncodingName("utf-8");
        wv.addJavascriptInterface(new JavaMethod(), "ZJDX");
    }


    class JavaMethod {
        @JavascriptInterface public String getPdfInfo() {
            return pdfInfo;
        }
    }
}
