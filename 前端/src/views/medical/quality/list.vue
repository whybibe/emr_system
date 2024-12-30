<template>
  <div class="app-container">
    <!-- 工具条 -->
    <div class="tools-div">
      <el-form :inline="true" :model="queryParams">
        <el-form-item  label="姓名">
          <el-input v-model="queryParams.userName" size="small" placeholder="请输入查询的姓名" clearable></el-input>
        </el-form-item>
        <el-form-item  label="身份证号">
          <el-input v-model="queryParams.identificationNumber" size="small" placeholder="身份证号" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="getDataList(1)">查询</el-button>
        </el-form-item>
      </el-form>

      <el-table
        :data="dataList.records"
        border
        size="mini"
        v-loading="dataListLoading"
        style="width: 100%;"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <diV style="width: 600px;margin-left: 100px">
              <el-descriptions title="病案质量分析结果" :column="2">
                <el-descriptions-item label="病案质量管理人">{{ props.row.medicalRecordQuality.registrarName }}</el-descriptions-item>
                <el-descriptions-item label="评分">
                  <el-rate
                    disabled
                    v-model="props.row.medicalRecordQuality.score"
                    show-text>
                  </el-rate>
                </el-descriptions-item>
                <el-descriptions-item label="评价内容">{{ props.row.medicalRecordQuality.evaluate }}</el-descriptions-item>
                <el-descriptions-item label="评价时间">{{ props.row.medicalRecordQuality.createTime }}</el-descriptions-item>
              </el-descriptions>
            </diV>

          </template>
        </el-table-column>
        <el-table-column prop="userName" header-align="center" align="center" label="姓名"/>
        <el-table-column prop="gender" header-align="center" align="center" label="性别"/>
        <el-table-column prop="gender" header-align="center" align="center" label="性别">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.medicalRecordQuality.id == null" type="text" size="small">未分析</el-tag>
            <el-tag v-else type="success" size="small">已分析</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="birthDay" header-align="center" align="center" label="出生日期"/>
        <el-table-column prop="age" header-align="center" align="center" label="年龄"/>
        <el-table-column prop="ethnicity" header-align="center" align="center" label="民族"/>
        <el-table-column prop="identificationType" header-align="center" align="center" label="证件类型">

        </el-table-column>
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
            <el-button v-if="scope.row.medicalRecordQuality.id == null" type="text" size="small" @click="addQuality(scope.row.id)">添加病案质量评价</el-button>
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

    <el-dialog title="病案质量分析" :visible.sync="dialogFormVisible">
      <el-form :model="medicalRecordQuality">
        <el-form-item label="评分" label-width="100px">
          <el-rate
            style="margin-top: 10px"
            v-model="medicalRecordQuality.score"
            show-text>
          </el-rate>
        </el-form-item>
        <el-form-item label="评价" label-width="100px">
          <el-input
            type="textarea"
            :rows="4"
            placeholder="请输入内容"
            v-model="medicalRecordQuality.evaluate">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialogFormVisible">取 消</el-button>
        <el-button type="primary" @click="onsubmitData">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>


<script>

import medicalRecordQualityApi from '@/api/medical/medicalRecordQuality'

export default {
  // 定义数据
  data() {
    return {
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        userName: '',
        identificationNumber: ''
      },
      dataList: {},
      dataListLoading: false,

      medicalRecordQuality: {
        admissionRegistrationId: null,
        score: 0,
        evaluate: ''
      },
      dialogFormVisible: false,
    }
  },

  //当页面加载时获取数据
  created() {
    this.getDataList()
  },

  methods: {
    // 获取数据列表
    getDataList(page = 1) {
      this.dataListLoading = true
      this.queryParams.pageNo = page
      medicalRecordQualityApi.list(this.queryParams)
        .then(res => {
          this.dataList = res.data
          this.dataListLoading = false
        })
    },
    handleSizeChange(value) {
      this.queryParams.pageSize = value
      this.getDataList()
    },
    addQuality(id) {
      this.medicalRecordQuality.admissionRegistrationId = id
      this.dialogFormVisible = true
    },
    closeDialogFormVisible(){
      this.medicalRecordQuality = {
        admissionRegistrationId: null,
          score: 0,
          evaluate: ''
      }
      this.dialogFormVisible = false
    },
    onsubmitData(){
      medicalRecordQualityApi.add(this.medicalRecordQuality)
      .then(res => {
        this.$message.success("评价成功")
        this.medicalRecordQuality = {
          admissionRegistrationId: null,
          score: 0,
          evaluate: ''
        }
        this.dialogFormVisible = false
        this.getDataList()
      })
    }
  }
}
</script>
