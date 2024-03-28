package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.TCollection;

import java.util.List;


/**
 * (TCollection)表服务接口
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
public interface TCollectionService extends IService<TCollection> {
    
    List<TCollection> getAllCollection(Long userId);

    void deleteCollection(Long userId, Long videoId);
}
