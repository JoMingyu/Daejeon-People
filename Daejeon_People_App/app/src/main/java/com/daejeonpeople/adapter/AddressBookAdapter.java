package com.daejeonpeople.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.valueobject.AddressBookListItem;
import com.daejeonpeople.valueobject.FriendListItem;

import java.util.ArrayList;
/**
 * Created by geni on 2017. 6. 8..
 */

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.ViewHolder>{

    private ArrayList<FriendListItem> mDataSet;
    public AddressBookAdapter(ArrayList<FriendListItem> dataSet) {
        this.mDataSet = dataSet;
    }

    @Override
    public AddressBookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_book_listview, parent, false);
        return new AddressBookAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressBookAdapter.ViewHolder holder, int position) {
        holder.userName.setText(mDataSet.get(position).getName());
        holder.userPhonenum.setText(mDataSet.get(position).getPhoneNum());
        holder.userEmail.setText(mDataSet.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
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
}
