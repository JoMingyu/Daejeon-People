package com.daejeonpeople.valueobject;

/**
 * Created by KimDongGyu on 2017-10-05.
 */

public class SearchItem {

    private int content_id;
    private int content_type_id;
    private String title;
    private boolean wish;
    private int wish_count;
    private String address;
    private String category;
    private String image;
    private double mapx;
    private double mapy;

    public int getContent_type_id() {
        return content_type_id;
    }

    public void setContent_type_id(int content_type_id) {
        this.content_type_id = content_type_id;
    }


    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getWish() {
        return wish;
    }

    public void setWish(boolean wish) {
        this.wish = wish;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getMapx() {
        return mapx;
    }

    public void setMapx(double mapx) {
        this.mapx = mapx;
    }

    public double getMapy() {
        return mapy;
    }

    public void setMapy(double mapy) {
        this.mapy = mapy;
    }

}
