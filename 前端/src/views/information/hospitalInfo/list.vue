<template>
  <div>
    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :xs="24" :sm="24">
        <el-card class="box-card">
          <el-descriptions class="margin-top" :column="3" border>
            <template slot="extra">
              <el-button @click="saveOrUpdateInfo" type="primary" size="small">保存医院信息</el-button>
            </template>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                医院名
              </template>
              <el-input
                v-model="hospitalInfo.hospName"
                placeholder="请输入医院名"
              ></el-input>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                医院编号
              </template>
              <el-input
                v-model="hospitalInfo.hospCode"
                placeholder="请输入医院编号"
              ></el-input>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                医院联系人
              </template>
              <el-input
                v-model="hospitalInfo.contactsName"
                placeholder="请输入医院联系人"
              ></el-input>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                联系人手机号
              </template>
              <el-input
                v-model="hospitalInfo.contactsPhone"
                placeholder="请输入联系人手机号"
              ></el-input>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                所处地区邮编
              </template>
              <el-input
                v-model="hospitalInfo.zipCode"
                placeholder="请输入所处地区邮编"
              ></el-input>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                医院地址
              </template>
              <el-input
                v-model="hospitalInfo.address"
                placeholder="请输入医院地址"
              ></el-input>
            </el-descriptions-item>
            <el-descriptions-item span="3">
              <template slot="label">
                <i class="el-icon-office-building"></i>
                医院logo
              </template>
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8800/admin/file/img/avter"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="imageUrl" :src="imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-descriptions-item>
            <el-descriptions-item span="3">
              <template slot="label">
                <i class="el-icon-office-building"></i>
                医院路线
              </template>
              <tinymce
                v-model="hospitalInfo.route"
                :height="300"
              />
            </el-descriptions-item>
            <el-descriptions-item span="3">
              <template slot="label">
                <i class="el-icon-office-building"></i>
                医院简介
              </template>
              <tinymce
                v-model="hospitalInfo.hospContent"
                :height="300"
              />
            </el-descriptions-item>
            <el-descriptions-item span="3">
              <template slot="label">
                <i class="el-icon-office-building"></i>
                医院电子病例管理规则
              </template>
              <tinymce
                v-model="hospitalInfo.manageRule"
                :height="300"
              />
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script >

import Tinymce from '@/components/Tinymce'
import hospitalInfoApi from '@/api/information/hospitalInfo'

export default {
  components: { Tinymce },
  data() {
    return {
      hospitalInfo: {},
      imageUrl: '',
    }
  },
  mounted() {
    this.findInfo()
  },

  methods: {
    findInfo(){
      hospitalInfoApi.findList().then(res => {
        this.hospitalInfo = res.data
        if (res.data != null) {
          this.imageUrl = this.hospitalInfo.logoUrl
        }

      })
    },
    handleAvatarSuccess(file){
      this.imageUrl = file.data
      this.hospitalInfo.logoUrl = file.data
    },
    beforeAvatarUpload(file){
      const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png')
      const isLt2M = file.size / 1024 / 1024 < 10

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    saveOrUpdateInfo() {
      if (this.hospitalInfo.id === null) {
        console.log("保存")
        console.log(this.hospitalInfo)

        // 保存
        hospitalInfoApi.saveHospital(this.hospitalInfo)
        .then(res => {
          this.$message({
            message: '医院信息保存成功',
            type: 'success'
          });
        })
      } else {
        console.log("更新")
        console.log(this.hospitalInfo)
        // 更新
        hospitalInfoApi.updateHospital(this.hospitalInfo)
        .then(res => {
          this.$message({
            message: '医院信息保存成功',
            type: 'success'
          });
        })
      }
    }
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
