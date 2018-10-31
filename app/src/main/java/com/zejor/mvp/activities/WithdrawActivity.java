package com.zejor.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.R;
import com.zejor.base.BaseActivity;
import com.zejor.base.BaseEntity;
import com.zejor.bean.BankBean;
import com.zejor.bean.CreditAmountBean;
import com.zejor.bean.ProcotolBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.mvp.contract.WithdrawContract;
import com.zejor.mvp.fragments.ItemAlertFragment;
import com.zejor.mvp.presenter.WithdrawPresenter;
import com.zejor.utils.SharedPerferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawActivity extends BaseActivity<WithdrawPresenter> implements WithdrawContract.View {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_card_list)
    LinearLayout llCardList;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.ll_default_bank)
    RelativeLayout llDefaultBank;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_num)
    TextView tvBankNum;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    @BindView(R.id.ll_procotol)
    LinearLayout llProcotol;
    private HashMap<String, String> cardMap;
    private List<BankBean> bankList;
    private HashMap<String, String> creditAmountMap;
    private String creditAmount;
    private HashMap<String, String> withDrawMap;
    private HashMap<String, String> agreementUrlMap;
    private String[] protocols = {"mobile_recovery_protocol", "mobile_repurchase_agreement"};
    private String[] protocolNames = {"《贷款协议》", "《信用花贷款协议》"};
    private ItemAlertFragment itemsDialogFragment;
    private int defaultBank = 0;

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_withdraw;
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
        tvTitle.setText("提现");
        tvProtocol.setText(protocolNames[getIntent().getIntExtra("recovery", 0)]);
        cb.setChecked(true);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvWithdraw.setBackgroundColor(getResources().getColor(R.color.green));
                    tvWithdraw.setTextColor(getResources().getColor(R.color.white));
                    tvWithdraw.setClickable(true);
                } else {
                    tvWithdraw.setBackgroundColor(getResources().getColor(R.color.division));
                    tvWithdraw.setTextColor(getResources().getColor(R.color.green));
                    tvWithdraw.setClickable(false);
                }
            }
        });
        itemsDialogFragment = new ItemAlertFragment();
        itemsDialogFragment.setItemClickListener(new ItemAlertFragment.OnItemClickListener() {
            @Override
            public void onDialogItemClick(int position) {
                if (position < bankList.size()) {
//                            itemsDialogFragment.updateDefault(position);
                    if (defaultBank != position) {
                        Toast.makeText(WithdrawActivity.this, "切换成功", Toast.LENGTH_SHORT).show();
                        defaultBank = position;
                        tvBankName.setText(bankList.get(defaultBank).getBankName());
                        tvBankNum.setText(bankList.get(defaultBank).getBankCardNo());
                    }
                } else {
                    startActivity(new Intent(WithdrawActivity.this, AddBankCardActivity.class));
                }
                itemsDialogFragment.dismiss();
            }
        });

        bankList = new ArrayList<>();
        cardMap = new HashMap<>();
        cardMap.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
        cardMap.put("version", CommonValueUtil.VERSION);
        cardMap.put("softType", CommonValueUtil.SOFTTYPE);
        cardMap.put("funCode", FunCode.BANK_LIST);
        cardMap.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
        cardMap.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));

        creditAmountMap = new HashMap<>();
        creditAmountMap.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
        creditAmountMap.put("version", CommonValueUtil.VERSION);
        creditAmountMap.put("softType", CommonValueUtil.SOFTTYPE);
        creditAmountMap.put("funCode", FunCode.CREDIT_AMOUNT);
        creditAmountMap.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
        creditAmountMap.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));
        mPresenter.getCreditAmount(creditAmountMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getBankCard(cardMap);
    }

    @OnClick({R.id.iv_back, R.id.ll_card_list, R.id.tv_withdraw, R.id.ll_default_bank, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_card_list:

                startActivity(new Intent(WithdrawActivity.this, AddBankCardActivity.class));
                break;
            case R.id.ll_default_bank:
                itemsDialogFragment.show(getFragmentManager(), bankList, defaultBank);

                break;
            case R.id.tv_withdraw:
                if (creditAmount.equals("0")) {
                    Toast.makeText(this, "暂无可用金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bankList.size() != 0) {
                    if (creditAmount != null) {
                        showLoading();
                        withDrawMap = new HashMap<>();
                        withDrawMap.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
                        withDrawMap.put("version", CommonValueUtil.VERSION);
                        withDrawMap.put("softType", CommonValueUtil.SOFTTYPE);
                        withDrawMap.put("funCode", FunCode.WITHDRAW);
                        withDrawMap.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
                        withDrawMap.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));
                        withDrawMap.put("agreeCode", "qzbk");
                        withDrawMap.put("agreementType", protocols[getIntent().getIntExtra("recovery", 0)]);
                        withDrawMap.put("clientBankName", bankList.get(0).getBankName());
                        withDrawMap.put("bankName", bankList.get(defaultBank).getBankName());
                        withDrawMap.put("clientBankNum", bankList.get(defaultBank).getBankCardNo());
                        withDrawMap.put("bankAccount", bankList.get(defaultBank).getBankCardNo());
                        withDrawMap.put("purchasePrice", creditAmount);
                        withDrawMap.put("arrivalAmount", creditAmount);
                        mPresenter.doWithDraw(withDrawMap);
                    } else {
                        Toast.makeText(this, "请稍后点击重试", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请先绑定银行卡", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_protocol:
                if (bankList.size() == 0) {
                    Toast.makeText(this, "请先绑定银行卡", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (creditAmount == null) {
                    Toast.makeText(this, "请稍后点击重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                agreementUrlMap = new HashMap<>();
                agreementUrlMap.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
                agreementUrlMap.put("version", CommonValueUtil.VERSION);
                agreementUrlMap.put("softType", CommonValueUtil.SOFTTYPE);
                agreementUrlMap.put("funCode", FunCode.AGREEMENTS);
                agreementUrlMap.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
                agreementUrlMap.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));
                agreementUrlMap.put("agreeCode", "xyh");
                agreementUrlMap.put("agreementType", "loanProtocol");
                agreementUrlMap.put("clientBankName", bankList.get(defaultBank).getBankName());
                agreementUrlMap.put("clientBankNum", bankList.get(defaultBank).getBankCardNo());
                agreementUrlMap.put("purchasePrice", creditAmount);
                mPresenter.getProtocol(agreementUrlMap);
                break;
        }
    }

    @Override
    public void loadBankCard(BaseEntity<List<BankBean>> bankBean) {
        if (bankBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            if (bankBean.getRetData() != null && bankBean.getRetData().size() != 0) {
                Logger.e(bankBean.toString());
                bankList = bankBean.getRetData();
                llCardList.setVisibility(View.GONE);
                llDefaultBank.setVisibility(View.VISIBLE);
                tvBankName.setText(bankList.get(defaultBank).getBankName());
                tvBankNum.setText(bankList.get(defaultBank).getBankCardNo());
            }
        }
    }

    @Override
    public void loadCreditAmount(BaseEntity<CreditAmountBean> creditAmountBean) {
        if (creditAmountBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            creditAmount = creditAmountBean.getRetData().getCreditAmount();
            if (creditAmount.equals("0")) {
                llProcotol.setVisibility(View.INVISIBLE);
            }
            tvMoney.setText("￥" + creditAmount);
        } else {
            showSuccess(creditAmountBean.getRetMsg());
        }
    }

    @Override
    public void withDrawSuccess(BaseEntity<Object> object) {
        if (object.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            showSuccess("提现成功");
            App.getInstance().setToHome(true);
            finish();
        } else {
            showSuccess(object.getRetMsg());
        }
    }

    @Override
    public void onProtocolSuccess(BaseEntity<ProcotolBean> Object) {
        if (Object.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            Intent intent = new Intent(this, PdfActivity.class)
                    .putExtra("url", Object.getRetData().getUserProtocol())
                    .putExtra("title", tvProtocol.getText().toString());
            startActivity(intent);

        } else {
            showSuccess("请求失败");
        }
    }

}
