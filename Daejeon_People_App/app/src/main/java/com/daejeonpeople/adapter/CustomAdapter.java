package com.daejeonpeople.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.daejeonpeople.R;

/**
 * Created by 10102김동규 on 2017-07-11.
 */
public class CustomAdapter extends PagerAdapter {
    LayoutInflater inflater;
    public CustomAdapter(LayoutInflater inflater) {
        // TODO Auto-generated constructor stub
        this.inflater=inflater;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 4;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        View view = null;
        view= inflater.inflate(R.layout.viewpager_childview, null);
        ImageView img= (ImageView)view.findViewById(R.id.img_viewpager_childimage);
        img.setImageResource(R.drawable.ic_test+position);
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
        return v == obj;
    }
}
