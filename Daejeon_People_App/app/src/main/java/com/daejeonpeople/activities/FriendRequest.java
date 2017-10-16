package com.daejeonpeople.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.FriendRequestListAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.security.AES;
import com.daejeonpeople.valueobject.FriendRequestItems;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendRequest extends BaseActivity {
    private EditText destination;
    private Button inqueryBtn, friendRequestBtn;
    private TextView userName, userEmail, userPhoneNum;
    private RecyclerView requestListRecyclerView;
    private LinearLayout userInqueryItem;
    private ImageView profile;
    private APIinterface apIinterface;
    private DBHelper dbHelper;

    private ArrayList<FriendRequestItems> RequestItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_request);

        destination=(EditText)findViewById(R.id.destination);
        inqueryBtn=(Button)findViewById(R.id.inqueryBtn);
        friendRequestBtn=(Button)findViewById(R.id.friendRequestBtn);
        userName=(TextView)findViewById(R.id.userName);
        userEmail=(TextView)findViewById(R.id.userEmail);
        userPhoneNum=(TextView)findViewById(R.id.userPhoneNum);
        profile=(ImageView)findViewById(R.id.profile);
        requestListRecyclerView=(RecyclerView)findViewById(R.id.requestList);

        apIinterface=APIClient.getClient().create(APIinterface.class);
        dbHelper=DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        inqueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apIinterface.userInquery(destination.getText().toString()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body() != null){
                            final JsonObject result = response.body();
                            profile.setVisibility(View.VISIBLE);
                            userName.setText(AES.decrypt(result.get("name").getAsString()));
                            userEmail.setText(AES.decrypt(result.get("email").getAsString()));
                            userPhoneNum.setText(result.get("phone_number").getAsString());

                            friendRequestBtn.setVisibility(View.VISIBLE);
                            friendRequestBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("id", result.get("id").getAsString());
                                    apIinterface.friendRequest("UserSession="+dbHelper.getCookie(), result.get("id").getAsString()).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            Toast.makeText(getApplicationContext(), "친구요청 성공", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        apIinterface.getRequestList("UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("frinedRequestList", response.body()+"");
                if(response.body() != null){
                    JsonArray requestList = response.body();
                    for(int i=0; i < requestList.size(); i++){
                        JsonObject requestItem = requestList.get(i).getAsJsonObject();
                        FriendRequestItems friendRequestItems = new FriendRequestItems();
                        friendRequestItems.setUserId(requestItem.get("requester_id").getAsString());
                        friendRequestItems.setUserName(AES.decrypt(requestItem.get("name").getAsString()));
                        if(requestItem.get("phone_number") != null){
                            friendRequestItems.setUserPhoneNum(requestItem.get("phone_number").getAsString());
                        } else {
                            friendRequestItems.setUserPhoneNum("전화번호 없음");
                        }
                        friendRequestItems.setUserEmail(AES.decrypt(requestItem.get("email").getAsString()));
                        friendRequestItems.setRequestDate(requestItem.get("date").getAsString());

                        RequestItem.add(i, friendRequestItems);
                    }
                }
                requestListRecyclerView.setAdapter(new FriendRequestListAdapter(RequestItem, getApplicationContext()));
                requestListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void onBackBtnClicked(View view){
        finish();
    }
}
