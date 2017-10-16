package com.daejeonpeople.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Introduction.Introduction_Accomodation;
import com.daejeonpeople.activities.Introduction.Introduction_Course;
import com.daejeonpeople.activities.Introduction.Introduction_Culture;
import com.daejeonpeople.activities.Introduction.Introduction_Festival;
import com.daejeonpeople.activities.Introduction.Introduction_Leisure;
import com.daejeonpeople.activities.Introduction.Introduction_Restaurant;
import com.daejeonpeople.activities.Introduction.Introduction_Shopping;
import com.daejeonpeople.activities.Introduction.Introduction_Tourism;
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
        public Button wish_btn;

        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.wishlist_title);
            address = (TextView)view.findViewById(R.id.wishlist_address);
            back_image = (ImageView)view.findViewById(R.id.wishlist_img);
            wish_btn = (Button)view.findViewById(R.id.wishlist_btn);
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
    public void onBindViewHolder(WishlistAdapter.ViewHolder holder, final int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.address.setText(mDataset.get(position).getAddress());
//        holder.love.setText(mDataset.get(position).getLove()+"");
//        holder.back_image.setImageResource(R.drawable.bg_accommodation);
        if(mDataset.get(position).getBack_image() == "NoImage"){
            Glide.with(holder.itemView.getContext()).load(R.drawable.noimage).into(holder.back_image);
        } else {
            String imgUrl = mDataset.get(position).getBack_image().substring(1,mDataset.get(position).getBack_image().length() - 1);
            Glide.with(holder.itemView.getContext()).load(imgUrl).into(holder.back_image);
        }
        final int content_type_id = mDataset.get(position).getContent_type_id();
        holder.wish_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (content_type_id == 12) {
                    Intent intent = new Intent(view.getContext(), Introduction_Tourism.class);
                    intent.putExtra("content_id", mDataset.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 14) {
                    Intent intent = new Intent(view.getContext(), Introduction_Culture.class);
                    intent.putExtra("content_id", mDataset.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 15) {
                    Intent intent = new Intent(view.getContext(), Introduction_Festival.class);
                    intent.putExtra("content_id", mDataset.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 25) {
                    Intent intent = new Intent(view.getContext(), Introduction_Course.class);
                    intent.putExtra("content_id", mDataset.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 28) {
                    Intent intent = new Intent(view.getContext(), Introduction_Leisure.class);
                    intent.putExtra("content_id", mDataset.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 32) {
                    Intent intent = new Intent(view.getContext(), Introduction_Accomodation.class);
                    intent.putExtra("content_id", mDataset.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 38) {
                    Intent intent = new Intent(view.getContext(), Introduction_Shopping.class);
                    intent.putExtra("content_id", mDataset.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 39) {
                    Intent intent = new Intent(view.getContext(), Introduction_Restaurant.class);
                    intent.putExtra("content_id", mDataset.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(),"잘못된 접근입니다.", Toast.LENGTH_LONG).show();
                }
            }
        }) ;

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
