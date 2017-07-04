package com.daejeonpeople.activities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.daejeonpeople.support.views.ColorManager;
import com.daejeonpeople.support.views.SnackbarManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geni on 2017. 5. 28.
 */

// 근철
// Modified by JoMingyu

public class FindPW extends Activity {
    private AQuery aQuery;

    private Button findBtn;
    private EditText inputId;
    private EditText inputEmail;
    private EditText inputCode;

    private Map<String, String> params = new HashMap<>();
    private boolean emailDemanded = false;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password);

        SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "Manager").show();
        Snackbar.make(getWindow().getDecorView().getRootView(), "Native", 3000).show();

        aQuery = new AQuery(getApplicationContext());

        findBtn = (Button) findViewById(R.id.findBtn);
        inputId = (EditText) findViewById(R.id.inputId);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputCode = (EditText) findViewById(R.id.inputCode);

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
                    params.put("id", inputId.getText().toString());
                    params.put("email", inputEmail.getText().toString());
                    aQuery.ajax("http://52.79.134.200/find/password/demand", params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status){
                            if(status.getCode() == 201){
                                emailDemanded = true;

                                inputId.setTextColor(ColorManager.successColor);
                                inputEmail.setTextColor(ColorManager.successColor);

                                findBtn.setText("인증");
                                ShowDialog();
                            } else {
                                inputId.setTextColor(ColorManager.failureColor);
                                inputEmail.setTextColor(ColorManager.failureColor);

                                SnackbarManager.createCancelableSnackbar(v, "일치하는 계정 정보가 없습니다.").show();
                            }
                        }
                    });
                } else {
                    params.put("code", inputCode.getText().toString());
                    aQuery.ajax("http://52.79.134.200/find/password/verify", params, String.class, new AjaxCallback<String>(){
                       @Override
                        public void callback(String url, String response, AjaxStatus status){
                           if(status.getCode() == 201){
                               inputCode.setTextColor(ColorManager.successColor);

                               SnackbarManager.createCancelableSnackbar(v, "임시 비밀번호가 " + inputEmail.getText().toString() + "로 전송되었습니다.").show();
                           } else {
                               inputCode.setTextColor(ColorManager.failureColor);
                           }
                       }
                    });
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

    private void PWDialog()
    {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.find_password_dialog, null);
        final Dialog myDialog = new Dialog(this);

        myDialog.setTitle("임시 비밀번호 발급");
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
