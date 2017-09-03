package com.daejeonpeople.activities.Filter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daejeonpeople.R;
import com.daejeonpeople.support.views.SnackbarManager;

/**
 * Created by KimDongGyu on 2017-08-31.
 */

public class Filter_detail extends AppCompatActivity {

    private Filter_detail_ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.filter_detail);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.filter_detial_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Filter_detail_CustomRecyclerView recyclerView = (Filter_detail_CustomRecyclerView) findViewById(R.id.filter_recyclerview);

        adapter = new Filter_detail_ItemAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new Filter_detail_ItemLayoutManger(this));

        Button filter_back_btn = (Button) findViewById(R.id.filter_detial_backbtn);
        filter_back_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                finish();
            }
        }) ;
    }

}
