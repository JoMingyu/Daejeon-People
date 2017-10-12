package com.daejeonpeople.valueobject;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class WishlistItem {
    private String title;
    private String address;
    private String back_image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBack_image() {
        return back_image;
    }

    public void setBack_image(String back_image) {
        this.back_image = back_image;
    }
}
