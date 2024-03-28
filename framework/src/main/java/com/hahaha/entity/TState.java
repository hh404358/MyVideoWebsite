package com.hahaha.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (TState)表实体类
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_state")
public class TState  {
@TableId
    private Long stateId;


    private String stateName;
    
}
