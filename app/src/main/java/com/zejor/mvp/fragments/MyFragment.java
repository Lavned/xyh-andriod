package com.zejor.mvp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zejor.R;
import com.zejor.base.BaseEntity;
import com.zejor.base.BaseFragment;
import com.zejor.bean.CreditAmountBean;
import com.zejor.bean.OrderBean;
import com.zejor.bean.UpdateBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.mvp.activities.AboutActivity;
import com.zejor.mvp.activities.HomeActivity;
import com.zejor.mvp.activities.WebViewActivity;
import com.zejor.mvp.activities.WithdrawActivity;
import com.zejor.mvp.contract.MyContract;
import com.zejor.mvp.presenter.MyPresenter;
import com.zejor.utils.SharedPerferenceUtil;
import com.zejor.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.iv_pot)
    ImageView ivPot;
    private AlertDialog dialog;
    private HashMap<String, String> creditAmountMap;
    private String creditAmount;
    private HashMap<String, String> paramter;
    private int recovery = 0;
    private List<OrderBean> orderList;
    private HashMap<String, String> updateMap;
    private UpdateBean updateBean;
    private AlertDialog.Builder build;

    @OnClick({R.id.ll_withdraw, R.id.ll_update_photo, R.id.ll_update, R.id.ll_about, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_withdraw:
//                if (orderList == null || orderList.size() == 0) {
//                    Toast.makeText(mContext, "暂无订单", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (orderList.get(0).getOrderType() == "3") {
//                    return;
//                }
//                if (orderList.get(0).getOrderType() == "1") {
//                    recovery = 0;
//                } else {
                    recovery = 1;
//                }
                if ((SharedPerferenceUtil.getData(getActivity(),"isOldMan","")+"").equals("2")){
                    //增加实名认证状态并跳转不同界面为1则实名认证跳绑定银行卡，0为未实名跳到实名认证
                    if ((SharedPerferenceUtil.getData(getActivity(),"realNameStatus","")+"").equals("0")){
                        Toast.makeText(mContext, "请先完善资料", Toast.LENGTH_SHORT).show();
                    }else {
                        if ((SharedPerferenceUtil.getData(getActivity(),"size","")+"").equals("0")){
                            //跳转到主页
                            HomeActivity homeActivity = (HomeActivity) getActivity();
                            homeActivity.setChangFragment(0);
                        }else {
                            Toast.makeText(mContext, "您有未支付的订单，请前去处理", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    if ((SharedPerferenceUtil.getData(getActivity(),"realNameStatus","")+"").equals("0")){
                        Toast.makeText(mContext, "请先完善资料", Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(new Intent(getActivity(), WithdrawActivity.class)
                                .putExtra("recovery", recovery));
                    }

                }

                break;
            case R.id.ll_update_photo:
                Logger.e(updateBean.toString());
                if (orderList == null || orderList.size() == 0) {
                    Toast.makeText(mContext, "暂无订单", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (orderList.get(0).getOrderType().equals("2") && (orderList.get(0).getOrderStatus().equals("90") || orderList.get(0).getOrderStatus().equals("100") || orderList.get(0).getOrderStatus().equals("130"))) {
//                    startActivity(new Intent(getActivity(), UpdatePhotoActivity.class));
//                } else {
//                    Toast.makeText(mContext, "非审核中不可修改", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.ll_update:
                showDialog();
                break;
            case R.id.ll_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.tv_exit:
                ((HomeActivity) getActivity()).viewHome();
                SharedPerferenceUtil.clear(getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tvName.setText(TextUtils.isEmpty((String) SharedPerferenceUtil.getData(getActivity(), "realName", "")) ? "你好" : (String) SharedPerferenceUtil.getData(getActivity(), "realName", ""));
        tvPhone.setText((String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));

        order();
        creditAmount();
        mPresenter.getUpdate(updateMap);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            order();
            creditAmount();
            mPresenter.getUpdate(updateMap);
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
        mPresenter.getOrder(paramter);
    }

    public void creditAmount() {
        if (!(boolean) SharedPerferenceUtil.getData(getActivity(), "isLogin", false)) {
            return;
        }
        creditAmountMap = new HashMap<>();
        creditAmountMap.put("mobile", (String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));
        creditAmountMap.put("version", CommonValueUtil.VERSION);
        creditAmountMap.put("softType", CommonValueUtil.SOFTTYPE);
        creditAmountMap.put("funCode", FunCode.CREDIT_AMOUNT);
        creditAmountMap.put("userId", (String) SharedPerferenceUtil.getData(getActivity(), "userId", ""));
        creditAmountMap.put("tokenId", (String) SharedPerferenceUtil.getData(getActivity(), "tokenId", ""));
        mPresenter.getCreditAmount(creditAmountMap);
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_my;
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

        updateMap = new HashMap<>();
        updateMap.put("version", CommonValueUtil.VERSION);
        updateMap.put("softType", CommonValueUtil.SOFTTYPE);
        updateMap.put("funCode", FunCode.UPDATE);
        updateMap.put("mobile", "");
    }

    public void showDialog() {
        if (updateBean == null) {
            return;
        }
        if (updateBean.getSign().equals("0")) {
            Toast.makeText(mContext, "已是最新版", Toast.LENGTH_SHORT).show();
            return;
        }
        build = new AlertDialog.Builder(getActivity())
                .setMessage(updateBean.getDescription().replace("_", "\n"))
                .setTitle("更新")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        InstallApkQuietly.getInstance(getActivity()).url = updateBean.getUrl();
//                        InstallApkQuietly.getInstance(getActivity()).init();
                        startActivity(new Intent(getActivity(), WebViewActivity.class)
                                .putExtra("url", updateBean.getUrl()));
                        dialog.dismiss();
                    }
                });
        if (updateBean.getSign().equals("1")) {
            build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        dialog = build.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void loadOrder(BaseEntity<List<OrderBean>> loginDataBean) {
        if (loginDataBean.getRetCode().equals("2001")) {
            ToastUtils.showToast(loginDataBean.getRetMsg());
            SharedPerferenceUtil.clear(getActivity());
            ((HomeActivity) getActivity()).viewHome();
            return;
        }
        if (loginDataBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            orderList = loginDataBean.getRetData();

        } else {
            showSuccess(loginDataBean.getRetMsg());
        }
    }

    @Override
    public void loadCreditAmount(BaseEntity<CreditAmountBean> creditAmountBean) {
        if (creditAmountBean.getRetCode().equals("2001")) {
            ToastUtils.showToast(creditAmountBean.getRetMsg());
            SharedPerferenceUtil.clear(getActivity());
            ((HomeActivity) getActivity()).viewHome();
            return;
        }
        if (creditAmountBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            creditAmount = creditAmountBean.getRetData().getCreditAmount();
            tvPrice.setText("￥" + creditAmount);
        } else {
            showSuccess(creditAmountBean.getRetMsg());
        }
    }

    @Override
    public void updateSuccess(BaseEntity<UpdateBean> updateBean) {
        if (updateBean.getRetCode().equals("2001")) {
            ToastUtils.showToast(updateBean.getRetMsg());
            SharedPerferenceUtil.clear(getActivity());
            ((HomeActivity) getActivity()).viewHome();
            return;
        }
        this.updateBean = updateBean.getRetData();
        if (this.updateBean.getSign().equals("0")) {
            ivPot.setVisibility(View.GONE);
        } else {
            ivPot.setVisibility(View.VISIBLE);
        }
    }

}
