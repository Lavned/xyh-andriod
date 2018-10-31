package com.zejor.mvp.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.xw.repo.BubbleSeekBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zejor.App;
import com.zejor.R;
import com.zejor.base.BaseActivity;
import com.zejor.base.BaseEntity;
import com.zejor.base.BaseFragment;
import com.zejor.bean.AdvertisementBean;
import com.zejor.bean.BannerBean;
import com.zejor.bean.LoanDayBean;
import com.zejor.bean.OrderBean;
import com.zejor.bean.OrderChangeStatusBean;
import com.zejor.bean.RefreshBean;
import com.zejor.bean.UpdateBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.customview.AutoVerticalScrollTextView;
import com.zejor.mvp.activities.HomeActivity;
import com.zejor.mvp.activities.LoanAmountInfoActivity;
import com.zejor.mvp.activities.LoginActivity;
import com.zejor.mvp.activities.WebViewActivity;
import com.zejor.mvp.contract.HomeContract;
import com.zejor.mvp.presenter.HomePresenster;
import com.zejor.utils.GlideImageLoader;
import com.zejor.utils.SharedPerferenceUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment<HomePresenster> implements HomeContract.View {


    @BindView(R.id.autoScrollTextView)
    AutoVerticalScrollTextView autoScrollTextView;
    @BindView(R.id.tv_new_order)
    TextView tvNewOrder;
    @BindView(R.id.tv_assessment)
    TextView tvAssessment;
    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.MoneyProgress)
    RelativeLayout MoneyProgress;

    @BindView(R.id.seek_bar_date)
    BubbleSeekBar seek_bar_date;
    @BindView(R.id.seek_bar_money)
    BubbleSeekBar seekBarMoney;

    @BindView(R.id.TVLoanMoney)
    TextView TVLoanMoney;
    @BindView(R.id.TVLoanMoneyMin)
    TextView TVLoanMoneyMin;
    @BindView(R.id.TVLoanMoneyMax)
    TextView TVLoanMoneyMax;
    @BindView(R.id.TVLoanDay)
    TextView TVLoanDay;
    @BindView(R.id.TVLoanDayMin)
    TextView TVLoanDayMin;
    @BindView(R.id.TVLoanDayMax)
    TextView TVLoanDayMax;

