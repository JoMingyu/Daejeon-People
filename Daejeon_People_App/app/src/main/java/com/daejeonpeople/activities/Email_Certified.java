package com.daejeonpeople.activities;

import android.app.Dialog;
import android.content.Intent;
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
import com.daejeonpeople.valueobject.UserInSignup;

import java.util.HashMap;
import java.util.Map;

// 민지
// Modified by JoMingyu

public class Email_Certified extends AppCompatActivity {
    private AQuery aQuery;
    private Map<String, Object> params = new HashMap<>();

    private EditText email;
    private EditText checkCode;
    private Button confirmButton;

    private int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_certified);

        confirmButton = (Button) findViewById(R.id.confirmBtn);
        email = (EditText) findViewById(R.id.email);
        checkCode = (EditText) findViewById (R.id.checkCode);

        UserInSignup.emailDemanded = false;
        // 액티비티 로드마다 false

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 이메일 EditText가 수정되면 발급으로 다시 되도록
                email.setTextColor(Color.BLACK);
                UserInSignup.emailDemanded = false;
                confirmButton.setText("발급");
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v)
            {
                if(!UserInSignup.emailDemanded){
                    // 버튼이 눌렸을 때 이메일 인증번호가 전송되지 않은 상태라면

                    params.put("email", email.getText().toString());
                    aQuery = new AQuery(getApplicationContext());
                    aQuery.ajax("http://52.79.134.200/signup/email/demand", params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status) {
                            statusCode = status.getCode();
                            if(statusCode == 201){
                                UserInSignup.emailDemanded = true;
                                // 이메일 인증 번호가 전송되었음을 표시

                                email.setTextColor(ColorManager.successColor);
                                // 이메일 텍스트 컬러를 바꿈

                                confirmButton.setText("인증");
                                ShowDialog();
                            } else {
                                UserInSignup.emailDemanded = false;
                                email.setTextColor(ColorManager.failureColor);
                                SnackbarManager.createCancelableSnackbar(v, "이미 존재하는 이메일입니다.", 3000).show();
                            }
                        }
                    });
                } else {
                    // 전송된 상태라면
                    params.put("code",checkCode.getText().toString());
                    aQuery.ajax("http://52.79.134.200/signup/email/verify", params, String.class, new AjaxCallback<String>(){
                       @Override
                        public void callback(String url, String response, AjaxStatus status){
                            if(status.getCode() == 201){
                                UserInSignup.email = email.getText().toString();
                                UserInSignup.emailCertified = true;
                                // 인증 완료되었음을 표시

                                checkCode.setTextColor(ColorManager.successColor);
                                finish();
                                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                                startActivity(intent);
                            } else {
                                UserInSignup.emailCertified = false;

                                checkCode.setTextColor(ColorManager.failureColor);
                                SnackbarManager.createCancelableSnackbar(v, "인증번호가 맞지 않습니다.", 3000).show();
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

        Button btn_ok = (Button)dialogLayout.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button)dialogLayout.findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDialog.cancel();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDialog.cancel();
            }
        });
    }
}

