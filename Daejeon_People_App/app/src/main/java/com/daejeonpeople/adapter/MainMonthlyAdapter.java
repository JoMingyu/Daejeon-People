package com.daejeonpeople.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.daejeonpeople.activities.Introduction.Introduction_Accomodation;
import com.daejeonpeople.activities.Introduction.Introduction_Course;
import com.daejeonpeople.activities.Introduction.Introduction_Culture;
import com.daejeonpeople.activities.Introduction.Introduction_Festival;
import com.daejeonpeople.activities.Introduction.Introduction_Leisure;
import com.daejeonpeople.activities.Introduction.Introduction_Restaurant;
import com.daejeonpeople.activities.Introduction.Introduction_Shopping;
import com.daejeonpeople.activities.Introduction.Introduction_Tourism;
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
    public Object instantiateItem(final ViewGroup container, final int position) {
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

        final int content_type_id = mDataset.get(position).getContent_type_id();
        Button btn= (Button) view.findViewById(R.id.view_button);
        btn.setOnClickListener(new Button.OnClickListener() {
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
                }
            }
        }) ;

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
