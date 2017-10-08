package com.daejeonpeople.activities.Category;

import java.util.ArrayList;

/**
 * Created by KimDongGyu on 2017-10-01.
 */

public class Category_detail_Data_Factory {

    private ArrayList<Category_Parents> parentsitem = new ArrayList<>();

    public Category_detail_Data_Factory(String[][] array, String[][] codeArray, String str){
        for(int i = 0; i < array.length; i++){
            ArrayList<Category_Children> childrenitem = new ArrayList<>();
            for(int j = 1; j < array[i].length; j++){
                Category_Children categoryChildren = new Category_Children((String)array[i][j], (String)codeArray[i][j], str);
                childrenitem.add(categoryChildren);
            }
            Category_Parents categoryParents = new Category_Parents((String)array[i][0], childrenitem);
            parentsitem.add(categoryParents);
        }
    }

    public ArrayList<Category_Parents> makeData() {
        return parentsitem;
    }

}
