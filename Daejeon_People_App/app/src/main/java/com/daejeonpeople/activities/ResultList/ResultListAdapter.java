package com.daejeonpeople.activities.ResultList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Introduction_Accomodation;
import com.daejeonpeople.activities.Introduction_Course;
import com.daejeonpeople.activities.Introduction_Culture;
import com.daejeonpeople.activities.Introduction_Festival;
import com.daejeonpeople.activities.Introduction_Leisure;
import com.daejeonpeople.activities.Introduction_Restaurant;
import com.daejeonpeople.activities.Introduction_Shopping;
import com.daejeonpeople.activities.Introduction_Tourism;
import com.daejeonpeople.valueobject.ResultListItem;

import java.util.ArrayList;

/**
 * Created by KimDongGyu on 2017-09-26.
 */

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder> {
    private ArrayList<ResultListItem> arrayListDetail;
    private Context context;
    private String category_key;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView detail_imgview;
        public TextView detail_title;
        public TextView detail_address;
        public TextView detail_love;
        public Button detail_button;

        public ViewHolder(View view) {
            super(view);
            detail_imgview = (ImageView)view.findViewById(R.id.detail_img);
            detail_title = (TextView)view.findViewById(R.id.detail_title);
            detail_address = (TextView)view.findViewById(R.id.detail_address);
            detail_love = (TextView)view.findViewById(R.id.detail_love);
            detail_button = (Button)view.findViewById(R.id.btn_detail);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ResultListAdapter(ArrayList<ResultListItem> myDataset, Context context, String category_key) {
        arrayListDetail = myDataset;
        this.context = context;
        this.category_key = category_key;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_list_recyclerview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.detail_title.setText(arrayListDetail.get(position).getTitle());
        holder.detail_address.setText(arrayListDetail.get(position).getAddress());
        holder.detail_love.setText(arrayListDetail.get(position).getWish_count()+"");
        if(arrayListDetail.get(position).getImage() == "NoImage"){
            Glide.with(holder.itemView.getContext()).load(R.drawable.noimage).into(holder.detail_imgview);
        } else {
            String imgUrl = arrayListDetail.get(position).getImage().substring(1,arrayListDetail.get(position).getImage().length() - 1);
            Glide.with(holder.itemView.getContext()).load(imgUrl).into(holder.detail_imgview);
        }
        if(category_key.equals("Tourist")) {
            holder.detail_button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), Introduction_Tourism.class);
                    intent.putExtra("content_id", arrayListDetail.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                }
            });
        } else if(category_key.equals("Events")) {
            holder.detail_button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), Introduction_Festival.class);
                    intent.putExtra("content_id", arrayListDetail.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                }
            });
        } else if(category_key.equals("Leisure")) {
            holder.detail_button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), Introduction_Leisure.class);
                    intent.putExtra("content_id", arrayListDetail.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                }
            });
        } else if(category_key.equals("Shopping")) {
            holder.detail_button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), Introduction_Shopping.class);
                    intent.putExtra("content_id", arrayListDetail.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                }
            });
        } else if(category_key.equals("Culture")) {
            holder.detail_button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), Introduction_Culture.class);
                    intent.putExtra("content_id", arrayListDetail.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                }
            });
        } else if(category_key.equals("Course")) {
            holder.detail_button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), Introduction_Course.class);
                    intent.putExtra("content_id", arrayListDetail.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                }
            });
        } else if(category_key.equals("Accommodation")) {
            holder.detail_button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), Introduction_Accomodation.class);
                    intent.putExtra("content_id", arrayListDetail.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                }
            });
        } else if(category_key.equals("Restaurant")) {
            holder.detail_button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), Introduction_Restaurant.class);
                    intent.putExtra("content_id", arrayListDetail.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                }
            });
        } else {
            Log.d("알림", "비정상적인 실행");
            Toast.makeText(holder.itemView.getContext(), "비정상적인 실행입니다.", Toast.LENGTH_LONG).show();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayListDetail.size();
    }
}