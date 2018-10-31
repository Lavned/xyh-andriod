package com.zejor.bean;

/**
 * Created by mango on 2018/8/7.
 */

public class ZhiMaBean {



    /**
     * status : 2
     */

    private String authUrl;
    private String status;

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ZhiMaBean{" +
                "authUrl='" + authUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
