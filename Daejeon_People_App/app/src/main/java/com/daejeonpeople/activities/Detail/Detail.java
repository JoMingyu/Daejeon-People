package com.daejeonpeople.activities.Detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.daejeonpeople.R;
import com.daejeonpeople.adapter.CustomAdapter;
import com.daejeonpeople.adapter.CustomsAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.DetailItem;
import com.daejeonpeople.valueobject.MainItemMonthly;
import com.daejeonpeople.valueobject.MainItemPopular;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.androidquery.util.AQUtility.getContext;

/**
 * Created by KimDongGyu on 2017-09-26.
 */

public class Detail extends Activity {

    private ArrayList<DetailItem> arrayListDetail = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private APIinterface apiInterface;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        mRecyclerView = (RecyclerView) findViewById(R.id.detail_recycler_view);
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
        Log.d("checkTheDetail", category);

//        DetailItem detailItem = new DetailItem();
//        detailItem.setContent_id(1);
//        detailItem.setTitle("타이틀1");
//        detailItem.setWish(true);
//        detailItem.setWish_count(3);
//        detailItem.setAddress("대전광역시");
//        detailItem.setCategory("카테고리1");
//        detailItem.setImage("http://tong.visitkorea.or.kr/cms/resource/12/2364212_image2_1.jpg");
//        detailItem.setMapx(1.2432);
//        detailItem.setMapy(3.241);
//
//        arrayListDetail.add(detailItem);
//
//        DetailItem detailItem2 = new DetailItem();
//        detailItem2.setContent_id(1);
//        detailItem2.setTitle("타이틀12");
//        detailItem2.setWish(true);
//        detailItem2.setWish_count(3);
//        detailItem2.setAddress("대전광역시2");
//        detailItem2.setCategory("카테고리12");
//        detailItem2.setImage("http://tong.visitkorea.or.kr/cms/resource/12/2364212_image2_1.jpg");
//        detailItem2.setMapx(1.2432);
//        detailItem2.setMapy(3.241);
//
//        arrayListDetail.add(detailItem2);


//        DetailAdapter mAdapter = new DetailAdapter(arrayListDetail, getContext());
//        mRecyclerView.setAdapter(mAdapter);



        apiInterface = APIClient.getClient().create(APIinterface.class);
        apiInterface.getFilteringPage("UserSession=" + dbHelper.getCookie(), category, 1 , 1).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.code() == 200){

                    Log.d("detail_success","야 성공했다!!!");

                    JsonArray jsonArray = response.body();

                    for(int i = 0; i < jsonArray.size(); i++){
                        DetailItem detailItem = new DetailItem();

                        detailItem.setContent_id(jsonArray.get(i).getAsJsonObject().get("content_id").getAsInt());
                        Log.d("checkContentid",jsonArray.get(i).getAsJsonObject().get("content_id").toString());
                        detailItem.setTitle(jsonArray.get(i).getAsJsonObject().get("title").toString().replaceAll("\"", ""));
                        detailItem.setWish(jsonArray.get(i).getAsJsonObject().get("wish").getAsBoolean());
                        detailItem.setWish_count(jsonArray.get(i).getAsJsonObject().get("wish_count").getAsInt());
                        detailItem.setAddress(jsonArray.get(i).getAsJsonObject().get("address").toString());
                        detailItem.setCategory(jsonArray.get(i).getAsJsonObject().get("category").toString());
                        detailItem.setImage(jsonArray.get(i).getAsJsonObject().get("image").toString());
                        detailItem.setMapx(jsonArray.get(i).getAsJsonObject().get("mapx").getAsDouble());
                        detailItem.setMapy(jsonArray.get(i).getAsJsonObject().get("mapy").getAsDouble());

                        arrayListDetail.add(detailItem);
                    }
                    DetailAdapter mAdapter = new DetailAdapter(arrayListDetail, getContext());
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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}