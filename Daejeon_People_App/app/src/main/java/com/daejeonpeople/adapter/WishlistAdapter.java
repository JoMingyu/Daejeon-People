package com.daejeonpeople.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.valueobject.WishlistItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by JMJ on 2017-08-28.
 */

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder>{

    private ArrayList<WishlistItem> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView title;
        public TextView address;
        public TextView love;


        public ViewHolder(View view) {
            super(view);

            date = (TextView)view.findViewById(R.id.date);
            title = (TextView)view.findViewById(R.id.title);
            address = (TextView)view.findViewById(R.id.address);
            love = (TextView)view.findViewById(R.id.love);
        }
    }

    public WishlistAdapter(ArrayList<WishlistItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_listview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(WishlistAdapter.ViewHolder holder, int position) {
        holder.date.setText(mDataset.get(position).getDate());
        holder.title.setText(mDataset.get(position).getTitle());
        holder.address.setText(mDataset.get(position).getAddress());
        holder.love.setText(mDataset.get(position).getLove());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
