package com.daejeonpeople.connection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.util.HashMap;

/**
 * Created by geni on 2017. 6. 1..
 */

public class AqueryConnection {
    private int statusCode;
    AQuery aQuery;
    public boolean isResponse = false;
    public AqueryConnection(Context context){
        aQuery = new AQuery(context);
    }

    public void connection(HashMap<String, Object> params, String uri){
        aQuery.ajax("http://52.79.134.200/" + uri, params, String.class, new AjaxCallback<String>(){
            @Override
            public void callback (String url, String response, AjaxStatus status){
                Log.d("callback what Da", "woo hahaha");
                status = new AjaxStatus(status.getCode(), "hello");
                isResponse = true;
                statusCode = status.getCode();
            }
        });
    }

    public int getStatusCode(){
        return this.statusCode;
    }
}
