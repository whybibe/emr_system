<template>
  <div>
    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :xs="24" :sm="24">
        <el-card class="box-card">
          <el-descriptions class="margin-top" title="基础信息设置" :column="2" border>
            <template slot="extra">
              <el-button :disabled="$hasBP('bnt.sysUser.update') === false" type="primary" size="small" @click="updateUserInfo">保存/修改</el-button>
            </template>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                姓名
              </template>
              <el-input v-model="userInfo.name" placeholder="请输入姓名"></el-input>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                手机
              </template>
              <el-input v-model="userInfo.phone" placeholder="请输入手机号"></el-input>
            </el-descriptions-item>
            <el-descriptions-item :span="3">
              <template slot="label">
                <i class="el-icon-tickets"></i>
                头像
              </template>
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8160/admin/file/img/avter"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="userInfo.headUrl" :src="userInfo.headUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-descriptions-item>
          </el-descriptions>


          <el-descriptions style="margin-top: 20px" class="margin-top" title="个人信息设置" :column="3" border>
            <template slot="extra">
              <el-button :disabled="$hasBP('bnt.doctor.update') === false" type="primary" size="small" @click="submitDoctorInfo">保存/修改</el-button>
            </template>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                医师职称
              </template>
              <el-select v-model="doctorInfo.position" placeholder="选择职称" style="width: 200px" clearable>
                <el-option
                  v-for="(item,index) in patientEnums"
                  :key="index"
                  align="center"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                所属科室门诊
              </template>
              <el-cascader
                style="width: 400px"
                v-model="doctorInfo.departmentPatient"
                :options="options"
                :props="props"
                clearable></el-cascader>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                工作日期
              </template>
              <el-select v-model="doctorInfo.weekday" multiple placeholder="请选择">
                <el-option label="星期一" value="星期一"></el-option>
                <el-option label="星期二" value="星期二"></el-option>
                <el-option label="星期三" value="星期三"></el-option>
                <el-option label="星期四" value="星期四"></el-option>
                <el-option label="星期五" value="星期五"></el-option>
                <el-option label="星期六" value="星期六"></el-option>
                <el-option label="星期日" value="星期日"></el-option>
              </el-select>
            </el-descriptions-item>
            <el-descriptions-item :span="3">
              <template slot="label">
                <i class="el-icon-tickets"></i>
                主治简介
              </template>
              <el-input
                v-model="doctorInfo.introduction"
                type="textarea"
                :rows="4"
                placeholder="请输入内容">
              </el-input>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
  </div>

</template>

<script>

import fileApi from '@/api/file/file'
import userApi from '@/api/system/sysUser'
import doctorApi from "@/api/information/doctor";
import departmentApi from '@/api/hosp/department'

export default {
  //import 引入的组件需要注入到对象中才能使用
  data() {
    return {
      userInfo: {},
      patientEnums: [],

      props: {multiple: true},
      options: [],

      doctorInfo: {},

    }
  },
  watch: {},
  mounted() {
    this.getUserInfo()
    this.getPatientEnums()
    this.findDepPatient()
    this.infoDoctor()
  },

  methods: {
    getUserInfo() {
      userApi.getUserInfo().then(res => {
        this.userInfo = res.data
      })
    },
    updateUserInfo() {
      userApi.updateById(this.userInfo)
        .then(res => {
          this.$message({
            message: '信息已更新',
            type: 'success'
          });
          this.getUserInfo()
        })
    },
    getPatientEnums() {
      doctorApi.patientEnums().then(res => {
        console.log(res)
        this.patientEnums = res.data
      })
    },
    findDepPatient() {
      departmentApi.depPatient().then(res => {
        this.options = res.data
      })
    },
    infoDoctor() {
      doctorApi.infoDoctor().then(res => {
        console.log(res)
        if (res.data != null) {
          this.doctorInfo = res.data
        }
      })
    },
    submitDoctorInfo() {
      console.log(this.doctorInfo)
      if(this.doctorInfo.id != null) {
        // 更新
        this.update()
      } else {
        this.save()
      }
    },
    save() {
      doctorApi.saveDoctor(this.doctorInfo)
        .then(res => {
          if (res.code == 200) {
            this.$message({
              message: '信息保存成功',
              type: 'success'
            });
            this.infoDoctor()
          } else {
            this.$message.error("网路异常，操作失败！");
          }
        })
    },
    update() {
      doctorApi.updateDoctor(this.doctorInfo)
        .then(res => {
          if (res.code == 200) {
            this.$message({
              message: '信息修改成功',
              type: 'success'
            });
            this.infoDoctor()
          } else {
            this.$message.error("网路异常，操作失败！");
          }
        })
    },

    handleAvatarSuccess(res, file) {
      // 先删除原有的照片再赋值
      fileApi.delete({fileUrl: this.userInfo.headUrl})
      this.userInfo.headUrl = res.data
    },
    beforeAvatarUpload(file) {
      const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
  }
}
</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.box-card {
  width: 98%;
  margin-left: 1%;
  margin-top: 10px;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.tinymce-container {
  line-height: 29px;
}

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
