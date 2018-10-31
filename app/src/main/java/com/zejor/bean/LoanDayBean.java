package com.zejor.bean;

/**
 * Created by mogojing on 2018/8/13/0013.
 */

public class LoanDayBean {


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
         * loanDayGrad : 7
         * loanDayUnit : 天
         * loanAmountGrad : 100
         * loanAmountUnit : 元
         * loanDayMax : 7
         * loanAmountMin : 1000
         * loanDayMin : 7
         * loanAmountMax : 1000
         */

        private int loanDayGrad;
        private String loanDayUnit;
        private int loanAmountGrad;
        private String loanAmountUnit;
        private int loanDayMax;
        private int loanAmountMin;
        private int loanDayMin;
        private int loanAmountMax;

        public int getLoanDayGrad() {
            return loanDayGrad;
        }

        public void setLoanDayGrad(int loanDayGrad) {
            this.loanDayGrad = loanDayGrad;
        }

        public String getLoanDayUnit() {
            return loanDayUnit;
        }

        public void setLoanDayUnit(String loanDayUnit) {
            this.loanDayUnit = loanDayUnit;
        }

        public int getLoanAmountGrad() {
            return loanAmountGrad;
        }

        public void setLoanAmountGrad(int loanAmountGrad) {
            this.loanAmountGrad = loanAmountGrad;
        }

        public String getLoanAmountUnit() {
            return loanAmountUnit;
        }

        public void setLoanAmountUnit(String loanAmountUnit) {
            this.loanAmountUnit = loanAmountUnit;
        }

        public int getLoanDayMax() {
            return loanDayMax;
        }

        public void setLoanDayMax(int loanDayMax) {
            this.loanDayMax = loanDayMax;
        }

        public int getLoanAmountMin() {
            return loanAmountMin;
        }

        public void setLoanAmountMin(int loanAmountMin) {
            this.loanAmountMin = loanAmountMin;
        }

        public int getLoanDayMin() {
            return loanDayMin;
        }

        public void setLoanDayMin(int loanDayMin) {
            this.loanDayMin = loanDayMin;
        }

        public int getLoanAmountMax() {
            return loanAmountMax;
        }

        public void setLoanAmountMax(int loanAmountMax) {
            this.loanAmountMax = loanAmountMax;
        }
    }
}
