<!-- 首页 -->
<template>
    <div>
        <navbar></navbar>
        <div class="container">
            <el-row>
                <el-carousel :interval="4000" type="card" height="400px">
                    <el-carousel-item v-for="item in topVideos" :key="item.thumbnailUrl" class="carousel-item">
                        <div class="carousel-item-image">
                            <a :href="'#/DetailVideo?aid=' + item.videoId" target="_blank"><img :src="item.thumbnailUrl"
                                    alt="图片">{{ item.videoTitle }}</a>
                        </div>

                    </el-carousel-item>
                </el-carousel>
            </el-row>
            <el-row :gutter="30">
                <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
                    <videolist></videolist>
                </el-col>
                <el-col :sm="24" :md="8">
                    <rightlist></rightlist>
                </el-col>
            </el-row>
        </div>
    </div>



</template>

<script>
import { getIndexRecommendVideo, updateViewCount } from '../api/video'
import header from '../components/header.vue'
import videolist from '../components/videolist.vue'
import rightlist from '../components/rightlist.vue'
export default {
    name: 'Home',
    data() { //选项 / 数据
        return {
            count: 0,
            topVideos: "",
            videoList: "",

        }
    },
    methods: { //事件处理器

        getTopVideo: function () {//獲取top3視頻
            getIndexRecommendVideo().then((response) => {
                this.topVideos = response;

            })

        },
        updateViewCount: function (videoId) {
            updateViewCount(videoId).then((response) => {
                console.log("新增浏览量" + count++);
            })
        }
    },
    components: { //定义组件
        'navbar': header,
        'videolist': videolist,
        'rightlist': rightlist,
    },
    created() { //生命周期函数
        this.getTopVideo();
    }
}
</script>

<style>
.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 1;
    line-height: 400px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}


.carousel-item-image img {
    width: 100%;
    height: 100%;
    object-fit: contain;
}
</style>../components/videoList.vue
