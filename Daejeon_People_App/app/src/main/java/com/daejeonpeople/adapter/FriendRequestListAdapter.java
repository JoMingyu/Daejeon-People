package com.daejeonpeople.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.FriendRequestItems;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 10. 4..
 */

public class FriendRequestListAdapter extends RecyclerView.Adapter<FriendRequestListAdapter.ViewHolder> {
    private ArrayList<FriendRequestItems> mDataSet;
    private APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

    public FriendRequestListAdapter(ArrayList<FriendRequestItems> dataSet){
        this.mDataSet = dataSet;
    }

    @Override
    public FriendRequestListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_item, parent, false);
        return new FriendRequestListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendRequestListAdapter.ViewHolder holder, final int position) {
        holder.userName.setText(mDataSet.get(position).getUserName());
        holder.userPhoneNum.setText(mDataSet.get(position).getUserPhoneNum());
        holder.userEmail.setText(mDataSet.get(position).getUserEmail());
        holder.requestDate.setText(mDataSet.get(position).getRequestDate());
        holder.acceptRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.acceptFriendRequest(mDataSet.get(position).getUserId()).enqueue(new Callback<Void>() {
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
        holder.refuseRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.refuseFriendRequest(mDataSet.get(position).getUserId()).enqueue(new Callback<Void>() {
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
