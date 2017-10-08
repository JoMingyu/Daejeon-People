package com.daejeonpeople.valueobject;

/**
 * Created by geni on 2017. 10. 8..
 */

public class ChatLogItem {
    private int index;
    private String type;
    private String userName;
    private String content;
    private String remainingViewsl;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemainingViewsl() {
        return remainingViewsl;
    }

    public void setRemainingViewsl(String remainingViewsl) {
        this.remainingViewsl = remainingViewsl;
    }
}
