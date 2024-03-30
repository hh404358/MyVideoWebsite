import request from '@/utils/request'

//新增收藏
export function addCollection(videoId) {
    return request ({
        url: '/collection/addCollection',
        method: 'post',
        params:{'videoId':videoId}
    })
}

//获取收藏
export function getAllCollection(videoId) {
    return request ({
        url: '/collection/getAllCollection',
        method: 'get',
        params:{'videoId':videoId}
    })
}

//取消
export function cancelCollection(videoId) {
    return request ({
        url: '/collection/deleteCollection',
        method: 'delete',
        params:{'videoId':videoId}
    })
}