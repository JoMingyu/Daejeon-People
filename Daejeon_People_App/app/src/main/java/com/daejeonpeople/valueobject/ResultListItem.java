package com.daejeonpeople.valueobject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KimDongGyu on 2017-10-03.
 */

public class ResultListItem {
    @SerializedName("content_id")
    private int content_id;
    @SerializedName("title")
    private String title;
    @SerializedName("wish")
    private boolean wish;
    @SerializedName("wish_count")
    private int wish_count;
    @SerializedName("address")
    private String address;
    @SerializedName("image")
    private String image;
    @SerializedName("category")
    private String category;
    @SerializedName("mapx")
    private double mapx;
    @SerializedName("mapy")
    private double mapy;

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
