package com.zejor.mvp.activities;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zejor.App;
import com.zejor.R;
import com.zejor.Utils;
import com.zejor.base.BaseActivity;
import com.zejor.base.BaseEntity;
import com.zejor.bean.BankAscriptionBean;
import com.zejor.bean.SupportBankBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.mvp.contract.AddBankCardContract;
import com.zejor.mvp.fragments.ChooseBankFragment;
import com.zejor.mvp.presenter.AddBankCardPresenter;
import com.zejor.thirdservice.FaceUtils;
import com.zejor.utils.AppUtils;
import com.zejor.utils.PermissionHelper;
import com.zejor.utils.PermissionUtils;
import com.zejor.utils.SharedPerferenceUtil;
import com.zejor.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zejor.contants.FunCode.REPAYMENT_MONEY;
import static com.zejor.contants.FunCode.VERIFY_BANK;
import static com.zejor.utils.ToastUtils.showToast;

public class AddBankCardActivity extends BaseActivity<AddBankCardPresenter> implements FaceUtils.OnYDCallBack, AddBankCardContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.rl_id)
    RelativeLayout rlId;
    @BindView(R.id.et_bank_card)
    EditText etBankCard;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.tv_add_card)
    TextView tvAddCard;

    private String name;
    private String idCard;
    private String bankCardNo;
    private String idcardFrontPhoto;
    private String idcardBackPhoto;
    private int PERMISSION_CAMERA = 200;
    private HashMap<String, String> requestMap;
    private String bankPhone;
    private String bankName;
    private HashMap<String, String> codeMap;
    private String verifyCode;
    private String logoCode;
    private HashMap<String, String> addBankMap;


    private HashMap<String, String> supportBankMap;
    private List<String> supportBankList;
    private ChooseBankFragment chooseBankFragment;
    private PermissionHelper permissionHelper;
    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_bank_card;
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
        tvTitle.setText("添加银行卡");
        name = (String) SharedPerferenceUtil.getData(this, "realName", "");
        idCard = (String) SharedPerferenceUtil.getData(this, "idCardNo", "");
        tvName.setText(name);
        tvId.setText(idCard);
        etBankCard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(etBankCard.getText().toString())) {
                        return;
                    }
                    bankCardNo = etBankCard.getText().toString();
                    showLoading();
                    requestMap = new HashMap<>();
                    requestMap.put("bankCardNo", bankCardNo);//银行卡号
                    requestMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));//用户Id
                    requestMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));//tokenId
                    requestMap.put("softType", "" + CommonValueUtil.SOFTTYPE);//
                    requestMap.put("version", "" + CommonValueUtil.VERSION);
                    requestMap.put("funCode", VERIFY_BANK);
                    requestMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
                    mPresenter.checkCardUsed(requestMap);
                }
            }
        });


        supportBankMap = new HashMap<>();
        supportBankMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));//用户Id
        supportBankMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));//tokenId
        supportBankMap.put("softType", "" + CommonValueUtil.SOFTTYPE);//
        supportBankMap.put("version", "" + CommonValueUtil.VERSION);
        supportBankMap.put("funCode", REPAYMENT_MONEY);
        supportBankMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
        supportBankMap.put("configType", "pmYeeBankList");
        supportBankMap.put("configKey", "yeeBankSuppport");
        mPresenter.supportBankList(supportBankMap);

        chooseBankFragment = new ChooseBankFragment();
        chooseBankFragment.setItemClickListener(new ChooseBankFragment.OnItemClickListener() {
            @Override
            public void onDialogItemClick(int position) {
                tvBankName.setText(supportBankList.get(position));
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.rl_name, R.id.rl_id, R.id.tv_send_code, R.id.tv_add_card,R.id.rl_bank_name})
    public void onViewClicked(View view) {
        //新的权限申请
        permissionHelper = new PermissionHelper(AddBankCardActivity.this);
        PermissionHelper.PermissionListener permissionListener;
        permissionListener = new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permission) {
                Log.e("doAfterGrand","success");
            }
            //没问题啊你操作下
            @Override
            public void doAfterDenied(String... permission) {
                //模拟器白屏？
                showToast("点击权限，并打开需要的权限");
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 5);
            }
        };
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_bank_name:
                chooseBankFragment.show(getFragmentManager(), supportBankList);
                break;
            case R.id.rl_name:
                if (name == null || idCard == null) {
                    boolean isStater = permissionHelper.hasPermissions(this,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (!isStater){
                        permissionHelper.requestPermissions("请授予该应用[相机][读写]权限！",permissionListener,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }else {
                        FaceUtils.getInstence().startORC(this, this);
                    }
//                    if (PermissionUtils.hasPermissons(this, Manifest.permission.CAMERA)) {
//                        //身份验证
//                        FaceUtils.getInstence().startORC(this, this);
//                    } else {
//                        PermissionUtils.getCameraPermissions(this, PERMISSION_CAMERA);
//
//                    }
                }
                break;
            case R.id.rl_id:
                if (name == null || idCard == null) {
                    boolean isStater1 = permissionHelper.hasPermissions(this,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (!isStater1){
                        permissionHelper.requestPermissions("请授予该应用[相机][读写]权限！",permissionListener,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }else {
                        FaceUtils.getInstence().startORC(this, this);
                    }
//                    if (PermissionUtils.hasPermissons(this, Manifest.permission.CAMERA)) {
//                        //身份验证
//                        FaceUtils.getInstence().startORC(this, this);
//                    } else {
//                        PermissionUtils.getCameraPermissions(this, PERMISSION_CAMERA);
//
//                    }
                }
                break;
            case R.id.tv_send_code:
                bankPhone = etPhone.getText().toString();
                bankCardNo = etBankCard.getText().toString();
                bankName = tvBankName.getText().toString();
                if (name == null || idCard == null || TextUtils.isEmpty(bankName)) {
                    ToastUtils.showToast(this, "请先校验身份信息");
                    return;
                }
                if (!Utils.isMobile(bankPhone)) {
                    ToastUtils.showToast(this, "请输入正确的手机号");
                    return;
                }
                showLoading();
                AppUtils.tvSendCode(tvSendCode);
                codeMap = new HashMap<>();
                codeMap.put("bankCardNo", "" + bankCardNo);//银行卡号
                codeMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));//用户Id
                codeMap.put("bankName", bankName);//银行名字
                codeMap.put("realName", name);//真实姓名
                codeMap.put("cardNo", idCard);//身份证号
                codeMap.put("bankPhone", bankPhone);//手机号
                codeMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));//tokenId
                codeMap.put("idcardFrontImgUrl", idcardFrontPhoto);//正面照
                codeMap.put("idcardBackImgUrl", idcardBackPhoto);//反面照
                codeMap.put("softType", "" + CommonValueUtil.SOFTTYPE);//
                codeMap.put("version", "" + CommonValueUtil.VERSION);
                codeMap.put("funCode", FunCode.BANK_CODE);
                codeMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
                mPresenter.sendCardCode(codeMap);
                break;
            case R.id.tv_add_card:

                if (name == null || idCard == null) {
                    ToastUtils.showToast(this, "请先校验身份信息");
                    return;
                }
                bankPhone = etPhone.getText().toString();
                bankCardNo = etBankCard.getText().toString();
                verifyCode = etCode.getText().toString();
                bankName = tvBankName.getText().toString();

                if (TextUtils.isEmpty(bankCardNo) || TextUtils.isEmpty(bankName)) {
                    ToastUtils.showToast(this, "请填写正确的银行卡号");
                    etBankCard.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(bankPhone)) {
                    ToastUtils.showToast(this, "请填写银行卡预留手机号");
                    etPhone.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(verifyCode)) {
                    etCode.requestFocus();
                    ToastUtils.showToast(this, "请填写验证码");
                    return;
                }

                showLoading();
                addBankMap = new HashMap<>();
                addBankMap.put("bankCardNo", bankCardNo);
                addBankMap.put("logoCode", logoCode);
                addBankMap.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
                addBankMap.put("bankName", bankName);
                addBankMap.put("userAge", "");
                addBankMap.put("realName", name);
                addBankMap.put("cardNo", idCard);
                addBankMap.put("bankPhone", bankPhone);
                addBankMap.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
                addBankMap.put("idcardFrontImgUrl", idcardFrontPhoto);
                addBankMap.put("idcardBackImgUrl", idcardBackPhoto);
                addBankMap.put("verifyCode", verifyCode);
                addBankMap.put("softType", CommonValueUtil.SOFTTYPE);
                addBankMap.put("version", CommonValueUtil.VERSION);
                addBankMap.put("funCode", FunCode.ADD_BANK);
                addBankMap.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
                mPresenter.addCard(addBankMap);
                break;
        }
    }

    @Override
    public void onlyFaceResult(String result) {

    }

    @Override
    public void onlyRealNameResult(String result) {

    }

    @Override
    public void contrastResult(String result) {

    }

    @Override
    public void onlyOCRResult(String realName, String idCardNo, String idcardFrontPhoto, String idcardBackPhoto, String result) {
        name = realName;
        idCard = idCardNo;
        this.idcardFrontPhoto = idcardFrontPhoto;
        this.idcardBackPhoto = idcardBackPhoto;
        ToastUtils.showToast(this, "身份认证成功！！！");
        tvName.setText(realName);
        tvId.setText(idCardNo);
    }

    @Override
    public void onErrMsg(String code, String msg) {

    }

    @Override
    public void onCheckCardSuccess(BaseEntity<BankAscriptionBean> commitInfoBean) {
        if (commitInfoBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            BankAscriptionBean retData = commitInfoBean.getRetData();
            String result = retData.getResult();
            if ("0".equals(result)) {
                showSuccess("暂不支持银行卡");
                tvBankName.setText("");
                return;
            }
            showSuccess(null);
            logoCode = commitInfoBean.getRetData().getLogoCode();
            tvBankName.setText("" + retData.getBankName());
        } else {
            tvBankName.setText("");
            showSuccess(commitInfoBean.getRetMsg());
        }
    }

    @Override
    public void onSendCardCodeSuccess(BaseEntity<Object> commitInfoBean) {
        if (commitInfoBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            showSuccess("发送验证码成功");
        } else {
            showSuccess(commitInfoBean.getRetMsg());
        }
    }

    @Override
    public void onAddCardSuccess(BaseEntity<Object> commitInfoBean) {
        if (commitInfoBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            showSuccess("银行卡添加成功");
            finish();
        } else {
            showSuccess(commitInfoBean.getRetMsg());
        }
    }

    @Override
    public void onSupportBankSusscee(BaseEntity<SupportBankBean> supportBankBean) {
        if (supportBankBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            supportBankList = supportBankBean.getRetData().getPm().get(0).getBankList();
        } else {
            showSuccess(supportBankBean.getRetMsg());
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.handleRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
