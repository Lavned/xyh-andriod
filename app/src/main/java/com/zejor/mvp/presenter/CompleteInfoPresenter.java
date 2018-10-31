package com.zejor.mvp.presenter;

import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.base.BaseEntity;
import com.zejor.base.BasePresenter;
import com.zejor.bean.CommitInfoBean;
import com.zejor.bean.SellCompleteBean;
import com.zejor.contants.Api;
import com.zejor.contants.ApiService;
import com.zejor.encrypt_utils.ParameterEncryptionUtil;
import com.zejor.mvp.contract.CompleteInfoContract;
import com.zejor.net.RxSchedulers;
import com.zejor.retrofit.RetrofitFactory;
import com.zejor.retrofit.RetrofitResultCallBack;
import com.zejor.utils.ToastUtils;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;
import retrofit2.Call;

public class CompleteInfoPresenter extends BasePresenter<CompleteInfoContract.View> implements CompleteInfoContract.Presenter {

    Api api;

    @Inject
    public CompleteInfoPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void commitInfo(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.commitCall(requestBody)
                .compose(RxSchedulers.<BaseEntity<CommitInfoBean>>applySchedulers())
                .compose(mView.<BaseEntity<CommitInfoBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<CommitInfoBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<CommitInfoBean> commitInfoBean) {
                        if (commitInfoBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(commitInfoBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.loadInfo(commitInfoBean);
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
    public void uploadData(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.sellComplete(requestBody)
                .compose(RxSchedulers.<BaseEntity<SellCompleteBean>>applySchedulers())
                .compose(mView.<BaseEntity<SellCompleteBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<SellCompleteBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<SellCompleteBean> sellCompleteBean) {
                        if (sellCompleteBean.getRetCode().equals("2001")) {
                            ToastUtils.showToast(sellCompleteBean.getRetMsg());
                            App.getInstance().loginAgain();
                        }
                        mView.loadData(sellCompleteBean);
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
    public void contactsCall(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);

        Call<BaseEntity<String>> call = RetrofitFactory.getInstance().getRetrofit().create(ApiService.class).contactsCall(requestBody);
        call.enqueue(new RetrofitResultCallBack<BaseEntity<String>>() {
            @Override
            public void onSuccess(BaseEntity<String> baseEntity) {
                if (baseEntity.getRetCode().equals("2001")) {
                    ToastUtils.showToast(baseEntity.getRetMsg());
                    App.getInstance().loginAgain();
                }
                mView.contactsCallSuccess(baseEntity);
            }

            @Override
            public void onFailure(Throwable throwable) {
                mView.showFaild();
                Logger.e(throwable.getMessage());
            }
        });
//        api.contactsCall(requestBody)
//                .compose(RxSchedulers.<BaseEntity<String>>applySchedulers())
//                .compose(mView.<BaseEntity<String>>bindToLife())
//                .subscribe(new Observer<BaseEntity<String>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(@NonNull BaseEntity<String> contactsCall) {
//                        if (contactsCall.getRetCode().equals("2001")) {
//                            ToastUtils.showToast(contactsCall.getRetMsg());
//                            App.getInstance().loginAgain();
//                        }
//                        mView.contactsCallSuccess(contactsCall);
//                    }
//
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        mView.showFaild();
//                        Logger.e(e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//

    }
}
