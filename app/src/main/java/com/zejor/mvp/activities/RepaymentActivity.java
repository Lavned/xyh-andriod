package com.zejor.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.zejor.bean.RepaymentMoneyBean;
import com.zejor.bean.RepaymentPayBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.mvp.contract.RepaymentContract;
import com.zejor.mvp.presenter.RepaymentPresenter;
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

public class RepaymentActivity extends BaseActivity<RepaymentPresenter> implements RepaymentContract.View {


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
    @BindView(R.id.tv_principal)
    TextView tvPrincipal;
    @BindView(R.id.tv_rent)
    TextView tvRent;
    @BindView(R.id.tv_lx)
    TextView tvLx;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.tv_state_over)
    TextView tvStateOver;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.rv_pay)
    RecyclerView rvPay;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_others)
    TextView tvOthers;
    @BindView(R.id.ll_repay)
    LinearLayout llRepay;
    private Intent intent;
    private String orderNum;
    private String moneyLeaseback;
    private String monthLeaseback;
    private String dayLeaseback;
    private HashMap<String, String> paramter;
    private List<RepaymentPayBean.PmBean.RepayWayBean> payList;
    private int checkPosition = 0;
    private HashMap<String, String> payModeParamter;
    private RepaymentPayAdapter adapter;
    private HashMap<String, String> payParamter;
    private String repayWay;
    String days;
    private String rent;
    private String usingDays;
    private String totalDays;
    private String overdueDays;

    private String fee,interest,overduefine;  //服务费，利息，滞纳金
    String isToal =""; //

    private double total = 0;
    private Handler mHandler = createHandler();
    private HashMap<String, String> orderParamter;

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_repayment;
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
        tvTitle.setText("还款详情");
        intent = getIntent();
        orderNum = intent.getStringExtra("orderNum");
        moneyLeaseback = intent.getStringExtra("moneyLeaseback");
        monthLeaseback = intent.getStringExtra("monthLeaseback");
        dayLeaseback = intent.getStringExtra("dayLeaseback");
        usingDays = intent.getStringExtra("usingDays");
        totalDays = intent.getStringExtra("totalDays");
        overdueDays = intent.getStringExtra("overdueDays");
        isToal = intent.getStringExtra("isTotal");
        fee = intent.getStringExtra("fee");
        interest = intent.getStringExtra("interest");
        overduefine = intent.getStringExtra("overduefine");
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


        if (isToal.equals("1")) {
            tvUseDay.setText(totalDays + "天");
        } else {
            tvUseDay.setText(usingDays + "天");
        }

        tvOrderNum.setText("订单号：" + orderNum);
        tvMoney.setText("￥" + moneyLeaseback);
        tvPrincipal.setText("￥" + moneyLeaseback);
        tvMonth.setText(monthLeaseback);
        tvDay.setText(dayLeaseback);
        if(Integer.parseInt(overdueDays) != 0){
            tvStateOver.setText("逾期" + overdueDays + "天");
        }


        tvLx.setText("￥" +interest);
        tvFee.setText("￥" +fee);
        tvRent.setText("￥" +overduefine);
        total = Double.parseDouble(moneyLeaseback) + Double.parseDouble(interest) + Long.parseLong(overduefine);
        tvTotal.setText("￥" + total);


        paramter = new HashMap<>();
        paramter.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
        paramter.put("version", CommonValueUtil.VERSION);
        paramter.put("softType", CommonValueUtil.SOFTTYPE);
        paramter.put("funCode", FunCode.REPAYMENT_MONEY);
        paramter.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
        paramter.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));
        paramter.put("configType", "qzbkLeaseAmount");
        paramter.put("configKey", "leaseAmount");
        mPresenter.getMoney(paramter);

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


    /**
     * 更多还款方式
     */
    private void showPopwindow() {
        View popView = View.inflate(this, R.layout.menu_repay, null);
        TextView tvContinue = popView.findViewById(R.id.tv_continue);
        tvContinue.setText("延期还款");
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        final PopupWindow popWindow = new PopupWindow(popView, width, height);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);// 设置同意在外点击消失
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_continue:
                        startActivityForResult(new Intent(RepaymentActivity.this, RenewalActivity.class)
                                .putExtra("days", days)
                                .putExtra("orderNum", orderNum)
                                .putExtra("moneyLeaseback", moneyLeaseback)
                                .putExtra("monthLeaseback", monthLeaseback)
                                .putExtra("dayLeaseback", dayLeaseback)
                                .putExtra("usingDays", usingDays)
                                .putExtra("overdueDays", overdueDays)
                                .putExtra("rent", rent)
                                .putExtra("overduefine",overduefine), CommonValueUtil.TURNOFF);
                        break;
                }
                popWindow.dismiss();
            }
        };

        tvContinue.setOnClickListener(listener);
        popWindow.showAtLocation(findViewById(R.id.ll_repay), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @OnClick({R.id.tv_pay, R.id.tv_others, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pay:
                if (repayWay == null) {
                    Toast.makeText(this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (payList.get(checkPosition).getPayName().equals("银行卡支付")) {
                    startActivityForResult(new Intent(this, BankPayActivity.class)
                                    .putExtra("payAmount", total + "")
                                    .putExtra("orderNum", orderNum)
                                    .putExtra("repayBizCode", "02")
                                    .putExtra("repayWay", repayWay)
                                    .putExtra("rentDays", "0")
                                    .putExtra("rentMoney", "0")
                                    .putExtra("overdueDays", overdueDays)
                                    .putExtra("overdueFine", Double.parseDouble(overduefine) * Long.parseLong(overdueDays) + "")
                                    .putExtra("totalMoney", total + "")
                            , CommonValueUtil.TURNOFF);
                    return;
                }
                showLoading();
                payParamter = new HashMap<>();
                payParamter.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
                payParamter.put("version", CommonValueUtil.VERSION);
                payParamter.put("softType", CommonValueUtil.SOFTTYPE);
                payParamter.put("funCode", FunCode.REPAYMENT_PAY);
                payParamter.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
                payParamter.put("repayWay", repayWay);//
                payParamter.put("rentDays", "0");//不改
                payParamter.put("rentMoney", "0");//不改
                payParamter.put("overdueDays", overdueDays);//逾期天
                payParamter.put("overdueFine", Double.parseDouble(rent) * Long.parseLong(overdueDays) + "");//逾期金额
                payParamter.put("totalMoney", total + "");//总额
                payParamter.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));
                payParamter.put("tradeOrderNo", orderNum);//订单号
                payParamter.put("repayBizCode", "02");//02全额还款
                mPresenter.toPay(payParamter);
                break;
            case R.id.tv_others:
                if (days == null) {
                    Toast.makeText(this, "加载数据中，请稍后重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                showPopwindow();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderParamter = new HashMap<>();
        orderParamter.put("mobile", (String) SharedPerferenceUtil.getData(this, "mobile", ""));
        orderParamter.put("version", CommonValueUtil.VERSION);
        orderParamter.put("softType", CommonValueUtil.SOFTTYPE);
        orderParamter.put("funCode", FunCode.ORDERS);
        orderParamter.put("userId", (String) SharedPerferenceUtil.getData(this, "userId", ""));
        orderParamter.put("tokenId", (String) SharedPerferenceUtil.getData(this, "tokenId", ""));
        mPresenter.getOrder(orderParamter);
    }

    @Override
    public void loadOrder(BaseEntity<List<OrderBean>> loginDataBean) {
        if (loginDataBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            if (loginDataBean.getRetData().size() == 0) {
                App.getInstance().setToHome(true);
                finish();
            }
            //300是还款失败  对应：使用中，今日还款，逾期
            if ((!loginDataBean.getRetData().get(0).getOrderStatus().equals("201"))
                    && (!loginDataBean.getRetData().get(0).getOrderStatus().equals("202"))
                    && (!loginDataBean.getRetData().get(0).getOrderStatus().equals("203"))
                    && (!loginDataBean.getRetData().get(0).getOrderStatus().equals("300"))) {
                App.getInstance().setToHome(true);
                finish();
            }
            if(loginDataBean.getRetData().get(0).getOrderStatus().equals("300")){
                tvStateOver.setText(loginDataBean.getRetData().get(0).getOrderDescription()+"");
            }
        }
    }

    @Override
    public void loadMoney(BaseEntity<RepaymentMoneyBean> repaymentMoneyBean) {
        if (repaymentMoneyBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            rent = repaymentMoneyBean.getRetData().getPm().get(0).getLeaseAmount().get(0).getAmount();
            days = repaymentMoneyBean.getRetData().getPm().get(0).getLeaseAmount().get(0).getDays();
        } else {
            showSuccess(repaymentMoneyBean.getRetMsg());
        }
    }

    @Override
    public void loadPayMode(BaseEntity<RepaymentPayBean> repaymentPayBean) {
        Logger.e(repaymentPayBean.toString());
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
                            BaseHelper.showDialog(RepaymentActivity.this, "提示",
                                    "支付成功",
                                    android.R.drawable.ic_dialog_alert);
                            App.getInstance().setToHome(true);
                            finish();
                        } else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
                            // TODO 处理中，掉单的情形
                            String resulPay = objContent.optString("result_pay");
                            if (Constants.RESULT_PAY_PROCESSING
                                    .equalsIgnoreCase(resulPay)) {
                                BaseHelper.showDialog(RepaymentActivity.this, "提示",
                                        "支付失败，请重试",
                                        android.R.drawable.ic_dialog_alert);
                            }

                        } else {
                            // TODO 失败
                            BaseHelper.showDialog(RepaymentActivity.this, "提示", "支付失败，请重试",
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CommonValueUtil.TURNOFF) {
            finish();
        }
    }
}
