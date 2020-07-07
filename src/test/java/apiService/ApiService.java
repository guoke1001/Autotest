package apiService;

import bean.YldBean;
import io.reactivex.Observable;
import jdk.nashorn.internal.objects.annotations.Getter;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import java.util.Map;


public interface ApiService {

    @GET("chapters")
    Observable<YldBean> getChapters(@QueryMap Map<String, String> map);

    @GET("entry/control/info")
    Observable<YldBean> channelInfo(@QueryMap Map<String, String> map);

    @GET("configs")
    Observable<YldBean> getConfigs(@QueryMap Map<String,String> map);

    @POST("config/update")
    Observable<YldBean> updateConfig(@QueryMap Map<String,String> map);




}
