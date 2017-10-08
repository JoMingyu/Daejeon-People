package com.daejeonpeople.support.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.ChatLogItem;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

/**
 * Created by geni on 2017. 10. 8..
 */

public class FirebaseMessageManager extends FirebaseMessagingService {
    private APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("remoteMessage", remoteMessage.getNotification().getBody()+"");
        Log.d("remoteMessage", remoteMessage.getData()+"");
        Log.d("remoteMessage", remoteMessage.getMessageId()+"");
        Log.d("remoteMessage", remoteMessage.getFrom()+"");
        Log.d("remoteMessage", remoteMessage.getMessageType()+"");
        Intent intent = new Intent("firebase broadcast");
        intent.putExtra("message", remoteMessage.getNotification().getBody());

        sendBroadcast(intent);
    }

    public static void updateChatLog(Context context, RecyclerView chatLog, ArrayList<ChatLogItem> chatLogItems){

    }
}
