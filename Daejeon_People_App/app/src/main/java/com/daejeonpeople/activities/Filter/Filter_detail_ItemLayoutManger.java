package com.daejeonpeople.activities.Filter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by KimDongGyu on 2017-09-01.
 */

public class Filter_detail_ItemLayoutManger  extends LinearLayoutManager {

    public Filter_detail_ItemLayoutManger(Context context) {
        super(context);
    }

    public Filter_detail_ItemLayoutManger(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public Filter_detail_ItemLayoutManger(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }


    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
    }

    @Override
    public void scrollToPositionWithOffset(int position, int offset) {
        super.scrollToPositionWithOffset(position, offset);

    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }
}
