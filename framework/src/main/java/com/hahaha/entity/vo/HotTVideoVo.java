package com.hahaha.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotTVideoVo {

    private Long videoId;
    //视频标题
    private String videoTitle;
    //缩略图
    private String thunmbnailUrl;
    //观看数量
    private Long viewNum;
    //评论数量
    private Long commentNum;
    //点赞数
    private Long thumbupNum;

}