package com.daejeonpeople.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import com.daejeonpeople.R;

/**
 * Created by geni on 2017. 5. 28..
 */
//동규

public class Category extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        final ActionBar category = getActionBar();
        category.setCustomView(R.layout.custom_category);
        category.setDisplayShowTitleEnabled(false);
        category.setDisplayShowCustomEnabled(true);
        category.setDisplayShowHomeEnabled(false);
    }
}
