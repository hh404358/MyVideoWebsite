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
 * 评论表(TComment)表实体类
 *
 * @author hahaha
 * @since 2024-03-20 20:17:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_comment")
public class TComment  {
@TableId
    private Long id;

//id
    private Long videoId;
//根评论id
    private Long rootId;
//评论内容
    private String content;
//所回复的目标评论的userid
    private Long toCommenuserId;
//回复目标评论id
    private Long toCommentId;
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
//删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
    
}
