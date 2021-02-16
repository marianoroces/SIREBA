package com.marianoroces.sireba.services;

import com.google.gson.JsonObject;
import com.marianoroces.sireba.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @GET("users")
    Call<List<User>> getSpecificUser(@Query("uid") String uid);

    @GET("users/{id}")
    Call<User> getUser(@Path("id") int id);

    @DELETE("users/{@id}")
    Call<User> deleteUser(@Path("uid") int id);

    @POST("users")
    Call<User> saveUser(@Body JsonObject body);
}
