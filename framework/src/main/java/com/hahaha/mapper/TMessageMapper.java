package com.hahaha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.entity.TMessage;
import org.apache.ibatis.annotations.Mapper;


/**
 * (TMessage)表数据库访问层
 *
 * @author hahaha
 * @since 2024-03-16 16:30:11
 */
@Mapper
public interface TMessageMapper extends BaseMapper<TMessage> {

}
