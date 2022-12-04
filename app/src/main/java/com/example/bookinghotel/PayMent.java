package com.example.bookinghotel;

public class PayMent {
    private String hinhthuc;
    private int img;

    public PayMent(String hinhthuc, int img) {
        this.hinhthuc = hinhthuc;
        this.img = img;
    }

    public String getHinhthuc() {
        return hinhthuc;
    }

    public void setHinhthuc(String hinhthuc) {
        this.hinhthuc = hinhthuc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
