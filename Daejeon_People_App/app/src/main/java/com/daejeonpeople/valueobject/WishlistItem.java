package com.daejeonpeople.valueobject;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class WishlistItem {
    private int content_id;
    private String title;
    private String address;
    private String back_image;
    private int content_type_id;

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public int getContent_type_id() {
        return content_type_id;
    }

    public void setContent_type_id(int content_type_id) {
        this.content_type_id = content_type_id;
    }

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
