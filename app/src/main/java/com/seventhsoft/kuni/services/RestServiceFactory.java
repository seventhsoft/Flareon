package com.seventhsoft.kuni.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by olimpia on 3/04/17.
 */

public class RestServiceFactory {
    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     *
     * @param clazz    Java interface of the retrofit service
     * @param endPoint REST endpoint url
     * @return retrofit service with defined endpoint
     */

    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint, final String authToken ) {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request newRequest = request.newBuilder()
                        .header("Authorization", authToken)
                        .build();
                Log.i(TAG, "OSE| AuthToken: " + authToken);
                return chain.proceed(newRequest);
            }
        }).addInterceptor(interceptor);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = httpClient.build();
        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(endPoint)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }
}
