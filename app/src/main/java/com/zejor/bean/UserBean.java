package com.zejor.bean;

/**
 * Created by THINK on 2018/9/19.
 */

public class UserBean {


    /**
     * retCode : 0000
     * retMsg : 成功！
     * retData : {"realNameStatus":"1","userRealName":"刘少勤","userCertificateNo":"350823198811216310","qq":"123456","weixin":"123456","idcardFrontImg":"http://xinyh.oss-cn-hzfinance.aliyuncs.com/d98cfafc-9e5b-4c16-b355-87bb3c4fd707.jpg","idcardBackImg":"http://xinyh.oss-cn-hzfinance.aliyuncs.com/85f895f7-b866-41a5-961e-4f34b1bb0cba.jpg","firstContactName":"啊","firstContactMobile":"17612345678","firstContactRelation":"同事","secondContactName":"唐","secondContactMobile":"13212345678","secondContactRelation":"朋友","completedInfoStatus":"1"}
     */

    private String retCode;
    private String retMsg;
    private RetDataBean retData;

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

    public RetDataBean getRetData() {
        return retData;
    }

    public void setRetData(RetDataBean retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        /**
         * realNameStatus : 1
         * userRealName : 刘少勤
         * userCertificateNo : 350823198811216310
         * qq : 123456
         * weixin : 123456
         * idcardFrontImg : http://xinyh.oss-cn-hzfinance.aliyuncs.com/d98cfafc-9e5b-4c16-b355-87bb3c4fd707.jpg
         * idcardBackImg : http://xinyh.oss-cn-hzfinance.aliyuncs.com/85f895f7-b866-41a5-961e-4f34b1bb0cba.jpg
         * firstContactName : 啊
         * firstContactMobile : 17612345678
         * firstContactRelation : 同事
         * secondContactName : 唐
         * secondContactMobile : 13212345678
         * secondContactRelation : 朋友
         * completedInfoStatus : 1
         */

        private String realNameStatus;
        private String userRealName;
        private String userCertificateNo;
        private String qq;
        private String weixin;
        private String idcardFrontImg;
        private String idcardBackImg;
        private String firstContactName;
        private String firstContactMobile;
        private String firstContactRelation;
        private String secondContactName;
        private String secondContactMobile;
        private String secondContactRelation;
        private String completedInfoStatus;

        public String getRealNameStatus() {
            return realNameStatus;
        }

        public void setRealNameStatus(String realNameStatus) {
            this.realNameStatus = realNameStatus;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public String getUserCertificateNo() {
            return userCertificateNo;
        }

        public void setUserCertificateNo(String userCertificateNo) {
            this.userCertificateNo = userCertificateNo;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getIdcardFrontImg() {
            return idcardFrontImg;
        }

        public void setIdcardFrontImg(String idcardFrontImg) {
            this.idcardFrontImg = idcardFrontImg;
        }

        public String getIdcardBackImg() {
            return idcardBackImg;
        }

        public void setIdcardBackImg(String idcardBackImg) {
            this.idcardBackImg = idcardBackImg;
        }

        public String getFirstContactName() {
            return firstContactName;
        }

        public void setFirstContactName(String firstContactName) {
            this.firstContactName = firstContactName;
        }

        public String getFirstContactMobile() {
            return firstContactMobile;
        }

        public void setFirstContactMobile(String firstContactMobile) {
            this.firstContactMobile = firstContactMobile;
        }

        public String getFirstContactRelation() {
            return firstContactRelation;
        }

        public void setFirstContactRelation(String firstContactRelation) {
            this.firstContactRelation = firstContactRelation;
        }

        public String getSecondContactName() {
            return secondContactName;
        }

        public void setSecondContactName(String secondContactName) {
            this.secondContactName = secondContactName;
        }

        public String getSecondContactMobile() {
            return secondContactMobile;
        }

        public void setSecondContactMobile(String secondContactMobile) {
            this.secondContactMobile = secondContactMobile;
        }

        public String getSecondContactRelation() {
            return secondContactRelation;
        }

        public void setSecondContactRelation(String secondContactRelation) {
            this.secondContactRelation = secondContactRelation;
        }

        public String getCompletedInfoStatus() {
            return completedInfoStatus;
        }

        public void setCompletedInfoStatus(String completedInfoStatus) {
            this.completedInfoStatus = completedInfoStatus;
        }
    }
}
