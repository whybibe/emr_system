<template>
  <div class="app-container">
    <!-- 工具条 -->
    <div class="tools-div">
      <el-form :inline="true" :model="queryParams">
        <el-form-item  label="姓名">
          <el-input v-model="queryParams.userName" size="small" placeholder="请输入查询的姓名" clearable></el-input>
        </el-form-item>
        <el-form-item  label="病案号">
          <el-input v-model="queryParams.id" size="small" placeholder="请输入查询的病案号" clearable></el-input>
        </el-form-item>
        <el-form-item  label="身份证号">
          <el-input v-model="queryParams.identificationNumber" size="small" placeholder="身份证号" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="getDataList(1)">查询</el-button>
          <el-button size="small" type="success" @click="showRegistration">病案首页登记</el-button>
        </el-form-item>
      </el-form>

      <el-table
        :data="dataList.records"
        border
        size="mini"
        v-loading="dataListLoading"
        @selection-change="selectionChangeHandle"
        style="width: 100%;"
      >
        <el-table-column type="selection" header-align="center" align="center" width="50"/>
        <el-table-column prop="id" header-align="center" align="center" label="病案号"/>
        <el-table-column prop="userName" header-align="center" align="center" label="姓名"/>
        <el-table-column prop="gender" header-align="center" align="center" label="性别"/>
        <el-table-column prop="birthDay" header-align="center" align="center" label="出生日期"/>
        <el-table-column prop="age" header-align="center" align="center" label="年龄"/>
        <el-table-column prop="ethnicity" header-align="center" align="center" label="民族"/>
        <el-table-column prop="identificationType" header-align="center" align="center" label="证件类型"/>
        <el-table-column prop="identificationNumber" header-align="center" :show-overflow-tooltip="true" align="center"
                         label="身份证号"
        />
        <el-table-column prop="contactNumber" header-align="center" align="center" label="联系电话"/>
        <el-table-column prop="insuranceType" header-align="center" align="center" label="医保类型">

        </el-table-column>
        <el-table-column prop="insuranceNumber" header-align="center" :show-overflow-tooltip="true" align="center"
                         label="医保号"
        />
        <el-table-column prop="department" header-align="center" align="center" label="科室/门诊"/>
        <el-table-column prop="doctorName" header-align="center" align="center" label="医师名称"/>
        <el-table-column prop="wardNumber" header-align="center" align="center" label="病房号"/>
        <el-table-column prop="bedNumber" header-align="center" align="center" label="床号"/>
        <el-table-column prop="registrationDate" header-align="center" align="center" label="入院登记日期"/>
        <el-table-column prop="registrar" header-align="center" align="center" label="登记人"/>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="200"
          label="操作"
        >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="updateData(scope.row)">修改</el-button>
            <el-button type="text" size="small" @click="addDetail(scope.row.id)">新增详情</el-button>
            <el-button type="text" size="small" style="color: red" @click="deleteHandle(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="getDataList"
        :current-page="dataList.pages"
        :page-sizes="[1, 5, 10, 15, 20, 30, 40, 50, 100]"
        :page-size="dataList.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="dataList.total"
        style="padding: 30px 0; text-align: center"
      >
      </el-pagination>
    </div>


    <el-dialog
      title="新增病案登记详情"
      :visible.sync="dialogVisible"
      width="65%"
      :before-close="handleClose">

      <el-button type="success" size="small" style="margin-left: 30px; margin-bottom: 15px;" @click="saveAdmissionRegistrationDetails">保存添加</el-button>

      <el-form :model="admissionRegistrationDetails">
        <!-- 会诊类型 -->
        <el-form-item label="会诊类型" label-width="100px">
          <el-radio-group v-model="admissionRegistrationDetails.admissionType">
            <el-radio label="诊断记录">诊断记录</el-radio>
            <el-radio label="手术记录">手术记录</el-radio>
            <el-radio label="治疗记录">治疗记录</el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- 手术室编号 -->
        <el-form-item v-if="admissionRegistrationDetails.admissionType == '手术记录'" label="手术名称" label-width="100px">
          <el-input v-model="admissionRegistrationDetails.operationName" placeholder="请输入手术名称"></el-input>
        </el-form-item>
        <!-- 手术室编号 -->
        <el-form-item v-if="admissionRegistrationDetails.admissionType == '手术记录'" label="手术室编号" label-width="100px">
          <el-input v-model="admissionRegistrationDetails.operationRoomNumber" placeholder="请输入手术室编号"></el-input>
        </el-form-item>
        <!-- 手术医生 -->
        <el-form-item v-if="admissionRegistrationDetails.admissionType == '手术记录'" label="手术医生" label-width="100px">
          <el-input v-model="admissionRegistrationDetails.operationDoctor" placeholder="请输入手术医生"></el-input>
        </el-form-item>
        <el-form-item label="登记时间" label-width="100px">
          <el-date-picker
            v-model="admissionRegistrationDetails.contentTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            clearable
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="疾病登记" label-width="100px">
          <el-select
            v-model="admissionRegistrationDetails.diseaseName"
            multiple
            filterable
            allow-create
            default-first-option
            placeholder="请选择已有疾病">
            <el-option
              v-for="item in diseaseNameList"
              :key="item.id"
              :label="item.caseName"
              :value="item.caseName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="会诊内容" label-width="100px">
          <el-input
            v-model="admissionRegistrationDetails.content"
            type="textarea"
            :rows="4"
            placeholder="请输入内容">
          </el-input>
        </el-form-item>
      </el-form>

      <el-table
        :data="admissionRegistrationDetailsList"
        size="small"
        border
        style="width: 95%; margin-left: 20px">
        <el-table-column prop="admissionType" align="center" label="会诊类型" width="120"></el-table-column>
        <el-table-column prop="operationName" align="center" label="手术名称" width="120"></el-table-column>
        <el-table-column prop="operationRoomNumber" align="center" label="手术室编号" width="120"></el-table-column>
        <el-table-column prop="operationDoctor" align="center" label="手术医生" width="120"></el-table-column>
        <el-table-column prop="contentTime" align="center" label="登记日期" width="160"></el-table-column>
        <el-table-column prop="userName" align="center" label="登记医师" width="120"></el-table-column>
        <el-table-column prop="diseaseName" align="center" label="疾病类型" width="180"></el-table-column>
        <el-table-column prop="content" align="center" label="登记内容"></el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="100"
          label="操作"
        >
          <template slot-scope="scope">
            <el-button type="text" size="small" style="color: red" @click="deleteDetails(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>


    <el-dialog
      title="修改病案登记详情"
      :visible.sync="dialogUpdateVisible"
      width="80%"
      :before-close="handleUpdateClose">

      <el-card class="box-card">
        <el-descriptions class="margin-top" border>
          <template slot="extra">
            <el-button type="primary" size="small" @click="updateAdminData">更新登记</el-button>
          </template>
        </el-descriptions>

        <!-- 个人基本信息 -->
        <div style="font-size: 17px; font-weight: bold; margin-bottom: 10px">个人基础信息<span style="font-size: 12px; padding-left: 5px; font-weight: normal; color: darkgrey">带星号（*）是必填项</span></div>
        <el-descriptions class="margin-top" :column="4" border>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 姓名
            </template>
            <el-input size="small" v-model="admissionRegistrationData.userName" class="custom-input" placeholder="姓名" clearable style="width: 170px"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              曾用名
            </template>
            <el-input size="small" v-model="admissionRegistrationData.formerName" class="custom-input" placeholder="曾用名" clearable style="width: 170px"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 性别
            </template>
            <el-select v-model="admissionRegistrationData.gender" size="small" clearable placeholder="请选择性别" style="width: 170px">
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
            <el-select v-model="admissionRegistrationData.ethnicity" size="small" clearable placeholder="请选择" style="width: 170px">
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
              v-model="admissionRegistrationData.birthDay"
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
            <el-input-number v-model="admissionRegistrationData.age" :step="1" step-strictly :min="1" :max="1000" controls-position="right" size="small" style="width: 170px"></el-input-number> 岁
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 体重
            </template>
            <el-input-number v-model="admissionRegistrationData.weight" :precision="2" :step="0.1" step-strictly :min="0" :max="1000" controls-position="right" size="small" style="width: 170px"></el-input-number> kg
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 身高
            </template>
            <el-input-number v-model="admissionRegistrationData.height" :precision="2" :step="0.1" step-strictly :min="0" :max="1000" controls-position="right" size="small" style="width: 170px"></el-input-number> cm
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 婚姻状况
            </template>
            <el-select v-model="admissionRegistrationData.maritalStatus" size="small" clearable placeholder="请选择" style="width: 170px">
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
            <el-select v-model="admissionRegistrationData.identificationType" size="small" clearable placeholder="请选择" style="width: 170px">
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
            <el-input v-model="admissionRegistrationData.identificationNumber" size="small" class="custom-input" placeholder="证件号" clearable ></el-input>
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 国籍
            </template>
            <el-select v-model="admissionRegistrationData.nationality" size="small" clearable placeholder="请选择" style="width: 170px">
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
            <el-select v-model="admissionRegistrationData.nativePlace" size="small" clearable placeholder="请选择" style="width: 170px">
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
            <el-input v-model="admissionRegistrationData.placeBirth" size="small" class="custom-input" placeholder="户口本详细地址" clearable></el-input>
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              文化程度
            </template>
            <el-select v-model="admissionRegistrationData.educationLevel" size="small" clearable placeholder="请选择" style="width: 170px">
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
            <el-input v-model="admissionRegistrationData.contactNumber" size="small" class="custom-input" placeholder="联系电话" clearable style="width: 170px"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              邮箱
            </template>
            <el-input v-model="admissionRegistrationData.email" size="small" class="custom-input" placeholder="邮箱" clearable style="width: 170px"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              宗教信仰
            </template>
            <el-select v-model="admissionRegistrationData.religiousBelief" size="small" clearable placeholder="请选择" style="width: 170px">
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
            <el-select v-model="admissionRegistrationData.insuranceType" size="small" clearable placeholder="请选择" style="width: 170px">
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
            <el-input v-model="admissionRegistrationData.insuranceNumber" size="small" class="custom-input" placeholder="医保号" clearable style="width: 450px"></el-input>
          </el-descriptions-item>
          <el-descriptions-item :span="2">
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 医疗证号
            </template>
            <el-input v-model="admissionRegistrationData.medicalCertificateNumber" size="small" class="custom-input" placeholder="医疗证号" clearable></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              血型
            </template>
            <el-select v-model="admissionRegistrationData.bloodType" size="small" clearable placeholder="请选择" style="width: 200px">
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
            <el-select v-model="admissionRegistrationData.rhType" size="small" clearable placeholder="请选择" style="width: 200px">
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
            <el-select v-model="admissionRegistrationData.department" @change="depChange" size="small" clearable placeholder="请选择" style="width: 200px">
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
            <el-select v-model="admissionRegistrationData.patient" @change="infoChange" size="small" clearable placeholder="请选择" style="width: 200px">
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
            <el-select v-model="admissionRegistrationData.doctorPosition" @change="infoChange" size="small" clearable placeholder="请选择" style="width: 200px">
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
            <el-select v-model="admissionRegistrationData.doctorId" size="small" clearable placeholder="请选择" style="width: 200px">
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
            <el-input size="small" v-model="admissionRegistrationData.wardNumber" class="custom-input" placeholder="病房号" clearable></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 床号
            </template>
            <el-input size="small" v-model="admissionRegistrationData.bedNumber" class="custom-input" placeholder="床号" clearable></el-input>
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 入院登记日期
            </template>
            <el-date-picker
              v-model="admissionRegistrationData.registrationDate"
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
            <el-input size="small" v-model="admissionRegistrationData.registrar" class="custom-input" placeholder="登记人" clearable></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 登记人职位
            </template>
            <el-input size="small" v-model="admissionRegistrationData.registrarPosition" class="custom-input" placeholder="登记人职位" clearable></el-input>
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
            <el-form :inline="true" v-for="(item, index) in admissionRegistrationData.allergyHistory"  class="demo-form-inline">
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
            <el-select v-model="admissionRegistrationData.occupation" size="small" clearable placeholder="请选择" style="width: 200px">
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
            <el-select v-model="admissionRegistrationData.isLocal" size="small" clearable placeholder="请选择" style="width: 200px">
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
            <el-input size="small" v-model="admissionRegistrationData.registeredPostcode" class="custom-input" placeholder="户口邮编" clearable></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 户口地址
            </template>
            <el-input size="small" v-model="admissionRegistrationData.registeredAddress" class="custom-input" placeholder="户口本详细地址" clearable></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 现住址
            </template>
            <el-input size="small" v-model="admissionRegistrationData.homeAddress" class="custom-input" placeholder="现住址" clearable></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 现住邮编
            </template>
            <el-input size="small" v-model="admissionRegistrationData.homePostcode" class="custom-input" placeholder="现住址邮编" clearable></el-input>
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 工作单位
            </template>
            <el-input size="small" v-model="admissionRegistrationData.employer" class="custom-input" placeholder="工作单位" clearable></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 工作地址
            </template>
            <el-input size="small" v-model="admissionRegistrationData.workAddress" class="custom-input" placeholder="工作地址" clearable></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 单位电话
            </template>
            <el-input size="small" v-model="admissionRegistrationData.workPhone" class="custom-input" placeholder="单位电话" clearable></el-input>
          </el-descriptions-item>
          <el-descriptions-item :span="3">
            <template slot="label">
              <span style="color: orangered; font-size: 15px">*</span> 紧急联系人
            </template>
            <el-button size="small" type="success" @click="addEmergencyContact">增加一行</el-button>
            <el-form :inline="true" v-for="(item, index) in admissionRegistrationData.emergencyContactGroup"  class="demo-form-inline">
              <el-form-item label="紧急联系人">
                <el-input v-model="item.emergencyContact" size="small" style="width: 200px" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="关系">
                <el-input v-model="item.relationship" size="small" style="width: 200px" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="联系人电话">
                <el-input v-model="item.contactPhone" size="small" style="width: 200px" placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button size="small" type="danger" @click="deleteEmergencyContactGroup(index)">删除</el-button>
              </el-form-item>
            </el-form>
          </el-descriptions-item>

        </el-descriptions>
      </el-card>

    </el-dialog>

  </div>
