package com.zejor.mvp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zejor.App;
import com.zejor.R;
import com.zejor.adapter.AuthenticationAdapter;
import com.zejor.base.BaseActivity;
import com.zejor.base.BaseEntity;
import com.zejor.bean.AuthenticationBean;
import com.zejor.bean.CommitFaceInfoBean;
import com.zejor.bean.FaceBean;
import com.zejor.bean.ZhiMaBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.mvp.contract.CompleteAllDataContract;
import com.zejor.mvp.presenter.CompleteAllDataPresenter;
import com.zejor.thirdservice.FaceUtils;
import com.zejor.thirdservice.MoxieUtil;
import com.zejor.utils.LoadingDialogUtil;
import com.zejor.utils.SharedPerferenceUtil;
import com.zejor.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.tongdun.android.shell.FMAgent;

public class CompleteAllDataActivity extends BaseActivity<CompleteAllDataPresenter> implements CompleteAllDataContract.View {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.listView)
    ListView listView;
    private HashMap<String, String> faceMap;
    private String cardNo;
    private String userRealName;
    private String userFaceStatus;
    private HashMap<String, String> commitFaceInfoMap;
    private HashMap<String, String> map;
    private AuthenticationAdapter adapter;
    private ArrayList<AuthenticationBean> subList;
    private Intent intent;
    private String loanAmount;
    private String loanStagesNum;
    private String arrivalAmount;
    private String fee;
    private HashMap<String, String> orderMap;
    private HashMap<String, String> zhimaMap;
    private HashMap<String, String> taskIdMap;

    //去轮询
    Timer timer;
    private boolean isXun = false;
     @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_complete_data_second;
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
        tvTitle.setText("完善资料");
        intent = getIntent();
        loanAmount = intent.getStringExtra("loanAmount");
        loanStagesNum = intent.getStringExtra("loanStagesNum");
        arrivalAmount = intent.getStringExtra("arrivalAmount");
        fee = intent.getStringExtra("fee");
        adapter = new AuthenticationAdapter(this, null, R.layout.authentication_list_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AuthenticationBean bean = adapter.getItem(position);
                String authStatus = bean.getAuthStatus();
                if ("已认证".equals(authStatus)) {
                    ToastUtils.showToast("此项已验证");
                    return;
                } else if ("认证中".equals(authStatus)){
                    ToastUtils.showToast("请等待认证结果");
                } else {
                    switch (bean.getAuthItem()) {
                        case "运营商":
                            AuthenticationBean zhiMaBean = adapter.getItem(0);
                            String authStatus1 = zhiMaBean.getAuthStatus();

                                SharedPerferenceUtil.saveData(App.getInstance(), "moxieType", "102");
                                MoxieUtil.confirmMoxie("运营商", CompleteAllDataActivity.this);
                            break;
                        case "芝麻信用":
                            if ("已认证".equals(subList.get(0).getAuthStatus())||"认证中".equals(subList.get(0).getAuthStatus())){
                                zhimaMap = new HashMap<>();
                                zhimaMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
                                zhimaMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
                                zhimaMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
                                zhimaMap.put("h5Type", CommonValueUtil.H5_TYPE);
                                zhimaMap.put("version", CommonValueUtil.VERSION);
                                zhimaMap.put("softType", CommonValueUtil.SOFTTYPE);
                                zhimaMap.put("funCode", FunCode.ZHIMA);
                                mPresenter.getZhiMaUrl(zhimaMap);
                            }else {
                                Toast.makeText(CompleteAllDataActivity.this, "请先认证运营商", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        case "支付宝":
                            SharedPerferenceUtil.saveData(App.getInstance(), "moxieType", "103");
                            break;
                        case "京东":
                            SharedPerferenceUtil.saveData(App.getInstance(), "moxieType", "104");
                            break;
                        case "淘宝":
                            SharedPerferenceUtil.saveData(App.getInstance(), "moxieType", "105");
                            break;
                        case "公积金":
                            SharedPerferenceUtil.saveData(App.getInstance(), "moxieType", "106");
                            break;

                    }
                }
            }
        });

        faceMap = new HashMap<>();

        faceMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
        faceMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
        faceMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
        faceMap.put("funCode", FunCode.FACE);
        faceMap.put("version", CommonValueUtil.VERSION);
        faceMap.put("softType", CommonValueUtil.SOFTTYPE);
        mPresenter.getFaceData(faceMap);


        map = new HashMap<>();
        map.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
        map.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
        map.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
        map.put("funCode", FunCode.AUTHENTICATION);
        map.put("version", CommonValueUtil.VERSION);
        map.put("softType", CommonValueUtil.SOFTTYPE);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAuthenticationData(map);
    }

    @OnClick({R.id.iv_back, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                if (userRealName != null && cardNo != null) {
                    if ("认证中".equals(subList.get(0).getAuthStatus())){
                        if ("已认证".equals(subList.get(1).getAuthStatus())){
                            isXun=true;
                            LoadingDialogUtil.getInstance().showDialog1(CompleteAllDataActivity.this,"正在认证中，请耐心等待");
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    mPresenter.getAuthenticationData(map);
                                }
                            },3000,6);
                        }else {
                            Toast.makeText(this, "请先认证芝麻信用", Toast.LENGTH_SHORT).show();
                        }

                       // Toast.makeText(this, "正在认证中，请耐心等待", Toast.LENGTH_SHORT).show();
                    }else {
                        if ((subList.get(0).getAuthStatus().equals("已认证")&&(subList.get(1).getAuthStatus().equals("已认证")))){
                            doFaceVerify(userRealName, cardNo);
                        }else {
                            Toast.makeText(this, "请先完善资料", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "请稍等", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    /**
     * 进行人脸验证
     */
    private void doFaceVerify(String userName, String userNo) {


        FaceUtils.getInstence().contrastRealNameAndFace(userName, userNo, this, new FaceUtils.OnYDCallBack() {

            @Override//第二回调(单人脸)
            public void onlyFaceResult(String result) {
/*
*/
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String livingPhoto = jsonObject.getString("living_photo");
                    SharedPerferenceUtil.saveData(App.getInstance(), "faceImgUrl", livingPhoto);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override//第一回调
            public void onlyRealNameResult(String result) {

            }

            @Override//第三回调(人脸对比)
            public void contrastResult(String result) {
/*
                {"partner_order_id":"orider_123123",
                "thresholds":{"1e-5":"0.9","1e-4":"0.8","1e-3":"0.7"},
                "similarity":"0.8627",
                "session_id":"240744380745580544",
                "success":"true",
                "message":"操作成功"}
*/

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String similarity = jsonObject.getString("result_status");
                    if(similarity.equals("01")){
                        similarity="0.99";
                    }else if(similarity.equals("02")){
                        similarity="0.12";
                    }else if(similarity.equals("03")){
                        similarity="0.13";
                    }else if(similarity.equals("04")){
                        similarity="0.14";
                    }else if(similarity.equals("05")){
                        similarity="0.15";
                    }
                    SharedPerferenceUtil.saveData(App.getInstance(), "similarity", similarity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                String userJsonLog = (String) SharedPerferenceUtil.getData(App.getInstance(), "userJsonLog", "");
//        commitFaceInfoMap.put("userJsonLog",userJsonLog);
                showLoading();
                commitFaceInfoMap = new HashMap<>();
                commitFaceInfoMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
                commitFaceInfoMap.put("similarity", (String) SharedPerferenceUtil.getData(App.getInstance(), "similarity", ""));
                commitFaceInfoMap.put("lPhoto", (String) SharedPerferenceUtil.getData(App.getInstance(), "faceImgUrl", ""));
                commitFaceInfoMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
                commitFaceInfoMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
                commitFaceInfoMap.put("funCode", FunCode.FACECOMMIT);
                commitFaceInfoMap.put("version", CommonValueUtil.VERSION);
                commitFaceInfoMap.put("softType", CommonValueUtil.SOFTTYPE);
                mPresenter.commitFaceInfoData(commitFaceInfoMap);
            }

            @Override
            public void onlyOCRResult(String realName, String idCardNo, String idcardFrontPhoto, String idcard_back_photo, String result) {

            }

            @Override
            public void onErrMsg(String code, String msg) {

            }
        });
    }

    @Override
    public void refreshFaceData(BaseEntity<FaceBean> faceBean) {
        if (faceBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            cardNo = faceBean.getRetData().getCardNo();
            userRealName = faceBean.getRetData().getUserRealName();
            userFaceStatus = faceBean.getRetData().getUserFaceStatus();
        } else {
            showSuccess(faceBean.getRetMsg());
        }
    }

    @Override
    public void loadFaceInfo(BaseEntity<CommitFaceInfoBean> commitFaceInfoBean) {
        String udKey = commitFaceInfoBean.getRetData().getUdKey();
        switch (udKey) {
            case "成功！":
                orderMap = new HashMap<>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String blackBox = FMAgent.onEvent(CompleteAllDataActivity.this);
                        orderMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
                        orderMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
                        orderMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
                        orderMap.put("version", CommonValueUtil.VERSION);
                        orderMap.put("softType", CommonValueUtil.SOFTTYPE);
                        orderMap.put("funCode", FunCode.REPAYMENT_COMMIT);
                        orderMap.put("loanAmount", loanAmount);
                        orderMap.put("loanStagesNum", loanStagesNum);
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.commitOrder(orderMap);
                            }
                        });
                    }
                }).start();

                break;
            default:
//                faceFailed = true;
                ToastUtils.showToast(udKey);
                showSuccess(null);
                break;

        }
    }

    @Override
    public void refreshListData(BaseEntity<List<AuthenticationBean>> commitFaceInfoBean) {
        subList = new ArrayList<>();
        for (AuthenticationBean bean : commitFaceInfoBean.getRetData()) {
            if ("启用".equals(bean.getStatus())) {
                subList.add(bean);
            }
        }

        adapter.updateRes(subList);
        Log.i("----------",subList.get(1).getAuthStatus());
//

        if (isXun) {
            if ("已认证".equals(commitFaceInfoBean.getRetData().get(0).getAuthStatus())&&"已认证".equals(commitFaceInfoBean.getRetData().get(1).getAuthStatus())) {
                LoadingDialogUtil.getInstance().stopDialog();
                timer.cancel();
               // doFaceVerify(userRealName, cardNo);
            }
        }
    }

    @Override
    public void loadOrder(BaseEntity<Object> object) {
        if (object.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            showSuccess("提交成功");
            setResult(RESULT_OK);
            finish();
        } else {
            showSuccess(object.getRetMsg());
        }
    }

    @Override
    public void onZhimaSuccess(BaseEntity<ZhiMaBean> zhiMaBean) {
        if(zhiMaBean.getRetData()!=null){
            if (zhiMaBean.getRetData().getStatus().equals("2")) {
                startActivity(new Intent(CompleteAllDataActivity.this, WebViewActivity.class)
                        .putExtra("url", zhiMaBean.getRetData().getAuthUrl()));
            } else {
                Toast.makeText(this, "芝麻信用已完善", Toast.LENGTH_SHORT).show();
                mPresenter.getAuthenticationData(map);
            }
        }else {
            Toast.makeText(CompleteAllDataActivity.this,
                    ""+zhiMaBean.getRetMsg(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onTaskIdSuccess(BaseEntity<String> taskIdBean) {
        mPresenter.getAuthenticationData(map);
    }

    /**
     * 处理调用魔蝎sdk 的回调内容
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
          //  SharedPerferenceUtil.saveData(App.getInstance(), "taskId", "123");
            taskIdMap = new HashMap<>();
            taskIdMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
            taskIdMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
            taskIdMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
            taskIdMap.put("taskId", "123");
            taskIdMap.put("version", CommonValueUtil.VERSION);
            taskIdMap.put("softType", CommonValueUtil.SOFTTYPE);
            taskIdMap.put("funCode", FunCode.TASKID);
            taskIdMap.put("type", (String) SharedPerferenceUtil.getData(App.getInstance(), "moxieType", ""));
            mPresenter.updateTaskId(taskIdMap);


        }


        MoxieUtil.moxieCallBack(requestCode, resultCode, data, this, new MoxieUtil.MoxieCallBackListener() {
            @Override
            public void moxieCallBack(String taskId) {
                SharedPerferenceUtil.saveData(App.getInstance(), "taskId", taskId);
                taskIdMap = new HashMap<>();
                taskIdMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
                taskIdMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
                taskIdMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
                taskIdMap.put("taskId", taskId);
                taskIdMap.put("version", CommonValueUtil.VERSION);
                taskIdMap.put("softType", CommonValueUtil.SOFTTYPE);
                taskIdMap.put("funCode", FunCode.TASKID);
                taskIdMap.put("type", (String) SharedPerferenceUtil.getData(App.getInstance(), "moxieType", ""));
                mPresenter.updateTaskId(taskIdMap);
            }
        });
    }

}
