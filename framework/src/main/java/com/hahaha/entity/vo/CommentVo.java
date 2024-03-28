package com.hahaha.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

    private Long id;

    //id
    private Long videoId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //发根评论的userid
    private Long toCommenuserId;
    //发根评论的userName
    private String toCommenuserName;
    //回复目标评论id
    private Long toCommentId;
    //当前评论的创建人id
    private Long createUser;

    private Date createTime;

    //评论是谁发的
    private String username;

    //查询子评论
    private List<CommentVo> children;

}
