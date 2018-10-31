package com.zejor.mvp.contract;

import com.zejor.base.BaseContract;
import com.zejor.base.BaseEntity;
import com.zejor.bean.AuthenticationBean;
import com.zejor.bean.CommitFaceInfoBean;
import com.zejor.bean.FaceBean;
import com.zejor.bean.ZhiMaBean;

import java.util.HashMap;
import java.util.List;

public class CompleteAllDataContract {
    public interface View extends BaseContract.BaseView {

        void refreshFaceData(BaseEntity<FaceBean> faceBean);

        void loadFaceInfo(BaseEntity<CommitFaceInfoBean> commitFaceInfoBean);

        void refreshListData(BaseEntity<List<AuthenticationBean>> commitFaceInfoBean);

        void loadOrder(BaseEntity<Object> object);

        void onZhimaSuccess(BaseEntity<ZhiMaBean> zhiMaBean);

        void onTaskIdSuccess(BaseEntity<String> taskIdBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getFaceData(HashMap<String, String> paramter);

        void commitFaceInfoData(HashMap<String, String> paramter);

        void getAuthenticationData(HashMap<String, String> paramter);

        void commitOrder(HashMap<String, String> paramter);

        void getZhiMaUrl(HashMap<String, String> paramter);

        void updateTaskId(HashMap<String, String> paramter);

    }
}
