package com.hahaha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.entity.TComment;
import com.hahaha.entity.vo.PageVo;


/**
 * 评论表(TComment)表服务接口
 *
 * @author hahaha
 * @since 2024-03-20 20:17:24
 */
public interface TCommentService extends IService<TComment> {

    PageVo commentList(Long videoId, Integer pageNum, Integer pageSize);

    void addComment(TComment comment);
}
