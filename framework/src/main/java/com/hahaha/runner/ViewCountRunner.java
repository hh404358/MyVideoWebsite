package com.hahaha.runner;


import com.hahaha.entity.TVideo;
import com.hahaha.mapper.TVideoMapper;
import com.hahaha.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TVideoMapper  videoMapper;
    @Override
    public void run(String... args) throws Exception {
        List<TVideo> videos = videoMapper.selectList(null);//表示查询所有
        Map<String, Integer> viewCountMap = videos.stream()
                .collect(Collectors.toMap(video -> video.getVideoId().toString(), video -> video.getViewNum().intValue()));
        redisCache.setCacheMap("video:viewCount",viewCountMap);
    }
}