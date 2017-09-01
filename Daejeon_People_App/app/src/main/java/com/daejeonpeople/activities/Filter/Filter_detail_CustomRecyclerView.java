package com.daejeonpeople.activities.Filter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by KimDongGyu on 2017-09-01.
 */

public class Filter_detail_CustomRecyclerView extends RecyclerView {

    public Filter_detail_CustomRecyclerView(Context context) {
        super(context);
        setItemAnimator(new Filter_detail_ItemAnimator());
    }

    public Filter_detail_CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setItemAnimator(new Filter_detail_ItemAnimator());
    }

    public Filter_detail_CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setItemAnimator(new Filter_detail_ItemAnimator());
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }
}
