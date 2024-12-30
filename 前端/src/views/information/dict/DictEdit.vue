<template>
    <el-dialog
        :close-on-click-modal="false"
        :close-on-press-escape="true"
        :title="title"
        :type="type"
        :visible.sync="isVisible"
        width="600px"
        top="150px"
    >
        <el-form
            ref="form"
            :model="dict"
            :rules="rules"
            label-position="right"
            label-width="120px"
        >
            <el-form-item label="父级ID" prop="parentId">
                <el-input disabled v-model="dict.parentId" />
            </el-form-item>
            <el-form-item label="名称" prop="dictName">
                <el-input v-model="dict.dictName" placeholder="请输入数据字典名称" />
            </el-form-item>
          <el-form-item label="编码" prop="dictCode">
            <el-input
              v-if="dict.id == ''"
              v-model="dict.dictCode"
              onkeyup="value=value.replace(/[^a-z|A-Z]/g,'').toUpperCase()"
              placeholder="唯一标识。注意：建议大写如：ROOT，一但添加不可修改！" />
            <el-input
              disabled
              v-else
              v-model="dict.dictCode" />
          </el-form-item>
          <el-form-item v-if="dict.id != ''" label="数据值" prop="dictValue">
            <el-input disabled v-model="dict.dictValue" placeholder="请输入数据字典名称" />
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
            <el-button plain type="warning" @click="isVisible = false">取消</el-button>
            <el-button plain type="primary" @click="submitData">确定</el-button>
        </div>
    </el-dialog>
</template>
<script>

import dictApi from '@/api/information/dict'

export default {
    components: {},
    props: {
        dialogVisible: {
            type: Boolean,
            default: false
        },
        type: {
            type: String,
            default: 'add'
        }
    },
    data() {
        //这里存放数据
        return {
            dict: this.initDict(),
            moduleId: 0,
            plan:{},

            //表单校验规则
            rules: {
                dictName: [
                    { required: true, message: '请输入数据字典名称名称', trigger: 'blur' }
                ]
            }
        };
    },
    //计算属性 类似于 data 概念
    computed: {
        isVisible: {
            get() {
                return this.dialogVisible
            },
            set() {
                this.close()
                this.reset()
            }
        },
        title() {
            return this.type === 'add'
                ? "添加"
                : "修改"
        }
    },
    //监控 data 中的数据变化
    watch: {},
    //方法集合
    methods: {
        initDict() {
            return {
              id: '',
              parentId: '',
              dictCode: '',
              dictName: '',
              dictValue: '',
            }
        },
        setDict(parentId,dictId) {
            if (dictId != null) {
                this.dict.id = dictId;
                this.dict.parentId = parentId;
                // 调用方法去查询信息
                dictApi.findById(dictId).then(res => {
                    this.dict = res.data;
                })
            }else{
              //添加操作
              this.dict.parentId = parentId
            }
        },
        close() {
            this.$emit('close')
        },
        reset() {
            // 先清除校验，再清除表单，不然有奇怪的bug
            this.$refs.form.clearValidate()
            this.$refs.form.resetFields()
            this.dict = this.initDict()
        },
        //提交表单数据
        submitData() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    this.editSubmit()
                    this.dict = this.initDict();
                } else {
                    return false
                }
            })
        },
        editSubmit() {
            const vm = this
            if (vm.type === 'add') {
                vm.save()
            } else {
                vm.update()
            }
        },
        save() {
            dictApi.saveDict(this.dict).then(res => {
                if (res.code = 200) {
                    this.isVisible = false
                    this.$message({
                        message: '添加成功！！！',
                        type: 'success'
                    });
                    this.dict = this.initDict();
                    this.$emit('success')
                } else {
                    this.$message.error('添加失败！！！');
                }
            })
        },
        update() {
            dictApi.updateById(this.dict).then(response => {
                if (response.code = 200) {
                    this.isVisible = false
                    this.$message({
                        message: "修改成功",
                        type: 'success'
                    })
                    this.dict = this.initDict();
                    this.$emit('success')
                } else {
                    this.$message.error('修改失败！！！');
                }
            })
        }
    },
    //生命周期 - 创建完成（可以访问当前 this 实例）
    created() {

    },
    //生命周期 - 挂载完成（可以访问 DOM 元素）
    mounted() {


    }
}
</script>
<style lang="scss" scoped>
//@import url(); 引入公共 css 类
</style>
