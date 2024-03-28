package com.hahaha.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.constants.SystemConstants;
import com.hahaha.entity.*;
import com.hahaha.entity.dto.CommentedStarDto;
import com.hahaha.entity.dto.VideoDto;
import com.hahaha.entity.vo.HotTVideoVo;
import com.hahaha.entity.vo.PageVo;
import com.hahaha.entity.vo.TVideoListVo;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.SystemException;
import com.hahaha.mapper.TCommentedstarMapper;
import com.hahaha.mapper.TMessageMapper;
import com.hahaha.mapper.TVideoMapper;
import com.hahaha.mapper.UserMapper;
import com.hahaha.service.TStateService;
import com.hahaha.service.TVideoService;
import com.hahaha.service.TVideotypeService;
import com.hahaha.utils.BeanCopyUtils;
import com.hahaha.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * (TVideo)表服务实现类
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
@Service("tVideoService")
public class TVideoServiceImpl extends ServiceImpl<TVideoMapper, TVideo> implements TVideoService {
    @Autowired
    private TVideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TMessageMapper msgMapper;


    @Override
    public List<TVideo> getVideoListByUserId(Long userId) {
        LambdaQueryWrapper<TVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideo::getCreateUser,userId);
        return list(queryWrapper);
    }

    @Override
    public List<TVideo> getVideoByVideoTypeId(Long videoTypeId) {
        LambdaQueryWrapper<TVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideo::getVideoTypeId,videoTypeId);
        return list(queryWrapper);
    }

    @Autowired
    private RedisCache redisCache;
    @Override
    public void updateViewCount(Long id) {
        redisCache.incrementCacheMapValue("video:viewCount",id.toString(),1);
    }


    @Override
    public List<VideoDto> getIndexRecommendVideo() {
        return videoMapper.getIndexRecommendVideo();
    }

    @Autowired
    private TVideoService videoService;

    @Autowired
    private TVideotypeService videotypeService;

    /**
     * 从redis中获取浏览量（分装成一个方法）
     */
    public void getViewCount(){
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("video:viewCount");
        List<TVideo> videos = viewCountMap.entrySet()
                .stream()
                .map(entry -> new TVideo(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        videoService.updateBatchById(videos);
    }

    @Override
    public List<HotTVideoVo> hotVideoList() {
        getViewCount();

        LambdaQueryWrapper<TVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideo::getVideoStateId, SystemConstants.Video_STATUS_NORMAL);
        //按照浏览量进行排序。也就是根据ViewCount字段降序排序
        queryWrapper.orderByDesc(TVideo::getViewNum);
        //最多只能查询出来10条消息。当前显示第一页的数据，每页显示10条数据
        Page<TVideo> page = new Page<>(SystemConstants.Video_STATUS_CURRENT,SystemConstants.Video_STATUS_SIZE);
        page(page,queryWrapper);
        //获取最终的查询结果
        List<TVideo> videos = page.getRecords();

        List<HotTVideoVo> hotTVideoVoList = new ArrayList<>();
        for(TVideo video:videos){
            HotTVideoVo vo = new HotTVideoVo();
            //使用spring提供的BeanUtils类，来实现把源数据拷贝给目标数据
            BeanUtils.copyProperties(video,vo);
            hotTVideoVoList.add(vo);
        }

        return hotTVideoVoList;
    }

    @Override
    public PageVo undervideoList(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<TVideo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq( TVideo::getVideoStateId, SystemConstants.Video_STATUS_UNDER);

        //分页查询
        Page<TVideo> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<TVideoListVo> vo = BeanCopyUtils.copyBeanList(page.getRecords(), TVideoListVo.class);
        //把查询结果和文章总数封装在PageVo
        return new PageVo(vo,page.getTotal());
    }

    @Override
    public PageVo videoList(Integer pageNum, Integer pageSize, Long typeId) {
        getViewCount();

        LambdaQueryWrapper<TVideo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(typeId) && typeId > 0, TVideo::getVideoTypeId, typeId);

        //只查询上架视频
        lambdaQueryWrapper.eq(TVideo::getVideoStateId, SystemConstants.Video_STATUS_NORMAL);

        //分页查询
        Page<TVideo> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        //查询'分类名称'
        List<TVideo> TVideos = page.getRecords();
        TVideos.stream()
                .map(new Function<TVideo, TVideo>() {
                    @Override
                    public TVideo apply(TVideo video) {
                        //类型
                        TVideotype videotype = videotypeService.getById(video.getVideoTypeId());
                        String name = videotype.getTypeName();
                        video.setTypeName(name);
                        //用户名
                        User user = userMapper.selectById(video.getCreateUser());
                        video.setUserName(user.getUserName());
                        return video;
                    }
                })
                .collect(Collectors.toList());
        List<TVideoListVo> vo = BeanCopyUtils.copyBeanList(page.getRecords(), TVideoListVo.class);
        //把查询结果和文章总数封装在PageVo
        return new PageVo(vo,page.getTotal());
    }


    @Autowired
    private TCommentedstarMapper commentedstarMapper;
    @Override
    public Integer addComment(@RequestBody CommentedStarDto dto) {
        TVideo video = videoMapper.selectById(dto.getVideoId());
        User user = userMapper.selectById(dto.getUserId());
        LambdaQueryWrapper<TCommentedstar> commentedstarLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentedstarLambdaQueryWrapper.eq(TCommentedstar::getVideoId,dto.getVideoId());
        commentedstarLambdaQueryWrapper.eq(TCommentedstar::getUserId,dto.getUserId());
        TCommentedstar commentedStar = commentedstarMapper.selectOne(commentedstarLambdaQueryWrapper);
        TMessage msg = new TMessage();
        if(commentedStar != null) {
            throw new SystemException(AppHttpCodeEnum.HAVA_STAR);
        }
        if(user != null && video != null) {
            commentedStar = BeanCopyUtils.copyBean(dto, TCommentedstar.class);
            commentedstarMapper.insert(commentedStar);
            msg.setMsgTitle("点评提醒");
            msg.setMsgContext("你好！你的主题为《" + video.getVideoTitle() + "》的视频获得用户【" + user.getUserName() + "】的" + dto.getStarNum() + "星点评");
            msg.setMsgReceiveUserId(dto.getUserId());
            msgMapper.insert(msg);
            return SystemConstants.STAR_SUCCESS;
        }
        throw new SystemException(AppHttpCodeEnum.NO_SELF_STAR);
    }

    @Override
    public List<TVideo> getRecommendVideo(int curPage, int pageSize, Long userId) {
        int videoCount = videoMapper.selectVideoCount();
        int curIndex = (curPage - 1) * pageSize;
        if (curPage > 0 && pageSize > 0 && curIndex <= videoCount) {
            List<TVideo> videoList = videoMapper.selectRecommendVideo(curIndex, pageSize, userId);
            if (videoList != null) {
                return videoList;
            }
        }
        return null;
    }

    @Override
    public PageVo selectTVideoPage(TVideo video, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<TVideo> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(video.getVideoTitle()),TVideo::getVideoTitle, video.getVideoTitle());
        queryWrapper.like(StringUtils.hasText(video.getVideoInfo()),TVideo::getVideoInfo, video.getVideoInfo());
        queryWrapper.eq(Objects.nonNull(video.getVideoTypeId())&&video.getVideoTypeId()>0,TVideo::getVideoTypeId, video.getVideoTypeId());
        queryWrapper.eq(Objects.nonNull(video.getVideoStateId())&&video.getVideoStateId()>0,TVideo::getVideoStateId,video.getVideoStateId());

        Page<TVideo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<TVideo> videos = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(videos);
        return pageVo;
    }



    /**
     * 视频审核通过
     * @param id
     * @return
     */
    @Override
    public int restoreVideo(Long id) {
        TMessage msg = new TMessage();
        TVideo video = videoMapper.selectById(id);
        msg.setMsgTitle("系统提醒");
        msg.setMsgContext("你好！你的主题为《" + video.getVideoTitle() + "》的视频审核通过");
        msg.setMsgtypeId(Long.valueOf(SystemConstants.SYSTEMMSG));
        msg.setMsgStateId(Long.valueOf(SystemConstants.UNREAD));
        msg.setMsgReceiveUserId(video.getCreateUser());
        msgMapper.insert(msg);
        video.setVideoStateId(SystemConstants.Video_STATUS_NORMAL);
        return videoMapper.updateById(video);
    }

    /**
     * 视频审核失败
     * @param id
     * @return
     */
    @Override
    public int deleteVideo(Long id) {
        TMessage msg = new TMessage();
        TVideo video = videoMapper.selectById(id);
        msg.setMsgTitle("系统提醒");
        msg.setMsgContext("你好！你的主题为《" + video.getVideoTitle() + "》的视频审核失败");
        msg.setMsgtypeId(Long.valueOf(SystemConstants.SYSTEMMSG));
        msg.setMsgStateId(Long.valueOf(SystemConstants.UNREAD));
        msg.setMsgReceiveUserId(video.getCreateUser());
        msgMapper.insert(msg);
        return videoMapper.deleteById(id);
    }

    /**
     * 下架
     * @param id
     * @return
     */
    @Override
    public int rdeleteVideo(Long id) {
        TMessage msg = new TMessage();
        TVideo video = videoMapper.selectById(id);
        msg.setMsgTitle("系统提醒");
        msg.setMsgContext("你好！你的主题为《" + video.getVideoTitle() + "》的视频因违反规定已被下架");
        msg.setMsgtypeId(Long.valueOf(SystemConstants.SYSTEMMSG));
        msg.setMsgStateId(Long.valueOf(SystemConstants.UNREAD));
        msg.setMsgReceiveUserId(video.getCreateUser());
        msgMapper.insert(msg);
        video.setVideoStateId(Long.valueOf(SystemConstants.Video_STATUS_UNDER));
        return videoMapper.updateById(video);
    }

}
