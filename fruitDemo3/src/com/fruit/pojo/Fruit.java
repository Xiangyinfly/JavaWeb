package com.fruit.pojo;

public class Fruit {
    private int fid;
    private String fname;
    private double fprice;
    private int fcount;
    private String fremark;

    public Fruit() {
    }

    public Fruit(int fid, String fname, double fprice, int fcount, String fremark) {
        this.fid = fid;
        this.fname = fname;
        this.fprice = fprice;
        this.fcount = fcount;
        this.fremark = fremark;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public double getFprice() {
        return fprice;
    }

    public void setFprice(double fprice) {
        this.fprice = fprice;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public String getFremark() {
        return fremark;
    }

    public void setFremark(String fremark) {
        this.fremark = fremark;
    }
}
