package com.zejor.bean;

/**
 * 协议条款的实体类
 */

public class AgreementUrlBean {

    private String userProtocol;

    public String getUserProtocol() {
        return userProtocol;
    }

    public void setUserProtocol(String userProtocol) {
        this.userProtocol = userProtocol;
    }

    @Override
    public String toString() {
        return "AgreementUrlBean{" +
                "userProtocol='" + userProtocol + '\'' +
                '}';
    }
}
