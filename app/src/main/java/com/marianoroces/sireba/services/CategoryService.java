package com.marianoroces.sireba.services;

import com.google.gson.JsonObject;
import com.marianoroces.sireba.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("categories/{id}")
    Call<Category> getCategory(@Path("id") int id);

    @GET("categories")
    Call<List<Category>> getAllCategories();

    @DELETE("categories/{id}")
    Call<Category> deleteCategory(@Path("id") int id);

    @PUT("categories")
    Call<Category> saveCategory(@Body JsonObject body);

}
