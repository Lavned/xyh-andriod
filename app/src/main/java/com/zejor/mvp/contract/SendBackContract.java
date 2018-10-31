package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;

import java.util.HashMap;

public class SendBackContract {
    public interface View extends BaseContract.BaseView {

        void loadData(BaseEntity<Object> object);

    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void sendMail(HashMap<String, String> paramter);

    }
}
