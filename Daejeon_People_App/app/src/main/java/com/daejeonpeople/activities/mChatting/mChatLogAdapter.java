package com.daejeonpeople.activities.mChatting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.valueobject.ChattingItem;
import com.daejeonpeople.valueobject.mChatLogItem;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by geni on 2017. 10. 10..
 */

public class mChatLogAdapter extends RecyclerView.Adapter<mChatLogAdapter.ViewHolder> {
    private ArrayList<mChatLogItem> mDataSet;

    public mChatLogAdapter(ArrayList<mChatLogItem> dataSet){
        this.mDataSet = dataSet;
    }

    @Override
    public mChatLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_item, parent, false);
        return new mChatLogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mChatLogAdapter.ViewHolder holder, int position) {
        holder.userName.setText(mDataSet.get(position).getUserName());
        holder.chatContent.setText(mDataSet.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView chatContent;

        public ViewHolder(View view){
            super(view);
            userName = (TextView)view.findViewById(R.id.userName);
            chatContent = (TextView)view.findViewById(R.id.chatContent);
        }
    }
}
