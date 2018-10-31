package com.zejor.bean;

/**
 * 展示首页循环滚动的借款信息
 */

public class AdvertisementBean {
    private String applySucessUser;

    public String getApplySucessUser() {
        return applySucessUser;
    }

    public void setApplySucessUser(String applySucessUser) {
        this.applySucessUser = applySucessUser;
    }

    @Override
    public String toString() {
        return "AdvertisementBean{" +
                "applySucessUser='" + applySucessUser + '\'' +
                '}';
    }
}
