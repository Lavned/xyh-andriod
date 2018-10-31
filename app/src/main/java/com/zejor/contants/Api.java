package com.zejor.contants;

import com.zejor.base.BaseEntity;
import com.zejor.bean.AdvertisementBean;
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


public class Api {

    public static Api sInstance;
    private ApiService mService;

    public Api(ApiService apiService) {
        this.mService = apiService;
    }

    public static Api getInstance(ApiService apiService) {
        if (sInstance == null)
            sInstance = new Api(apiService);
        return sInstance;
    }

    public Observable<BaseEntity<LoginDataBean>> userLogin(RequestBody body) {
        return mService.userLogin(body);
    }

    public Observable<BaseEntity<Object>> sendCode(RequestBody body) {
        return mService.sendCode(body);
    }

    public Observable<BaseEntity<List<AdvertisementBean>>> advertisementCall(RequestBody body) {
        return mService.advertisementCall(body);
    }

    public Observable<BaseEntity<PhonePicBean>> phonePic(RequestBody body) {
        return mService.phonePic(body);
    }

    public Observable<BaseEntity<SellCompleteBean>> sellComplete(RequestBody body) {
        return mService.sellComplete(body);
    }

    public Observable<BaseEntity<List<OrderBean>>> getOrder(RequestBody body) {
        return mService.getOrder(body);
    }

    public Observable<BaseEntity<Object>> expressOrder(RequestBody body) {
        return mService.expressOrder(body);
    }

    public Observable<BaseEntity<Object>> cancelOrder(RequestBody body) {
        return mService.cancelOrder(body);
    }

    public Observable<BaseEntity<RepaymentMoneyBean>> repaymentMoney(RequestBody body) {
        return mService.repaymentMoney(body);
    }

    public Observable<BaseEntity<RepaymentPayBean>> payMode(RequestBody body) {
        return mService.payMode(body);
    }

    public Observable<BaseEntity<PayBean>> toPay(RequestBody body) {
        return mService.toPay(body);
    }

    public Observable<BaseEntity<FaceBean>> faceCall(RequestBody body) {
        return mService.faceCall(body);
    }

    public Observable<BaseEntity<CommitFaceInfoBean>> faceInfoCall(RequestBody body) {
        return mService.faceInfoCall(body);
    }

    public Observable<BaseEntity<OrderDetailBean>> orderDetail(RequestBody body) {
        return mService.orderDetail(body);
    }

    public Observable<BaseEntity<Object>> savePhoneMessage(RequestBody body) {
        return mService.savePhoneMessage(body);
    }

    public Observable<BaseEntity<MobilePriceBean>> mobilePrice(RequestBody body) {
        return mService.mobilePrice(body);
    }

    public Observable<BaseEntity<CommitInfoBean>> commitCall(RequestBody body) {
        return mService.commitCall(body);
    }

    public Observable<BaseEntity<List<AuthenticationBean>>> authenticationCall(RequestBody body) {
        return mService.authenticationCall(body);
    }

    public Observable<BaseEntity<Object>> commitOrder(RequestBody body) {
        return mService.commitOrder(body);
    }

    public Observable<BaseEntity<List<BankBean>>> getBankList(RequestBody body) {
        return mService.getBankList(body);
    }

    public Observable<BaseEntity<BankAscriptionBean>> checkCard(RequestBody body) {
        return mService.checkCard(body);
    }

    public Observable<BaseEntity<Object>> addBank(RequestBody body) {
        return mService.addBank(body);
    }

    public Observable<BaseEntity<SupportBankBean>> supportBank(RequestBody body) {
        return mService.supportBank(body);
    }

    public Observable<BaseEntity<CreditAmountBean>> getCreditAmount(RequestBody body) {
        return mService.getCreditAmount(body);
    }

    public Observable<BaseEntity<Object>> bankCode(RequestBody body) {
        return mService.bankCode(body);
    }

    public Observable<BaseEntity<Object>> withDraw(RequestBody body) {
        return mService.withDraw(body);
    }

    public Observable<BaseEntity<OrderChangeStatusBean>> orderChangeStatus(RequestBody body) {
        return mService.orderChangeStatus(body);
    }

    public Observable<BaseEntity<ProcotolBean>> getProtocol(RequestBody body) {
        return mService.getProtocol(body);
    }

    public Observable<BaseEntity<ZhiMaBean>> zhiMaCall(RequestBody body) {
        return mService.zhiMaCall(body);
    }

    public Observable<BaseEntity<String>> taskIdCall(RequestBody body) {
        return mService.taskIdCall(body);
    }

    public Observable<BaseEntity<UpdateBean>> getUpdate(RequestBody body) {
        return mService.getUpdate(body);
    }

    public Observable<BaseEntity<RefreshBean>> homeRefresh(RequestBody body) {
        return mService.homeRefresh(body);
    }

    public Observable<BaseEntity<BannerBean>> bannerCall(RequestBody body) {
        return mService.bannerCall(body);
    }

}
