package com.daejeonpeople.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.valueobject.AddressBookListItem;

import java.util.ArrayList;
/**
 * Created by geni on 2017. 6. 8..
 */

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.ViewHolder>{

    private ArrayList<AddressBookListItem> aDataset;
    public AddressBookAdapter(ArrayList<AddressBookListItem> myDataset) {
        this.aDataset = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView userPhonenum;
        public TextView userEmail;

        public ViewHolder(View view) {
            super(view);
            userName = (TextView)view.findViewById(R.id.userName);
            userPhonenum = (TextView)view.findViewById(R.id.userPhoneNum);
            userEmail = (TextView)view.findViewById(R.id.userEmail);
        }
    }

    public void setData(AddressBookListItem[] datas){
        ArrayList<AddressBookListItem> arrayListDatas = new ArrayList<>();
        for(AddressBookListItem data : datas){
            arrayListDatas.add(data);
        }
        aDataset = arrayListDatas;
    }

    @Override
    public AddressBookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_book_listview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AddressBookAdapter.ViewHolder holder, int position) {
        holder.userName.setText(aDataset.get(position).getUser_name());
        holder.userPhonenum.setText(aDataset.get(position).getPhone_number());
        holder.userEmail.setText(aDataset.get(position).getUser_email());
    }

    @Override
    public int getItemCount() {
        return aDataset.size();
    }
}


//public class AddressBookAdapter extends BaseAdapter {
////    HashMap <String, Object> params = new HashMap<>();
////    AqueryConnection connection;
//
//    private ArrayList<AddressBookListItem> addressBookListItems = new ArrayList<>() ;
//
//    @Override
//    public int getCount() {
//        return addressBookListItems.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return addressBookListItems.get(position) ;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        AddressBookListItem addressBookListItem = new AddressBookListItem();
//        final Context context = parent.getContext();
//
//
//        if(convertView == null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.address_book_listview, parent, false);
//        }
//
//        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
//        TextView userNameTextView = (TextView) convertView.findViewById(R.id.user_name); //convertView 내에 모든 객체를 저장하고 있다가 재활용한다.
//        TextView phoneNumberTextView = (TextView) convertView.findViewById(R.id.phone_number);
//        TextView userEmailTextView = (TextView) convertView.findViewById(R.id.user_email);
//
//        addressBookListItem = addressBookListItems.get(position);
//
//        userNameTextView.setText(addressBookListItem.getUser_name());
//        phoneNumberTextView.setText(addressBookListItem.getPhone_number());
//        userEmailTextView.setText(addressBookListItem.getUser_email());
//
//        return convertView;
//    }
//
//    public void addItem(String user_name, String phone_number, String user_email) {
//        AddressBookListItem item = new AddressBookListItem();
//
//        item.setPhone_number(user_name);
//        item.setPhone_number(phone_number);
//        item.setUser_email(user_email);
//
//        addressBookListItems.add(item);
//    }
//}
