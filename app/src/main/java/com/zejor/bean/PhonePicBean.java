package com.zejor.bean;

public class PhonePicBean {


    @Override
    public String toString() {
        return "PhonePicBean{" +
                "mobileFrontImg='" + mobileFrontImg + '\'' +
                ", mobileBackImg='" + mobileBackImg + '\'' +
                '}';
    }
    private String mobileFrontImg;

    private String mobileBackImg;

    public String getMobileFrontImg() {
        return mobileFrontImg;
    }

    public void setMobileFrontImg(String mobileFrontImg) {
        this.mobileFrontImg = mobileFrontImg;
    }

    public String getMobileBackImg() {
        return mobileBackImg;
    }

    public void setMobileBackImg(String mobileBackImg) {
        this.mobileBackImg = mobileBackImg;
    }
}
