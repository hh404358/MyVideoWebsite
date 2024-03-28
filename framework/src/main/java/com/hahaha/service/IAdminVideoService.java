package com.hahaha.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.TState;
import com.hahaha.entity.TVideo;
import com.hahaha.entity.TVideotype;
import com.hahaha.entity.vo.PageVo;
import com.hahaha.entity.vo.TVideoListVo;

import java.util.List;

public interface IAdminVideoService extends IService<TVideo> {


    int restoreVideo(Long id);

    int deleteVideo(Long id);


    List<TState> getStateList();

    TVideoListVo getVideoById(Long id);



    int editVideo(TVideo video);


    int rdeleteVideo(Long id);

    List<TVideo> underVideoList();

    List<TVideoListVo> videoList();
}
