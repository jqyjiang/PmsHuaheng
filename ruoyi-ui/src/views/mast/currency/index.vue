<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="币种编码" prop="currencyCode">
        <el-input
          v-model="queryParams.currencyCode"
          placeholder="请输入币种编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="币种名称" prop="currencyName">
        <el-input
          v-model="queryParams.currencyName"
          placeholder="请输入币种名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mast:currency:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mast:currency:edit']"
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
          v-hasPermi="['mast:currency:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mast:currency:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="currencyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="币种ID" align="center" prop="currencyId" /> -->
      <el-table-column label="币种编码" align="center" prop="currencyCode" />
      <el-table-column label="币种名称" align="center" prop="currencyName" />
      <el-table-column label="国家/地区" align="center" prop="countryRegion" />
      <el-table-column label="财务精度" align="center" prop="financialAccuracy" :formatter="formatTaxRate"/>
      <el-table-column label="精度" align="center" prop="accuracy" :formatter="formatTaxRate"/>
      <el-table-column label="货币符号" align="center" prop="currencySymbol" />
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
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mast:currency:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mast:currency:remove']"
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

    <!-- 添加或修改币种对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="币种编码" prop="currencyCode">
          <el-input v-model="form.currencyCode" placeholder="请输入币种编码" />
        </el-form-item>
        <el-form-item label="币种名称" prop="currencyName">
          <el-input v-model="form.currencyName" placeholder="请输入币种名称" />
        </el-form-item>
        <el-form-item label="国家/地区" prop="countryRegion">
          <el-input v-model="form.countryRegion" placeholder="请输入国家/地区" />
        </el-form-item>
        <el-form-item label="财务精度" prop="financialAccuracy">
          <el-input v-model="form.financialAccuracy" placeholder="请输入财务精度" />
        </el-form-item>
        <el-form-item label="精度" prop="accuracy">
          <el-input v-model="form.accuracy" placeholder="请输入精度" />
        </el-form-item>
        <el-form-item label="货币符号" prop="currencySymbol">
          <el-input v-model="form.currencySymbol" placeholder="请输入货币符号" />
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
import { listCurrency, getCurrency, delCurrency, addCurrency, updateCurrency } from "@/api/mast/currency";

export default {
  name: "Currency",
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
      // 币种表格数据
      currencyList: [],
      enableStatus: [], // 用于存储复选框选中状态的数组
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        currencyCode: null,
        currencyName: null,
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
  },
  methods: {
    formatTaxRate(row, column) {
      const value = row[column.property];
      if (value === null || value === undefined) {
        return ''; // 处理空值情况
      }
      return parseFloat(value).toFixed(2); // 使用 toFixed 方法保留两位小数
    },
    /** 查询币种列表 */
    getList() {
      this.loading = true;
      listCurrency(this.queryParams).then(response => {
        this.currencyList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.enableStatus = this.currencyList.map((account) => account.enable === 1);
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
        currencyId: null,
        currencyCode: null,
        currencyName: null,
        countryRegion: null,
        financialAccuracy: null,
        accuracy: null,
        currencySymbol: null,
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
      this.ids = selection.map(item => item.currencyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加币种";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const currencyId = row.currencyId || this.ids
      getCurrency(currencyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改币种";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.currencyId != null) {
            updateCurrency(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCurrency(this.form).then(response => {
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
      const currencyIds = row.currencyId || this.ids;
      this.$modal.confirm('是否确认删除币种编号为"' + currencyIds + '"的数据项？').then(function() {
        return delCurrency(currencyIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mast/currency/export', {
        ...this.queryParams
      }, `currency_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
