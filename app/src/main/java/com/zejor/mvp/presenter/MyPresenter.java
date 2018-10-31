package com.zejor.mvp.presenter;

import com.orhanobut.logger.Logger;
import com.zejor.base.BaseEntity;
import com.zejor.base.BasePresenter;
import com.zejor.bean.CreditAmountBean;
import com.zejor.bean.OrderBean;
import com.zejor.bean.UpdateBean;
import com.zejor.contants.Api;
import com.zejor.encrypt_utils.ParameterEncryptionUtil;
import com.zejor.mvp.contract.MyContract;
import com.zejor.net.RxSchedulers;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class MyPresenter extends BasePresenter<MyContract.View> implements MyContract.Presenter {
    Api api;

    @Inject
    public MyPresenter(Api api) {
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
    public void getUpdate(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.getUpdate(requestBody)
                .compose(RxSchedulers.<BaseEntity<UpdateBean>>applySchedulers())
                .compose(mView.<BaseEntity<UpdateBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<UpdateBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<UpdateBean> updateBean) {
                        mView.updateSuccess(updateBean);
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
