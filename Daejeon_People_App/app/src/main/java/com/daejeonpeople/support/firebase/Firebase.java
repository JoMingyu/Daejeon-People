package com.daejeonpeople.support.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by dsm2016 on 2017-05-11.
 */

public class Firebase extends FirebaseInstanceIdService {
    public static String getFirebaseToken() {
        String token = FirebaseInstanceId.getInstance().getToken();

        return token;
    }
}
