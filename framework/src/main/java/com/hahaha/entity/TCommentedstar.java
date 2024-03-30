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
 * (TCommentedstar)表实体类
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_commentedstar")
public class TCommentedstar  {
@TableId
    private Long commentedstarId;


    private Long userId;

    private Long videoId;

    private Long starNum;
@TableField(fill= FieldFill.INSERT)
    private Date commentDate;
    
}