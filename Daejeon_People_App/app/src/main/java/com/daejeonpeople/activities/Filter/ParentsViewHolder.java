package com.daejeonpeople.activities.Filter;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class ParentsViewHolder extends GroupViewHolder {

  private TextView parentsName;
  private ImageView arrow;

  public ParentsViewHolder(View itemView) {
    super(itemView);
    parentsName = (TextView) itemView.findViewById(R.id.item_name);
    arrow = (ImageView) itemView.findViewById(R.id.item_arrow);
  }

  public void setParentsTitle(ExpandableGroup parents) {
    if (parents instanceof Parents) {
      parentsName.setText(parents.getTitle());
    }
}

  @Override
  public void expand() {
  animateExpand();
  }

  @Override
  public void collapse() {
  animateCollapse();
  }

  private void animateExpand() {
  RotateAnimation rotate =
      new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
  rotate.setDuration(300);
  rotate.setFillAfter(true);
  arrow.setAnimation(rotate);
  }

  private void animateCollapse() {
  RotateAnimation rotate =
      new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
  rotate.setDuration(300);
  rotate.setFillAfter(true);
  arrow.setAnimation(rotate);
  }
}
