package com.hahaha.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (TVideo)表实体类
 *
 * @author hahaha
 * @since 2024-03-17 13:30:41
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_video")
public class TVideo  {
    @TableId
    private Long videoId;

    //视频标题
    private String videoTitle;
    //详情
    private String videoInfo;
    //视频上传日期
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;

    private String videoUrl;
    //缩略图
    private String thunmbnailUrl;

    private Integer videoTypeId;

    @TableField(exist = false)
    private String typeName;
    //视频状态
    private Long videoStateId;
@TableField(fill= FieldFill.INSERT)
    private Long createUser;
    @TableField(exist = false)
    private String userName;
    //观看数量
    private Long viewNum;
    //评论数量
    private Long commentNum;
    //点赞数
    private Long thumbupNum;
    //收藏数
    @TableField(exist = false)
    private Long collectionNum;


    //把redis的浏览量数据更新到mysql数据库
    public TVideo(Long id, long viewNum) {
        this.videoId = id;
        this.viewNum = viewNum;
    }
}
