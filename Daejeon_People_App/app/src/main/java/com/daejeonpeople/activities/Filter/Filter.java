package com.daejeonpeople.activities.Filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;

/**
 * Created by KimDongGyu on 2017-08-29.
 */

public class Filter extends AppCompatActivity{

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.filter_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView iv = (ImageView)findViewById(R.id.filter_img);
        Glide.with(this).load(R.drawable.background_filter).into(iv);


        Button filter_back_btn = (Button) findViewById(R.id.filter_backbtn);
        filter_back_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                finish();
            }
        }) ;

        Button page_btn1 = (Button) findViewById(R.id.page_btn1);
        page_btn1.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Filter.this, Filter_detail.class);
                startActivity(intent);
            }
        }) ;

        Button page_btn2 = (Button) findViewById(R.id.page_btn2);
        page_btn2.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Filter.this, Filter_detail.class);
                startActivity(intent);
            }
        }) ;

        Button page_btn3 = (Button) findViewById(R.id.page_btn3);
        page_btn3.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Filter.this, Filter_detail.class);
                startActivity(intent);
            }
        }) ;

        Button page_btn4 = (Button) findViewById(R.id.page_btn4);
        page_btn4.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Filter.this, Filter_detail.class);
                startActivity(intent);
            }
        }) ;

        Button page_btn5 = (Button) findViewById(R.id.page_btn5);
        page_btn5.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Filter.this, Filter_detail.class);
                startActivity(intent);
            }
        }) ;

        Button page_btn6 = (Button) findViewById(R.id.page_btn6);
        page_btn6.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Filter.this, Filter_detail.class);
                startActivity(intent);
            }
        }) ;

        Button page_btn7 = (Button) findViewById(R.id.page_btn7);
        page_btn7.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Filter.this, Filter_detail.class);
                startActivity(intent);
            }
        }) ;

        Button page_btn8 = (Button) findViewById(R.id.page_btn8);
        page_btn8.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Filter.this, Filter_detail.class);
                startActivity(intent);
            }
        }) ;
    }
}