</template>


<script>

import admissionRegistrationApi from '@/api/medical/admissionRegistration'
import admissionRegistrationDetailsApi from '@/api/medical/admissionRegistrationDetails'
import caseLibraryApi from '@/api/medical/caseLibrary'
import dictApi from '@/api/information/dict'
import departmentApi from '@/api/hosp/department'
import doctorApi from "@/api/information/doctor";

export default {
  // 定义数据
  data() {
    return {
      dictDwMap: {},
      depList: [],
      patientList: [],
      patientEnums: [],
      doctorList: [],
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        id: null,
        userName: '',
        identificationNumber: ''
      },
      dataList: {},
      dataListLoading: false,

      dataListSelections: [],

      dialogVisible: false,
      admissionRegistrationDetails: {
        admissionRegistrationId: null,
        admissionType: "诊断记录",
        operationName: '',
        operationRoomNumber: '',
        operationDoctor: '',
        contentTime: null,
        diseaseName: [],
        content: ''
      },
      diseaseNameList: [],
      admissionRegistrationDetailsList: [],

      dialogUpdateVisible: false,
      admissionRegistrationData: {
        emergencyContactGroup: [],
        allergyHistory: []
      },
    }
  },

  //当页面加载时获取数据
  created() {
    this.findDictDw()
    this.getDataList()
    this.getDepList()
    this.getPatientEnums()
  },

  methods: {
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
    // 获取数据列表
    getDataList(page = 1) {
      this.dataListLoading = true
      this.queryParams.pageNo = page
      admissionRegistrationApi.findList(this.queryParams)
        .then(res => {
          console.log(res)
          this.dataList = res.data
          this.dataListLoading = false
        })
    },
    handleSizeChange(value) {
      this.queryParams.pageSize = value
      this.getDataList()
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },
    // 删除
    deleteHandle(id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        admissionRegistrationApi.deleteByIds(ids)
        .then(({ data }) => {
          if (data && data.code == 200) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList(1)
              }
            })
          } else {
            this.$message.error(data.data)
          }
        })
      })
    },
    showRegistration() {
      this.$router.push('/medical/registration')
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    addDetail(id){
      this.admissionRegistrationDetails.contentTime = null
      this.admissionRegistrationDetails.content = null
      this.admissionRegistrationDetails.diseaseName = []
      this.admissionRegistrationDetails.admissionRegistrationId = id;
      admissionRegistrationDetailsApi.list({id:id})
      .then(res => {
        // 清空数据
        this.admissionRegistrationDetailsList = res.data
      })
      caseLibraryApi.list()
      .then(res => {
        this.diseaseNameList = res.data
      })
      this.dialogVisible = true
    },
    // 保存会诊详情
    saveAdmissionRegistrationDetails(){
      admissionRegistrationDetailsApi.save(this.admissionRegistrationDetails)
      .then(res => {
        // 保存成功
        this.$message({
          message: '入院记录登记成功成功',
          type: 'success'
        })

        admissionRegistrationDetailsApi.list({id:this.admissionRegistrationDetails.admissionRegistrationId})
          .then(res => {
            this.admissionRegistrationDetailsList = res.data
          })



        // 清空数据
        // this.admissionRegistrationDetails.contentTime = null
        // this.admissionRegistrationDetails.content = null
        // this.admissionRegistrationDetails.diseaseName = []

        this.admissionRegistrationDetails = {
          admissionRegistrationId: null,
          admissionType: "诊断记录",
          operationName: '',
          operationRoomNumber: '',
          operationDoctor: '',
          contentTime: null,
          diseaseName: [],
          content: ''
        }

      })
    },
    deleteDetails(id) {
      admissionRegistrationDetailsApi.delete({id:id})
      .then(res => {
        admissionRegistrationDetailsApi.list({id:this.admissionRegistrationDetails.admissionRegistrationId})
          .then(res => {
            this.admissionRegistrationDetailsList = res.data
          })
      })
    },
    handleUpdateClose() {
      this.dialogUpdateVisible = false
    },
    updateData(row) {
      this.admissionRegistrationData = row
      this.dialogUpdateVisible = true
    },
    depChange(v) {
      if (v === '') {
        this.patientList = []
        this.admissionRegistrationData.patient = ''
      } else {
        departmentApi.findPatientNameByDepName({ depName: v })
          .then(res => {
            this.patientList = res.data
          })
      }

      if (this.admissionRegistrationData.department != null
        && this.admissionRegistrationData.department != ''
        && this.admissionRegistrationData.patient != null
        && this.admissionRegistrationData.patient != ''
        && this.admissionRegistrationData.doctorPosition != null
        && this.admissionRegistrationData.doctorPosition != ''){
        this.listPatient({
            department: this.admissionRegistrationData.department,
            patient: this.admissionRegistrationData.patient,
            doctorPosition: this.admissionRegistrationData.doctorPosition
          }
        )
      }
    },
    infoChange(){
      if (this.admissionRegistrationData.department != null
        && this.admissionRegistrationData.department != ''
        && this.admissionRegistrationData.patient != null
        && this.admissionRegistrationData.patient != ''
        && this.admissionRegistrationData.doctorPosition != null
        && this.admissionRegistrationData.doctorPosition != ''){
        this.listPatient({
            department: this.admissionRegistrationData.department,
            patient: this.admissionRegistrationData.patient,
            doctorPosition: this.admissionRegistrationData.doctorPosition
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
      this.admissionRegistrationData.emergencyContactGroup.push({
        emergencyContact: '',
        relationship: '',
        contactPhone: '',
      })
    },
    addAllergyHistory() {
      if (this.admissionRegistrationData.allergyHistory == null) {
        this.admissionRegistrationData.allergyHistory = []
      }
      this.admissionRegistrationData.allergyHistory.push({
        title: '',
        content: ''
      })
    },
    deleteEmergencyContactGroup(index){
      this.admissionRegistrationData.emergencyContactGroup.splice(index, 1);
    },
    deleteAllergyHistory(index){
      this.admissionRegistrationData.allergyHistory.splice(index, 1);
    },
    updateAdminData(){
      admissionRegistrationApi.updateData(this.admissionRegistrationData)
      .then(res => {
        this.dialogUpdateVisible = false
        this.getDataList(1)
      })
    }

  }
}
</script>
