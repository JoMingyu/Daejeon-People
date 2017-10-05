package com.daejeonpeople.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.valueobject.ChatListItem;

import java.util.ArrayList;

/**
 * Created by geni on 2017. 10. 4..
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private ArrayList<ChatListItem> mChatListItems;

    public ChatListAdapter(ArrayList<ChatListItem> chatListItems){
        this.mChatListItems = chatListItems;
    }

    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_item, parent, false);
        return new ChatListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatListAdapter.ViewHolder holder, int position) {
        holder.chatTitle.setText(mChatListItems.get(position).getTitle());
        holder.lastIndex.setText(mChatListItems.get(position).getLastIndex());
    }

    @Override
    public int getItemCount() {
        return mChatListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView chatTitle;
        private TextView lastIndex;

        public ViewHolder(View view){
            super(view);
            chatTitle=(TextView)view.findViewById(R.id.chatTitle);
            lastIndex=(TextView)view.findViewById(R.id.lastIndex);
        }
    }
}
