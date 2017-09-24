package com.daejeonpeople.activities.account;

import android.app.Dialog;
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
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.views.ColorManager;
import com.daejeonpeople.support.views.SnackbarManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 5. 28.
 */

// 근철
// Modified by JoMingyu

public class FindPW extends BaseActivity {
    private APIinterface apiInterface;

    private Button findBtn;
    private EditText inputId;
    private EditText inputEmail;
    private boolean emailDemanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_pw);

        apiInterface = APIClient.getClient().create(APIinterface.class);

        findBtn = (Button) findViewById(R.id.findBtn);
        inputId = (EditText) findViewById(R.id.inputId);
        inputEmail = (EditText) findViewById(R.id.inputEmail);

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
                    apiInterface.doFindIdDemand(inputId.getText().toString(),
                                                inputEmail.getText().toString()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code() == 201){
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

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                } else {
//                    apiInterface.doFindIdVerify(inputEmail.getText().toString(),
//                                                inputCode.getText().toString()).enqueue(new Callback<Void>() {
//                        @Override
//                        public void onResponse(Call<Void> call, Response<Void> response) {
//                            if(response.code() == 201){
//                                inputCode.setTextColor(ColorManager.successColor);
//                                SnackbarManager.createCancelableSnackbar(v, "임시 비밀번호가 " + inputEmail.getText().toString() + "로 전송되었습니다.").show();
//                            } else {
//                                inputCode.setTextColor(ColorManager.failureColor);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Void> call, Throwable t) {
//
//                        }
//                    });
                }
            }
        });
    }

    private void ShowDialog()
    {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.dialog_email_certified_input, null);
        final Dialog myDialog = new Dialog(this);

        myDialog.setTitle("이메일 인증");
        myDialog.setContentView(dialogLayout);
        myDialog.show();

        Button okBtn = (Button)dialogLayout.findViewById(R.id.okBtn);
        Button cancelBtn = (Button)dialogLayout.findViewById(R.id.cancelBtn);
        final EditText checkCode = (EditText)dialogLayout.findViewById(R.id.checkCode);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                apiInterface.doFindIdVerify(inputEmail.getText().toString(),
                                            checkCode.getText().toString()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 201){
                            checkCode.setTextColor(ColorManager.successColor);
                            SnackbarManager.createCancelableSnackbar(v, "임시 비밀번호가 " + inputEmail.getText().toString() + "로 전송되었습니다.").show();
                        } else {
                            checkCode.setTextColor(ColorManager.failureColor);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
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
        final View dialogLayout = dialog.inflate(R.layout.dialog_find_pw_input, null);
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
