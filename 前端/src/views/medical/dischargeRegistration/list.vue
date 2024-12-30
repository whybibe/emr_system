<template>
  <div class="app-container">
    <!-- 工具条 -->
    <div class="tools-div">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="姓名">
          <el-input v-model="queryParams.userName" size="small" placeholder="请输入查询的姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="病案号">
          <el-input v-model="queryParams.id" size="small" placeholder="请输入查询的病案号" clearable></el-input>
        </el-form-item>
        <el-form-item label="科室">
          <el-select
            v-model="queryParams.department"
            size="small"
            @change="depChange"
            placeholder="选择科室"
            style="width: 200px"
            clearable
          >
            <el-option
              v-for="(item, index) in depList"
              :key="index"
              align="center"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="门诊">
          <el-select
            v-model="queryParams.patient"
            size="small"
            placeholder="选择门诊"
            style="width: 200px"
            clearable
          >
            <el-option
              v-for="(item, index) in patientList"
              :key="index"
              align="center"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病房号">
          <el-input v-model="queryParams.wardNumber" size="small" placeholder="病房号" clearable></el-input>
        </el-form-item>
        <el-form-item label="床号">
          <el-input v-model="queryParams.bedNumber" size="small" placeholder="床号" clearable></el-input>
        </el-form-item>
        <el-form-item label="出院登记日期">
          <el-date-picker
            size="small"
            v-model="queryParams.dischargeDate"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            type="date"
            clearable
            placeholder="出院登记日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="getDataList(1)">查询</el-button>
          <el-button size="small" type="success" @click="saveDish">新增入院记录</el-button>
        </el-form-item>
      </el-form>

      <el-table
        :data="dataList.dischargeRegistrationRoList"
        border
        size="mini"
        v-loading="dataListLoading"
        style="width: 100%;"
      >
        <el-table-column type="selection" header-align="center" align="center" width="50"/>
        <el-table-column prop="aid" header-align="center" align="center" label="病案号"/>
        <el-table-column prop="userName" header-align="center" align="center" label="姓名"/>
        <el-table-column prop="contactNumber" header-align="center" align="center" label="联系电话"/>
        <el-table-column prop="department" header-align="center" align="center" label="科室"/>
        <el-table-column prop="patient" header-align="center" align="center" label="门诊"/>
        <el-table-column prop="doctorName" header-align="center" align="center" label="医师名称"/>
        <el-table-column prop="wardNumber" header-align="center" align="center" label="病房号"/>
        <el-table-column prop="bedNumber" header-align="center" align="center" label="床号"/>
        <el-table-column prop="dischargeDate" header-align="center" align="center" label="出院登记日期"/>
        <el-table-column prop="registrarName" header-align="center" :show-overflow-tooltip="true" align="center"
                         label="登记人"
        />
        <el-table-column prop="registrarPosition" header-align="center" align="center" label="登记人职位"/>
        <el-table-column prop="costCalculation" header-align="center" align="center" label="费用结算"/>
        <el-table-column prop="isSettled" header-align="center" :show-overflow-tooltip="true" align="center"
                         label="是否结算"
        />
        <el-table-column prop="createTime" header-align="center" align="center" label="创建时间"/>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="200"
          label="操作"
        >
          <template slot-scope="scope">
            <el-button v-if="scope.row.isSettled == '否'" type="text" size="small"
                       @click="changeIsSettled(scope.row.id)"
            >一键结算
            </el-button>
            <el-button type="text" size="small" @click="details(scope.row)">病案详情</el-button>
            <el-button v-if="scope.row.fileUrl == null" type="text" size="small" @click="uploadFile(scope.row)">上传病案</el-button>
            <el-button v-if="scope.row.fileUrl != null" type="text" size="small" @click="downLoadfile(scope.row)">下载病案</el-button>
            <el-button type="text" size="small" style="color: red" @click="deleteHandle(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="getDataList"
        :current-page="dataList.pageNo"
        :page-sizes="[1, 5, 10, 15, 20, 30, 40, 50, 100]"
        :page-size="dataList.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="dataList.total"
        style="padding: 30px 0; text-align: center"
      >
      </el-pagination>
    </div>


    <el-dialog title="新增出院登记" :visible.sync="dialogFormVisible" width="70%">

      <!-- 工具条 -->
      <div class="tools-div">
        <el-form :inline="true" :model="admissionParam">
          <el-form-item label="姓名">
            <el-input v-model="admissionParam.userName" size="small" placeholder="请输入查询的姓名" clearable></el-input>
          </el-form-item>
          <el-form-item label="身份证号">
            <el-input v-model="admissionParam.identificationNumber" size="small" placeholder="身份证号" clearable
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button size="small" type="primary" @click="findListNotOut">查询</el-button>
          </el-form-item>
        </el-form>

        <el-table
          :data="admissionData"
          border
          size="mini"
          v-loading="dataListLoading"
          @selection-change="admissionDataSelectionChangeHandle"
          style="width: 100%;"
        >
          <el-table-column type="selection" header-align="center" align="center" width="50"/>
          <el-table-column prop="userName" header-align="center" align="center" label="姓名"/>
          <el-table-column prop="gender" header-align="center" align="center" label="性别"/>
          <el-table-column prop="birthDay" header-align="center" align="center" label="出生日期"/>
          <el-table-column prop="age" header-align="center" align="center" label="年龄"/>
          <el-table-column prop="ethnicity" header-align="center" align="center" label="民族"/>
          <el-table-column prop="identificationType" header-align="center" align="center" label="证件类型">

          </el-table-column>
          <el-table-column prop="identificationNumber" header-align="center" :show-overflow-tooltip="true"
                           align="center"
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
        </el-table>
      </div>

      <!-- 工具条 -->
      <div class="tools-div">
        <el-descriptions class="margin-top" :column="3" border>
          <el-descriptions-item>
            <template slot="label">
              登记人职位
            </template>
            <el-input size="small" v-model="dischargeRegistration.registrarPosition" class="custom-input"
                      placeholder="请输入登记人职位" clearable
            ></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              结算费用
            </template>
            <el-input-number size="small" v-model="dischargeRegistration.costCalculation" step="0.1"
                             style="width: 200px"
                             placeholder="请输入结算费用" clearable
            ></el-input-number>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              是否结算
            </template>
            <el-select v-model="dischargeRegistration.isSettled" size="small" clearable placeholder="请选择"
                       style="width: 200px"
            >
              <el-option label="是" value="是"></el-option>
              <el-option label="否" value="否"></el-option>
            </el-select>
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitDish">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="上传病案" :visible.sync="fileDialogVisible" width="40%">
      <el-form
        ref="dataForm"
        :model="medicalFile"
        label-width="100px"
        size="small"
        style="padding-right: 40px"
      >
        <el-form-item label="病案文件" prop="headUrl">
          <el-upload
            class="upload-demo"
            action="http://localhost:8800/admin/file/img/medical"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            list-type="text"
          >
            <el-button size="small" type="primary"
            >点击上传
            </el-button
            >
            <div slot="tip" class="el-upload__tip">
              只能上传文件，且不超过500kb
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
                <el-button
                  @click="fileDialogVisible = false"
                  size="small"
                  icon="el-icon-refresh-right"
                >取 消</el-button
                >
                <el-button
                  type="primary"
                  icon="el-icon-check"
                  @click="saveOrUpdate()"
                  size="small"
                >确 定</el-button
                >
            </span>
    </el-dialog>

  </div>
