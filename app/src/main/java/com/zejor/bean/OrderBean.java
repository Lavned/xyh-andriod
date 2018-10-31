package com.zejor.bean;

public class OrderBean {


    private String orderType;
    private String tradeOrderNo;
    private String applyTime;
    private String shouldRepaymentTime;
    private String usingDays;
    private String orderStatus;
    private String overdueDays;
    private String orderDescription;
    private String orderAuditResult;
    private String shouldRepayPrincipal;
    private String shouldRepayFee;
    private String shouldOverdueFine;
    private String shouldRepayInterest;
    private String isRenewRenting;
    private OrderRenewRentingInfoBean orderRenewRentingInfo;

    private String bankNum;

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getShouldOverdueFine() {
        return shouldOverdueFine;
    }

    public String getShouldRepayFee() {
        return shouldRepayFee;
    }

    public String getShouldRepayInterest() {
        return shouldRepayInterest;
    }

    public String getShouldRepayPrincipal() {
        return shouldRepayPrincipal;
    }

    public void setShouldOverdueFine(String shouldOverdueFine) {
        this.shouldOverdueFine = shouldOverdueFine;
    }

    public void setShouldRepayFee(String shouldRepayFee) {
        this.shouldRepayFee = shouldRepayFee;
    }

    public void setShouldRepayInterest(String shouldRepayInterest) {
        this.shouldRepayInterest = shouldRepayInterest;
    }

    public void setShouldRepayPrincipal(String shouldRepayPrincipal) {
        this.shouldRepayPrincipal = shouldRepayPrincipal;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getShouldRepaymentTime() {
        return shouldRepaymentTime;
    }

    public void setShouldRepaymentTime(String shouldRepaymentTime) {
        this.shouldRepaymentTime = shouldRepaymentTime;
    }


    public String getUsingDays() {
        return usingDays;
    }

    public void setUsingDays(String usingDays) {
        this.usingDays = usingDays;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
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

    public String getIsRenewRenting() {
        return isRenewRenting;
    }

    public void setIsRenewRenting(String isRenewRenting) {
        this.isRenewRenting = isRenewRenting;
    }

//    public OrderMobileInfoBean getOrderMobileInfo() {
//        return orderMobileInfo;
//    }
//
//    public void setOrderMobileInfo(OrderMobileInfoBean orderMobileInfo) {
//        this.orderMobileInfo = orderMobileInfo;
//    }

    public OrderRenewRentingInfoBean getOrderRenewRentingInfo() {
        return orderRenewRentingInfo;
    }

    public void setOrderRenewRentingInfo(OrderRenewRentingInfoBean orderRenewRentingInfo) {
        this.orderRenewRentingInfo = orderRenewRentingInfo;
    }

//    public static class OrderMobileInfoBean {
//        /**
//         * courierNumber :
//         * mobileModels :
//         * mobileMemory :
//         */
//
//        private String courierNumber;
//        private String mobileModels;
//        private String mobileMemory;
//
//        public String getCourierNumber() {
//            return courierNumber;
//        }
//
//        public void setCourierNumber(String courierNumber) {
//            this.courierNumber = courierNumber;
//        }
//
//        public String getMobileModels() {
//            return mobileModels;
//        }
//
//        public void setMobileModels(String mobileModels) {
//            this.mobileModels = mobileModels;
//        }
//
//        public String getMobileMemory() {
//            return mobileMemory;
//        }
//
//        public void setMobileMemory(String mobileMemory) {
//            this.mobileMemory = mobileMemory;
//        }
//    }

    public static class OrderRenewRentingInfoBean {
        /**
         * unusedDays : 0
         * rentDays : 7
         * totalDays : 0
         */

        private String unusedDays;
        private String rentDays;
        private String totalDays;

        public String getUnusedDays() {
            return unusedDays;
        }

        public void setUnusedDays(String unusedDays) {
            this.unusedDays = unusedDays;
        }

        public String getRentDays() {
            return rentDays;
        }

        public void setRentDays(String rentDays) {
            this.rentDays = rentDays;
        }

        public String getTotalDays() {
            return totalDays;
        }

        public void setTotalDays(String totalDays) {
            this.totalDays = totalDays;
        }
    }
}
