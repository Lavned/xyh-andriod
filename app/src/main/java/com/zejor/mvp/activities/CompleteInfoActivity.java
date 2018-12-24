package com.zejor.mvp.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.R;
import com.zejor.Utils;
import com.zejor.base.BaseActivity;
import com.zejor.base.BaseEntity;
import com.zejor.bean.CommitInfoBean;
import com.zejor.bean.ContactsBean;
import com.zejor.bean.SellCompleteBean;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerHttpComponent;
import com.zejor.contants.CommonValueUtil;
import com.zejor.contants.FunCode;
import com.zejor.customview.DataPopupWindow;
import com.zejor.mvp.contract.CompleteInfoContract;
import com.zejor.mvp.presenter.CompleteInfoPresenter;
import com.zejor.thirdservice.FaceUtils;
import com.zejor.utils.NetUtil;
import com.zejor.utils.PermissionHelper;
import com.zejor.utils.PermissionUtils;
import com.zejor.utils.SharedPerferenceUtil;
import com.zejor.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zejor.utils.ToastUtils.showToast;

public class CompleteInfoActivity extends BaseActivity<CompleteInfoPresenter> implements FaceUtils.OnYDCallBack, CompleteInfoContract.View {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_relationship1)
    TextView tvRelationship1;
    @BindView(R.id.tv_relationship2)
    TextView tvRelationship2;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_name1)
    TextView tvName1;
    @BindView(R.id.tv_phone1)
    TextView tvPhone1;
    @BindView(R.id.tv_name2)
    TextView tvName2;
    @BindView(R.id.tv_phone2)
    TextView tvPhone2;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.et_qq)
    EditText etQq;
    @BindView(R.id.et_wechat)
    EditText etWechat;
    @BindView(R.id.iv_front)
    ImageView ivFront;
    @BindView(R.id.iv_behind)
    ImageView ivBehind;
    private List<String> relationShipList = new ArrayList<>();
    private int ADDRESS_REQUEST = 301;
    private String phoneType;
    private int PERMISSION_ADDRESS = 300;
    private int PERMISSION_CAMERA = 200;
    private String name;
    private String idCard;
    private String frontPhoto;
    private String backPhoto;
    private HashMap<String, String> map;
    private HashMap<String, String> paramter;
    private Intent intent;
    private String loanAmount;
    private String loanStagesNum;
    private String arrivalAmount;
    private String fee;
    private String lastName;
    private HashMap<String, String> contactsParamter;

    @BindView(R.id.rl_phone1)
    RelativeLayout rl_phone1;

    @BindView(R.id.rl_phone2)
    RelativeLayout rl_phone2;

    @BindView(R.id.ll_linkman1)
    LinearLayout ll_linkman1;

    @BindView(R.id.ll_linkman2)
    LinearLayout ll_linkman2;

    @BindView(R.id.testPhone1)
    RelativeLayout testPhone1;

    @BindView(R.id.testPhone2)
    RelativeLayout testPhone2;
    private PermissionHelper permissionHelper;
    //已经上传资料用来传值
    private String idcardFrontImg="",idcardBackImg="",userRealName="",userCertificateNo="";
    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_complete_data_first;
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
        Bundle bundle = intent.getExtras();
        loanAmount =  bundle.getString("loanAmount");
        loanStagesNum = bundle.getString("loanStagesNum");
        arrivalAmount =  bundle.getString("arrivalAmount");
        fee =  bundle.getString("fee");
        //已有的资料
        idcardFrontImg=bundle.getString("idcardFrontImg");
        idcardBackImg=bundle.getString("idcardBackImg");
        userRealName=bundle.getString("userRealName");
        userCertificateNo=bundle.getString("userCertificateNo");
        //初始化资料
        tvName.setText(userRealName);
        tvId.setText(userCertificateNo);
        if (!TextUtils.isEmpty(idcardFrontImg)){
            Glide.with(CompleteInfoActivity.this).load(idcardFrontImg).into(ivFront);
            Glide.with(CompleteInfoActivity.this).load(idcardBackImg).into(ivBehind);
            name = userRealName;
            idCard = userCertificateNo;
            frontPhoto = idcardFrontImg;
            backPhoto = idcardBackImg;
        }


        //初始化弹窗选择关系的集合
        relationShipList.clear();
        List<String> relationShipListCopy = Arrays.asList(getResources().getStringArray(R.array.relationship));//亲友关系
        relationShipList.addAll(relationShipListCopy);
    }

    /**
     * 获取联系人列表
     */
    public void getContacts() {

        ArrayList<ContactsBean> list = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();

        ContentResolver resolver = getContentResolver();
        //搜索字段
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.DISPLAY_NAME};
        // 获取手机联系人
        Cursor contactsCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, null, null, null);

        if (contactsCursor != null) {
            ContactsBean bean = null;
            while (contactsCursor.moveToNext()) {


                //获取联系人的ID
                int contactId = contactsCursor.getInt(0);
                //获取联系人的姓名
                String name = contactsCursor.getString(2);
                //获取联系人的号码
                String phoneNumber = contactsCursor.getString(1);
                //号码处理
                String replace = phoneNumber.replace(" ", "").replace("-", "").replace("+86", "");


                if (name.equals(lastName)) {
                    bean.getPhone().add(replace);
                } else {
                    bean = new ContactsBean();
                    List<String> phoneList = new ArrayList<>();
                    phoneList.add(replace);
                    bean.setName(name);
                    bean.setPhone(phoneList);
                    list.add(bean);
                }

            }
            contactsCursor.close();


            for (ContactsBean bean2 : list) {
                String name = bean2.getName();
                List<String> phone = bean2.getPhone();

                List<String> allPhone = new ArrayList<>();

                if (name.equals(lastName)) {
                    if (null != allPhone) {
                        allPhone.addAll(phone);
                        List<String> list1 = map.get(name);
                        list1.addAll(phone);
                        map.put(name, list1);
                    }
                } else {
                    map.put(name, phone);
                }
                lastName = name;

            }
            Gson gson = new Gson();
            String json = gson.toJson(map);

            contactsParamter = new HashMap<>();
            contactsParamter.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
            contactsParamter.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
            contactsParamter.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
            contactsParamter.put("content", json);
            contactsParamter.put("version", CommonValueUtil.VERSION);
            contactsParamter.put("softType", CommonValueUtil.SOFTTYPE);
            contactsParamter.put("funCode", FunCode.CALLCONTACTS);
            mPresenter.contactsCall(contactsParamter);
        }


    }

    @OnClick({R.id.iv_back, R.id.ll_linkman1, R.id.ll_linkman2, R.id.tv_next, R.id.rl_phone1,
            R.id.rl_phone2, R.id.iv_front, R.id.iv_behind,R.id.testPhone1,R.id.testPhone2})
    public void onViewClicked(View view) {
        //新的权限申请
        permissionHelper = new PermissionHelper(CompleteInfoActivity.this);
        PermissionHelper.PermissionListener permissionListener;
        permissionListener = new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permission) {
                    Log.e("doAfterGrand","success");
            }
            @Override
            public void doAfterDenied(String... permission) {
                showToast("点击权限，并打开需要的权限");
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 5);
            }
        };
        boolean isStater ;
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_linkman1:
                selectFirstRelationShip();
                break;
            case R.id.ll_linkman2:
                selectSecondRelationShip();
                break;
            case R.id.testPhone1:
                phoneType = "firstPhone";
