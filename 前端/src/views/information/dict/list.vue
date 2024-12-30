<template>
  <div>
    <!-- 作业信息 -->
    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :xs="24" :sm="24">
        <el-card class="box-card">
          <div slot="header" class="header">
            <div>
              <a @click="dictImport">
                <el-button
                  :disabled="$hasBP('bnt.dict.import') === false"
                  type="success"
                  size="small"
                  icon="el-icon-bottom"
                  round
                >导入Excel</el-button>
              </a>
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
                  <el-form
                    label-position="right"
                    label-width="110px"
                  >
                    <el-form-item label="文件">
                      <el-upload
                        :multiple="false"
                        :on-success="onUploadSuccess"
                        :on-error="onUploadError"
                        :on-change="onChange"
                        :action="'http://localhost:8800/admin/info/dict/anno/excel/import_dict'"
                        class="upload-demo"
                      >
                        <el-button
                          size="small"
                          type="primary"
                        >点击上传
                        </el-button>
                        <div
                          slot="tip"
                          class="el-upload_tip"
                        >
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
                    <el-button
                      @click="dialogImportVisible = false"
                    >
                      取消
                    </el-button>
                  </div>
                </el-dialog>
              </template>
              <a
                :disabled="$hasBP('bnt.dict.export') === false"
                href="http://localhost:8800/admin/info/dict/anno/excel/export_dict"
                target="_blank"
              >
                <el-button
                  type="primary"
                  size="small"
                  icon="el-icon-top"
                  round
                >导出Excel</el-button
                >
              </a>
              <a
                :disabled="$hasBP('bnt.dict.export') === false"
                href="http://localhost:8800/admin/info/dict/anno/excel/export_template"
                target="_blank"
              >
                <el-button
                  type="primary"
                  size="small"
                  icon="el-icon-top"
                  round
                >下载模板</el-button
                >
              </a>
            </div>

            <el-button
              :disabled="$hasBP('bnt.dict.delete') === false"
              size="small"
              type="danger"
              icon="el-icon-delete"
              @click="deleteBeach"
            >批量删除</el-button
            >
          </div>
          <div>
            <el-input
              placeholder="输入关键字进行过滤"
              clearable
              v-model="filterText"
              style="margin-bottom: 10px"
            >
            </el-input>
            <span style="">
                            <el-button
                              size="small"
                              type="primary"
                              icon="el-icon-circle-plus"
                              @click="add(0, null)"
                            >添加根节点</el-button
                            >
                        </span>
            <el-tree
              class="filter-tree"
              :data="dictList"
              show-checkbox
              node-key="id"
              :default-expanded-keys="expandedKey"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
            >
              <span
                class="custom-tree-node"
                slot-scope="{ node, data }"
              >
                  <span>{{ data.dictName }}</span>
                  <span>
                      <i
                        :disabled="$hasBP('bnt.dict.add') === false"
                        class="el-icon-circle-plus table-operation"
                        style="color: #0691ff"
                        @click="add(data.id, null)"
                      >添加</i>
                      <el-divider
                        direction="vertical"
                      ></el-divider>
                      <i
                        :disabled="$hasBP('bnt.dict.update') === false"
                        class="el-icon-edit table-operation"
                        style="color: #ff8913"
                        @click="edit(node.parent.data.id, data.id)"
                      >修改</i
                      >
                      <el-divider
                        v-if="data.children.length == 0"
                        direction="vertical"
                      ></el-divider>
                      <i
                        :disabled="$hasBP('bnt.dict.delete') === false"
                        v-if="data.children.length == 0"
                        size="mini"
                        class="el-icon-delete table-operation"
                        style="color: #e05635"
                        @click="remove(node, data)"
                      >删除</i
                      >
                  </span>
              </span>
            </el-tree>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 添加修改的对话框 -->
    <dict-edit
      ref="edit"
      :dialog-visible="dialog.isVisible"
      :type="dialog.type"
      @close="editClose"
      @success="editSuccess"
    />
  </div>
</template>

<script>

import dictApi from '@/api/information/dict'
import DictEdit from '@/views/information/dict/DictEdit'

export default {
  //import 引入的组件需要注入到对象中才能使用
  components: { DictEdit },
  data() {
    return {
      filterText: '',
      expandedKey: [],
      dictList: [],
      dialogImportVisible: false,  //设置弹框是否弹出

      dialog: {
        isVisible: false,
        type: 'add'
      },
      fullscreenLoading: false
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  mounted() {
    this.findList()
  },

  methods: {
    findList() {
      this.dialogImportVisible = false
      this.fullscreenLoading = false
      dictApi.findList().then(response => {
        console.log(response)
        this.dictList = response.data
        //设置默认展示的节点
        /*this.dictList.forEach(d => {
          this.expandedKey.push(d.id)
        })*/
      })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.dictName.indexOf(value) !== -1
    },
    // 添加节点
    append(data) {
      console.log(data)
    },
    // 删除节点
    remove(node, data) {
      this.$confirm('确定要删除该内容吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const dictIds = []
        dictIds.push(data.id)
        dictApi.deleteById(dictIds).then(response => {
          if (response.code === 200) {
            this.$message({
              message: '删除成功！！！',
              type: 'success'
            })
            this.findList()
            this.expandedKey = []
            this.expandedKey.push(node.parent.data.id)
          } else {
            this.$message.error('删除失败！！！')
          }
        })
      }).catch(() => {
      })
    },
    // 批量删除
    deleteBeach() {
      let checkedNodes = this.$refs.tree.getCheckedNodes()
      if (checkedNodes.length > 0) {
        this.$confirm('确定要删除该内容吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let dictIds = []

          this.extractIds(checkedNodes, dictIds)

          dictApi.deleteById(dictIds).then(response => {
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
      } else {
        this.$message({
          message: '请选择要删除的节点！！！',
          type: 'warning'
        })
      }

    },
    extractIds(data, ids) {
      for (let i = 0; i < data.length; i++) {
        ids.push(data[i].id);
        if (data[i].children && data[i].children.length > 0) {
          this.extractIds(data[i].children, ids);
        }
      }
    },
    editClose() {
      this.dialog.isVisible = false
    },
    editSuccess() {
      this.findList()
    },
    add(parentId, dictId) {
      this.dialog.type = 'add'
      this.dialog.isVisible = true
      this.$refs.edit.setDict(parentId, dictId)
      this.expandedKey = []
      this.expandedKey.push(parentId)
    },
    edit(parentId, dictId) {
      this.dialog.type = 'edit'
      this.dialog.isVisible = true
      this.$refs.edit.setDict(parentId, dictId)
      this.expandedKey = []
      this.expandedKey.push(dictId)
    },
    dictImport() {
      this.dialogImportVisible = true
    },
    //文件上传成功执行的方法
    onUploadSuccess() {
      //关闭弹框
      this.fullscreenLoading = false
      this.$message({
        message: '上传成功！！！',
        type: 'success'
      })
      this.dialogImportVisible = false
      //刷新页面
      this.findList()
    },
    onUploadError() {
      this.fullscreenLoading = false
      this.$message.error('上传失败！！！')
    },
    onChange() {
      this.fullscreenLoading = true
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
</style>
