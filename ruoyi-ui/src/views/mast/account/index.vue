<template>
  <div class="app-container">
    <!-- <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form> -->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mast:account:add']"
        >新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mast:account:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mast:account:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mast:account:export']"
        >导出</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="accountList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="单位ID" align="center" prop="unitId" /> -->
      <!-- <el-table-column label="单位类型名称" align="center" prop="unitTypeName" /> -->
      <el-table-column label="单位类型名称" align="center" prop="unitTypeName" >
        <template slot-scope="scope">
          <span v-for="item in unit_typeList" >
            <template v-if="scope.row.unitTypeName===item.unitTypeId">
              {{item.unitTypeName}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="计算单位编码" align="center" prop="calculationUnitCode" />
      <el-table-column label="计量单位" align="center" prop="meteringUnit" />
      <el-table-column label="来源系统" align="center" prop="sourceSystem" />
      <!-- <el-table-column label="单位转换率" align="center" prop="unitConversionRate" />
      <el-table-column label="精度" align="center" prop="accuracy" /> -->
      <el-table-column label="是否启用" align="center" prop="enable">
       <template slot-scope="scope">
        <el-checkbox
         :value="enableStatus[scope.$index]"
         :disabled="true"
        ></el-checkbox>
       </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mast:account:edit']"
          >修改</el-button> -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mast:account:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改计算单位定义对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="计算单位编码" prop="calculationUnitCode">
          <el-input v-model="form.calculationUnitCode" placeholder="请输入计算单位编码" />
        </el-form-item>
        <el-form-item label="计量单位" prop="meteringUnit">
          <el-input v-model="form.meteringUnit" placeholder="请输入计量单位" />
        </el-form-item>
        <el-form-item label="转换单位" prop="conversionUnits">
          <el-select v-model="form.conversionUnits" placeholder="请选择转换单位" >
            <el-option
              v-for="dict in accountList"
              :key="dict.unitId"
              :label="dict.meteringUnit"
              :value="dict.unitId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="来源系统" prop="sourceSystem">
          <el-input v-model="form.sourceSystem" placeholder="请输入来源系统" />
        </el-form-item>
        <el-form-item label="单位转换率" prop="unitConversionRate">
          <el-input v-model="form.unitConversionRate" placeholder="请输入单位转换率" />
        </el-form-item>
        <el-form-item label="精度" prop="accuracy">
          <el-input v-model="form.accuracy" placeholder="请输入精度" />
        </el-form-item>
        <el-form-item label="计量单位类型" prop="unitTypeName">
          <el-select v-model="form.unitTypeName" placeholder="请选择计量单位类型" >
            <el-option
              v-for="dict in unit_typeList"
              :key="dict.unitTypeId"
              :label="dict.unitTypeName"
              :value="dict.unitTypeId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="启用" prop="enable">
        <el-checkbox v-model="form.enable" ></el-checkbox>
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
import { listAccount, getAccount, delAccount, addAccount, updateAccount,listUnit_type } from "@/api/mast/account";

export default {
  name: "Account",
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
      // 计算单位定义表格数据
      accountList: [],
      unit_typeList: [],
      enableStatus: [], // 用于存储复选框选中状态的数组
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getList1();
  },
  methods: {
    /** 查询计算单位定义列表 */
    getList() {
      this.loading = true;
      listAccount(this.queryParams).then(response => {
        this.accountList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.enableStatus = this.accountList.map((account) => account.enable === 1);
      });
    },
    /** 查询计量单位类型定义列表 */
    getList1() {
      listUnit_type().then(response => {
        this.unit_typeList = response.rows;
        // console.info(this.unit_typeList)
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
        unitId: null,
        unitTypeName: null,
        calculationUnitCode: null,
        meteringUnit: null,
        sourceSystem: null,
        unitConversionRate: null,
        accuracy: null,
        enable: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.unitId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加计算单位定义";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const unitId = row.unitId || this.ids
      getAccount(unitId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改计算单位定义";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.unitId != null) {
            updateAccount(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if(this.form.enable==true){
              this.form.enable=1
            }else{
              this.form.enable=0
            }
            addAccount(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const unitIds = row.unitId || this.ids;
      this.$modal.confirm('是否确认删除计算单位定义编号为"' + unitIds + '"的数据项？').then(function() {
        return delAccount(unitIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mast/account/export', {
        ...this.queryParams
      }, `account_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
