package com.zejor.base;

/**
 * 网络请求结果的基类,具体页面时只需要传入相应的泛型就可以
 */

public class BaseEntity<T> {

    private String retCode;
    private String retMsg;
    private T retData;

    public void setRetData(T retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", retData=" + retData +
                '}';
    }

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

    public T getRetData() {
        return retData;
    }
}
