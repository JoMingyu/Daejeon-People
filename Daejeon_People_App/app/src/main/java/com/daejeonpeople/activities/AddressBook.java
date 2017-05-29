package com.daejeonpeople.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.daejeonpeople.R;

/**
 * Created by geni on 2017. 5. 28..
 */

// 근철

public class AddressBook extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_book);

        final AddressBookAdapter adapter = new AddressBookAdapter();
        final ListView listView = (ListView)findViewById(R.id.addressList);
        listView.setAdapter(adapter);
    }
}
