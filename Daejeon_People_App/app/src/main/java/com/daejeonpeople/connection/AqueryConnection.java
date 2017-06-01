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
    private AjaxStatus status;

    public AqueryConnection(){

    }

    public AqueryConnection(Context context, HashMap<String, Object> params, String uri) {
        AQuery aQuery = new AQuery(context);
        aQuery.ajax("http://52.79.134.200/" + uri, params, String.class, new AjaxCallback<String>(){
           @Override
            public void callback(String url, String response, AjaxStatus status) {
               Log.d("url", url);
               Log.d("response", response);
               Log.d("status", Integer.toString(status.getCode()));
               this.status = status;
           }
        });
    }

    public int getCode(){
        return (int)status.getCode();
    }
}
