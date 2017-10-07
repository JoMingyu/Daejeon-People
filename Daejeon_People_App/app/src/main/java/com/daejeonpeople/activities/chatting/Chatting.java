package com.daejeonpeople.activities.chatting;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.MapView;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.firebase.Firebase;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 5. 18..
 */

//근철

public class Chatting extends BaseActivity {
    private Button mapViewBtn, sendBtn;
    private EditText inputMessage;
    private RecyclerView chatLog;
    private APIinterface apiInterface;
    private Intent mIntent;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting);

        mapViewBtn=(Button)findViewById(R.id.mapViewBtn);
        sendBtn=(Button)findViewById(R.id.sendBtn);
        inputMessage=(EditText)findViewById(R.id.inputMessage);
        chatLog=(RecyclerView)findViewById(R.id.chatLog);
        mIntent = getIntent();
        dbHelper=DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        apiInterface=APIClient.getClient().create(APIinterface.class);

        Firebase.subscribeTopic(mIntent.getStringExtra("topic"));

        mapViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapView.class));
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.sendMessage("UserSession="+dbHelper.getCookie(), mIntent.getStringExtra("topic"), "text", inputMessage.getText().toString()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }

    public void onBackBtnClicked(View view){
        Firebase.unsubscribeTopic(mIntent.getStringExtra("topic"));
        finish();
    }
}
