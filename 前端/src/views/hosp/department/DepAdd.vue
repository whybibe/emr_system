<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="true"
    :title="title"
    :type="type"
    :visible.sync="isVisible"
    width="600px"
    top="150px"
  >
    <el-form
      ref="form"
      :model="depInfo"
      :rules="rules"
      label-position="right"
      label-width="140px"
    >
      <el-form-item label="科室名字" prop="depName">
        <el-input :disabled="isDisabled" v-model="depInfo.depName" placeholder="请输入科室的名字"/>
      </el-form-item>
      <el-form-item label="科室编码（唯一）" prop="depCode">
        <el-input :disabled="isDisabled" v-model="depInfo.depCode" placeholder="请输入科室编码（唯一）"/>
      </el-form-item>
      <el-form-item label="门诊名" prop="patientName">
        <el-input v-model="depInfo.patientName" placeholder="门诊名"/>
      </el-form-item>
      <el-form-item label="门诊编号" prop="patientCode">
        <el-input v-model="depInfo.patientCode"  placeholder="门诊编号（唯一）"/>
      </el-form-item>
      <el-form-item label="门诊介绍" prop="intro">
        <el-input
          type="textarea"
          :rows="4"
          v-model="depInfo.intro"
          placeholder="门诊介绍"/>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button plain type="warning" @click="isVisible = false">取消</el-button>
      <el-button plain type="primary" @click="submitData">确定</el-button>
    </div>
  </el-dialog>
</template>
<script>

import departmentApi from '@/api/hosp/department'

export default {
  components: {},
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
      depInfo: {},

      //表单校验规则
      rules: {
        depName: [
          { required: true, message: '请输入科室名称', trigger: 'blur' }
        ],
        depCode: [
          { required: true, message: '请输入科室编码', trigger: 'blur' }
        ],
        patientName: [
          { required: true, message: '请输入门诊名称', trigger: 'blur' }
        ],
        patientCode: [
          { required: true, message: '请输入门诊编码', trigger: 'blur' }
        ],
        intro: [
          { required: true, message: '请输入门诊介绍', trigger: 'blur' }
        ],
      },
      isDisabled : false
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
    setDep(data) {
      if (data != null) {
        this.depInfo.depName = data.depName
        this.depInfo.depCode = data.depCode
        this.isDisabled = true
      } else {
        this.isDisabled = false
      }
    },
    close() {
      this.$emit('close')
    },
    reset() {
      // 先清除校验，再清除表单，不然有奇怪的bug
      this.$refs.form.clearValidate()
      this.$refs.form.resetFields()
      this.depInfo = {}
    },
    //提交表单数据
    submitData() {
      this.$refs.form.validate(valid => {
        if (valid) {
          departmentApi.saveDep(this.depInfo).then(res => {
            if (res.code = 200) {
              this.isVisible = false
              this.$message({
                message: '添加成功！！！',
                type: 'success'
              })
              this.depInfo = {}
              this.$emit('success')
            } else {
              this.$message.error('添加失败！！！')
            }
          })
        } else {
          return false
        }
      })
    },
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
