package com.hahaha.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hahaha.entity.TCollection;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.result.Result;
import com.hahaha.service.TCollectionService;
import com.hahaha.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏视频管理
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private TCollectionService collectionService;

    /**
     * 新增收藏
     * @param videoId
     * @return
     */
    @PostMapping("/addCollection")
    public Result addCollection(Long videoId) {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        if (userId == null) {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        LambdaQueryWrapper<TCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TCollection::getUserId, userId);
        queryWrapper.eq(TCollection::getVideoId, videoId);
        TCollection collection = collectionService.getBaseMapper().selectOne(queryWrapper);
        if (collection != null) {
            return Result.errorResult(AppHttpCodeEnum.NO_SELF_COLLECT);
        }
        return Result.okResult(collectionService.getBaseMapper().insert(new TCollection(null, userId, videoId)));
    }

    /**
     * 获取当前用户的所用收藏视频
     * @return
     */
    @GetMapping("/getAllCollection")
    @ResponseBody
    public Result getAllCollection() {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        if (userId != null) {
            List<TCollection> collection = collectionService.getAllCollection(userId);
            if (collection != null) {
                return Result.okResult(collection);
            } else {
                return Result.errorResult(AppHttpCodeEnum.GET_COLLECTION_ERROR);
            }
        } else {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
    }

    /**
     * 删除收藏视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/deleteCollection")
    public Result deleteCollection( Long videoId) {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        if (userId == null) {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else if (videoId == null) {
            return Result.errorResult(AppHttpCodeEnum.VIDEOID_ERROR);
        } else {
            collectionService.deleteCollection(userId, videoId);
            return Result.okResult();
        }
    }
}
