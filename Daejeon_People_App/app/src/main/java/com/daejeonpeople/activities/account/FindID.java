package com.daejeonpeople.activities.account;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.security.AES;
import com.daejeonpeople.support.views.ColorManager;
import com.daejeonpeople.support.views.SnackbarManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geni on 2017. 5. 28..
 */
// 근철
// Modified by JoMingyu

public class FindID extends BaseActivity {
    private AQuery aQuery;

    private Button findBtn;
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
        setContentView(R.layout.find_id);

        aQuery = new AQuery(getApplicationContext());

        findBtn = (Button)findViewById(R.id.findBtn);
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

                                SnackbarManager.createCancelableSnackbar(v, "일치하는 계정 정보가 없습니다.").show();
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
                                    ShowIdDialog(id);
                                } catch(JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                inputCode.setTextColor(ColorManager.failureColor);
                            }
                        }
                    });
                    //전송된 id 확인할 레이아웃 제작 필요 - 민지
                    //근철이 제작했다. - 민지
                }
            }
        });
    }

    private void ShowDialog()
    {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.dialog_email_certified_sended, null);
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

    //성공시 찾은 ID를 보여주는 Dialog
    private void ShowIdDialog(String id){
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.dialog_find_id_certified, null);
        final Dialog mShowIdDialog = new Dialog(this);

        mShowIdDialog.setTitle("아이디 찾기 성공");
        mShowIdDialog.setContentView(dialogLayout);
        mShowIdDialog.show();

        EditText findIdResult = (EditText)dialogLayout.findViewById(R.id.findIdResult);
        Button okBtn = (Button)dialogLayout.findViewById(R.id.okBtn);
        Button cancelBtn = (Button)dialogLayout.findViewById(R.id.cancelBtn);

        findIdResult.setEnabled(false);
        findIdResult.setText(id);

        okBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mShowIdDialog.cancel();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mShowIdDialog.cancel();
            }
        });
    }

    //Dialog내에서 요청처리할건가요?

//    private void ShowDialog()
//    {
//        LayoutInflater dialog = LayoutInflater.from(this);
//        final View dialogLayout = dialog.inflate(R.layout.dialog_find_id_input, null);
//        final Dialog myDialog = new Dialog(this);
//
//        Button okBtn = (Button)dialogLayout.findViewById(R.id.okBtn);
//        final EditText inputCode = (EditText)dialogLayout.findViewById(R.id.input_code);
//        Button cancelBtn = (Button)dialogLayout.findViewById(R.id.cancelBtn);
//
//        myDialog.setTitle("인증코드 입력");
//        myDialog.setContentView(dialogLayout);
//        myDialog.show();
//
//        okBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                params.put("email", inputEmail.getText().toString());
//                params.put("code", inputCode.getText().toString());
//                aQuery.ajax("http://52.79.134.200/find/id/verify", params, String.class, new AjaxCallback<String>(){
//                    @Override
//                    public void callback(String url, String object, AjaxStatus status) {
//                        Log.d("Find id Status Code", status.getCode()+"");
//                    }
//                });
//            }
//        });
//
//        cancelBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                myDialog.cancel();
//            }
//        });
//    }
}
