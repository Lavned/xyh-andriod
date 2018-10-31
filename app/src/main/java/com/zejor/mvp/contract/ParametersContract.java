package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseContract.BasePresenter;
import com.zejor.base.BaseEntity;
import com.zejor.bean.PhonePicBean;

import java.util.HashMap;


public class ParametersContract {
    public interface View extends BaseContract.BaseView {

        void loadData(BaseEntity<PhonePicBean> phonePicBean);

    }

    public interface Presenter extends BasePresenter<View> {

        void getData(HashMap<String, String> paramter);

        void getPhoto(HashMap<String, String> paramter);
    }
}
