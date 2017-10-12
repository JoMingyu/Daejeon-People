package com.daejeonpeople.support.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by JoMingyu on 2017-05-11.
 */

public class Firebase extends FirebaseInstanceIdService {
    public static String getFirebaseToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    public static void subscribeTopic(String topicName) {
        FirebaseMessaging.getInstance().subscribeToTopic(topicName);
    }

    public static void unsubscribeTopic(String topicName) {
        FirebaseMessaging.getInstance().subscribeToTopic(topicName);
    }
}
