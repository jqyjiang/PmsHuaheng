<template>
    <div class="app-container">
        <div style="display:flex;border: 1px solid rgb(220, 220, 220);">
            <div style="width: 23%;border-bottom: 1px solid rgb(220, 220, 220);border-right: 1px solid rgb(220, 220, 220);">
                <h4 style="border-bottom: 1px solid #DCDCDC;padding-bottom: 20px;padding-left: 20px;">供应商分类</h4>

                <el-input style="width: 93%;margin: 0 auto;display: block;" v-model="queryParams.classDescribe"
                    placeholder="请输入分类描述" clearable @keyup.enter.native="handleQuery">
                    <template v-slot:suffix>
                        <i class="el-icon-search" style="position: absolute;top: 12px;right: 24px;" @click="handleIconClick"></i>
                    </template>
                </el-input>
                <el-menu>
                    <el-menu-item v-for="(item, index) in defineList" @click="toggleSelection(item.classCode)" :key="index"
                        :index="index.toString()">
                        {{ item.classDescribe }}
                    </el-menu-item>
                </el-menu>
            </div>
            <div style="width: 77%;">
                <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                    label-width="88px">
                    <el-form-item label="供应商名称" prop="sbiName">
                        <el-input v-model="queryParams.sbiName" placeholder="请输入供应商名称" clearable
                            @keyup.enter.native="handleQuery" />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                    </el-form-item>
                </el-form>

                <el-row :gutter="10" class="mb8">
                    <el-col :span="1.5">
                        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                            v-hasPermi="['supplierpms:classification:add']">新增</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
                            @click="handleUpdate" v-hasPermi="['supplierpms:classification:edit']">修改</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                            @click="handleDelete" v-hasPermi="['supplierpms:classification:remove']">删除</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                            v-hasPermi="['supplierpms:classification:export']">导出</el-button>
                    </el-col>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </el-row>

                <el-table v-loading="loading" :data="classificationList" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="供应商编码" align="center" prop="sdCode" />
                    <el-table-column label="供应商名称" align="center" prop="sbiName" />
                    <el-table-column label="供应商分类" align="center" prop="classDescribe">
                    </el-table-column>
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">/
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                                v-hasPermi="['supplierpms:classification:edit']">修改</el-button>
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                                v-hasPermi="['supplierpms:classification:remove']">删除</el-button>
                        </template>
                    </el-table-column>

                </el-table>
            </div>
        </div>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
            @pagination="getList" />

        <!-- 添加或修改供应商分类定义对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="分类编码" prop="classCode">
                    <el-input v-model="form.classCode" placeholder="请输入分类编码" />
                </el-form-item>
                <el-form-item label="分类描述" prop="classDescribe">
                    <el-input v-model="form.classDescribe" placeholder="请输入分类描述" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>
  
<script>
import { listClassification, getClassification, delClassification, addClassification, updateClassification } from "@/api/supplierpms/classification";
import { listDetails, listFilter, getDetails, delDetails, addDetails, updateDetails } from "@/api/supplierpms/details";
export default {
    name: "Classification",
    data() {
        return {
            // 遮罩层
            loading: true,
            // 选中数组
            ids: [],
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 显示搜索条件
            showSearch: true,
            // 总条数
            total: 0,
            // 供应商分类定义表格数据
            classificationList: [],
            //供应商类型
            defineList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                classCode: null,
                classDescribe: null,
                sbiName: null,
                sdClass: null,
                sdCode:null
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                classCode: [
                    { required: true, message: "分类编码不能为空", trigger: "blur" }
                ],
                classDescribe: [
                    { required: true, message: "分类描述不能为空", trigger: "blur" }
                ]
            }
        };
    },
    created() {
        this.getList();
        this.getList1();
    },
    methods: {
        /** 查询供应商分类定义列表 */
        getList() {
            this.loading = true;
            listClassification(this.queryParams).then(response => {
                this.defineList = response.rows;
                const hasAllOption = this.defineList.some(item => item.classCode === null);

                if (!hasAllOption) {
                    // 如果不存在 "全部" 选项，则添加
                    this.defineList.unshift({ classCode: null, classDescribe: '全部' });
                }
                this.loading = false;
            });
        },
        /** 分类定义列表 */
        getList1() {
            this.loading = true;
            listFilter(this.queryParams).then(response => {
                this.classificationList = response.rows;
                this.total = response.total;
                this.loading = false;
            });
        },
        // 取消按钮
        cancel() {
            this.open = false;
            this.reset();
        },
        // 表单重置
        reset() {
            this.form = {
                classId: null,
                classCode: null,
                classDescribe: null
            };
            this.resetForm("form");
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getList1();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.resetForm("queryForm");
            this.handleQuery();
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            this.ids = selection.map(item => item.sdId)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = "添加供应商分类定义";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const sdId = row.sdId || this.ids
            getDetails(sdId).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = "修改供应商分类定义";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.form.classId != null) {
                        updateClassification(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addClassification(this.form).then(response => {
                            this.$modal.msgSuccess("新增成功");
                            this.open = false;
                            this.getList();
                        });
                    }   
                }
            });
        },
        /**左边搜索列表搜索功能 */
        handleIconClick() {
            console.log("显示一下");
            this.getList()
        },
        /**左边类别列表点击事件 */
        toggleSelection(classCode) {
            //console.log("显示一下" + classCode);
            if (this.queryParams.sdClass == null || this.queryParams.sdClass != classCode) {
                this.queryParams.sdClass = classCode;
                this.getList1();
            } else {

            }
        },
        /** 删除按钮操作 */
        handleDelete(row) {
            const classIds = row.classId || this.ids;
            this.$modal.confirm('是否确认删除供应商分类定义编号为"' + classIds + '"的数据项？').then(function () {
                return delClassification(classIds);
            }).then(() => {
                this.getList();
                this.$modal.msgSuccess("删除成功");
            }).catch(() => { });
        },
        /** 导出按钮操作 */
        handleExport() {
            this.download('supplierpms/classification/export', {
                ...this.queryParams
            }, `classification_${new Date().getTime()}.xlsx`)
        }
    }
};
</script>
  