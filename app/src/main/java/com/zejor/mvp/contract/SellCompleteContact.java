package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.SellCompleteBean;

import java.util.HashMap;

public class SellCompleteContact {
    public interface View extends BaseContract.BaseView {

        void loadData(BaseEntity<SellCompleteBean> sellCompleteBean);

        void loadOrder(BaseEntity<Object> object);
    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void uploadData(HashMap<String, String> paramter);

        void commitOrder(HashMap<String, String> paramter);
    }
}
