package com.example.project1;

public class Notice {

    //이름 내용 날짜가 들어갈 수 있는 공간을 만들고
    String index;
    String title;
    String kinds;
    String date;



    public Notice(String index, String title, String kinds, String date) {
        this.index = index;
        this.title = title;
        this.kinds = kinds;
        this.date = date;
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

