package com.howsmart.housemart;

import com.howsmart.housemart.Model.Member;

import java.io.Serializable;
import java.util.List;

public class HouseInfoDetail implements Serializable {

    private String lastModifiedDate;
    private Long id;
    private String residence_name;//아파트 이름
    private String code;//아파트 코드
    private Integer dong; // 동
    private Integer ho; // 호수
    private Double net_leaseable_area;//전용면적
    private Double leaseable_area;//공급면적
    private String residence_type;//매물 타입(A,V,O)
    private String sale_type;//"월세","전세","매매"
    private Long sale_price;//매매가/전세금/보증금
    private Long monthly_price;//월세
    private Long admin_expenses;//관리비
    private Integer provisional_down_pay_per;//가계약금 비율
    private Integer down_pay_per;//계약금 비율
    private Integer intermediate_pay_per;//중도금 비율
    private Integer balance_per;//잔금 비율
    private Integer room_num;//방 개수
    private Integer toilet_num;//욕실 개수
    private boolean middle_door;//중문
    private boolean air_conditioner;//시스템 에어컨
    private boolean refrigerator;//냉장고
    private boolean kimchi_refrigerator;//김치냉장고
    private boolean closet;//붙박이장
    private boolean oven;//빌트인 오븐
    private boolean induction;//인덕션
    private boolean airsystem;//공조기 시스템
    private boolean nego;//네고가능
    private String short_description;//짧은 집 소개
    private String long_description;//긴 집 소개
    private String apartment_description;//아파트 소개
    private String livingroom_description;//거실 소개
    private String kitchen_description;//주방 소개
    private String room1_description;//방 1 소개
    private String room2_description;//방 2 소개
    private String room3_description;//방 3 소개
    private String toilet1_description;//화장실 1 소개
    private String toilet2_description;//화장실 2 소개
    private String movedate;//입주가능일
    private Member member;
    private String address;//도로명 주소
    private String sido;//시도
    private String sigungoo;//시군구
    private String dongri;//동리
    private String date;//사용승인일일
    private String allnumber;//세대수
    private String parkingnumber;//총주차대수
    private String contact;//관리사무소 연락처
    private String porch_description; // 현관 설명
    private Integer numOfImg;
    private List<String> salesOfferURLS;

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResidence_name() {
        return residence_name;
    }

