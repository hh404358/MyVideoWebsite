package com.hahaha.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class VideoDto {

    private Long videoId;

    //视频标题
    private String videoTitle;
    //详情
    private String videoInfo;
    //视频状态
    private Long videoStateId;

    private String videoUrl;
    //缩略图
    private String thunmbnailUrl;

    private Integer videoTypeId;

    private Integer userId;

}
