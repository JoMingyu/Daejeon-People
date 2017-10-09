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
import com.daejeonpeople.valueobject.ChatLogItem;
import com.daejeonpeople.valueobject.ChattingItem;
import com.google.gson.JsonObject;

import io.realm.Realm;
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
    private Realm mRealm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_chatting);

        chatName=(EditText)findViewById(R.id.chatName);
        nextBtn=(TextView) findViewById(R.id.nextBtn);
        apiInterface= APIClient.getClient().create(APIinterface.class);
        dbHelper=DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
        mRealm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("chatName", chatName.getText().toString());
                apiInterface.makeTravel("UserSession="+dbHelper.getCookie(), chatName.getText().toString()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, final Response<JsonObject> response) {
                        Intent intent = new Intent(getApplicationContext(), MakeChattingInvite.class);
                        intent.putExtra("topic", response.body().get("topic").getAsString());
                        intent.putExtra("chatName", chatName.getText().toString());
                        Firebase.subscribeTopic(response.body().get("topic").getAsString());
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.createObject(ChattingItem.class, response.body().get("topic").getAsString());
                            }
                        });
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    public void onBackBtnClicked(View view){
        startActivity(new Intent(getApplicationContext(), ChatList.class));
        finish();
    }
}
