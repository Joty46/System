package com.example.kutibari;

import android.media.Image;
import android.widget.ImageView;

public class Product {
    private String id,title,price,days,uid;
    private String image;
    public Product(){

    }

    public Product(String id, String image, String title, String price, String days,String uid) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.price = price;
        this.days = days;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDays() {
        return days;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
