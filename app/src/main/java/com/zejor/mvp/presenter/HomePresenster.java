package com.zejor.mvp.presenter;

import com.orhanobut.logger.Logger;
import com.zejor.base.BaseEntity;
import com.zejor.base.BasePresenter;
import com.zejor.bean.AdvertisementBean;
import com.zejor.bean.BannerBean;
import com.zejor.bean.OrderBean;
import com.zejor.bean.OrderChangeStatusBean;
import com.zejor.bean.RefreshBean;
import com.zejor.bean.UpdateBean;
import com.zejor.contants.Api;
import com.zejor.encrypt_utils.ParameterEncryptionUtil;
import com.zejor.mvp.contract.HomeContract;
import com.zejor.net.RxSchedulers;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class HomePresenster extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    Api api;

    @Inject
    public HomePresenster(Api api) {
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
    public void getScrollText(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.advertisementCall(requestBody)
                .compose(RxSchedulers.<BaseEntity<List<AdvertisementBean>>>applySchedulers())
                .compose(mView.<BaseEntity<List<AdvertisementBean>>>bindToLife())
                .subscribe(new Observer<BaseEntity<List<AdvertisementBean>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<List<AdvertisementBean>> advertisementBean) {
                        mView.loadAdvertisementCall(advertisementBean);
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
    public void getOrderStatus(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.orderChangeStatus(requestBody)
                .compose(RxSchedulers.<BaseEntity<OrderChangeStatusBean>>applySchedulers())
                .compose(mView.<BaseEntity<OrderChangeStatusBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<OrderChangeStatusBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<OrderChangeStatusBean> orderChangeStatusBean) {
                        mView.onOrderStatusSuccess(orderChangeStatusBean);
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

    @Override
    public void homeRefresh(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.homeRefresh(requestBody)
                .compose(RxSchedulers.<BaseEntity<RefreshBean>>applySchedulers())
                .compose(mView.<BaseEntity<RefreshBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<RefreshBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<RefreshBean> refreshBean) {
                        mView.refreshSuccess(refreshBean);
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
    public void getBannerData(HashMap<String, String> paramter) {
        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(paramter);
        api.bannerCall(requestBody)
                .compose(RxSchedulers.<BaseEntity<BannerBean>>applySchedulers())
                .compose(mView.<BaseEntity<BannerBean>>bindToLife())
                .subscribe(new Observer<BaseEntity<BannerBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<BannerBean> bannerBean) {
                        mView.refreshBanner(bannerBean);
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
