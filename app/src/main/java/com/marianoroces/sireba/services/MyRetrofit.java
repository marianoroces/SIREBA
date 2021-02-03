package com.marianoroces.sireba.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private static final MyRetrofit retrofitInstance = new MyRetrofit();

    private Retrofit retrofit;

    public static MyRetrofit getInstance() {
        return retrofitInstance;
    }

    private MyRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public ReportService createReportService(){
        return retrofit.create(ReportService.class);
    }

    public CategoryService createCategoryService(){
        return retrofit.create(CategoryService.class);
    }

}
