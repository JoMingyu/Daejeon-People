package com.daejeonpeople.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.daejeonpeople.support.security.AES;
import com.daejeonpeople.support.views.ColorManager;
import com.daejeonpeople.support.views.SnackbarManager;
import com.daejeonpeople.valueobject.UserInSignup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geni on 2017. 5. 28..
 */
// 근철
// Modified by JoMingyu

public class FindID extends Activity {
    private AQuery aQuery;

    private EditText inputEmail;
    private EditText inputCode;
    private Button findBtn;

    private Map<String, String> params = new HashMap<>();
    private boolean emailDemanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id);

        aQuery = new AQuery(getApplicationContext());

        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputCode = (EditText) findViewById(R.id.inputCode);
        findBtn = (Button)findViewById(R.id.findBtn);

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailDemanded = false;
                inputEmail.setTextColor(Color.BLACK);
                findBtn.setText("발급");
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!emailDemanded){
                    params.put("email", inputEmail.getText().toString());
                    aQuery.ajax("http://52.79.134.200/find/id/demand", params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status){
                            if(status.getCode() == 201){
                                emailDemanded = true;

                                inputEmail.setTextColor(ColorManager.successColor);

                                findBtn.setText("인증");
                                ShowDialog();
                            } else {
                                inputEmail.setTextColor(ColorManager.failureColor);

                                SnackbarManager.createCancelableSnackbar(v, "일치하는 계정 정보가 없습니다.", 3000).show();
                            }
                        }
                    });
                } else {
                    params.put("code", inputCode.getText().toString());
                    aQuery.ajax("http://52.79.134.200/find/id/verify", params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status){
                            if(status.getCode() == 201){
                                inputCode.setTextColor(ColorManager.successColor);

                                try {
                                    JSONObject resp = new JSONObject(response);

                                    String id = AES.decrypt(resp.getString("id"));
                                } catch(JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                inputCode.setTextColor(ColorManager.failureColor);
                            }
                        }
                    });
                    //전송된 id 확인할 레이아웃 제작 필요 - 민지
                }
            }
        });
    }

    private void ShowDialog()
    {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.email_certified_dialog, null);
        final Dialog myDialog = new Dialog(this);

        myDialog.setTitle("이메일 인증");
        myDialog.setContentView(dialogLayout);
        myDialog.show();

        Button okBtn = (Button)dialogLayout.findViewById(R.id.okBtn);
        Button cancelBtn = (Button)dialogLayout.findViewById(R.id.cancelBtn);

        okBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDialog.cancel();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDialog.cancel();
            }
        });
    }
}
