package com.hahaha.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.entity.TVideotype;
import com.hahaha.entity.vo.PageVo;
import com.hahaha.mapper.TVideotypeMapper;
import com.hahaha.service.TVideotypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * (TVideotype)表服务实现类
 *
 * @author hahaha
 * @since 2024-03-16 16:12:09
 */
@Service("tVideotypeService")
public class TVideotypeServiceImpl extends ServiceImpl<TVideotypeMapper, TVideotype> implements TVideotypeService {
    //查询标签列表
    @Override
    public PageVo getVideoTypeList(Integer pageNum, Integer pageSize, TVideotype type) {

        LambdaQueryWrapper<TVideotype> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(type.getTypeName()), TVideotype::getTypeName, type.getTypeName());

        //分页查询。
        Page<TVideotype> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);


        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return pageVo;
    }
    //查询标签列表
    @Override
    public PageVo getAllVideoTypeList(Integer pageNum, Integer pageSize) {

        //分页查询。
        Page<TVideotype> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,null);


        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return pageVo;
    }
}
