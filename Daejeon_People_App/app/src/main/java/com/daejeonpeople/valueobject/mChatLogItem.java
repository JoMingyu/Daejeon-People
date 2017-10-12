package com.daejeonpeople.valueobject;

import io.realm.RealmObject;

/**
 * Created by geni on 2017. 10. 8..
 */

public class mChatLogItem extends RealmObject {
    private String userName;
    private String content;

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
}
