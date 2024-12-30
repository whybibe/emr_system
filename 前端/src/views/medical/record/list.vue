<template>
  <div class="app-container">
    <!-- 工具条 -->
    <div class="tools-div">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="姓名">
          <el-input v-model="queryParams.userName" size="small" placeholder="请输入查询的姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="病案类型">
          <el-select v-model="queryParams.medicalType" size="small" placeholder="选择病案类型" style="width: 200px" clearable>
            <el-option align="center" label="出院病案" value="出院病案"></el-option>
            <el-option align="center" label="死亡病案" value="死亡病案"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否归还">
          <el-select v-model="queryParams.isReturn" size="small" placeholder="选择是否归还" style="width: 200px" clearable>
            <el-option align="center" label="是" value="是"></el-option>
            <el-option align="center" label="否" value="否"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="findList">查询</el-button>
          <el-button size="small" type="success" @click="recordedMedical">病案借阅</el-button>
          <a
            href="http://localhost:8800/admin/medical/medical_record_borrowing/export/excel"
            target="_blank"
            style="margin-left: 10px"
          >
            <el-button
              type="primary"
              size="small"
              icon="el-icon-top"
            >导出Excel</el-button>
          </a>
        </el-form-item>
      </el-form>

      <el-table
        :data="dataList.records"
        border
        size="mini"
        style="width: 100%;"
      >
        <el-table-column type="selection" header-align="center" align="center" width="50"/>
        <el-table-column prop="userName" header-align="center" align="center" label="借阅人"/>
        <el-table-column prop="admissionRegistrationId" header-align="center" align="center" label="病案ID"/>
        <el-table-column prop="medicalType" header-align="center" align="center" label="病案类型"/>
        <el-table-column prop="borrowingDuration" header-align="center" align="center" label="借阅到期时间"/>
        <el-table-column prop="borrowingDurationNote" header-align="center" align="center" label="借阅备注"/>
        <el-table-column prop="isReturn" header-align="center" align="center" label="是否归还"/>
        <el-table-column prop="createTime" header-align="center" align="center" label="创建时间"/>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="200"
          label="操作"
        >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="returnRecord(scope.row.id)">一键归还</el-button>
            <el-button size="small" type="text" @click="deletById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="findList"
        :current-page="dataList.current"
        :page-sizes="[1, 5, 10, 15, 20, 30, 40, 50, 100]"
        :page-size="dataList.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="dataList.total"
        style="padding: 30px 0; text-align: center"
      >
      </el-pagination>
    </div>

    <el-dialog title="病案借阅登记" :visible.sync="dialogFormVisible" width="70%">

      <!-- 工具条 -->
      <div class="tools-div">
        <el-form :inline="true" :model="borrowParam">
          <el-form-item label="病案类型">
            <el-select v-model="borrowParam.medicalType" size="small" placeholder="选择病案类型" style="width: 200px">
              <el-option align="center" label="出院病案" value="出院病案"></el-option>
              <el-option align="center" label="死亡病案" value="死亡病案"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button size="small" type="primary" @click="recordedMedical">查询</el-button>
          </el-form-item>
        </el-form>

        <el-table
          :data="admissionData"
          border
          size="mini"
          @selection-change="admissionDataSelectionChangeHandle"
          style="width: 100%;"
        >
          <el-table-column type="selection" header-align="center" align="center" width="50"/>
          <el-table-column prop="userName" header-align="center" align="center" label="姓名"/>
          <el-table-column prop="gender" header-align="center" align="center" label="性别"/>
          <el-table-column prop="birthDay" header-align="center" align="center" label="出生日期"/>
          <el-table-column prop="age" header-align="center" align="center" label="年龄"/>
          <el-table-column prop="ethnicity" header-align="center" align="center" label="民族"/>
          <el-table-column prop="identificationType" header-align="center" align="center" label="证件类型"/>
          <el-table-column prop="identificationNumber" header-align="center" :show-overflow-tooltip="true"
                           align="center" label="身份证号"
          />
          <el-table-column prop="contactNumber" header-align="center" align="center" label="联系电话"/>
          <el-table-column prop="insuranceType" header-align="center" align="center" label="医保类型"/>
          <el-table-column prop="insuranceNumber" header-align="center" :show-overflow-tooltip="true" align="center"
                           label="医保号"
          />
          <el-table-column prop="department" header-align="center" align="center" label="科室/门诊"/>
          <el-table-column prop="doctorName" header-align="center" align="center" label="医师名称"/>
          <el-table-column prop="wardNumber" header-align="center" align="center" label="病房号"/>
          <el-table-column prop="bedNumber" header-align="center" align="center" label="床号"/>
          <el-table-column prop="registrationDate" header-align="center" align="center" label="入院登记日期"/>
          <el-table-column prop="registrar" header-align="center" align="center" label="登记人"/>
        </el-table>
      </div>

      <!-- 工具条 -->
      <div class="tools-div">
        <el-descriptions class="margin-top" :column="3" border>
          <el-descriptions-item>
            <template slot="label">
              借阅到期时间
            </template>
            <el-date-picker
              size="small"
              v-model="medicalRecordBorrowing.borrowingDuration"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              clearable
              placeholder="借阅到期时间"
            >
            </el-date-picker>
          </el-descriptions-item>
          <el-descriptions-item :span="2">
            <template slot="label">
              借阅备注
            </template>
            <el-input
              size="small"
              type="textarea"
              :rows="4"
              placeholder="请输入内容"
              style="width: 500px"
              v-model="medicalRecordBorrowing.borrowingDurationNote"
            >
            </el-input>
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitData">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>


