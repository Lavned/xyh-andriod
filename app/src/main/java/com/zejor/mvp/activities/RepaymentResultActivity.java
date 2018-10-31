package com.zejor.mvp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zejor.R;
import com.zejor.base.BaseActivity;
import com.zejor.component.ApplicationComponent;

import butterknife.BindView;
import butterknife.OnClick;

public class RepaymentResultActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;


    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_repayment_result;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }


    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        tvTitle.setText("还款结果");
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
