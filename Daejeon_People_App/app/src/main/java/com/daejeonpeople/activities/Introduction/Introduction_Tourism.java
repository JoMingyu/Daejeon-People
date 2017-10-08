package com.daejeonpeople.activities.Introduction;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.daejeonpeople.R;

public class Introduction_Tourism extends AppCompatActivity {

    private ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_tourism);

        back_img = (ImageView)findViewById(R.id.intro_background);
        back_img.setBackgroundResource(R.drawable.bg_tourism);
    }
}
