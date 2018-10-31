package com.zejor.bean;

/**
 * 银行卡信息的实体类
 */


public class BankBean {


    private String bankCardNo;//622********7961,
    private String bankCardType;//信用卡,
    private String bankName;//民生银行,
    private String defaultBankCard;//1.0,   2017年9月26日   0 是默认   确认人：廖训传  刘欢
    private String logoUrl;//456.com

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDefaultBankCard() {
        return defaultBankCard;
    }

    public void setDefaultBankCard(String defaultBankCard) {
        this.defaultBankCard = defaultBankCard;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "BankBean{" +
                "bankCardNo='" + bankCardNo + '\'' +
                ", bankCardType='" + bankCardType + '\'' +
                ", bankName='" + bankName + '\'' +
                ", defaultBankCard='" + defaultBankCard + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}


//public class BankBean {
//
//
//    @Override
//    public String toString() {
//        return "BankBean{" +
//                "bankCardNo='" + bankCardNo + '\'' +
//                ", bankCardType='" + bankCardType + '\'' +
//                ", bankName='" + bankName + '\'' +
//                ", defaultBankCard='" + defaultBankCard + '\'' +
//                ", logoUrl='" + logoUrl + '\'' +
//                '}';
//    }
//    private String bankCardNo;//622********7961,
//    private String bankCardType;//信用卡,
//    private String bankName;//民生银行,
//    private String defaultBankCard;//1.0,   2017年9月26日   0 是默认   确认人：廖训传  刘欢
//
//    private String logoUrl;//456.com
//
//    public String getBankCardNo() {
//        return bankCardNo;
//    }
//
//    public void setBankCardNo(String bankCardNo) {
//        this.bankCardNo = bankCardNo;
//    }
//
//    public String getBankCardType() {
//        return bankCardType;
//    }
//
//    public void setBankCardType(String bankCardType) {
//        this.bankCardType = bankCardType;
//    }
//
//    public String getBankName() {
//        return bankName;
//    }
//
//    public void setBankName(String bankName) {
//        this.bankName = bankName;
//    }
//
//    public String getDefaultBankCard() {
//        return defaultBankCard;
//    }
//
//    public void setDefaultBankCard(String defaultBankCard) {
//        this.defaultBankCard = defaultBankCard;
//    }
//
//    public String getLogoUrl() {
//        return logoUrl;
//    }
//
//    public void setLogoUrl(String logoUrl) {
//        this.logoUrl = logoUrl;
//    }
//}
