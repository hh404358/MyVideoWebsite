<template>
    <div>
        <el-row class="sharelistBox" >
        <el-col :span="24" class="s-item tcommonBox" v-for="(item, index) in videoList" :key="'video' + index">
            <span class="s-round-date">
                <span class="month" v-html="showInitDate(item.createTime, 'month') + '月'"></span>
                <span class="day" v-html="showInitDate(item.createTime, 'date')"></span>
            </span>
            <header>
                <h1 >
                    <a :href="'#/DetailVideo?aid=' + item.videoId">
                        {{ item.videoTitle }}</a>
                   
                </h1>
                <h2>
                    
                    <i class="fa fa-fw fa-user"></i>{{item.userName}}发表于
                    <i class="fa fa-fw fa-history"></i><span>
                    {{showInitDate(item.createTime,'all')}}</span> •
                    <i class="fa fa-fw fa-eye"></i>{{ item.viewNum }} 次围观 •
                    <i class="fa fa-fw fa-comment"></i>{{ item.commentNum }}条评论•
                    <i class="fa fa-fw fa-thumbs-up"></i>{{ item.thumbupNum }}赞•


                </h2>
                <div class="ui label">
                    <a :href="'#/Share?classId=' + item.typeName">{{ item.typeName}}</a>
                </div>
            </header>
            <el-card class="video-content">
               <el-row>

                <el-col :span="24">
                    <p style="text-indent:2em;">
                    {{ item.video }}
                </p>
                </el-col>
               </el-row>
                <p style="max-height:300px;overflow:hidden;text-align:center;">
                    <img :src="item.thunmbnailUrl" alt="" class="maxW">
                </p>
            </el-card>
            <div class="viewdetail">
                <a class="tcolors-bg" :href="'#/DetailVideo?aid=' + item.id" target="_blank">
                    快来看看吧 >>
                </a>
            </div>

        </el-col>
        <el-col class="viewmore">
            <a v-show="hasMore" class="tcolors-bg" href="javascript:void(0);" @click="addMoreFun">点击加载更多</a>
            <a v-show="!hasMore" class="tcolors-bg" href="javascript:void(0);">暂无更多数据</a>
        </el-col>
    </el-row>
    </div>
  
</template>

<script>
import { initDate } from '../utils/server.js'
import { videoList} from '../api/video'
import { ElIcon } from 'element-ui';
export default {
    name: 'Share',
    data() { //选项 / 数据
        return {
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                videoTypeId: 0
            },
            videoList: [],
            hasMore: true,
           
        }
    },

    methods: { //事件处理器

        showInitDate: function (oldDate, full) {
            return initDate(oldDate, full)
        },
        getList() {
            videoList(this.queryParams).then((response) => {
                this.videoList = this.videoList.concat(response.rows)
                if (response.total <= this.videoList.length) {
                    this.hasMore = false
                } else {
                    this.hasMore = true
                    this.queryParams.pageNum++
                }
            })
        },
        
        showSearchShowList: function (initData) {//展示数据
            if (initData) {
                this.videoList = []
            }
            this.getList()
        },
        addMoreFun: function () {//查看更多
            this.showSearchShowList(false);
        },
        routeChange: function () {
            var that = this;
            this.queryParams.videoTypeId = (that.$route.query.classId == undefined ? 0 : parseInt(that.$route.query.classId));//获取传参的classId
            this.showSearchShowList(true);
        }
    },
    components: { //定义组件
        ElIcon
    },
    watch: {
        // 如果路由有变化，会再次执行该方法
        '$route': 'routeChange',
        '$store.state.keywords': 'routeChange'
    },
    created() { //生命周期函数
        // console.log(this.$route);
        var that = this;
       
        that.routeChange();
        this.getList();
    }
}
</script>

<style>
/* 分类标签 */
.box-card {
    display: block;
    margin-right: 160px;
    margin-left: 30px;
    border-bottom: 100px;
}

.item {
    display: block;
    margin-top: 20px;
    margin-right: 60px;
    height: 60px;
    width: 100px;
}
/*分享标题*/
.shareTitle {
    margin-bottom: 40px;
    position: relative;
    border-radius: 5px;
    background: #fff;
    padding: 15px;
}

.shareclassTwo {
    width: 100%;
}

.shareclassTwo li {
    display: inline-block;
}

.shareclassTwo li a {
    display: inline-block;
    padding: 3px 7px;
    margin: 5px 10px;
    color: #fff;
    border-radius: 4px;
    background: #64609E;
    border: 1px solid #64609E;
    transition: transform 0.2s linear;
    -webkit-transition: transform 0.2s linear;
}

.shareclassTwo li a:hover {
    transform: translate(0, -3px);
    -webkit-transform: translate(0, -3px);
}

.shareclassTwo li a.active {
    background: #fff;
    color: #64609E;

}

/*视频列表*/
.sharelistBox {
    transition: all 0.5s ease-out;
    font-size: 15px;
}


/*.sharelistBox .viewmore a:hover,.s-item .viewdetail a:hover{
        background: #48456C;
    }*/
</style>./videolist.vue/index.js
