package com.daejeonpeople.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.ShareChatListAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.ShareChatListItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 10. 16..
 */

public class ShareChatList extends BaseActivity {
    private Intent mIntent;
    private RecyclerView chatList;
    private APIinterface apiInterface;
    private DBHelper dbHelper;
    private ArrayList<ShareChatListItem> shareChatListItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_chat_list);
        mIntent = getIntent();
        chatList = (RecyclerView)findViewById(R.id.chatList);
        apiInterface = APIClient.getClient().create(APIinterface.class);
        dbHelper=DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        apiInterface.getTravelList("UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.body() != null){
                    for(int i=0; i<response.body().size(); i++){
                        ShareChatListItem shareChatListItem = new ShareChatListItem();
                        JsonObject result = response.body().get(i).getAsJsonObject();
                        shareChatListItem.setChatName(result.get("title").getAsString());
                        shareChatListItem.setTopic(result.get("topic").getAsString());

                        shareChatListItems.add(i, shareChatListItem);
                    }
                    chatList.setAdapter(new ShareChatListAdapter(getApplicationContext(), shareChatListItems, mIntent.getIntExtra("content_id", 0)));
                    chatList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
