package com.daejeonpeople.activities.Category;

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


public class Category_detail_Adapter extends ExpandableRecyclerViewAdapter<CategoryParentsViewHolder, CategoryChildrenViewHolder> {

    public Category_detail_Adapter(ArrayList<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public CategoryParentsViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_detail_item, parent, false);
        return new CategoryParentsViewHolder(view);
    }

    @Override
    public CategoryChildrenViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_detail_sub_item, parent, false);
        return new CategoryChildrenViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(CategoryChildrenViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {

        final Category_Children categoryChildren = ((Category_Parents) group).getItems().get(childIndex);
        holder.setChildrenName(categoryChildren.getName());
        holder.setChildCode(categoryChildren.getCode());

    }

    @Override
    public void onBindGroupViewHolder(CategoryParentsViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.setParentsTitle(group);
    }
}
