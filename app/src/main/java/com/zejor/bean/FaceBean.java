package com.zejor.bean;


public class FaceBean {

    @Override
    public String toString() {
        return "FaceBean{" +
                "userRealName='" + userRealName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", userFaceStatus='" + userFaceStatus + '\'' +
                '}';
    }
    private String userRealName;
    private String cardNo;

    private String userFaceStatus;

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getUserFaceStatus() {
        return userFaceStatus;
    }

    public void setUserFaceStatus(String userFaceStatus) {
        this.userFaceStatus = userFaceStatus;
    }
}
