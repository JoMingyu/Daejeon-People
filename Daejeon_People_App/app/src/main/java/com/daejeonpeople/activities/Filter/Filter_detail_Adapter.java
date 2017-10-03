package com.daejeonpeople.activities.Filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daejeonpeople.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;

/**
 * Created by KimDongGyu on 2017-10-02.
 */


public class Filter_detail_Adapter extends ExpandableRecyclerViewAdapter<ParentsViewHolder, ChildrenViewHolder> {

    public Filter_detail_Adapter(ArrayList<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ParentsViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_detail_item, parent, false);
        return new ParentsViewHolder(view);
    }

    @Override
    public ChildrenViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_detail_sub_item, parent, false);
        return new ChildrenViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildrenViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {

        final Children children = ((Parents) group).getItems().get(childIndex);
        holder.setChildrenName(children.getName());
        holder.setChildCode(children.getCode());

    }

    @Override
    public void onBindGroupViewHolder(ParentsViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.setParentsTitle(group);
    }
}
