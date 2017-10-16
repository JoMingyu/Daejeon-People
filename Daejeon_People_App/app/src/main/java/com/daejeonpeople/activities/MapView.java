package com.daejeonpeople.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.mChatting.mChatting;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 10. 1..
 */

public class MapView extends FragmentActivity implements OnMapReadyCallback {
    private UiSettings mUiSettings;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private TextView select;
    private Intent mIntent;
    private APIinterface apiInterface;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);
        select = (TextView)findViewById(R.id.select);
        apiInterface = APIClient.getClient().create(APIinterface.class);
        dbHelper=DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
        mIntent = getIntent();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mUiSettings = googleMap.getUiSettings();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(36.338193, 127.393331)));
        final double[][] LatLngs = new double[20][];
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(127.3457377686, 36.366626963)));
        apiInterface.getMapData("UserSession="+dbHelper.getCookie(), mIntent.getStringExtra("topic")).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                for(int i=0; i<response.body().size(); i++){
                    JsonObject result = response.body().get(i).getAsJsonObject();
                    LatLng location = new LatLng(result.get("mapx").getAsDouble(), result.get("mapy").getAsDouble());
                    Log.d("googlemap", googleMap+"");
                    Log.d("type", Double.parseDouble(result.get("mapx").toString())+"");
                    double x = result.get("mapx").getAsDouble();
                    double y = result.get("mapy").getAsDouble();
                    Log.d("x", x+"");
                    Log.d("y", y+"");
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(x, y)));
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Log.d("a map", googleMap+"");
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            Toast.makeText(getApplicationContext(), "위치정보제공동의가 필요합니다.", Toast.LENGTH_SHORT).show();
        }
        mUiSettings.setZoomControlsEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("onRequestPermissions", "called");
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            Log.d("onRequestPermissions", "success");
            Log.d("boolean", (permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION)+"");
            Log.d("boolean", permissions[0]+"");
            if (permissions.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("onRequestPermissions", "success");
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "위치정보제공동의가 되었습니다.", Toast.LENGTH_SHORT).show();
//                            .setMyLocationEnabled(true);
                        } else {
                            // Show rationale and request permission.
                            Toast.makeText(getApplicationContext(), "위치정보제공동의가 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
            } else {
                // Permission was denied. Display an error message.
                Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
    }

    public void mapSet(GoogleMap googleMap, double[][] dataSet){
        for(int i=0; i<dataSet.length; i++){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(dataSet[i][0], dataSet[i][1])));
        }
    }

    public void onBackBtnClicked(View view){
        Intent intent = new Intent(getApplicationContext(), mChatting.class);
        intent.putExtra("topic", mIntent.getStringExtra("topic"));
        startActivity(intent);
        finish();
    }
}
