package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.TVideo;
import com.hahaha.entity.dto.CommentedStarDto;
import com.hahaha.entity.dto.VideoDto;
import com.hahaha.entity.vo.HotTVideoVo;
import com.hahaha.entity.vo.PageVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * (TVideo)表服务接口
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
public interface TVideoService extends IService<TVideo> {

    List<TVideo> getVideoListByUserId(Long userId);

    List<TVideo> getVideoByVideoTypeId(Long videoTypeId);


    void updateViewCount(Long id);


    PageVo undervideoList(Integer pageNum, Integer pageSize);

    Integer addComment(@RequestBody CommentedStarDto dto);

    List<TVideo> getRecommendVideo(int curPage, int pageSize, Long userId);


    List<VideoDto> getIndexRecommendVideo();

    PageVo videoList(Integer pageNum, Integer pageSize, Long videoTypeId);


    List<HotTVideoVo> hotVideoList();


    PageVo selectTVideoPage(TVideo video, Integer pageNum, Integer pageSize);

    int restoreVideo(Long id);

    int deleteVideo(Long id);

    int rdeleteVideo(Long id);
}
