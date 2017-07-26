package com.daejeonpeople.activities.side_menu;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Main;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.network.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;
//민지

public class ChatList extends BaseActivity {
    private Button backBtn;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_listview);

        final ActionBar chatting = getActionBar();
        chatting.setCustomView(R.layout.custom_chat_list);
        chatting.setDisplayShowTitleEnabled(false);
        chatting.setDisplayShowCustomEnabled(true);
        chatting.setDisplayShowHomeEnabled(false);

        backBtn = (Button) findViewById(R.id.backBtn);

        AQuery aq = new AQuery(getApplicationContext());

        aq.ajax("http://52.79.134.200/travel", String.class, new AjaxCallback<String>() {
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
