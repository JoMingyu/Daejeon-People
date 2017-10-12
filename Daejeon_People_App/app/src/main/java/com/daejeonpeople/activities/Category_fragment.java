package com.daejeonpeople.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;

/**
 * Created by KimDongGyu on 2017-08-27.
 */

public class Category_fragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.tab2_category, container, false);

        //카테고리 이미지 적용

        ImageView iv = (ImageView)viewGroup.findViewById(R.id.tourism);
        Glide.with(this).load(R.drawable.bg_tourism).into(iv);

        iv = (ImageView)viewGroup.findViewById(R.id.culture);
        Glide.with(this).load(R.drawable.bg_culture).into(iv);

        iv = (ImageView)viewGroup.findViewById(R.id.event);
        Glide.with(this).load(R.drawable.bg_event).into(iv);

        iv = (ImageView)viewGroup.findViewById(R.id.course);
        Glide.with(this).load(R.drawable.bg_course).into(iv);

        iv = (ImageView)viewGroup.findViewById(R.id.leisure);
        Glide.with(this).load(R.drawable.bg_leisure).into(iv);

        iv = (ImageView)viewGroup.findViewById(R.id.accommodation);
        Glide.with(this).load(R.drawable.bg_accommodation).into(iv);

        iv = (ImageView)viewGroup.findViewById(R.id.shopping);
        Glide.with(this).load(R.drawable.bg_shopping).into(iv);

        iv = (ImageView)viewGroup.findViewById(R.id.food);
        Glide.with(this).load(R.drawable.bg_food).into(iv);

        return viewGroup;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
