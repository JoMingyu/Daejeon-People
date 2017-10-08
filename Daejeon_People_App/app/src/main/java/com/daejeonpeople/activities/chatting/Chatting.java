package com.daejeonpeople.activities.chatting;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.MapView;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.ChatLogAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.firebase.Firebase;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.ChatLogItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 5. 18..
 */

//근철

public class Chatting extends BaseActivity {
    private Button mapViewBtn, sendBtn;
    private EditText inputMessage;
    private RecyclerView chatLog;
    private APIinterface apiInterface;
    private Intent mIntent;
    private DBHelper dbHelper;
    private ArrayList<ChatLogItem> chatLogItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting);

        mapViewBtn=(Button)findViewById(R.id.mapViewBtn);
        sendBtn=(Button)findViewById(R.id.sendBtn);
        inputMessage=(EditText)findViewById(R.id.inputMessage);
        chatLog=(RecyclerView)findViewById(R.id.chatLog);
        mIntent = getIntent();
        dbHelper=DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        apiInterface=APIClient.getClient().create(APIinterface.class);

        mapViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapView.class));
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.sendMessage("UserSession="+dbHelper.getCookie(), mIntent.getStringExtra("topic"), "text", inputMessage.getText().toString()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        apiInterface.inqueryMessages(mIntent.getStringExtra("topic"), "UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonArray>() {
                            @Override
                            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                                if(response.body() != null){
                                    for(int i=0; i<response.body().size(); i++){
                                        JsonObject result = response.body().get(i).getAsJsonObject();
                                        ChatLogItem chatLogItem = new ChatLogItem();
                                        chatLogItem.setUserName(result.get("name").getAsString());
                                        chatLogItem.setContent(result.get("content").getAsString());
                                        chatLogItem.setIndex(result.get("idx").getAsInt());
                                        chatLogItem.setType(result.get("type").getAsString());
                                        chatLogItem.setRemainingViewsl(result.get("remaining_views").getAsString());

                                        chatLogItems.add(i, chatLogItem);
                                    }
                                    chatLog.setAdapter(new ChatLogAdapter(chatLogItems));
                                    chatLog.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonArray> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    public void onBackBtnClicked(View view){
        Firebase.unsubscribeTopic(mIntent.getStringExtra("topic"));
        finish();
    }
}
