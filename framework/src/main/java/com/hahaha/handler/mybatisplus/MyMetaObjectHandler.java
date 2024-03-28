package com.hahaha.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hahaha.constants.AutoFillConstant;
import com.hahaha.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            //获取用户id
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            //该用户还没注册
            userId = -1L;
        }
        //自动把下面四个字段新增了值。
        this.setFieldValByName(AutoFillConstant.CREATE_TIME, new Date(), metaObject);
        this.setFieldValByName(AutoFillConstant.CREATE_USER,userId , metaObject);
        this.setFieldValByName(AutoFillConstant.UPDATE_TIME, new Date(), metaObject);
        this.setFieldValByName(AutoFillConstant.UPDATE_USER, userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(AutoFillConstant.UPDATE_TIME, new Date(), metaObject);
        this.setFieldValByName(AutoFillConstant.UPDATE_USER, SecurityUtils.getUserId(), metaObject);
        this.setFieldValByName(" ", SecurityUtils.getUserId(), metaObject);
    }
}
