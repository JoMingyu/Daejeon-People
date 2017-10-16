package com.daejeonpeople.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.MapView;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.ShareChatListItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 10. 16..
 */

public class ShareChatListAdapter extends RecyclerView.Adapter<ShareChatListAdapter.ViewHolder> {
    private ArrayList<ShareChatListItem> mDataSet;
    private APIinterface apiInterface;
    private Context mContext;
    private int mContentId;
    private DBHelper dbHelper;

    public ShareChatListAdapter(Context context, ArrayList<ShareChatListItem> dataSet, int contentId){
        this.mDataSet = dataSet;
        this.mContext = context;
        this.mContentId = contentId;
        apiInterface = APIClient.getClient().create(APIinterface.class);
        dbHelper=DBHelper.getInstance(mContext, "CHECK.db", null, 1);
    }

    @Override
    public ShareChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_chat_list_item, parent, false);
        return new ShareChatListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShareChatListAdapter.ViewHolder holder, final int position) {
        holder.chatName.setText(mDataSet.get(position).getChatName());
        holder.chatListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.addMapData("UserSession="+dbHelper.getCookie(), mDataSet.get(position).getTopic(), mContentId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("response", response+"");
                        Intent intent = new Intent(mContext, MapView.class);
                        intent.putExtra("topic", mDataSet.get(position).getTopic());
                        mContext.startActivity(intent);
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
        private TextView chatName;
        private RelativeLayout chatListItem;
        public ViewHolder(View view){
            super(view);
            chatName = (TextView)view.findViewById(R.id.chatTitle);
            chatListItem = (RelativeLayout)view.findViewById(R.id.chatListItem);
        }
    }
}
