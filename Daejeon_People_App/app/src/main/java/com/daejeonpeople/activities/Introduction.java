package com.daejeonpeople.activities;

import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;

        import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;

/**
 * Created by Jeong Minji on 2017-07-03.
 */

public class Introduction extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_form);
    }
}
