package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.TFocus;
import com.hahaha.entity.User;
import com.hahaha.entity.vo.UserInfoVo;

import java.util.List;


/**
 * (TFocus)表服务接口
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
public interface TFocusService extends IService<TFocus> {
    List<UserInfoVo> getTFocus(Long id);

    List<UserInfoVo> getFan(Long id);

    List<UserInfoVo> getFriends(Long id);

    void addTFocus(Long id, Long userId);

    void cancelTFocus(Long id, Long userId);
}
