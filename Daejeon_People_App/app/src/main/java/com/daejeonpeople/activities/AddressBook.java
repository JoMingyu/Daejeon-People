package com.daejeonpeople.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.AddressBookAdapter;
import com.daejeonpeople.adapter.ConsonantsBtnAdapter;
import com.daejeonpeople.adapter.FriendListAdapter;
import com.daejeonpeople.adapter.WishlistAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.security.AES;
import com.daejeonpeople.valueobject.AddressBookListItem;
import com.daejeonpeople.valueobject.FriendListItem;
import com.daejeonpeople.valueobject.MainItemMonthly;
import com.daejeonpeople.valueobject.MainItemPopular;
import com.daejeonpeople.valueobject.WishlistItem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 5. 28..
 */

// 근철

public class AddressBook extends BaseActivity {
    private RecyclerView mRecyclerView, sort;
    private TextView myName, myEmail, myPhoneNum, countOfAddress;
    private String[] consonants = {
            "#", "ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ", "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    private ArrayList<AddressBookListItem> Dataset = new ArrayList<>();
    private ArrayList<FriendListItem> friendListItems = new ArrayList<>();
    private APIinterface apIinterface;
    private FloatingActionButton addFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_book);

        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        mRecyclerView = (RecyclerView) findViewById(R.id.friendlist_recycler);
        myName=(TextView)findViewById(R.id.myName);
        myEmail=(TextView)findViewById(R.id.myEmail);
        myPhoneNum=(TextView)findViewById(R.id.myPhoneNum);
        countOfAddress=(TextView)findViewById(R.id.countOfAddress);
        sort=(RecyclerView)findViewById(R.id.sorts);
        sort.setAdapter(new ConsonantsBtnAdapter(consonants));
        sort.setLayoutManager(new LinearLayoutManager(this));
        addFriend = (FloatingActionButton)findViewById(R.id.addFriend);

        apIinterface = APIClient.getClient().create(APIinterface.class);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FriendRequest.class));
            }
        });

        apIinterface.getMyPage("UserSession=" + dbHelper.getCookie()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                myName.setText(AES.decrypt(response.body().get("name").toString()));
                myEmail.setText(AES.decrypt(response.body().get("email").toString()));
                myPhoneNum.setText(response.body().get("phone_number").toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        apIinterface.getFriendList("UserSession=" + dbHelper.getCookie()).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.code() == 200) {
                    if(response.body() != null){
                        countOfAddress.setText((response.body().size()) + "");
                        for(int i=0; i<response.body().size(); i++){
                            JsonObject result = response.body().get(i).getAsJsonObject();
                            FriendListItem friendListItem = new FriendListItem();
                            friendListItem.setName(AES.decrypt(result.get("name").getAsString()));
                            friendListItem.setPhoneNum(result.get("phone_number").getAsString());
                            friendListItem.setEmail(AES.decrypt(result.get("email").getAsString()));

                            friendListItems.add(i, friendListItem);
                        }
                        mRecyclerView.setAdapter(new AddressBookAdapter(friendListItems));
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                } else if(response.code() == 204) {
                    Log.d("response", "FAIL");
                }
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
