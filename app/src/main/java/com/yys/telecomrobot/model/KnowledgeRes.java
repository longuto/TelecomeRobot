package com.yys.telecomrobot.model;

/**
 * Created by yltang3 on 2017/8/31.
 * 知识库答案
 */

public class KnowledgeRes {

    /**
     * checkTime : false
     * content : 我的爸爸妈妈是大机器人。
     * fuText : false
     * id : 61
     */

    private boolean checkTime;
    private String content;
    private boolean fuText;
    private int id;

    public boolean isCheckTime() {
        return checkTime;
    }

    public void setCheckTime(boolean checkTime) {
        this.checkTime = checkTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFuText() {
        return fuText;
    }

    public void setFuText(boolean fuText) {
        this.fuText = fuText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
