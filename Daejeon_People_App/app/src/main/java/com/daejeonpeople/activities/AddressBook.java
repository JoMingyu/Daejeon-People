package com.daejeonpeople.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.AddressBookAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by geni on 2017. 5. 28..
 */

// 근철

public class AddressBook extends BaseActivity {
    AQuery aQuery;
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
                try {
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

        final ActionBar addressbook = getActionBar();
        addressbook.setCustomView(R.layout.custom_address_book);
        addressbook.setDisplayShowTitleEnabled(false);
        addressbook.setDisplayShowCustomEnabled(true);
        addressbook.setDisplayShowHomeEnabled(false);

        final AddressBookAdapter adapter = new AddressBookAdapter();
        final ListView listView = (ListView)findViewById(R.id.addressList);
        listView.setAdapter(adapter);
    }
}
