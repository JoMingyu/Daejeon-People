package com.daejeonpeople.valueobject;

/**
 * Created by geni on 2017. 10. 4..
 */

public class ChatListItem {
    private String topic;
    private String title;
    private int lastIndex;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }
}
