package com.daejeonpeople.activities.ResultList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.daejeonpeople.R;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.ResultListItem;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.androidquery.util.AQUtility.getContext;

/**
 * Created by KimDongGyu on 2017-09-26.
 */

public class ResultList extends Activity {

    private ArrayList<ResultListItem> arrayListResultList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private APIinterface apiInterface;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.result_list_recycler_view);
        dbHelper = DBHelper.getInstance(getContext(), "CHECK.db", null, 1);

        Button backbtn = (Button) findViewById(R.id.detail_backbtn) ;
        backbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Intent intent = getIntent();
        final String category = intent.getStringExtra("category");
        final String category_key = intent.getStringExtra("category_key");
        Log.d("checkTheDetail", category);

        for(int i = 1; i < 4; i++){
            apiInterface = APIClient.getClient().create(APIinterface.class);
            apiInterface.getFilteringPage("UserSession=" + dbHelper.getCookie(), category, 1 , i).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if(response.code() == 200){

                        Log.d("detail_success","야 성공했다!!!");

                        JsonArray jsonArray = response.body();

                        for(int i = 0; i < jsonArray.size(); i++){
                            ResultListItem resultListItem = new ResultListItem();
                            resultListItem.setContent_id(jsonArray.get(i).getAsJsonObject().get("content_id").getAsInt());
                            Log.d("checkContentid",jsonArray.get(i).getAsJsonObject().get("content_id").toString());
                            resultListItem.setTitle(jsonArray.get(i).getAsJsonObject().get("title").toString().replaceAll("\"", ""));
                            resultListItem.setWish(jsonArray.get(i).getAsJsonObject().get("wish").getAsBoolean());
                            resultListItem.setWish_count(jsonArray.get(i).getAsJsonObject().get("wish_count").getAsInt());
                            if(jsonArray.get(i).getAsJsonObject().get("address") == null){
                                resultListItem.setImage("주소 정보가 없습니다.");
                            } else{
                                resultListItem.setAddress(jsonArray.get(i).getAsJsonObject().get("address").toString());
                            }
                            resultListItem.setCategory(jsonArray.get(i).getAsJsonObject().get("category").toString());
                            if(jsonArray.get(i).getAsJsonObject().get("image") == null){
                                resultListItem.setImage("NoImage");
                            } else {
                                resultListItem.setImage(jsonArray.get(i).getAsJsonObject().get("image").toString());
                            }
                            resultListItem.setMapx(jsonArray.get(i).getAsJsonObject().get("mapx").getAsDouble());
                            resultListItem.setMapy(jsonArray.get(i).getAsJsonObject().get("mapy").getAsDouble());

                            arrayListResultList.add(resultListItem);
                        }
                        ResultListAdapter mAdapter = new ResultListAdapter(arrayListResultList, getContext(), category_key);
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        Log.d("Detail_error", "ang gi mo thi");
                    }
                }
                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}