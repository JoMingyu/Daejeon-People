package com.daejeonpeople.activities.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Main;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.views.SnackbarManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 10102김동규 on 2017-05-11.
 */
// Modified by JoMingyu

public class SignIn extends BaseActivity {
    private APIinterface apiInterface;
    private DBHelper dbHelper;

    private Button submitBtn;
    private EditText userId, userPassword;
    private TextView signUpView, findIdView, findPasswordView;
    private ImageView background;

    private boolean needFinish;

    @Override
    protected void onPause() {
        super.onPause();
        if(needFinish) {
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        apiInterface = APIClient.getClient().create(APIinterface.class);
        dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
        needFinish = false;

        signUpView = (TextView) findViewById(R.id.signUpView);
        findIdView = (TextView) findViewById(R.id.findIdView);
        findPasswordView = (TextView) findViewById(R.id.findPasswordView);
        background = (ImageView) findViewById(R.id.background);

        Glide.with(getApplicationContext()).load(R.drawable.phone_certified_background).centerCrop().into(background);

        submitBtn = (Button) findViewById(R.id.okBtn);
        userId = (EditText) findViewById(R.id.inputId);
        userPassword = (EditText) findViewById(R.id.inputPw);

        userPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        userPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        signUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        findIdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindID.class);
                startActivity(intent);
            }
        });

        findPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindPW.class);
                startActivity(intent);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String id = userId.getText().toString();
                String password = userPassword.getText().toString();

                if(!id.isEmpty() && !password.isEmpty()) {
                    doSignIn(id, password);
                } else {
                    SnackbarManager.createCancelableSnackbar(v, "아이디나 비밀번호를 확인하세요.").show();
                }
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


    public void doSignIn(String id, String password){
        apiInterface.doSignIn(id, password).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201){
                    needFinish = true;
                    startActivity(new Intent(getApplicationContext(), Main.class));
                    dbHelper.setCookie(response.headers().get("Set-Cookie"));
                    Log.d("getCookie", dbHelper.getCookie());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}

