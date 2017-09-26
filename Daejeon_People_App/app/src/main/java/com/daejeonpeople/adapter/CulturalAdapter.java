package com.daejeonpeople.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.valueobject.CulturalItem;
import com.daejeonpeople.valueobject.WishlistItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2017-09-12.
 */

public class CulturalAdapter extends RecyclerView.Adapter<CulturalAdapter.ViewHolder> {
    private ArrayList<CulturalItem> mDataset;

    public CulturalAdapter(ArrayList<CulturalItem> myDataset) {
        this.mDataset = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView category;
        public TextView call;
        public TextView time;
        public TextView holiday;
        public TextView charge;
        public TextView using_time;
        public TextView address;

        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.placename);
            category = (TextView) view.findViewById(R.id.placekind);
            call = (TextView) view.findViewById(R.id.call_inquiry);
            time = (TextView) view.findViewById(R.id.usetime);
            holiday = (TextView) view.findViewById(R.id.holiday);
            charge = (TextView) view.findViewById(R.id.charge);
            using_time = (TextView) view.findViewById(R.id.requiredtime);
            address = (TextView) view.findViewById(R.id.location);
        }
    }

    public void setData(CulturalItem[] datas) {
        ArrayList<CulturalItem> arrayListDatas = new ArrayList<>();
        for (CulturalItem data : datas) {
            arrayListDatas.add(data);
        }
        mDataset = arrayListDatas;
    }

    public CulturalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.introduction_cultural, parent, false);
        CulturalAdapter.ViewHolder vh = new CulturalAdapter.ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(CulturalAdapter.ViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.category.setText(mDataset.get(position).getCategory());
        holder.call.setText(mDataset.get(position).getCall());
        holder.time.setText(mDataset.get(position).getTime());
        holder.holiday.setText(mDataset.get(position).getHoliday());
        holder.charge.setText(mDataset.get(position).getCharge());
        holder.using_time.setText(mDataset.get(position).getUsing_time());
        holder.address.setText(mDataset.get(position).getAddress());
    }

    public int getItemCount() {
        return mDataset.size();
    }
}
