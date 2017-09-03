package com.daejeonpeople.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daejeonpeople.R;

import org.w3c.dom.Text;

/**
 * Created by JMJ on 2017-08-28.
 */

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder>{

    private String[] mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView title;
        public TextView address;
        public TextView love;
        public Button btn_detail;


        public ViewHolder(View view) {
            super(view);

            date = (TextView)view.findViewById(R.id.date);
            title = (TextView)view.findViewById(R.id.title);
            address = (TextView)view.findViewById(R.id.address);
            love = (TextView)view.findViewById(R.id.love);
            btn_detail = (Button)view.findViewById(R.id.btn_detail);
        }
    }

//    public WishlistAdapter(ArrayList<MyData> myDataset) {
//        mDataset = myDataset;
//    }

    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(WishlistAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
