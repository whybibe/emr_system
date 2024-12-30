<template>
  <div>
    <!-- 搜索条件 -->
    <el-card class="box-card">
      <el-descriptions class="margin-top" border>
        <template slot="extra">
          <el-button type="primary" size="small" @click="saveAdmissionRegistration">确认登记</el-button>
        </template>
      </el-descriptions>

      <!-- 个人基本信息 -->
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px">个人基础信息<span style="font-size: 12px; padding-left: 5px; font-weight: normal; color: darkgrey">带星号（*）是必填项</span></div>
      <el-descriptions class="margin-top" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 姓名
          </template>
          <el-input size="small" v-model="admissionRegistration.userName" class="custom-input" placeholder="姓名" clearable style="width: 170px"></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            曾用名
          </template>
          <el-input size="small" v-model="admissionRegistration.formerName" class="custom-input" placeholder="曾用名" clearable style="width: 170px"></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 性别
          </template>
          <el-select v-model="admissionRegistration.gender" size="small" clearable placeholder="请选择性别" style="width: 170px">
            <el-option
              v-for="(label, value) in dictDwMap.GENDER"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 民族
          </template>
          <el-select v-model="admissionRegistration.ethnicity" size="small" clearable placeholder="请选择" style="width: 170px">
            <el-option
              v-for="(label, value) in dictDwMap.NATION"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 出生日期
          </template>
          <el-date-picker
            v-model="admissionRegistration.birthDay"
            clearable
            size="small"
            type="date"
            value-format="yyyy-MM-dd"
            style="width: 170px"
            placeholder="选择日期">
          </el-date-picker>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 年龄
          </template>
          <el-input-number v-model="admissionRegistration.age" :step="1" step-strictly :min="1" :max="1000" controls-position="right" size="small" style="width: 170px"></el-input-number> 岁
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 体重
          </template>
          <el-input-number v-model="admissionRegistration.weight" :precision="2" :step="0.1" step-strictly :min="0" :max="1000" controls-position="right" size="small" style="width: 170px"></el-input-number> kg
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 身高
          </template>
          <el-input-number v-model="admissionRegistration.height" :precision="2" :step="0.1" step-strictly :min="0" :max="1000" controls-position="right" size="small" style="width: 170px"></el-input-number> cm
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 婚姻状况
          </template>
          <el-select v-model="admissionRegistration.maritalStatus" size="small" clearable placeholder="请选择" style="width: 170px">
            <el-option
              v-for="(label, value) in dictDwMap.MARITALSTATUS"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 证件类型
          </template>
          <el-select v-model="admissionRegistration.identificationType" size="small" clearable placeholder="请选择" style="width: 170px">
            <el-option
              v-for="(label, value) in dictDwMap.CERTIFICATESTYPE"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item :span="2">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 证件号
          </template>
          <el-input v-model="admissionRegistration.identificationNumber" size="small" class="custom-input" placeholder="证件号" clearable ></el-input>
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 国籍
          </template>
          <el-select v-model="admissionRegistration.nationality" size="small" clearable placeholder="请选择" style="width: 170px">
            <el-option
              v-for="(label, value) in dictDwMap.NATIONALITY"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 籍贯
          </template>
          <el-select v-model="admissionRegistration.nativePlace" size="small" clearable placeholder="请选择" style="width: 170px">
            <el-option
              v-for="(label, value) in dictDwMap.NATIONALITY"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item :span="2">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 出生地址
          </template>
          <el-input v-model="admissionRegistration.placeBirth" size="small" class="custom-input" placeholder="户口本详细地址" clearable></el-input>
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            文化程度
          </template>
          <el-select v-model="admissionRegistration.educationLevel" size="small" clearable placeholder="请选择" style="width: 170px">
            <el-option
              v-for="(label, value) in dictDwMap.EDUCATION"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 联系电话
          </template>
          <el-input v-model="admissionRegistration.contactNumber" size="small" class="custom-input" placeholder="联系电话" clearable style="width: 170px"></el-input>
        </el-descriptions-item>
     <el-descriptions-item>
          <template slot="label">
            邮箱
          </template>
          <el-input v-model="admissionRegistration.email" size="small" class="custom-input" placeholder="邮箱" clearable style="width: 170px"></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            宗教信仰
          </template>
          <el-select v-model="admissionRegistration.religiousBelief" size="small" clearable placeholder="请选择" style="width: 170px">
            <el-option
              v-for="(label, value) in dictDwMap.RELIGIOUSBELIEF"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>

      </el-descriptions>

      <!-- 医保信息 -->
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px; margin-top: 20px">医保信息<span style="font-size: 12px; padding-left: 5px; font-weight: normal; color: darkgrey">带星号（*）是必填项</span></div>
      <el-descriptions class="margin-top" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 医保类型
          </template>
          <el-select v-model="admissionRegistration.insuranceType" size="small" clearable placeholder="请选择" style="width: 170px">
            <el-option
              v-for="(label, value) in dictDwMap.MEDICALINSURANCETYPE"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item :span="3">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 医保号
          </template>
          <el-input v-model="admissionRegistration.insuranceNumber" size="small" class="custom-input" placeholder="医保号" clearable style="width: 450px"></el-input>
        </el-descriptions-item>
        <el-descriptions-item :span="2">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 医疗证号
          </template>
          <el-input v-model="admissionRegistration.medicalCertificateNumber" size="small" class="custom-input" placeholder="医疗证号" clearable></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            血型
          </template>
          <el-select v-model="admissionRegistration.bloodType" size="small" clearable placeholder="请选择" style="width: 200px">
            <el-option
              v-for="(label, value) in dictDwMap.BLOODTYPE"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            是否RH型
          </template>
          <el-select v-model="admissionRegistration.rhType" size="small" clearable placeholder="请选择" style="width: 200px">
            <el-option
              v-for="(label, value) in dictDwMap.WHETHERORNOT"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
      </el-descriptions>

      <!-- 住院登记信息 -->
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px; margin-top: 20px">住院登记信息<span style="font-size: 12px; padding-left: 5px; font-weight: normal; color: darkgrey">带星号（*）是必填项</span></div>
      <el-descriptions class="margin-top" :column="3" border>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 科室
          </template>
          <el-select v-model="admissionRegistration.department" @change="depChange" size="small" clearable placeholder="请选择" style="width: 200px">
            <el-option
              v-for="(item, index) in depList"
              :key="index"
              align="center"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 门诊
          </template>
          <el-select v-model="admissionRegistration.patient" @change="infoChange" size="small" clearable placeholder="请选择" style="width: 200px">
            <el-option
              v-for="(item, index) in patientList"
              :key="index"
              align="center"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 医师职位
          </template>
          <el-select v-model="admissionRegistration.doctorPosition" @change="infoChange" size="small" clearable placeholder="请选择" style="width: 200px">
            <el-option
              v-for="(item, index) in patientEnums"
              :key="index"
              align="center"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 医师
          </template>
          <el-select v-model="admissionRegistration.doctorId" size="small" clearable placeholder="请选择" style="width: 200px">
            <el-option
              v-for="item in doctorList"
              :key="item.id"
              :label="item.username"
              :value="item.id">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 病房号
          </template>
          <el-input size="small" v-model="admissionRegistration.wardNumber" class="custom-input" placeholder="病房号" clearable></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 床号
          </template>
          <el-input size="small" v-model="admissionRegistration.bedNumber" class="custom-input" placeholder="床号" clearable></el-input>
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 入院登记日期
          </template>
          <el-date-picker
            v-model="admissionRegistration.registrationDate"
            clearable
            size="small"
            type="date"
            value-format="yyyy-MM-dd"
            style="width: 200px"
            placeholder="选择日期">
          </el-date-picker>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 登记人
          </template>
          <el-input size="small" v-model="admissionRegistration.registrar" class="custom-input" placeholder="登记人" clearable></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 登记人职位
          </template>
          <el-input size="small" v-model="admissionRegistration.registrarPosition" class="custom-input" placeholder="登记人职位" clearable></el-input>
        </el-descriptions-item>

      </el-descriptions>

      <!-- 过敏史 -->
      <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px; margin-top: 20px">过敏史<span style="font-size: 12px; padding-left: 5px; font-weight: normal; color: darkgrey">带星号（*）是必填项</span></div>
      <el-descriptions class="margin-top" :column="3" border>
        <el-descriptions-item :span="3">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 过敏史
          </template>
          <el-button size="small" type="success" @click="addAllergyHistory">增加一行</el-button>
          <el-form :inline="true" v-for="(item, index) in admissionRegistration.allergyHistory"  class="demo-form-inline">
            <el-form-item label="过敏史">
              <el-input v-model="item.title" size="small" style="width: 250px" placeholder="过敏史"></el-input>
            </el-form-item>
            <el-form-item label="内容">
              <el-input
                type="textarea"
                style="width: 600px"
                :rows="2"
                placeholder="请输入内容"
                v-model="item.content">
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button size="small" type="danger" @click="deleteAllergyHistory(index)">删除</el-button>
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
          <el-select v-model="admissionRegistration.occupation" size="small" clearable placeholder="请选择" style="width: 200px">
            <el-option
              v-for="(label, value) in dictDwMap.CAREER"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 是否本市
          </template>
          <el-select v-model="admissionRegistration.isLocal" size="small" clearable placeholder="请选择" style="width: 200px">
            <el-option
              v-for="(label, value) in dictDwMap.WHETHERORNOT"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            户口邮编
          </template>
          <el-input size="small" v-model="admissionRegistration.registeredPostcode" class="custom-input" placeholder="户口邮编" clearable></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 户口地址
          </template>
          <el-input size="small" v-model="admissionRegistration.registeredAddress" class="custom-input" placeholder="户口本详细地址" clearable></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 现住址
          </template>
          <el-input size="small" v-model="admissionRegistration.homeAddress" class="custom-input" placeholder="现住址" clearable></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 现住邮编
          </template>
          <el-input size="small" v-model="admissionRegistration.homePostcode" class="custom-input" placeholder="现住址邮编" clearable></el-input>
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 工作单位
          </template>
          <el-input size="small" v-model="admissionRegistration.employer" class="custom-input" placeholder="工作单位" clearable></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 工作地址
          </template>
          <el-input size="small" v-model="admissionRegistration.workAddress" class="custom-input" placeholder="工作地址" clearable></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 单位电话
          </template>
          <el-input size="small" v-model="admissionRegistration.workPhone" class="custom-input" placeholder="单位电话" clearable></el-input>
        </el-descriptions-item>
        <el-descriptions-item :span="3">
          <template slot="label">
            <span style="color: orangered; font-size: 15px">*</span> 紧急联系人
          </template>
          <el-button size="small" type="success" @click="addEmergencyContact">增加一行</el-button>
          <el-form :inline="true" v-for="(item, index) in admissionRegistration.emergencyContactGroup"  class="demo-form-inline">
            <el-form-item label="紧急联系人">
              <el-input v-model="item.emergencyContact" size="small" style="width: 250px" placeholder="审批人"></el-input>
            </el-form-item>
            <el-form-item label="关系">
              <el-input v-model="item.relationship" size="small" style="width: 250px" placeholder="审批人"></el-input>
            </el-form-item>
            <el-form-item label="联系人电话">
              <el-input v-model="item.contactPhone" size="small" style="width: 250px" placeholder="审批人"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button size="small" type="danger" @click="deleteEmergencyContactGroup(index)">删除</el-button>
            </el-form-item>
          </el-form>
        </el-descriptions-item>

      </el-descriptions>
    </el-card>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具 js,第三方插件 js,json文件，图片文件等等）
