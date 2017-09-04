package com.daejeonpeople.valueobject;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class WishlistItem {
    private String date;
    private String title;
    private String address;
    private int love;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }
}
