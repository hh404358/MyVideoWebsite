package com.hahaha.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TVideoListVo {

    private Long videoId;

    //视频标题
    private String videoTitle;

    //视频上传日期
    private Date createTime;

    private String videoUrl;
    //缩略图
    private String thunmbnailUrl;

    private String typeName;

    private Long createUser;

    private String userName;
    //观看数量
    private Long viewNum;
    //评论数量
    private Long commentNum;
    //点赞数
    private Long thumbupNum;


}