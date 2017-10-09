package com.daejeonpeople;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.daejeonpeople.support.converter.CategoryConverter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test extends AppCompatActivity {
    Button btn1;
    Button btn2;
    APIinterface apIinterface;
    DBHelper dbHelper;
    CategoryConverter categoryConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        apIinterface = APIClient.getClient().create(APIinterface.class);
        dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
        categoryConverter = new CategoryConverter();

        btn1 = (Button)findViewById(R.id.btn_one);
        btn2 = (Button)findViewById(R.id.btn_two);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apIinterface.getAttractionsList("UserSession="+dbHelper.getCookie(),
                        1, 1, 1).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("response", "SUCCESS");

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("response", "FAIL");

                    }
                });
            }
        });
    }
}
