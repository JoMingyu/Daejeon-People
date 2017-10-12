package com.daejeonpeople.valueobject;

/**
 * Created by KimDongGyu on 2017-09-19.
 */

public class MainItemPopular {
    private String image;
    private int content_id;
    private String title;
    private String eng_title;


    public String getImage () {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getEng_title() {
        return eng_title;
    }

    public void setEng_title(String eng_title){
        this.eng_title = eng_title;
    }

}