//                if (PermissionUtils.hasPermissons(CompleteInfoActivity.this, Manifest.permission.READ_CONTACTS)) {
//                    startAddressBook();
//                    getContacts();
//                } else {
//                    PermissionUtils.getContactsPermissions(CompleteInfoActivity.this, PERMISSION_ADDRESS);
//                }
                isStater = permissionHelper.hasPermissions(this,Manifest.permission.READ_CONTACTS);
                if (!isStater){
                    permissionHelper.requestPermissions("请授予该应用通讯录权限！",permissionListener,Manifest.permission.READ_CONTACTS);
                }else {
                    startAddressBook();
                    getContacts();
                }
                break;
            case R.id.testPhone2:
                phoneType = "secondPhone";
//                if (PermissionUtils.hasPermissons(CompleteInfoActivity.this, Manifest.permission.READ_CONTACTS)) {
//                    startAddressBook();
//                } else {
//                    PermissionUtils.getContactsPermissions(CompleteInfoActivity.this, PERMISSION_ADDRESS);
//                }
                isStater = permissionHelper.hasPermissions(this,Manifest.permission.READ_CONTACTS);
                if (!isStater){
                    permissionHelper.requestPermissions("请授予该应用通讯录权限！",permissionListener,Manifest.permission.READ_CONTACTS);
                }else {
                    startAddressBook();
                }
                break;
            case R.id.rl_phone1:
                phoneType = "firstPhone";
