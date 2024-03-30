<!-- 详情 -->
<template>
    <div>
        <nav></nav>
        <div class="container" id="detail">
            <el-row :gutter="30"style="transition:all .5s ease-out;margin-bottom:30px;">
                    <videoDetail></videoDetail>
            </el-row>
        </div>
    </div>
</template>

<script >
import header from '../components/header.vue'
import rightlist from '../components/rightlist.vue'
import videolist from '../components/videolist.vue'
import videoDetail from '../components/videoDetail.vue'
import recommendVideo from '../components/recommendVideo.vue'
export default {
    name: 'DetailShare',
    data() { //选项 / 数据
        return {

        }
    },
    methods: { //事件处理器
       
    },
    components: { //定义组件
        'nav': header,
        'videoDetail': videoDetail,
        'rightlist': rightlist,
        'videolist': videolist,
        'recommendVideo': recommendVideo
    },
    created() { //生命周期函数
        
    },
    mounted() {
        var anchor = document.querySelector("#detail");
        // console.log(anchor,anchor.offsetTop);
        var top = anchor.offsetTop - 60;
        document.body.scrollTop = top;
        // Firefox
        document.documentElement.scrollTop = top;
        // Safari
        window.pageYOffset = top;
        
   
  
    }
}
</script>

<style></style>
