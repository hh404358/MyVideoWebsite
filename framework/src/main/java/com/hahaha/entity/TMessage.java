package com.hahaha.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (TMessage)表实体类
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_message")
public class TMessage  {
@TableId
    private Long msgId;


    private String msgTitle;

    private String msgContext;

    private Date msgSendDate;

    private Long msgSendUserId;

    private Long msgReceiveUserId;

    private Long msgStateId;

    private Long msgtypeId;
    
}