<script>

import medicalRecordBorrowingApi from '@/api/medical/medicalRecordBorrowing'
import admissionRegistrationApi from '@/api/medical/admissionRegistration'

export default {
  // 定义数据
  data() {
    return {
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        userName: '',
        medicalType: '',
        isReturn: ''
      },
      dataList: [],

      // 病案借阅
      dialogFormVisible: false,

      borrowParam: {
        medicalType: '出院病案'
      },
      admissionData: [],
      dataListSelections: [],

      medicalRecordBorrowing: {
        admissionRegistrationId: null,
        medicalType: '',
        borrowingDuration: '',
        borrowingDurationNote: ''
      }
    }
  },

  //当页面加载时获取数据
  created() {
    this.findList()
  },
  methods: {
    findList() {
      medicalRecordBorrowingApi.findAllList(this.queryParams)
        .then(res => {
          this.dataList = res.data
        })
    },
    handleSizeChange(size) {
      this.queryParams.pageSize = size
      this.findList()
    },
    recordedMedical() {
      admissionRegistrationApi.getAdmissionRegistrationByCaseType(this.borrowParam)
        .then(res => {
          this.admissionData = res.data
          this.dialogFormVisible = true
        })
    },
    // 多选
    admissionDataSelectionChangeHandle(val) {
      this.dataListSelections = val
    },
    // 提交数据进行保存
    submitData() {
      if (this.dataListSelections.length > 1) {
        // 提示只能选择一条数据
        this.$message.error('只能选择一条数据')
        return
      }
      if (this.dataListSelections.length === 0) {
        // 提示至少选择一条数据
        this.$message.error('至少选择一条数据')
        return
      }

      this.medicalRecordBorrowing.admissionRegistrationId = this.dataListSelections[0].id
      this.medicalRecordBorrowing.medicalType = this.borrowParam.medicalType
      medicalRecordBorrowingApi.addMedicalRecord(this.medicalRecordBorrowing)
        .then(res => {
          this.dialogFormVisible = false
          this.medicalRecordBorrowing = {
            admissionRegistrationId: null,
            medicalType: '',
            borrowingDuration: '',
            borrowingDurationNote: ''
          },
            this.findList()
        })
    },
    returnRecord(id) {
      medicalRecordBorrowingApi.returnMedicalRecord(id)
        .then(res => {
          this.findList()
          this.$message.success('归还成功')

        })
    },
    deletById(id) {
      medicalRecordBorrowingApi.deleteMedicalRecord(id)
        .then(res => {
          this.findList()
          this.$message.success('删除成功')

        })
    }
  }
}
</script>
