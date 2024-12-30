<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="true"
    :title="title"
    :type="type"
    :visible.sync="isVisible"
    width="800px"
    top="150px"
  >

    <el-descriptions class="margin-top" :column="2" border>
      <el-descriptions-item>
        <template slot="label">
          公告标题
        </template>
        <el-input
          v-model="hospNotice.title"
          placeholder="请输入公告标题"
          clearable>
        </el-input>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          公告类型
        </template>
        <el-select v-model="hospNotice.noticeType" clearable placeholder="请选择">
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
    <tinymce v-model="hospNotice.content" :height="400"/>

    <div slot="footer" class="dialog-footer">
      <el-button plain type="warning" @click="isVisible = false">取消</el-button>
      <el-button plain type="primary" @click="submitData">确定</el-button>
    </div>
  </el-dialog>
</template>
<script>

import noticeApi from '@/api/information/notice'
import Tinymce from '@/components/Tinymce'

export default {
  components: { Tinymce },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    },
    type: {
      type: String,
      default: 'add'
    }
  },
  data() {
    //这里存放数据
    return {
      hospNotice: {},
      noticeTypeList: [],
    };
  },
  //计算属性 类似于 data 概念
  computed: {
    isVisible: {
      get() {
        return this.dialogVisible
      },
      set() {
        this.close()
        this.reset()
      }
    },
    title() {
      return this.type === 'add'
        ? "添加"
        : "修改"
    }
  },
  //监控 data 中的数据变化
  watch: {},
  //方法集合
  methods: {
    setNotice(data) {
      this.hospNotice = data
    },
    close() {
      this.$emit('close')
    },
    reset() {
      // 先清除校验，再清除表单，不然有奇怪的bug
      this.hospNotice = {}
    },
    getNoticeType() {
      noticeApi.findNoticeType().then(res => {
        this.noticeTypeList = res.data
      })
    },
    //提交表单数据
    submitData() {
      noticeApi.updateNotice(this.hospNotice).then(res => {
        this.isVisible = false
        this.$message({
          message: '公告信息修改成功',
          type: 'success'
        });
        this.$emit('success')
      })
    },
  },
  //生命周期 - 创建完成（可以访问当前 this 实例）
  created() {

  },
  //生命周期 - 挂载完成（可以访问 DOM 元素）
  mounted() {
    this.getNoticeType()
  }
}
</script>
<style lang="scss" scoped>
//@import url(); 引入公共 css 类
</style>
