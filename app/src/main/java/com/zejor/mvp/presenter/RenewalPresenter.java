package com.zejor.mvp.presenter;

import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.base.BaseEntity;
import com.zejor.base.BasePresenter;
import com.zejor.bean.OrderBean;
import com.zejor.bean.PayBean;
import com.zejor.bean.RepaymentPayBean;
import com.zejor.contants.Api;
import com.zejor.encrypt_utils.ParameterEncryptionUtil;
import com.zejor.mvp.contract.RenewalContract;
import com.zejor.net.RxSchedulers;
import com.zejor.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class RenewalPresenter extends BasePresenter<RenewalContract.View> implements RenewalContract.Presenter {

    Api api;

    @Inject
    public RenewalPresenter(Api api) {
        this.api = api;
    }


    @Override
    public void getOrder(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.getOrder(requestBody)
                .compose(RxSchedulers.<BaseEntity<List<OrderBean>>>applySchedulers())
                .compose(mView.<BaseEntity<List<OrderBean>>>bindToLife())
                .subscribe(new Observer<BaseEntity<List<OrderBean>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<List<OrderBean>> orderBean) {
                        if (orderBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(orderBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.loadOrder(orderBean);
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
    public void getPayMode(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.payMode(requestBody)
                .compose(RxSchedulers.<BaseEntity<RepaymentPayBean>>applySchedulers())
                .compose(mView.<BaseEntity<RepaymentPayBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<RepaymentPayBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<RepaymentPayBean> repaymentPayBean) {
                        if (repaymentPayBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(repaymentPayBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.loadPayMode(repaymentPayBean);
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
    public void toPay(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.toPay(requestBody)
                .compose(RxSchedulers.<BaseEntity<PayBean>>applySchedulers())
                .compose(mView.<BaseEntity<PayBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<PayBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<PayBean> payBean) {
                        if (payBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(payBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.loadPay(payBean);
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