    public void setResidence_name(String residence_name) {
        this.residence_name = residence_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDong() {
        return dong;
    }

    public void setDong(Integer dong) {
        this.dong = dong;
    }

    public Integer getHo() {
        return ho;
    }

    public void setHo(Integer ho) {
        this.ho = ho;
    }

    public Double getNet_leaseable_area() {
        return net_leaseable_area;
    }

    public void setNet_leaseable_area(Double net_leaseable_area) {
        this.net_leaseable_area = net_leaseable_area;
    }

    public Double getLeaseable_area() {
        return leaseable_area;
    }

    public void setLeaseable_area(Double leaseable_area) {
        this.leaseable_area = leaseable_area;
    }

    public String getResidence_type() {
        return residence_type;
    }

    public void setResidence_type(String residence_type) {
        this.residence_type = residence_type;
    }

    public String getSale_type() {
        return sale_type;
    }

    public void setSale_type(String sale_type) {
        this.sale_type = sale_type;
    }

    public Long getSale_price() {
        return sale_price;
    }

    public void setSale_price(Long sale_price) {
        this.sale_price = sale_price;
    }

    public Long getMonthly_price() {
        return monthly_price;
    }

    public void setMonthly_price(Long monthly_price) {
        this.monthly_price = monthly_price;
    }

    public Long getAdmin_expenses() {
        return admin_expenses;
    }

    public void setAdmin_expenses(Long admin_expenses) {
        this.admin_expenses = admin_expenses;
    }

    public Integer getProvisional_down_pay_per() {
        return provisional_down_pay_per;
    }

    public void setProvisional_down_pay_per(Integer provisional_down_pay_per) {
        this.provisional_down_pay_per = provisional_down_pay_per;
    }

    public Integer getDown_pay_per() {
        return down_pay_per;
    }

    public void setDown_pay_per(Integer down_pay_per) {
        this.down_pay_per = down_pay_per;
    }

    public Integer getIntermediate_pay_per() {
        return intermediate_pay_per;
    }

    public void setIntermediate_pay_per(Integer intermediate_pay_per) {
        this.intermediate_pay_per = intermediate_pay_per;
    }

    public Integer getBalance_per() {
        return balance_per;
    }

    public void setBalance_per(Integer balance_per) {
        this.balance_per = balance_per;
    }

    public Integer getRoom_num() {
        return room_num;
    }

    public void setRoom_num(Integer room_num) {
        this.room_num = room_num;
    }

    public Integer getToilet_num() {
        return toilet_num;
    }

    public void setToilet_num(Integer toilet_num) {
        this.toilet_num = toilet_num;
    }

    public boolean isMiddle_door() {
        return middle_door;
    }

    public void setMiddle_door(boolean middle_door) {
        this.middle_door = middle_door;
    }

    public boolean isAir_conditioner() {
        return air_conditioner;
    }

    public void setAir_conditioner(boolean air_conditioner) {
        this.air_conditioner = air_conditioner;
    }

    public boolean isRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(boolean refrigerator) {
        this.refrigerator = refrigerator;
    }

    public boolean isKimchi_refrigerator() {
        return kimchi_refrigerator;
    }

    public void setKimchi_refrigerator(boolean kimchi_refrigerator) {
        this.kimchi_refrigerator = kimchi_refrigerator;
    }

    public boolean isCloset() {
        return closet;
    }

    public void setCloset(boolean closet) {
        this.closet = closet;
    }

    public boolean isOven() {
        return oven;
    }

    public void setOven(boolean oven) {
        this.oven = oven;
    }

    public boolean isInduction() {
        return induction;
    }

    public void setInduction(boolean induction) {
        this.induction = induction;
    }

    public boolean isAirsystem() {
        return airsystem;
    }

    public void setAirsystem(boolean airsystem) {
        this.airsystem = airsystem;
    }

    public boolean isNego() {
        return nego;
    }

    public void setNego(boolean nego) {
        this.nego = nego;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getApartment_description() {
        return apartment_description;
    }

    public void setApartment_description(String apartment_description) {
        this.apartment_description = apartment_description;
    }

    public String getLivingroom_description() {
        return livingroom_description;
    }

    public void setLivingroom_description(String livingroom_description) {
        this.livingroom_description = livingroom_description;
    }

    public String getKitchen_description() {
        return kitchen_description;
    }

    public void setKitchen_description(String kitchen_description) {
        this.kitchen_description = kitchen_description;
    }

    public String getRoom1_description() {
        return room1_description;
    }

    public void setRoom1_description(String room1_description) {
        this.room1_description = room1_description;
    }

    public String getRoom2_description() {
        return room2_description;
    }

    public void setRoom2_description(String room2_description) {
        this.room2_description = room2_description;
    }

    public String getRoom3_description() {
        return room3_description;
    }

    public void setRoom3_description(String room3_description) {
        this.room3_description = room3_description;
    }

    public String getToilet1_description() {
        return toilet1_description;
    }

    public void setToilet1_description(String toilet1_description) {
        this.toilet1_description = toilet1_description;
    }

    public String getToilet2_description() {
        return toilet2_description;
    }

    public void setToilet2_description(String toilet2_description) {
        this.toilet2_description = toilet2_description;
    }

    public String getMovedate() {
        return movedate;
    }

    public void setMovedate(String movedate) {
        this.movedate = movedate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPorch_description() {
        return porch_description;
    }

    public void setPorch_description(String porch_description) {
        this.porch_description = porch_description;
    }

    public Integer getNumOfImg() {
        return numOfImg;
    }

    public void setNumOfImg(Integer numOfImg) {
        this.numOfImg = numOfImg;
    }

    public List<String> getSalesOfferURLS() {
        return salesOfferURLS;
    }

    public void setSalesOfferURLS(List<String> salesOfferURLS) {
        this.salesOfferURLS = salesOfferURLS;
    }
}
