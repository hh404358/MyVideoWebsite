package com.hahaha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author hahaha
 * @since 2024-03-14 21:03:33
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Long aId);



    int insertSelective(User record);

    User selectByPrimaryKey(Long aId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    List<String> getVideoData();

    List<Integer> getData();
}
