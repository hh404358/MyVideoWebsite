package com.hahaha.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hahaha.annotation.mySystemlog;
import com.hahaha.entity.TCollection;
import com.hahaha.entity.TVideo;
import com.hahaha.entity.dto.CommentedStarDto;
import com.hahaha.entity.dto.VideoDto;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.result.Result;
import com.hahaha.service.TCollectionService;
import com.hahaha.service.TVideoService;
import com.hahaha.service.UserService;
import com.hahaha.utils.AliOSSUtils;
import com.hahaha.utils.BeanCopyUtils;
import com.hahaha.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.hahaha.utils.RedisCache;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 视频管理
 */
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private TVideoService videoService;

    /**
     * 根据视频类型查询视频
     * @param videoTypeId
     * @return
     */
    @RequestMapping("/getVideoByType")
    public Result getVideoByType( String videoTypeId) {
        return Result.okResult(videoService.getVideoByVideoTypeId(Long.valueOf(videoTypeId)));
    }

    /**
     * 根据视频标题查询视频
     * @param title
     * @return
     */
    @GetMapping("/getVideoByTitle")
    public Result getVideoByTitle(String title) {
        LambdaQueryWrapper<TVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(TVideo::getVideoTitle,title);
        TVideo video = videoService.getOne(wrapper);
        return Result.okResult(video);
    }
    /**
     * 获取指定用户发布的视频
     * @param userId
     * @return
     */
    @GetMapping("/getVideoListByUserId")
    public Result getVideoListByUserId(String userId) {
        List<TVideo> videoList = videoService.getVideoListByUserId(Long.valueOf(userId));
        if (videoList != null) {
            return Result.okResult(videoList);
        }
        return Result.errorResult(AppHttpCodeEnum.USER_NO_VIDEO);
    }
    @GetMapping("/hotVideoList")
    @mySystemlog(xxbusinessName = "查询热门视频")
    public Result hotTVideoList(){
        return Result.okResult(videoService.hotVideoList());
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param videoTypeId
     * @return
     */
    @GetMapping("/videoList")
    @mySystemlog(xxbusinessName = "分页查询")
    public Result videoList(Integer pageNum,Integer pageSize,Long videoTypeId){
        return Result.okResult(videoService.videoList(pageNum,pageSize,videoTypeId));
    }

    /**
     * 增加浏览次数
     * @param videoId
     * @return
     */
    @PutMapping("/updateViewCount/{videoId}")
    @mySystemlog(xxbusinessName = "增加浏览次数")
    public Result updateViewCount(@PathVariable("videoId") Long videoId){
        videoService.updateViewCount(videoId);
        return Result.okResult();
    }

    @Autowired
    private UserService userService;
    @Autowired
    private TCollectionService collectionService;
    /**
     * 获取视频详情
     */
    @GetMapping("/{videoId}")
    public Result getVideo(@PathVariable("videoId") Long videoId) {
        TVideo video = videoService.getById(videoId);
        video.setUserName(userService.getById(video.getCreateUser()).getUserName());
        LambdaQueryWrapper<TCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TCollection::getVideoId,videoId);
        video.setCollectionNum((long) collectionService.count(queryWrapper));
        if (video != null) {
            return Result.okResult(video);
        }
        else {
            return Result.errorResult(AppHttpCodeEnum.VIDEO_NOT_EXIST);
        }
    }

    @Autowired
    private AliOSSUtils aliOSSUtils;

    /**
     * 上传视频图片
     * @param file
     * @return
     */
    @PostMapping("/uploadImage")
    public Result uploadImage(MultipartFile file/*, @PathVariable Long videoId*/) throws IOException {
        String url = aliOSSUtils.uploadImgae(file);
//        TVideo video = videoService.getById(videoId);
//        video.setThunmbnailUrl(url);
//        videoService.updateById(video);
        return Result.okResult(url);
    }


    /**
     * 添加视频
     * @return
     */
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody VideoDto videoDto) {
        Long userId = SecurityUtils.getUserId();
        TVideo video = BeanCopyUtils.copyBean(videoDto,TVideo.class);
        video.setVideoTypeId((int) videoDto.getVideoTypeId().longValue());;
        videoService.save(video);
        return Result.okResult();
    }

    /**
     * 上传视频
     * @param file
     * @return
     */
    @PostMapping("/uploadVideo")
    public Result uploadVideo(MultipartFile file) throws IOException {
        String url = aliOSSUtils.uploadVideo(file);
        return Result.okResult(url);
    }

    /**
     * 获得分页的推荐视频
     * @return
     */
    @GetMapping("/getRecommendVideo")
    public Result getRecommendVideo() {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        return Result.okResult(videoService.getRecommendVideo(1, 3, userId));
    }
    /**
     * 获得top前3的视频
     * @return
     */
    @GetMapping("/getIndexRecommendVideo")
    public Result getIndexRecommendVideo() {
        return Result.okResult(videoService.getIndexRecommendVideo());
    }



    @Autowired
    private RedisCache redisCache;
    /**
     * 视频播放界面
     * @return
     */
    @RequestMapping("/videoPlay")
    public Result videoPlay(String videoId) throws IOException {
        if (videoId != null) {
            // 增加观看次数
            videoService.updateViewCount(Long.valueOf(videoId));
            TVideo video =videoService.getBaseMapper().selectById(Long.valueOf(videoId));

            if (video != null) {
                redisCache.setCacheObject("curVideo",video);
            }
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.VIDEO_PLAY_ERRPR);


    }
    /**
     * 点赞
     * @return
     */
    @PutMapping("/thumbsUp")
    public Result thumbsUp(Long videoId) {
        try{
            Long userId = SecurityUtils.getUserId();
        }catch (Exception e){
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        TVideo video = videoService.getBaseMapper().selectById(videoId);
        video.setThumbupNum(video.getThumbupNum() + 1);
        videoService.updateById(video);
        return Result.okResult();
    }

    /**
     * 取消点赞
     * @return
     */
    @PutMapping("/cancelThumbsUp")
    public Result cancelThumbsUp(String videoId) {
        try{
            Long userId = SecurityUtils.getUserId();
        }catch (Exception e){
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        TVideo video = videoService.getBaseMapper().selectById(Long.valueOf(videoId));
        video.setThumbupNum(video.getThumbupNum() - 1);
        videoService.updateById(video);
        return Result.okResult();
    }
    /**
     * 评星
     * @return
     */
    @PutMapping("/star")
    public Result star(@RequestBody CommentedStarDto dto) {
        videoService.addComment(dto);
        return  Result.okResult();
    }
}
