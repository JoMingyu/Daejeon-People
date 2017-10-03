package com.daejeonpeople.activities.Filter;

import android.content.Intent;
import android.support.v4.app.SupportActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.Detail.Detail;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ChildrenViewHolder extends ChildViewHolder {

  private TextView childTextView;
  private Button childButton;
  private String childCode;

  public ChildrenViewHolder(final View itemView) {
    super(itemView);
    childTextView = (TextView) itemView.findViewById(R.id.subitem_name);
    childButton = (Button) itemView.findViewById(R.id.filter_detail_subitem_btn);

    childButton.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), Detail.class);
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
