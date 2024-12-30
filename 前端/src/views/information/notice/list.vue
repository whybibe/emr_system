<template>
  <div class="app-container">
    <div class="search-div">

      <el-row>
        <span style="font-size: 15px; margin-top: 5px">公告标题：</span>
        <el-input
          v-model="queryParams.title"
          placeholder="请输入公告标题"
          clearable
          size="small"
          style="width: 200px"
        >
        </el-input>
        <span style="font-size: 15px; margin-top: 5px; margin-left: 15px">公告类型：</span>
        <el-select v-model="queryParams.noticeType" size="small" placeholder="选择公告类型" style="width: 200px" clearable>
          <el-option
            v-for="(item,index) in noticeTypeList"
            :key="index"
            align="center"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-button
          :disabled="$hasBP('bnt.notice.list') === false"
          type="primary"
          icon="el-icon-search"
          size="mini"
          style="margin-left: 15px"
          @click="findList(1)"
        >搜索
        </el-button>
        <el-button
          @click="clearSubmitData"
          icon="el-icon-refresh"
          size="mini"
        >重置
        </el-button>
        <el-button
          :disabled="$hasBP('bnt.notice.save') === false"
          type="primary"
          icon="el-icon-refresh"
          size="mini"
          @click="publishNotice"
        >发布公告
        </el-button>
      </el-row>
      <el-row style="margin-top: 10px">

        <el-table
          v-loading="loading"
          ref="multipleTable"
          :data="hospNoticeData.records"
          style="width: 100%; margin-top: 15px"
          border
        >
          <el-table-column
            label="序号"
            type="index"
            align="center"
            width="60"
          >
          </el-table-column>
          <el-table-column prop="author" label="发布人姓名" align="center" width="200"/>
          <el-table-column prop="title" align="center" label="公告标题" width="240"/>
          <el-table-column prop="noticeType" align="center" label="公告类型" width="200">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.noticeType === 'PIN_TAI'">平台公告</el-tag>
              <el-tag type="success" v-if="scope.row.noticeType === 'STOP'">停诊公告</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="readingVolume" align="center" label="阅读量" width="200"/>
          <el-table-column prop="totalScore" align="center" label="操作">
            <template slot-scope="scope">
              <el-button
                :disabled="$hasBP('bnt.notice.update') === false"
                icon="el-icon-edit"
                type="primary"
                size="small"
                @click="editNotice(scope.row)"
              >编辑
              </el-button>
              <el-button
                :disabled="$hasBP('bnt.notice.list') === false"
                icon="el-icon-view"
                type="primary"
                size="small"
                @click="details(scope.row)"
              >详情
              </el-button>
              <el-button
                :disabled="$hasBP('bnt.notice.delete') === false"
                icon="el-icon-delete-solid"
                type="danger"
                size="small"
                @click = "deleteNotice(scope.row.id)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>


        <el-pagination
          @size-change="handleSizeChange"
          @current-change="findList"
          :current-page="this.queryParams.page"
          :page-sizes="[1,5, 10, 15, 20, 30, 40, 50, 100]"
          :page-size="this.queryParams.limit"
          layout="total, sizes, prev, pager, next, jumper"
          :total="hospNoticeData.total"
          style="padding: 30px 0; text-align: center"
        >
        </el-pagination>
      </el-row>
    </div>

    <edit
      ref="edit"
      :dialog-visible="dialog.isVisible"
      :type="dialog.type"
      @close="editClose"
      @success="editSuccess"/>

    <notice_details
      ref="det"
      :dialog-visible="dialogDetails.isVisible"
      :type="dialogDetails.type"
      @close="detailsClose"
      @success="detailsSuccess"/>

  </div>
</template>

<script>

import noticeApi from '@/api/information/notice'
import Edit from '@/views/information/notice/edit'
import Notice_details from '@/views/information/notice/notice_details'

export default {
  components: { Notice_details, Edit },
  data() {
    return {
      queryParams: {
        page: 1,
        limit: 10,
        title: '',
        noticeType: ''
      },
      noticeTypeList: [],

      hospNoticeData: {},
      loading: false,

      dialog: {
        isVisible: false,
        type: 'add'
      },
      dialogDetails: {
        isVisible: false,
        type: 'add'
      },
    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
  },

  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
    this.findList()
    this.getNoticeType()
  },

  methods: {
    findList(page = 1) {
      this.loading = true
      if (page != null) {
        this.queryParams.page = page
      }
      noticeApi.findHospNoticeList(this.queryParams)
        .then(res => {
          this.loading = false
          this.hospNoticeData = res.data
        })
    },
    clearSubmitData() {
      this.queryParams = {
        page: 1,
        limit: 10,
        title: '',
        noticeType: ''
      }
    },
    publishNotice() {
      this.$router.push('/information/publish')
    },
    handleSizeChange(size) {
      this.queryParams.limit = size
      this.findList()
    },
    getNoticeType() {
      noticeApi.findNoticeType().then(res => {
        this.noticeTypeList = res.data
      })
    },
    editClose() {
      this.dialog.isVisible = false
    },
    editSuccess() {
      this.findList(1)
    },
    editNotice(row){
      this.dialog.type = 'update'
      this.dialog.isVisible = true
      this.$refs.edit.setNotice(row)
    },
    detailsClose() {
      this.dialogDetails.isVisible = false
    },
    detailsSuccess() {
      this.findList(1)
    },
    details(row){
      this.dialogDetails.type = 'update'
      this.dialogDetails.isVisible = true
      this.$refs.det.setData(row)
    },
    deleteNotice(id) {
      this.$confirm('确定要删除该公告吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        noticeApi.deleteNotice(id).then(response => {
          if (response.code === 200) {
            this.$message({
              message: '删除成功！！！',
              type: 'success'
            })
            this.findList(1)
          } else {
            this.$message.error('删除失败！！！')
          }
        })
      }).catch(() => {
      })
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
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
