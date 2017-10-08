package com.daejeonpeople.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.FriendListItem;
import com.daejeonpeople.valueobject.InviteListItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 10. 6..
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private ArrayList<InviteListItem> mDataSet = new ArrayList<>();
    private APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

    public FriendListAdapter(ArrayList<InviteListItem> friendListItems){
        this.mDataSet = friendListItems;
    }

    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.make_chatting_invite_item, parent, false);
        return new FriendListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendListAdapter.ViewHolder holder, final int position) {
        holder.userName.setText(mDataSet.get(position).getName());
        holder.userPhoneNum.setText(mDataSet.get(position).getPhoneNum());
        holder.userEmail.setText(mDataSet.get(position).getEmail());
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.inviteFriend(mDataSet.get(position).getId().toString(), mDataSet.get(position).getTopic(), "").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView userEmail;
        private TextView userPhoneNum;
        private TextView addBtn;

        public ViewHolder(View view){
            super(view);
            userName=(TextView)view.findViewById(R.id.userName);
            userEmail=(TextView)view.findViewById(R.id.userEmail);
            userPhoneNum=(TextView)view.findViewById(R.id.userPhoneNum);
            addBtn=(TextView)view.findViewById(R.id.addBtn);
        }
    }
}
