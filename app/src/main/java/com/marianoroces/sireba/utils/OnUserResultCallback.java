package com.marianoroces.sireba.utils;

import com.marianoroces.sireba.model.User;

import java.util.List;

public interface OnUserResultCallback {
    void onUserResult(User user);
    void onUserResult(List<User> userList);
}
