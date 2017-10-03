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
