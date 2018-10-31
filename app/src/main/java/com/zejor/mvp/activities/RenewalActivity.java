package com.zejor.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.R;
import com.zejor.adapter.RepaymentPayAdapter;
import com.zejor.base.BaseActivity;
import com.zejor.base.BaseEntity;
import com.zejor.bean.OrderBean;
import com.zejor.bean.PayBean;
import com.zejor.bean.RepaymentPayBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.mvp.contract.RenewalContract;
import com.zejor.mvp.presenter.RenewalPresenter;
import com.zejor.utils.SharedPerferenceUtil;
import com.zejor.utils.utils.BaseHelper;
import com.zejor.utils.utils.Constants;
import com.zejor.utils.utils.MobileSecurePayer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RenewalActivity extends BaseActivity<RenewalPresenter> implements RenewalContract.View {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_use_day)
    TextView tvUseDay;
    @BindView(R.id.tv_num_mail)
    TextView tvNumMail;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.ll_mail)
    LinearLayout llMail;
    @BindView(R.id.tv_principal)
    TextView tvPrincipal;
    @BindView(R.id.tv_renewal_days)
    TextView tvRenewalDays;
    @BindView(R.id.tv_rent)
    TextView tvRent;
    @BindView(R.id.tv_over_days)
    TextView tvOverDays;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.rv_pay)
    RecyclerView rvPay;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    private String days;
    private Intent intent;
    private String orderNum;
    private String moneyLeaseback;
    private String monthLeaseback;
    private String dayLeaseback;
    private String usingDays;
    private String overdueDays;
    private String rent;
    private double total;
    private List<RepaymentPayBean.PmBean.RepayWayBean> payList;
    private RepaymentPayAdapter adapter;
    private String repayWay;
    private int checkPosition = 0;
    private HashMap<String, String> payModeParamter;
    private HashMap<String, String> payParamter;
    private Handler mHandler = createHandler();

    private String overduefine;

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_renewal;
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
        tvTitle.setText("延期还款");
        days = getIntent().getStringExtra("days");
        intent = getIntent();
        orderNum = intent.getStringExtra("orderNum");
        moneyLeaseback = intent.getStringExtra("moneyLeaseback");
        monthLeaseback = intent.getStringExtra("monthLeaseback");
        dayLeaseback = intent.getStringExtra("dayLeaseback");
        usingDays = intent.getStringExtra("usingDays");
        overdueDays = intent.getStringExtra("overdueDays");
        rent = intent.getStringExtra("rent");
        overduefine = intent.getStringExtra("overduefine");
        total = Double.parseDouble(rent) * Long.parseLong(days) + Double.parseDouble(overduefine) ;

        tvOrderNum.setText("订单号：" + orderNum);
        tvMoney.setText("￥" + moneyLeaseback);
        tvMonth.setText(monthLeaseback);
        tvDay.setText(dayLeaseback);
        tvUseDay.setText(usingDays + "天");
        tvOverDays.setText("×" + overdueDays);
        tvRent.setText("￥" + overduefine + "");
        tvPrincipal.setText("￥" + rent + "/天");
        tvRenewalDays.setText("×" + days);
        tvTotal.setText("￥" + total);

        payList = new ArrayList<>();
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        rvPay.setLayoutManager(layoutmanager);
        //设置Adapter
        adapter = new RepaymentPayAdapter(this, payList);
        rvPay.setAdapter(adapter);
        adapter.setItemClickListener(new RepaymentPayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                checkPosition = position;
                adapter.changeCheck(position);
                repayWay = payList.get(position).getPayWay();
            }
        });

        payModeParamter = new HashMap<>();
        payModeParamter.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
        payModeParamter.put("version", CommonValueUtil.VERSION);
        payModeParamter.put("softType", CommonValueUtil.SOFTTYPE);
        payModeParamter.put("funCode", FunCode.REPAYMENT_MONEY);
        payModeParamter.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
        payModeParamter.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));
        payModeParamter.put("configType", "qzbkRepayWay");
        payModeParamter.put("configKey", "repayWay");
        mPresenter.getPayMode(payModeParamter);
    }


    @OnClick({R.id.iv_back, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_pay:
                if (repayWay == null) {
                    Toast.makeText(this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (payList.get(checkPosition).getPayName().equals("银行卡支付")) {
                    startActivityForResult(new Intent(this, BankPayActivity.class)
                                    .putExtra("payAmount", total + "")
                                    .putExtra("orderNum", orderNum)
                                    .putExtra("repayBizCode", "01")
                                    .putExtra("repayWay", repayWay)
                                    .putExtra("rentDays", days)
                                    .putExtra("rentMoney", Double.parseDouble(rent) * Long.parseLong(days) + "")
                                    .putExtra("overdueDays", overdueDays)
                                    .putExtra("overdueFine", Double.parseDouble(overduefine)+"")
                                    .putExtra("totalMoney", total + "")
                            , CommonValueUtil.TURNOFF);
                    return;
                }

                payParamter = new HashMap<>();
                payParamter.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
                payParamter.put("version", CommonValueUtil.VERSION);
                payParamter.put("softType", CommonValueUtil.SOFTTYPE);
                payParamter.put("funCode", FunCode.REPAYMENT_PAY);
                payParamter.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
                payParamter.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));
                payParamter.put("tradeOrderNo", orderNum);//订单号
                payParamter.put("repayBizCode", "01");//还款业务码01续租02全额还款
                payParamter.put("repayWay", repayWay);//还款方式由支付方式列表返回
                payParamter.put("rentDays", days);//续租天数
                payParamter.put("rentMoney", Double.parseDouble(rent) * Long.parseLong(days) + "");//续租金额
                payParamter.put("overdueDays", overdueDays);//逾期天数
                payParamter.put("overdueFine", Double.parseDouble(overduefine)+"");//逾期金额
                payParamter.put("totalMoney", total + "");//还款总额
                mPresenter.toPay(payParamter);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        App.getInstance().setToHome(true);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void loadOrder(BaseEntity<List<OrderBean>> loginDataBean) {

    }

    @Override
    public void loadPayMode(BaseEntity<RepaymentPayBean> repaymentPayBean) {
        if (repaymentPayBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            payList = repaymentPayBean.getRetData().getPm().get(0).getRepayWay();
            if (payList.size() != 0) {
                repayWay = payList.get(0).getPayWay();
            }
            adapter.update(payList);
        } else {
            showSuccess(repaymentPayBean.getRetMsg());
        }
    }

    private Handler createHandler() {
        return new Handler() {
            public void handleMessage(Message msg) {
                String strRet = (String) msg.obj;
                switch (msg.what) {
                    case Constants.RQF_PAY: {
                        JSONObject objContent = BaseHelper.string2JSON(strRet);
                        String retCode = objContent.optString("ret_code");
                        String retMsg = objContent.optString("ret_msg");

                        // 成功
                        if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
                            // TODO 卡前置模式返回的银行卡绑定协议号，用来下次支付时使用，此处仅作为示例使用。正式接入时去掉
//                            if (pay_type_flag == 1) {
//                                TextView tv_agree_no = (TextView) findViewById(R.id.tv_agree_no);
//                                tv_agree_no.setVisibility(View.VISIBLE);
//                                tv_agree_no.setText(objContent.optString(
//                                        "agreementno", ""));
//                            }
                            BaseHelper.showDialog(RenewalActivity.this, "提示",
                                    "支付成功",
                                    android.R.drawable.ic_dialog_alert);
                            App.getInstance().setToHome(true);
                            setResult(RESULT_OK);
                            finish();
                        } else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
                            // TODO 处理中，掉单的情形
                            String resulPay = objContent.optString("result_pay");
                            if (Constants.RESULT_PAY_PROCESSING
                                    .equalsIgnoreCase(resulPay)) {
                                BaseHelper.showDialog(RenewalActivity.this, "提示",
                                        "支付失败，请重试",
                                        android.R.drawable.ic_dialog_alert);
                            }

                        } else {
                            // TODO 失败
                            BaseHelper.showDialog(RenewalActivity.this, "提示", "支付失败，请重试",
                                    android.R.drawable.ic_dialog_alert);
                        }
                    }
                    break;
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void loadPay(BaseEntity<PayBean> payBean) {
        Logger.e(payBean.toString());
        if (payBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            Logger.e(payList.get(checkPosition).getPayName());
            switch (payList.get(checkPosition).getPayName()) {
                case "银行卡支付":
                    MobileSecurePayer msp = new MobileSecurePayer();
                    String content4Pay = BaseHelper.toJSONString(payBean.getRetData());
                    boolean bRet = msp.pay(content4Pay, mHandler,
                            Constants.RQF_PAY, this, false);
                    break;
                case "微信支付":
                    startActivity(new Intent(this, WebViewActivity.class).putExtra("url", payBean.getRetData().getReturnUrl()));
                    break;
                case "支付宝支付":
                    Toast.makeText(this, "支付宝", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        showSuccess(null);
    }
}
