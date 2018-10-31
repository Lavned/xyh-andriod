package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.BankAscriptionBean;
import com.zejor.bean.SupportBankBean;

import java.util.HashMap;

public class AddBankCardContract {
    public interface View extends BaseContract.BaseView {

        void onCheckCardSuccess(BaseEntity<BankAscriptionBean> commitInfoBean);

        void onSendCardCodeSuccess(BaseEntity<Object> commitInfoBean);

        void onAddCardSuccess(BaseEntity<Object> commitInfoBean);

        void onSupportBankSusscee(BaseEntity<SupportBankBean> supportBankBean);

    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        /*校验银行卡是否可用*/
        void checkCardUsed(HashMap<String, String> paramter);

        /*发送验证码*/
        void sendCardCode(HashMap<String, String> paramter);

        /*添加银行卡*/
        void addCard(HashMap<String, String> paramter);

        /*支持银行卡列表*/
        void supportBankList(HashMap<String, String> paramter);
    }
}
