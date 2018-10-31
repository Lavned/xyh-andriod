package com.zejor.thirdservice;


/**
 * 获取芝麻信用的跳转地址
 */

public class ZhiMaUtil {

//    public Map<String, String> map;
//
//
//    public void getZhiMaUrl(final ZhiMaListener zhiMaListener) {
//        initMap();
//        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(map);
//        Call<BaseEntity<ZhiMaBean>> call = RetrofitFactory.getInstance().getRetrofit().create(ApiService.class).zhiMaCall(requestBody);
//        call.enqueue(new RetrofitResultCallBack<BaseEntity<ZhiMaBean>>() {
//            @Override
//            public void onSuccess(BaseEntity<ZhiMaBean> baseEntity) {
//                zhiMaListener.postZhiMaUrl(baseEntity.getRetData().getUrl());
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//
//            }
//        });
//
//    }
//
//    private void initMap() {
//        map = new HashMap<>();
//        String userId = (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", "");
//        String h5Type = CommonValue.H5_TYPE;
//        String version = CommonValue.VERSION;
//        String softType = CommonValue.SOFTTYPE;
//        String funCode = "100029";
//        String mobile = (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", "");
//        String tokenId = (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", "");
//
//        map.put("mobile", mobile);
//        map.put("tokenId", tokenId);
//        map.put("userId", userId);
//        map.put("h5Type", h5Type);
//        map.put("version", version);
//        map.put("softType", softType);
//        map.put("funCode", funCode);
//
//    }
//
//
//    public interface ZhiMaListener {
//        void postZhiMaUrl(String url);
//    }
}
