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
 * (TDanmu)表实体类
 *
 * @author makejava
 * @since 2024-03-21 18:23:52
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_danmu")
public class TDanmu  {
//主键id@TableId
    private Integer id;

//昵称
    private String nickname;
//头像
    private String avatar;
//留言内容
    private String danmuContent;
//弹幕速度
    private Integer time;
//发布时间
@TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Long videoId;
    
}
