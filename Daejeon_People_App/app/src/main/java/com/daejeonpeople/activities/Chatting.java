package com.daejeonpeople.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;

/**
 * Created by geni on 2017. 5. 18..
 */

//근철

public class Chatting extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting);

        final ActionBar chatting = getActionBar();
        //chatting.setCustomView(R.layout.custom_chatting);
        chatting.setDisplayShowTitleEnabled(false);
        chatting.setDisplayShowCustomEnabled(true);
        chatting.setDisplayShowHomeEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.chatting_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.viewMap){
            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
