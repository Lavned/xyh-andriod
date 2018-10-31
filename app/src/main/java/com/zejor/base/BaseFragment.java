package com.zejor.base;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zejor.App;
import com.zejor.mvp.activities.MainActivity;
import com.zejor.utils.LoadingDialogUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T1 extends BaseContract.BasePresenter> extends RxFragment implements IBase, BaseContract.BaseView {


    protected View mRootView;
    protected Context mContext;
    Unbinder unbinder;

    @Nullable
    @Inject
    protected T1 mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = createView(inflater, container, savedInstanceState);
        }

        mContext = mRootView.getContext();
        return mRootView;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!isNetworkConnected(getActivity())){
            Toast.makeText(getActivity(),"当前网络无连接，请检查网络",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInjector(App.getInstance().getApplicationComponent());
        attachView();
        bindView(view, savedInstanceState);
        initData();
    }


    @Nullable
    @Override
    public View getView() {
        return mRootView;
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void showLoading() {
        LoadingDialogUtil.getInstance().showDialog(getActivity());
    }

    @Override
    public void showSuccess(@Nullable String msg) {
        if (LoadingDialogUtil.getInstance().isShowing()) {
            LoadingDialogUtil.getInstance().stopDialog();
        }
        if (msg != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showFaild() {
        if (LoadingDialogUtil.getInstance().isShowing()) {
            LoadingDialogUtil.getInstance().stopDialog();
        }
        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoNet() {
        if (LoadingDialogUtil.getInstance().isShowing()) {
            LoadingDialogUtil.getInstance().stopDialog();
        }
    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}