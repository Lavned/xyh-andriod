package com.zejor.bean;

/**
 * 认证项的信息
 */

public class AuthenticationBean {
    @Override
    public String toString() {
        return "AuthenticationBean{" +
                "authItem='" + authItem + '\'' +
                ", authCode='" + authCode + '\'' +
                ", authStatus='" + authStatus + '\'' +
                ", conditionSort='" + conditionSort + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    private String authItem;
    private String authCode;
    private String authStatus;
    private String conditionSort;

    private String status;

    public String getAuthItem() {
        return authItem;
    }

    public void setAuthItem(String authItem) {
        this.authItem = authItem;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getConditionSort() {
        return conditionSort;
    }

    public void setConditionSort(String conditionSort) {
        this.conditionSort = conditionSort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AuthenticationBean(String title) {
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
