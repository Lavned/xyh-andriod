package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.CommitInfoBean;
import com.zejor.bean.SellCompleteBean;

import java.util.HashMap;

public class CompleteInfoContract {
    public interface View extends BaseContract.BaseView {

        void loadInfo(BaseEntity<CommitInfoBean> commitInfoBean);

        void loadData(BaseEntity<SellCompleteBean> sellCompleteBean);

        void contactsCallSuccess(BaseEntity<String> contactsCall);
    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void commitInfo(HashMap<String, String> paramter);

        void uploadData(HashMap<String, String> paramter);

        void contactsCall(HashMap<String, String> paramter);

    }
}
