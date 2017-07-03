package com.daejeonpeople.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.adapter.AddressBookAdapter;
import com.daejeonpeople.connection.connectionValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by geni on 2017. 5. 28..
 */

// 근철

public class AddressBook extends Activity {
    AQuery aQuery;
    HashMap<String, Object> params = new HashMap<>();
    ListView listView;
    final AddressBookAdapter adapter = new AddressBookAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_book);
        listView = (ListView)findViewById(R.id.addressList);
        listView.setAdapter(adapter);
        listView.setVerticalScrollBarEnabled(true);
        aQuery = new AQuery(getApplicationContext());

        aQuery.ajax("http://52.79.134.200:80/friend", JSONArray.class, new AjaxCallback<JSONArray>(){
            @Override
            public void callback(String url, JSONArray response, AjaxStatus status) {
                System.out.println(status.getCode());
                System.out.println(url);
                try {
                    System.out.println("connect success");
                    JSONArray jsonArray = new JSONArray();
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject friendInfo = jsonArray.getJSONObject(i);
                        System.out.println(friendInfo.getString("name") + friendInfo.getString("phone_number") + friendInfo.getString("email"));
                        adapter.addItem(friendInfo.getString("name"), friendInfo.getString("phone_number"), friendInfo.getString("email"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
