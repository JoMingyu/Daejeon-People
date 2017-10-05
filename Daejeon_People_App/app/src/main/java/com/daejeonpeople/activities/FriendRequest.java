package com.daejeonpeople.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.FriendRequestListAdapter;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.FriendRequestItems;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendRequest extends BaseActivity {
    private EditText destination;
    private Button requestBtn;
    private RecyclerView requestListRecyclerView;
    private APIinterface apIinterface;

    private ArrayList<FriendRequestItems> RequestItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_request);

        destination=(EditText)findViewById(R.id.destination);
        requestBtn=(Button)findViewById(R.id.requestBtn);
        requestListRecyclerView=(RecyclerView)findViewById(R.id.requestList);

        apIinterface= APIClient.getClient().create(APIinterface.class);

        apIinterface.getRequestList().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("frinedRequestList", response.body()+"");
                if(response.body().size() > 0){
                    JsonArray requestList = response.body();
                    for(int i=0; i < requestList.size(); i++){
                        JsonObject requestItem = requestList.get(i).getAsJsonObject();
                        FriendRequestItems friendRequestItems = new FriendRequestItems();
                        friendRequestItems.setUserId(requestItem.get("requester_id").getAsString());
                        friendRequestItems.setUserName(requestItem.get("name").getAsString());
                        friendRequestItems.setUserPhoneNum(requestItem.get("phone_number").getAsString());
                        friendRequestItems.setUserEmail(requestItem.get("email").getAsString());
                        friendRequestItems.setRequestDate(requestItem.get("date").getAsString());

                        RequestItem.add(i, friendRequestItems);
                    }
                }
                requestListRecyclerView.setAdapter(new FriendRequestListAdapter(RequestItem));
                requestListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                t.printStackTrace();
            }
        });

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(destination.getText().length() != 0){
                    apIinterface.friendRequest(destination.getText().toString()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("response", response.code()+"");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
    }
}
