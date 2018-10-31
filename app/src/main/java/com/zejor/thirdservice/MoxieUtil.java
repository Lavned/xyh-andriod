package com.zejor.thirdservice;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.moxie.client.MainActivity;
import com.moxie.client.model.MxParam;
import com.zejor.App;
import com.zejor.contants.CommonValueUtil;
import com.zejor.utils.SharedPerferenceUtil;
import com.zejor.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * 魔蝎的工具类,辅助完成魔蝎的认证项
 */

public class MoxieUtil {

    private static final String TAG = MoxieUtil.class.getSimpleName();
    private static Context mContext = App.getInstance();

    /**
     * fragment中调用魔蝎的第三方认证
     */
    public static void confirmMoxie(String title, Fragment fragment){
        String userId = (String) SharedPerferenceUtil.getData(mContext,"userId","");
        String mobile = (String) SharedPerferenceUtil.getData(mContext,"mobile","");//手机号
        String realName = (String) SharedPerferenceUtil.getData(mContext,"realName","");//真实姓名
        String idCardNO = (String) SharedPerferenceUtil.getData(mContext,"idCardNo","");//身份证号
        Bundle bundle = new Bundle();
        MxParam mxParam = new MxParam();
        mxParam.setUserId(userId);
        mxParam.setApiKey(CommonValueUtil.mXApiKey);
        mxParam.setAgreementUrl(CommonValueUtil.mXAgreementUrl);
        mxParam.setAgreementEntryText("同意数据获取协议");
        mxParam.setBannerBgColor("#1bbfa1");

        Intent intent = new Intent(fragment.getActivity(), MainActivity.class);

        switch(title){
            case "运营商":
                HashMap<String, String> extendParam = new HashMap<>();
                extendParam.put(MxParam.PARAM_CARRIER_IDCARD, idCardNO); // 身份证
                extendParam.put(MxParam.PARAM_CARRIER_PHONE, mobile); // 手机号
                extendParam.put(MxParam.PARAM_CARRIER_NAME, realName); // 姓名
                mxParam.setExtendParams(extendParam);
                mxParam.setFunction(MxParam.PARAM_FUNCTION_CARRIER);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                fragment.startActivityForResult(intent, 0);
                break;

            case "淘宝":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_TAOBAO);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                fragment.startActivityForResult(intent, 0);
                break;

            case "京东":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_JINGDONG);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                fragment.startActivityForResult(intent, 0);
                break;

