package com.example.kutibari;

import java.util.UUID;

public class AllProduct {
    String pid,pname,uname;
    String uid,image;

    public AllProduct(String pid, String pname, String uname, String uid, String image) {
        this.pid = pid;
        this.pname = pname;
        this.uname = uname;
        this.uid = uid;
        this.image = image;
    }
    public AllProduct(){}

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
}
