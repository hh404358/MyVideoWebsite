<!-- 用户中心 -->
<template>
    <div>
        <wbc-nav></wbc-nav>
        <div class="container">
            <!-- 编辑用户信息 -->
            <el-card v-show="isEdit" class="tcommonBox">
                <header>
                    <h1>
                        编辑个人资料
                    </h1>
                </header>
                <section>
                    <ul class="userInfoBox">
                        <li class="avatarlist">
                            <span class="leftTitle">头像</span>
                            <el-upload class="avatar-uploader" name="file" :action="uploadURL" :show-file-list="false"
                                :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                                <img v-if="userInfoObj.avatar"
                                    :src="userInfoObj.avatar ? userInfoObj.avatar : 'static/img/tou.jpg'"
                                    :onerror="$store.state.errorImg" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                <div slot="tip" class="el-upload__tip">点击上传头像，只能上传jpg/png文件，且不超过1mb</div>
                            </el-upload>
                        </li>
                        <li class="username">
                            <span class="leftTitle">昵称</span>
                            <el-input v-model="userInfoObj.nickName" placeholder="昵称"></el-input> <i
                                class="fa fa-wa fa-asterisk"></i>
                        </li>
                        <li>
                            <span class="leftTitle">电子邮件</span>
                            <span>{{ userInfoObj.email }}</span>
                        </li>
                        <li>
                            <span class="leftTitle">性别</span>
                            <template>
                                <el-radio class="radio" v-model="userInfoObj.sex" label="0">男</el-radio>
                                <el-radio class="radio" v-model="userInfoObj.sex" label="1">女</el-radio>
                            </template>
                        </li>


                    </ul>
                    <div class=" saveInfobtn">
                        <a class="tcolors-bg" href="javascript:void(0);" @click="isEdit = !isEdit">返 回</a>
                        <a class="tcolors-bg" href="javascript:void(0);" @click="saveInfoFun">保 存</a>
                    </div>
                </section>
            </el-card>
            <!-- 展示个人信息 -->
            <el-card v-show="!isEdit" class="tcommonBox">
                <header>
                    <h1>
                        个人中心
                        <span class="gotoEdit" @click="isEdit = !isEdit"><i class="fa fa-wa fa-edit"></i>编辑</span>
                    </h1>

                </header>
                <section>
                    <ul class="userInfoBox">
                        <li class="avatarlist">
                            <span class="leftTitle">头像</span>
                            <div class="avatar-uploader">
                                <img :src="userInfoObj.avatar ? userInfoObj.avatar : 'static/img/tou.jpg'"
                                    :onerror="$store.state.errorImg" class="avatar">
                            </div>
                        </li>
                        <li class="username">
                            <span class="leftTitle">昵称</span>
                            <span>{{ userInfoObj.nickName ? userInfoObj.nickName : "无" }}</span>

                        </li>
                        <li>
                            <span class="leftTitle">电子邮件</span>
                            <span>{{ userInfoObj.email ? userInfoObj.email : "无" }}</span>
                        </li>
                        <li>
                            <span class="leftTitle">性别</span>
                            <span>{{ userInfoObj.sex == 0 ? '男' : '女' }}</span>
                        </li>

                        <li>
                            <el-button @click="isVideo = true">快来上传你的视频叭~~~</el-button>

                            <!-- <img src="../../static/img/coffee.jpg" @click="isVideo = true"
                                style="width: 300px;height: 300px;border-radius: 50%;" />{{ isVideo }} -->
                        </li>
                    </ul>

                </section>
            </el-card>
            <!-- 添加视频 -->
            <el-dialog title="上传属于你自己的视频吧~~~" :visible.sync="isVideo" width="30%" :before-close="handleClose">
                <el-form ref="video" :model="video" label-width="80px">
                    <!-- <el-form-item label="视频">
                        <el-upload class="video-uploader" name="file" :action="uploadURL1" :show-file-list="false"
                            :on-success="handleVideoSuccess"   :accept="videoAccept">
                            <video v-if="video.thunmbnailUrl" :src="video.thunmbnailUrl" class=""></video>
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            <div slot="tip" class="el-upload__tip">点击上传视频</div>
                        </el-upload>

                    </el-form-item> -->
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="缩略图">
                                <el-upload :file-list="fileList" class="upload-demo" list-type="picture" drag name="img"
                                    :action="this.uploadURL2" :on-remove="fileRemove" :limit="1"
                                    :http-request="handleUpload" :on-exceed="onExceed">
                                    <i class="el-icon-upload" />
                                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2MB</div>
                                </el-upload>
                            </el-form-item>
                        </el-col>

                    </el-row>
                    <el-row>
                        <el-form-item label="视频">
                            <el-upload :file-list="fileList" class="upload-demo" list-type="picture" drag name="video"
                                :action="this.uploadURL1" :on-remove="fileRemove" :limit="1"
                                :http-request="handleUpload" :on-exceed="onExceed">
                                <i class="el-icon-upload" />
                                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                                <div slot="tip" class="el-upload__tip">只能上传mp4文件</div>
                            </el-upload>
                        </el-form-item>
                    </el-row>

                    <!-- <el-form-item label="视频图片">
                        <el-upload class="videoImg-uploader" name="file" :action="uploadURL2" :show-file-list="false"
                            :on-success="handleVideoImgSuccess">
                            <img v-if="video.thunmbnailUrl" :src="video.thunmbnailUrl" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            <div slot="tip" class="el-upload__tip">点击上传视频图片</div>
                        </el-upload>

                    </el-form-item> -->
                    <el-form-item label="视频标题">
                        <el-input type="textarea" v-model="video.videoTitle"></el-input>
                    </el-form-item>
                    <el-form-item label="视频详情">
                        <el-input type="textarea" v-model="video.videoInfo"></el-input>
                    </el-form-item>
                    <el-form-item label="视频类别">
                        <el-checkbox-group v-model="video.videoTypeId">
                            <el-checkbox label=1 name="type">影视</el-checkbox>
                            <el-checkbox label=2 name="type">新闻</el-checkbox>
                            <el-checkbox label=3 name="type">生活</el-checkbox>
                            <el-checkbox label=4 name="type">美食</el-checkbox>
                            <el-checkbox label=5 name="type">音乐</el-checkbox>
                            <el-checkbox label=6 name="type">电视剧</el-checkbox>
                            <el-checkbox label=7 name="type">舞蹈</el-checkbox>
                            <el-checkbox label=8 name="type">动漫</el-checkbox>
                            <el-checkbox label=9 name="type">娱乐</el-checkbox>
                            <el-checkbox label=10 name="type">科技数码</el-checkbox>
                            <el-checkbox label=11 name="type">体育</el-checkbox>
                            <el-checkbox label=12 name="type">美妆</el-checkbox>
                        </el-checkbox-group>
                    </el-form-item>
                    <el-form-item>

                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="saveVideo()">立即创建</el-button>
                    <el-button type="primary" @click="isVideo = !isVideo">取消</el-button>
                </span>
            </el-dialog>
            <!-- 粉丝列表 -->
            <social></social>

        </div>
    </div>
