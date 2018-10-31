package com.zejor.mvp.presenter;


import com.orhanobut.logger.Logger;
import com.zejor.base.BaseEntity;
import com.zejor.base.BasePresenter;
import com.zejor.bean.LoginDataBean;
import com.zejor.bean.ProcotolBean;
import com.zejor.contants.Api;
import com.zejor.encrypt_utils.ParameterEncryptionUtil;
import com.zejor.mvp.contract.LoginContract;
import com.zejor.net.RxSchedulers;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;


public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    Api api;

    @Inject
    public LoginPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void getData(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.userLogin(requestBody)
                .compose(RxSchedulers.<BaseEntity<LoginDataBean>>applySchedulers())
                .compose(mView.<BaseEntity<LoginDataBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<LoginDataBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<LoginDataBean> loginDataBean) {
                        mView.loadData(loginDataBean);
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
    public void sendCode(HashMap<String, String> paramter) {

        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.sendCode(requestBody)
                .compose(RxSchedulers.<BaseEntity<Object>>applySchedulers())
                .compose(mView.<BaseEntity<Object>>bindToLife())
                .subscribe(new Observer<BaseEntity<Object>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<Object> object) {
                        mView.getCode(object);
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
    public void savePhoneMessage(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.savePhoneMessage(requestBody)
                .compose(RxSchedulers.<BaseEntity<Object>>applySchedulers())
                .compose(mView.<BaseEntity<Object>>bindToLife())
                .subscribe(new Observer<BaseEntity<Object>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<Object> object) {
                        mView.getPhoneMessage(object);
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
    public void getProtocol(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.getProtocol(requestBody)
                .compose(RxSchedulers.<BaseEntity<ProcotolBean>>applySchedulers())
                .compose(mView.<BaseEntity<ProcotolBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<ProcotolBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<ProcotolBean> procotolBean) {
                        mView.onProtocolSuccess(procotolBean);
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
