import request from '@/utils/request'

// 发送
export function sendDanmu( message) {
    return request({
        url: '/danmu',
        method: 'post',
        data: message
    })
}


export function getDanmu(videoId) {
    return request({
        url: '/danmu',
        method: 'get',
        headers: {
          isToken: false
        },
        params: {'videoId':videoId}
    })
}
