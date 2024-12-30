<template>
    <div class="app-container">
        <div class="search-div">
            <el-row>
                <span style="font-size: 15px; margin-top: 5px">用户名：</span>
                <el-input
                    v-model="queryParams.username"
                    placeholder="请输入用户名"
                    size="small"
                    clearable
                    style="width: 200px"
                >
                </el-input>
                <span
                    style="font-size: 15px; margin-top: 5px; margin-left: 15px"
                    >医师职位：</span
                >
                <el-select
                    v-model="queryParams.position"
                    size="small"
                    placeholder="选择职称"
                    style="width: 200px"
                    clearable
                >
                    <el-option
                        v-for="(item, index) in patientEnums"
                        :key="index"
                        align="center"
                        :label="item.label"
                        :value="item.value"
                    >
                    </el-option>
                </el-select>
                <el-button
                    type="primary"
                    icon="el-icon-search"
                    size="mini"
                    style="margin-left: 15px"
                    @click="findList(1)"
                    >搜索
                </el-button>
                <el-button
                    @click="clearSubmitData"
                    icon="el-icon-refresh"
                    size="mini"
                    >重置
                </el-button>
                <el-button
                    type="primary"
                    icon="el-icon-refresh"
                    size="mini"
                    @click="refreshDoctorInfo"
                    >同步医师信息
                </el-button>
            </el-row>
            <el-row style="margin-top: 10px">
                <el-table
                    v-loading="loading"
                    ref="multipleTable"
                    size="small"
                    :data="doctorInfoVo.doctorInfoRos"
                    style="width: 100%; margin-top: 15px"
                >
                    <el-table-column
                        prop="username"
                        label="用户名"
                        align="center"
                        :show-overflow-tooltip="true"
                        width="100"
                    />
                    <el-table-column
                        prop="name"
                        align="center"
                        label="姓名"
                        width="100"
                    />
                    <el-table-column
                        prop="phone"
                        align="center"
                        label="手机"
                        width="110"
                    />
                    <el-table-column
                        prop="headUrl"
                        align="center"
                        label="头像"
                        width="60"
                    >
                        <template slot-scope="scope">
                            <el-image
                                style="width: 65px; height: 65px"
                                :src="scope.row.headUrl"
                                fit="headUrl"
                            ></el-image>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="status"
                        align="center"
                        label="状态"
                        width="100"
                    >
                        <template slot-scope="scope">
                            <el-switch
                                disabled
                                v-model="scope.row.status === 1"
                            ></el-switch>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="position"
                        align="center"
                        label="医师职称"
                        width="120"
                    >
                        <template slot-scope="scope">
                            <el-tag
                                v-if="scope.row.position === 'DOCTOR_POSITION'"
                                >普通医师</el-tag
                            >
                            <el-tag
                                v-if="
                                    scope.row.position === 'ASSISTANT_DOCTOR'
                                "
                                >主治医师</el-tag
                            >
                            <el-tag
                                v-if="
                                    scope.row.position === 'VICE_DIRECTOR'
                                "
                                >副主任医师</el-tag
                            >
                            <el-tag
                                v-if="scope.row.position === 'DIRECTOR'"
                                >主任医师</el-tag
                            >
                            <el-tag
                                v-if="scope.row.position === 'EXPERT_DOCTOR'"
                                >专家医师</el-tag
                            >
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="departmentPatient"
                        align="center"
                        label="负责科室门诊 "
                        width="200"
                    />
                    <el-table-column
                      prop="weekday"
                      align="center"
                      label="工作日"
                      width="100"
                    />
                    <el-table-column
                        prop="introduction"
                        align="center"
                        label="主治简介"
                        width="240"
                    />
                    <el-table-column
                        prop="totalScore"
                        align="center"
                        label="操作"
                    >
                        <template slot-scope="scope">
                            <el-button
                                icon="el-icon-edit"
                                type="primary"
                                size="small"
                                @click="infoDoctor(scope.row.id)"
                                >编辑
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="findList"
                    :current-page="doctorInfoVo.page"
                    :page-sizes="[1, 5, 10, 15, 20, 30, 40, 50, 100]"
                    :page-size="doctorInfoVo.limit"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="doctorInfoVo.total"
                    style="padding: 30px 0; text-align: center"
                >
                </el-pagination>
            </el-row>
        </div>

        <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="80%">
            <el-descriptions
                style="margin-top: 20px"
                class="margin-top"
                :column="3"
                border
            >
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        医师职称
                    </template>
                    <el-select
                        v-model="doctorInfo.position"
                        size="small"
                        placeholder="选择职称"
                        style="width: 200px"
                        clearable
                    >
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
                        <i class="el-icon-mobile-phone"></i>
                        所属科室门诊
                    </template>
                    <el-cascader
                        style="width: 400px"
                        v-model="doctorInfo.departmentPatient"
                        :options="options"
                        :props="props"
                        clearable
                    ></el-cascader>
                </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-mobile-phone"></i>
                  工作日期
                </template>
                <el-select
                  v-model="doctorInfo.weekday"
                  multiple
                  placeholder="请选择"
                >
                  <el-option label="星期一" value="星期一"></el-option>
                  <el-option label="星期二" value="星期二"></el-option>
                  <el-option label="星期三" value="星期三"></el-option>
                  <el-option label="星期四" value="星期四"></el-option>
                  <el-option label="星期五" value="星期五"></el-option>
                  <el-option label="星期六" value="星期六"></el-option>
                  <el-option label="星期日" value="星期日"></el-option>
                </el-select>
              </el-descriptions-item>
                <el-descriptions-item :span="3">
                    <template slot="label">
                        <i class="el-icon-tickets"></i>
                        主治简介
                    </template>
                    <el-input
                        v-model="doctorInfo.introduction"
                        type="textarea"
                        :rows="4"
                        placeholder="请输入内容"
                    >
                    </el-input>
                </el-descriptions-item>
            </el-descriptions>

            <span slot="footer" class="dialog-footer">
                <el-button
                    @click="dialogVisible = false"
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

