package com.daejeonpeople.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daejeonpeople.R;
import com.daejeonpeople.adapter.MainPopularAdapter;
import com.daejeonpeople.adapter.MainMonthlyAdapter;
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
                    for(int i = 0; i < jsonArrayM.size(); i++) {
                        MainItemMonthly mainItemMonthly = new MainItemMonthly();
                        MainItemPopular mainItemPopular = new MainItemPopular();

                        mainItemMonthly.setWish(jsonArrayM.get(i).getAsJsonObject().get("wish").getAsBoolean());
                        if(jsonArrayM.get(i).getAsJsonObject().get("image") == null){
                            mainItemMonthly.setImage("NoImage");
                        } else {
                            mainItemMonthly.setImage(jsonArrayM.get(i).getAsJsonObject().get("image").toString());
                        }
                        if(jsonArrayM.get(i).getAsJsonObject().get("address") == null){
                            mainItemMonthly.setAddress("주소 정보가 없습니다.");
                        } else {
                            mainItemMonthly.setAddress(jsonArrayM.get(i).getAsJsonObject().get("address").toString());
                        }
                        mainItemMonthly.setContent_id(jsonArrayM.get(i).getAsJsonObject().get("content_id").getAsInt());
                        mainItemMonthly.setTitle(jsonArrayM.get(i).getAsJsonObject().get("title").toString().replaceAll("\"", ""));
                        mainItemMonthly.setWish_count(jsonArrayM.get(i).getAsJsonObject().get("wish_count").getAsInt());
                        if(jsonArrayM.get(i).getAsJsonObject().get("eng_title") == null) {
                            mainItemMonthly.setEng_title("title 정보가 없습니다.");
                        } else {
                            mainItemMonthly.setEng_title(jsonArrayM.get(i).getAsJsonObject().get("eng_title").toString().replaceAll("\"", ""));
                        }
                        arrayListM.add(mainItemMonthly);

                        if(jsonArrayP.get(i).getAsJsonObject().get("image") == null){
                            mainItemPopular.setImage("NoImage");
                        } else {
                            mainItemPopular.setImage(jsonArrayP.get(i).getAsJsonObject().get("image").toString());
                        }
                        mainItemPopular.setContent_id(jsonArrayP.get(i).getAsJsonObject().get("content_id").getAsInt());
                        mainItemPopular.setTitle(jsonArrayP.get(i).getAsJsonObject().get("title").toString().replaceAll("\"", ""));
                        if(jsonArrayP.get(i).getAsJsonObject().get("eng_title") == null) {
                            mainItemPopular.setEng_title("title 정보가 없습니다.");
                        } else {
                            mainItemPopular.setEng_title(jsonArrayP.get(i).getAsJsonObject().get("eng_title").toString().replaceAll("\"", ""));
                        }
                        arrayListP.add(mainItemPopular);
                    }
                    MainPopularAdapter adapter1 = new MainPopularAdapter(getActivity().getLayoutInflater(), arrayListP, getActivity());
                    pager1.setAdapter(adapter1);
                    MainMonthlyAdapter adapter2 = new MainMonthlyAdapter(getActivity().getLayoutInflater(), arrayListM, getActivity());
                    pager2.setAdapter(adapter2);
                    Log.d("image", arrayListM.get(3).getTitle()+"");
                } else {
                    Log.d("Main_fragment_error", "ang gi mo thi");
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
