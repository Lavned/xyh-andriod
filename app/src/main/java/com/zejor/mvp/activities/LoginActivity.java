package com.zejor.mvp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ishumei.smantifraud.SmAntiFraud;
import com.orhanobut.logger.Logger;
import com.zejor.R;
import com.zejor.Utils;
import com.zejor.base.BaseActivity;
import com.zejor.base.BaseEntity;
import com.zejor.bean.LoginDataBean;
import com.zejor.bean.ProcotolBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.mvp.contract.LoginContract;
import com.zejor.mvp.presenter.LoginPresenter;
import com.zejor.utils.AppUtils;
import com.zejor.utils.SharedPerferenceUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.tongdun.android.shell.FMAgent;

import static com.zejor.contants.FunCode.REGISTER_PROTOCOL;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private HashMap<String, String> paramter;
    private HashMap<String, String> phoneParamter;
    private HashMap<String, String> agreementUrlMap;

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }


    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        tvTitle.setText("登录");
        ivBack.setImageResource(R.drawable.ic_denglu_cancel);
        toProtocol();
        cb.setChecked(true);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvLogin.setBackgroundColor(getResources().getColor(R.color.green));
                    tvLogin.setTextColor(getResources().getColor(R.color.white));
                    tvLogin.setClickable(true);
                } else {
                    tvLogin.setBackgroundColor(getResources().getColor(R.color.division));
                    tvLogin.setTextColor(getResources().getColor(R.color.green));
                    tvLogin.setClickable(false);
                }
            }
        });
    }

    @Override
    public void loadData(BaseEntity<LoginDataBean> loginDataBean) {
        Logger.e(loginDataBean.toString());
        if (loginDataBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            showSuccess("登录成功");
            SharedPerferenceUtil.saveData(this, "isLogin", true);
            SharedPerferenceUtil.saveData(this, "idCardNo", loginDataBean.getRetData().getCardId() == null ? "" : loginDataBean.getRetData().getCardId());
            SharedPerferenceUtil.saveData(this, "mobile", loginDataBean.getRetData().getMobile());
            SharedPerferenceUtil.saveData(this, "realName", loginDataBean.getRetData().getRealName() == null ? "" : loginDataBean.getRetData().getRealName());
            SharedPerferenceUtil.saveData(this, "redPoint", loginDataBean.getRetData().getRedPoint());
            SharedPerferenceUtil.saveData(this, "tokenId", loginDataBean.getRetData().getTokenId());
            SharedPerferenceUtil.saveData(this, "userId", loginDataBean.getRetData().getUserId());
            phoneParamter = new HashMap<>();
            phoneParamter.put("mobile", loginDataBean.getRetData().getMobile());
            phoneParamter.put("version", CommonValueUtil.VERSION);
            phoneParamter.put("softType", CommonValueUtil.SOFTTYPE);
            phoneParamter.put("funCode", FunCode.SAVE_PHONE_MESSAGE);
            phoneParamter.put("userId", loginDataBean.getRetData().getUserId());
            phoneParamter.put("tokenId", loginDataBean.getRetData().getTokenId());
            phoneParamter.put("mobileModels", CommonValueUtil.MOBILE_MODELS);
            phoneParamter.put("mobileMemory", CommonValueUtil.MOBILE_MEMORY);
            phoneParamter.put("imei", CommonValueUtil.IMEI);
            mPresenter.savePhoneMessage(phoneParamter);
            finish();
        } else {
            showSuccess(loginDataBean.getRetMsg());
        }
    }

    @Override
    public void getCode(BaseEntity<Object> Object) {
        Logger.e(Object + "");
        showSuccess("发送验证码成功");
        etCode.setFocusable(true);
        etCode.setFocusableInTouchMode(true);
        etCode.requestFocus();
    }

    private void toProtocol() {
        SpannableStringBuilder builder = new SpannableStringBuilder(tvProtocol.getText().toString());
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);
        UnderlineSpan lineSpan = new UnderlineSpan();
        builder.setSpan(lineSpan, 0, 8, getResources().getColor(R.color.green));  //下划线
        builder.setSpan(blueSpan, 0, 8, getResources().getColor(R.color.green));  //字体颜色
        tvProtocol.setText(builder);
    }

    @Override
    public void getPhoneMessage(BaseEntity<Object> object) {
        Logger.e(object.toString());
    }

    @Override
    public void onProtocolSuccess(BaseEntity<ProcotolBean> Object) {
        if (Object.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            Intent intent = new Intent(this, WebViewActivity.class)
                    .putExtra("url", Object.getRetData().getUserProtocol());
            startActivity(intent);

        } else {
            showSuccess("请求失败");
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_code, R.id.tv_login, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_code:
                if (!Utils.isMobile(etPhone.getText().toString())) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoading();
                AppUtils.tvSendCode(tvCode);
                paramter = new HashMap<>();
                paramter.put("mobile", etPhone.getText().toString());
                paramter.put("version", CommonValueUtil.VERSION);
                paramter.put("softType", CommonValueUtil.SOFTTYPE);
                paramter.put("funCode", FunCode.SENDCODE);
                paramter.put("businessCode", "03");
                mPresenter.sendCode(paramter);
                break;
            case R.id.tv_login:
                if (!Utils.isMobile(etPhone.getText().toString())) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etCode.getText().toString().length() != 6) {
                    Toast.makeText(this, "请输入6位验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoading();
                paramter = new HashMap<>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String blackBox = FMAgent.onEvent(LoginActivity.this);
                        String userDeviceId = SmAntiFraud.getDeviceId();
                        paramter.put("mobile", "" + etPhone.getText().toString());
                        paramter.put("version", "" + CommonValueUtil.VERSION);
                        paramter.put("softType", "" + CommonValueUtil.SOFTTYPE);
                        paramter.put("funCode", FunCode.USERLOGIN);
                        paramter.put("blackBox", "" + blackBox);
                        //验证码
                        paramter.put("authCode", etCode.getText().toString());
                        //设备ID
                        paramter.put("userDeviceId", userDeviceId);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.getData(paramter);
                            }
                        });
                    }
                }).start();
                break;
            case R.id.tv_protocol:
                agreementUrlMap = new HashMap<>();
                agreementUrlMap.put("mobile", "");
                agreementUrlMap = new HashMap<>();
                agreementUrlMap.put("type", "1");
                agreementUrlMap.put("version", CommonValueUtil.VERSION);
                agreementUrlMap.put("softType", CommonValueUtil.SOFTTYPE);
                agreementUrlMap.put("funCode", REGISTER_PROTOCOL);
                mPresenter.getProtocol(agreementUrlMap);
                break;
            default:
                break;
        }
    }

}
