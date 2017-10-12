package com.daejeonpeople.valueobject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by geni on 2017. 10. 8..
 */

public class ChattingItem extends RealmObject {
    @PrimaryKey
    @Required
    private String topic;
    private RealmList<mChatLogItem> chatLogs;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public RealmList<mChatLogItem> getChatLogs() {
        return chatLogs;
    }

    public void setChatLogs(RealmList<mChatLogItem> chatLogs) {
        this.chatLogs = chatLogs;
    }
}
