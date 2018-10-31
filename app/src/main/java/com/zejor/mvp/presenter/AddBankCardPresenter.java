package com.zejor.mvp.presenter;

import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.base.BaseEntity;
import com.zejor.base.BasePresenter;
import com.zejor.bean.BankAscriptionBean;
import com.zejor.bean.SupportBankBean;
import com.zejor.contants.Api;
import com.zejor.encrypt_utils.ParameterEncryptionUtil;
import com.zejor.mvp.contract.AddBankCardContract;
import com.zejor.net.RxSchedulers;
import com.zejor.utils.ToastUtils;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class AddBankCardPresenter extends BasePresenter<AddBankCardContract.View> implements AddBankCardContract.Presenter {

    Api api;

    @Inject
    public AddBankCardPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void sendCardCode(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.bankCode(requestBody)
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
                        mView.onSendCardCodeSuccess(object);
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
    public void supportBankList(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.supportBank(requestBody)
                .compose(RxSchedulers.<BaseEntity<SupportBankBean>>applySchedulers())
                .compose(mView.<BaseEntity<SupportBankBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<SupportBankBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<SupportBankBean> supportBankBean) {
                        if (supportBankBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(supportBankBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.onSupportBankSusscee(supportBankBean);
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
    public void addCard(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.addBank(requestBody)
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
                        mView.onAddCardSuccess(object);
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
    public void checkCardUsed(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.checkCard(requestBody)
                .compose(RxSchedulers.<BaseEntity<BankAscriptionBean>>applySchedulers())
                .compose(mView.<BaseEntity<BankAscriptionBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<BankAscriptionBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<BankAscriptionBean> bankAscriptionBean) {
                        if (bankAscriptionBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(bankAscriptionBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.onCheckCardSuccess(bankAscriptionBean);
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
