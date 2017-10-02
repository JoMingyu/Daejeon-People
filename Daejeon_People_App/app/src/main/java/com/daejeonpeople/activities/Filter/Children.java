package com.daejeonpeople.activities.Filter;

import android.os.Parcel;
import android.os.Parcelable;

public class Children implements Parcelable {

  private String name;

  public Children(String name) {
    this.name = name;
  }

  protected Children(Parcel in) {
    name = in.readString();
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Children)) return false;

    Children children = (Children) o;

    return getName() != null ? getName().equals(children.getName()) : children.getName() == null;

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

  public static final Creator<Children> CREATOR = new Creator<Children>() {
    @Override
    public Children createFromParcel(Parcel in) {
      return new Children(in);
    }

    @Override
    public Children[] newArray(int size) {
      return new Children[size];
    }
  };
}

