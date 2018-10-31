package com.zejor.mvp.presenter;

import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.base.BaseEntity;
import com.zejor.base.BasePresenter;
import com.zejor.contants.Api;
import com.zejor.encrypt_utils.ParameterEncryptionUtil;
import com.zejor.mvp.contract.SendBackContract;
import com.zejor.net.RxSchedulers;
import com.zejor.utils.ToastUtils;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class SendBackPresenter extends BasePresenter<SendBackContract.View> implements SendBackContract.Presenter {
    Api api;

    @Inject
    public SendBackPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void sendMail(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.expressOrder(requestBody)
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
                        mView.loadData(object);
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
