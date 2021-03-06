package com.howsmart.housemart.Model;

import java.io.Serializable;

public class PermittedHouse implements Serializable {

    private int idx, dong, ho, sale_price, monthly_price, monthly_deposit, deposit;
    private double leaseable_area;
    private String residence_name, titleImg, residence_type;
    private String sido;//시도
    private String sigungoo;//시군구
    private String dongri;//동리
    private String date;//사용승인일일
    private String allnumber;//세대수
    private String parkingnumber;//총주차대수
    private String sale_type;//"월세","전세","매매"
    private String offerState;

    public PermittedHouse(int idx, int dong, int ho, double leaseable_area, int sale_price, int monthly_price, int monthly_deposit, int deposit, String residence_name, String titleImg, String residence_type, String sido, String sigungoo, String dongri, String date, String allnumber, String parkingnumber, String sale_type, String offerState) {
        this.idx = idx;
        this.dong = dong;
        this.ho = ho;
        this.leaseable_area = leaseable_area;
        this.sale_price = sale_price;
        this.monthly_price = monthly_price;
        this.monthly_deposit = monthly_deposit;
        this.deposit = deposit;
        this.residence_name = residence_name;
        this.titleImg = titleImg;
        this.residence_type = residence_type;
        this.sido = sido;
        this.sigungoo = sigungoo;
        this.dongri = dongri;
        this.date = date;
        this.allnumber = allnumber;
        this.parkingnumber = parkingnumber;
        this.sale_type = sale_type;
        this.offerState = offerState;
    }

    public String getSale_type() {
        return sale_type;
    }

    public void setSale_type(String sale_type) {
        this.sale_type = sale_type;
    }

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getSigungoo() {
        return sigungoo;
    }

    public void setSigungoo(String sigungoo) {
        this.sigungoo = sigungoo;
    }

    public String getDongri() {
        return dongri;
    }

    public void setDongri(String dongri) {
        this.dongri = dongri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAllnumber() {
        return allnumber;
    }

    public void setAllnumber(String allnumber) {
        this.allnumber = allnumber;
    }

    public String getParkingnumber() {
        return parkingnumber;
    }

    public void setParkingnumber(String parkingnumber) {
        this.parkingnumber = parkingnumber;
    }

    public String getResidence_name() {
        return residence_name;
    }

    public void setResidence_name(String residence_name) {
        this.residence_name = residence_name;
    }

    public String getResidence_type() {
        return residence_type;
    }

    public void setResidence_type(String residence_type) {
        this.residence_type = residence_type;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getDong() {
        return dong;
    }

    public void setDong(int dong) {
        this.dong = dong;
    }

    public int getHo() {
        return ho;
    }

    public void setHo(int ho) {
        this.ho = ho;
    }

    public double getLeaseable_area() {
        return leaseable_area;
    }

    public void setLeaseable_area(double leaseable_area) {
        this.leaseable_area = leaseable_area;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public int getMonthly_price() {
        return monthly_price;
    }

    public void setMonthly_price(int monthly_price) {
        this.monthly_price = monthly_price;
    }

    public int getMonthly_deposit() {
        return monthly_deposit;
    }

    public void setMonthly_deposit(int monthly_deposit) {
        this.monthly_deposit = monthly_deposit;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getOfferState() {
        return offerState;
    }

    public void setOfferState(String offerState) {
        this.offerState = offerState;
    }

    @Override
    public String toString() {
        return "PermittedHouse{" +
                "idx=" + idx +
                ", dong=" + dong +
                ", ho=" + ho +
                ", leaseable_area=" + leaseable_area +
                ", sale_price=" + sale_price +
                ", monthly_price=" + monthly_price +
                ", monthly_deposit=" + monthly_deposit +
                ", deposit=" + deposit +
                ", residence_name='" + residence_name + '\'' +
                ", titleImg='" + titleImg + '\'' +
                ", residence_type='" + residence_type + '\'' +
                ", sido='" + sido + '\'' +
                ", sigungoo='" + sigungoo + '\'' +
                ", dongri='" + dongri + '\'' +
                ", date='" + date + '\'' +
                ", allnumber='" + allnumber + '\'' +
                ", parkingnumber='" + parkingnumber + '\'' +
                ", sale_type='" + sale_type + '\'' +
                '}';
    }
}
