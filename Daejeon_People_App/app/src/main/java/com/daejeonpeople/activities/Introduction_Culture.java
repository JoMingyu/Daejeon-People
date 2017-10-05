package com.daejeonpeople.activities;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.WishlistAdapter;
import com.daejeonpeople.support.converter.CategoryConverter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.valueobject.CulturalItem;
import com.daejeonpeople.valueobject.WishlistItem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jeong Minji on 2017-07-03.
 */

public class Introduction_Culture extends AppCompatActivity {

    private ImageButton btn_star;
    private APIinterface apIinterface;
    private ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_cultural);

        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        btn_star = (ImageButton)findViewById(R.id.star);
        TextView call_inquiry = (TextView) findViewById(R.id.call_inquiry);

        apIinterface = APIClient.getClient().create(APIinterface.class);

        back_img = (ImageView)findViewById(R.id.intro_background);
        back_img.setBackgroundResource(R.drawable.bg_culture);

        apIinterface.getDetail(125379).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == 201) {
                    Log.d("Response", response.body() + "");

                    response.body().get("address").getAsJsonArray();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
}
