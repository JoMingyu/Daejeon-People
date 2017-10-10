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
import com.daejeonpeople.activities.MapView;
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
import com.google.android.gms.maps.MapFragment;
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

public class Introduction_Accomodation extends BaseActivity {

    private ImageButton btn_star, back_btn;
    private APIinterface apIinterface;
    private ImageView back_img;
    private TextView placename, call_inquiry, checkin, checkout, benikia, goodstay, accomcount, location;
    public boolean star = false;
    private int content_id;
    private Object value;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_accomodation);

        getFragmentManager().beginTransaction().replace(R.id.placemap, new MapFragment());

        final DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

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

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apIinterface.addWish("UserSession=" + dbHelper.getCookie(), (Integer)value).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 201) {
                            Log.d("ResponseWish", "SUCCESS");


                        } else if(response.code() == 0) {
                            Log.d("ResponseWish", "FAIL");

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        placename = (TextView)findViewById(R.id.placename);
        call_inquiry = (TextView)findViewById(R.id.call_inquiry);
        checkin = (TextView)findViewById(R.id.checkin);
        checkout = (TextView)findViewById(R.id.checkout);
        benikia = (TextView)findViewById(R.id.benikia);
        goodstay = (TextView)findViewById(R.id.goodstay);
        accomcount = (TextView)findViewById(R.id.accomcount);
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
                    checkin.setText(response.body().get("checkin_time").getAsString());
                    checkout.setText(response.body().get("checkout_time").getAsString());
                    benikia.setText(response.body().get("benikia").getAsString());
                    goodstay.setText(response.body().get("goodstay").getAsString());
                    accomcount.setText(response.body().get("accomcount").getAsString());
                    location.setText(response.body().get("address").getAsString());

                    if(response.body().get("image").getAsString().equals("정보 없음")) {
                        back_img.setImageResource(R.drawable.no_image_black);
                    } else {
                        Glide.with(getApplicationContext()).load(response.body().get("image").getAsString()).into(back_img);
                    }
                } else if(response.code() == 0) {
                    Log.d("Response", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void onBackBtnClicked(View view){
        finish();
    }
}
