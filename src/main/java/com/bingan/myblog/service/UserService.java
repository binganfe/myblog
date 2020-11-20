package com.bingan.myblog.service;

import com.bingan.myblog.entity.User;

public interface UserService {

    User checkUser(String username, String password);
}
