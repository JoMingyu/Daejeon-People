package com.daejeonpeople.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.ResultListItem;
import com.daejeonpeople.valueobject.SearchItem;

import java.util.ArrayList;

import static com.androidquery.util.AQUtility.getContext;

/**
 * Created by geni on 2017. 5. 28..
 */
//근철

public class Search extends BaseActivity {

    private ArrayList<SearchItem> arrayListSearch = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private APIinterface apiInterface;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        mRecyclerView = (RecyclerView) findViewById(R.id.search_result_recyclerview);
        dbHelper = DBHelper.getInstance(getContext(), "CHECK.db", null, 1);

        //search_back버튼 이벤트
        Button search_back_btn = (Button) findViewById(R.id.backBtn);
        search_back_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //검색창
                finish();
            }
        }) ;

        //Spinner설정
        final Spinner spinner = (Spinner)findViewById(R.id.search_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 // 조회순
                if(position == 0) {
                    Button search_btn = (Button) findViewById(R.id.search_btn);
                    search_btn.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EditText editText = (EditText)findViewById(R.id.searchContent);
                            String search_result = editText.getText().toString();
                            if ( editText.getText().toString().length() == 0 ) {
                                Toast.makeText(getApplicationContext(), "검색 내용을 입력해주세요.", Toast.LENGTH_LONG).show();
                            } else {











                            }
                        }
                    }) ;




                } // 위시순
                else if(position == 1){
                    Button search_btn = (Button) findViewById(R.id.search_btn);
                    search_btn.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EditText editText = (EditText)findViewById(R.id.searchContent);
                            String search_result = editText.getText().toString();
                            if ( editText.getText().toString().length() == 0 ) {
                                Toast.makeText(getApplicationContext(), "검색 내용을 입력해주세요.", Toast.LENGTH_LONG).show();
                            } else {















                            }
                        }
                    }) ;


                } // 거리순
                else if(position == 2) {
                    Button search_btn = (Button) findViewById(R.id.search_btn);
                    search_btn.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EditText editText = (EditText)findViewById(R.id.searchContent);
                            String search_result = editText.getText().toString();
                            if ( editText.getText().toString().length() == 0 ) {
                                Toast.makeText(getApplicationContext(), "검색 내용을 입력해주세요.", Toast.LENGTH_LONG).show();
                            } else {













                            }
                        }
                    }) ;


                } else {
                    Toast.makeText(getApplicationContext(), "잘못된 접근입니다.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            //디폴트 값 조회순
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(0);
            }
        });









    }
}
