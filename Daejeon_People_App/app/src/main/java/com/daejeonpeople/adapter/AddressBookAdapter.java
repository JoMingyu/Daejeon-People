package com.daejeonpeople.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.ValueObjects.AddressBookListItem;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by geni on 2017. 6. 8..
 */

public class AddressBookAdapter extends BaseAdapter {
    HashMap <String, Object> params = new HashMap<>();

    private ArrayList<AddressBookListItem> addressBookListItems = new ArrayList<>() ;

    @Override
    public int getCount() {
        return addressBookListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return addressBookListItems.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressBookListItem addressBookListItem = new AddressBookListItem();
        final Context context = parent.getContext();


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.address_book_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView userNameTextView = (TextView) convertView.findViewById(R.id.user_name); //convertView 내에 모든 객체를 저장하고 있다가 재활용한다.
        TextView phoneNumberTextView = (TextView) convertView.findViewById(R.id.phone_number);
        TextView userEmailTextView = (TextView) convertView.findViewById(R.id.user_email);

        addressBookListItem = addressBookListItems.get(position);

        userNameTextView.setText(addressBookListItem.getUser_name());
        phoneNumberTextView.setText(addressBookListItem.getPhone_number());
        userEmailTextView.setText(addressBookListItem.getUser_email());

        return convertView;
    }

    public void addItem(String user_name, String phone_number, String user_email) {
        AddressBookListItem item = new AddressBookListItem();

        item.setPhone_number(user_name);
        item.setPhone_number(phone_number);
        item.setUser_email(user_email);

        addressBookListItems.add(item);
    }
}
