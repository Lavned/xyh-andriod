package com.zejor.contants;

import com.zejor.Utils;
import com.zejor.utils.AppUtils;

/**
 * 公共信息
 */

public class CommonValueUtil {

    //正式环境
    public static final String RELEASE_URL = "http://loan.xinyonghua.net/api/";  //先注释了

    //发布地址
    public static final String URL = RELEASE_URL;

    //借款协议地址
    public static final String SECOND_URL = "gw";

    //魔蝎秘钥
    public static final String mXApiKey = "7595d9180ceb44bca74db2ac2aea1d5e";
    public static final String mXAgreementUrl = "https://api.51datakey.com/h5/agreement.html";
    //有盾秘钥
    public static final String pub_key = "5bd81f28-f700-4b29-9f34-07243459255c";
    public static final String security_key = "e7663b69-e391-461c-bdaa-6684d54264c6";


    //version
    public static final String VERSION = AppUtils.getAppVersionName();
    //softType
    public static final String SOFTTYPE = "android_xyh_v1.0";


    public static final String H5_TYPE = "android";


    public static final String SUCCESS = "0000";
    public static final int TURNOFF = 1123;



    public static final String MOBILE_MEMORY = Utils.readSDCard() + Utils.readSystem() + "";
    public static final String MOBILE_BRAND = android.os.Build.BRAND;
    public static final String MOBILE_MODELS = android.os.Build.MODEL;
    public static final String IMEI = Utils.getIMEI();

}