</template>


<script>

import dischargeRegistrationApi from '@/api/medical/dischargeRegistration'
import admissionRegistrationApi from '@/api/medical/admissionRegistration'
import departmentApi from '@/api/hosp/department'
import fileApi from '@/api/file/file'

export default {
  // 定义数据
  data() {
    return {
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        userName: '',
        id: null,
        department: '',
        patient: '',
        wardNumber: '',
        bedNumber: '',
        dischargeDate: null
      },
      dataList: {},
      dataListLoading: false,

      dataListSelections: [],

      depList: [],
      patientList: [],

      // 新增出院登记
      dialogFormVisible: false,
      admissionParam: {
        userName: '',
        identificationNumber: ''
      },
      admissionData: [],
      dischargeRegistration: {
        admissionRegistrationId: '',
        registrarPosition: '',
        costCalculation: '',
        isSettled: ''
      },

      fileDialogVisible: false,
      fileList: [],
      medicalFile: {
        id: null,
        fileUrl: ''
      },
    }
  },

  //当页面加载时获取数据
  created() {
    this.getDepList()
    this.getDataList()
  },

  methods: {
    // 获取数据列表
    getDataList(page = 1) {
      this.dataListLoading = true
      this.queryParams.pageNo = page
      dischargeRegistrationApi.findList(this.queryParams)
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
    getDepList() {
      departmentApi.findAllDep().then(res => {
        this.depList = res.data
      })
    },
    depChange(v) {
      if (v === '') {
        this.patientList = []
        this.queryParams.patientName = ''
      } else {
        departmentApi.findPatientNameByDepName({ depName: v })
          .then(res => {
            this.patientList = res.data
          })
      }
    },
    saveDish() {
      this.admissionParam = {
        userName: '',
        identificationNumber: ''
      },
        this.dischargeRegistration = {}
      this.findListNotOut()
      this.dialogFormVisible = true
    },
    findListNotOut() {
      admissionRegistrationApi.listNotOut(this.admissionParam)
        .then(res => {
          this.admissionData = res.data
        })
    },
    // 多选
    admissionDataSelectionChangeHandle(val) {
      this.dataListSelections = val
    },
    // 提交数据进行保存
    submitDish() {
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

      this.dischargeRegistration.admissionRegistrationId = this.dataListSelections[0].id
      dischargeRegistrationApi.saveDisch(this.dischargeRegistration)
        .then(res => {
          this.dialogFormVisible = false
          this.getDataList()
        })
    },
    changeIsSettled(id) {

      this.$confirm(`确定要进行结算吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        dischargeRegistrationApi.settleAccounts({ id: id })
          .then(res => {
            this.$message({
              message: '操作成功',
              type: 'success',
              onClose: () => {
                this.getDataList()
              }
            })
          })
      })
    },
    // 删除
    deleteHandle(id) {
      this.$confirm(`确定对[id=${id}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        dischargeRegistrationApi.deleteByIds({ id: id })
          .then(data => {
            if (data && data.code == 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.data)
            }
          })
      })
    },
    handlePreview(file) {
      window.open(file.url, '_blank')
    },
    handleRemove(file, fileList) {
      this.deleteFile(file.url)
    },
    deleteFile(fileUrl) {
      fileApi.delete({ fileUrl: fileUrl }).then(res => {
      })
    },
    handleAvatarSuccess(res, file) {
      console.log(res.data)
      if (this.fileList.length > 0 && (this.medicalFile.fileUrl != null || this.medicalFile.fileUrl != '')) {
        // 先删除以前的图片
        this.deleteFile(this.medicalFile.fileUrl)
        this.fileList = []
      }
      this.fileList.push(
        {
          name: file.name,
          url: res.data
        })
      this.medicalFile.fileUrl = res.data

    },
    uploadFile(row) {
      this.fileList = []
      if (row.fileUrl != null && row.fileUrl != '') {
        this.fileList.push({
          name: row.userName+"的病案",
          url: row.fileUrl
        })
      }
      this.medicalFile.id = row.admissionRegistrationId
      this.medicalFile.fileUrl = row.fileUrl
      this.fileDialogVisible = true
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2
      return isLt2M
    },
    saveOrUpdate() {
      dischargeRegistrationApi.upload(this.medicalFile)
      .then(res => {
        // 提示成功
        this.$message.success("病案上传成功")
        this.getDataList(1)
        this.fileDialogVisible = false
      })
    },
    downLoadfile(row) {
      window.open(row.fileUrl, '_blank')
    },
    details(row) {
      this.$router.push('/medical/details?id=' + row.admissionRegistrationId );
    }
  }
}
</script>
