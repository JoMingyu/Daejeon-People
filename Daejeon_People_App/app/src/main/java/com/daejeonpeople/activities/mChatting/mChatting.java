package com.daejeonpeople.activities.mChatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.ChatLogAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.security.AES;
import com.daejeonpeople.valueobject.ChatLogItem;
import com.daejeonpeople.valueobject.ChattingItem;
import com.daejeonpeople.valueobject.mChatLogItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 10. 10..
 */

public class mChatting extends BaseActivity {
    private RecyclerView chatLog;
    private EditText inputMessage;
    private Button sendBtn;
    private TextView chatName;
    private APIinterface apiInterface;
    private DBHelper dbHelper;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private Realm mRealm;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting);

        chatLog = (RecyclerView)findViewById(R.id.chatLog);
        inputMessage = (EditText)findViewById(R.id.inputMessage);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        chatName = (TextView)findViewById(R.id.chatName);

        apiInterface = APIClient.getClient().create(APIinterface.class);
        dbHelper=DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
        mRealm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();
        mIntent = getIntent();

        Log.d("intent", mIntent+"");
        Log.d("intent", mIntent.getStringExtra("topic")+"");

        chatName.setText(mIntent.getStringExtra("chatName"));

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d("realm", realm.where(ChattingItem.class).contains("topic", mIntent.getStringExtra("topic")).findFirst()+"");
                if(realm.where(ChattingItem.class).contains("topic", mIntent.getStringExtra("topic")).findFirst() != null){
                    RealmList realmList = realm.where(ChattingItem.class).contains("topic", mIntent.getStringExtra("topic")).findFirst().getChatLogs();
                    ArrayList<mChatLogItem> mChatLogItems = new ArrayList<mChatLogItem>();
                    for(int i=0; i<realmList.size(); i++){
                        mChatLogItem result = (mChatLogItem)realmList.get(i);
                        mChatLogItems.add(i, result);
                    }
                    chatLog.setAdapter(new mChatLogAdapter(mChatLogItems));
                    chatLog.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } else {
//                    Log.d("topic", mIntent.getStringArrayExtra("topic"));
                    realm.createObject(ChattingItem.class, mIntent.getStringExtra("topic"));
                }
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.getMyPage("UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, final Response<JsonObject> response) {
                        mChatData chatData = new mChatData(AES.decrypt(response.body().get("name").getAsString()), inputMessage.getText().toString());
                        databaseReference.child(mIntent.getStringExtra("topic")).push().setValue(chatData);
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmList realmList = realm.where(ChattingItem.class).contains("topic", mIntent.getStringExtra("topic")).findFirst().getChatLogs();
                                mChatLogItem mChatLogItem = new mChatLogItem();
                                mChatLogItem.setUserName(AES.decrypt(response.body().get("name").getAsString()));
                                mChatLogItem.setContent(inputMessage.getText().toString());
                                realmList.add(realmList.size(), mChatLogItem);

                                ArrayList<mChatLogItem> mChatLogItems = new ArrayList<mChatLogItem>();

                                for(int i=0; i<realmList.size(); i++){
                                    mChatLogItem result = (mChatLogItem)realmList.get(i);
                                    mChatLogItems.add(i, result);
                                }
                                chatLog.setAdapter(new mChatLogAdapter(mChatLogItems));
                                chatLog.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                        });
                        inputMessage.setText("");
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        databaseReference.child(mIntent.getStringExtra("topic")).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mChatData chatData = dataSnapshot.getValue(mChatData.class);  // chatData를 가져오고
                Log.d("chatData", chatData.getUserName() + " " + chatData.getMessage());
                apiInterface.getMyPage("UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(!chatData.getUserName().equals(AES.decrypt(response.body().get("name").getAsString()))){
                            mRealm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    RealmList realmList = realm.where(ChattingItem.class).contains("topic", mIntent.getStringExtra("topic")).findFirst().getChatLogs();
                                    mChatLogItem mChatLogItem = new mChatLogItem();
                                    Log.d("realmListSize", realmList.size()+"");
                                    mChatLogItem.setUserName(chatData.getUserName());
                                    mChatLogItem.setContent(chatData.getMessage());
                                    realmList.add(realmList.size(), mChatLogItem);

                                    ArrayList<mChatLogItem> mChatLogItems = new ArrayList<mChatLogItem>();

                                    for(int i=0; i<realmList.size(); i++){
                                        mChatLogItem result = (mChatLogItem)realmList.get(i);
                                        mChatLogItems.add(i, result);
                                    }
                                    chatLog.setAdapter(new mChatLogAdapter(mChatLogItems));
                                    chatLog.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void onBackBtnClicked(View view){
        finish();
    }
}
