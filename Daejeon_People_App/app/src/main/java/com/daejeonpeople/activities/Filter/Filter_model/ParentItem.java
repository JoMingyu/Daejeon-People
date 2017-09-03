package com.daejeonpeople.activities.Filter.Filter_model;

import java.util.ArrayList;

/**
 * Created by KimDongGyu on 2017-09-01.
 */

public class ParentItem extends Item{
    public boolean visibilityOfChildItems = true;
    public ArrayList<ChildItem> unvisibleChildItems = new ArrayList<>();

    public ParentItem(String name, int viewType){
        this.name = name;
        this.viewType = viewType;
    }

}
