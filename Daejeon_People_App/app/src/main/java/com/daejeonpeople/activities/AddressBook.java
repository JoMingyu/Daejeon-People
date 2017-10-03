package com.daejeonpeople.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.AddressBookAdapter;
import com.daejeonpeople.adapter.WishlistAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.AddressBookListItem;
import com.daejeonpeople.valueobject.WishlistItem;
import com.google.gson.Gson;
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

@Deprecated
public class AddressBook extends BaseActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<AddressBookListItem> Dataset;
    private APIinterface apIinterface;
    private FloatingActionButton addFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_book);

        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        mRecyclerView = (RecyclerView) findViewById(R.id.friendlist_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Dataset = new ArrayList<>();
        myAdapter = new AddressBookAdapter(Dataset);
        mRecyclerView.setAdapter(myAdapter);
        addFriend = (FloatingActionButton)findViewById(R.id.addFriend);

        apIinterface = APIClient.getClient().create(APIinterface.class);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        apIinterface.getFriend("UserSession=" + dbHelper.getCookie()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == 200) {
                    Log.d("response", "SUCCESS");
                    Gson gson = new Gson();
                    AddressBookListItem[] items = gson.fromJson(response.body().toString(), AddressBookListItem[].class);
                    ((AddressBookAdapter)mRecyclerView.getAdapter()).setData(items);
                    mRecyclerView.getAdapter().notifyDataSetChanged();

                    response.body();
                } else if(response.code() == 204) {
                    Log.d("response", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });

        for(int i = 0; i < 5; i++) {
            AddressBookListItem addresslistItem = new AddressBookListItem();
            addresslistItem.setUser_name("정민지");
            addresslistItem.setUser_email("alswl4152@naver.com");
            addresslistItem.setPhone_number("010-9945-7580");
            this.Dataset.add(i, addresslistItem);
        }
    }
}
