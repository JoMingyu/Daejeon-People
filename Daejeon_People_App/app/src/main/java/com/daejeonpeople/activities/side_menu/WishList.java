package com.daejeonpeople.activities.side_menu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Main;
import com.daejeonpeople.activities.account.SignIn;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.WishlistAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.network.SessionManager;
import com.daejeonpeople.support.views.SnackbarManager;
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
 * Created by dsm2016 on 2017-07-20.
 */

public class WishList extends BaseActivity {
    private Button backBtn;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<WishlistItem> Dataset;
    private APIinterface apIinterface;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist);

        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        mRecyclerView = (RecyclerView)findViewById(R.id.wishlist_recycler);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



        apIinterface = APIClient.getClient().create(APIinterface.class);
        apIinterface.getWish("UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.code()== 200) {
                    Log.d("response", "SUCCESS");
                    JsonArray jsonArray = response.body();

                    for(int i = 0; i < jsonArray.size(); i++){
                        WishlistItem wishlistItem = new WishlistItem();
                        wishlistItem.setTitle(jsonArray.get(i).getAsJsonObject().get("title").toString());
                        wishlistItem.setAddress(jsonArray.get(i).getAsJsonObject().get("address").toString());
                        wishlistItem.setBack_image(jsonArray.get(i).getAsJsonObject().get("image").toString());
                        Dataset.add(wishlistItem);
                    }
                    Dataset = new ArrayList<>();
                    myAdapter = new WishlistAdapter(Dataset);
                    mRecyclerView.setAdapter(myAdapter);

                } else if(response.code() == 204) {
                    Log.d("response", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                t.printStackTrace();
            }
        });

        backBtn = (Button) findViewById(R.id.wishlist_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main.class));
            }
        });
    }
}