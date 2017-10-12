package com.daejeonpeople.activities.Category;

import android.os.Parcel;
import android.os.Parcelable;

public class Category_Children implements Parcelable {

  private String name;

  private String childCode;

  private String str;

  public Category_Children(String name, String childCode, String str) {
    this.name = name;
    this.childCode = childCode;
    this.str = str;
  }

  protected Category_Children(Parcel in) {
    name = in.readString();
  }

  public String getName() {
    return name;
  }

  public String getCode() {return childCode;}

  public String getStr() {return str;}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Category_Children)) return false;

    Category_Children categoryChildren = (Category_Children) o;

    return getName() != null ? getName().equals(categoryChildren.getName()) : categoryChildren.getName() == null;

  }

  @Override
  public int hashCode() {
    int result = getName() != null ? getName().hashCode() : 0;
    return result;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Category_Children> CREATOR = new Creator<Category_Children>() {
    @Override
    public Category_Children createFromParcel(Parcel in) {
      return new Category_Children(in);
    }

    @Override
    public Category_Children[] newArray(int size) {
      return new Category_Children[size];
    }
  };
}

