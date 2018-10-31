package com.zejor.bean;

import java.util.List;

public class RepaymentPayBean {

    @Override
    public String toString() {
        return "RepaymentPayBean{" +
                "pm=" + pm +
                '}';
    }

    private List<PmBean> pm;

    public List<PmBean> getPm() {
        return pm;
    }

    public void setPm(List<PmBean> pm) {
        this.pm = pm;
    }

    public static class PmBean {
        @Override
        public String toString() {
            return "PmBean{" +
                    "repayWay=" + repayWay +
                    '}';
        }

        private List<RepayWayBean> repayWay;

        public List<RepayWayBean> getRepayWay() {
            return repayWay;
        }

        public void setRepayWay(List<RepayWayBean> repayWay) {
            this.repayWay = repayWay;
        }

        public static class RepayWayBean {
            @Override
            public String toString() {
                return "RepayWayBean{" +
                        "payType='" + payType + '\'' +
                        ", payWay='" + payWay + '\'' +
                        ", payName='" + payName + '\'' +
                        ", goodsName='" + goodsName + '\'' +
                        ", logoUrl='" + logoUrl + '\'' +
                        '}';
            }


            private String payType;
            private String payWay;
            private String payName;
            private String goodsName;
            private String logoUrl;

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getPayWay() {
                return payWay;
            }

            public void setPayWay(String payWay) {
                this.payWay = payWay;
            }

            public String getPayName() {
                return payName;
            }

            public void setPayName(String payName) {
                this.payName = payName;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getLogoUrl() {
                return logoUrl;
            }

            public void setLogoUrl(String logoUrl) {
                this.logoUrl = logoUrl;
            }
        }
    }
}
