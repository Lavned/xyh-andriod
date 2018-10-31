package com.zejor.contants;


import com.zejor.base.BaseEntity;
import com.zejor.bean.AdvertisementBean;
import com.zejor.bean.AgreementUrlBean;
import com.zejor.bean.AuthenticationBean;
import com.zejor.bean.BankAscriptionBean;
import com.zejor.bean.BankBean;
import com.zejor.bean.BannerBean;
import com.zejor.bean.CommitFaceInfoBean;
import com.zejor.bean.CommitInfoBean;
import com.zejor.bean.CreditAmountBean;
import com.zejor.bean.FaceBean;
import com.zejor.bean.LoginDataBean;
import com.zejor.bean.MobilePriceBean;
import com.zejor.bean.OrderBean;
import com.zejor.bean.OrderChangeStatusBean;
import com.zejor.bean.OrderDetailBean;
import com.zejor.bean.PayBean;
import com.zejor.bean.PhonePicBean;
import com.zejor.bean.ProcotolBean;
import com.zejor.bean.RefreshBean;
import com.zejor.bean.RepaymentMoneyBean;
import com.zejor.bean.RepaymentPayBean;
import com.zejor.bean.SellCompleteBean;
import com.zejor.bean.SupportBankBean;
import com.zejor.bean.UpdateBean;
import com.zejor.bean.ZhiMaBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * 所有网络请求
 * Created by mango on 2018/7/12.
 */

public interface ApiService {


    /**
     * 可用银行
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<SupportBankBean>> supportBank(@Body RequestBody body);

    /**
     * Banner
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<BannerBean>> bannerCall(@Body RequestBody body);


    /**
     * 滚动借款信息
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<List<AdvertisementBean>>> advertisementCall(@Body RequestBody body);



    /**
     * 验证码
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<Object>> sendCode(@Body RequestBody body);

    /**
     * 登录
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<LoginDataBean>> userLogin(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<PhonePicBean>> phonePic(@Body RequestBody body);

    /**
     * 提交资料
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<SellCompleteBean>> sellComplete(@Body RequestBody body);

    /**
     * 订单
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<List<OrderBean>>> getOrder(@Body RequestBody body);

    /**
     * 订单
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<Object>> expressOrder(@Body RequestBody body);

    /**
     * 取消订单
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<Object>> cancelOrder(@Body RequestBody body);

    /**
     * 保存用户信息
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<Object>> savePhoneMessage(@Body RequestBody body);

    /**
     *  发起支付
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<RepaymentMoneyBean>> repaymentMoney(@Body RequestBody body);

    /**
     * 订单
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<OrderDetailBean>> orderDetail(@Body RequestBody body);

    /**
     * 支付方式
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<RepaymentPayBean>> payMode(@Body RequestBody body);

    /**
     * 支付
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<PayBean>> toPay(@Body RequestBody body);

    /**
     *是否进行过人脸验证
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<FaceBean>> faceCall(@Body RequestBody body);

    /**
     * 提交人脸
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<CommitFaceInfoBean>> faceInfoCall(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<MobilePriceBean>> mobilePrice(@Body RequestBody body);

    /**
     * 个人信息
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<CommitInfoBean>> commitCall(@Body RequestBody body);

    /**
     * 获取三方审核
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<List<AuthenticationBean>>> authenticationCall(@Body RequestBody body);

    /**
     * 获取三方审核
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<Object>> commitOrder(@Body RequestBody body);

    /**
     * 银行卡
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<List<BankBean>>> getBankList(@Body RequestBody body);

    /**
     * 行卡归属银行
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<BankAscriptionBean>> checkCard(@Body RequestBody body);

    /**
     * 添加银行卡
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<Object>> addBank(@Body RequestBody body);

    /**
     * 提现信息
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<CreditAmountBean>> getCreditAmount(@Body RequestBody body);

    /**
     * 验证码
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<Object>> bankCode(@Body RequestBody body);

    /**
     * 提现
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<Object>> withDraw(@Body RequestBody body);

    /**
     * 订单状态更新
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<OrderChangeStatusBean>> orderChangeStatus(@Body RequestBody body);

    /**
     * pdf协议
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<ProcotolBean>> getProtocol(@Body RequestBody body);


    /**
     * 获取更新
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<UpdateBean>> getUpdate(@Body RequestBody body);


    /**
     * 上传通讯信息
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Call<BaseEntity<String>> contactsCall(@Body RequestBody body);

    /**
     * 刷新
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<RefreshBean>> homeRefresh(@Body RequestBody body);

    /**
     * 下载文件
     */
    @GET
    Call<ResponseBody> downFile(@Url String fileUrl);
    /**
     * 芝麻
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<ZhiMaBean>> zhiMaCall(@Body RequestBody body);


    /**
     * 魔蝎
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("qzbk/")
    Observable<BaseEntity<String>> taskIdCall(@Body RequestBody body);
}
