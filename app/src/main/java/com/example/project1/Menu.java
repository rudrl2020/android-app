package com.example.project1;

public class Menu {

    String menuName;
    String menuKinds;
    String price;
    String imageName;


    public Menu(String menuName, String menuKinds, String price, String imageName) {
        this.menuName = menuName;
        this.menuKinds = menuKinds;
        this.price = price;
        this.imageName = imageName;
    }


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuKinds() {
        return menuKinds;
    }

    public void setMenuKinds(String menuKinds) {
        this.menuKinds = menuKinds;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
