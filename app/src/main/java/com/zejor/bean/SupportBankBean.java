package com.zejor.bean;

import java.util.List;

public class SupportBankBean {

    private List<Pm> pm;

    public List<Pm> getPm() {
        return pm;
    }

    public void setPm(List<Pm> pm) {
        this.pm = pm;
    }

    public static class Pm {
        private List<String> bankList;

        public List<String> getBankList() {
            return bankList;
        }

        public void setBankList(List<String> bankList) {
            this.bankList = bankList;
        }
    }
}