//例如:import 《组件名称》 from '《组件路径》';
import dictApi from '@/api/information/dict'
import departmentApi from '@/api/hosp/department'
import doctorApi from "@/api/information/doctor";
import admissionRegistrationApi from "@/api/medical/admissionRegistration";

export default {
  //import 引入的组件需要注入到对象中才能使用
  data() {
    //这里存放数据
    return {
      dictDwMap: {},
      depList: [],
      patientList: [],
      patientEnums: [],
      doctorList: [],
      // admissionRegistration: {
      //   emergencyContactGroup: []
      // },
      // 测试直接使用
      admissionRegistration: {
        userName: '张三',
        formerName: '李四',
        gender: '男',
        birthDay: '1990-01-01',
        age: 30,
        weight: 70,
        height: 175,
        maritalStatus: '已婚',
        ethnicity: '汉族',
        identificationType: '身份证',
        identificationNumber: '310110199001010001',
        placeBirth: '上海',
        nativePlace: '江苏',
        nationality: '中国',
        educationLevel: '本科',
        contactNumber: '13812345678',
        email: 'example@example.com',
        religiousBelief: '无',
        insuranceNumber: '1234567890',
        insuranceType: '社会保险',
        medicalCertificateNumber: 'ABC123',
        bloodType: 'A',
        rhType: '阳性',
        depId: 1,
        department: '内科',
        patient: '张三',
        doctorId: 1001,
        doctorName: '王医生',
        doctorPosition: '主治医师',
        wardNumber: 'A001',
        bedNumber: '001',
        registrationDate: '2021-01-01',
        registrar: '李护士',
        registrarPosition: '护士长',
        occupation: '工程师',
        isLocal: '是',
        registeredAddress: '上海市浦东新区',
        registeredPostcode: '200000',
        homeAddress: '上海市徐汇区',
        homePostcode: '201000',
        employer: 'ABC公司',
        workAddress: '上海市黄浦区',
        workPhone: '021-12345678',
        emergencyContactGroup: [
          { name: '紧急联系人1', relationship: '家人', phoneNumber: '13987654321' },
          { name: '紧急联系人2', relationship: '朋友', phoneNumber: '13876543210' }
        ],
        allergyHistory: [
          { title: '过敏史1', content: '内容1'},
          { title: '过敏史2', content: '内容2'}
        ]
      },
    };
  },
  mounted() {
    this.getDepList()
    this.getPatientEnums()
    this.findDictDw()
  },
  //方法集合
  methods: {
    // 保存电子病例
    saveAdmissionRegistration(){
      admissionRegistrationApi.saveAdmissionRegistration(this.admissionRegistration)
      .then(res => {
        if (res.code == 200) {
          this.$message.success("病案保存成功")
          this.$router.push('/medical/admissionRegistration');
        } else {
          this.$message.error(res.msg)
        }

      })
    },
    findDictDw(){
      dictApi.getDictDwMap()
      .then(res => {
        this.dictDwMap = res.data
      })
    },
    getDepList() {
      departmentApi.findAllDep().then(res => {
        this.depList = res.data
      })
    },
    getPatientEnums() {
      doctorApi.patientEnums().then(res => {
        this.patientEnums = res.data
      })
    },
    depChange(v) {
      if (v === '') {
        this.patientList = []
        this.admissionRegistration.patient = ''
      } else {
        departmentApi.findPatientNameByDepName({ depName: v })
          .then(res => {
            this.patientList = res.data
          })
      }

      if (this.admissionRegistration.department != null
        && this.admissionRegistration.department != ''
        && this.admissionRegistration.patient != null
        && this.admissionRegistration.patient != ''
        && this.admissionRegistration.doctorPosition != null
        && this.admissionRegistration.doctorPosition != ''){
        this.listPatient({
          department: this.admissionRegistration.department,
          patient: this.admissionRegistration.patient,
          doctorPosition: this.admissionRegistration.doctorPosition
        }
        )
      }
    },
    infoChange(){
      if (this.admissionRegistration.department != null
        && this.admissionRegistration.department != ''
        && this.admissionRegistration.patient != null
        && this.admissionRegistration.patient != ''
        && this.admissionRegistration.doctorPosition != null
        && this.admissionRegistration.doctorPosition != ''){
        this.listPatient({
            department: this.admissionRegistration.department,
            patient: this.admissionRegistration.patient,
            doctorPosition: this.admissionRegistration.doctorPosition
          }
        )
      }
    },
    listPatient(param){
      doctorApi.listPatient(param)
      .then(res => {
        this.doctorList = res.data
      })
    },
    addEmergencyContact(){
      this.admissionRegistration.emergencyContactGroup.push({
        emergencyContact: '',
        relationship: '',
        contactPhone: '',
      })
    },
    addAllergyHistory() {
      this.admissionRegistration.allergyHistory.push({
        title: '',
        content: ''
      })
    },
    deleteEmergencyContactGroup(index){
      this.admissionRegistration.emergencyContactGroup.splice(index, 1);
    },
    deleteAllergyHistory(index){
      this.admissionRegistration.allergyHistory.splice(index, 1);
    }
  },
}
</script>
<style lang='scss' scoped>
.box-card {
  width: 98%;
  margin-left: 1%;
  margin-top: 10px;
}

.custom-descriptions {
  margin-top: 20px; /* 自定义el-descriptions的上下间距 */
}

.custom-descriptions-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px; /* 自定义el-descriptions-item的上下间距 */
}

.custom-input {
  flex: 1;
  margin-left: 0px; /* 自定义输入框的左边距 */
  height: 32px; /* 自定义输入框的高度 */
}
</style>
