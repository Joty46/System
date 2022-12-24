package com.example.kutibari;

public class AllProduct {
    String pid,pname,uname;
    String uid,image;
    int five,four,three,two,one,total;
    public AllProduct(){

    }

    public AllProduct(String pid, String pname, String uname, String uid, String image,int five,int four,int three,int two,int one,int total) {
        this.pid = pid;
        this.pname = pname;
        this.uname = uname;
        this.uid = uid;
        this.image = image;
        this.five=five;
        this.four=four;
        this.three=three;
        this.two=two;
        this.one=one;
    }
    public AllProduct(String id, String title, String uname, String uid, String s, int five, int four, int three, int two, int one){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String getUname() {
        return uname;
    }

    public String getUid() {
        return uid;
    }

    public int getFive(){return five;}

    public int getOne() {
        return one;
    }

    public int getFour() {
        return four;
    }

    public int getThree() {
        return three;
    }

    public int getTwo() {
        return two;
    }

    public int getTotal() {
        return total;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
