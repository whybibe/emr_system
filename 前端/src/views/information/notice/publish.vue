<template>
  <div class="app-container">
    <div style="padding: 20px 20px">
      <el-link :underline="false" type="primary" style="font-weight: bold" @click="$router.push('/information/notice')">
        <i class="el-icon-caret-left" style="font-size: 14px;">
          返回上一级</i>
      </el-link>

      <el-row style="margin-top: 20px">
        <el-descriptions class="margin-top" :column="2" border>
          <template slot="extra">
            <el-button :disabled="$hasBP('bnt.notice.save') === false" type="primary" size="small" @click="submitData">一键发布</el-button>
          </template>
          <el-descriptions-item>
            <template slot="label">
              公告标题
            </template>
            <el-input
              v-model="noticeData.title"
              placeholder="请输入公告标题"
              clearable>
            </el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              公告类型
            </template>
            <el-select v-model="noticeData.noticeType" clearable placeholder="请选择">
              <el-option
                v-for="item in noticeTypeList"
                :key="item.value"
                align="center"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-descriptions-item>
        </el-descriptions>
        <tinymce v-model="noticeData.content" :height="400"/>
      </el-row>

    </div>
  </div>
</template>
<script>

import noticeApi from "@/api/information/notice";
import Tinymce from "@/components/Tinymce";

export default {
  components: {Tinymce},
  data() {
    return {
      noticeData: {},
      noticeTypeList: [],
    };
  },

  created() {
    this.noticeData = {}
    this.getNoticeType()
  },

  methods: {

    getNoticeType(){
      noticeApi.findNoticeType().then(res => {
        this.noticeTypeList = res.data
      })
    },
    submitData(){
      noticeApi.saveNotice(this.noticeData).then(res => {
        this.$message({
          message: '公告发布成功',
          type: 'success'
        });

        this.noticeData = {}
        this.$router.push('/message/notice')

      })
    },
  }
};
</script>
