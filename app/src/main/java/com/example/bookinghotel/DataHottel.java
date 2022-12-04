package com.example.bookinghotel;

import java.io.Serializable;

public class DataHottel implements Serializable {
    private int imgHottel , phoneHottel;
    private String nameHottel, qualityHottel, priceHottel, descriptionHottel, rateHottel, addressHotel;

    public DataHottel(int imgHottel, String nameHottel, String priceHottel, String qualityHottel,String rateHottel, int phoneHottel, String addressHotel, String descriptionHottel) {
        this.imgHottel = imgHottel;
        this.nameHottel = nameHottel;
        this.qualityHottel = qualityHottel;
        this.priceHottel = priceHottel;
        this.descriptionHottel = descriptionHottel;
        this.rateHottel = rateHottel;
        this.phoneHottel = phoneHottel;
        this.addressHotel = addressHotel;
    }

    public int getPhoneHottel() {
        return phoneHottel;
    }

    public void setPhoneHottel(int phoneHottel) {
        this.phoneHottel = phoneHottel;
    }

    public String getAddressHotel() {
        return addressHotel;
    }

    public void setAddressHotel(String addressHotel) {
        this.addressHotel = addressHotel;
    }


    public String getRateHottel() {
        return rateHottel;
    }

    public void setRateHottel(String rateHottel) {
        this.rateHottel = rateHottel;
    }

    public String getDescriptionHottel() {
        return descriptionHottel;
    }

    public void setDescriptionHottel(String descriptionHottel) {
        this.descriptionHottel = descriptionHottel;
    }

    public int getImgHottel() {
        return imgHottel;
    }

    public void setImgHottel(int imgHottel) {
        this.imgHottel = imgHottel;
    }

    public String getNameHottel() {
        return nameHottel;
    }

    public void setNameHottel(String nameHottel) {
        this.nameHottel = nameHottel;
    }

    public String getQualityHottel() {
        return qualityHottel;
    }

    public void setQualityHottel(String qualityHottel) {
        this.qualityHottel = qualityHottel;
    }

    public String getPriceHottel() {
        return priceHottel;
    }

    public void setPriceHottel(String priceHottel) {
        this.priceHottel = priceHottel;
    }
}
