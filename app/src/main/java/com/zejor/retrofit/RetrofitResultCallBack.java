package com.zejor.retrofit;

import android.content.Context;


import com.zejor.App;
import com.zejor.R;
import com.zejor.base.BaseEntity;
import com.zejor.customview.SweetAlertDialog;
import com.zejor.utils.ToastUtils;

import cn.tongdun.android.shell.utils.LogUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class RetrofitResultCallBack<T extends BaseEntity> implements Callback<T> {

    private Context mContext = App.getInstance();

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        SweetAlertDialog.getInstance().stopDialog();
        int code1 = response.code();
        if (200 != code1) {
            ToastUtils.showToast(mContext, code1 + mContext.getResources().getString(R.string.netErrText));
            onFailure(new Throwable(mContext.getResources().getString(R.string.codeError) + code1 + "---" + mContext.getResources().getString(R.string.netErrText)));
            return;
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        LogUtil.e("网络错误:" + t.getMessage());
        ToastUtils.showToast(mContext, "网络错误:" + t.getMessage());
        onFailure(t);
        SweetAlertDialog.getInstance().stopDialog();
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable throwable);

}
