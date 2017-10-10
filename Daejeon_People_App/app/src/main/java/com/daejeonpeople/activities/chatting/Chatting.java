//package com.daejeonpeople.activities.chatting;
//
//import android.app.ActionBar;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.daejeonpeople.R;
//import com.daejeonpeople.activities.MapView;
//import com.daejeonpeople.activities.base.BaseActivity;
//import com.daejeonpeople.adapter.ChatLogAdapter;
//import com.daejeonpeople.support.database.DBHelper;
//import com.daejeonpeople.support.firebase.Firebase;
//import com.daejeonpeople.support.network.APIClient;
//import com.daejeonpeople.support.network.APIinterface;
//import com.daejeonpeople.valueobject.ChatListItem;
//import com.daejeonpeople.valueobject.ChatLogItem;
//import com.daejeonpeople.valueobject.ChattingItem;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//
//import java.util.ArrayList;
//
//import io.realm.Realm;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * Created by geni on 2017. 5. 18..
// */
//
////근철
//
//public class Chatting extends BaseActivity {
//    private Button mapViewBtn, sendBtn;
//    private EditText inputMessage;
//    private TextView chatName;
//    private RecyclerView chatLog;
//    private APIinterface apiInterface;
//    private Intent mIntent;
//    private DBHelper dbHelper;
//    private ArrayList<ChatLogItem> chatLogItems = new ArrayList<>();
//    private Realm mRealm;
//    private BroadcastReceiver mBroadcastReceiver;
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("firebase broadcast");
//        registerReceiver(mBroadcastReceiver, filter);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.chatting);
//
//        mapViewBtn = (Button)findViewById(R.id.mapViewBtn);
//        sendBtn = (Button)findViewById(R.id.sendBtn);
//        inputMessage = (EditText)findViewById(R.id.inputMessage);
//        chatLog = (RecyclerView)findViewById(R.id.chatLog);
//        chatName = (TextView)findViewById(R.id.chatName);
//        mIntent = getIntent();
//        final IntentFilter intentfilter = new IntentFilter();
//        intentfilter.addAction("firebase broadcast");
//
//        apiInterface = APIClient.getClient().create(APIinterface.class);
//        dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
//        mRealm.init(getApplicationContext());
//        mRealm = Realm.getDefaultInstance();
//        chatName.setText(mIntent.getStringExtra("chatName"));
//
//        mapViewBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), MapView.class));
//            }
//        });
//
//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                Log.d("topic", mIntent.getStringExtra("topic"));
//                ChattingItem chattingItem = realm.where(ChattingItem.class).contains("topic", mIntent.getStringExtra("topic")).findFirst();
//                ArrayList<ChatLogItem> chatLogItems = new ArrayList<>();
//                for(int i=0; i<chattingItem.getChatLogs().size(); i++){
//                    ChatLogItem mChatLogItem = chattingItem.getChatLogs().get(i);
//                    chatLogItems.add(i, mChatLogItem);
//                }
//                chatLog.setAdapter(new ChatLogAdapter(chatLogItems));
//                chatLog.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//            }
//        });
//
//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("topic", mIntent.getStringExtra("topic"));
//                apiInterface.sendMessage("UserSession="+dbHelper.getCookie(), mIntent.getStringExtra("topic"), "text", inputMessage.getText().toString()).enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        apiInterface.getMyPage("UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonObject>() {
//                            @Override
//                            public void onResponse(Call<JsonObject> call, final Response<JsonObject> response) {
//                                mRealm.executeTransaction(new Realm.Transaction() {
//                                    @Override
//                                    public void execute(Realm realm) {
//                                        ChattingItem chattingItem = realm.where(ChattingItem.class).contains("topic", mIntent.getStringExtra("topic")).findFirst();
//                                        ChatLogItem chatLogItem = new ChatLogItem();
//                                        int lastIdx = chattingItem.getChatLogs().size();
//                                        Log.d("chattingItem size", chattingItem.getChatLogs().size()+"");
//                                        chatLogItem.setType("text");
//                                        chatLogItem.setIndex(lastIdx);
//                                        chatLogItem.setUserName(response.body().get("name").getAsString());
//                                        chatLogItem.setContent(inputMessage.getText().toString());
//                                        chatLogItem.setRemainingViews(0);
//                                        chattingItem.getChatLogs().add(lastIdx, chatLogItem);
//                                    }
//                                });
//                                inqueryChatLog();
//                                inputMessage.setText(null);
//                            }
//
//                            @Override
//                            public void onFailure(Call<JsonObject> call, Throwable t) {
//                                t.printStackTrace();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
//            }
//        });
//
//        mBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String sendString = intent.getStringExtra("message");
//                Log.d("hello", sendString+"");
//                mRealm.executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        final ChattingItem chattingItem = realm.where(ChattingItem.class).contains("topic", mIntent.getStringExtra("topic")).findFirst();
//                        int lastIdx = 0;
//                        if(chattingItem.getChatLogs().size() >= 1){
//                            lastIdx = chattingItem.getChatLogs().get(chattingItem.getChatLogs().size() - 1).getIndex();
//                        }
//                        apiInterface.inqueryMessages("UserSession="+dbHelper.getCookie(), mIntent.getStringExtra("topic"), lastIdx + 1).enqueue(new retrofit2.Callback<JsonArray>() {
//                            @Override
//                            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                                if(response.body() != null){
//                                    ArrayList<ChatLogItem> chatLogItems = new ArrayList<>();
////                                    for(int i=0; i<response.body().size(); i++){
////                                        if(chattingItem.getChatLogs() != null){
////                                            ChatLogItem chatLogItem = chattingItem.getChatLogs().get(i);
////                                            chatLogItems.add(i, chatLogItem);
////                                        }
////                                    }
//                                    chatLog.setAdapter(new ChatLogAdapter(chatLogItems));
//                                    chatLog.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<JsonArray> call, Throwable t) {
//                                t.printStackTrace();
//                            }
//                        });
//                    }
//                });
//
//                registerReceiver(mBroadcastReceiver, new IntentFilter("firebase broadcast"));
//            }
//        };
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(mBroadcastReceiver);
//    }
//
//    public void inqueryChatLog(){
//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                ChattingItem chattingItem = realm.where(ChattingItem.class).contains("topic", mIntent.getStringExtra("topic")).findFirst();
//                ArrayList<ChatLogItem> chatLogItems = new ArrayList<>();
//                for(int i=0; i<chattingItem.getChatLogs().size(); i++){
//                    chatLogItems.add(i, chattingItem.getChatLogs().get(i));
//                }
//                chatLog.setAdapter(new ChatLogAdapter(chatLogItems));
//                chatLog.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//            }
//        });
//    }
//
//    public void onBackBtnClicked(View view){
//        Firebase.unsubscribeTopic(mIntent.getStringExtra("topic"));
//        finish();
//    }
//}
