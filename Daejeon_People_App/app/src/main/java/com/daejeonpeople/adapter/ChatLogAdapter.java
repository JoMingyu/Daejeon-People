package com.daejeonpeople.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.valueobject.ChatLogItem;

import java.util.ArrayList;

/**
 * Created by geni on 2017. 10. 8..
 */

public class ChatLogAdapter extends RecyclerView.Adapter<ChatLogAdapter.ViewHolder> {
    private ArrayList<ChatLogItem> mDataSet;

    public ChatLogAdapter(ArrayList<ChatLogItem> dataSet){
        this.mDataSet = dataSet;
    }

    @Override
    public ChatLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_item, parent, false);
        return new ChatLogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatLogAdapter.ViewHolder holder, int position) {
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
            userName=(TextView)view.findViewById(R.id.userName);
            chatContent=(TextView)view.findViewById(R.id.chatContent);
        }
    }
}
