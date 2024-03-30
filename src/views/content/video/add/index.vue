<template>
  <div class="app-container">

    <el-form ref="form" :model="form" label-width="90px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="视频标题" >
            <el-input
              v-model="form.videoTitle"
              placeholder="请输入视频标题" 
              maxlength="30"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="分类">
            <el-select v-model="form.videoTypeId" placeholder="请选择">
              <el-option
                v-for="(category,index) in categoryList"
                :key="index"
                :label="category"
                :value="index + 1" 
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="视频摘要">
            <el-input v-model="form.videoInfo" type="textarea" />
          </el-form-item>
        </el-col>
         <el-col :span="12">
          <el-form-item label="视频状态">
            <el-radio-group v-model="form.videoStateId">
              <el-radio :key="'1'" :label="'1'">认证中</el-radio>
              <el-radio :key="'2'" :label="'2'">已认证</el-radio>
              <el-radio :key="'3'" :label="'3'">认证失败</el-radio>
              <el-radio :key="'4'" :label="'4'">上架</el-radio>
              <el-radio :key="'5'" :label="'5'">下架</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row :gutter="20" />

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="缩略图">
            <el-upload
              :file-list="fileList"
              class="upload-demo"
              list-type="picture"
              drag
              name="img"
              :action="this.imgUrl"
              :on-remove="fileRemove"
              :limit="1"
              :http-request="handleUpload"
              :on-exceed="onExceed"
            >
              <i class="el-icon-upload" />
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2MB</div>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item>
            <el-button type="primary" size="medium" @click="handleSubmit">{{ aId?"更新":"发布" }}</el-button>
          </el-form-item>
        
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="视频">
            <el-upload
              :file-list="fileList"
              class="upload-demo"
              list-type="picture"
              drag
              name="video"
              :action="this.videoUrl"
              :on-remove="fileRemove"
              :limit="1"
              :http-request="handleUpload"
              :on-exceed="onExceed"
            >
              <i class="el-icon-upload" />
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div slot="tip" class="el-upload__tip">只能上传mp4文件</div>
            </el-upload>
          </el-form-item>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { uploadImg,uploadVideo } from '@/api/content/upload'
import { addVideo, getVideo, updateVideo } from '@/api/content/video'
export default {
  name: 'Add',
  data() {
    return {
      form: {
        videoTitle:'',
        videoTypeId:'',
        videoInfo:'',
        videoStateId:'',
        videoUrl:'',
        thunmbnailUrl:''
      },
      
      categoryList: [
                "影视",
                "新闻",
                "生活",
                "美食",
                "音乐",
                "电视剧",
                "舞蹈",
                "动漫",
                "娱乐",
                "科技数码",
                "体育",
                "美妆"],
      aId: -1,
      fileList: [],
      imgUrl:'',
      videoUrl:''
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.aId = route.query && route.query.id
        console.log(this.aId)
      },
      immediate: true
    }
  },
  created() {
    
    this.imgUrl = 'http://localhost:8989/content/video/uploadImage',
    this.videoUrl = 'http://localhost:8989/content/video/uploadVideo'
    if (this.aId) {
      this.getVideo()
    }
  },
  methods: {
    getVideo() {
      getVideo(this.aId).then(response => {
        this.form = response
        this.fileList.push({ name: '缩略图', url: response.thunmbnailUrl })
        this.fileList.push({ name: '视频', url: response.videoUrl })
      })
    },
   
    handleSubmit() {
      if (!this.aId) {
        addVideo(this.form).then(response => {
          this.$modal.msgSuccess('视频发布成功')
          this.$router.push({ path: '/content/video' })
        })
      } else {
        // 更新视频信息
        updateVideo(this.form).then(response => {
          this.$modal.msgSuccess('视频更新成功')
          this.$router.push({ path: '/content/video' })
        })
      }
    },
    onExceed() {
      this.$message.error('只能上传一个文件(图片文件)')
    },
    handleUploadImg(img) {
      uploadImg(img.file).then(response => {
        this.form.thunmbnailUrl = response
        this.fileList.push({ name: img.file.name, url: response })
      }).catch(error => {
        this.$message.error(error.msg)
      })
    },
    handleUploadVideo(video) {
      uploadVideo(video.file).then(response => {
        this.form.videoUrl = response
        this.fileList.push({ name: video.file.name, url: response })
      }).catch(error => {
        this.$message.error(error.msg)
      })
    },
    fileRemove(file, fileList) {
      this.fileList.pop()
    },
    // 绑定@imgAdd event
    addImg(pos, file) {
      // 第一步.将图片上传到服务器.
      uploadImg(file).then(response => {
        this.$refs.md.$img2Url(pos, response)
      }).catch(error => {
        this.$message.error(error.msg)
      })
      
    },
    addVideo(pos, file) {
      // 第一步.将图片上传到服务器.
      uploadVideo(file).then(response => {
        this.$refs.md.$img2Url(pos, response)
      }).catch(error => {
        this.$message.error(error.msg)
      })
      
    },

  
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    }
  }
}
</script>
<style scoped>

.el-col .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;

    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
