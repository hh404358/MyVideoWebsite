import request from '@/utils/request'

// 登录
export function userLogin(username,password) {
    return request({
        url: '/login',
        method: 'post',
        headers: {
            isToken: false
          },
        data: {'username':username,'password':password}
    })
}

//注册
export function userRegister(username,nickName,email,password,phonenumber) {
    return request({
        url: '/register',
        method: 'post',
        headers: {
            isToken :false
        },
        data: {"username":username,"nickName":nickName,"email":email,"password":password,"phonenumber":phonenumber}
    })
}

//退出
export function logout() {
    return request({
        url: '/logout',
        method: 'post'
    })
}

//获取用户信息
export function getUserInfo(userId) {
    return request ({
        url: '/user/userInfo',
        method: 'get',
        params: {"userId":userId}
    })
}

//保存用户信息
export function saveUserInfo(userinfo) {
    return request({
        url: '/user/userInfo',
        method: 'put',
        data: userinfo
    })
}

//获取用户头像
export function getAvatar(userId) {
    return request ({
        url: '/user/getAvatar',
        method: 'get',
        params: {"userId":userId}
    })
}



