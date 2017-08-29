package com.daejeonpeople.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.daejeonpeople.R;
import com.daejeonpeople.adapter.CustomAdapter;
import com.daejeonpeople.adapter.FilterAdapter;

/**
 * Created by KimDongGyu on 2017-08-29.
 */

public class Filter extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_edit);

        ViewPager pager;

        //Advertising ViewPager
        pager = (ViewPager)findViewById(R.id.filter_pager);
        FilterAdapter adapter = new FilterAdapter(getLayoutInflater());
        pager.setAdapter(adapter);
    }
}
