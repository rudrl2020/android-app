package com.example.project1;

public class CouponFind {

    String idx, coupondId, salePrice;

    public CouponFind(String idx, String couponId, String salePrice) {
        this.idx = idx;
        this.coupondId = couponId;
        this.salePrice = salePrice;
    }

    public String getIdx() {return idx;}

    public void setIdx(String idx) {this.idx = idx;}

    public String getCoupondId() {
        return coupondId;
    }

    public void setCoupondId(String coupondId) {
        this.coupondId = coupondId;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }
}