            case "支付宝":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_ALIPAY);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                fragment.startActivityForResult(intent, 0);
                break;

            case "银行账单":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_EMAIL);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                fragment.startActivityForResult(intent, 0);

                break;
            case "社保":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_SECURITY);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                fragment.startActivityForResult(intent, 0);

                break;
            case "学信网":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_CHSI);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                fragment.startActivityForResult(intent, 0);

                break;
            case "公积金":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_FUND);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                fragment.startActivityForResult(intent, 0);

                break;
            case "信用卡":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_ONLINEBANK);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                fragment.startActivityForResult(intent, 0);

                break;
            case "个人征信":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_ZHENGXIN);
                mxParam.setLoginVersion("v2");
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                fragment.startActivityForResult(intent, 0);

                break;

        }
    }

    /**
     * Activity中调用魔蝎的第三方认证
     */
    public static void confirmMoxie(String title,Activity activity){
        String userId = (String) SharedPerferenceUtil.getData(mContext,"userId","1");
        String mobile = (String) SharedPerferenceUtil.getData(mContext,"mobile","");//手机号
        String realName = (String) SharedPerferenceUtil.getData(mContext,"realName","");//真实姓名
        String idCardNO = (String) SharedPerferenceUtil.getData(mContext,"idCardNo","");//身份证号

        Bundle bundle = new Bundle();
        MxParam mxParam = new MxParam();
        mxParam.setUserId(userId);
        mxParam.setApiKey(CommonValueUtil.mXApiKey);
        mxParam.setAgreementUrl(CommonValueUtil.mXAgreementUrl);
        mxParam.setAgreementEntryText("同意数据获取协议");

        Intent intent = new Intent(activity, MainActivity.class);

        switch(title){
            case "运营商":
                HashMap<String, String> extendParam = new HashMap<>();
                extendParam.put(MxParam.PARAM_CARRIER_IDCARD, idCardNO); // 身份证
                extendParam.put(MxParam.PARAM_CARRIER_PHONE, mobile); // 手机号
                extendParam.put(MxParam.PARAM_CARRIER_NAME, realName); // 姓名
                mxParam.setExtendParams(extendParam);
                mxParam.setFunction(MxParam.PARAM_FUNCTION_CARRIER);
                mxParam.setQuitLoginDone(MxParam.PARAM_COMMON_YES);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 2);
                break;

            case "淘宝":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_TAOBAO);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 0);
                break;

            case "京东":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_JINGDONG);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 0);
                break;

            case "支付宝":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_ALIPAY);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 0);
                break;

            case "银行账单":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_EMAIL);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 0);

                break;
            case "社保":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_SECURITY);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 0);

                break;
            case "学信网":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_CHSI);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 0);

                break;
            case "公积金":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_FUND);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 0);

                break;
            case "信用卡":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_ONLINEBANK);
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 0);

                break;
            case "个人征信":
                mxParam.setFunction(MxParam.PARAM_FUNCTION_ZHENGXIN);
                mxParam.setLoginVersion("v2");
                bundle.putParcelable("param", mxParam);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 0);

                break;

        }
    }

    /**
     * 魔蝎认证的返回结果
     */
    public static void moxieCallBack(int requestCode, int resultCode, Intent data,Context context,MoxieCallBackListener moxieCallBackListener){
        switch (requestCode) {
            case 0://请求魔蝎
                switch (resultCode) {
                    case RESULT_OK:
                        Bundle b = data.getExtras();
                        String result = b.getString("result");
                        Log.e(TAG, "moxieCallBack:  == "+ result );
                        if(TextUtils.isEmpty(result)){
                            ToastUtils.showToast(context,"用户没有进行导入操作");
                        }else {
                            try {
                                int code = 0;
                                JSONObject jsonObject = new JSONObject(result);
                                code = jsonObject.getInt("code");
                                String taskType = jsonObject.getString("taskType");
                                String thirdType = null,value = null;
                                switch (taskType){
                                    case "carrier":
                                        thirdType = "5";
                                        value = "CODE_1";
                                        break;
                                    case "taobao":
                                        thirdType = "5";
                                        value = "CODE_2";
                                        break;
                                    case "jingdong":
                                        thirdType = "5";
                                        value = "CODE_4";
                                        break;
                                    case "alipay":
                                        thirdType = "5";
                                        value = "CODE_5";
                                        break;
                                    case "email":
                                        thirdType = "5";
                                        value = "CODE_6";
                                        break;
                                    case "security"://社保
                                        thirdType = "5";
                                        value = "CODE_7";
                                        break;
                                    case "chsi":
                                        thirdType = "5";
                                        value = "CODE_8";
                                        break;
                                    case "fund":
                                        thirdType = "5";
                                        value = "CODE_9";
                                        break;
                                    case "bank":
                                        thirdType = "5";
                                        value = "CODE_10";
                                        break;
                                    case "zhengxin":
                                        thirdType = "5";
                                        value = "CODE_11";
                                        break;
                                }
                                switch (code) {
                                    case -1:
                                        ToastUtils.showToast(context, "取消操作");
                                        break;
                                    case -2:
                                        ToastUtils.showToast(context, "导入失败(平台方服务问题)");
                                        break;
                                    case -3:
                                        ToastUtils.showToast(context, "导入失败(魔蝎数据服务异常)");
                                        break;
                                    case -4:
                                        ToastUtils.showToast(context, "导入失败(" + jsonObject.getString("message") + ")");
                                        break;
                                    case 0:
                                        ToastUtils.showToast(context, "导入失败");
                                        break;
                                    case 1:
                                        String taskId = jsonObject.getString("taskId");
                                        if(moxieCallBackListener != null){
                                            moxieCallBackListener.moxieCallBack(taskId);
                                        }
                                        break;
                                    case 2:
                                        ToastUtils.showToast(context, "导入中");
                                        break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        break;
                }
                break;
            case 2://请求魔蝎
                switch (resultCode) {
                    case RESULT_OK:
                        Bundle b = data.getExtras();
                        String result = b.getString("result");
                        Log.e(TAG, "moxieCallBack:  == "+ result );
                        if(TextUtils.isEmpty(result)){
                            ToastUtils.showToast(context,"用户没有进行导入操作");
                        }else {
                            try {
                                int code = 0;
                                JSONObject jsonObject = new JSONObject(result);
                                code = jsonObject.getInt("code");
                                String taskType = jsonObject.getString("taskType");
                                String thirdType = null,value = null;
                                switch (taskType){
                                    case "carrier":
                                        thirdType = "5";
                                        value = "CODE_1";
                                        break;
                                    case "taobao":
                                        thirdType = "5";
                                        value = "CODE_2";
                                        break;
                                    case "jingdong":
                                        thirdType = "5";
                                        value = "CODE_4";
                                        break;
                                    case "alipay":
                                        thirdType = "5";
                                        value = "CODE_5";
                                        break;
                                    case "email":
                                        thirdType = "5";
                                        value = "CODE_6";
                                        break;
                                    case "security"://社保
                                        thirdType = "5";
                                        value = "CODE_7";
                                        break;
                                    case "chsi":
                                        thirdType = "5";
                                        value = "CODE_8";
                                        break;
                                    case "fund":
                                        thirdType = "5";
                                        value = "CODE_9";
                                        break;
                                    case "bank":
                                        thirdType = "5";
                                        value = "CODE_10";
                                        break;
                                    case "zhengxin":
                                        thirdType = "5";
                                        value = "CODE_11";
                                        break;
                                }
                                switch (code) {
                                    case -1:
                                        ToastUtils.showToast(context, "取消操作");
                                        break;
                                    case -2:
                                        ToastUtils.showToast(context, "导入失败(平台方服务问题)");
                                        break;
                                    case -3:
                                        ToastUtils.showToast(context, "导入失败(魔蝎数据服务异常)");
                                        break;
                                    case -4:
                                        ToastUtils.showToast(context, "导入失败(" + jsonObject.getString("message") + ")");
                                        break;
                                    case 0:
                                        ToastUtils.showToast(context, "导入失败");
                                        break;
                                    case 1:
                                        String taskId = jsonObject.getString("taskId");
                                        if(moxieCallBackListener != null){
                                            moxieCallBackListener.moxieCallBack(taskId);
                                        }
                                        break;
                                    case 2:
                                        ToastUtils.showToast(context, "导入中");
                                        break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        break;
                }
                break;
            default://其他
                break;
        }
    }


    public interface MoxieCallBackListener{
        void moxieCallBack(String taskId);
    }
}
