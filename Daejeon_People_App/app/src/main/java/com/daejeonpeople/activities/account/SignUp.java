package com.daejeonpeople.activities.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.firebase.Firebase;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.views.ColorManager;
import com.daejeonpeople.support.views.SnackbarManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// 민지
// Modified by JoMingyu

public class SignUp extends BaseActivity {
    private APIinterface apIinterface;
    private Firebase firebase;
    private Intent intent;

    private Button submitBtn, emailCertifiedBtn;
    private EditText userName, userId, userPassword, passwordConfirm;
    private TextView jumpToSignin;
    private ImageView background;

    private boolean nameCheck = false;
    private boolean idCheck = false;
    private boolean passwordConfirmCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        intent = getIntent();
        apIinterface = APIClient.getClient().create(APIinterface.class);
        firebase = new Firebase();

        submitBtn = (Button) findViewById(R.id.submitButton);
        emailCertifiedBtn = (Button) findViewById(R.id.emailButton);

        userName = (EditText) findViewById(R.id.inputName);
        userId = (EditText) findViewById(R.id.inputId);
        userPassword = (EditText) findViewById(R.id.inputPassword);
        passwordConfirm = (EditText) findViewById(R.id.inputPasswordConfirm);
        background = (ImageView) findViewById(R.id.background);

        Glide.with(getApplicationContext()).load(R.drawable.background).centerCrop().into(background);

        userPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        userPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        passwordConfirm.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordConfirm.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        jumpToSignin = (TextView) findViewById(R.id.jumpToSignin);

        if(intent.getBooleanExtra("emailCertified", false) && intent != null) {
            // 이메일 인증이 완료됐다면 버튼 컬러 변경
            emailCertifiedBtn.setTextColor(ColorManager.successColor);
            SnackbarManager.createCancelableSnackbar(emailCertifiedBtn, "이메일 인증 완료").show();
        }

        emailCertifiedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인증 액티비티로 분기
                startActivity(new Intent(getApplicationContext(), Email_Certified.class));
            }
        });

        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !userName.getText().toString().isEmpty()) {
                    // 이름이 1글자 이상일 때. 명시적
                    userName.setTextColor(ColorManager.successColor);
                    nameCheck = true;
                } else {
                    // 포커스가 다시 바뀌는 경우, 이름이 비어있는 경우를 방지
                    nameCheck = false;
                }
            }
        });

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        userId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Focus가 넘어갈 때
                    if(!userId.getText().toString().isEmpty()) {
                        // EditText가 비어있지 않을 때
                        apIinterface.doIDcheck(userId.getText().toString()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code() == 201){
                                    userId.setTextColor(ColorManager.successColor);
                                    idCheck = true;
                                } else {
                                    userId.setTextColor(ColorManager.failureColor);
                                    idCheck = false;
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    } else {
                        // EditText가 비어있을 때
                        idCheck = false;
                    }
                } else {
                    // Focus가 넘어올 때
                }
            }
        });

        passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String confirm = s.toString();

                if (userPassword.getText().toString().equals(confirm)) {
                    userPassword.setTextColor(ColorManager.successColor);
                    passwordConfirm.setTextColor(ColorManager.successColor);
                    passwordConfirmCheck = true;
                } else {
                    passwordConfirm.setTextColor(ColorManager.failureColor);
                    passwordConfirmCheck = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                intent = getIntent();
                Log.d("emailCertified", intent.getBooleanExtra("emailCertified", false)+"");
                if (intent.getBooleanExtra("emailCertified", false) && nameCheck && idCheck && passwordConfirmCheck) {
                    apIinterface.doSignUp(
                            userId.getText().toString(),
                            userPassword.getText().toString(),
                            intent.getStringExtra("email"),
                            userName.getText().toString(),
                            firebase.getFirebaseToken()
                    ).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code() == 201){
                                SnackbarManager.createCancelableSnackbar(v, "회원가입 성공!").show();
                                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(intent);
                            } else {
                                SnackbarManager.createCancelableSnackbar(v, "회원가입에 실패했습니다.").show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                } else {
                    SnackbarManager.createCancelableSnackbar(v, "회원가입에 실패했습니다.").show();
                }
            }
        });

        jumpToSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
            }
        });
    }


    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }
            public char charAt(int index) {
                return '●'; // This is the important part
            }
            public int length() {
                return mSource.length(); // Return default
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }
}
