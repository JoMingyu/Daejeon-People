package com.daejeonpeople.activities.Category;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.ResultList.ResultList;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class CategoryChildrenViewHolder extends ChildViewHolder {

  private TextView childTextView;
  private Button childButton;
  private String childCode;

  public CategoryChildrenViewHolder(final View itemView) {
    super(itemView);
    childTextView = (TextView) itemView.findViewById(R.id.subitem_name);
    childButton = (Button) itemView.findViewById(R.id.filter_detail_subitem_btn);

    childButton.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ResultList.class);
        intent.putExtra("category", childCode);
        view.getContext().startActivity(intent);
      }
    }) ;
  }

  public void setChildrenName(String name) {
    childTextView.setText(name);
  }

  public void setChildCode(String childCode) {
    this.childCode = childCode;
  }
}
