package com.daejeonpeople.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geni on 2017. 5. 28..
 */
//근철

public class FindID extends Activity {
    private AQuery aQuery;
    private EditText inputEmail;
    private Button findBtn;
    private Map<String, String> params = new HashMap<>();
    private boolean emailDemanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id);
        inputEmail = (EditText)findViewById(R.id.inputEmail);
        findBtn = (Button)findViewById(R.id.findBtn);
        aQuery = new AQuery(getApplicationContext());

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailDemanded){
                    params.put("email", inputEmail.getText().toString());
                    aQuery.ajax("http://52.79.134.200/find/id/demand", params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status){
                            if(status.getCode() == 201){
                                ShowDialog();
                                emailDemanded = true;
                            }
                        }
                    });
                } else {
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
