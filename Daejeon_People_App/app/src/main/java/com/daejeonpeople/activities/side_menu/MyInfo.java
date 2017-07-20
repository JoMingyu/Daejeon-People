package com.daejeonpeople.activities.side_menu;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.daejeonpeople.R;

/**
 * Created by dsm2016 on 2017-07-20.
 */

public class MyInfo extends Activity {
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info);
    }
}
