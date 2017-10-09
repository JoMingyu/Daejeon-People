package com.daejeonpeople.valueobject;

import java.util.ArrayList;

/**
 * Created by KimDongGyu on 2017-09-19.
 */

public class MainItemMonthly {
    private boolean wish;
    private String image;
    private String address;
    private int content_id;
    private String title;
    private int wish_count;
    private String eng_title;

    public boolean getWish () {
        return wish;
    }

    public void setWish(boolean wish) {
        this.wish = wish;
    }

    public String getImage () {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getContent_id () {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWish_count () {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getEng_title() {
        return eng_title;
    }

    public void setEng_title(String eng_title){
        this.eng_title = eng_title;
    }
}
