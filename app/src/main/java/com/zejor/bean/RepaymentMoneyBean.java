package com.zejor.bean;

import java.util.List;

public class RepaymentMoneyBean {


    private List<PmBean> pm;

    public List<PmBean> getPm() {
        return pm;
    }

    public void setPm(List<PmBean> pm) {
        this.pm = pm;
    }

    public static class PmBean {
        private List<LeaseAmountBean> leaseAmount;

        public List<LeaseAmountBean> getLeaseAmount() {
            return leaseAmount;
        }

        public void setLeaseAmount(List<LeaseAmountBean> leaseAmount) {
            this.leaseAmount = leaseAmount;
        }

        public static class LeaseAmountBean {

            private String overdueAmount;
            private String amount;
            private String display;
            private String days;

            public String getOverdueAmount() {
                return overdueAmount;
            }

            public void setOverdueAmount(String overdueAmount) {
                this.overdueAmount = overdueAmount;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDisplay() {
                return display;
            }

            public void setDisplay(String display) {
                this.display = display;
            }

            public String getDays() {
                return days;
            }

            public void setDays(String days) {
                this.days = days;
            }
        }
    }
}
