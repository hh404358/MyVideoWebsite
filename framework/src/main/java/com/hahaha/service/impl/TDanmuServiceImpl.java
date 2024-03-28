package com.hahaha.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.entity.TDanmu;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.SystemException;
import com.hahaha.mapper.TDanmuMapper;
import com.hahaha.result.Result;
import com.hahaha.service.TDanmuService;
import com.hahaha.service.UserService;
import com.hahaha.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TDanmu)表服务实现类
 *
 * @author makejava
 * @since 2024-03-21 18:23:52
 */
@Service("tDanmuService")
public class TDanmuServiceImpl extends ServiceImpl<TDanmuMapper, TDanmu> implements TDanmuService {

    @Autowired
    private TDanmuMapper danmuMapper;
    @Autowired
    private UserService userService;
    @Override
    public Result saveTDanmu(TDanmu danmu) {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (SystemException e) {
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        danmu.setAvatar(userService.getById(userId).getAvatar());
        System.out.println(danmu.toString());
        danmuMapper.insert(danmu);
        return Result.okResult();
    }

    @Override
    public List<TDanmu> getAllTDanmu(Long videoId) {
        LambdaQueryWrapper<TDanmu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TDanmu::getVideoId,videoId);
        return danmuMapper.selectList(queryWrapper);
    }
}
