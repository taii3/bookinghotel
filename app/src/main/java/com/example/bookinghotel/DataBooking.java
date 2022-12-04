package com.example.bookinghotel;

import java.io.Serializable;

public class DataBooking implements Serializable {
    int time, imgHottel;
    String name, phone, hottel, address, day, price;

    public DataBooking(String name, String phone, String hottel, String address, int time, String day, String price, int imgHottel) {
        this.time = time;
        this.name = name;
        this.phone = phone;
        this.hottel = hottel;
        this.address = address;
        this.day = day;
        this.price = price;
        this.imgHottel = imgHottel;
    }

    public int getImgHottel() {
        return imgHottel;
    }

    public void setImgHottel(int imgHottel) {
        this.imgHottel = imgHottel;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHottel() {
        return hottel;
    }

    public void setHottel(String hottel) {
        this.hottel = hottel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
