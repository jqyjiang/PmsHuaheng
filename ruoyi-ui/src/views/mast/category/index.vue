<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="品类代码" prop="categoryCode">
        <el-input v-model="queryParams.categoryCode" placeholder="请输入品类代码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="品类名称" prop="categoryName">
        <el-input v-model="queryParams.categoryName" placeholder="请输入品类名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="是否启用" prop="enable">
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
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['mast:category:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" @click="showBatchAddDialog"
          v-hasPermi="['mast:category:edit']">批量新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['mast:category:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="categoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="品类代码" align="center" prop="categoryCode" />
      <el-table-column label="品类名称" align="center" prop="categoryName" />
      <el-table-column label="是否启用" align="center" prop="enable">
        <template slot-scope="scope">
          <el-checkbox :value="enableStatus[scope.$index]" :disabled="true"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column label="是否允许超量送货" align="center" prop="isNo">
        <template slot-scope="scope">
          <el-checkbox :value="isNoStatus[scope.$index]" :disabled="true"></el-checkbox>
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
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mast:category:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mast:category:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改品类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="品类代码" prop="categoryCode">
          <el-input v-model="form.categoryCode" placeholder="请输入品类代码" />
        </el-form-item>
        <el-form-item label="品类名称" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请输入品类名称" />
        </el-form-item>
        <el-form-item label="是否允许超量送货" prop="isNo">
          <el-checkbox v-model="form.isNo"></el-checkbox>
        </el-form-item>
        <el-form-item label="上级品类" prop="superiorCategory">
          <el-input v-model="form.superiorCategory" placeholder="请选择上级品类" />
          <i class="el-icon-search" id="serachOne1" @click="showMaterial1()"></i>
          <el-dialog :visible.sync="dialogMaterial1" title="品类对象-浏览框" :modal="false">
            <el-table :data="categoryList1" v-loading="loading" @row-click="handleRowClickMaterial1">
              <el-table-column label="品类名称" align="center" prop="categoryName" />
              <el-table-column label="品类代码" align="center" prop="categoryCode" />
              <el-table-column label="上级品类" align="center" prop="superiorCategory" />
            </el-table>
            <pagination v-show="catotal > 0" :total="catotal" :page.sync="caqueryParams.pageNum"
              :limit.sync="caqueryParams.pageSize" @pagination="getList2" />
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogMaterial1 = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="创建时间" prop="creationTime">
          <el-date-picker clearable v-model="form.creationTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最后更新人" prop="lUpdated">
          <el-input v-model="form.lUpdated" placeholder="请输入最后更新人" />
        </el-form-item>
        <!-- <el-form-item label="最后更新时间" prop="lUpdateTime">
          <el-date-picker clearable v-model="form.lUpdateTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择最后更新时间">
          </el-date-picker>
        </el-form-item> -->
        <el-form-item v-if="false" label="公司" prop="company">
          <el-input v-model="form.company" placeholder="请输入公司" />
        </el-form-item>
        <el-form-item label="公司" prop="companiesName">
          <el-input v-model="form.companiesName" placeholder="请选择公司" />
          <i class="el-icon-search" id="serachOne2" @click="showMaterial2()"></i>
          <el-dialog :visible.sync="dialogMaterial2" title="公司-浏览框" :modal="false">

            <el-table :data="companiesList" v-loading="loading" @row-click="handleRowClickMaterial2">
              <el-table-column label="公司名称" align="center" prop="companiesName" />
              <el-table-column label="公司代码" align="center" prop="companiesCode" />
            </el-table>
            <pagination v-show="catotal > 0" :total="catotal" :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize" @pagination="getList3" />
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogMaterial2 = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>

        <el-form-item label="基本计算单位" prop="meteringUnit">
          <el-select v-model="form.meteringUnit" placeholder="请选择基本计算单位">
            <el-option v-for="dict in accountList" :key="dict.unitId" :label="dict.meteringUnit" :value="dict.unitId" />
          </el-select>
        </el-form-item>
        <el-form-item label="来源系统" prop="sourceSystem">
          <el-input v-model="form.sourceSystem" placeholder="请输入来源系统" />
        </el-form-item>
        <el-form-item label="引入要求 " prop="introductionRequirements">
          <el-radio-group v-model="form.introductionRequirements" placeholder="请选择引入要求">
            <el-radio :label="1">严格</el-radio>
            <el-radio :label="0">一般</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="分配采购人" prop="assignPurchaser">
          <el-input v-model="form.assignPurchaser" placeholder="请输入分配采购人" />
        </el-form-item>
        <el-form-item label="启用" prop="enable">
          <el-checkbox v-model="form.enable"></el-checkbox>
        </el-form-item>
      </el-form>

      <!-- <el-dialog :visible="dialogVisible" @close="closeDialog">
      <el-table :data="tableData">
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="value" label="值"></el-table-column>
      </el-table>
      </el-dialog> -->

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 批量新增对话框 -->
    <el-dialog :visible.sync="batchAddDialogVisible" title="批量维护">
      <el-form :model="form" ref="form">
        <el-divider content-position="center">品类</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddCategory">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteCategory">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="categoryList5" :row-class-name="rowCategoryIndex"
          @selection-change="handleCategorySelectionChange" ref="category" @row-click="clickRowCategory">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="品类编码" prop="categoryCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.categoryCode" placeholder="请输入品类编码" />
            </template>
          </el-table-column>
          <el-table-column label="品类名称" prop="categoryName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.categoryName" placeholder="请输入品类名称" />
            </template>
          </el-table-column>
          <el-table-column label="启用" prop="enable" width="50">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.enable"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="是否允许超量送货" prop="isNo" width="150">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.categoryIsNo"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="上级品类" prop="superiorCategory" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.superiorCategory" placeholder="请选择上级品类" />
              <i class="el-icon-search" id="serachOne1" @click="showMaterial1()"></i>
              <el-dialog :visible.sync="dialogMaterial1" title="品类对象-浏览框" :modal="false">
                <el-table :data="categoryList1" v-loading="loading" @row-click="handleRowClickMaterial11">
                  <el-table-column label="品类名称" align="center" prop="categoryName" />
                  <el-table-column label="品类代码" align="center" prop="categoryCode" />
                  <el-table-column label="上级品类" align="center" prop="superiorCategory" />
                </el-table>
                <pagination v-show="catotal > 0" :total="catotal" :page.sync="caqueryParams.pageNum"
                  :limit.sync="caqueryParams.pageSize" @pagination="getList2" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial1 = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" prop="creationTime" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.creationTime" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择创建时间" />
            </template>
          </el-table-column>
          <el-table-column label="最后更新人" prop="lUpdated" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.lUpdated" placeholder="请输入最后更新人" />
            </template>
          </el-table-column>
          <!-- <el-table-column label="最后更新时间" prop="lUpdateTime" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.lUpdateTime" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择最后更新时间" />
            </template>
          </el-table-column> -->
          <el-table-column label="公司" prop="companiesName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.companiesName" placeholder="请选择公司" />
              <i class="el-icon-search" id="serachOne2" @click="showMaterial2()"></i>
              <el-dialog :visible.sync="dialogMaterial2" title="公司-浏览框" :modal="false">
                <el-table :data="companiesList" v-loading="loading" @row-click="handleRowClickMaterial22">
                  <el-table-column label="公司名称" align="center" prop="companiesName" />
                  <el-table-column label="公司代码" align="center" prop="companiesCode" />
                </el-table>
                <pagination v-show="catotal > 0" :total="catotal" :page.sync="queryParams.pageNum"
                  :limit.sync="queryParams.pageSize" @pagination="getList3" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial2 = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="基本计算单位" prop="meteringUnit" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.meteringUnit" placeholder="请选择基本计算单位">
                <el-option v-for="dict in accountList" :key="dict.unitId" :label="dict.meteringUnit"
                  :value="dict.unitId" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="来源系统" prop="sourceSystem" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sourceSystem" placeholder="请输入来源系统" />
            </template>
          </el-table-column>
          <el-table-column label="分配采购人" prop="assignPurchaser" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.assignPurchaser" placeholder="请输入分配采购人" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer">
        <el-button @click="cancelBatchAdd">取消</el-button>
        <el-button type="primary" @click="submitForm1">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listCategory, getCategory, delCategory, addCategory, updateCategory, listAccount, listCategory1, listCompanies, batchCategory } from "@/api/mast/category";

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
      catotal: 0,
      // 品类表格数据
      categoryList: [],
      enableStatus: [], // 用于存储复选框选中状态的数组
      isNoStatus: [], // 用于存储复选框选中状态的数组  是否允许超量送货
      accountList: [],
      categoryList1: [],
      companiesList: [],
      categoryList5: [],//批量新增
      CategoryIndex: 0,//批量下标
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
      // 查询参数
      caqueryParams: {
        pageNum: 1,
        pageSize: 10,

      },
      // 表单参数
      form: {},
      forms: {},
      // 表单校验
      rules: {
      },
      dialogMaterial1: false,
      dialogMaterial2: false,
      dialogMaterial3: false,

      // 批量新增对话框的可见性
      batchAddDialogVisible: false,

      checkedCategorys: null,

      // forms:{
      //   categoryid: null,
      //   categoryCode: null,
      //   categoryName: null,
      //   enable: null,
      //   isNo: null,
      //   superiorCategory: null,
      //   creationTime: null,
      //   lUpdated: null,
      //   lUpdateTime: null,
      //   company: null,
      //   meteringUnit: null,
      //   sourceSystem: null,
      //   introductionRequirements: null,
      //   assignPurchaser: null
      // },
    };
  },
  created() {
    this.getList();
    this.getList1();
    this.getList2();
    this.getList3();
  },

  computed: {},

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
    /**弹框 */
    getList2() {
      this.loading = true;
      listCategory1(this.caqueryParams).then(response => {
        this.categoryList1 = response.rows;
        this.catotal = response.total;
        this.loading = false;
      });
    },
    /** 查询计算单位定义列表 */
    getList1() {
      listAccount().then(response => {
        this.accountList = response.rows;
      });
    },
    /** 查询公司表列表 */
    getList3() {
      this.loading = true;
      listCompanies(this.queryParams).then(response => {
        this.companiesList = response.rows;
        this.catotal = response.total;
        this.loading = false;
      });
    },

    /** 复选框选中数据 */
    handleCategorySelectionChange(selection) {
      this.checkedCategory = selection.map(item => item.index)
    },

    // 显示批量新增对话框
    showBatchAddDialog() {
      this.batchAddDialogVisible = true;
    },
    // 取消批量新增
    cancelBatchAdd() {
      this.batchAddDataList = []; // 清空批量新增数据列表
      this.batchAddDialogVisible = false;
    },

    // // 执行批量新增
    // doBatchAdd() {
    //   if (this.checkedCategorys.length === 0) {
    //     this.$message.warning('请至少选择一条记录');
    //     return;
    //   }
    //   // 对选中的行数据进行修改
    //   this.checkedCategorys.forEach(row => {
    //     row.categoryCode = this.forms.categoryCode;
    //     row.requireNumber = this.forms.batchRequireNumber;
    //     row.consignee = this.forms.batchConsignee;
    //     row.receivingAddress = this.forms.batchReceivingAddress;
    //     row.receivingPhone = this.forms.batchReceivingPhone;
    //   });
    //   // console.log(this.checkedCategorys)
    //   // 清空批量修改的值
    //   this.categoryCode1 = '';
    //   this.batchRequireNumber = '';
    //   this.batchConsignee = '';
    //   this.batchReceivingAddress = '';
    //   this.batchReceivingPhone = '';
    //   this.$message.success('批量修改成功');
    //   this.batchAddDialogVisible = false;
    // },


    showMaterial1() {
      this.dialogMaterial1 = true;
      this.getList2();
    },
    showMaterial2() {
      this.dialogMaterial2 = true;
      this.getList3();
    },

    //批量  序号
    rowCategoryIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
      row.lineNumber = row.index
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
      this.categoryList5 = [];
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
      this.single = selection.length !== 1
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
        if (response.data.enable == 1) {
          response.data.enable = true
        } else {
          response.data.enable = false
        }
        if (response.data.isNo == 1) {
          response.data.isNo = true
        } else {
          response.data.isNo = false
        }
        this.form = response.data;
        this.open = true;
        this.title = "修改品类";
      });
    },

    handleRowClickMaterial1(row) {
      // 修改数据的属性值
      console.log(row.categoryCode);
      this.form.superiorCategory = row.categoryCode;
      this.dialogMaterial1 = false; // 关闭对话框
    },
    handleRowClickMaterial11(row) {
      // 修改数据的属性值
      console.log(row.categoryCode);
      this.categoryList5[this.CategoryIndex].superiorCategory = row.categoryCode;
      this.dialogMaterial1 = false; // 关闭对话框
    },

    handleRowClickMaterial2(row) {
      // 修改数据的属性值
      this.form.companiesName = row.companiesName;
      this.form.company = row.companiesId;
      this.dialogMaterial2 = false; // 关闭对话框
    },
    handleRowClickMaterial22(row) {
      // 修改数据的属性值
      this.categoryList5[this.CategoryIndex].companiesName = row.companiesName;
      this.categoryList5[this.CategoryIndex].company = row.companiesId;
      this.dialogMaterial2 = false; // 关闭对话框
    },
    clickRowCategory(row, event, column) {
      this.CategoryIndex = this.categoryList5.indexOf(row); // 获取点击的行索引
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.categoryid != null) {
            if (this.form.enable == true) {
              this.form.enable = 1
            } else {
              this.form.enable = 0
            }
            if (this.form.isNo == true) {
              this.form.isNo = 1
            } else {
              this.form.isNo = 0
            }
            updateCategory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            console.log("你好：" + this.form.enable);
            if (this.form.enable == true) {
              this.form.enable = 1
            } else {
              this.form.enable = 0
            }
            if (this.form.isNo == true) {
              this.form.isNo = 1
            } else {
              this.form.isNo = 0
            }
            addCategory(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /**批量提交 */
    submitForm1() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.forms.categoryid != null) {
            if (this.forms.enable == true) {
              this.forms.enable = 1
            } else {
              this.forms.enable = 0
            }
            if (this.forms.isNo == true) {
              this.forms.isNo = 1
            } else {
              this.forms.isNo = 0
            }
            updateCategory(this.forms).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.batchAddDialogVisible = false;
              this.getList();
            });
          } else {
            this.categoryList5.forEach(item => {
              item.enable = item.enable ? 1 : 0;
              item.isNo = item.isNo ? 1 : 0;
            });
            let data = JSON.stringify(this.categoryList5);
            batchCategory(data).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.batchAddDialogVisible = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const categoryids = row.categoryid || this.ids;
      this.$modal.confirm('是否确认删除品类编号为"' + categoryids + '"的数据项？').then(function () {
        return delCategory(categoryids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },

    /** 订单物料明细序号 */
    rowOrderMaterialIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 品类明细添加按钮操作 */
    handleAddCategory() {
      let obj = {};
      obj.categoryCode = "";
      obj.categoryName = "";
      obj.enable = "";
      obj.isNo = "";
      obj.superiorCategory = "";
      obj.creationTime = "";
      obj.lUpdated = "";
      obj.lUpdateTime = "";
      obj.company = "";
      obj.meteringUnit = "";
      obj.sourceSystem = "";
      obj.introductionRequirements = "";
      obj.assignPurchaser = "";
      this.categoryList5.push(obj);
    },
    /** 品类明细删除按钮操作 */
    handleDeleteCategory() {
      if (this.checkedCategory.length == 0) {
        this.$modal.msgError("请先选择要删除的品类明细数据");
      } else {
        const categoryList5 = this.categoryList5;
        const checkedCategory = this.checkedCategory;
        this.categoryList5 = categoryList5.filter(function (item) {
          return checkedCategory.indexOf(item.index) == -1
        });
      }
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('mast/category/export', {
        ...this.queryParams
      }, `category_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style>
#serachOne {
  position: absolute;
  right: 10px;
  top: 10px;
}

#serachOne1 {
  position: absolute;
  right: 15px;
  top: 12.5px;
}

#serachOne2 {
  position: absolute;
  right: 15px;
  top: 12.5px;
}

#serachOne3 {
  position: absolute;
  right: 15px;
  top: 12.5px;
}
</style>

