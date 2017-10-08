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
    private ImageView back_img, card, carriage, pet;
    private TextView placename, call_inquiry, checkin, checkout, benekia, goodstay, roomcount, location;
    public boolean star = false;
    private int content_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_cultural);

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
        location = (TextView)findViewById(R.id.location);
        card = (ImageView)findViewById(R.id.card);
        carriage = (ImageView)findViewById(R.id.carriage);
        pet = (ImageView)findViewById(R.id.pet);
        back_img = (ImageView)findViewById(R.id.intro_background);
        back_btn = (ImageButton)findViewById(R.id.backBtn);

        apIinterface = APIClient.getClient().create(APIinterface.class);

        apIinterface.getDetail("UserSession=" + dbHelper.getCookie(), 129723).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == 200) {
                    Log.d("Response", response.body() + "");

                    placename.setText(response.body().get("title").getAsString());
                    call_inquiry.setText(response.body().get("info_center").getAsString());

                    location.setText(response.body().get("address").getAsString());

                    if(response.body().get("credit_card").getAsString().equals("없음")) {
                        card.setImageResource(R.drawable.ic_g_card);
                    } else {
                        card.setImageResource(R.drawable.ic_w_card);
                    }

                    if(response.body().get("baby_carriage").getAsString().equals("없음")) {
                        carriage.setImageResource(R.drawable.ic_g_carriage);
                    } else {
                        carriage.setImageResource(R.drawable.ic_w_carriage);
                    }

                    if(response.body().get("pet").getAsString().equals("없음")) {
                        pet.setImageResource(R.drawable.ic_g_pet);
                    } else {
                        pet.setImageResource(R.drawable.ic_w_pet);
                    }

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
