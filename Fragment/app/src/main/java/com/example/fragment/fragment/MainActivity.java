package com.example.fragment.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout II_fragment;
    TextView tv_activity;
    Button btn_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        II_fragment = (LinearLayout) findViewById(R.id.ll_fragment);
        tv_activity = (TextView) findViewById(R.id.tv_activity);
        btn_activity = (Button) findViewById(R.id.btn_activity);

        btn_activity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainFragment mainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.ll_fragment);
                mainFragment.changeFragmentTextView("호호호");
            }
        });
        replaeFragment();
    }

    public void replaeFragment() {
        MainFragment mainFragment = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll_fragment, mainFragment);
        fragmentTransaction.commit();
    }

    public void changeText(String text)
    {
        tv_activity.setText(text);
    }


}
