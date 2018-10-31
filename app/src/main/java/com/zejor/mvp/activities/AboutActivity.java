package com.zejor.mvp.activities;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zejor.R;
import com.zejor.base.BaseActivity;
import com.zejor.component.ApplicationComponent;
import com.zejor.utils.AppUtils;
import com.zejor.utils.PermissionUtils;
import com.zejor.utils.SharedPerferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_vision)
    TextView tvVision;
    @BindView(R.id.tv_qq1)
    TextView tvQq1;
    @BindView(R.id.tv_qq2)
    TextView tvQq2;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    private ClipboardManager cm;
    private ClipData mClipData;

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }


    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        tvVision.setText(AppUtils.getAppVersionName());
        tvTitle.setText("关于我们");
        cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);// 复制粘贴
    }



    @SuppressLint("MissingPermission")
    @OnClick({R.id.iv_back, R.id.tv_copy1, R.id.tv_copy2, R.id.tv_call, R.id.tv_copy3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_copy1:
                // 将ClipData内容放到系统剪贴板里。
                mClipData = ClipData.newPlainText("Label", tvQq1.getText().toString());
                cm.setPrimaryClip(mClipData);
                Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_copy2:
                mClipData = ClipData.newPlainText("Label", tvQq2.getText().toString());// 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_call:
                if (PermissionUtils.getCallPhonePermissions(this, 0)) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + tvPhone.getText().toString());
                    intent.setData(data);
                    startActivity(intent);
                }
                break;
            case R.id.tv_copy3:
                mClipData = ClipData.newPlainText("Label", tvPay.getText().toString());// 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
