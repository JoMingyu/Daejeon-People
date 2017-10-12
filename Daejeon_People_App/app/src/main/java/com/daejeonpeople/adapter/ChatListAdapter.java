package com.daejeonpeople.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daejeonpeople.R;
//import com.daejeonpeople.activities.chatting.Chatting;
import com.daejeonpeople.activities.mChatting.mChatting;
import com.daejeonpeople.valueobject.ChatListItem;

import java.util.ArrayList;

/**
 * Created by geni on 2017. 10. 4..
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private ArrayList<ChatListItem> mChatListItems;
    private Activity mContext;

    public ChatListAdapter(Activity context, ArrayList<ChatListItem> chatListItems){
        this.mChatListItems = chatListItems;
        this.mContext = context;
    }

    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
        return new ChatListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatListAdapter.ViewHolder holder, final int position) {
        holder.chatTitle.setText(mChatListItems.get(position).getTitle());
//        holder.lastIndex.setText(mChatListItems.get(position).getLastIndex()+"");
//        holder.lastMessage.setText(mChatListItems.get(position).getLastMessage());
        holder.chatListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = mChatListItems.get(position).getTopic();
                String title = mChatListItems.get(position).getTitle();
                Intent intent = new Intent(mContext, mChatting.class);
                intent.putExtra("topic", topic);
                intent.putExtra("chatName", title);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChatListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView chatTitle, lastIndex, lastMessage;
        private RelativeLayout chatListItem;

        public ViewHolder(View view){
            super(view);
            chatTitle=(TextView)view.findViewById(R.id.chatTitle);
//            lastIndex=(TextView)view.findViewById(R.id.lastIndex);
//            lastMessage=(TextView)view.findViewById(R.id.lastMessage);
            chatListItem=(RelativeLayout)view.findViewById(R.id.chatListItem);
        }
    }
}
