package com.daejeonpeople.activities.Filter;

import java.util.ArrayList;

/**
 * Created by KimDongGyu on 2017-10-01.
 */

public class Filter_detail_Data_Factory {

    private ArrayList<Parents> parentsitem = new ArrayList<>();

    public Filter_detail_Data_Factory(String[][] array){
        for(int i = 0; i < array.length; i++){
            ArrayList<Children> childrenitem = new ArrayList<>();
            for(int j = 1; j < array[i].length; j++){
                Children children = new Children((String)array[i][j]);
                childrenitem.add(children);
            }
            Parents parents = new Parents((String)array[i][0], childrenitem);
            parentsitem.add(parents);
        }
    }

    public ArrayList<Parents> makeData() {
        return parentsitem;
    }

}