import doctorApi from "@/api/information/doctor";
import departmentApi from "@/api/hosp/department";

export default {
    data() {
        return {
            queryParams: {
                page: 1,
                limit: 10,
                username: '',
                position: ''
            },
            patientEnums: [],

            doctorInfoVo: {},
            loading: false,

            dialogVisible: false,
            options: [],
            props: { multiple: true },
            doctorInfo: {},
        }
    },

    // 生命周期函数：内存准备完毕，页面尚未渲染
    created() {
    },

    // 生命周期函数：内存准备完毕，页面渲染成功
    mounted() {
      this.findList()
      this.getPatientEnums()
      this.findDepPatient()
    },

    methods: {
        findList(page = 1) {
            this.loading = true
            this.queryParams.page = page
            doctorApi.findList(this.queryParams)
                .then(res => {
                  console.log(res)
                    this.doctorInfoVo = res.data
                    this.loading = false
                })
        },
        clearSubmitData() {
            this.queryParams.username = ''
            this.queryParams.position = ''
            this.patientList = []
            this.findList()
        },
        handleSizeChange(value) {
            this.queryParams.limit = value
            this.findList()
        },
        findDepPatient() {
            departmentApi.depPatient().then(res => {
                this.options = res.data
            })
        },
        infoDoctor(id) {
            if (id == null) {
                this.$message({
                    message: '医师详情未同步，请先同步详情信息！！！',
                    type: 'warning'
                });
            } else {
                this.dialogVisible = true
                doctorApi.infoDoctorById(id).then(res => {
                    console.log(res)
                    if (res.data != null) {
                        this.doctorInfo = res.data
                    }
                })
            }

        },
        saveOrUpdate() {
            doctorApi.updateDoctor(this.doctorInfo)
                .then(res => {
                    if (res.code == 200) {
                        this.$message({
                            message: '信息修改成功',
                            type: 'success'
                        });
                        this.dialogVisible = false
                        this.findList()
                    } else {
                        this.$message.error("网路异常，操作失败！");
                    }
                })
        },

        getPatientEnums() {
            doctorApi.patientEnums().then(res => {
                this.patientEnums = res.data
            })
        },
        // 同步医师信息
        refreshDoctorInfo() {
            this.loading = true
            doctorApi.refreshInfo().then(res => {
                this.findList()
                this.loading = false
                this.$message({
                    message: '信息同步成功',
                    type: 'success'
                });
            })
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
