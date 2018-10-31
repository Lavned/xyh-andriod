package com.zejor.mvp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.suke.widget.SwitchButton;
import com.zejor.App;
import com.zejor.R;
import com.zejor.Utils;
import com.zejor.bean.AmounInfoBean;
import com.zejor.bean.UserBean;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.utils.SharedPerferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.tongdun.android.shell.FMAgent;

public class LoanAmountInfoActivity extends Activity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.loanAmount)
    TextView loanAmount;
    @BindView(R.id.loanDay)
    TextView loanDay;
    @BindView(R.id.loanAmountActual)
    TextView loanAmountActual;
    @BindView(R.id.loanAmountInterest)
    TextView loanAmountInterest;
    @BindView(R.id.loanAmountService)
    TextView loanAmountService;
    @BindView(R.id.switch_button)
    SwitchButton switch_button;
    @BindView(R.id.xy)
    TextView xy;
    @BindView(R.id.agree)
    CheckBox agree;
    @BindView(R.id.btnNext)
    TextView btnNext;

    Intent intent;
    String loanA,loanD;
    Context mContext;
    String fee,arrivalAmount="";
    //新增个人信息实体
    private  UserBean userBean;

    @OnClick({R.id.iv_back, R.id.addBank,R.id.btnNext,R.id.agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.addBank:
                startActivity(new Intent(mContext,AddBankCardActivity.class));
                break;
            case R.id.btnNext:
                Intent intent ;
                Log.d("tzj",SharedPerferenceUtil.getData(LoanAmountInfoActivity.this,"isOldMan","")+"---");
                if ((SharedPerferenceUtil.getData(LoanAmountInfoActivity.this,"isOldMan","")+"").equals("2")){
                    commitOrder();
                }else {
                    if (userBean.getRetData().getCompletedInfoStatus().equals("0")) {
                        if (userBean.getRetData().getRealNameStatus().equals("1")) {
                            intent = new Intent(mContext, CompleteInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("loanAmount", loanA.replace("¥", ""));
                            bundle.putString("loanStagesNum", loanD.replace("天", ""));
                            bundle.putString("arrivalAmount", arrivalAmount);
                            bundle.putString("fee", fee);
                            //保存身份信息传数据
                            bundle.putString("idcardFrontImg", userBean.getRetData().getIdcardFrontImg());
                            bundle.putString("idcardBackImg", userBean.getRetData().getIdcardBackImg());
                            bundle.putString("userRealName", userBean.getRetData().getUserRealName());
                            bundle.putString("userCertificateNo", userBean.getRetData().getUserCertificateNo());

                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        } else {
                            intent = new Intent(mContext, CompleteInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("loanAmount", loanA.replace("¥", ""));
                            bundle.putString("loanStagesNum", loanD.replace("天", ""));
                            bundle.putString("arrivalAmount", arrivalAmount);
                            bundle.putString("fee", fee);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        startActivityForResult(new Intent(this, CompleteAllDataActivity.class)
                                .putExtra("loanAmount", loanA.replace("¥", ""))
                                .putExtra("loanStagesNum", loanD.replace("天", ""))
                                .putExtra("arrivalAmount", arrivalAmount)
                                .putExtra("fee", fee), CommonValueUtil.TURNOFF);
//                    intent = new Intent(mContext, CompleteAllDataActivity.class);
//                    Bundle bundles = new Bundle();
//                    bundles.putString("loanAmount", loanA.replace("¥", ""));
//                    bundles.putString("loanStagesNum", loanD.replace("天", ""));
//                    bundles.putString("arrivalAmount", arrivalAmount);
//                    bundles.putString("fee", fee);
//                    intent.putExtras(bundles);
//                    startActivity(intent);
//                    finish();
                        // if()

                    }
                }
                break;
            case R.id.agree:
                break;
        }
    }


    /**
     * 直接下单
     */
    private void commitOrder() {
        Map<String, String> orderMap = new HashMap<>();
        String blackBox = FMAgent.onEvent(LoanAmountInfoActivity.this);
        orderMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
        orderMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
        orderMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
        orderMap.put("version", CommonValueUtil.VERSION);
        orderMap.put("softType", CommonValueUtil.SOFTTYPE);
        orderMap.put("funCode", FunCode.REPAYMENT_COMMIT);
        orderMap.put("loanAmount", loanA.replace("¥", ""));
        orderMap.put("loanStagesNum", loanD.replace("天", ""));
        orderMap.put("loanStages", "1");
        orderMap.put("loanFree", "0");
        orderMap.put("bankAccount", "");
        orderMap.put("automaticRepayment", "1");
        orderMap.put("agreeCode", "");
        orderMap.put("loanStagesUnit", "0");
        orderMap.put("userAccount", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
        orderMap.put("arrivalAmount", arrivalAmount);
        orderMap.put("fee", fee);
        orderMap.put("blackBox", blackBox);
        orderMap.put("bankName", "");
        final JSONObject jsonObject = new JSONObject(orderMap);
        OkGo.<String>post(CommonValueUtil.URL + "qzbk/")
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.body());
                            if(jsonObject1.getString("retCode").equals("0000")){
                                Toast.makeText(mContext,"借款成功！",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(mContext,jsonObject1.getString("retMsg"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast.makeText(mContext, "请求出错", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(R.layout.activity_loan_amount_info);
        mContext = this;
        ButterKnife.bind(this);
        if(!SharedPerferenceUtil.getData(mContext,"isOldMan","").equals("1")) {
            btnNext.setText("确认提现");
        }
        initData();
        loadData();

        ///获取用户个人完善信息
        loadUserData();
    }

    private void loadUserData() {
        Map<String, String> httpParam = new HashMap<>();
        httpParam.put("funCode",FunCode.USER);
        httpParam.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
        httpParam.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
        httpParam.put("softType", CommonValueUtil.SOFTTYPE);
        httpParam.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
        httpParam.put("version", CommonValueUtil.VERSION);
        OkGo.<String>post(CommonValueUtil.URL + "qzbk/")
                .tag(this)
                .upJson(new JSONObject(httpParam))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                         userBean = gson.fromJson(response.body(),UserBean.class);
                        if (userBean!=null&&userBean.getRetCode().equals("0000")){
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 网路请求
     */
    private void loadData() {
        Map<String, String> httpParams = new HashMap<>();
        httpParams.put("softType", CommonValueUtil.SOFTTYPE);
        httpParams.put("funCode", FunCode.lOANINFO);
        httpParams.put("dayNum", loanD.replace("天",""));
        httpParams.put("loanAmount",loanA.replace("¥",""));
        httpParams.put("version", CommonValueUtil.VERSION);
        JSONObject jsonObject = new JSONObject(httpParams);
        OkGo.<String>post(CommonValueUtil.URL + "qzbk/")
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        AmounInfoBean bean = gson.fromJson(response.body(),AmounInfoBean.class);
                        if(bean.getRetData()!=null){
                            arrivalAmount = bean.getRetData().getActualArrival();
                            fee = bean.getRetData().getServiceFee();
                           loanAmountActual.setText("¥"+ bean.getRetData().getActualArrival());
                           loanAmountInterest.setText("¥"+bean.getRetData().getInterestFee());
                           loanAmountService.setText("¥" +bean.getRetData().getServiceFee());
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
     * 信息
     */
    private void initData() {
        tvTitle.setText("提交借款信息");
        intent = getIntent();
        loanA = intent.getStringExtra("loanAmount");
        loanD = intent.getStringExtra("loanDay");
        loanAmount.setText("" + loanA);
        loanDay.setText(loanD+"");
    }


    /**
     * 头部设置
     */
    public void fullScreen() {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Utils.setWindowStatusBarColor(this, R.color.green);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // getPhoneNum(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CommonValueUtil.TURNOFF) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
