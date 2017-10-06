package com.daejeonpeople.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
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

        public ViewHolder(View view) {
            super(view);
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
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
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
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayListSearch.size();
    }

}