//    @BindView(R.id.dashboard_view_2)
//    DashboardView2 mDashboardView2;
    private HashMap<String, String> advertisementMap;
    private HashMap<String, String> paramter;
    private String orderChangeStatus;
    private HashMap<String, String> orderParamter;
    private HashMap<String, String> updateMap;
    private AlertDialog dialog;
    private UpdateBean updateBean;
    private HashMap<String, String> refreshParamter;
    private List<String> imageList;
    private HashMap<String, String> bannerMap;
    private boolean showAgain = true;
    private AlertDialog.Builder build;

    @OnClick({R.id.tv_assessment, R.id.tv_new_order,R.id.MoneyProgress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_assessment:
                if ((boolean) SharedPerferenceUtil.getData(getActivity(), "isLogin", false)) {
                    Intent intent = new Intent(getActivity(), LoanAmountInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("loanAmount", TVLoanMoney.getText().toString());
                    bundle.putString("loanDay", TVLoanDay.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.tv_new_order:
                ((HomeActivity) getActivity()).viewOrder();
                break;
            case R.id.MoneyProgress:
                ((HomeActivity) getActivity()).viewOrder();
                break;
        }
    }


    @Override
    public int getContentLayout() {
        return R.layout.fragment_home;
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
        fullScreen(getActivity());
//        mDashboardView2.setCreditValueWithAnim(940);
        initScroll();
        initBanner();
        loadmarketRankingData();

    }


    /**
     * "softType" : "ios_qzbk_v1.0",
     * "funCode" : "100017",
     * "productPlatform":"xyh",
     * "version" : "1.0.0"
     * 网络请求
     */
    public void loadmarketRankingData() {
        Map<String, String> httpParams = new HashMap<>();
        httpParams.put("softType", CommonValueUtil.SOFTTYPE);
        httpParams.put("funCode", FunCode.lOANDAY);
        httpParams.put("productPlatform", "xyh");
        httpParams.put("version", CommonValueUtil.VERSION);

        JSONObject jsonObject = new JSONObject(httpParams);
        OkGo.<String>post(CommonValueUtil.URL + "qzbk/")
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoanDayBean bean = gson.fromJson(response.body(), LoanDayBean.class);
                        if (bean.getRetData() != null) {
                            //设置天数进度条
                            initLoanDate(bean.getRetData().getLoanDayMin(),
                                    bean.getRetData().getLoanDayMax(),
                                    bean.getRetData().getLoanDayGrad(),
                                    bean.getRetData().getLoanDayMax());
                            //设置金额进度条
                            initLoanMoney(bean.getRetData().getLoanAmountMin(),
                                    bean.getRetData().getLoanAmountMax(),
                                    bean.getRetData().getLoanAmountGrad(),
                                    bean.getRetData().getLoanAmountMax());
                            initLoanData(bean.getRetData());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast.makeText(mContext, "请求出错", Toast.LENGTH_SHORT).show();
                    }

                });
    }


    /**
     * 设置最大值最小值
     * @param retData
     */
    private void initLoanData(LoanDayBean.RetDataBean retData) {
        TVLoanDay.setText(retData.getLoanDayMax() +"天");
        TVLoanDayMin.setText(retData.getLoanDayMin()+"天");
        TVLoanDayMax.setText(retData.getLoanDayMin() +"天");
        TVLoanMoney.setText("¥" +retData.getLoanAmountMax());
        TVLoanMoneyMin.setText("¥" +retData.getLoanAmountMin());
        TVLoanMoneyMax.setText("¥" +retData.getLoanAmountMax());
    }


    /**
     * 借款天数进度条
     */
    private void initLoanDate(final int min, final int max, int count, int progress) {
        seek_bar_date.getConfigBuilder()
                .min(0)
                .max(max)
                .progress(max)
                .sectionCount(count)
                .seekStepSection()
                .build();
        if(min == max){
            seek_bar_date.setEnabled(false);
        }
        seek_bar_date.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                super.onProgressChanged(bubbleSeekBar, progress, progressFloat, fromUser);
                TVLoanDay.setText(progress+"天");
            }
        });
    }

    /**
     * 借款金额进度条
     */
    private void initLoanMoney(int min, int max, int count, int progress) {
        seekBarMoney.getConfigBuilder()
                .min(0)
                .max(max)
                .progress(max)
                .sectionCount(count)
                .seekStepSection()
                .build();
        if(min == max){
            seekBarMoney.setEnabled(false);
        }
        seekBarMoney.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                super.onProgressChanged(bubbleSeekBar, progress, progressFloat, fromUser);
                TVLoanMoney.setText("¥" +progress+"");
            }
        });
    }


    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * chushihua滚动
     */
    private void initScroll() {
        advertisementMap = new HashMap<>();
        advertisementMap.put("version", CommonValueUtil.VERSION);
        advertisementMap.put("softType", CommonValueUtil.SOFTTYPE);
        advertisementMap.put("funCode", FunCode.ADVERTISEMENT);
        advertisementMap.put("mobile", "");
        mPresenter.getScrollText(advertisementMap);
    }

    /**
     * 初始化banner
     */
    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        bannerMap = new HashMap<>();
        bannerMap.put("pmType", "0");
        bannerMap.put("version", CommonValueUtil.VERSION);
        bannerMap.put("softType", CommonValueUtil.SOFTTYPE);
        bannerMap.put("funCode", FunCode.BANNER);
        bannerMap.put("mobile", "");
        mPresenter.getBannerData(bannerMap);
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderStatus();
        getOrder();
        getRefresh();
        checkUpdate();
        App.getInstance().setToHome(false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getOrderStatus();
            getOrder();
            getRefresh();
            checkUpdate();
        }
    }

    public void checkUpdate() {
        updateMap = new HashMap<>();
        updateMap.put("version", CommonValueUtil.VERSION);
        updateMap.put("softType", CommonValueUtil.SOFTTYPE);
        updateMap.put("funCode", FunCode.UPDATE);
        updateMap.put("mobile", "");
        mPresenter.getUpdate(updateMap);
    }


    /**
     * 订单状态
     */
    public void getOrder() {
        if ((boolean) SharedPerferenceUtil.getData(getActivity(), "isLogin", false)) {
            orderParamter = new HashMap<>();
            orderParamter.put("mobile", (String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));
            orderParamter.put("version", CommonValueUtil.VERSION);
            orderParamter.put("softType", CommonValueUtil.SOFTTYPE);
            orderParamter.put("funCode", FunCode.ORDERS);
            orderParamter.put("userId", (String) SharedPerferenceUtil.getData(getActivity(), "userId", ""));
            orderParamter.put("tokenId", (String) SharedPerferenceUtil.getData(getActivity(), "tokenId", ""));
            mPresenter.getOrder(orderParamter);
        }
    }


    /**
     * 订单状态
     */
    public void getRefresh() {
        if ((boolean) SharedPerferenceUtil.getData(getActivity(), "isLogin", false)) {
            refreshParamter = new HashMap<>();
            refreshParamter.put("mobile", (String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));
            refreshParamter.put("version", CommonValueUtil.VERSION);
            refreshParamter.put("softType", CommonValueUtil.SOFTTYPE);
            refreshParamter.put("funCode", FunCode.HOME_REFRESH);
            refreshParamter.put("userId", (String) SharedPerferenceUtil.getData(getActivity(), "userId", ""));
            refreshParamter.put("tokenId", (String) SharedPerferenceUtil.getData(getActivity(), "tokenId", ""));
            mPresenter.homeRefresh(refreshParamter);
        }
    }


    /**
     * 订单状态
     */
    public void getOrderStatus() {
        if ((boolean) SharedPerferenceUtil.getData(getActivity(), "isLogin", false)) {
            paramter = new HashMap<>();
            paramter.put("mobile", (String) SharedPerferenceUtil.getData(getActivity(), "mobile", ""));
            paramter.put("version", CommonValueUtil.VERSION);
            paramter.put("softType", CommonValueUtil.SOFTTYPE);
            paramter.put("funCode", FunCode.ORDER_UPDATE);
            paramter.put("userId", (String) SharedPerferenceUtil.getData(getActivity(), "userId", ""));
            paramter.put("tokenId", (String) SharedPerferenceUtil.getData(getActivity(), "tokenId", ""));
            mPresenter.getOrderStatus(paramter);
        }
    }


    /**
     * 加载订单
     */
    @Override
    public void loadOrder(BaseEntity<List<OrderBean>> loginDataBean) {
        if (loginDataBean.getRetData() != null && loginDataBean.getRetData().size() != 0) {
            tvAssessment.setVisibility(View.GONE);
            MoneyProgress.setVisibility(View.VISIBLE);
        } else {
            tvAssessment.setVisibility(View.VISIBLE);
            MoneyProgress.setVisibility(View.GONE);
        }
        //存储是否有订单的size
        SharedPerferenceUtil.saveData(getActivity(), "size", loginDataBean.getRetData().size() == 0 ? "0" : loginDataBean.getRetData().size()+"");

    }

    @Override
    public void loadAdvertisementCall(BaseEntity<List<AdvertisementBean>> advertisementBean) {
        if (advertisementBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            List<String> advertisementList = new ArrayList<>();
            for (AdvertisementBean bean : advertisementBean.getRetData()) {
                advertisementList.add(bean.getApplySucessUser());
            }
            if (null == autoScrollTextView.getData())
                autoScrollTextView.setData(advertisementList);
            showSuccess(null);
        } else
            showSuccess(advertisementBean.getRetMsg());
    }

    @Override
    public void onOrderStatusSuccess(BaseEntity<OrderChangeStatusBean> orderChangeStatusBean) {
        if (orderChangeStatusBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            orderChangeStatus = orderChangeStatusBean.getRetData().getOrderChangeStatus();
            App.getInstance().setOrderChangeStatus(orderChangeStatus);
            if (orderChangeStatus.equals("1")) {
                tvNewOrder.setVisibility(View.VISIBLE);
            } else {
                tvNewOrder.setVisibility(View.GONE);
            }
        }
    }

    public void showDialog() {
        build = new AlertDialog.Builder(getActivity())
                .setMessage(updateBean.getDescription().replace("_", "\n"))
                .setTitle("更新")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
    public void updateSuccess(BaseEntity<UpdateBean> updateBean) {
        this.updateBean = updateBean.getRetData();
        if (!this.updateBean.getSign().equals("0")) {
            if (this.updateBean.getSign().equals("1")) {
                if (showAgain) {
                    showDialog();
                    showAgain = false;
                }
            }
            if (this.updateBean.getSign().equals("2")) {
                showDialog();
            }
        }
    }

    @Override
    public void refreshBanner(final BaseEntity<BannerBean> bannerBean) {
        if (bannerBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            imageList = new ArrayList<>();
            for (BannerBean.BannerInfo bannerInfo : bannerBean.getRetData().getBannerList()) {
                imageList.add(bannerInfo.getLogoUrl());
            }
            //设置图片集合
            banner.setImages(imageList);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (bannerBean.getRetData().getBannerList().get(position).getLinkUrl().contains("http://")) {
                        startActivity(new Intent(getActivity(), WebViewActivity.class)
                                .putExtra("url", bannerBean.getRetData().getBannerList().get(position).getLinkUrl()));
                    }
                }
            });
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        } else {
            showSuccess(bannerBean.getRetMsg());
        }
    }

    @Override
    public void refreshSuccess(BaseEntity<RefreshBean> updateBean) {
        if (updateBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            App.getInstance().setIsOldMan(updateBean.getRetData().getIsOldMan());
            SharedPerferenceUtil.saveData(getActivity(), "idCardNo", updateBean.getRetData().getCardId() == null ? "" : updateBean.getRetData().getCardId());
            SharedPerferenceUtil.saveData(getActivity(), "zhiMaUrl", updateBean.getRetData().getZhiMaURL() == null ? "" : updateBean.getRetData().getZhiMaURL());
            SharedPerferenceUtil.saveData(getActivity(), "realName", updateBean.getRetData().getRealName() == null ? "" : updateBean.getRetData().getRealName());
            SharedPerferenceUtil.saveData(getActivity(), "alipayAccount", updateBean.getRetData().getAlipayAccount() == null ? "" : updateBean.getRetData().getAlipayAccount());
            //增加存储实名认证字段
            SharedPerferenceUtil.saveData(getActivity(), "realNameStatus", updateBean.getRetData().getRealNameStatus() == null ? "" : updateBean.getRetData().getRealNameStatus());
           //增加判断是否是老用户判断
            SharedPerferenceUtil.saveData(getActivity(), "isOldMan", updateBean.getRetData().getIsOldMan() == 0 ? 0 : updateBean.getRetData().getIsOldMan());

            if(updateBean.getRetData().getIsOldMan() == 1){
                SharedPerferenceUtil.saveData(getActivity(), "isOldMan","1");
                tvAssessment.setText("马上申请借款");
            }else {
                tvAssessment.setText("立即提现");
                SharedPerferenceUtil.saveData(getActivity(), "isOldMan", "2");
            }
        } else {
            showSuccess(updateBean.getRetMsg());
        }
    }


}
