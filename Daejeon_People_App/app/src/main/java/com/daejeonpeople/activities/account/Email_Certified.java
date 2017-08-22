package com.daejeonpeople.activities.account;

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
import com.daejeonpeople.activities.account.SignUp;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.views.ColorManager;
import com.daejeonpeople.support.views.SnackbarManager;
import com.daejeonpeople.valueobject.UserInSignup;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 민지
// Modified by JoMingyu

public class Email_Certified extends BaseActivity {
    private Intent intent;
    private APIinterface apiInterface;

    private EditText email;
    private Button confirmButton;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_certified);

        intent = new Intent(getApplicationContext(), SignUp.class);
        apiInterface = APIClient.getClient().create(APIinterface.class);

        confirmButton = (Button) findViewById(R.id.confirmBtn);
        email = (EditText) findViewById(R.id.email);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 이메일 EditText가 수정되면 다시 검은색이 되도록
                email.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
            if(email.getText().toString().isEmpty()) {
                SnackbarManager.createCancelableSnackbar(v, "이메일을 확인하세요.").show();
            } else {
                apiInterface.doSignUpDemandE(email.getText().toString()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 201){
                            email.setTextColor(ColorManager.successColor);
                            // 이메일 텍스트 컬러를 바꿈

                            ShowDialog();
                        } else {
                            email.setTextColor(ColorManager.failureColor);

                            SnackbarManager.createCancelableSnackbar(v, "이미 존재하는 이메일입니다.").show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
            }
        });
    }

    private void ShowDialog()
    {
        final LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.dialog_email_certified_input, null);
        final Dialog myDialog = new Dialog(this);

        myDialog.setTitle("이메일 인증");
        myDialog.setContentView(dialogLayout);
        myDialog.show();

        final Button okBtn = (Button) dialogLayout.findViewById(R.id.okBtn);
        final Button cancelBtn = (Button) dialogLayout.findViewById(R.id.cancelBtn);
        final EditText checkCode = (EditText) dialogLayout.findViewById(R.id.checkCode);

        checkCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCode.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        okBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
            apiInterface.doSignUpVerifyE(email.getText().toString(), checkCode.getText().toString()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 201){
                        intent.putExtra("email", email.getText().toString());
                        intent.putExtra("emailCertified", true);
                        // 인증 완료되었음을 표시

                        SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "이메일 인증 완료").show();
                        myDialog.cancel();
                        finish();
                        Intent intent = new Intent(getApplicationContext(), SignUp.class);
                        startActivity(intent);
                    } else {
                        intent.putExtra("emailCertified", false);

                        checkCode.setTextColor(ColorManager.failureColor);
                        SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "인증번호가 맞지 않습니다.").show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
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

