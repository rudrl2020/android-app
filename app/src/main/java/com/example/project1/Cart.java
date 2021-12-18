package com.example.project1;

public class Cart {

    String menuName, price, menuNum;

    public Cart(String menuName, String price, String menuNum) {
        this.menuName = menuName;
        this.price = price;
        this.menuNum = menuNum;
    }

    public String getMenuName() {return menuName;}

    public void setMenuName(String menuName) {this.menuName = menuName;}

    public String getPrice() {return price;}

    public void setPrice(){this.price = price;}

    public String getMenuNum() {return menuNum;}

    public void setMenuNum(String menuNum) { this.menuNum=menuNum;}

    public void add(Cart cart) {

    }
}