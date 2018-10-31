package com.zejor.mvp.contract;


import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.BankBean;

import java.util.HashMap;
import java.util.List;

public class BankPayContract {
    public interface View extends BaseContract.BaseView {

        void loadBankCard(BaseEntity<List<BankBean>> bankBean);

        void codeSuccess(BaseEntity<Object> object);

        void paySuccess(BaseEntity<Object> object);

    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getBankCard(HashMap<String, String> paramter);

        void sendCode(HashMap<String, String> paramter);

        void pay(HashMap<String, String> paramter);

    }
}
