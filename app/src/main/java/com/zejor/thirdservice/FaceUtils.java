package com.zejor.thirdservice;

import android.content.Context;

//import com.authreal.api.AuthBuilder;
//import com.authreal.api.FormatException;
//import com.authreal.api.OnResultListener;
//import com.authreal.component.AuthComponentFactory;
//import com.authreal.component.CompareItemFactory;
//import com.authreal.component.CompareItemSession;
//import com.authreal.component.LivingComponent;
//import com.authreal.component.OCRComponent;
//import com.authreal.component.VerifyComponent;
//import com.face.bsdk.FVSdk;
//import com.face.bsdk.crypt.Md5;
//import com.face.bsdk.FVSdk;
//import com.face.bsdk.crypt.Md5;
import com.authreal.api.AuthBuilder;
import com.authreal.api.FormatException;
import com.authreal.api.OnResultListener;
import com.authreal.component.AuthComponentFactory;
import com.authreal.component.CompareItemFactory;
import com.authreal.component.CompareItemSession;
import com.authreal.component.LivingComponent;
import com.authreal.component.OCRComponent;
import com.authreal.component.VerifyComponent;
import com.authreal.util.FVSdk;
import com.authreal.util.Md5;
import com.orhanobut.logger.Logger;
import com.zejor.App;
import com.zejor.contants.CommonValueUtil;
import com.zejor.utils.SharedPerferenceUtil;
import com.zejor.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * https://static.udcredit.com/doc/idsafe/android/V43/product_compare.html
 * 人脸验证的工具类 辅助完成人脸验证
 */

public class FaceUtils {

