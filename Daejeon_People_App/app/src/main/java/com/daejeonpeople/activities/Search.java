package com.daejeonpeople.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.SearchAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.SearchItem;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.androidquery.util.AQUtility.getContext;

/**
 * Created by geni on 2017. 5. 28..
 */
//근철

public class Search extends BaseActivity {

    private ArrayList<SearchItem> arrayListSearch = new ArrayList<>();
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
            SearchAdapter mAdapter = null;
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
                                Log.d("Stringcheck",search_result);
                                apiInterface = APIClient.getClient().create(APIinterface.class);
                                apiInterface.getSearchResult("UserSession=" + dbHelper.getCookie(), search_result, 1 , 1).enqueue(new Callback<JsonArray>() {
                                    @Override
                                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                                        if(response.code() == 200){
                                            Log.d("search_success","야 성공했다!!!");
                                            JsonArray jsonArray = response.body();
                                            arrayListSearch.clear();
                                            for(int i = 0; i < jsonArray.size(); i++){
                                                SearchItem searchItem = new SearchItem();

                                                searchItem.setContent_id(jsonArray.get(i).getAsJsonObject().get("content_id").getAsInt());
                                                Log.d("checkContentid",jsonArray.get(i).getAsJsonObject().get("content_id").toString());
                                                searchItem.setTitle(jsonArray.get(i).getAsJsonObject().get("title").toString().replaceAll("\"", ""));
                                                searchItem.setWish(jsonArray.get(i).getAsJsonObject().get("wish").getAsBoolean());
                                                searchItem.setWish_count(jsonArray.get(i).getAsJsonObject().get("wish_count").getAsInt());
                                                if(jsonArray.get(i).getAsJsonObject().get("address") == null){
                                                    searchItem.setImage("주소 정보가 없습니다.");
                                                } else{
                                                    searchItem.setAddress(jsonArray.get(i).getAsJsonObject().get("address").toString());
                                                }
                                                searchItem.setCategory(jsonArray.get(i).getAsJsonObject().get("category").toString());
                                                if(jsonArray.get(i).getAsJsonObject().get("image") == null){
                                                    searchItem.setImage("NoImage");
                                                } else {
                                                    searchItem.setImage(jsonArray.get(i).getAsJsonObject().get("image").toString());
                                                }
                                                searchItem.setMapx(jsonArray.get(i).getAsJsonObject().get("mapx").getAsDouble());
                                                searchItem.setMapy(jsonArray.get(i).getAsJsonObject().get("mapy").getAsDouble());

                                                arrayListSearch.add(searchItem);
                                            }
                                            mAdapter = new SearchAdapter(arrayListSearch, getContext());
                                            mRecyclerView.setAdapter(mAdapter);

                                        } else {
                                            Log.d("search_error", "ang gi mo thi"+response.code());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<JsonArray> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                                // use this setting to improve performance if you know that changes
                                // in content do not change the layout size of the RecyclerView
                                mRecyclerView.setHasFixedSize(true);
                                // use a linear layout manager
                                RecyclerView.LayoutManager mLayoutManager;
                                mLayoutManager = new LinearLayoutManager(view.getContext());
                                mRecyclerView.setLayoutManager(mLayoutManager);
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
                                Log.d("Stringcheck",search_result);
                                apiInterface = APIClient.getClient().create(APIinterface.class);
                                apiInterface.getSearchResult("UserSession=" + dbHelper.getCookie(), search_result, 2 , 1).enqueue(new Callback<JsonArray>() {
                                    @Override
                                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                                        if(response.code() == 200){
                                            Log.d("search_success","야 성공했다!!!");
                                            JsonArray jsonArray = response.body();
                                            arrayListSearch.clear();
                                            for(int i = 0; i < jsonArray.size(); i++){
                                                SearchItem searchItem = new SearchItem();

                                                searchItem.setContent_id(jsonArray.get(i).getAsJsonObject().get("content_id").getAsInt());
                                                Log.d("checkContentid",jsonArray.get(i).getAsJsonObject().get("content_id").toString());
                                                searchItem.setTitle(jsonArray.get(i).getAsJsonObject().get("title").toString().replaceAll("\"", ""));
                                                searchItem.setWish(jsonArray.get(i).getAsJsonObject().get("wish").getAsBoolean());
                                                searchItem.setWish_count(jsonArray.get(i).getAsJsonObject().get("wish_count").getAsInt());
                                                if(jsonArray.get(i).getAsJsonObject().get("address") == null){
                                                    searchItem.setImage("주소 정보가 없습니다.");
                                                } else{
                                                    searchItem.setAddress(jsonArray.get(i).getAsJsonObject().get("address").toString());
                                                }
                                                searchItem.setCategory(jsonArray.get(i).getAsJsonObject().get("category").toString());
                                                if(jsonArray.get(i).getAsJsonObject().get("image") == null){
                                                    searchItem.setImage("NoImage");
                                                } else {
                                                    searchItem.setImage(jsonArray.get(i).getAsJsonObject().get("image").toString());
                                                }
                                                searchItem.setMapx(jsonArray.get(i).getAsJsonObject().get("mapx").getAsDouble());
                                                searchItem.setMapy(jsonArray.get(i).getAsJsonObject().get("mapy").getAsDouble());

                                                arrayListSearch.add(searchItem);
                                            }
                                            mAdapter = new SearchAdapter(arrayListSearch, getContext());
                                            mRecyclerView.setAdapter(mAdapter);
                                        } else {
                                            Log.d("search_error", "ang gi mo thi"+response.code());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<JsonArray> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                                // use this setting to improve performance if you know that changes
                                // in content do not change the layout size of the RecyclerView
                                mRecyclerView.setHasFixedSize(true);
                                // use a linear layout manager
                                RecyclerView.LayoutManager mLayoutManager;
                                mLayoutManager = new LinearLayoutManager(view.getContext());
                                mRecyclerView.setLayoutManager(mLayoutManager);
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
                                Log.d("Stringcheck",search_result);
                                apiInterface = APIClient.getClient().create(APIinterface.class);
                                apiInterface.getSearchResult("UserSession=" + dbHelper.getCookie(), search_result, 3 , 1, 32.54556, 127.045145).enqueue(new Callback<JsonArray>() {
                                    @Override
                                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                                        if(response.code() == 200){
                                            Log.d("search_success","야 성공했다!!!");
                                            JsonArray jsonArray = response.body();
                                            arrayListSearch.clear();

                                            for(int i = 0; i < jsonArray.size(); i++){
                                                SearchItem searchItem = new SearchItem();

                                                searchItem.setContent_id(jsonArray.get(i).getAsJsonObject().get("content_id").getAsInt());
                                                Log.d("checkContentid",jsonArray.get(i).getAsJsonObject().get("content_id").toString());
                                                searchItem.setTitle(jsonArray.get(i).getAsJsonObject().get("title").toString().replaceAll("\"", ""));
                                                searchItem.setWish(jsonArray.get(i).getAsJsonObject().get("wish").getAsBoolean());
                                                searchItem.setWish_count(jsonArray.get(i).getAsJsonObject().get("wish_count").getAsInt());
                                                if(jsonArray.get(i).getAsJsonObject().get("address") == null){
                                                    searchItem.setImage("주소 정보가 없습니다.");
                                                } else{
                                                    searchItem.setAddress(jsonArray.get(i).getAsJsonObject().get("address").toString());
                                                }
                                                searchItem.setCategory(jsonArray.get(i).getAsJsonObject().get("category").toString());
                                                if(jsonArray.get(i).getAsJsonObject().get("image") == null){
                                                    searchItem.setImage("NoImage");
                                                } else {
                                                    searchItem.setImage(jsonArray.get(i).getAsJsonObject().get("image").toString());
                                                }
                                                searchItem.setMapx(jsonArray.get(i).getAsJsonObject().get("mapx").getAsDouble());
                                                searchItem.setMapy(jsonArray.get(i).getAsJsonObject().get("mapy").getAsDouble());

                                                arrayListSearch.add(searchItem);
                                            }
                                            mAdapter = new SearchAdapter(arrayListSearch, getContext());
                                            mRecyclerView.setAdapter(mAdapter);
                                        } else {
                                            Log.d("search_error", "ang gi mo thi"+response.code());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<JsonArray> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                                // use this setting to improve performance if you know that changes
                                // in content do not change the layout size of the RecyclerView
                                mRecyclerView.setHasFixedSize(true);
                                // use a linear layout manager
                                RecyclerView.LayoutManager mLayoutManager;
                                mLayoutManager = new LinearLayoutManager(view.getContext());
                                mRecyclerView.setLayoutManager(mLayoutManager);
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
