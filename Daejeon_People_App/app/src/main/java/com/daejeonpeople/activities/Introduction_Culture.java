package com.daejeonpeople.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.CulturalAdapter;
import com.daejeonpeople.adapter.WishlistAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.CulturalItem;
import com.daejeonpeople.valueobject.WishlistItem;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jeong Minji on 2017-07-03.
 */

public class Introduction_Culture extends BaseActivity {

    private ImageButton btn_star;
    private APIinterface apIinterface;
    private ArrayList<CulturalItem> Dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_cultural);

        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        btn_star = (ImageButton)findViewById(R.id.star);
        apIinterface = APIClient.getClient().create(APIinterface.class);

        apIinterface.getDetail("UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code()== 200) {
                    Log.d("response", "SUCCESS");
                    Gson gson = new Gson();
                    CulturalItem[] items = gson.fromJson(response.body().toString(), CulturalItem[].class);
//                    ((CulturalAdapter))
//                    ((CulturalAdapter)mRecyclerView.getAdapter()).setData(items);
//                    mRecyclerView.getAdapter().notifyDataSetChanged();

                    response.body();
                } else if(response.code() == 204) {
                    Log.d("response", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });

        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
