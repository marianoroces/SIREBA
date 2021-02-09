package com.marianoroces.sireba.services;

import com.google.gson.JsonObject;
import com.marianoroces.sireba.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/{@uid}")
    Call<User> getUser(@Path("uid") String uid);

    @DELETE("users/{@uid}")
    Call<User> deleteUser(@Path("uid") String uid);

    @POST("users")
    Call<User> saveUser(@Body JsonObject body);
}
