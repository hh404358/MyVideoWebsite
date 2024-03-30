package com.hahaha.entity.dto;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hahaha.entity.TState;
import lombok.Data;

import java.util.Date;

@Data
public class UserStateDTO {

    private Long id;

    //用户名
    private String userName;
    //昵称
    private String nickName;
    //用户类型：0代表普通用户，1代表管理员
    private String type;
    //账号状态（0正常 1停用）
    private String status;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    //粉丝数量
    private Long fansNum;
    //关注数量
    private Long focusNum;
    //朋友数量
    private Long friendsNum;
    //创建人的用户id
    private Long createUser;
    //创建时间
    private Date createTime;
    //更新人
    private Long updateUser;
    //更新时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;


}