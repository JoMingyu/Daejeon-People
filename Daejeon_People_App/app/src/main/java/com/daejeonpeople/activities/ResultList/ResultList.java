package com.daejeonpeople.activities.ResultList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daejeonpeople.R;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.ResultListItem;
import com.github.ybq.endless.Endless;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.androidquery.util.AQUtility.getContext;
/**
 * Created by KimDongGyu on 2017-09-26.
 */
public class ResultList extends Activity {
    private RecyclerView mRecyclerView;
    private ResultListAdapter mAdapter;
    private APIinterface apiInterface;
    private DBHelper dbHelper;
    private View loadingView;
    Endless endless;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.result_list_recycler_view);
        dbHelper = DBHelper.getInstance(getContext(), "CHECK.db", null, 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        Button backbtn = (Button) findViewById(R.id.detail_backbtn) ;
        backbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadingView = View.inflate(this, R.layout.result_list_loading, null);
        loadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        endless = Endless.applyTo(mRecyclerView,
                loadingView
        );
        final Intent intent = getIntent();
        final String category = intent.getStringExtra("category");
        category_key = intent.getStringExtra("category_key");
        Log.d("checkTheDetail", category);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData(category, 1);
        endless.setLoadMoreListener(new Endless.LoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                getData(category, page);
            }
        });

    }
    private String category_key;
    private void getData(String category, final int page){
        apiInterface = APIClient.getClient().create(APIinterface.class);
        apiInterface.getFilteringPage("UserSession=" + dbHelper.getCookie(), category, 1 , page).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, final Response<JsonObject> response) {
                if(response.code() == 200){
                    JsonElement jsonElement = response.body().get("data");
                    Gson gson = new Gson();
                    ResultListItem[] filterData = gson.fromJson(jsonElement, ResultListItem[].class);
                    final List<ResultListItem> arrayListResultList = Arrays.asList(filterData);
                    if (page == 1) {
                        mAdapter = new ResultListAdapter(arrayListResultList, category_key);
                        //mRecyclerView.setAdapter(mAdapter);
                        endless.setAdapter(mAdapter);
                    } else {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                            mAdapter.addData(arrayListResultList);
                                            endless.loadMoreComplete();
                                    }
                                });
                            }
                        }, 1000*2);
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }
}