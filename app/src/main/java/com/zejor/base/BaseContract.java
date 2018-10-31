package com.zejor.base;

import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by mango on 2018/5/16.
 */

public class BaseContract {
    public interface BasePresenter<T extends BaseContract.BaseView> {

        void attachView(T view);

        void detachView();
    }


    public interface BaseView {

        void showLoading();

        void showSuccess(@Nullable String msg);

        void showFaild();

        void showNoNet();

        void onRetry();

        /**
         * 生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();
    }
}
