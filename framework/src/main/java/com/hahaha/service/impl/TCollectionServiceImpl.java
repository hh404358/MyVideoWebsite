package com.hahaha.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.entity.TCollection;
import com.hahaha.mapper.TCollectionMapper;
import com.hahaha.service.TCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TCollection)表服务实现类
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
@Service("tCollectionService")
public class TCollectionServiceImpl extends ServiceImpl<TCollectionMapper, TCollection> implements TCollectionService {

    @Autowired
    private TCollectionMapper collectionMapper;
    @Override

    public List<TCollection> getAllCollection(Long userId) {
        return collectionMapper.getAll(userId);
    }

    @Override
    public void deleteCollection(Long userId, Long videoId) {
        LambdaQueryWrapper<TCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TCollection::getUserId,userId);
        queryWrapper.eq(TCollection::getVideoId,videoId);
        getBaseMapper().delete(queryWrapper);
    }
}
