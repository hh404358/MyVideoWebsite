import request from '@/utils/request'
import store from '@/store'

// 上传视频缩略图

export function uploadImg(img) {
  const formData = new FormData()
  formData.append('img', img)
  return request({
    url: store.state.BaseUrl+'/content/video/uploadImg',
    headers: { 'Content-Type': 'multipart/form-data' },
    method: 'post',
    data: formData
  })
}

//上传视频
export function uploadVideo(video) {
  const formData = new FormData()
  return request({
    url: store.state.BaseUrl+'/content/video/uploadVideo',
    headers: { 'Content-Type': 'multipart/form-data' },
    method: 'post',
    data: formData
  })
}
