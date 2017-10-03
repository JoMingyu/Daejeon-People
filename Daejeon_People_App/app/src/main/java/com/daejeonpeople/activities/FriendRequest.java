package com.daejeonpeople.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendRequest extends BaseActivity {
    private EditText destination;
    private Button requestBtn;
    private RecyclerView requestList;
    private APIinterface apIinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_request_listview);

        destination=(EditText)findViewById(R.id.destination);
        requestBtn=(Button)findViewById(R.id.requestBtn);
        requestList=(RecyclerView)findViewById(R.id.requestList);

        apIinterface= APIClient.getClient().create(APIinterface.class);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(destination.getText().length() != 0){
                    apIinterface.friendRequest(destination.getText().toString()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("response", response.code()+"");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
    }
}
