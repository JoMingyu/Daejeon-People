package com.daejeonpeople.activities.Filter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        ImageView iv = (ImageView)findViewById(R.id.filter_img);
        Glide.with(this).load(R.drawable.background_filter).into(iv);


        Button filter_back_btn = (Button) findViewById(R.id.filter_backbtn);

        filter_back_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                finish();
            }
        }) ;
    }
}
