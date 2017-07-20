package com.daejeonpeople.activities.side_menu;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.daejeonpeople.R;

/**
 * Created by dsm2016 on 2017-07-20.
 */

public class FriendList extends Activity {
    private Button backBtn;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_book_listview);

        final ActionBar chatting = getActionBar();
        chatting.setCustomView(R.layout.custom_address_book);
        chatting.setDisplayShowTitleEnabled(false);
        chatting.setDisplayShowCustomEnabled(true);
        chatting.setDisplayShowHomeEnabled(false);

        backBtn = (Button) findViewById(R.id.backBtn);
    }
}
