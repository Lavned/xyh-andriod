package com.zejor.bean;

/**
 * 校验银行卡-银行卡名称
 * Created by mango on 2018/1/24.
 */

public class BankAscriptionBean {
    private String result;//
    private String bankName;//
    private String logoCode;//

    @Override
    public String toString() {
        return "BankAscriptionBean{" +
                "result='" + result + '\'' +
                ", bankName='" + bankName + '\'' +
                ", logoCode='" + logoCode + '\'' +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLogoCode() {
        return logoCode;
    }

    public void setLogoCode(String logoCode) {
        this.logoCode = logoCode;
    }
}
