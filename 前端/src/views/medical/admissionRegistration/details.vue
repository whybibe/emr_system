<template>
  <div class="home page-component">
    <el-card class="box-card">
      <el-descriptions class="margin-top">
      </el-descriptions>
      <!-- 个人基本信息 -->
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px">个人基础信息<span style="font-size: 12px; padding-left: 5px; font-weight: normal; color: darkgrey">带星号（*）是必填项</span></div>
      <el-descriptions class="margin-top" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 姓名
          </template>
          {{admissionRegistrationData.userName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            曾用名
          </template>
          {{admissionRegistrationData.formerName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 性别
          </template>
          {{admissionRegistrationData.gender}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 民族
          </template>
          {{admissionRegistrationData.ethnicity}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 出生日期
          </template>
          {{admissionRegistrationData.birthDay}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 年龄
          </template>
          {{admissionRegistrationData.age}} 岁
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 体重
          </template>
          {{admissionRegistrationData.weight}} kg
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 身高
          </template>
          {{admissionRegistrationData.height}} cm
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 婚姻状况
          </template>
          {{admissionRegistrationData.maritalStatus}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 证件类型
          </template>
          {{admissionRegistrationData.identificationType}}
        </el-descriptions-item>
        <el-descriptions-item :span="2">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 证件号
          </template>
          {{admissionRegistrationData.identificationNumber}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 国籍
          </template>
          {{admissionRegistrationData.nationality}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 籍贯
          </template>
          {{admissionRegistrationData.nativePlace}}
        </el-descriptions-item>
        <el-descriptions-item :span="2">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 出生地址
          </template>
          {{admissionRegistrationData.placeBirth}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            文化程度
          </template>
          {{admissionRegistrationData.educationLevel}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 联系电话
          </template>
          {{admissionRegistrationData.contactNumber}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            邮箱
          </template>
          {{admissionRegistrationData.email}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            宗教信仰
          </template>
          {{admissionRegistrationData.religiousBelie}}
        </el-descriptions-item>

      </el-descriptions>

      <!-- 医保信息 -->
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px; margin-top: 20px">医保信息<span style="font-size: 12px; padding-left: 5px; font-weight: normal; color: darkgrey">带星号（*）是必填项</span></div>
      <el-descriptions class="margin-top" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 医保类型
          </template>
          {{admissionRegistrationData.insuranceType}}
        </el-descriptions-item>
        <el-descriptions-item :span="3">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 医保号
          </template>
          {{admissionRegistrationData.insuranceNumber}}
        </el-descriptions-item>
        <el-descriptions-item :span="2">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 医疗证号
          </template>
          {{admissionRegistrationData.medicalCertificateNumber}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            血型
          </template>
          {{admissionRegistrationData.bloodType}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            是否RH型
          </template>
          {{admissionRegistrationData.rhType}}
        </el-descriptions-item>
      </el-descriptions>

      <!-- 住院登记信息 -->
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px; margin-top: 20px">住院登记信息<span style="font-size: 12px; padding-left: 5px; font-weight: normal; color: darkgrey">带星号（*）是必填项</span></div>
      <el-descriptions class="margin-top" :column="3" border>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 科室
          </template>
          {{admissionRegistrationData.department}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 门诊
          </template>
          {{admissionRegistrationData.patient}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 医师职位
          </template>
          {{admissionRegistrationData.doctorPosition}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 医师
          </template>
          {{admissionRegistrationData.doctorName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 病房号
          </template>
          {{admissionRegistrationData.wardNumber}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 床号
          </template>
          {{admissionRegistrationData.bedNumber}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 入院登记日期
          </template>
          {{admissionRegistrationData.registrationDate}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 登记人
          </template>
          {{admissionRegistrationData.registrar}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 登记人职位
          </template>
          {{admissionRegistrationData.registrarPosition}}
        </el-descriptions-item>

      </el-descriptions>

      <!-- 过敏史 -->
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px; margin-top: 20px">过敏史<span style="font-size: 12px; padding-left: 5px; font-weight: normal; color: darkgrey">带星号（*）是必填项</span></div>
      <el-descriptions class="margin-top" :column="3" border>
        <el-descriptions-item :span="3">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 过敏史
          </template>
          <el-form :inline="true" v-for="(item, index) in admissionRegistrationData.allergyHistory"  class="demo-form-inline">
            <el-form-item label="过敏史">
              <el-input disabled v-model="item.title" size="small" style="width: 250px" placeholder="过敏史"></el-input>
            </el-form-item>
            <el-form-item label="内容">
              <el-input
                disabled
                type="textarea"
                style="width: 600px"
                :rows="2"
                placeholder="请输入内容"
                v-model="item.content">
              </el-input>
            </el-form-item>
          </el-form>
        </el-descriptions-item>
      </el-descriptions>


      <!-- 工作情况 -->
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px; margin-top: 20px">工作情况<span style="font-size: 12px; padding-left: 5px; font-weight: normal; color: darkgrey">带星号（*）是必填项</span></div>
      <el-descriptions class="margin-top" :column="3" border>
        <el-descriptions-item>
          <template slot="label">
            职业
          </template>
          {{admissionRegistrationData.occupation}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 是否本市
          </template>
          {{admissionRegistrationData.isLocal}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            户口邮编
          </template>
          {{admissionRegistrationData.registeredPostcode}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 户口地址
          </template>
          {{admissionRegistrationData.registeredAddress}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 现住址
          </template>
          {{admissionRegistrationData.homeAddress}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 现住邮编
          </template>
          {{admissionRegistrationData.homePostcode}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 工作单位
          </template>
          {{admissionRegistrationData.employer}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 工作地址
          </template>
          {{admissionRegistrationData.workAddress}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 单位电话
          </template>
          {{admissionRegistrationData.workPhone}}
        </el-descriptions-item>
        <el-descriptions-item :span="3">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 紧急联系人
          </template>
          <el-form :inline="true" v-for="(item, index) in admissionRegistrationData.emergencyContactGroup" :key="index" class="demo-form-inline">
            <el-form-item label="紧急联系人">
              <el-input disabled v-model="item.emergencyContact" size="small" style="width: 200px" placeholder="紧急联系人"></el-input>
            </el-form-item>
            <el-form-item label="关系">
              <el-input disabled v-model="item.relationship" size="small" style="width: 200px" placeholder="关系"></el-input>
            </el-form-item>
            <el-form-item label="联系人电话">
              <el-input disabled v-model="item.contactPhone" size="small" style="width: 200px" placeholder="联系人电话"></el-input>
            </el-form-item>
          </el-form>
        </el-descriptions-item>

      </el-descriptions>
    </el-card>
    <el-card v-if="dischargeRegistration != null" class="box-card">
      <el-descriptions class="margin-top">
      </el-descriptions>
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px">出院记录</div>
      <el-descriptions class="margin-top" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            出院登记日期
          </template>
          {{dischargeRegistration.dischargeDate}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            登记人
          </template>
          {{dischargeRegistration.registrarName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            登记人职位
          </template>
          {{dischargeRegistration.registrarPosition}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            费用计算
          </template>
          {{dischargeRegistration.costCalculation}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            是否结算
          </template>
          {{dischargeRegistration.isSettled}}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
    <el-card v-if="deathRegistration != null" class="box-card">
      <el-descriptions class="margin-top">
      </el-descriptions>
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px">死亡登记</div>
      <el-descriptions class="margin-top" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            死亡登记日期
          </template>
          {{deathRegistration.deathDate}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            死亡详细时间
          </template>
          {{deathRegistration.deathTime}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            登记人
          </template>
          {{deathRegistration.registrarName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            登记人职位
          </template>
          {{deathRegistration.registrarPosition}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            费用计算
          </template>
          {{deathRegistration.costCalculation}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            是否结算
          </template>
          {{deathRegistration.isSettled}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            确认医生
          </template>
          {{deathRegistration.confirmingDoctorName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            确认医生职位
          </template>
          {{deathRegistration.confirmingDoctorPosition}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            死亡原因
          </template>
          {{deathRegistration.causeOfDeath}}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
    <el-card v-if="admissionRegistrationDetailsList.length > 0" class="box-card">
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px">病案会诊详情</div>
      <el-timeline>
        <el-timeline-item v-for="(item, index) in admissionRegistrationDetailsList" :timestamp="item.contentTime" :key="index" placement="top">
          <el-card>
            <h4>会诊类型：{{item.admissionType}}</h4>
            <h4>会诊医生名称：{{item.userName}}</h4>
            <p>疾病名称：{{item.diseaseName}}</p>
            <p>会诊内容：{{item.content}}</p>
            <p v-if="item.admissionType == '手术记录'">手术名称：{{item.operationName}}</p>
            <p v-if="item.admissionType == '手术记录'">手术编号：{{item.operationRoomNumber}}</p>
            <p v-if="item.admissionType == '手术医生'">手术医生：{{item.operationDoctor}}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>

    </el-card>
  </div>
</template>
<script>

import admissionRegistrationApi from '@/api/medical/admissionRegistration'

export default {
  //服务端渲染异步，显示医院列表
  //asyncData：渲染组件之前异步获取数据
  data() {
    return {
      id: null,
      admissionRegistrationData: {
        emergencyContactGroup: []
      },
      admissionRegistrationDetailsList: [],
      dischargeRegistration: {},
      deathRegistration: {}
    };
  },
  mounted() {
    this.id = this.$route.query.id
    this.findInfo(this.id)
  },
  methods: {
    findInfo(id){
      admissionRegistrationApi.getById(id)
        .then(res => {
          this.admissionRegistrationData = res.data.admissionRegistration
          this.admissionRegistrationDetailsList  = res.data.admissionRegistrationDetailsList
          this.dischargeRegistration = res.data.dischargeRegistration
          this.deathRegistration = res.data.deathRegistration
        })
    }
  },
};
</script>

