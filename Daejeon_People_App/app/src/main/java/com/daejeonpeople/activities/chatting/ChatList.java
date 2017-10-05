package com.daejeonpeople.activities.chatting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.daejeonpeople.R;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.ChatListItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 10. 4..
 */

public class ChatList extends AppCompatActivity {
    private RecyclerView chatList;
    private ArrayList<ChatListItem> chatListItem = new ArrayList<>();
    private APIinterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);
        chatList=(RecyclerView)findViewById(R.id.chatList);

        apiInterface= APIClient.getClient().create(APIinterface.class);

        apiInterface.getTravelList().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray result = response.body();
                for(int i=0; i<result.size(); i++){
                    ChatListItem chatListItem = new ChatListItem();
                    JsonObject jsonObject = result.get(i).getAsJsonObject();
                    chatListItem.setTitle(jsonObject.get("title").getAsString());
                    chatListItem.setLastIndex(jsonObject.get("last_idx").getAsInt());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }
}
