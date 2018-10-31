package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseContract.BasePresenter;
import com.zejor.base.BaseEntity;
import com.zejor.bean.LoginDataBean;
import com.zejor.bean.ProcotolBean;

import java.util.HashMap;


public class LoginContract {
    public interface View extends BaseContract.BaseView {

        void loadData(BaseEntity<LoginDataBean> loginDataBean);

        void getCode(BaseEntity<Object> Object);

        void getPhoneMessage(BaseEntity<Object> Object);

        void onProtocolSuccess(BaseEntity<ProcotolBean> Object);
    }

    public interface Presenter extends BasePresenter<View> {

        void getData(HashMap<String, String> paramter);

        void sendCode(HashMap<String, String> paramter);

        void savePhoneMessage(HashMap<String, String> paramter);

        void getProtocol(HashMap<String, String> paramter);

    }
}
