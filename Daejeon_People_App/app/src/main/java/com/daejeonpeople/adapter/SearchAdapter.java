package com.daejeonpeople.adapter;

import android.content.Context;
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
import com.daejeonpeople.valueobject.SearchItem;

import java.util.ArrayList;

/**
 * Created by KimDongGyu on 2017-10-05.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<SearchItem> arrayListSearch;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView search_img;
        public TextView search_title;
        public TextView search_address;
        public TextView search_love;
        public Button btn;

        public ViewHolder(View view) {
            super(view);
            btn= (Button) view.findViewById(R.id.search_result_btn);
            search_img = (ImageView)view.findViewById(R.id.search_result_img);
            search_title = (TextView)view.findViewById(R.id.search_result_title);
            search_address = (TextView)view.findViewById(R.id.search_result_address);
            search_love = (TextView)view.findViewById(R.id.search_result_like);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchAdapter(ArrayList<SearchItem> myDataset, Context context) {
        arrayListSearch = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_recyclerview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        SearchAdapter.ViewHolder vh = new SearchAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.search_title.setText(arrayListSearch.get(position).getTitle());
        holder.search_address.setText(arrayListSearch.get(position).getAddress());
        holder.search_love.setText(arrayListSearch.get(position).getWish_count()+"");
        if(arrayListSearch.get(position).getImage() == "NoImage"){
            Glide.with(holder.itemView.getContext()).load(R.drawable.noimage).into(holder.search_img);
        } else {
            String imgUrl = arrayListSearch.get(position).getImage().substring(1,arrayListSearch.get(position).getImage().length() - 1);
            Glide.with(holder.itemView.getContext()).load(imgUrl).into(holder.search_img);
        }

        final int content_type_id = arrayListSearch.get(position).getContent_type_id();
        holder.btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (content_type_id == 12) {
                    Intent intent = new Intent(view.getContext(), Introduction_Tourism.class);
                    intent.putExtra("content_id", arrayListSearch.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 14) {
                    Intent intent = new Intent(view.getContext(), Introduction_Culture.class);
                    intent.putExtra("content_id", arrayListSearch.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 15) {
                    Intent intent = new Intent(view.getContext(), Introduction_Festival.class);
                    intent.putExtra("content_id", arrayListSearch.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 25) {
                    Intent intent = new Intent(view.getContext(), Introduction_Course.class);
                    intent.putExtra("content_id", arrayListSearch.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 28) {
                    Intent intent = new Intent(view.getContext(), Introduction_Leisure.class);
                    intent.putExtra("content_id", arrayListSearch.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 32) {
                    Intent intent = new Intent(view.getContext(), Introduction_Accomodation.class);
                    intent.putExtra("content_id", arrayListSearch.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 38) {
                    Intent intent = new Intent(view.getContext(), Introduction_Shopping.class);
                    intent.putExtra("content_id", arrayListSearch.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else if (content_type_id == 39) {
                    Intent intent = new Intent(view.getContext(), Introduction_Restaurant.class);
                    intent.putExtra("content_id", arrayListSearch.get(position).getContent_id());
                    view.getContext().startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(),"잘못된 접근입니다.", Toast.LENGTH_LONG).show();
                }
            }
        }) ;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayListSearch.size();
    }

    public void updateData(ArrayList<SearchItem> searchItems) {
        arrayListSearch.clear();
        arrayListSearch.addAll(searchItems);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        arrayListSearch.remove(position);
        notifyItemRemoved(position);
    }

    public void addData(ArrayList<SearchItem> datas) {
        for(SearchItem data : datas){
            arrayListSearch.add(data);
        }
        notifyDataSetChanged();
    }

}
