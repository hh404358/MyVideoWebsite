package com.hahaha.controller;

import com.hahaha.annotation.mySystemlog;
import com.hahaha.entity.TDanmu;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.result.Result;
import com.hahaha.service.TDanmuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 视频弹幕管理
 */
@RestController
@Slf4j
public class DanmuController {

    @Autowired
    private TDanmuService danmuService;

    /**
     * 获取指定视频的弹幕
     * @param videoId
     * @return
     */
    @GetMapping("/danmu")
    @mySystemlog(xxbusinessName = "弹幕获取")
    public Result getAll(String videoId){

        List<TDanmu> allTDanmu = danmuService.getAllTDanmu(Long.valueOf(videoId));
        log.info(allTDanmu.toString());
        return Result.okResult(allTDanmu);
    }

    /**
     * 新增弹幕
     * @param danmu
     * @return
     */
    @PostMapping("/danmu")
    public Result sendDanmu(@RequestBody TDanmu danmu){
        System.out.println(danmu.toString());
        return danmuService.saveTDanmu(danmu);
    }
}
