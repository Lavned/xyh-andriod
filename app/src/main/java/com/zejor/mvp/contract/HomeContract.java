package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.AdvertisementBean;
import com.zejor.bean.BannerBean;
import com.zejor.bean.OrderBean;
import com.zejor.bean.OrderChangeStatusBean;
import com.zejor.bean.RefreshBean;
import com.zejor.bean.UpdateBean;

import java.util.HashMap;
import java.util.List;

public class HomeContract {
    public interface View extends BaseContract.BaseView {

        void loadOrder(BaseEntity<List<OrderBean>> loginDataBean);

        void loadAdvertisementCall(BaseEntity<List<AdvertisementBean>> loginDataBean);

        void onOrderStatusSuccess(BaseEntity<OrderChangeStatusBean> orderChangeStatusBean);

        void updateSuccess(BaseEntity<UpdateBean> updateBean);

        void refreshSuccess(BaseEntity<RefreshBean> updateBean);

        void refreshBanner(BaseEntity<BannerBean> bannerBean);


    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getOrder(HashMap<String, String> paramter);

        void getScrollText(HashMap<String, String> paramter);

        void getOrderStatus(HashMap<String, String> paramter);

        void getUpdate(HashMap<String, String> paramter);

        void homeRefresh(HashMap<String, String> paramter);

        void getBannerData(HashMap<String, String> paramter);

    }
}
