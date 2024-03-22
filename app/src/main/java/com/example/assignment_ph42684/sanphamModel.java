package com.example.assignment_ph42684;

public class sanphamModel {
    private String _id;
    private String ten;
    private String soluong;
    private String tonkho;
    private double gia;

    public sanphamModel(String _id, String ten, String soluong, String tonkho, double gia) {
        this._id = _id;
        this.ten = ten;
        this.soluong = soluong;
        this.tonkho = tonkho;
        this.gia = gia;
    }

    public sanphamModel(String ten,double gia, String soluong, String tonkho ) {
        this.ten = ten;
        this.soluong = soluong;
        this.tonkho = tonkho;
        this.gia = gia;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getTonkho() {
        return tonkho;
    }

    public void setTonkho(String tonkho) {
        this.tonkho = tonkho;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}