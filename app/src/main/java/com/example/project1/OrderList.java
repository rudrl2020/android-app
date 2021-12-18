package com.example.project1;

public class OrderList {

    String index, menuName, count,date;

    public OrderList(String index, String menuName, String count, String date) {
        this.index = index;
        this.menuName = menuName;
        this.count = count;
        this.date = date;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {this.index = index;}

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getCounts() {
        return count;
    }

    public void setCounts(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void add(OrderList orderList) {
    }
}