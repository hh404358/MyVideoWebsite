import request from '@/utils/request'

// 发送评论
export function sendComment(videoId,rootId,toCommentId,toCommentUserId,content) {
    return request({
        url: '/comment',
        method: 'post',
        data: {"videoId":videoId,"rootId":rootId,"toCommentId":toCommentId,"toCommentUserId":toCommentUserId,"content":content}
    })
}


export function getComment(videoId) {
    return request({
        url: '/comment/commentList',
        method: 'get',
        headers: {
          isToken: false
        },
        params: videoId
    })
}