</template>

<script>
import header from '../components/header.vue'
import { getUserInfo, saveUserInfo } from '../api/user.js'//获取用户信息，保存用户信息
import store from '../store'
import { addVideo } from '../api/video.js'
import fanList from '../components/social.vue'
export default {
    name: 'UserInfo',
    data() { //选项 / 数据
        return {
            uploadURL: '',
            isEdit: false,
            userInfo: {},//本地存储的用户信
            userInfoObj: '',//用户的信息
            video: {
                videoTitle: '',
                videoInfo: '',
                videoTypeId: [],
                videoUrl: '',
                thunmbnailUrl: '',
            },//新增视频信息
            videoAccept: 'video/*',
            isVideo: false,
            fileList: [],
            uploadURL1: '',
            uploadURL2: '',
            loading: false
        }
    },
    methods: { //事件处理器
        handleAvatarSuccess(res, file) {//上传头像
            if (res.code == 200) {
                this.userInfoObj.avatar = res.data;
                console.log("imgurl:" + res.data);
                this.userInfoObj.head_start = 1;
            } else {
                this.$message.error('上传图片失败');
            }

        },
        beforeAvatarUpload(file) {//判断头像大小
            const isJPG = file.type == 'image/png' || file.type == 'image/jpg' || file.type == 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 1;
            console.log(file);
            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG/JPEG/PNG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 1MB!');
            }

            console.log("res:" + (isJPG && isLt2M));
            return isJPG && isLt2M;
        },

        saveInfoFun: function () {//保存编辑的用户信息
            var that = this;
            console.log("save:");
            if (!that.userInfoObj.nickName) { //昵称为必填
                that.$message.error('昵称为必填项，请填写昵称');
                return;
            };


            saveUserInfo(that.userInfoObj).then((response) => {//保存信息接口，返回展示页
                that.$message.success('保存成功！');
                that.isEdit = false;
                that.routeChange();
            })
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
        },
        saveVideo: function () {//保存编辑的用户信息
            var that = this;
            console.log("save:" + that.video.videoTitle);
            if (!that.video.videoTitle) {
                that.$message.error('视频标题为必填项，请填写');
                return;
            };
            if (!that.video.videoInfo) {
                that.$message.error('视频详情为必填项，请填写');
                return;
            };
            if (!that.video.videoTypeId) {
                that.$message.error('视频类型为必选项，请填写');
                return;
            };
            this.video.videoTypeId = parseInt(this.video.videoTypeId);
            console.log("video:" + that.video);
            addVideo(that.video).then((response) => {
                that.$message.success('保存成功！');
                that.isVideo = false;
                that.routeChange();
            })
        },
        routeChange: function () {//展示页面信息
            var that = this;
            // console.log(this.$router,this.$route);
            if (localStorage.getItem('userInfo')) {
                that.haslogin = true;
                that.userInfo = JSON.parse(localStorage.getItem('userInfo'));
                that.userId = that.userInfo.id;
                getUserInfo(that.userId).then((response) => {
                    that.userInfoObj = response;
                    that.userInfoObj.head_start = 0;
                })
            } else {
                that.haslogin = false;
            }

        },
        beforeUpload(videoUrl) {
            const isMp4 = videoUrl.type === 'video/mp4';
            if (!isMp4) {
                this.$message.error('只能上传mp4格式的视频');
            }
            return isMp4;
        },
        handleClose(done) {
            if (this.loading) {
                return;
            }
            this.$confirm('确定要关闭表单吗？')
                .then(_ => {
                    this.loading = true;
                    this.timer = setTimeout(() => {
                        done();
                        // 动画关闭需要一定的时间
                        setTimeout(() => {
                            this.loading = false;
                        }, 400);
                    }, 2000);
                })
                .catch(_ => { });
        },
        cancelForm() {
            this.loading = false;
            this.dialog = false;
            clearTimeout(this.timer);
        }
    },
    components: { //定义组件
        'wbc-nav': header,
        'social': fanList
    },
    watch: {
        // 如果路由有变化，会再次执行该方法
        '$route': 'routeChange'
    },
    created() { //生命周期函数
        this.routeChange();
        this.uploadURL = store.state.baseURL + 'user/uploadAvatar';
        this.uploadURL1 = store.state.baseURL + 'video/uploadVideo';
        this.uploadURL2 = store.state.baseURL + 'video/uploadImage';
    }
}
</script>

