package com.yys.telecomrobot.model;

/**
 * Created by yltang3 on 2017/11/20.
 *
 * 选择号码的详情
 */

public class SelectnumInfo {

    public SelectnumInfo(boolean isChoose, String phone) {
        this.isChoose = isChoose;
        this.phone = phone;
    }

    public SelectnumInfo() {}

    private boolean isChoose;   // 是否选中

    private String phone;   // 号码

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
