package com.daejeonpeople.activities.mChatting;

/**
 * Created by geni on 2017. 10. 10..
 */

public class mChatData {
    private String userName;
    private String message;

    public mChatData() { }

    public mChatData(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