<style>
.userInfoBox .avatarlist {
    position: relative;
}

.video-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.video-uploader .el-upload:hover {
    border-color: #409EFF;
}

.video-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.video {
    width: 178px;
    height: 178px;
    display: block;
}

.avatar-uploader {
    display: inline-block;
    vertical-align: top;
}

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 50%;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 120px;
    height: 120px;
}

.avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
    position: absolute;
    top: 0;
    left: 0;
}

.avatar {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    display: block;
    object-fit: cover;
}

.gotoEdit {
    font-size: 15px;
    float: right;
    cursor: pointer;
    color: #999;
}

.gotoEdit:hover {
    color: #333;
}

/*个人设置*/
.userInfoBox .leftTitle {
    display: inline-block;
    width: 100px;
    padding: 10px 0;
}

.userInfoBox .rightInner {
    display: inline-block;
    max-width: calc(100% - 140px);
    vertical-align: top;
}

.userInfoBox li {
    padding: 20px;
    border-bottom: 1px solid #ddd;
}

.userInfoBox li:last-child {
    border-bottom: 1px solid transparent;
}

.userInfoBox .el-input,
.userInfoBox .el-textarea {
    max-width: 300px;
    min-width: 100px;
}

.userInfoBox .el-input__inner {
    border-radius: 4px;
}

.userInfoBox .el-textarea {
    vertical-align: top;
}

.saveInfobtn {
    margin: 20px 0;
    text-align: center;
}

.saveInfobtn a {
    color: #fff;
    padding: 6px 20px;
    margin: 5px 10px;
    border-radius: 5px;
    font-size: 14px;
}

.userInfoBox .fa-asterisk {
    color: #DF2050;
}
</style>