//                if (PermissionUtils.hasPermissons(CompleteInfoActivity.this, Manifest.permission.READ_CONTACTS)) {
//                    startAddressBook();
//                    getContacts();
//                } else {
//                    PermissionUtils.getContactsPermissions(CompleteInfoActivity.this, PERMISSION_ADDRESS);
//                }
                isStater = permissionHelper.hasPermissions(this,Manifest.permission.READ_CONTACTS);
                if (!isStater){
                    permissionHelper.requestPermissions("请授予该应用通讯录权限！",permissionListener,Manifest.permission.READ_CONTACTS);
                }else {
                    startAddressBook();
                    getContacts();
                }
                break;
            case R.id.rl_phone2:
                phoneType = "secondPhone";
//                if (PermissionUtils.hasPermissons(CompleteInfoActivity.this, Manifest.permission.READ_CONTACTS)) {
//                    startAddressBook();
//                } else {
//                    PermissionUtils.getContactsPermissions(CompleteInfoActivity.this, PERMISSION_ADDRESS);
//                }
                 isStater = permissionHelper.hasPermissions(this,Manifest.permission.READ_CONTACTS);
                if (!isStater){
                    permissionHelper.requestPermissions("请授予该应用通讯录权限！",permissionListener,Manifest.permission.READ_CONTACTS);
                }else {
                    startAddressBook();
                }
                break;
            case R.id.iv_behind:
                 isStater = permissionHelper.hasPermissions(this,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (!isStater){
                    permissionHelper.requestPermissions("请授予该应用[相机][读写]权限！",permissionListener,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }else {
                    FaceUtils.getInstence().startORC(this, this);
                }
//                if (PermissionUtils.hasPermissons(this, Manifest.permission.CAMERA)) {
//                    //身份验证
//                    FaceUtils.getInstence().startORC(this, this);
//                } else {
//                    PermissionUtils.getCameraPermissions(this, PERMISSION_CAMERA);
//
//                }
                break;
            case R.id.iv_front:
                boolean isStater1 = permissionHelper.hasPermissions(this,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (!isStater1){
                    permissionHelper.requestPermissions("请授予该应用[相机][读写]权限！",permissionListener,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }else {
                    FaceUtils.getInstence().startORC(this, this);
                }

//                if (PermissionUtils.hasPermissons(this, Manifest.permission.CAMERA)) {
//                    //身份验证
//                    FaceUtils.getInstence().startORC(this, this);
//                } else {
//                    //这里就是权
//                    PermissionUtils.getCameraPermissions(this, PERMISSION_CAMERA);
//
//                }
                break;
            case R.id.tv_next:
                String qq = etQq.getText().toString();
                String weChat = etWechat.getText().toString();
                String firstContactName = tvName1.getText().toString();
                String secondContactName = tvName2.getText().toString();
                String relationShip1 = tvRelationship1.getText().toString();
                String relationShip2 = tvRelationship2.getText().toString();
                String phone1 = tvPhone1.getText().toString();
                String phone2 = tvPhone2.getText().toString();
                if (TextUtils.isEmpty(name)//判断基础的填入信息是否为空
                        || TextUtils.isEmpty(idCard)
                        || TextUtils.isEmpty(weChat)
                        || TextUtils.isEmpty(qq)
                        || TextUtils.isEmpty(relationShip1)
                        || TextUtils.isEmpty(relationShip2)
                        || TextUtils.isEmpty(phone1)
                        || TextUtils.isEmpty(phone2)) {
                    showToast("请将个人信息补充完整");
                    return;
                }
                if ((!Utils.isMobile(phone1)) || (!Utils.isMobile(phone2))) {
                    Toast.makeText(this, "请选择正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone1.equals(phone2)) {
                    Toast.makeText(this, "两个联系人号码不能相同", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone1.equals(SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""))) {
                    Toast.makeText(this, "联系人号码不能为自己", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone2.equals(SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""))) {
                    Toast.makeText(this, "联系人号码不能为自己", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvName1.getText().toString().trim().equals(tvName2.getText().toString().trim())) {
                    Toast.makeText(this, "联系人名称不能重复", Toast.LENGTH_SHORT).show();
                    return;
                }

                showLoading();
                map = new HashMap<>();
                map.put("mobile", (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", ""));
                map.put("userId", (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", ""));
                map.put("tokenId", (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", ""));
                map.put("qq", qq);
                map.put("weixin", weChat);
                map.put("firstContactRelation", relationShip1);
                map.put("firstContactMobile", phone1);
                map.put("firstContactName", firstContactName);
                map.put("secondContactRelation", relationShip2);
                map.put("secondContactMobile", phone2);
                map.put("secondContactName", secondContactName);
                map.put("version", CommonValueUtil.VERSION);
                map.put("softType", CommonValueUtil.SOFTTYPE);
                map.put("funCode", FunCode.COMMIT_INFO);

                map.put("userProvince", "");
                map.put("userCity", "");
                map.put("userArea", "");
                map.put("userAddress", "");
                map.put("userMarriage", "");
                map.put("userMail", "");
                mPresenter.commitInfo(map);

                rl_phone1.setEnabled(false);
                etQq.setEnabled(false);
                etWechat.setEnabled(false);
                ivBehind.setClickable(false);
                ivFront.setClickable(false);
                rl_phone2.setEnabled(false);
                testPhone1.setEnabled(false);
                testPhone2.setEnabled(false);
                ll_linkman1.setEnabled(false);
                ll_linkman2.setEnabled(false);



                break;
            default:
                break;
        }

    }

    public void startAddressBook() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, ADDRESS_REQUEST);
    }

    /**
     * 选择第一亲友关系
     */
    private void selectFirstRelationShip() {
        String stringShip1 = tvRelationship1.getText().toString();
        String stringShip2 = tvRelationship2.getText().toString();
        if (!stringShip1.equals("请选择")) {
            if (!relationShipList.contains(stringShip1)) {
                relationShipList.add(stringShip1);
            }
        }
        if (!stringShip2.equals("请选择")) {
            if (relationShipList.contains(stringShip2)) {
                relationShipList.remove(stringShip2);
            }
        }
        DataPopupWindow popupWindow2 = new DataPopupWindow(this, relationShipList);
        popupWindow2.showPop(tvNext);
        popupWindow2.setOnDialogItemClickListener(new DataPopupWindow.OnDialogItemClickListener() {
            @Override
            public void onDialogItemClick(String str) {
                tvRelationship1.setText(str);
                relationShipList.remove(str);
//                SharedPerferenceUtil.saveData(App.getInstance(), "firstContactRelation", str);
            }
        });
    }

    /**
     * 选择第二亲友关系
     */
    private void selectSecondRelationShip() {
        String stringShip1 = tvRelationship1.getText().toString();
        String stringShip2 = tvRelationship2.getText().toString();
        if (!stringShip1.equals("请选择")) {
            if (relationShipList.contains(stringShip1)) {
                relationShipList.remove(stringShip1);
            }
        }
        if (!stringShip2.equals("请选择")) {
            if (!relationShipList.contains(stringShip2)) {
                relationShipList.add(stringShip2);
            }
        }
        DataPopupWindow popupWindow3 = new DataPopupWindow(this, relationShipList);
        popupWindow3.showPop(tvNext);
        popupWindow3.setOnDialogItemClickListener(new DataPopupWindow.OnDialogItemClickListener() {
            @Override
            public void onDialogItemClick(String str) {
                tvRelationship2.setText(str);
                relationShipList.remove(str);
//                SharedPerferenceUtil.saveData(App.getInstance(), "secondContactRelation", str);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPhoneNum(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CommonValueUtil.TURNOFF) {
            setResult(RESULT_OK);
            finish();
        }
    }

    /**
     * 获取手机通讯录的结果
     */
    private void getPhoneNum(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADDRESS_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                Uri result = data.getData();
                String[] phoneContacts = getPhoneContacts(result);
                if (null == phoneContacts) {
                    return;
                }
                String phoneNum = phoneContacts[phoneContacts.length - 1];//手机号
                switch (phoneType) {
                    case "firstPhone":
                        tvPhone1.setText(phoneNum);
                        tvName1.setText(phoneContacts[0]);
//                        SharedPerferenceUtil.saveData(App.getInstance(), "firstContactMobile", phoneNum);
                        break;
                    case "secondPhone":
                        if (phoneNum.equals(tvPhone1.getText().toString())){
                            Toast.makeText(this, "联系人号码重复", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tvPhone2.setText(phoneNum);
                        tvName2.setText(phoneContacts[0]);
//                        SharedPerferenceUtil.saveData(App.getInstance(), "secondContactMobile", phoneNum);
                        break;
                    default:
                        break;
                }

            }
        }
    }

    /**
     * 处理返回的信息,获取选择的联系人电话和姓名
     */
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        try {
            //得到ContentResolver对象
            ContentResolver cr = getContentResolver();
            //取得电话本中开始一项的光标
            Cursor cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                //取得联系人姓名
                int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                contact[0] = cursor.getString(nameFieldColumnIndex);
                //取得电话号码
                String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
                if (phone != null) {
                    phone.moveToFirst();
                    contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace("+86", "").replaceAll(" ", "").trim();
                }
                phone.close();
                cursor.close();
            } else {
                showToast(this, "请选择正确格式的联系人");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast(this, "请选择正确格式的联系人");
            return null;
        } finally {

        }

        return contact;
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
        frontPhoto = idcardFrontPhoto;
        backPhoto = idcardBackPhoto;
        showToast(this, "身份认证成功！！！");
        tvName.setText(realName);
        tvId.setText(idCardNo);
        Glide.with(CompleteInfoActivity.this).load(idcardFrontPhoto).into(ivFront);
        Glide.with(CompleteInfoActivity.this).load(idcardBackPhoto).into(ivBehind);
        SharedPerferenceUtil.saveData(CompleteInfoActivity.this, "realName", realName);//真实姓名
        SharedPerferenceUtil.saveData(CompleteInfoActivity.this, "idCardNo", idCardNo);//身份证号

        new Thread(new Runnable() {
            @Override
            public void run() {
                paramter = new HashMap<>();
                paramter.put("mobile", (String) SharedPerferenceUtil.getData(CompleteInfoActivity.this, "mobile", ""));
                paramter.put("version", CommonValueUtil.VERSION);
                paramter.put("softType", CommonValueUtil.SOFTTYPE);
                paramter.put("funCode", FunCode.SUBMITALLINFO);
                paramter.put("tokenId", (String) SharedPerferenceUtil.getData(CompleteInfoActivity.this, "tokenId", ""));
                paramter.put("userId", (String) SharedPerferenceUtil.getData(CompleteInfoActivity.this, "userId", ""));
                paramter.put("userRealName", name);
                paramter.put("userCertificateNo", idCard);
                try {
                    paramter.put("fPhoto", NetUtil.loadRawDataFromURL(frontPhoto));
                    paramter.put("bPhoto", NetUtil.loadRawDataFromURL(backPhoto));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPresenter.uploadData(paramter);
            }
        }).start();
    }

    @Override
    public void onErrMsg(String code, String msg) {

    }

    @Override
    public void loadInfo(BaseEntity<CommitInfoBean> commitInfoBean) {
        if (commitInfoBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            startActivityForResult(new Intent(this, CompleteAllDataActivity.class)
                    .putExtra("loanAmount", loanAmount)
                    .putExtra("loanStagesNum", loanStagesNum)
                    .putExtra("arrivalAmount", arrivalAmount)
                    .putExtra("fee", fee), CommonValueUtil.TURNOFF);
            showSuccess("提交成功");
        } else {
            showSuccess(commitInfoBean.getRetMsg());
        }
    }

    @Override
    public void loadData(BaseEntity<SellCompleteBean> sellCompleteBean) {
        if (sellCompleteBean.getRetCode().equals(CommonValueUtil.SUCCESS)) {
            showSuccess(null);
        } else {
            showSuccess(sellCompleteBean.getRetMsg());
        }
    }

    @Override
    public void contactsCallSuccess(BaseEntity<String> contactsCall) {
        Logger.e(contactsCall.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.handleRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //    /**
//     * 权限请求结果回调
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        boolean hasAllGranted = true;
//        StringBuilder  permissionName = new StringBuilder();
//        for (String s: permissions) {
//            permissionName = permissionName.append(s + "\r\n");
//        }
//        switch (requestCode) {
//            case mRequestCode: {
//                for (int i = 0; i < grantResults.length; ++i) {
//                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                        hasAllGranted = false;
//                        //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
//                        // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
//                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//                            new AlertDialog.Builder(this)
//                                    .setMessage("【用户选择了不在提示按钮，或者系统默认不在提示（如MIUI）。" +
//                                            "引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】\r\n" +
//                                            "获取相关权限失败:\r\n" +
//                                            permissionName +
//                                            "将导致部分功能无法正常使用，需要到设置页面手动授权")
//                                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
//                                            intent.setData(uri);
//                                            startActivity(intent);
//                                        }
//                                    })
//                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            mRequestPermissionCallBack.denied();
//                                        }
//                                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialog) {
//                                    mRequestPermissionCallBack.denied();
//                                }
//                            }).show();
//
//                        } else {
//                            //用户拒绝权限请求，但未选中“不再提示”选项
//                            mRequestPermissionCallBack.denied();
//                        }
//                        break;
//                    }
//                }
//                if (hasAllGranted) {
//                    mRequestPermissionCallBack.granted();
//                }
//            }
//        }
//    }
//
//    /**
//     * 发起权限请求
//     *
//     * @param context
//     * @param permissions
//     * @param callback
//     */
//    public void requestPermissions(final Context context, final String[] permissions,
//                                   RequestPermissionCallBack callback) {
//        this.mRequestPermissionCallBack = callback;
//        StringBuilder permissionNames = new StringBuilder();
//        for(String s : permissions){
//            permissionNames = permissionNames.append(s + "\r\n");
//        }
//        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
//        boolean isAllGranted = true;
//        for (String permission : permissions) {
//            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
//                isAllGranted = false;
//
//                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
//                    new AlertDialog.Builder(context)
//                            .setMessage("【用户曾经拒绝过你的请求，所以这次发起请求时解释一下】\r\n" +
//                                    "您好，需要如下权限：\r\n" +
//                                    permissionNames+
//                                    " 请允许，否则将影响部分功能的正常使用。")
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ActivityCompat.requestPermissions(((Activity) context), permissions, mRequestCode);
//                                }
//                            }).show();
//                } else {
//                    ActivityCompat.requestPermissions(((Activity) context), permissions, mRequestCode);
//                }
//
//                break;
//            }
//        }
//        if (isAllGranted) {
//            mRequestPermissionCallBack.granted();
//            return;
//        }
//    }
//
//    /**
//     * 权限请求结果回调接口
//     */
//    public interface RequestPermissionCallBack {
//        /**
//         * 同意授权
//         */
//        public void granted();
//
//        /**
//         * 取消授权
//         */
//        public void denied();
//    }



//    public void requestSinglePermission(View view){
//        requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},new RequestPermissionCallBack() {
//            @Override
//            public void granted() {
//                Toast.makeText(CompleteInfoActivity.this, "获取权限成功，执行正常操作", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void denied() {
//                Toast.makeText(CompleteInfoActivity.this, "获取权限失败，正常功能受到影响", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    public void requestMultiPermission(){
//        requestPermissions(this, new String[]{Manifest.permission.CAMERA,
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_CONTACTS},
//                new RequestPermissionCallBack() {
//                    @Override
//                    public void granted() {
//                        Toast.makeText(CompleteInfoActivity.this, "获取权限成功，执行正常操作", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void denied() {
//                        Toast.makeText(CompleteInfoActivity.this, "获取权限失败，正常功能受到影响", Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
}
