package com.daejeonpeople.activities.side_menu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Main;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.network.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dsm2016 on 2017-07-20.
 */

public class MyInfo extends Fragment{


    @Nullable
    @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.my_info, container, false);


//        final ActionBar chatting = getActivity().getActionBar();
//        chatting.setCustomView(R.layout.custom_myinfo);
//        chatting.setDisplayShowTitleEnabled(false);
//        chatting.setDisplayShowCustomEnabled(true);
//        chatting.setDisplayShowHomeEnabled(false);

//        backBtn = (Button) getView().findViewById(R.id.backBtn);

//        AQuery aq = new AQuery(getActivity().getApplicationContext());

//        aq.ajax("http://52.79.134.200/wish", String.class, new AjaxCallback<String>() {
//            @Override
//            public void callback(String url, String response, AjaxStatus status) {
//                if(status.getCode() == 200) {
//                    try {
//                        JSONObject res = new JSONObject(response);
//                    } catch(JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//
//                }
//            }
//        }.method(AQuery.METHOD_GET).cookie("UserSession", SessionManager.getCookieFromDB(getApplicationContext())));

//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity().getApplicationContext(), Main.class));
//            }
//        });




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
    }
}
