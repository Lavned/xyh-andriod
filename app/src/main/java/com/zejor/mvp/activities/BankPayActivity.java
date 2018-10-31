package com.zejor.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.R;
import com.zejor.base.BaseActivity;
import com.zejor.base.BaseEntity;
import com.zejor.bean.BankBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.mvp.contract.BankPayContract;
import com.zejor.mvp.fragments.ItemAlertFragment;
import com.zejor.mvp.presenter.BankPayPresenter;
import com.zejor.utils.AppUtils;
import com.zejor.utils.SharedPerferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class BankPayActivity extends BaseActivity<BankPayPresenter> implements BankPayContract.View {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_num)
    TextView tvBankNum;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;

    private List<BankBean> bankList;
    private int defaultBank = 0;
    private ItemAlertFragment itemsDialogFragment;
    private HashMap<String, String> cardMap;
    private String payAmount;
    private HashMap<String, String> codeParamter;
    private String orderNum;
    private String repayBizCode;
    private String repayWay;
    private String overdueDays;
    private String overdueFine;
    private HashMap<String, String> payParamter;
    private String rentDays;
    private String rentMoney;

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_bank_pay;
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
        tvTitle.setText("支付");
        payAmount = getIntent().getStringExtra("payAmount");
        tvRefund.setText("￥" + payAmount);
        bankList = new ArrayList<>();
        cardMap = new HashMap<>();
        cardMap.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
        cardMap.put("version", CommonValueUtil.VERSION);
        cardMap.put("softType", CommonValueUtil.SOFTTYPE);
        cardMap.put("funCode", FunCode.BANK_LIST);
        cardMap.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
        cardMap.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));

        itemsDialogFragment = new ItemAlertFragment();
        itemsDialogFragment.setItemClickListener(new ItemAlertFragment.OnItemClickListener() {
            @Override
            public void onDialogItemClick(int position) {
                if (position < bankList.size()) {
                    if (defaultBank != position) {
                        Toast.makeText(BankPayActivity.this, "切换成功", Toast.LENGTH_SHORT).show();
                        defaultBank = position;
                        tvBankName.setText(bankList.get(defaultBank).getBankName());
                        tvBankNum.setText(bankList.get(defaultBank).getBankCardNo());
                    }
                } else {
                    startActivity(new Intent(BankPayActivity.this, AddBankCardActivity.class));
                }
                itemsDialogFragment.dismiss();
            }
        });

        orderNum = getIntent().getStringExtra("orderNum");
        repayBizCode = getIntent().getStringExtra("repayBizCode");
        repayWay = getIntent().getStringExtra("repayWay");
        overdueDays = getIntent().getStringExtra("overdueDays");
        overdueFine = getIntent().getStringExtra("overdueFine");
        rentDays = getIntent().getStringExtra("rentDays");
        rentMoney = getIntent().getStringExtra("rentMoney");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getBankCard(cardMap);
    }


    @OnClick({R.id.iv_back, R.id.ll_bank_list, R.id.tv_send_code, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_bank_list:
                itemsDialogFragment.show(getFragmentManager(), bankList, defaultBank);
                break;
            case R.id.tv_send_code:
                sendCode();
                break;
            case R.id.tv_pay:
                pay();
                break;
        }
    }

    private void pay() {
        if (TextUtils.isEmpty(etCode.getText().toString())) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();
        payParamter = new HashMap<>();
        payParamter.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
        payParamter.put("version", CommonValueUtil.VERSION);
        payParamter.put("softType", CommonValueUtil.SOFTTYPE);
        payParamter.put("funCode", FunCode.REPAYMENT_PAY);
        payParamter.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
        payParamter.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));
        payParamter.put("tradeOrderNo", orderNum);//订单号
        payParamter.put("repayBizCode", repayBizCode);//02全额还款
        payParamter.put("repayWay", repayWay);//还款方式
        payParamter.put("rentDays", rentDays);//天数
        payParamter.put("rentMoney", rentMoney);//金额
        payParamter.put("overdueDays", overdueDays);//逾期
        payParamter.put("overdueFine", overdueFine);//逾期金额
        payParamter.put("totalMoney", payAmount);//总额
        payParamter.put("verifyCode", etCode.getText().toString());//验证码
        mPresenter.pay(payParamter);
    }

    private void sendCode() {
        if (TextUtils.isEmpty(tvBankNum.getText().toString())) {
            Toast.makeText(this, "请选择银行卡", Toast.LENGTH_SHORT).show();
            return;
        }
        AppUtils.tvSendCode(tvSendCode);
        showLoading();
        codeParamter = new HashMap<>();
        codeParamter.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
        codeParamter.put("version", CommonValueUtil.VERSION);
        codeParamter.put("softType", CommonValueUtil.SOFTTYPE);
        codeParamter.put("funCode", FunCode.BANKCODE);
        codeParamter.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
        codeParamter.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));
        codeParamter.put("tradeOrderNo", orderNum);//
        codeParamter.put("repayBizCode", repayBizCode);//02全额还款
        codeParamter.put("repayWay", repayWay);//还款方式
        codeParamter.put("rentDays", rentDays);//
        codeParamter.put("rentMoney", rentMoney);//续租金额
        codeParamter.put("overdueDays", overdueDays);//逾期天数
        codeParamter.put("overdueFine", overdueFine);//逾期金额
        codeParamter.put("totalMoney", payAmount);//还款总额
        codeParamter.put("bankAccount", tvBankNum.getText().toString());//卡号
        mPresenter.sendCode(codeParamter);
    }

    @Override
    public void loadBankCard(BaseEntity<List<BankBean>> bankBean) {
        if (bankBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            if (bankBean.getRetData() != null && bankBean.getRetData().size() != 0) {
                Logger.e(bankBean.toString());
                bankList = bankBean.getRetData();
                tvBankName.setText(bankList.get(defaultBank).getBankName());
                tvBankNum.setText(bankList.get(defaultBank).getBankCardNo());
            }
        }
    }

    @Override
    public void codeSuccess(BaseEntity<Object> object) {
        if (object.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            showSuccess("发送验证码成功");
        } else {
            showSuccess(object.getRetMsg());
        }
    }

    @Override
    public void paySuccess(BaseEntity<Object> object) {
        if (object.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            showSuccess(null);
            setResult(RESULT_OK);
            finish();
            App.getInstance().setToHome(true);
        } else {
            showSuccess(object.getRetMsg());
        }
    }

}

