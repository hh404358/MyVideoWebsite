package com.hahaha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.entity.TFocus;
import com.hahaha.entity.User;
import com.hahaha.entity.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (TFocus)表数据库访问层
 *
 * @author hahaha
 * @since 2024-03-16 16:30:11
 */
@Mapper
public interface TFocusMapper extends BaseMapper<TFocus> {
    List<UserInfoVo> getTFocus(Long id);

    List<UserInfoVo> getFan(Long id);


    List<UserInfoVo> getFriends(Long id);
    void cancelfocus(@Param("userId") Long userId, @Param("focusId") Long focusId);
}
