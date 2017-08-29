package com.daejeonpeople.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.daejeonpeople.R;

/**
 * Created by KimDongGyu on 2017-08-29.
 */

public class FilterAdapter extends PagerAdapter {
    LayoutInflater inflater;
    public FilterAdapter(LayoutInflater inflater) {
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
        view = inflater.inflate(R.layout.filter_childview, null);

        Button btn = (Button) view.findViewById(R.id.page_btn1);
        btn.getResources();

        Button btn2 = (Button) view.findViewById(R.id.page_btn2);
        btn2.getResources();

        Button btn3 = (Button) view.findViewById(R.id.page_btn3);
        btn3.getResources();

        Button btn4 = (Button) view.findViewById(R.id.page_btn4);
        btn4.getResources();

        Button btn5 = (Button) view.findViewById(R.id.page_btn5);
        btn5.getResources();

        Button btn6 = (Button) view.findViewById(R.id.page_btn6);
        btn6.getResources();

        Button btn7 = (Button) view.findViewById(R.id.page_btn7);
        btn7.getResources();

        Button btn8 = (Button) view.findViewById(R.id.page_btn8);
        btn8.getResources();

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
