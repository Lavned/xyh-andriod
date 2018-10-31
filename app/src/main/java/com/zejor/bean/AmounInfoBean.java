package com.zejor.bean;

/**
 * Created by mogojing on 2018/8/14/0014.
 */

public class AmounInfoBean {


    private String retCode;
    private String retMsg;
    private RetDataBean retData;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public RetDataBean getRetData() {
        return retData;
    }

    public void setRetData(RetDataBean retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        /**
         * serviceFee : 190
         * actualMoney : 1003.5
         * interestFee : 3.5
         * actualArrival : 810
         */

        private String serviceFee;
        private String actualMoney;
        private String interestFee;
        private String actualArrival;

        public String getServiceFee() {
            return serviceFee;
        }

        public void setServiceFee(String serviceFee) {
            this.serviceFee = serviceFee;
        }

        public String getActualMoney() {
            return actualMoney;
        }

        public void setActualMoney(String actualMoney) {
            this.actualMoney = actualMoney;
        }

        public String getInterestFee() {
            return interestFee;
        }

        public void setInterestFee(String interestFee) {
            this.interestFee = interestFee;
        }

        public String getActualArrival() {
            return actualArrival;
        }

        public void setActualArrival(String actualArrival) {
            this.actualArrival = actualArrival;
        }
    }
}
