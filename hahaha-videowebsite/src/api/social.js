import request from '../utils/request'

//关注列表
export function focus() {
    return request ({
        url: '/user/focus',
        method: 'get',
    })
}

//粉丝列表
export function fans() {
    return request ({
        url: '/user/fans',
        method: 'get',
    })
}

//朋友列表
export function friends() {
    return request ({
        url: '/user/friends',
        method: 'get',
    })
}

//新增关注
export function addFocus(focusId) {
    return request ({
        url: '/user/addFocus',
        method: 'post',
        params:{'focusId':focusId}
    })
}

//取消
export function cancelFocus(focusId) {
    return request ({
        url: '/user/cancelFocus',
        method: 'delete',
        params:{'focusId':focusId}
    })
}