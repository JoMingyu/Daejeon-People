package com.daejeonpeople.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.connection.connectionValues;
import com.daejeonpeople.support.network.SessionManager;

import org.apache.http.cookie.Cookie;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10102김동규 on 2017-05-11.
 */
// Modified by JoMingyu

public class SignIn extends Activity{
    private AQuery aQuery;

    private Button submitBtn;
    private EditText userId;
    private EditText userPassword;
//    private CheckBox keepLoginBox;

    private TextView signUpView;
    private TextView findIdView;
    private TextView findPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        Map<String, Object> params = new HashMap<>();

        submitBtn = (Button) findViewById(R.id.signinSubmit);
        userId = (EditText) findViewById(R.id.userId);
        userPassword = (EditText) findViewById(R.id.userPassword);

        signUpView = (TextView) findViewById(R.id.signUp);
        findIdView = (TextView) findViewById(R.id.findId);
        findPasswordView = (TextView) findViewById(R.id.findPassword);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aQuery = new AQuery(getApplicationContext());

                String id = userId.getText().toString();
                String password = userPassword.getText().toString();
//                boolean keepLogin = keepLoginBox.isChecked();
                boolean keepLogin = true;

                if(!id.isEmpty() && !password.isEmpty()) {
                    Map<String, Object> params = new HashMap<>();

                    params.put("id", id);
                    params.put("password", password);
                    params.put("keep_login", keepLogin);

                    aQuery.ajax("http://52.79.134.200/signin", params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status){
                            String cookie = new SessionManager(status).detectCookie("UserSession");
                            int statusCode = status.getCode();
                            if(statusCode == 201) {
                                finish();
                                Intent intent = new Intent(getApplicationContext(), Main.class);
                                startActivity(intent);
                            } else {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "아이디나 비밀번호를 확인하세요.", 3000).show();
                            }
                        }
                    });
                } else {
                    Snackbar.make(v, "로그인 성공", 3000).show();
                }
            }
        });
    }
}
