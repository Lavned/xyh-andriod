package com.zejor.mvp.presenter;

import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.base.BaseEntity;
import com.zejor.base.BasePresenter;
import com.zejor.bean.BankBean;
import com.zejor.bean.CreditAmountBean;
import com.zejor.bean.ProcotolBean;
import com.zejor.contants.Api;
import com.zejor.encrypt_utils.ParameterEncryptionUtil;
import com.zejor.mvp.contract.WithdrawContract;
import com.zejor.net.RxSchedulers;
import com.zejor.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class WithdrawPresenter extends BasePresenter<WithdrawContract.View> implements WithdrawContract.Presenter {

    Api api;

    @Inject
    public WithdrawPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void getBankCard(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.getBankList(requestBody)
                .compose(RxSchedulers.<BaseEntity<List<BankBean>>>applySchedulers())
                .compose(mView.<BaseEntity<List<BankBean>>>bindToLife())
                .subscribe(new Observer<BaseEntity<List<BankBean>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<List<BankBean>> bankBean) {
                        if (bankBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(bankBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.loadBankCard(bankBean);
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
    public void getCreditAmount(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.getCreditAmount(requestBody)
                .compose(RxSchedulers.<BaseEntity<CreditAmountBean>>applySchedulers())
                .compose(mView.<BaseEntity<CreditAmountBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<CreditAmountBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<CreditAmountBean> creditAmountBean) {
                        if (creditAmountBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(creditAmountBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.loadCreditAmount(creditAmountBean);
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
    public void doWithDraw(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.withDraw(requestBody)
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
                        mView.withDrawSuccess(object);
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
                        if (procotolBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(procotolBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
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
