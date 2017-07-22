package com.daejeonpeople.activities.side_menu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.daejeonpeople.support.network.SessionManager;
import com.daejeonpeople.support.views.SnackbarManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dsm2016 on 2017-07-20.
 */

public class WishList extends BaseActivity {
    private Button backBtn;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_listview);

        final ActionBar chatting = getActionBar();
        chatting.setCustomView(R.layout.custom_wishlist);
        chatting.setDisplayShowTitleEnabled(false);
        chatting.setDisplayShowCustomEnabled(true);
        chatting.setDisplayShowHomeEnabled(false);

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