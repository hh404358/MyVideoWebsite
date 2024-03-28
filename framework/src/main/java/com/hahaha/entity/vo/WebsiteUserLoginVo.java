package com.hahaha.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteUserLoginVo {

    private String token;
    private UserInfoVo userInfo;
}