package com.example.android2.FINAL.PROJECT.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.example.android2.FINAL.PROJECT.BuildConfig;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataSource {

    private DataInterface api;

    public DataSource(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieInfo.BASE_URL)
                .client(builder())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(DataInterface.class);
    }

    private OkHttpClient builder() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();

        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key", MovieInfo.API_KEY)
                        .addQueryParameter("language", MovieInfo.LANG_EN)
                        .build();

                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        });

        return okHttpClient.build();
    }

    public void getPopularMovies(int page, Callback callback) {
        api.popularMovies(page).enqueue(callback);
    }

    public void getMovieDetail(int movieId, Callback callback) {
        api.movieDetail(movieId).enqueue(callback);
    }
}

