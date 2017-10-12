package com.daejeonpeople.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
import com.daejeonpeople.valueobject.WishlistItem;

import java.util.ArrayList;

/**
 * Created by JMJ on 2017-08-28.
 */

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder>{

    private ArrayList<WishlistItem> mDataset;
    public WishlistAdapter(ArrayList<WishlistItem> myDataset) {
        this.mDataset = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView address;
        public TextView love;
        public ImageView back_image;

        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.wishlist_title);
            address = (TextView)view.findViewById(R.id.wishlist_address);
            back_image = (ImageView)view.findViewById(R.id.wishlist_img);
        }
    }

    public void setData(WishlistItem[] datas){
        ArrayList<WishlistItem> arrayListDatas = new ArrayList<>();
        for(WishlistItem data : datas){
            arrayListDatas.add(data);
        }
        mDataset = arrayListDatas;
    }

    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_recyclerview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(WishlistAdapter.ViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.address.setText(mDataset.get(position).getAddress());
//        holder.love.setText(mDataset.get(position).getLove()+"");
//        holder.back_image.setImageResource(R.drawable.bg_accommodation);
            String imgUrl = mDataset.get(position).getBack_image().substring(1,mDataset.get(position).getBack_image().length() - 1);
            Glide.with(holder.itemView.getContext()).load(imgUrl).into(holder.back_image);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
