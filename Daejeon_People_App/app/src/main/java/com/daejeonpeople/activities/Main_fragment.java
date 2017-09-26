package com.daejeonpeople.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daejeonpeople.R;
import com.daejeonpeople.adapter.CustomAdapter;
import com.daejeonpeople.adapter.CustomsAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.MainItemMonthly;
import com.daejeonpeople.valueobject.MainItemPopular;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KimDongGyu on 2017-08-27.
 */

public class Main_fragment extends Fragment {
    private ArrayList<MainItemMonthly> arrayListM = new ArrayList<>();
    private ArrayList<MainItemPopular> arrayListP = new ArrayList<>();
    private APIinterface apiInterface;
    private DBHelper dbHelper;
    private ViewPager pager1;
    private ViewPager pager2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.tab1_main, container, false);

        apiInterface = APIClient.getClient().create(APIinterface.class);
        dbHelper = DBHelper.getInstance(getContext(), "CHECK.db", null, 1);
        pager1 = (ViewPager) viewGroup.findViewById(R.id.pager1);
        pager2 = (ViewPager) viewGroup.findViewById(R.id.pager2);

        apiInterface.getMainInfo("UserSession=" + dbHelper.getCookie(), 5, 5).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == 200){
                    JsonArray jsonArrayM = response.body().get("monthly").getAsJsonArray();
                    JsonArray jsonArrayP = response.body().get("popular").getAsJsonArray();
                    for(int i = 0; i < 5; i++) {
                        MainItemMonthly mainItemMonthly = new MainItemMonthly();
                        MainItemPopular mainItemPopular = new MainItemPopular();

                        mainItemMonthly.setWish(jsonArrayM.get(i).getAsJsonObject().get("wish").getAsBoolean());
                        mainItemMonthly.setImage(jsonArrayM.get(i).getAsJsonObject().get("image").toString());
                        mainItemMonthly.setAddress(jsonArrayM.get(i).getAsJsonObject().get("address").toString());
                        mainItemMonthly.setContent_id(jsonArrayM.get(i).getAsJsonObject().get("content_id").getAsInt());
                        mainItemMonthly.setTitle(jsonArrayM.get(i).getAsJsonObject().get("title").toString());
                        mainItemMonthly.setContent_id(jsonArrayM.get(i).getAsJsonObject().get("wish_count").getAsInt());

                        arrayListM.add(mainItemMonthly);

                        mainItemPopular.setImage(jsonArrayP.get(i).getAsJsonObject().get("image").toString());
                        mainItemPopular.setContent_id(jsonArrayP.get(i).getAsJsonObject().get("content_id").getAsInt());
                        mainItemPopular.setTitle(jsonArrayP.get(i).getAsJsonObject().get("title").toString());
                        arrayListP.add(mainItemPopular);
                    }
                    CustomAdapter adapter1 = new CustomAdapter(getActivity().getLayoutInflater(), arrayListP, getActivity());
                    pager1.setAdapter(adapter1);
                    CustomsAdapter adapter2 = new CustomsAdapter(getActivity().getLayoutInflater(), arrayListM, getActivity());
                    pager2.setAdapter(adapter2);
                    Log.d("image", arrayListM.get(3).getTitle()+"");
                } else {
                    Log.d("error", "ang gi mo thi");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return viewGroup;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
