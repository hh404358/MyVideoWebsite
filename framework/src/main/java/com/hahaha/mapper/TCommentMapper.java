package com.hahaha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.entity.TComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论表(TComment)表数据库访问层
 *
 * @author hahaha
 * @since 2024-03-20 20:17:24
 */
@Mapper
public interface TCommentMapper extends BaseMapper<TComment> {

}
