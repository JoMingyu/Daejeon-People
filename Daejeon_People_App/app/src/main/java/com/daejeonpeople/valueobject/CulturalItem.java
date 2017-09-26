package com.daejeonpeople.valueobject;

/**
 * Created by dsm2016 on 2017-09-12.
 */

public class CulturalItem {

    private String title;
    private String category;
    private String call;
    private String time;
    private String holiday;
    private int charge;
    private String using_time;
    private String address;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int fee) {
        this.charge = charge;
    }

    public String getUsing_time() {
        return using_time;
    }

    public void setUsing_time(String using_time) {
        this.using_time = using_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
