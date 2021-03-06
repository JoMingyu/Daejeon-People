package com.daejeonpeople.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
import com.daejeonpeople.valueobject.MainItemPopular;

import java.util.ArrayList;

/**
 * Created by 10102김동규 on 2017-07-11.
 */
public class MainPopularAdapter extends PagerAdapter {
    LayoutInflater inflater;
    private ArrayList<MainItemPopular> mDataset;
    private Context mContext;

    public MainPopularAdapter(LayoutInflater inflater, ArrayList<MainItemPopular> myDataset, Context context) {
        // TODO Auto-generated constructor stub
        this.mDataset = myDataset;
        Log.d("myData" , myDataset.toString());
        this.inflater=inflater;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDataset.size();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        View view = null;
        view= inflater.inflate(R.layout.main_viewpager_popular, null);
        ImageView img= (ImageView)view.findViewById(R.id.img_viewpager_childimage);
        TextView maintext = (TextView)view.findViewById(R.id.text_viewpager_main);
        TextView subtext = (TextView)view.findViewById(R.id.text_viewpager_sub);
        Log.d("position", position+"");
        maintext.setText(mDataset.get(position).getTitle());
        subtext.setText(mDataset.get(position).getEng_title());
        Log.d("image url", mDataset.get(position).getImage());

        if(mDataset.get(position).getImage() == "NoImage"){
            Glide.with(mContext).load(R.drawable.noimage).into(img);
        } else {
            String imgUrl = mDataset.get(position).getImage().substring(1, mDataset.get(position).getImage().length() - 1);
            Log.d("image url2", imgUrl);
            Glide.with(mContext).load(imgUrl).into(img);
        }
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
