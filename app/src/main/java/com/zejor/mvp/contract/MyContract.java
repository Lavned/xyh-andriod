package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.CreditAmountBean;
import com.zejor.bean.OrderBean;
import com.zejor.bean.UpdateBean;

import java.util.HashMap;
import java.util.List;

public class MyContract {
    public interface View extends BaseContract.BaseView {

        void loadOrder(BaseEntity<List<OrderBean>> loginDataBean);

        void loadCreditAmount(BaseEntity<CreditAmountBean> creditAmountBean);

        void updateSuccess(BaseEntity<UpdateBean> updateBean);


    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getOrder(HashMap<String, String> paramter);

        void getCreditAmount(HashMap<String, String> paramter);

        void getUpdate(HashMap<String, String> paramter);


    }
}
