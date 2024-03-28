package com.hahaha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.entity.TVideo;
import com.hahaha.entity.dto.VideoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * (TVideo)表数据库访问层
 *
 * @author hahaha
 * @since 2024-03-16 16:30:11
 */
@Mapper
public interface TVideoMapper extends BaseMapper<TVideo> {

    List<VideoDto> getIndexRecommendVideo();

    List<TVideo> selectRecommendVideo(@Param("curIndex") int curIndex, @Param("pageSize") int pageSize, @Param("userId") Long userId);

    @Select("select count(*) from hahaha_security.t_video")
    int selectVideoCount();
}
