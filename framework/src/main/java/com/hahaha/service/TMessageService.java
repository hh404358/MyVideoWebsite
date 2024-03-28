package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.TMessage;
import com.hahaha.result.Result;


/**
 * (TMessage)表服务接口
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
public interface TMessageService extends IService<TMessage> {

    Result add(String msgTitle, String msgContext, Long receiveUserId, Long msgTypeId);
}
