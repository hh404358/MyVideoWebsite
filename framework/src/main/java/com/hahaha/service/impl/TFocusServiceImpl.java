package com.hahaha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.entity.TFocus;
import com.hahaha.entity.User;
import com.hahaha.entity.vo.UserInfoVo;
import com.hahaha.mapper.TFocusMapper;
import com.hahaha.service.TFocusService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TFocus)表服务实现类
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
@Service("tFocusService")
public class TFocusServiceImpl extends ServiceImpl<TFocusMapper, TFocus> implements TFocusService {

    @Autowired
    private TFocusMapper focusMapper;

    @Override
    public List<UserInfoVo> getFriends(Long id) {
        return focusMapper.getFriends(id);
    }

    @Override
    public List<UserInfoVo> getTFocus(Long id) {
        return focusMapper.getTFocus(id);
    }

    @Override
    public void addTFocus(Long id, Long userId) {
        focusMapper.insert(new TFocus(null,id,userId));
    }

    @Override
    public void cancelTFocus(@Param("userId") Long userId, @Param("focusId") Long focusId) {
        focusMapper.cancelfocus(userId, focusId);
    }

    @Override
    public List<UserInfoVo> getFan(Long id) {
        return focusMapper.getFan(id);
    }
}
