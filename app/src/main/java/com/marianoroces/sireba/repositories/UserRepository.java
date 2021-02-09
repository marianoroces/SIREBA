package com.marianoroces.sireba.repositories;

import android.util.Log;

import com.google.gson.JsonObject;
import com.marianoroces.sireba.model.User;
import com.marianoroces.sireba.services.MyRetrofit;
import com.marianoroces.sireba.services.UserService;
import com.marianoroces.sireba.utils.OnUserResultCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository implements OnUserResultCallback {

    private User user;
    private UserService userService;
    private OnUserResultCallback userCallback;

    public UserRepository(OnUserResultCallback context){
        userService = MyRetrofit.getInstance().createUserService();
        userCallback = context;
    }

    public void getUser(String uid){
        Call<User> callUser = userService.getUser(uid);

        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("USER", "Buscar usuario exitoso");
                user = response.body();
                userCallback.onUserResult(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("USER", "Buscar usuario fallido");
            }
        });
    }

    public void save(User user){
        JsonObject aux = new JsonObject();

        aux.addProperty("uid", user.getUid());
        aux.addProperty("email", user.getEmail());
        aux.addProperty("password", user.getPassword());

        Log.d("USER", aux.toString());

        Call<User> callUser = userService.saveUser(aux);

        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("USER", "Usuario guardado");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("USER", "Usuario no guardado");
            }
        });
    }

    @Override
    public void onUserResult(User user) {
        userCallback.onUserResult(user);
    }
}
