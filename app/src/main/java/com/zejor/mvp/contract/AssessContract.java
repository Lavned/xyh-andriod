package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.MobilePriceBean;

import java.util.HashMap;

public class AssessContract {
    public interface View extends BaseContract.BaseView {


        void loadMobilePrice(BaseEntity<MobilePriceBean> mobilePriceBean);


    }

    public interface Presenter extends BaseContract.BasePresenter<View> {


        void getMobilePrice(HashMap<String, String> paramter);


    }
}
