package com.example.kutibari;

public class Orders {
    String name,address,quantity,pname,pid;
    Orders(){}

    public Orders(String name, String address, String quantity, String pname, String pid) {
        this.name = name;
        this.address = address;
        this.quantity = quantity;
        this.pname = pname;
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getQuantity() {
        return quantity;
    }

}
