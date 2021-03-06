package com.daejeonpeople.activities.Introduction;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.ResultList.ResultList;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.AddressBookAdapter;
import com.daejeonpeople.adapter.WishlistAdapter;
import com.daejeonpeople.support.converter.CategoryConverter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.security.AES;
import com.daejeonpeople.valueobject.CulturalItem;
import com.daejeonpeople.valueobject.FriendListItem;
import com.daejeonpeople.valueobject.WishlistItem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jeong Minji on 2017-07-03.
 */

public class Introduction_Festival extends BaseActivity {

    private ImageButton btn_star, back_btn;
    private APIinterface apIinterface;
    private ImageView back_img;
    private TextView placename, call_inquiry, start_date, end_date, charge, spend_time, location;
    public boolean star = false;
    private int content_id;
    private Object value;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_festival);

        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        btn_star = (ImageButton)findViewById(R.id.star);

        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star =! star;

                if(star == false) {
                    btn_star.setImageResource(R.drawable.ic_star_border);
                } else {
                    btn_star.setImageResource(R.drawable.ic_star);
                }
            }

        });

        placename = (TextView)findViewById(R.id.placename);
        call_inquiry = (TextView)findViewById(R.id.call_inquiry);
        start_date = (TextView)findViewById(R.id.start_date);
        end_date = (TextView)findViewById(R.id.end_date);
        charge = (TextView)findViewById(R.id.charge);
        spend_time = (TextView)findViewById(R.id.spend_time);
        location = (TextView)findViewById(R.id.location);
        back_img = (ImageView)findViewById(R.id.intro_background);
        back_btn = (ImageButton)findViewById(R.id.backBtn);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Iterator<String> iter = b.keySet().iterator();
        while(iter.hasNext()) {
            key = iter.next();
            value = b.get(key);
            Log.d("TAG", "key : "+key+", value : " + value.toString());
        }

        apIinterface = APIClient.getClient().create(APIinterface.class);

        apIinterface.getDetail("UserSession=" + dbHelper.getCookie(), (Integer)value).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == 200) {
                    Log.d("Response", response.body() + "");

                    placename.setText(response.body().get("title").getAsString());
                    call_inquiry.setText(response.body().get("info_center").getAsString());
                    start_date.setText(response.body().get("start_date").getAsString());
                    end_date.setText(response.body().get("end_date").getAsString());
                    charge.setText(response.body().get("use_fee").getAsString());
                    call_inquiry.setText(response.body().get("info_center").getAsString());
                    spend_time.setText(response.body().get("spend_time").getAsString());
                    location.setText(response.body().get("address").getAsString());

                    Glide.with(getApplicationContext()).load(response.body().get("image").getAsString()).into(back_img);
                } else if(response.code() == 0) {
                    Log.d("Response", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    public void onBackBtnClicked(View view){
        finish();
    }
}
