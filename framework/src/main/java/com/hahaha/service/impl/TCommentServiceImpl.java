package com.hahaha.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.constants.SystemConstants;
import com.hahaha.entity.TComment;
import com.hahaha.entity.User;
import com.hahaha.entity.vo.CommentVo;
import com.hahaha.entity.vo.PageVo;
import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.exception.SystemException;
import com.hahaha.mapper.TCommentMapper;
import com.hahaha.service.TCommentService;
import com.hahaha.service.UserService;
import com.hahaha.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(TComment)表服务实现类
 *
 * @author hahaha
 * @since 2024-03-20 20:17:24
 */
@Service("tCommentService")
public class TCommentServiceImpl extends ServiceImpl<TCommentMapper, TComment> implements TCommentService {

    @Autowired
    private TCommentService commentService;
    @Autowired
    private UserService userService;
    @Override
    public PageVo commentList(Long  videoId, Integer pageNum, Integer pageSize) {
        System.out.println("videoID"+videoId);
        LambdaQueryWrapper<TComment> queryWrapper = new LambdaQueryWrapper<>();

        //对评论区的某条评论的rootID进行判断，如果为-1，就表示是根评论
        queryWrapper.eq(TComment::getRootId, SystemConstants.COMMENT_ROOT);
        queryWrapper.eq(TComment::getVideoId,videoId);
        //分页查询。查的是整个评论区的每一条评论
        Page<TComment> page  = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        //根评论排序
        List<TComment> sortedComments = page.getRecords().stream()
                .sorted(Comparator.comparing(TComment::getCreateTime).reversed())
                .collect(Collectors.toList());
        List<CommentVo> commentVoList = ToCommentList(sortedComments);


        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());

            commentVo.setChildren(children);

        }


        return new PageVo(commentVoList,page.getTotal());
    }
    /**
     * 在评论区发送评论
     * @param comment
     */
    @Override
    public void addComment(TComment comment) {

        //限制用户在发送评论时，评论内容不能为空。
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
         commentService.save(comment);

    }


    /**
     * 封装响应返回
     */
    private List<CommentVo> ToCommentList(List<TComment> list){
        //获取评论区的所有评论
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //得到username字段
        for (CommentVo commentVo : commentVos) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getId,commentVo.getCreateUser());
            String nickName = userService.getOne(queryWrapper).getNickName();
            commentVo.setUsername(nickName);

            if(commentVo.getToCommenuserId() != -1){
                String toCommenuserName = userService.getById(commentVo.getToCommenuserId()).getNickName();
                commentVo.setToCommenuserName(toCommenuserName);
            }

        }

        return commentVos;
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<TComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TComment::getRootId,id);
        //对子评论按照时间进行排序
        queryWrapper.orderByDesc(TComment::getCreateTime);
        List<TComment> comments = list(queryWrapper);

        List<CommentVo> commentVos = ToCommentList(comments);
        return commentVos;
    }
}
