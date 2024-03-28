package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.TDanmu;
import com.hahaha.result.Result;

import java.util.List;


/**
 * (TDanmu)表服务接口
 *
 * @author makejava
 * @since 2024-03-21 18:23:52
 */
public interface TDanmuService extends IService<TDanmu> {


    Result saveTDanmu(TDanmu danmu);

    List<TDanmu> getAllTDanmu(Long videoId);
}
