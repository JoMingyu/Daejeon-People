package com.daejeonpeople.activities.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.FriendListAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.FriendListItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 10. 6..
 */

public class MakeChattingInvite extends BaseActivity {
    private TextView nextBtn;
    private RecyclerView friendList;
    private ArrayList<FriendListItem> friendListItems = new ArrayList<>();
    private APIinterface apiInterface;
    private DBHelper dbHelper;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_chatting_invite);

        nextBtn=(TextView)findViewById(R.id.nextBtn);
        friendList=(RecyclerView)findViewById(R.id.friendList);

        apiInterface=APIClient.getClient().create(APIinterface.class);
        dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
        mIntent = getIntent();

        apiInterface.getFriendList("UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.body() != null){
                    for(int i=0; i<response.body().size(); i++){
                        JsonObject result = response.body().get(i).getAsJsonObject();
                        FriendListItem friendListItem = new FriendListItem();
                        friendListItem.setName(result.get("name").getAsString());
                        friendListItem.setEmail(result.get("email").getAsString());
                        friendListItem.setPhoneNum(result.get("phone_number").getAsString());
                        friendListItem.setId(result.get("id").getAsString());
                        friendListItem.setTopic(mIntent.getStringExtra("topic"));

                        friendListItems.add(i, friendListItem);
                    }
                    friendList.setAdapter(new FriendListAdapter(friendListItems));
                    friendList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chatting.class);
                intent.putExtra("topic", mIntent.getStringExtra("topic"));
                startActivity(intent);
            }
        });
    }
}
