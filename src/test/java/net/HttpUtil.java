package net;


import config.BaseDate;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

public class HttpUtil {
    private static HttpUtil httpUtil;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private HttpUtil(){

    }
    public static HttpUtil getInstance(){
        if(httpUtil==null){
            synchronized (HttpUtil.class){
                if (httpUtil==null){
                    httpUtil=new HttpUtil();
                }
            }
        }
        return httpUtil;
    }
    public Retrofit getRetrofit(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BaseDate.API_DEV)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(new Retrofit2ConverterFactory())
                .client(genericClient())
                .build();
        return retrofit;
    }
    private static OkHttpClient genericClient() {
        okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
//                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {//添加拦截器，可进行其他相关操作
                        Request request=chain.request();
                        request=request
                                .newBuilder()
                                .addHeader("Content-type","application/x-www-form-urlencoded")
//                                .addHeader("Accept-Encoding", "gzip")
                                .addHeader("Connection","keep-alive")
                                .build();
                        return chain.proceed(request);
                    }
                })
                //okHttpClient log日志打印
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return okHttpClient;
    }


    public <T> T creat(Class<T> clazz){

        return getRetrofit().create(clazz);
    }

    public  RequestBody requestBody(String  str){

        RequestBody  requestBody=RequestBody.create(MediaType.parse("application/json"), str);

        return requestBody;

    }
}
