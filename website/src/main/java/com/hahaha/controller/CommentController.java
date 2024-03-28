package com.hahaha.controller;


import com.hahaha.annotation.mySystemlog;
import com.hahaha.constants.SystemConstants;
import com.hahaha.entity.TComment;
import com.hahaha.result.Result;
import com.hahaha.service.TCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论管理
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private TCommentService commentService;

    /**
     * 获取评论列表
     * @param videoId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("commentList")
    @mySystemlog(xxbusinessName = "获取评论列表")
    public Result commentList(Long videoId, Integer pageNum, Integer pageSize){
        return Result.okResult(commentService.commentList(videoId,pageNum,pageSize));
    }

    /**
     * 在评论区发送评论。
     * @param comment
     * @return
     */
    @PostMapping
    @mySystemlog(xxbusinessName = "在评论区发送评论")
    public Result addComment(@RequestBody TComment comment){
        commentService.addComment(comment);
        return Result.okResult();
    }


}