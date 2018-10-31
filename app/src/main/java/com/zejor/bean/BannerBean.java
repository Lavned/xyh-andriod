package com.zejor.bean;

import java.util.List;

/**
 * Banner图
 */

public class BannerBean {
    private List<BannerInfo> bannerList;
    private String bannerNum;

    public List<BannerInfo> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerInfo> bannerList) {
        this.bannerList = bannerList;
    }

    public String getBannerNum() {
        return bannerNum;
    }

    public void setBannerNum(String bannerNum) {
        this.bannerNum = bannerNum;
    }

    public class BannerInfo{
        private String linkUrl;
        private String step;
        private String logoUrl;

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        @Override
        public String toString() {
            return "BannerInfo{" +
                    "linkUrl='" + linkUrl + '\'' +
                    ", step='" + step + '\'' +
                    ", logoUrl='" + logoUrl + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "bannerList=" + bannerList +
                ", bannerNum='" + bannerNum + '\'' +
                '}';
    }
}
