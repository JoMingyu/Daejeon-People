package com.daejeonpeople.activities.Category;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;

public class Category_Parents extends ExpandableGroup<Category_Children> {
  public Category_Parents(String title, ArrayList<Category_Children> items) {
    super(title, items);
  }
}

