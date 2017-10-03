package com.daejeonpeople.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daejeonpeople.R;

import org.w3c.dom.Text;

/**
 * Created by geni on 2017. 10. 4..
 */

public class FriendRequestListAdapter extends RecyclerView.Adapter<FriendRequestListAdapter.ViewHolder> {
    @Override
    public FriendRequestListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_item, parent, false);
        return new FriendRequestListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendRequestListAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView userPhoneNum;
        private TextView userEmail;
        private TextView requestDate;
        private Button acceptRequestBtn;
        private Button refuseRequestBtn;

        public ViewHolder(View view){
            super(view);
            userName=(TextView)view.findViewById(R.id.userName);
            userPhoneNum=(TextView)view.findViewById(R.id.userPhoneNum);
            userEmail=(TextView)view.findViewById(R.id.userEmail);
            requestDate=(TextView)view.findViewById(R.id.requestDate);
            acceptRequestBtn=(Button)view.findViewById(R.id.acceptRequestBtn);
            refuseRequestBtn=(Button)view.findViewById(R.id.refuseRequestBtn);
        }
    }
}
