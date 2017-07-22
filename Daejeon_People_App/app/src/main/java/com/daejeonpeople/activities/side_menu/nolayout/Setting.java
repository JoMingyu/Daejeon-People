package com.daejeonpeople.activities.side_menu.nolayout;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.Button;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;

/**
 * Created by dsm2016 on 2017-07-20.
 */

public class Setting extends BaseActivity {
    private Button backBtn;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.chat_list_listview);

        final ActionBar chatting = getActionBar();
//        chatting.setCustomView(R.layout.custom_chat_list);
        chatting.setDisplayShowTitleEnabled(false);
        chatting.setDisplayShowCustomEnabled(true);
        chatting.setDisplayShowHomeEnabled(false);

        backBtn = (Button) findViewById(R.id.backBtn);
    }
}

