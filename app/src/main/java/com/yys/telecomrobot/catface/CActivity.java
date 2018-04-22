package com.yys.telecomrobot.catface;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yys.telecomrobot.R;

public class CActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);


        replaceFm(new SignFm());
//        replaceFm(new PaperFm());
    }


    public void t() {

    }



    FragmentManager mFmManager = getSupportFragmentManager();

    public void replaceFm(Fragment fg) {
        FragmentTransaction fmTransaction = mFmManager.beginTransaction();
        fmTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fmTransaction.replace(R.id.fl, fg).commit();
    }
}
