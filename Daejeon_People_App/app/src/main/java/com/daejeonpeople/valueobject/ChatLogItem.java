package com.daejeonpeople.valueobject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by geni on 2017. 10. 8..
 */

public class ChatLogItem extends RealmObject {
    private int index;
    private String type;
    private String userName;
    private String content;
    private int remainingViews;

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

    public int getRemainingViews() {
        return remainingViews;
    }

    public void setRemainingViews(int remainingViews) {
        this.remainingViews = remainingViews;
    }
}
