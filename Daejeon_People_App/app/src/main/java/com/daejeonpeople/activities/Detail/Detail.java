package com.daejeonpeople.activities.Detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daejeonpeople.R;

import java.util.ArrayList;

/**
 * Created by KimDongGyu on 2017-09-26.
 */

public class Detail extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<DetailData> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        mRecyclerView = (RecyclerView) findViewById(R.id.detail_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        mAdapter = new DetailAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        myDataset.add(new DetailData("#InsideOut", R.drawable.bg_accommodation));
        myDataset.add(new DetailData("#Mini", R.drawable.bg_shopping));
        myDataset.add(new DetailData("#ToyStroy", R.drawable.bg_culture));

    }
}