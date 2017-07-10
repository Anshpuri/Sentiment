package com.example.android.project4.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amandhapola on 10/07/17.
 */

public class ApiService {
    private static final ApiService ourInstance = new ApiService();
    public static final String BASE_URL="https://api.cognitive.microsoft.com/bing/v5.0/news/";
    private Retrofit retrofit;
    private NewsService newsService;
    private OkHttpClient.Builder builder;


    public Retrofit getRetrofit() {
        return retrofit;
    }

    public NewsService getNewsService() {
        return newsService;
    }

    public static ApiService getInstance() {
        return ourInstance;
    }

    private ApiService() {


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Ocp-Apim-Subscription-Key", "c0e942cdfc544dcabf470e5dd7854e73")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });


        OkHttpClient client = httpClient.build();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        newsService=retrofit.create(NewsService.class);

    }
//    public class AddHeaderInterceptor implements Interceptor {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request.Builder builder = chain.request().newBuilder();
//            builder.addHeader("Ocp-Apim-Subscription-Key", "c0e942cdfc544dcabf470e5dd7854e73");
//            return chain.proceed(builder.build());
//        }
//    }
}
