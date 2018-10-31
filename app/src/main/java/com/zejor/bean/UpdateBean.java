package com.zejor.bean;

public class UpdateBean {


    private String versionName;
    private String url;
    private String description;
    private String sign;

    @Override
    public String toString() {
        return "UpdateBean{" +
                "versionName='" + versionName + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
