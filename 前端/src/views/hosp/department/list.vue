<template>
  <div class="app-container">
    <div class="search-div">
      <el-row>
        <span style="font-size: 15px; margin-top: 5px">科室：</span>
        <el-select
          v-model="queryParams.depName"
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
        <span
          style="font-size: 15px; margin-top: 5px; margin-left: 15px"
        >门诊：</span
        >
        <el-select
          v-model="queryParams.patientName"
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
      </el-row>
      <el-row style="margin-top: 10px">
        <div style="display: flex; justify-content: space-between">
          <div>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="findList"
            >搜索
            </el-button>
            <el-button
              @click="clearSubmitData"
              icon="el-icon-refresh"
              size="mini"
            >重置
            </el-button>
            <el-button
              icon="el-icon-circle-plus"
              type="primary"
              size="small"
              @click="addDepPatient(null)"
            >添加科室
            </el-button>
          </div>
          <div>
            <a
              href="http://localhost:8800/admin/hosp/hospDepartment/excel/export_template"
              target="_blank"
            >
              <el-button
                type="primary"
                icon="el-icon-download"
                size="mini"
              >下载模板
              </el-button>
            </a>
            <a
              href="http://localhost:8800/admin/hosp/hospDepartment/excel/export_department"
              target="_blank"
              style="margin-left: 10px"
            >
              <el-button
                icon="el-icon-download"
                size="mini"
                type="primary"
              >导出信息
              </el-button>
            </a>
            <el-button
              type="success"
              icon="el-icon-upload"
              size="mini"
              style="margin-left: 10px"
              @click="importHospDepartment"
            >导入
            </el-button>
          </div>
        </div>

        <template>
          <el-dialog
            title="导入"
            :visible.sync="dialogImportVisible"
            width="400px"
            v-loading="fullscreenLoading"
            element-loading-text="拼命上传中"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
          >
            <el-form label-position="right" label-width="110px">
              <el-form-item label="文件">
                <el-upload
                  :multiple="false"
                  :on-success="onUploadSuccess"
                  :on-error="onUploadError"
                  :on-change="onChange"
                  :action="'http://localhost:8800/admin/hosp/hospDepartment/excel/import/department'"
                  class="upload-demo"
                >
                  <el-button size="small" type="primary"
                  >点击上传
                  </el-button>
                  <div slot="tip" class="el-upload_tip">
                    只能上传excel文件,且不超过500kb<br />
                    <span
                      style="
                                                color: red;
                                                font-weight: bold;
                                            "
                    >上传过程中不要随意取消操作，上传完成自动会关闭弹窗！</span
                    >
                  </div>
                </el-upload>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogImportVisible = false">
                取消
              </el-button>
            </div>
          </el-dialog>
        </template>

        <el-table
          v-loading="loading"
          ref="multipleTable"
          :data="hospDepartmentList"
          style="width: 100%; margin-top: 15px"
          tooltip-effect="dark"
        >
          <el-table-column
            prop="hospDepartmentRo"
            align="center"
            type="expand"
            label="科室/门诊"
            width="100"
          >
            <template slot-scope="scope">
              <el-table
                :data="scope.row.hospDepartmentRo"
                border
                width="100%"
              >
                <el-table-column
                  align="center"
                  label="序号"
                  type="index"
                  width="50"
                >
                </el-table-column>
                <el-table-column
                  align="center"
                  prop="patientName"
                  :show-overflow-tooltip="true"
                  label="门诊名"
                  width="200"
                >
                  <template slot-scope="scope">
                    <span style="font-weight: bold">{{
                        scope.row.patientName
                      }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  sortable
                  align="center"
                  prop="patientCode"
                  label="门诊编号"
                  width="150"
                />
                <el-table-column
                  align="center"
                  prop="intro"
                  label="门诊介绍"
                />
                <el-table-column
                  align="center"
                  prop="status"
                  label="状态"
                  width="200"
                >
                  <template slot-scope="scope">
                    <el-switch
                      style="display: block"
                      v-model="scope.row.status"
                      active-color="#13ce66"
                      inactive-color="#ff4949"
                      active-text="启用"
                      inactive-text="禁用"
                      :active-value="1"
                      :inactive-value="0"
                      @change="updateStatus(scope.row.status,null,scope.row.id)"
                    >
                    </el-switch>
                  </template>
                </el-table-column>
                <el-table-column align="center" label="操作">
                  <template slot-scope="scope2">
                    <el-button
                      type="primary"
                      icon="el-icon-edit"
                      size="small"
                      circle
                      @click="editDepPatient(scope.row,scope2.row)"
                    />
                    <el-button
                      type="danger"
                      icon="el-icon-delete"
                      size="small"
                      circle
                      @click="deleteById(scope2.row.id)"
                    />
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="depName"
            label="科室"
            :show-overflow-tooltip="true"
            width="300"
          >
            <template slot-scope="scope">
              <span style="font-weight: bold">{{
                  scope.row.depName
                }}</span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="depCode"
            sortable
            label="科室编号"
            width="200"
          />
          <el-table-column
            prop="status"
            sortable
            align="center"
            label="状态"
            width="200"
          >
            <template slot-scope="scope">
              <el-switch
                style="display: block"
                v-model="scope.row.status"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="启用"
                inactive-text="禁用"
                :active-value="1"
                :inactive-value="0"
                @change="updateStatus(scope.row.status,scope.row.depName,null )"
              >
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column
            prop="totalScore"
            align="center"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button
                icon="el-icon-circle-plus"
                type="primary"
                size="small"
                @click="addDepPatient(scope.row)"
              >添加门诊
              </el-button>
              <el-button
                icon="el-icon-edit"
                type="warning"
                size="small"
                @click="editDepPatient(scope.row, null)"
              >修改科室
              </el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="small"
                circle
                @click="deleteByDepName(scope.row.depName)"
              />
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </div>

    <dep-add
      ref="add"
      :dialog-visible="dialog.isVisible"
      :type="dialog.type"
      @close="addClose"
      @success="addSuccess"
    />

    <dep-edit
      ref="edit"
      :dialog-visible="editDialog.isVisible"
      :type="editDialog.type"
      @close="editClose"
      @success="editSuccess"
    />
  </div>
</template>

<script>

import departmentApi from '@/api/hosp/department'
import DepAdd from '@/views/hosp/department/DepAdd'
import DepEdit from '@/views/hosp/department/DepEdit'

export default {
  components: { DepEdit, DepAdd },
  data() {
    return {
      queryParams: {
        depName: '',
        patientName: ''
      },
      depList: [],
      patientList: [],

      dialogImportVisible: false,
      fullscreenLoading: false,

      hospDepartmentList: [{
        hospDepartmentRo: []
      }],
      loading: false,

      dialog: {
        isVisible: false,
        type: 'add'
      },
      editDialog: {
        isVisible: false,
        type: 'add'
      }
    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
  },

  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
    this.getDepList()
    this.findList()
  },

  methods: {
    findList() {
      this.loading = true
      departmentApi.depList(this.queryParams).then(res => {
        console.log(res)
        this.hospDepartmentList = res.data
        this.loading = false
      })
    },
    addClose() {
      this.dialog.isVisible = false
    },
    addSuccess() {
      this.findList()
    },
    addDepPatient(row) {
      this.dialog.type = 'add'
      this.dialog.isVisible = true
      this.$refs.add.setDep(row)
    },
    deleteByDepName(depName) {
      this.$confirm('此操作将删除科室和所有门诊信息，且不可恢复，确定删除吗', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        departmentApi.removeByDepName({ depName: depName }).then(response => {
          if (response.code === 200) {
            this.$message({
              message: '删除成功！！！',
              type: 'success'
            })
            this.findList()
          } else {
            this.$message.error('删除失败！！！')
          }
        })
      }).catch(() => {
      })
    },
    deleteById(id) {
      console.log(id)
      this.$confirm('此操作将删除门诊信息，确定删除吗', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        departmentApi.removeById({ id: id }).then(response => {
          if (response.code === 200) {
            this.$message({
              message: '删除成功！！！',
              type: 'success'
            })
            this.findList()
          } else {
            this.$message.error('删除失败！！！')
          }
        })
      }).catch(() => {
      })
    },
    updateStatus(status, depName, id) {
      if (depName != null) {
        this.$confirm('此操作将影响科室下的所有门诊信息，确定继续吗', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          departmentApi.updateStatus({
            id: id,
            depName: depName,
            status: status
          }).then(response => {
            if (response.code === 200) {
              this.$message({
                message: '状态修改成功！！！',
                type: 'success'
              })
              this.findList()
            } else {
              this.$message.error('状态失败！！！')
            }
          })
        }).catch(() => {
        })
      } else {
        this.$confirm('确实修改门诊状态吗', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          departmentApi.updateStatus({
            id: id,
            depName: depName,
            status: status
          }).then(response => {
            if (response.code === 200) {
              this.$message({
                message: '状态修改成功！！！',
                type: 'success'
              })
              this.findList()
            } else {
              this.$message.error('状态修改失败！！！')
            }
          })
        }).catch(() => {
        })
      }
    },

    editClose() {
      this.editDialog.isVisible = false
    },
    editSuccess() {
      this.findList()
    },
    editDepPatient(dep, data) {
      this.editDialog.type = 'add'
      this.editDialog.isVisible = true
      this.$refs.edit.setDep(dep, data)
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
    clearSubmitData() {
      this.queryParams = {
        depName: '',
        patientName: ''
      }
      this.patientList = []
    },

    //文件上传成功执行的方法
    onUploadSuccess() {
      //关闭弹框
      this.dialogImportVisible = false
      this.fullscreenLoading = false
      this.$message({
        message: '上传成功！！！',
        type: 'success'
      })
      //刷新页面
      this.findList()
    },
    onUploadError() {
      this.fullscreenLoading = false
      this.$message.error('上传失败！！！')
    },
    onChange() {
      this.fullscreenLoading = true
    },
    importHospDepartment() {
      this.dialogImportVisible = true
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
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
