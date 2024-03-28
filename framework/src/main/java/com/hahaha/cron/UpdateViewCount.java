package com.hahaha.cron;

import com.hahaha.entity.TVideo;
import com.hahaha.service.TVideoService;
import com.hahaha.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务实现每10秒把redis中的浏览量更新到mysql数据库中
 */
@Component
@Slf4j
public class UpdateViewCount {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TVideoService videoService;

    @Scheduled(cron = "* 1 * * * *")
    public void updateViewCount(){
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("video:viewCount");
        List<TVideo> videos = viewCountMap.entrySet()
                .stream()
                .map(entry -> new TVideo(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        videoService.updateBatchById(videos);

        log.info("redis的浏览量数据已更新到数据库，现在的时间是: "+ LocalTime.now());
    }
}