package com.hahaha.service;

import com.hahaha.entity.User;

import java.util.Map;

public interface SystemLoginService {
    Map<String,String> login(User user);

    void logout();
}
