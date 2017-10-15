package com.daejeonpeople.activities.side_menu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Main;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.network.SessionManager;
import com.daejeonpeople.support.security.AES;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dsm2016 on 2017-07-20.
 */

public class MyInfo extends Fragment{

    private APIinterface apIinterface;
    private TextView myName, myEmail, myPhoneNum;

    @Nullable
    @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.my_info, container, false);
        return viewGroup;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myName = (TextView)getActivity().findViewById(R.id.userName);
        myEmail = (TextView)getActivity().findViewById(R.id.userEmail);
        myPhoneNum = (TextView)getActivity().findViewById(R.id.userPhoneNum);

        final DBHelper dbHelper = DBHelper.getInstance(getActivity(), "CHECK.db", null, 1);
        apIinterface = APIClient.getClient().create(APIinterface.class);
        apIinterface.getUser("UserSession=" + dbHelper.getCookie()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == 200) {
                    Log.d("TEST", "SUCCESS");
                    myName.setText(response.body().get("name").getAsString());
                    myEmail.setText(response.body().get("email").getAsString());
                    myPhoneNum.setText(response.body().get("phone_number").getAsString());
                } else {
                    Log.d("TEST", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
