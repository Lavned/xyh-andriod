package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.BankBean;
import com.zejor.bean.OrderBean;
import com.zejor.bean.OrderDetailBean;
import com.zejor.bean.PayBean;
import com.zejor.bean.RepaymentMoneyBean;
import com.zejor.bean.RepaymentPayBean;

import java.util.HashMap;
import java.util.List;

public class OrderContract {
    public interface View extends BaseContract.BaseView {
        void loadBankCard(BaseEntity<List<BankBean>> bankBean);

        void loadOrder(BaseEntity<List<OrderBean>> loginDataBean);

        void cancelResult(BaseEntity<Object> object);

        void loadMoney(BaseEntity<RepaymentMoneyBean> repaymentMoneyBean);

        void loadPayMode(BaseEntity<RepaymentPayBean> repaymentPayBean);

        void loadPay(BaseEntity<PayBean> payBean);

        void loadDetail(BaseEntity<OrderDetailBean> orderDetailBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getOrder(HashMap<String, String> paramter);

        void cancelOrder(HashMap<String, String> paramter);

        void getMoney(HashMap<String, String> paramter);

        void getPayMode(HashMap<String, String> paramter);

        void toPay(HashMap<String, String> paramter);

        void getOrderDetail(HashMap<String, String> paramter);

        void getBankCard(HashMap<String, String> paramter);
    }
}
