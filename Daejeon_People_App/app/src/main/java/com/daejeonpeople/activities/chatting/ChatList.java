package com.daejeonpeople.activities.chatting;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Main;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.network.SessionManager;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//민지

public class ChatList extends BaseActivity {
    private RecyclerView chatList;
    private FloatingActionButton startChattingBtn;
    private APIinterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);

        chatList=(RecyclerView)findViewById(R.id.chatList);
        startChattingBtn=(FloatingActionButton)findViewById(R.id.startChatting);
        apiInterface= APIClient.getClient().create(APIinterface.class);

        startChattingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MakeChatting.class));
            }
        });
    }

    public void onBackBtnClicked(View view){
        finish();
    }
}
