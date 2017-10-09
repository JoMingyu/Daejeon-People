package com.daejeonpeople.activities.chatting;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Main;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.ChatListAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.network.SessionManager;
import com.daejeonpeople.valueobject.ChatListItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//민지

public class ChatList extends BaseActivity {
    private RecyclerView chatList;
    private FloatingActionButton startChattingBtn;
    private APIinterface apiInterface;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);

        chatList=(RecyclerView)findViewById(R.id.chatList);
        startChattingBtn=(FloatingActionButton)findViewById(R.id.startChatting);
        apiInterface= APIClient.getClient().create(APIinterface.class);
        dbHelper=DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        apiInterface.getTravelList("UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.body() != null){
                    ArrayList<ChatListItem> chatListItems = new ArrayList<ChatListItem>();
                    for(int i=0; i<response.body().size(); i++){
                        ChatListItem chatListItem = new ChatListItem();
                        JsonObject result = response.body().get(i).getAsJsonObject();
                        chatListItem.setTitle(result.get("title").getAsString());
                        chatListItem.setTopic(result.get("topic").getAsString());
                        chatListItem.setLastIndex(result.get("last_idx").getAsInt());
                        chatListItem.setLastMessage(result.get("last_message").getAsString());

                        chatListItems.add(i, chatListItem);
                    }
                    chatList.setAdapter(new ChatListAdapter(ChatList.this, chatListItems));
                    chatList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                t.printStackTrace();
            }
        });

        startChattingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MakeChatting.class));
            }
        });
    }

    public void onBackBtnClicked(View view){
        finish();
    }
}
