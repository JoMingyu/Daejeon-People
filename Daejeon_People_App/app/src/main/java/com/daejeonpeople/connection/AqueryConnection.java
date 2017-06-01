package com.daejeonpeople.connection;

import android.content.Context;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.util.HashMap;

/**
 * Created by geni on 2017. 6. 1..
 */

public class AqueryConnection {
    int statusCode;

    public int connection(Context context, HashMap<String, Object> params, String uri){
        AQuery aQuery = new AQuery(context);
        aQuery.ajax("http://52.79.134.200/" + uri, params, String.class, new AjaxCallback<String>(){
            @Override
            public void callback(String url, String response, AjaxStatus status) {
                Log.d("url", url);
                Log.d("response", response);
                Log.d("status", Integer.toString(status.getCode()));
                setCode(status.getCode());
            }
        });
        return this.statusCode;
    }

    public void setCode(int code){
        this.statusCode = code;
    }
}
