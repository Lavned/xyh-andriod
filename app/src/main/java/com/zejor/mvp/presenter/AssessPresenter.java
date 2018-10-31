package com.zejor.mvp.presenter;


import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.base.BaseEntity;
import com.zejor.base.BasePresenter;
import com.zejor.bean.MobilePriceBean;
import com.zejor.contants.Api;
import com.zejor.encrypt_utils.ParameterEncryptionUtil;
import com.zejor.mvp.contract.AssessContract;
import com.zejor.net.RxSchedulers;
import com.zejor.utils.ToastUtils;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class AssessPresenter extends BasePresenter<AssessContract.View> implements AssessContract.Presenter {
    Api api;

    @Inject
    public AssessPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void getMobilePrice(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.mobilePrice(requestBody)
                .compose(RxSchedulers.<BaseEntity<MobilePriceBean>>applySchedulers())
                .compose(mView.<BaseEntity<MobilePriceBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<MobilePriceBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<MobilePriceBean> mobilePriceBean) {
                        if (mobilePriceBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(mobilePriceBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.loadMobilePrice(mobilePriceBean);
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
