package com.daejeonpeople.activities.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.firebase.Firebase;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 10. 6..
 */

public class MakeChatting extends BaseActivity {
    private EditText chatName;
    private TextView nextBtn;
    private APIinterface apiInterface;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_chatting);

        chatName=(EditText)findViewById(R.id.chatName);
        nextBtn=(TextView) findViewById(R.id.nextBtn);
        apiInterface= APIClient.getClient().create(APIinterface.class);
        dbHelper=DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("chatName", chatName.getText().toString());
                apiInterface.makeTravel("UserSession="+dbHelper.getCookie(), chatName.getText().toString()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Intent intent = new Intent(getApplicationContext(), MakeChattingInvite.class);
                        Firebase.subscribeTopic(response.body().get("topic").getAsString());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
