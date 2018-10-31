package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.BankBean;
import com.zejor.bean.CreditAmountBean;
import com.zejor.bean.ProcotolBean;

import java.util.HashMap;
import java.util.List;

public class WithdrawContract {
    public interface View extends BaseContract.BaseView {

        void loadBankCard(BaseEntity<List<BankBean>> bankBean);

        void loadCreditAmount(BaseEntity<CreditAmountBean> creditAmountBean);

        void withDrawSuccess(BaseEntity<Object> object);

        void onProtocolSuccess(BaseEntity<ProcotolBean> Object);
    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getBankCard(HashMap<String, String> paramter);

        void getCreditAmount(HashMap<String, String> paramter);

        void doWithDraw(HashMap<String, String> paramter);

        void getProtocol(HashMap<String, String> paramter);
    }
}
