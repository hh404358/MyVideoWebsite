package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.TVideotype;
import com.hahaha.entity.vo.PageVo;


/**
 * (TVideotype)表服务接口
 *
 * @author hahaha
 * @since 2024-03-16 16:12:09
 */
public interface TVideotypeService extends IService<TVideotype> {

    //查询标签列表
    PageVo getVideoTypeList(Integer pageNum, Integer pageSize, TVideotype type);

    //查询标签列表
    PageVo getAllVideoTypeList(Integer pageNum, Integer pageSize);
}
