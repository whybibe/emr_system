<template>
  <div class="app-container">
    <!-- 工具条 -->
    <div class="tools-div">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="疾病名称">
          <el-input v-model="queryParams.caseName" size="small" placeholder="请输入查询的疾病名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="疾病编码">
          <el-input v-model="queryParams.caseNum" size="small" placeholder="疾病编码" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="findList()">查询</el-button>
          <el-button size="small" type="success" @click="addCase(null)">新增疾病</el-button>
        </el-form-item>
      </el-form>

      <el-table
        :data="dataList.records"
        border
        size="mini"
        style="width: 100%;"
      >
        <el-table-column prop="id" header-align="center" align="center" label="ID" width="50"/>
        <el-table-column prop="registrarId" header-align="center" align="center" label="首次登记人ID"/>
        <el-table-column prop="registrarName" header-align="center" align="center" label="首次登记人姓名"/>
        <el-table-column prop="caseName" header-align="center" align="center" label="病例名称"/>
        <el-table-column prop="caseNum" header-align="center" align="center" label="疾病编码"/>
        <el-table-column prop="createTime" header-align="center" align="center" label="创建时间"/>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="200"
          label="操作"
        >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="addCase(scope.row)">编辑</el-button>
            <el-button type="text" style="color: red" size="small" @click="deleteById(scope.row.id)">删除</el-button>
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


    <el-dialog title="新增/编辑疾病" :visible.sync="dialogFormVisible" width="400px">

      <!-- 工具条 -->
      <el-form :inline="true" :model="caseData">
        <el-form-item label="疾病名称">
          <el-input v-model="caseData.caseName" size="small" placeholder="疾病名称" style="width: 240px;" clearable></el-input>
        </el-form-item>
        <el-form-item label="疾病编码">
          <el-input v-model="caseData.caseNum" size="small" placeholder="疾病编码" style="width: 240px;" clearable></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitData">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>


<script>

import caseLibraryApi from '@/api/medical/caseLibrary'

export default {
  // 定义数据
  data() {
    return {
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        caseName: '',
        caseNum: ''
      },
      dataList: [],

      dialogFormVisible: false,
      caseData: {
        caseName: '',
        caseNum: ''
      }
    }
  },

  //当页面加载时获取数据
  created() {
    this.findList()
  },
  methods: {
    findList() {
      caseLibraryApi.listPage(this.queryParams)
        .then(res => {
          this.dataList = res.data
        })
    },
    handleSizeChange(size) {
      this.queryParams.pageSize = size
      this.findList()
    },
    addCase(data) {
      if (data == null) {
        this.caseData = {
          caseName: '',
          caseNum: ''
        }
      } else {
        this.caseData = data
      }
      this.dialogFormVisible = true
    },
    submitData() {
      caseLibraryApi.addCaseLibrary(this.caseData)
      .then(res => {
        if (res.code == 200) {
          this.$message({
            type: 'success',
            message: '保存成功'
          })
          this.findList()
          this.dialogFormVisible = false
        } else {
          this.$message({
            type: 'error',
            message: '保存失败'
          })
        }
      })
    },
    // 删除病例
    deleteById(id) {
      this.$confirm('此操作将永久删除该病例, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        caseLibraryApi.deleteCaseLibrary(id)
          .then(res => {
            if (res.code == 200) {
              this.findList()
              this.$message({
                type: 'success',
                message: '删除成功!'
             })
           }
         })
       })
     }
  }
}
</script>
