package com.daejeonpeople.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daejeonpeople.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by geni on 2017. 5. 29..
 */

public class AddressBookAdapter extends BaseAdapter {

    private ArrayList<AddressBookListItem> addressBookListItems = new ArrayList<AddressBookListItem>() ;

    public void URLConnection() throws IOException {
            String url = "http://127.0.0.1:3000";
            String charset = "UTF-8";

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            //print result
            System.out.println(response.toString());
    }

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
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.address_book_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView userNameTextView = (TextView) convertView.findViewById(R.id.user_name); //convertView 내에 모든 객체를 저장하고 있다가 재활용한다.
        TextView phoneNumberTextView = (TextView) convertView.findViewById(R.id.phone_number);
        TextView userEmailTextView = (TextView) convertView.findViewById(R.id.user_email);

        AddressBookListItem addressBookListItem = addressBookListItems.get(position);

        userNameTextView.setText(addressBookListItem.getUser_name());
        phoneNumberTextView.setText(addressBookListItem.getPhone_number());
        userEmailTextView.setText(addressBookListItem.getUser_email());

        return convertView;
    }

    public void addItem(int count, String user_name, String phone_number, String user_email) {
        AddressBookListItem item = new AddressBookListItem();

        item.setCount(count);
        item.setPhone_number(user_name);
        item.setPhone_number(phone_number);
        item.setUser_email(user_email);

        addressBookListItems.add(item);
    }
}
