package com.zejor.bean;

/**
 * 提交个人基本信息
 */

public class CommitInfoBean {
    /* "isSuccess":1 //(说明：返回正确情况，此处可为null)*/


    private String isSuccess;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Override
    public String toString() {
        return "CommitInfoBean{" +
                "isSuccess='" + isSuccess + '\'' +
                '}';
    }
}
