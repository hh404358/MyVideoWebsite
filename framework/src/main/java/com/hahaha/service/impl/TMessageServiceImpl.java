package com.hahaha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.constants.SystemConstants;
import com.hahaha.entity.TMessage;
import com.hahaha.entity.User;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.SystemException;
import com.hahaha.mapper.TMessageMapper;
import com.hahaha.result.Result;
import com.hahaha.service.TMessageService;
import com.hahaha.service.UserService;
import com.hahaha.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (TMessage)表服务实现类
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
@Service("tMessageService")
public class TMessageServiceImpl extends ServiceImpl<TMessageMapper, TMessage> implements TMessageService {

    @Autowired
    private UserService userService;
    @Autowired
    private TMessageService messageService;
    @Override
    public Result add(String msgTitle, String msgContext, Long receiveUserId, Long msgTypeId) {
        Long userId;
        try{
            userId = SecurityUtils.getUserId();
        }catch (SystemException e){
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        TMessage msg = new TMessage();
        msg.setMsgTitle(msgTitle);
        msg.setMsgContext(msgContext);
        msg.setMsgSendUserId(userId);
        User recieveUser = userService.getById(receiveUserId);
        if(recieveUser == null) {
            return  Result.errorResult(AppHttpCodeEnum.NO_RECEIVERUSER);
        }
        if(receiveUserId.equals(userId)) {
            return Result.errorResult(AppHttpCodeEnum.NO_SELF_MESSAGE);
        }
        msg.setMsgReceiveUserId(receiveUserId);
        msg.setMsgStateId(Long.valueOf(SystemConstants.UNREAD));
        messageService.save(msg);
        return Result.okResult(msg);
    }
}
