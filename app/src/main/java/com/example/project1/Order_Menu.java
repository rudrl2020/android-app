package com.example.project1;

public class Order_Menu {

    String menuName, price, imgName;


    public Order_Menu(String menuName, String price, String imgName) {
        this.menuName = menuName;
        this.price = price;
        this.imgName = imgName;
    }

    public String getMenuName() {return menuName;}

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgName() { return imgName;}

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}