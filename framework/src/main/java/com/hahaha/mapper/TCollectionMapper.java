package com.hahaha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.entity.TCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * (TCollection)表数据库访问层
 *
 * @author hahaha
 * @since 2024-03-16 16:30:11
 */
@Mapper
public interface TCollectionMapper extends BaseMapper<TCollection> {

    @Select("select * from hahaha_security.t_collection where user_id=#{userId}")
    List<TCollection> getAll(Long userId);
}
