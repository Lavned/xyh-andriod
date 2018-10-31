package com.zejor.bean;

public class SellCompleteBean {


    @Override
    public String toString() {
        return "SellCompleteBean{" +
                "userRealName='" + userRealName + '\'' +
                ", userCertificateNo='" + userCertificateNo + '\'' +
                ", idcardFrontImg='" + idcardFrontImg + '\'' +
                ", idcardBackImg='" + idcardBackImg + '\'' +
                ", idcardHandImg='" + idcardHandImg + '\'' +
                ", userFace='" + userFace + '\'' +
                '}';
    }
    private String userRealName;
    private String userCertificateNo;
    private String idcardFrontImg;
    private String idcardBackImg;
    private String idcardHandImg;

    private String userFace;

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserCertificateNo() {
        return userCertificateNo;
    }

    public void setUserCertificateNo(String userCertificateNo) {
        this.userCertificateNo = userCertificateNo;
    }

    public String getIdcardFrontImg() {
        return idcardFrontImg;
    }

    public void setIdcardFrontImg(String idcardFrontImg) {
        this.idcardFrontImg = idcardFrontImg;
    }

    public String getIdcardBackImg() {
        return idcardBackImg;
    }

    public void setIdcardBackImg(String idcardBackImg) {
        this.idcardBackImg = idcardBackImg;
    }

    public String getIdcardHandImg() {
        return idcardHandImg;
    }

    public void setIdcardHandImg(String idcardHandImg) {
        this.idcardHandImg = idcardHandImg;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }
}
