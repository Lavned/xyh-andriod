package com.zejor.thirdservice;


/**
 * 回传魔蝎的taskId
 */

public class MoxieTaskIdUtil {

//    private Map<String,String> map;
//
//    public void initMap(){
//        map = new HashMap<>();
//        String userId = (String) SharedPerferenceUtil.getData(App.getInstance(), "userId", "");
//        String tokenId = (String) SharedPerferenceUtil.getData(App.getInstance(), "tokenId", "");
//        String taskId = (String) SharedPerferenceUtil.getData(App.getInstance(), "taskId", "");
//        String type = (String) SharedPerferenceUtil.getData(App.getInstance(), "moxieType", "");
//        String version = CommonValue.VERSION;
//        String softType = CommonValue.SOFTTYPE;
//        String funCode = "100037";
//        String mobile = (String) SharedPerferenceUtil.getData(App.getInstance(), "mobile", "");
//
//        map.put("mobile",mobile);
//
//        map.put("userId",userId);
//        map.put("tokenId",tokenId);
//        map.put("taskId",taskId);
//        map.put("version",version);
//        map.put("softType",softType);
//        map.put("funCode",funCode);
//        map.put("type",type);
//    }
//
//
//    public void returnTaskId(final TaskIdListener taskIdListener){
//        initMap();
//        RequestBody requestBody = ParameterEncryptionUtil.getInstance().getRequestBody(map);
//        Call<BaseEntity<String>> call = RetrofitFactory.getInstance().getRetrofit().create(ApiService.class).taskIdCall(requestBody);
//        call.enqueue(new RetrofitResultCallBack<BaseEntity<String>>() {
//            @Override
//            public void onSuccess(BaseEntity<String> baseEntity) {
//                taskIdListener.returnTaskIdSuccess(baseEntity.getRetData());
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//
//            }
//        });
//    }
//
//
//    public interface TaskIdListener{
//        void returnTaskIdSuccess(String msg);
//    }
}
