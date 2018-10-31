package com.zejor.mvp.presenter;

import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.base.BaseEntity;
import com.zejor.base.BasePresenter;
import com.zejor.bean.AuthenticationBean;
import com.zejor.bean.CommitFaceInfoBean;
import com.zejor.bean.FaceBean;
import com.zejor.bean.ZhiMaBean;
import com.zejor.contants.Api;
import com.zejor.encrypt_utils.ParameterEncryptionUtil;
import com.zejor.mvp.contract.CompleteAllDataContract;
import com.zejor.net.RxSchedulers;
import com.zejor.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class CompleteAllDataPresenter extends BasePresenter<CompleteAllDataContract.View> implements CompleteAllDataContract.Presenter {
    Api api;

    @Inject
    public CompleteAllDataPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void getFaceData(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.faceCall(requestBody)
                .compose(RxSchedulers.<BaseEntity<FaceBean>>applySchedulers())
                .compose(mView.<BaseEntity<FaceBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<FaceBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<FaceBean> faceBean) {
                        if (faceBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(faceBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.refreshFaceData(faceBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showFaild();
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void commitFaceInfoData(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.faceInfoCall(requestBody)
                .compose(RxSchedulers.<BaseEntity<CommitFaceInfoBean>>applySchedulers())
                .compose(mView.<BaseEntity<CommitFaceInfoBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<CommitFaceInfoBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<CommitFaceInfoBean> commitFaceInfoBean) {
                        if (commitFaceInfoBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(commitFaceInfoBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.loadFaceInfo(commitFaceInfoBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showFaild();
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getAuthenticationData(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.authenticationCall(requestBody)
                .compose(RxSchedulers.<BaseEntity<List<AuthenticationBean>>>applySchedulers())
                .compose(mView.<BaseEntity<List<AuthenticationBean>>>bindToLife())
                .subscribe(new Observer<BaseEntity<List<AuthenticationBean>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<List<AuthenticationBean>> authenticationBean) {
                        if (authenticationBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(authenticationBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.refreshListData(authenticationBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showFaild();
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void commitOrder(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.commitOrder(requestBody)
                .compose(RxSchedulers.<BaseEntity<Object>>applySchedulers())
                .compose(mView.<BaseEntity<Object>>bindToLife())
                .subscribe(new Observer<BaseEntity<Object>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<Object> object) {
                        if (object.getRetCode().equals("2001")) {
                            ToastUtils.showToast(object.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.loadOrder(object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showFaild();
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getZhiMaUrl(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.zhiMaCall(requestBody)
                .compose(RxSchedulers.<BaseEntity<ZhiMaBean>>applySchedulers())
                .compose(mView.<BaseEntity<ZhiMaBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<ZhiMaBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<ZhiMaBean> zhiMaBean) {
                        if (zhiMaBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(zhiMaBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.onZhimaSuccess(zhiMaBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showFaild();
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void updateTaskId(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.taskIdCall(requestBody)
                .compose(RxSchedulers.<BaseEntity<String>>applySchedulers())
                .compose(mView.<BaseEntity<String>>bindToLife())
                .subscribe(new Observer<BaseEntity<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<String> string) {
                        if (string.getRetCode().equals("2001")) {
                            ToastUtils.showToast(string.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.onTaskIdSuccess(string);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showFaild();
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
