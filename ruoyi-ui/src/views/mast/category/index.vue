<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="品类代码" prop="categoryCode">
        <el-input
          v-model="queryParams.categoryCode"
          placeholder="请输入品类代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="品类名称" prop="categoryName">
        <el-input
          v-model="queryParams.categoryName"
          placeholder="请输入品类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否启用" prop="enable">
        <el-input
          v-model="queryParams.enable"
          placeholder="请输入是否启用"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上级品类" prop="superiorCategory">
        <el-input
          v-model="queryParams.superiorCategory"
          placeholder="请输入上级品类"
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
          v-hasPermi="['mast:category:add']"
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
          v-hasPermi="['mast:category:edit']"
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
          v-hasPermi="['mast:category:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mast:category:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="categoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="品类序号" align="center" prop="categoryid" /> -->
      <el-table-column label="品类代码" align="center" prop="categoryCode" />
      <el-table-column label="品类名称" align="center" prop="categoryName" />
      <el-table-column label="是否启用" align="center" prop="enable">
       <template slot-scope="scope">
        <el-checkbox
         :value="enableStatus[scope.$index]"
         :disabled="true"
        ></el-checkbox>
       </template>
      </el-table-column>
      <!-- <el-table-column label="是否允许超量送货" align="center" prop="isNo" /> -->
      <el-table-column label="是否允许超量送货" align="center" prop="isNo">
       <template slot-scope="scope">
        <el-checkbox
         :value="isNoStatus[scope.$index]"
         :disabled="true"
        ></el-checkbox>
       </template>
      </el-table-column>
      <el-table-column label="上级品类" align="center" prop="superiorCategory" />
      <el-table-column label="创建时间" align="center" prop="creationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.creationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后更新人" align="center" prop="lUpdated" />
      <el-table-column label="最后更新时间" align="center" prop="lUpdateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lUpdateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mast:category:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mast:category:remove']"
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

    <!-- 添加或修改品类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="品类代码" prop="categoryCode">
          <el-input v-model="form.categoryCode" placeholder="请输入品类代码" />
        </el-form-item>
        <el-form-item label="品类名称" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请输入品类名称" />
        </el-form-item>
        <el-form-item label="是否启用" prop="enable">
          <el-input v-model="form.enable" placeholder="请输入是否启用" />
        </el-form-item>
        <el-form-item label="是否允许超量送货" prop="isNo">
          <el-input v-model="form.isNo" placeholder="请输入是否允许超量送货" />
        </el-form-item>
        <el-form-item label="上级品类" prop="superiorCategory">
          <el-input v-model="form.superiorCategory" placeholder="请输入上级品类" />
        </el-form-item>
        <el-form-item label="创建时间" prop="creationTime">
          <el-date-picker clearable
            v-model="form.creationTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最后更新人" prop="lUpdated">
          <el-input v-model="form.lUpdated" placeholder="请输入最后更新人" />
        </el-form-item>
        <el-form-item label="最后更新时间" prop="lUpdateTime">
          <el-date-picker clearable
            v-model="form.lUpdateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最后更新时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="公司" prop="company">
          <el-input v-model="form.company" placeholder="请输入公司" />
        </el-form-item>
        <el-form-item label="计量单位" prop="meteringUnit">
          <el-input v-model="form.meteringUnit" placeholder="请输入计量单位" />
        </el-form-item>
        <el-form-item label="来源系统" prop="sourceSystem">
          <el-input v-model="form.sourceSystem" placeholder="请输入来源系统" />
        </el-form-item>
        <el-form-item label="引入要求 1:严格 0：一般" prop="introductionRequirements">
          <el-input v-model="form.introductionRequirements" placeholder="请输入引入要求 1:严格 0：一般" />
        </el-form-item>
        <el-form-item label="分配采购人" prop="assignPurchaser">
          <el-input v-model="form.assignPurchaser" placeholder="请输入分配采购人" />
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
import { listCategory, getCategory, delCategory, addCategory, updateCategory } from "@/api/mast/category";

export default {
  name: "Category",
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
      // 品类表格数据
      categoryList: [],
      enableStatus: [], // 用于存储复选框选中状态的数组
      isNoStatus: [], // 用于存储复选框选中状态的数组  是否允许超量送货
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryCode: null,
        categoryName: null,
        enable: null,
        superiorCategory: null,
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
    /** 查询品类列表 */
    getList() {
      this.loading = true;
      listCategory(this.queryParams).then(response => {
        this.categoryList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.enableStatus = this.categoryList.map((account) => account.enable === 1);
        this.isNoStatus = this.categoryList.map((account) => account.isNo === 1);
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
        categoryid: null,
        categoryCode: null,
        categoryName: null,
        enable: null,
        isNo: null,
        superiorCategory: null,
        creationTime: null,
        lUpdated: null,
        lUpdateTime: null,
        company: null,
        meteringUnit: null,
        sourceSystem: null,
        introductionRequirements: null,
        assignPurchaser: null
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
      this.ids = selection.map(item => item.categoryid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加品类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const categoryid = row.categoryid || this.ids
      getCategory(categoryid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改品类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.categoryid != null) {
            updateCategory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCategory(this.form).then(response => {
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
      const categoryids = row.categoryid || this.ids;
      this.$modal.confirm('是否确认删除品类编号为"' + categoryids + '"的数据项？').then(function() {
        return delCategory(categoryids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mast/category/export', {
        ...this.queryParams
      }, `category_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
