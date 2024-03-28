package com.hahaha.service;

import com.hahaha.entity.User;
import com.hahaha.entity.vo.WebsiteUserLoginVo;

public interface WebsiteLoginService {
    WebsiteUserLoginVo login(User user);

    void logout();

    void register(User user);
}
