<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="true"
    title="公告详情"
    :type="type"
    :visible.sync="isVisible"
    width="800px"
    top="150px"
  >

    <template>
      <div :underline="false" style="text-align: center">
        <el-link :underline="false" type="primary" style="font-weight: bold; font-size: 20px">
          {{ hospNotice.title }}
        </el-link>
      </div>
      <br/>
      <div style="margin-top: 10px;margin-bottom: 5px; text-align: center">
                        <span style="font-size: 12px;color: darkgrey; margin-right: 5px">
                        作者：{{ hospNotice.author }}
                        </span>
        <span style="font-size: 12px;color: darkgrey; margin-right: 5px">
                        公告类型：
              <el-tag v-if="hospNotice.noticeType === 'PIN_TAI'">平台公告</el-tag>
              <el-tag type="success" v-if="hospNotice.noticeType === 'STOP'">停诊公告</el-tag>
                        </span>
        <span style="font-size: 12px;color: darkgrey; margin-right: 5px">
                        阅读量：{{ hospNotice.readingVolume }}
                        </span>
      </div>
      <!-- 公告内容 -->
      <div style="font-size: 15px;color: darkgrey; padding: 20px; text-align: center" v-html="hospNotice.content"></div>
    </template>
    <div slot="footer" class="dialog-footer">
      <el-button plain type="warning" @click="isVisible = false">取消</el-button>
      <el-button plain type="primary" @click="submitData">确定</el-button>
    </div>
  </el-dialog>
</template>
<script>

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
      hospNotice: {}
    }
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
        ? '添加'
        : '修改'
    }
  },
  //监控 data 中的数据变化
  watch: {},
  //方法集合
  methods: {
    setData(data) {
      this.hospNotice = data
    },
    close() {
      this.$emit('close')
    },
    reset() {
      // 先清除校验，再清除表单，不然有奇怪的bug
      this.hospNotice = {}
    },
    //提交表单数据
    submitData() {
      this.isVisible = false
      this.$emit('success')
    }
  },
  //生命周期 - 创建完成（可以访问当前 this 实例）
  created() {

  },
  //生命周期 - 挂载完成（可以访问 DOM 元素）
  mounted() {
  }
}
</script>
<style lang="scss" scoped>
//@import url(); 引入公共 css 类
</style>
