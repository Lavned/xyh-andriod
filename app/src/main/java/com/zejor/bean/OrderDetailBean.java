package com.zejor.bean;

public class OrderDetailBean {


    private String isReturningMobile;
    private String tradeOrderNo;
    private String courierType;
    private String courierNumber;
    private String repurchaseOffsetAmount;
    private String orderStatus;
    private String orderDescription;
    private String orderAuditResult;

    public String getIsReturningMobile() {
        return isReturningMobile;
    }

    public void setIsReturningMobile(String isReturningMobile) {
        this.isReturningMobile = isReturningMobile;
    }

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public String getCourierType() {
        return courierType;
    }

    public void setCourierType(String courierType) {
        this.courierType = courierType;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public String getRepurchaseOffsetAmount() {
        return repurchaseOffsetAmount;
    }

    public void setRepurchaseOffsetAmount(String repurchaseOffsetAmount) {
        this.repurchaseOffsetAmount = repurchaseOffsetAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getOrderAuditResult() {
        return orderAuditResult;
    }

    public void setOrderAuditResult(String orderAuditResult) {
        this.orderAuditResult = orderAuditResult;
    }
}
