package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.OrderBean;
import com.zejor.bean.PayBean;
import com.zejor.bean.RepaymentPayBean;

import java.util.HashMap;
import java.util.List;

public class RenewalContract {
    public interface View extends BaseContract.BaseView {

        void loadOrder(BaseEntity<List<OrderBean>> loginDataBean);

        void loadPayMode(BaseEntity<RepaymentPayBean> repaymentPayBean);

        void loadPay(BaseEntity<PayBean> payBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getOrder(HashMap<String, String> paramter);

        void getPayMode(HashMap<String, String> paramter);

        void toPay(HashMap<String, String> paramter);
    }
}
