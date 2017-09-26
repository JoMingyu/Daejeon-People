package com.daejeonpeople.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;

/**
 * Created by geni on 2017. 5. 28..
 */
//근철

public class Search extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        //search버튼 이벤트
        Button search_btn = (Button) findViewById(R.id.backBtn);

        search_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //검색창
                finish();
            }
        }) ;
    }
}
