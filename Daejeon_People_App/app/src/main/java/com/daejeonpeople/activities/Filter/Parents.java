package com.daejeonpeople.activities.Filter;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;

public class Parents extends ExpandableGroup<Children> {
  public Parents(String title, ArrayList<Children> items) {
    super(title, items);
  }
}

