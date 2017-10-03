package com.daejeonpeople.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Category.Category_detail;

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
        Button tourism_btn = (Button)viewGroup.findViewById(R.id.category_tourist);
        tourism_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getContext(), Category_detail.class);
                intent.putExtra("key","Tourist");
                startActivity(intent);
            }
        });


        ImageView iv2 = (ImageView)viewGroup.findViewById(R.id.culture);
        Glide.with(this).load(R.drawable.bg_culture).into(iv2);
        Button culture_btn = (Button)viewGroup.findViewById(R.id.category_culture);
        culture_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getContext(), Category_detail.class);
                intent.putExtra("key","Culture");
                startActivity(intent);
            }
        });



        ImageView iv3 = (ImageView)viewGroup.findViewById(R.id.event);
        Glide.with(this).load(R.drawable.bg_event).into(iv3);
        Button event_btn = (Button)viewGroup.findViewById(R.id.category_event);
        event_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getContext(), Category_detail.class);
                intent.putExtra("key","Events");
                startActivity(intent);
            }
        });




        ImageView iv4 = (ImageView)viewGroup.findViewById(R.id.course);
        Glide.with(this).load(R.drawable.bg_course).into(iv4);
        Button course_btn = (Button)viewGroup.findViewById(R.id.category_course);
        course_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getContext(), Category_detail.class);
                intent.putExtra("key","Course");
                startActivity(intent);
            }
        });




        ImageView iv5 = (ImageView)viewGroup.findViewById(R.id.leisure);
        Glide.with(this).load(R.drawable.bg_leisure).into(iv5);
        Button leisure_btn = (Button)viewGroup.findViewById(R.id.category_leisure);
        leisure_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getContext(), Category_detail.class);
                intent.putExtra("key","Leisure");
                startActivity(intent);
            }
        });



        ImageView iv6 = (ImageView)viewGroup.findViewById(R.id.accommodation);
        Glide.with(this).load(R.drawable.bg_accommodation).into(iv6);
        Button accommodation_btn = (Button)viewGroup.findViewById(R.id.category_accommodation);
        accommodation_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getContext(), Category_detail.class);
                intent.putExtra("key","Accommodation");
                startActivity(intent);
            }
        });



        ImageView iv7 = (ImageView)viewGroup.findViewById(R.id.shopping);
        Glide.with(this).load(R.drawable.bg_shopping).into(iv7);
        Button shopping_btn = (Button)viewGroup.findViewById(R.id.category_shopping);
        shopping_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getContext(), Category_detail.class);
                intent.putExtra("key","Shopping");
                startActivity(intent);
            }
        });



        ImageView iv8 = (ImageView)viewGroup.findViewById(R.id.food);
        Glide.with(this).load(R.drawable.bg_food).into(iv8);
        Button food_btn = (Button)viewGroup.findViewById(R.id.category_food);
        food_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getContext(), Category_detail.class);
                intent.putExtra("key","Restaurant");
                startActivity(intent);
            }
        });



        return viewGroup;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
