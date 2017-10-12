package com.daejeonpeople.activities.account;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.views.ColorManager;
import com.daejeonpeople.support.views.SnackbarManager;
import com.daejeonpeople.valueobject.UserInSignup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geni on 2017. 5. 28..
 */

//민지

public class EntryID extends AppCompatActivity {
    AQuery aQuery;
    Button okBtn;
    EditText inputCode;
    Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_find_id_input);
        okBtn = (Button)findViewById(R.id.okBtn);
        inputCode = (EditText)findViewById(R.id.input_code);
        aQuery = new AQuery(getApplicationContext());
        params = new HashMap<>();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aQuery.ajax("http://52.79.134.200/signin", params, String.class, new AjaxCallback<String>(){
                    @Override
                    public void callback(String url, String object, AjaxStatus status) {

                    }
                });
            }
        });
    }
}
