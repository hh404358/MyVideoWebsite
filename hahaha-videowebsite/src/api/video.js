import request from '@/utils/request'

//根据视频类型查询
export function getVideoByType(videoTypeId) {
  return request({
    url: '/video/getVideoByType' ,
    method: 'get',
    headers: {
      isToken: false
    },
    params:{'videoTypeId':videoTypeId}
  })
}

//根据视频标题查询
export function getVideoByTitle(title) {
  return request({
    url: '/video/getVideoByTitle/' + title,
    method: 'get',
    headers: {
      isToken: false
    },
    params:{'title':title}
  })
}

// 根据用户id查询视频
export function getVideoListByUserId(userId) {
  return request({
    url: '/video/getVideoListByUserId/' + userId,
    method: 'get',
    headers: {
      isToken: false
    }
  })
}

// 分页查询视频
export function videoList(query) {
  return request({
    url: '/video/videoList',
    method: 'get',
    headers: {
      isToken: false
    },
    params: query
  })
}

//查询最热视频
export function hotVideoList() {
  return request({
    url: '/video/hotVideoList',
    headers: {
      isToken: false
    },
    method: 'get'
  })
}

//获取视频详情
export function getVideo(videoId) {
  return request({
    url: '/video/' + videoId,
    headers: {
      isToken: false
    },
    method: 'get'
  })
}

//上传视频图片
export function uploadImage(videoId) {
  return request({
    url: '/video/uploadImage/' + videoId,
    headers: {
      isToken: true
    },
    method: 'put'
  })
}

//添加视频
export function addVideo(videoDto) {
  return request({
    url: '/video/addVideo/',
    headers: {
      isToken: true
    },
    method: 'post',
    data: videoDto
  })
}

//上传视频
export function uploadVideo() {
  return request({
    url: '/video/uploadVideo',
    headers: {
      isToken: true
    },
    method: 'post'
  })

}

//更新观看数量
export function updateViewCount(videoId) {
  return request({
    url: '/video/updateViewCount/' + videoId,
    headers: {
      isToken: false
    },
    method: 'put'
  })
}

//获得分页的推荐视频
export function getRecommendVideo() {
  return request({
    url: '/video/getRecommendVideo',
    headers: {
      isToken: true
    },
    method: 'get'
  })
}

//获得top前3的视频
export function getIndexRecommendVideo() {
  return request({
    url: '/video/getIndexRecommendVideo',
    headers: {
      isToken: true
    },
    method: 'get'
  })
}

//点赞
export function thumbsUp(videoId) {
  return request({
    url: '/video/thumbsUp',
    headers: {
      isToken: true
    },
    method: 'put',
    params:{'videoId':videoId}
  })
}
//取消点赞
export function cancelThumbsUp(videoId) {
  return request({
    url: '/video/cancelThumbsUp',
    headers: {
      isToken: true
    },
    method: 'put',
    params:{'videoId':videoId}
  })
}


//评星
export function star() {
  return request({
    url: '/video/star',
    headers: {
      isToken: true
    },
    method: 'put'
  })
}



