package com.fernandocejas.android10.restrofit.net;

import android.util.Log;

import com.fernandocejas.android10.restrofit.enity.mapper.AutoValueGsonFactory;
import com.fernandocejas.android10.sample.data.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class initializes retrofit with a default configuration.
 * You can use this class to initialize the different services.
 */

@Singleton
public class RetrofitHelper {

    private final Retrofit retrofit;

    private RestApi restApi;

    @Inject
    RetrofitHelper() {
        retrofit = createRetrofit();
    }

    /**
     * The CityService communicates with the json api of the city provider.
     */
    public RestApi getRestApiService() {
        if (restApi == null) {
            restApi = retrofit.create(RestApi.class);
        }
        return restApi;
    }

    Gson createGson() {
        return new GsonBuilder()
//                .serializeNulls()
                .registerTypeAdapterFactory(AutoValueGsonFactory.create())
                .create();
    }

    private OkHttpClient createOkHttpClient() {
        //todo: logging interceptors
        HttpLoggingInterceptor logging;
        {
            logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new HttpExceptionInterceptor())
                .build();
    }

    private GsonConverterFactory createGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    /**
     * Creates a pre configured Retrofit instance
     */
    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.END_POINT)
                .client(createOkHttpClient())
                .addConverterFactory(createGsonConverterFactory(createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public class HttpExceptionInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            switch (response.code()) {
                case 401://Unauthorized
                    break;
                default:
                    Log.e("HttpException", response.toString());
                    break;
            }
            return response;
        }
    }
}
