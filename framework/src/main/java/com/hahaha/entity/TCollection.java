package com.hahaha.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (TCollection)表实体类
 *
 * @author hahaha
 * @since 2024-03-16 16:12:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_collection")
public class TCollection  {
@TableId
    private Long collectionId;


    private Long userId;

    private Long videoId;
    
}
