package com.example.kutibari;

public class Product {
    private String id,title,price,days,uid;
    int five,four,three,two,one,total;
    private String image;
    public Product(){

    }

    public Product(String id, String image, String title, String price, String days,String uid,int five,int four,int three,int two,int one,int total) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.price = price;
        this.days = days;
        this.uid=uid;
        this.five=five;
        this.four=four;
        this.three=three;
        this.two=two;
        this.one=one;
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

    public int getFive(){return five;}


    public int getFour() {
        return four;
    }

    public int getThree() {
        return three;
    }

    public int getTwo() {
        return two;
    }

    public int getOne() {
        return one;
    }

    public int getTotal() {
        return total;
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

    public void setFive(int five) {
        this.five = five;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

