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
import com.daejeonpeople.support.network.SessionManager;
import com.daejeonpeople.support.views.SnackbarManager;
import com.daejeonpeople.valueobject.WishlistItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2017-07-20.
 */

public class WishList extends BaseActivity {
    private Button backBtn;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<WishlistItem> myDataset;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_listview);

        mRecyclerView = (RecyclerView)findViewById(R.id.wishlist_recycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        myAdapter = new WishlistAdapter(myDataset);
        mRecyclerView.setAdapter(myAdapter);

//        myDataset.add(new WishlistItem(""))


        backBtn = (Button) findViewById(R.id.backBtn);

        AQuery aq = new AQuery(getApplicationContext());

        aq.ajax("http://52.79.134.200/wish", String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String response, AjaxStatus status) {
                if(status.getCode() == 200) {
                    try {
                        JSONObject res = new JSONObject(response);
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        }.method(AQuery.METHOD_GET).cookie("UserSession", SessionManager.getCookieFromDB(getApplicationContext())));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main.class));
            }
        });
    }
}