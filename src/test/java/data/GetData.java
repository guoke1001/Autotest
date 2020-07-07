package data;

import apiService.ApiService;
import bean.YldBean;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.reactivex.schedulers.Schedulers;
import net.HttpUtil;
import net.MyObserver;
import util.DataUtils;

import java.util.HashMap;
import java.util.Map;

public class GetData {


    private static HttpUtil httpUtil= HttpUtil.getInstance();
    private static DataUtils dataUtils=new DataUtils();
    private static ApiService apiService=httpUtil.creat(ApiService.class);




    public  Map<String,String> getChapters(){
        final Map<String,String> map=new HashMap<String, String>();
        final Map<String,String> chapterinfo=new HashMap<String, String>();
        map.put("aid","com.cheetahplay.yinglingdian");
        map.put("ver","1.6.2");
        map.put("os","android");
        map.put("os_ver","29");
        map.put("ch","111111");
        map.put("version","16200");
        apiService.getChapters(map)
                .unsubscribeOn(Schedulers.io())
                .subscribe(new MyObserver<YldBean>(){
                    @Override
                    public void onNext(YldBean object) {

                        chapterinfo.put("code",String.valueOf(object.getCode()));
                        chapterinfo.put("msg",object.getMsg());
                        chapterinfo.put("data",object.getData());
                      JSONArray jsonObject=JSONObject.parseArray(object.getData());
                      for (int i=0;i<jsonObject.size();i++){
                          JSONObject jsonObject1=jsonObject.getJSONObject(i);
                          System.out.println(jsonObject1.getString("name"));
                      }

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("33333333333333333333"+throwable.toString());
                    }
                });
        return chapterinfo;

    }

    public Map<String,String>  channelOption(){
        final Map<String,String> channelOp=new HashMap<String, String>();
        Map<String,String> map=new HashMap<String, String>();
        map.put("aid","com.cheetahplay.yinglingdian");
        map.put("ver","1.6.2");
        map.put("os","android");
        map.put("os_ver","29");
        map.put("ch","111111");
        map.put("channel_num","111111");
        map.put("version","1.6.2");
        apiService.channelInfo(map)
                .unsubscribeOn(Schedulers.io())
                .subscribe(new MyObserver<YldBean>(){
                    @Override
                    public void onNext(YldBean object) {
                        JSONObject jsonObject=JSONObject.parseObject(object.getData());
                        channelOp.put("channel_num",jsonObject.getString("channel_num"));
                        channelOp.put("version",jsonObject.getString("version"));
                        channelOp.put("status",jsonObject.getString("status"));

                    }
                });
        return channelOp;

    }
    public static void  getConfig(){
        Map<String,String> map=new HashMap<String, String>();
        map.put("aid","com.cheetahplay.yinglingdian");
        map.put("ver","1.6.3");
        map.put("os","android");
        map.put("os_ver","29");
        map.put("ch","111111");
        apiService.getConfigs(map)
                .unsubscribeOn(Schedulers.io())
                .subscribe(new MyObserver<YldBean>(){
                    @Override
                    public void onNext(YldBean object) {
                        System.out.println(object.getCode());
                        JSONArray jsonElements=JSONObject.parseArray(object.getData());
                        for (int i=0;i<jsonElements.size();i++){
                            JSONObject jsonObject=JSONObject.parseObject(jsonElements.getString(i));
                            System.out.println(jsonObject.getString("name")+" : "+jsonObject.getString("url"));
                        }


                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println(throwable.toString());
                    }
                });


    }
    public static void updateConfig(){
        String config="[\n" +
                "    {\n" +
                "        \"name\": \"log\",\n" +
                "        \"config_md5\": \"c4ca4238a0b923820dcc509a6f7588\",\n" +
                "        \"path\": \"/chapter/v1/log.json?ver=1\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"hash\",\n" +
                "        \"config_md5\": \"c81e728d9d4c2f636f067f89cc14862c7\",\n" +
                "        \"path\": \"/chapter/v1/hash.json?ver=1\"\n" +
                "    }\n" +
                "]";
        Map<String,String> map=new HashMap<String, String>();
        map.put("aid","com.cheetahplay.yinglingdian");
        map.put("ver","1.6.3");
        map.put("os","android");
        map.put("os_ver","29");
        map.put("ch","111111");
        map.put("config",config);
        apiService.updateConfig(map)
                .unsubscribeOn(Schedulers.io())
                .subscribe(new MyObserver<YldBean>(){
                    @Override
                    public void onNext(YldBean object) {
                        System.out.println(object.getCode()+"  :  "+object.getMsg()+"  :  "+object.getData());

                    }

                });
        System.out.println("-------------------config------------------------------------");

    }

    public static void main(String[] args) {
//        updateConfig();
//        new GetData().getChapters();

    }


}
