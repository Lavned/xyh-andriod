package com.zejor.mvp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.R;
import com.zejor.adapter.RepaymentPayAdapter;
import com.zejor.base.BaseEntity;
import com.zejor.base.BaseFragment;
import com.zejor.bean.BankBean;
import com.zejor.bean.OrderBean;
import com.zejor.bean.OrderDetailBean;
import com.zejor.bean.PayBean;
import com.zejor.bean.RepaymentMoneyBean;
import com.zejor.bean.RepaymentPayBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.mvp.activities.HomeActivity;
import com.zejor.mvp.activities.RepaymentActivity;
import com.zejor.mvp.activities.WebViewActivity;
import com.zejor.mvp.activities.WithdrawActivity;
import com.zejor.mvp.contract.OrderContract;
import com.zejor.mvp.presenter.OrderPresenter;
import com.zejor.utils.PermissionUtils;
import com.zejor.utils.SharedPerferenceUtil;
import com.zejor.utils.ToastUtils;
import com.zejor.utils.utils.BaseHelper;
import com.zejor.utils.utils.Constants;
import com.zejor.utils.utils.MobileSecurePayer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrderFragment extends BaseFragment<OrderPresenter> implements OrderContract.View {


    @BindView(R.id.bankNum)
    TextView bankNum;
    @BindView(R.id.tv_num_over)
    TextView tvNumOver;
    @BindView(R.id.tv_state_send)
    TextView tvStateSend;
    @BindView(R.id.tv_serve)
    TextView tvServe;
    @BindView(R.id.rl_serve)
    RelativeLayout rlServe;
    @BindView(R.id.tv_reason_over)
    TextView tvReasonOver;
    @BindView(R.id.tv_residue)
    TextView tvResidue;
    @BindView(R.id.tv_rent_over)
    TextView tvRentOver;
    @BindView(R.id.tv_days_over)
    TextView tvDaysOver;
    @BindView(R.id.tv_total_over)
    TextView tvTotalOver;
    @BindView(R.id.rv_pay_over)
    RecyclerView rvPayOver;
    @BindView(R.id.ll_no_order)
    LinearLayout llNoOrder;
    @BindView(R.id.tv_order_num_leaseback)
    TextView tvOrderNumLeaseback;
    @BindView(R.id.tv_state_leaseback)
    TextView tvStateLeaseback;
    @BindView(R.id.tv_money_leaseback)
    TextView tvMoneyLeaseback;
    @BindView(R.id.tv_day_leaseback)
    TextView tvDayLeaseback;
    @BindView(R.id.tv_month_leaseback)
    TextView tvMonthLeaseback;
    @BindView(R.id.tv_daycount_leaseback)
    TextView tvDaycountLeaseback;
    @BindView(R.id.tv_next_leaseback)
    TextView tvNextLeaseback;
    @BindView(R.id.leaseback)
    LinearLayout leaseback;
    @BindView(R.id.tv_order_num_over)
    TextView tvOrderNumOver;
    @BindView(R.id.tv_state_over)
    TextView tvStateOver;
    @BindView(R.id.tv_money_over)
    TextView tvMoneyOver;
    @BindView(R.id.tv_day_over)
    TextView tvDayOver;
    @BindView(R.id.tv_month_over)
    TextView tvMonthOver;
    @BindView(R.id.tv_daycount_over)
    TextView tvDaycountOver;
    @BindView(R.id.over)
    ScrollView over;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.rl_reason)
    RelativeLayout rlReason;
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.tv_residue_text)
    TextView tvResidueText;
    Unbinder unbinder;
    private HashMap<String, String> paramter;
    private Calendar calendar;
    private String orderNum;
    private String monthLeaseback;
    private String dayLeaseback;
    private String usingDays;
    private String overdueDays;
    private String rent = "0";
    private String days = "";
    private double total = 0;
    private HashMap<String, String> payModeParamter;
    private HashMap<String, String> detailParamter;
    private String unusedDays;
    private String rentDays;
    private String totalDays;
    private String orderDescription;
    private String repurchaseAmount;
    private String repurchaseOffsetAmount;
    private List<RepaymentPayBean.PmBean.RepayWayBean> payList;
    private RepaymentPayAdapter adapter;
    private String repayWay;
    private int checkPosition = 0;
    private HashMap<String, String> payParamter;
    private Handler mHandler = createHandler();
    private String BankCardNo = "";
    @Override
    public int getContentLayout() {
        return R.layout.fragment_order;
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
    public void onResume() {
        super.onResume();
        //获取银行卡
        getBank();
        llNoOrder.setVisibility(View.VISIBLE);
        leaseback.setVisibility(View.GONE);
        over.setVisibility(View.GONE);

        order();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            order();
        }
    }

    public void order() {
        if (!(boolean) SharedPerferenceUtil.getData(getActivity(), "isLogin", false)) {
            return;
        }
        paramter = new HashMap<>();
        paramter.put("mobile", (String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));
        paramter.put("version", CommonValueUtil.VERSION);
        paramter.put("softType", CommonValueUtil.SOFTTYPE);
        paramter.put("funCode", FunCode.ORDERS);
        paramter.put("userId", (String) SharedPerferenceUtil.getData(getActivity(), "userId", ""));
        paramter.put("tokenId", (String) SharedPerferenceUtil.getData(getActivity(), "tokenId", ""));
        paramter.put("orderChangeStatus", App.getInstance().getOrderChangeStatus());
        mPresenter.getOrder(paramter);
    }

    @Override
    public void initData() {
        calendar = Calendar.getInstance();
        payList = new ArrayList<>();
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        rvPayOver.setLayoutManager(layoutmanager);
        //设置Adapter
        adapter = new RepaymentPayAdapter(getActivity(), payList);
        rvPayOver.setAdapter(adapter);
        adapter.setItemClickListener(new RepaymentPayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                checkPosition = position;
                adapter.changeCheck(position);
                repayWay = payList.get(position).getPayWay();
            }
        });

    }

    private void getBank() {
        HashMap<String, String>  cardMap = new HashMap<>();
        cardMap.put("mobile", (String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));
        cardMap.put("version", CommonValueUtil.VERSION);
        cardMap.put("softType", CommonValueUtil.SOFTTYPE);
        cardMap.put("funCode", FunCode.BANK_LIST);
        cardMap.put("userId", (String) SharedPerferenceUtil.getData(getActivity(), "userId", ""));
        cardMap.put("tokenId", (String) SharedPerferenceUtil.getData(getActivity(), "tokenId", ""));
        mPresenter.getBankCard(cardMap);
    }

    private void showPopwindow() {
        final View popView = View.inflate(getActivity(), R.layout.menu_order, null);
        TextView unusedDay = popView.findViewById(R.id.unused_days);
        TextView rentDay = popView.findViewById(R.id.rent_days);
        TextView totalDay = popView.findViewById(R.id.total_days);
        ImageView ivDetailCancel = popView.findViewById(R.id.iv_detail_cancel);

        unusedDay.setText(unusedDays);
        rentDay.setText(rentDays);
        totalDay.setText(totalDays);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        final PopupWindow popWindow = new PopupWindow(popView, width, height);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
        final WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.4f;
        getActivity().getWindow().setAttributes(lp);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        ivDetailCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });

        popWindow.showAtLocation(getActivity().findViewById(R.id.ll_order), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @OnClick({R.id.tv_pay_over, R.id.iv_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_over:
                if (repayWay == null) {
                    Toast.makeText(getActivity(), "请选择支付方式", Toast.LENGTH_SHORT).show();
                    return;
                }
                payParamter = new HashMap<>();
                payParamter.put("mobile", (String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));
                payParamter.put("version", CommonValueUtil.VERSION);
                payParamter.put("softType", CommonValueUtil.SOFTTYPE);
                payParamter.put("funCode", FunCode.REPAYMENT_PAY);
                payParamter.put("userId", (String) SharedPerferenceUtil.getData(getActivity(), "userId", ""));
                payParamter.put("tokenId", (String) SharedPerferenceUtil.getData(getActivity(), "tokenId", ""));
                payParamter.put("tradeOrderNo", orderNum);//订单号
                payParamter.put("repayBizCode", "02");//02全额还款
                payParamter.put("repayWay", repayWay);//还款方式
                payParamter.put("rentDays", "0");//续天数
                payParamter.put("rentMoney", "0");//续金额
                payParamter.put("overdueDays", overdueDays);//逾期天数
                payParamter.put("overdueFine", Double.parseDouble(rent) * Long.parseLong(overdueDays) + "");//逾期金额
                payParamter.put("totalMoney", total + "");//总额
                mPresenter.toPay(payParamter);
                break;
            case R.id.iv_detail:
                showPopwindow();
                break;
        }
    }

    @Override
    public void loadBankCard(BaseEntity<List<BankBean>> bankBean) {
        if (bankBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            if (bankBean.getRetData() != null && bankBean.getRetData().size() != 0) {
                BankCardNo = bankBean.getRetData().get(0).getBankCardNo();
            }
        }
    }

    @Override
    public void loadOrder(final BaseEntity<List<OrderBean>> loginDataBean) {
        Logger.e(loginDataBean.toString());
        if (loginDataBean.getRetCode().equals("2001")) {
            ToastUtils.showToast(loginDataBean.getRetMsg());
            SharedPerferenceUtil.clear(getActivity());
            ((HomeActivity) getActivity()).viewHome();
            return;
        }
        if (loginDataBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            if (loginDataBean.getRetData().size() == 0) {
                leaseback.setVisibility(View.GONE);
                over.setVisibility(View.GONE);
                llNoOrder.setVisibility(View.VISIBLE);
                return;
            }
            orderNum = loginDataBean.getRetData().get(0).getTradeOrderNo();//订单号
            usingDays = loginDataBean.getRetData().get(0).getUsingDays();//使用天数
            overdueDays = loginDataBean.getRetData().get(0).getOverdueDays();//逾期天数
            repurchaseAmount = loginDataBean.getRetData().get(0).getShouldRepayPrincipal();//逾期天数
            llNoOrder.setVisibility(View.GONE);
            tvNextLeaseback.setBackgroundColor(getResources().getColor(R.color.green));

            //设置可点击
            tvNextLeaseback.setFocusable(true);
            switch (loginDataBean.getRetData().get(0).getOrderType()) {
                case "2":
                    leaseback.setVisibility(View.VISIBLE);
                    ivDetail.setVisibility(View.GONE);
                    over.setVisibility(View.GONE);
                    calendar.setTimeInMillis(Long.parseLong(loginDataBean.getRetData().get(0).getApplyTime()));
                    tvOrderNumLeaseback.setText(orderNum);
                    tvStateLeaseback.setText(loginDataBean.getRetData().get(0).getOrderDescription());
                    tvMoneyLeaseback.setText("￥" + repurchaseAmount);
                    tvMonthLeaseback.setText("申请日期" + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
                    tvDayLeaseback.setText(calendar.get(Calendar.DAY_OF_MONTH) + "日");
                    tvDaycountLeaseback.setText(usingDays + "天");
                  //  if (loginDataBean.getRetData().get(0).getOrderStatus().equals("110")) {

//                    } else {
//                        tvNextLeaseback.setVisibility(View.GONE);
//                    }
                    if (!TextUtils.isEmpty(BankCardNo)){
                        tvNextLeaseback.setVisibility(View.GONE);
                    }else {
                        tvNextLeaseback.setVisibility(View.VISIBLE);
                        tvNextLeaseback.setText("绑卡使用");
                    }
                    tvNextLeaseback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), WithdrawActivity.class)
                                    .putExtra("recovery", 1));
                        }
                    });
                    break;
                case "3":
                    leaseback.setVisibility(View.VISIBLE);
                    ivDetail.setVisibility(View.GONE);
                    over.setVisibility(View.GONE);
                    calendar.setTimeInMillis(Long.parseLong(loginDataBean.getRetData().get(0).getApplyTime()));
                    tvOrderNumLeaseback.setText(orderNum);
                    tvStateLeaseback.setText(loginDataBean.getRetData().get(0).getOrderDescription());
                    tvMoneyLeaseback.setText("￥" + repurchaseAmount);
                    tvMonthLeaseback.setText("申请日期" + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
                    tvDayLeaseback.setText(calendar.get(Calendar.DAY_OF_MONTH) + "日");
                    tvDaycountLeaseback.setText(usingDays + "天");
                    if (loginDataBean.getRetData().get(0).getOrderStatus().equals("330")) {
                        tvNextLeaseback.setText("联系客服（点击直接拨打）");
                        tvNextLeaseback.setVisibility(View.VISIBLE);
                        tvNextLeaseback.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (PermissionUtils.getCallPhonePermissions(getActivity(), 0)) {
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    Uri data = Uri.parse("tel:" + "18250101004");
                                    intent.setData(data);
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {
                        tvNextLeaseback.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(BankCardNo)){
                        if (tvNextLeaseback.getText().toString().equals("绑卡使用")){
                            tvNextLeaseback.setVisibility(View.GONE);
                        }
                    }
                    break;
                case "4":
                    calendar.setTimeInMillis(Long.parseLong(loginDataBean.getRetData().get(0).getShouldRepaymentTime()));
                    unusedDays = loginDataBean.getRetData().get(0).getOrderRenewRentingInfo().getUnusedDays();
                    rentDays = loginDataBean.getRetData().get(0).getOrderRenewRentingInfo().getRentDays();
                    totalDays = loginDataBean.getRetData().get(0).getOrderRenewRentingInfo().getTotalDays();
                    monthLeaseback = "还款日期" + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月";
                    dayLeaseback = calendar.get(Calendar.DAY_OF_MONTH) + "日";
                    leaseback.setVisibility(View.VISIBLE);
                    over.setVisibility(View.GONE);
                    tvOrderNumLeaseback.setText(orderNum);
                    tvStateLeaseback.setText(loginDataBean.getRetData().get(0).getOrderDescription());
                    tvMoneyLeaseback.setText("￥" + repurchaseAmount);
                    tvMonthLeaseback.setText(monthLeaseback);
                    tvDayLeaseback.setText(dayLeaseback);
                    bankNum.setVisibility(View.VISIBLE);
                    bankNum.setText("到账银行卡：" + loginDataBean.getRetData().get(0).getBankNum());
                    if (loginDataBean.getRetData().get(0).getIsRenewRenting().equals("1")) {
                        ivDetail.setVisibility(View.VISIBLE);
                        tvDaycountLeaseback.setText(totalDays + "天");
                    } else {
                        ivDetail.setVisibility(View.GONE);
                        tvDaycountLeaseback.setText(usingDays + "天");
                    }
                    tvNextLeaseback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), RepaymentActivity.class)
                                    .putExtra("orderNum", orderNum)
                                    .putExtra("moneyLeaseback", repurchaseAmount)
                                    .putExtra("monthLeaseback", monthLeaseback)
                                    .putExtra("dayLeaseback", dayLeaseback)
                                    .putExtra("usingDays", usingDays)
                                    .putExtra("isTotal", loginDataBean.getRetData().get(0).getIsRenewRenting() + "")
                                    .putExtra("totalDays", totalDays)
                                    .putExtra("overdueDays", overdueDays)
                                    .putExtra("fee", loginDataBean.getRetData().get(0).getShouldRepayFee())
                                    .putExtra("interest", loginDataBean.getRetData().get(0).getShouldRepayInterest())
                                    .putExtra("overduefine", loginDataBean.getRetData().get(0).getShouldOverdueFine()));
                        }
                    });
                    tvNextLeaseback.setVisibility(View.VISIBLE);

                    if (loginDataBean.getRetData().get(0).getOrderStatus().equals("300")) {
                        tvNextLeaseback.setText("重新发起还款");
                    } else if (loginDataBean.getRetData().get(0).getOrderStatus().equals("400")) {
                        tvNextLeaseback.setText("还款中");
                        tvNextLeaseback.setFocusable(false);
                    } else if (loginDataBean.getRetData().get(0).getOrderStatus().equals("201")){
                        tvNextLeaseback.setText("前往还款");
                    }
                    else {
                        tvNextLeaseback.setText("提前还款");
                    }

                    if (loginDataBean.getRetData().get(0).getOrderDescription().contains("今日还款")) {
                        tvNextLeaseback.setText("马上还款");
                        tvNextLeaseback.setBackgroundColor(getResources().getColor(R.color.color_today));
                    }
                    if (loginDataBean.getRetData().get(0).getOrderDescription().contains("逾期")) {
                        tvNextLeaseback.setText("马上还款");
                        tvNextLeaseback.setBackgroundColor(getResources().getColor(R.color.color_overdue));
                    }
                    if (!TextUtils.isEmpty(BankCardNo)){
                        if (tvNextLeaseback.getText().toString().equals("绑卡使用")){
                            tvNextLeaseback.setVisibility(View.GONE);
                        }
                    }
                    break;
                case "5":
                    leaseback.setVisibility(View.GONE);
                    over.setVisibility(View.VISIBLE);
                    tvStateOver.setText("逾期" + overdueDays + "天");
                    tvOrderNumOver.setText(orderNum);
                    tvMoneyOver.setText("￥" + repurchaseAmount);
                    tvMonthOver.setText("申请日期" + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
                    tvDayOver.setText(calendar.get(Calendar.DAY_OF_MONTH) + "日");
                    tvDaycountOver.setText(usingDays + "天");
                    tvDaysOver.setText("×" + overdueDays);

                    payModeParamter = new HashMap<>();
                    payModeParamter.put("mobile", (String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));
                    payModeParamter.put("version", CommonValueUtil.VERSION);
                    payModeParamter.put("softType", CommonValueUtil.SOFTTYPE);
                    payModeParamter.put("funCode", FunCode.REPAYMENT_MONEY);
                    payModeParamter.put("userId", (String) SharedPerferenceUtil.getData(getActivity(), "userId", ""));
                    payModeParamter.put("tokenId", (String) SharedPerferenceUtil.getData(getActivity(), "tokenId", ""));
                    payModeParamter.put("configType", "qzbkRepayWay");
                    payModeParamter.put("configKey", "repayWay");
                    mPresenter.getPayMode(payModeParamter);

                    detailParamter = new HashMap<>();
                    detailParamter.put("mobile", (String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));
                    detailParamter.put("version", CommonValueUtil.VERSION);
                    detailParamter.put("softType", CommonValueUtil.SOFTTYPE);
                    detailParamter.put("funCode", FunCode.ORDER_DETAIL);
                    detailParamter.put("userId", (String) SharedPerferenceUtil.getData(getActivity(), "userId", ""));
                    detailParamter.put("tokenId", (String) SharedPerferenceUtil.getData(getActivity(), "tokenId", ""));
                    detailParamter.put("tradeOrderNo", orderNum);
                    mPresenter.getOrderDetail(detailParamter);

                    paramter = new HashMap<>();
                    paramter.put("mobile", (String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));
                    paramter.put("version", CommonValueUtil.VERSION);
                    paramter.put("softType", CommonValueUtil.SOFTTYPE);
                    paramter.put("funCode", FunCode.REPAYMENT_MONEY);
                    paramter.put("userId", (String) SharedPerferenceUtil.getData(getActivity(), "userId", ""));
                    paramter.put("tokenId", (String) SharedPerferenceUtil.getData(getActivity(), "tokenId", ""));
                    paramter.put("configType", "qzbkLeaseAmount");
                    paramter.put("configKey", "leaseAmount");
                    mPresenter.getMoney(paramter);
                    if (!TextUtils.isEmpty(BankCardNo)){
                        if (tvNextLeaseback.getText().toString().equals("绑卡使用")){
                            tvNextLeaseback.setVisibility(View.GONE);
                        }
                    }
                    break;
            }
        } else {
            showSuccess(loginDataBean.getRetMsg());
        }
    }

    @Override
    public void cancelResult(BaseEntity<Object> object) {
        if (object.getRetCode().equals("2001")) {
            ToastUtils.showToast(object.getRetMsg());
            SharedPerferenceUtil.clear(getActivity());
            ((HomeActivity) getActivity()).viewHome();
            return;
        }
        if (object.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            showSuccess("取消成功");
            mPresenter.getOrder(paramter);
        } else {
            showSuccess(object.getRetMsg());
        }
    }

    @Override
    public void loadMoney(BaseEntity<RepaymentMoneyBean> repaymentMoneyBean) {
        if (repaymentMoneyBean.getRetCode().equals("2001")) {
            ToastUtils.showToast(repaymentMoneyBean.getRetMsg());
            SharedPerferenceUtil.clear(getActivity());
            ((HomeActivity) getActivity()).viewHome();
            return;
        }
        if (repaymentMoneyBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            rent = repaymentMoneyBean.getRetData().getPm().get(0).getLeaseAmount().get(0).getAmount();
            tvRentOver.setText("￥" + rent + "/天");
            days = repaymentMoneyBean.getRetData().getPm().get(0).getLeaseAmount().get(0).getDays();
            if (repurchaseOffsetAmount != null) {
                total = (Double.parseDouble(repurchaseAmount) - Double.parseDouble(repurchaseOffsetAmount) + Double.parseDouble(rent) * Long.parseLong(overdueDays));
                tvTotalOver.setText("￥" + total);
            }
        } else {
            showSuccess(repaymentMoneyBean.getRetMsg());
        }

    }


    @Override
    public void loadPayMode(BaseEntity<RepaymentPayBean> repaymentPayBean) {
        if (repaymentPayBean.getRetCode().equals("2001")) {
            ToastUtils.showToast(repaymentPayBean.getRetMsg());
            SharedPerferenceUtil.clear(getActivity());
            ((HomeActivity) getActivity()).viewHome();
            return;
        }
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
        if (payBean.getRetCode().equals("2001")) {
            ToastUtils.showToast(payBean.getRetMsg());
            SharedPerferenceUtil.clear(getActivity());
            ((HomeActivity) getActivity()).viewHome();
            return;
        }
        if (payBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            Logger.e(payList.get(checkPosition).getPayName());
            switch (payList.get(checkPosition).getPayName()) {
                case "银行卡支付":
                    MobileSecurePayer msp = new MobileSecurePayer();
                    String content4Pay = BaseHelper.toJSONString(payBean.getRetData());
                    boolean bRet = msp.pay(content4Pay, mHandler,
                            Constants.RQF_PAY, getActivity(), false);
                    break;
                case "微信支付":
                    startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("url", payBean.getRetData().getReturnUrl()));
                    break;
                case "支付宝支付":
                    Toast.makeText(getActivity(), "支付宝", Toast.LENGTH_SHORT).show();
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
                            BaseHelper.showDialog(getActivity(), "提示",
                                    "支付成功",
                                    android.R.drawable.ic_dialog_alert);
                        } else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
                            // TODO 处理中，掉单的情形
                            String resulPay = objContent.optString("result_pay");
                            if (Constants.RESULT_PAY_PROCESSING
                                    .equalsIgnoreCase(resulPay)) {
                                BaseHelper.showDialog(getActivity(), "提示",
                                        "支付失败，请重试",
                                        android.R.drawable.ic_dialog_alert);
                            }

                        } else {
                            // TODO 失败
                            BaseHelper.showDialog(getActivity(), "提示", "支付失败，请重试",
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
    public void loadDetail(BaseEntity<OrderDetailBean> orderDetailBean) {
        if (orderDetailBean.getRetCode().equals("2001")) {
            ToastUtils.showToast(orderDetailBean.getRetMsg());
            SharedPerferenceUtil.clear(getActivity());
            ((HomeActivity) getActivity()).viewHome();
            return;
        }
        if (orderDetailBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            tvNumOver.setText(orderDetailBean.getRetData().getCourierNumber());
            orderDescription = orderDetailBean.getRetData().getOrderDescription();
            tvStateSend.setText(orderDescription);
            repurchaseOffsetAmount = orderDetailBean.getRetData().getRepurchaseOffsetAmount();
            if (TextUtils.isEmpty(repurchaseOffsetAmount) || repurchaseOffsetAmount.equals("0")) {
                rlServe.setVisibility(View.GONE);
            } else {
                rlServe.setVisibility(View.VISIBLE);
                tvServe.setText("￥" + repurchaseOffsetAmount);
            }
            tvType.setText(orderDetailBean.getRetData().getCourierType());
            if (TextUtils.isEmpty(orderDetailBean.getRetData().getOrderAuditResult())) {
                rlReason.setVisibility(View.GONE);
            } else {
                rlReason.setVisibility(View.VISIBLE);
                tvReasonOver.setText(orderDetailBean.getRetData().getOrderAuditResult());
            }
            if (orderDescription.equals("未签收")) {
                tvResidueText.setText("回购金额");
                tvResidue.setText("￥" + (Double.parseDouble(repurchaseAmount) - Double.parseDouble(repurchaseOffsetAmount)));
            } else {
                tvResidueText.setText("抵用剩余金额");
            }

            if (rent != null) {
                total = (Double.parseDouble(repurchaseAmount) - Double.parseDouble(repurchaseOffsetAmount) + Double.parseDouble(rent) * Long.parseLong(overdueDays));
                tvTotalOver.setText("￥" + total);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
