<template>
    <div class="app-container">
        <div class="search-div">
            <el-form label-width="70px" size="small">
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="用户账号">
                            <el-input
                                style="width: 95%"
                                v-model="searchObj.username"
                                placeholder="用户账号"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="操作时间">
                            <el-date-picker
                                v-model="createTimes"
                                type="datetimerange"
                                range-separator="至"
                                start-placeholder="开始时间"
                                end-placeholder="结束时间"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                style="margin-right: 10px; width: 100%"
                            />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row style="display: flex">
                    <el-button
                        type="primary"
                        icon="el-icon-search"
                        size="mini"
                        @click="fetchData()"
                        >搜索</el-button
                    >
                    <el-button
                        icon="el-icon-refresh"
                        size="mini"
                        @click="resetData"
                        >重置</el-button
                    >
                    <el-button
                        type="danger"
                        class="btn-add"
                        size="mini"
                        @click="removeBeatch"
                        >批量删除</el-button
                    >
                </el-row>
            </el-form>
        </div>

        <!-- 列表 -->
        <el-table
            v-loading="listLoading"
            :data="list"
            stripe
            border
            style="width: 100%; margin-top: 10px"
            @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55"> </el-table-column>

            <el-table-column label="序号" width="70" align="center">
                <template slot-scope="scope">
                    {{ (page - 1) * limit + scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column
                align="center"
                prop="username"
                label="登录账号"
                width="180"
            />
            <el-table-column
                align="center"
                prop="ipaddr"
                label="登录Id地址"
                width="110"
            />
            <el-table-column
                align="center"
                prop="status"
                label="状态"
                width="110"
            >
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.status == 1"
                        >成功</el-tag
                    >
                    <el-tag v-else type="danger">失败</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                align="center"
                prop="msg"
                label="提示信息"
                width="110"
            />
            <el-table-column
                align="center"
                prop="createTime"
                label="创建时间"
            />
        </el-table>

        <!-- 分页组件 -->

        <el-pagination
            @size-change="handleSizeChange"
            @current-change="fetchData"
            :current-page="page"
            :page-sizes="[5, 10, 15, 20, 30, 40, 50, 100]"
            :page-size="limit"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            style="padding: 30px 0; text-align: center"
        >
        </el-pagination>
    </div>
</template>

<script>
import api from '@/api/system/sysLog'

export default {
    data() {
        return {
            listLoading: true, // 数据是否正在加载
            list: null, // banner列表
            total: 0, // 数据库中的总记录数
            page: 1, // 默认页码
            limit: 10, // 每页记录数
            searchObj: {}, // 查询表单对象

            createTimes: [],

            multipleSelection: [],
        }
    },

    // 生命周期函数：内存准备完毕，页面尚未渲染
    created() {
        this.fetchData()
    },

    // 生命周期函数：内存准备完毕，页面渲染成功
    mounted() {
    },

    methods: {
        // 加载banner列表数据
        fetchData(page = 1) {
            this.page = page
            if (this.createTimes && this.createTimes.length == 2) {
                this.searchObj.createTimeBegin = this.createTimes[0]
                this.searchObj.createTimeEnd = this.createTimes[1]
            }

            api.logPageByList(this.page, this.limit, this.searchObj).then(
                response => {
                    //this.list = response.data.list
                    this.list = response.data.records
                    this.total = response.data.total

                    // 数据加载并绑定成功
                    this.listLoading = false
                }
            )
        },

        // 重置查询表单
        resetData() {
            this.searchObj = {}
            this.createTimes = []
            this.fetchData()
        },
        handleSizeChange(value) {
            this.limit = value
            this.fetchData()
        },

        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        removeBeatch() {
            if (this.multipleSelection.length === 0) {
                this.$message({
                    message: '必须选择要删除的日志记录',
                    type: 'warning'
                });
            } else {
                this.$confirm('此操作将永久删除该日志记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // 点击确定，远程调用ajax
                    // 遍历selection，将id取出放入id列表
                    var idList = []
                    this.multipleSelection.forEach(item => {
                        idList.push(item.id)
                    })
                    console.log(idList)
                    // 调用api
                    return api.delete(idList)
                }).then((response) => {
                    this.fetchData()
                    this.$message.success(response.message)
                }).catch(error => {
                    if (error === 'cancel') {
                        this.$message.info('取消删除')
                    }
                })
            }
        }
    }
}
</script>