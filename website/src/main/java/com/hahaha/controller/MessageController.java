package com.hahaha.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hahaha.constants.SystemConstants;
import com.hahaha.entity.TMessage;
import com.hahaha.entity.TMsgtype;
import com.hahaha.entity.TState;
import com.hahaha.entity.User;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.SystemException;
import com.hahaha.mapper.TMsgtypeMapper;
import com.hahaha.result.Result;
import com.hahaha.service.TMessageService;
import com.hahaha.service.TMsgtypeService;
import com.hahaha.service.TStateService;
import com.hahaha.service.UserService;
import com.hahaha.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 消息管理
 */
@RestController
@RequestMapping("/Message")
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private TMsgtypeService msgTypeService;

    @Autowired
    private TStateService stateService;

    @Autowired
    private TMessageService messageService;

    /**
     * 获取消息列表
     * @param msgTypeName
     * @return
     */
    @GetMapping("/getMsgList")
    public Result getMsgList( @RequestParam String msgTypeName) {
        Long userId;
        try{
            userId = SecurityUtils.getUserId();
        }catch (SystemException e){
            return Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        //查询消息类型对应的id
        LambdaQueryWrapper<TMsgtype> msgtypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        msgtypeLambdaQueryWrapper.eq(TMsgtype::getMsgtypeName,msgTypeName);
        Long msgTypeId = msgTypeService.getOne(msgtypeLambdaQueryWrapper).getMsgtypeId();
        
        //查询消息
        LambdaQueryWrapper<TMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TMessage::getMsgReceiveUserId,userId);
        queryWrapper.eq(TMessage::getMsgtypeId,msgTypeId);
        List<TMessage> messages = messageService.getBaseMapper().selectList(queryWrapper);
        return Result.okResult(messages);
    }

    /**
     * 删除消息
     * @param msgId
     * @return
     */
    @DeleteMapping("/{msgId}")
    public Result delMsg(@PathVariable Long msgId) {
        messageService.removeById(msgId);
        return Result.okResult();
    }

    /**
     * 更新信息状态为已读
     * @param msgId
     * @return
     */
    @PutMapping("/updateMsgState")
    public Result updateMsgState(Long msgId) {
        TMessage message = messageService.getById(msgId);
        message.setMsgStateId(Long.valueOf(SystemConstants.READ));
        messageService.updateById(message);
        return Result.okResult();
    }

    /**
     * 发消息
     * @param msgTitle
     * @param msgContext
     * @param receiveUserId
     * @param msgTypeId
     * @return
     */
    @PostMapping("/addMessage")
    public Result addMessage(@RequestParam String msgTitle, @RequestParam String msgContext, @RequestParam Long receiveUserId, @RequestParam Long msgTypeId) {
       return  messageService.add(msgTitle,msgContext,receiveUserId,msgTypeId);
    }
}
