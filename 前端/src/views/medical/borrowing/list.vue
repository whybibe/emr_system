<template>
  <div class="app-container">
    <!-- 工具条 -->
    <div class="tools-div">
      <el-form :inline="true" :model="queryParams">
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
          <el-button size="small" type="primary" @click="findList" >查询</el-button>
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
        <el-table-column prop="borrowingDuration" header-align="center" align="center" label="借阅时间"/>
        <el-table-column prop="borrowingDurationNote" header-align="center" align="center" label="借阅备注"/>
        <el-table-column prop="isReturn" header-align="center" align="center" label="是否归还"/>
        <el-table-column prop="createTime" header-align="center" align="center" label="创建时间"/>
<!--        <el-table-column-->
<!--          fixed="right"-->
<!--          header-align="center"-->
<!--          align="center"-->
<!--          width="200"-->
<!--          label="操作"-->
<!--        >-->
<!--          <template slot-scope="scope">-->
<!--            <el-button  type="text" size="small" @click="returnRecord(scope.row.id)">归还</el-button>-->
<!--          </template>-->
<!--        </el-table-column>-->
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


  </div>
</template>


<script>

import medicalRecordBorrowingApi from '@/api/medical/medicalRecordBorrowing'

export default {
  // 定义数据
  data() {
    return {
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        medicalType: '',
        isReturn: ''
      },
      dataList: [],
    }
  },

  //当页面加载时获取数据
  created() {
    this.findList()
  },
  methods: {
    findList() {
      medicalRecordBorrowingApi.findByUserId(this.queryParams)
      .then(res => {
        this.dataList = res.data
      })
    },
    handleSizeChange(size) {
      this.queryParams.pageSize = size
      this.findList()
    },
    returnRecord(id) {
      medicalRecordBorrowingApi.returnMedicalRecord(id)
        .then(res => {
          this.findList()
          this.$message.success('归还成功')

        })
    }
  }
}
</script>
