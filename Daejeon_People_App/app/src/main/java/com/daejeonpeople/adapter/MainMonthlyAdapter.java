package com.daejeonpeople.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
import com.daejeonpeople.valueobject.MainItemMonthly;

import java.util.ArrayList;

/**
 * Created by 10102김동규 on 2017-07-11.
 */

public class MainMonthlyAdapter extends PagerAdapter {
    LayoutInflater inflater;
    private ArrayList<MainItemMonthly> mDataset;
    private Context mContext;

    public MainMonthlyAdapter(LayoutInflater inflater, ArrayList<MainItemMonthly> myDataset, Context context) {
        // TODO Auto-generated constructor stub
        this.mDataset = myDataset;
        this.inflater=inflater;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        View view = null;
        view = inflater.inflate(R.layout.main_viewpager_monthly, null);

        TextView viewTitle = (TextView)view.findViewById(R.id.view_title);
        viewTitle.setText(mDataset.get(position).getTitle());

        TextView viewSubTitle = (TextView)view.findViewById(R.id.viewpager_subtitle);
        viewSubTitle.setText(mDataset.get(position).getEng_title());

        TextView viewPlace = (TextView)view.findViewById(R.id.view_place);
        viewPlace.setText(mDataset.get(position).getAddress());

        TextView viewCount = (TextView)view.findViewById(R.id.view_count);
        viewCount.setText(mDataset.get(position).getWish_count()+"");

        ImageView img = (ImageView) view.findViewById(R.id.img_viewpager_childimage2);

        if(mDataset.get(position).getImage() == "NoImage"){
            Glide.with(mContext).load(R.drawable.noimage).into(img);
        } else {
            String imgUrl = mDataset.get(position).getImage().substring(1, mDataset.get(position).getImage().length() - 1);
            Glide.with(mContext).load(imgUrl).into(img);
            Log.d("url", mDataset.get(position).getImage());
        }

        Button btn= (Button) view.findViewById(R.id.view_button);
        btn.getResources();

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        // TODO Auto-generated method stub
        return v==obj;
    }

}