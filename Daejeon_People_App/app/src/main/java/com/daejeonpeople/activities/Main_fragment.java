package com.daejeonpeople.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daejeonpeople.R;
import com.daejeonpeople.adapter.CustomAdapter;
import com.daejeonpeople.adapter.CustomsAdapter;

/**
 * Created by KimDongGyu on 2017-08-27.
 */

public class Main_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.tab1_main, container, false);

        ViewPager pager;

        //Advertising ViewPager
        pager = (ViewPager) viewGroup.findViewById(R.id.pager1);
        CustomAdapter adapter = new CustomAdapter(getActivity().getLayoutInflater());
        pager.setAdapter(adapter);

        //이달의 인기만점 ViewPager
        pager = (ViewPager) viewGroup.findViewById(R.id.pager2);
        CustomsAdapter adapter2 = new CustomsAdapter(getActivity().getLayoutInflater());
        pager.setAdapter(adapter2);

        return viewGroup;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
