import request from '@/utils/request'

// 查询视频列表
export function addVideo(data) {
  return request({
    url: '/content/video',
    method: 'post',
    data: data
  })
}

// 查询视频列表
export function listVideo(query) {
  return request({
    url: '/content/video/list',
    method: 'get',
    params: query
  })
}

// 删除视频
export function delVideo(id) {
  return request({
    url: '/content/video/' + id,
    method: 'delete'
  })
}

// 查询分类详细
export function getVideo(id) {
  return request({
    url: '/content/video/' + id,
    method: 'get'
  })
}

// 修改分类
export function updateVideo(data) {
  return request({
    url: '/content/video',
    method: 'put',
    data: data
  })
}
