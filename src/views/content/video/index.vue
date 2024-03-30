<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
          <el-form-item label="视频标题" prop="videoTitle">
            <el-input v-model="queryParams.videoTitle" placeholder="请输入视频标题" clearable size="small" style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="类型" prop="videoTypeId">
            <el-select v-model="queryParams.videoTypeId" placeholder="请选择类型">
              <el-option v-for="category in categoryList" :label="category.typeName"
                :value="category.videoTypeId" />
            </el-select>
          </el-form-item>
          <el-form-item label="视频状态" prop="videoStateId">
            <el-select v-model="queryParams.videoStateId" placeholder="请选择视频状态">
              <el-option v-for="( s,index) in stateList" :key="index " :label="s" :value="index + 1" />
            </el-select>
          </el-form-item>
          <el-form-item label="视频简介" prop="videoInfo">
            <el-input v-model="queryParams.videoInfo" placeholder="请输入视频简介关键词" clearable size="small"
              style="width: 240px" @keyup.enter.native="handleQuery" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="handleDelete">删除</el-button>
          </el-col>

        </el-row>

        <el-table :data="videoList" style="width: 100%" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="videoTitle" label="标题" align="center" />
          <el-table-column prop="typeName" label="类型" align="center" />
          <el-table-column prop="videoInfo" label="摘要" align="center" />
          <el-table-column prop="createTime" label="创建时间" align="center" />
          <el-table-column prop="createUser" label="创建用户" align="center" />
          <el-table-column prop="commentNum" label="评论数量" align="center" />
          <el-table-column prop="viewNum" label="观看数量" align="center" />
          <el-table-column prop="thumbupNum" label="点赞数量" align="center" />


          <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-pagination :page-size.sync="queryParams.pageSize" layout="total, sizes, prev, pager, next, jumper"
      :total="total" :page-sizes="[10, 20, 30, 40]" :current-page.sync="queryParams.pageNum" @current-change="getList"
      @size-change="getList" />

  </div>
</template>

<script>
// import { getToken } from '@/utils/auth'
import {
  listVideo,
  delVideo
}
  from '@/api/content/video'
import { listAllCategory } from '@/api/content/type'
export default {
  name: 'Video',
  data() {
    return {
      category: [],
      stateList: [
        '认证中',
        '已认证',
        '认证失败',
        '已上架',
        '已下架',
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        videoTitle: '',
        videoInfo: '',
        videoTypeId: ''

      },
      categoryList: [],
      videoTitle: '',
      // 是否显示弹出层
      open: false,
      // 总条数
      total: 0,
      videoList: [],
      showSearch: true
    }
  },
  watch: {},
  created() {
    this.getList()
    this.getCategory()
  },
  methods: {
    getCategory() {
      listAllCategory().then((response) => {
        this.categoryList = response
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
  
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true
      listVideo(this.queryParams).then((response) => {
        this.videoList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$router.push('/add?id=' + parseInt(row.videoId))
    },
    /** 新增用户 */
    handleAdd() {
      this.$router.push('/add')
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.videoId || this.ids
      this.$modal.confirm('是否确认删除' + row.videoTitle ).then(function () {
        return delVideo(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => { })
    }
  }
}
</script>
@/api/content/video