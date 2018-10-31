package com.zejor.bean;


public class LoginDataBean {
    private String cardId;//
    private String mobile;//
    private String realName;//
    private String redPoint;//0,
    private String tokenId;//
    private String userId;//0

    @Override
    public String toString() {
        return "LoginDataBean{" +
                "cardId='" + cardId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", realName='" + realName + '\'' +
                ", redPoint='" + redPoint + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRedPoint() {
        return redPoint;
    }

    public void setRedPoint(String redPoint) {
        this.redPoint = redPoint;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