    private static String userId = (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", "");

    private static String TAG = "FaceUtils";

    private FaceUtils() {

    }

    private static FaceUtils instence;

    public static FaceUtils getInstence() {
        if (instence == null) {
            synchronized (FaceUtils.class) {
                if (instence == null) {
                    instence = new FaceUtils();
                }
            }
        }
        return instence;
    }

    /**
     * 获取AuthBuilder。
     * 请在每次调用前获取新的AuthBuilder
     * 一个AuthBuilder 不要调用两次start()方法
     *
     * @return
     */
    public AuthBuilder getAuthBuilder() {
        // 订单号商户自己生成：不超过36位，非空，不能重复
        String outOrderId = userId + "_Android" + System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //签名时间：有效期5分钟，请每次重新生成 :签名时间格式：yyyyMMddHHmmss
        String sign_time = simpleDateFormat.format(new Date());
        // 签名规则
        String singStr = "pub_key=" + CommonValueUtil.pub_key + "|partner_order_id=" + outOrderId + "|sign_time=" + sign_time + "|security_key=" + CommonValueUtil.security_key;
        //生成 签名
        String sign = Md5.encrypt(singStr);
        /** 以上签名 请在服务端生成，防止key泄露 */


        AuthBuilder authBuilder = new AuthBuilder(outOrderId, CommonValueUtil.pub_key, sign_time, sign, new OnResultListener() {


            //            AuthBuilder mAuthBuidler = new AuthBuilder("1354321032456123", pubKey, "", new OnResultListener() {
            @Override
            public void onResult(int op_type, String result) {
                Logger.e("onResult == " + op_type + "//" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    //业务处理成功（不是认证成功）

                    if (jsonObject.has("success") && jsonObject.getString("success").equals("true")) {
                        //业务处理成功 ，可以根据不同的模块 处理数据
//                         Logger.e("onResult == "+ op_type+"//"+result);
                        switch (op_type) {
                            case AuthBuilder.OPTION_ERROR:
                                // TODO:  error
                                if (null != callBack) {
                                    callBack.onErrMsg("", "" + result);
                                }
                                String message = jsonObject.getString("message");
                                ToastUtils.showToast(message);
                                break;
                            case AuthBuilder.OPTION_OCR:
                                // TODO:  OCR扫描 回调
                                //身份证号
                                JSONObject object = new JSONObject(result);
                                String idCardNO = object.getString("id_number");
                                //姓名
                                String realName = object.getString("id_name");
                                //年龄
                                String userAge = object.getString("age");
                                //身份证正面
                                String idcardFrontPhoto = object.getString("idcard_front_photo");
                                //身份证反面
                                String idcard_back_photo = object.getString("idcard_back_photo");
                                if (null != callBack) {
                                    SharedPerferenceUtil.saveData(App.getInstance(), "userAge", userAge);
                                    callBack.onlyOCRResult("" + realName, "" + idCardNO, "" + idcardFrontPhoto, "" + idcard_back_photo, result);
                                }
                                break;
                            case AuthBuilder.OPTION_VERIFY:
                                //// TODO:  实名验证 回调
                                if (null != callBack) {
                                    callBack.onlyRealNameResult(result);
                                }
                                break;
                            case AuthBuilder.OPTION_LIVENESS:
                                //// TODO:  活体 回调
                                if (null != callBack) {
                                    callBack.onlyFaceResult(result);
                                }
                                break;
                            case AuthBuilder.OPTION_COMPARE:
                                //// TODO:  人像比对 回调
                                if (null != callBack) {
                                    callBack.contrastResult(result);
                                }
                                break;
                            case AuthBuilder.OPTION_VIDEO:
                                //// TODO:  视频存证 回调
                                break;
                            default:
                                break;
                            case AuthBuilder.OPTION_VERIFY_COMPARE:
                                //// TODO:  人像比对 回调

                                if (null != callBack) {
                                    callBack.contrastResult(result);
                                }
                                break;
                        }
                    } else {

                        String message = jsonObject.getString("message");
                        String errorcode = jsonObject.getString("errorcode");
                        Logger.e(errorcode + ":" + message);
                        ToastUtils.showToast(message);
                        if (null != callBack) {
                            callBack.onErrMsg("" + errorcode, "" + message);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

        return authBuilder;
    }

    /**
     * 只拍照
     *
     * @return
     */
    private OCRComponent onlyPhoto() {
        AuthComponentFactory.getOcrComponent();
        OCRComponent ocrComponenet = AuthComponentFactory.getOcrComponent();
        ocrComponenet.showConfirm(true);//是否显示确认页面
//        ocrComponenet.mosaicIdName(true);//是否隐藏姓名
        ocrComponenet.mosaicIdNumber(false);//是否隐藏身份证号
        ocrComponenet.showConfirmIdNumber(true);//身份证号码在确认页面明文展示
//        compareComponent.setNotifyUrl(""); //结果异步通知地址
        return ocrComponenet;
    }

    /**
     * 制作活体识别
     *
     * @return
     */
    private LivingComponent onlyFace() {
        LivingComponent livingComponent = AuthComponentFactory.getLivingComponent();
        /*声音true打开*/
//        livingComponent.setVoiceEnable(true);
        /*单个动作*/
        livingComponent.setSingle(false);
        livingComponent.setNotifyUrl("https://liaoxc.mulhyac.com/qhh2-user/user/callbackTest");
        /*活体难度*/
        livingComponent.setFV_SAFE_MODE(FVSdk.FVSafeMode.FVSafeHighMode);
        return livingComponent;
    }

    /**
     * 只做实名认证
     *
     * @return
     */
    private VerifyComponent onlyRealName(String realName, String idCardNO) {

        try {
            VerifyComponent verifyComponent = AuthComponentFactory.getVerifyComponent();

            verifyComponent.setNameAndNumber("" + realName, "" + idCardNO);
            verifyComponent.needGridPhoto(true);
//            verifyComponent.setNotifyUrl("");
            return verifyComponent;
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 身份认证加活体识别对比
     */
    public void contrastRealNameAndFace(String realName, String idCardNO, Context context, OnYDCallBack callBack) {
        this.callBack = callBack;

        try {
            getAuthBuilder()
                    /*添加实名认证验证*/
//                    .addFollow(onlyRealName(realName, idCardNO))
                    /* 添加 活体检测 模块 */
                    .addFollow(onlyFace())
                    /** 添加 人脸比对 模块 */
    //                .addFollow(AuthComponentFactory.getCompareComponent()
    //                        /** 设置A比对象为 实名认证 */
    //                        .setCompareItemA(CompareItemFactory.getCompareItemBySessionId
    //                                (CompareItemSession.SessionType.PHOTO_IDENTIFICATION))
    //                        /** 设置B比对象为 活体检测 活体照 */
    //                        .setCompareItemB(CompareItemFactory.getCompareItemBySessionId
    //                                (CompareItemSession.SessionType.PHOTO_LIVING))
    ////                        .isGrid(true))
    //                )
                    .addFollow(AuthComponentFactory.getVerifyCompareComponent()
                            //此示例对比项B为活体过程中截图,
                            .setCompareItem(CompareItemFactory.getCompareItemBySessionId(CompareItemSession.SessionType.PHOTO_LIVING))
                            //如果认证比对图片为OCR获取，此项为非必填项，人证比对图片不是OCR获取，此项必填
                            .setNameAndNumber(realName, idCardNO)
//                            .setNotifyUrl("http:......")
                    )
                    /** 开始流程 */
                    .start(context);
        } catch (FormatException e) {
            e.printStackTrace();
        }

//        AuthBuilder authBuilder = getAuthBuilder();


    }


    /**
     * 单拍照
     */
    public void startFace(Context context, OnYDCallBack callBack) {
        this.callBack = callBack;
        getAuthBuilder().addFollow(onlyFace()).start(context);
    }

    /**
     * 单拍照OCR
     */
    public void startORC(Context context, OnYDCallBack callBack) {
        this.callBack = callBack;
        getAuthBuilder().addFollow(onlyPhoto()).start(context);
    }

    private OnYDCallBack callBack;

    public interface OnYDCallBack {
        void onlyFaceResult(String result);//活体人脸回调

        void onlyRealNameResult(String result);//实名认证回调

        void contrastResult(String result);//实名认证加活体对比回调

        void onlyOCRResult(String realName, String idCardNo, String idcardFrontPhoto, String idcard_back_photo, String result);//拍照回调

        void onErrMsg(String code, String msg);
    }

}
