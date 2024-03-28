package com.hahaha.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hahaha.annotation.mySystemlog;
import com.hahaha.entity.*;
import com.hahaha.entity.dto.VideoDto;
import com.hahaha.entity.vo.TVideoListVo;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.result.Result;
import com.hahaha.service.*;
import com.hahaha.utils.AliOSSUtils;
import com.hahaha.utils.BeanCopyUtils;
import com.hahaha.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/content/video")
public class AdminVideoController {



    @Autowired
    private TVideoService videoService;

    /**
     * 根据条件分頁查詢
     * @param video
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('content:video:list')")//权限控制
    @mySystemlog(xxbusinessName = "分页查询")
    public Result videoList(TVideo video,Integer pageNum,Integer pageSize){
        return Result.okResult(videoService.selectTVideoPage(video,pageNum,pageSize));
    }

    /**
     * 删除视频
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:video:remove')")//权限控制
    public Result deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return Result.okResult();
    }

    /**
     * 修改视频
     * @param video
     * @return
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('content:video:edit')")//权限控制
    public Result editVideo(@RequestBody TVideo video) {
        videoService.updateById(video);
        return Result.okResult();
    }




    @Autowired
    private UserService userService;
    /**
     * 添加视频
     * @return
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('content:video:add')")
    public Result addVideo(@RequestBody VideoDto videoDto) {
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
     * 获得top前3的视频
     * @return
     */
    @GetMapping("/getIndexRecommendVideo")
    @PreAuthorize("@ps.hasPermission('content:video:query')")//权限控制
    public Result getIndexRecommendVideo() {
        return Result.okResult(videoService.getIndexRecommendVideo());
    }




    /**
     * 获取指定用户发布的视频
     * @param userId
     * @return
     */
    @GetMapping("/getVideoListByUserId")
    @PreAuthorize("@ps.hasPermission('content:video:query')")//权限控制
    public Result getVideoListByUserId(String userId) {
        List<TVideo> videoList = videoService.getVideoListByUserId(Long.valueOf(userId));
        if (videoList != null) {
            return Result.okResult(videoList);
        }
        return Result.errorResult(AppHttpCodeEnum.USER_NO_VIDEO);
    }

    /**
     * 查询热门视频
     * @return
     */
    @GetMapping("/hotVideoList")
    @mySystemlog(xxbusinessName = "查询热门视频")
    @PreAuthorize("@ps.hasPermission('content:video:query')")//权限控制
    public Result hotTVideoList(){
        return Result.okResult(videoService.hotVideoList());
    }

    @Autowired
    private TCollectionService collectionService;
    /**
     * 获取视频详情
     */
    @GetMapping("/{videoId}")
    @PreAuthorize("@ps.hasPermission('content:video:query')")//权限控制
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
        return Result.okResult(url);
    }


}
