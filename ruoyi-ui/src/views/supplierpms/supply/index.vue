<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="供应商名称" prop="sdiName">
        <el-input v-model="queryParams.sbiName" placeholder="请输入供应商名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="公司" prop="companiesName">
        <el-input v-model="queryParams.companiesName" placeholder="请输入公司" clearable @keyup.enter.native="handleQuery">
          <template v-slot:suffix>
            <el-popover placement="right" width="340" trigger="click">
              <el-table :data="companiesList" @row-click="handleRowClickque">
                <el-table-column width="156" property="companiesId" label="公司id"></el-table-column>
                <el-table-column width="156" property="companiesName" label="公司名称"></el-table-column>
              </el-table>
              <i class="el-icon-search" slot="reference"></i>
            </el-popover>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['supplierpms:supply:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['supplierpms:supply:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['supplierpms:supply:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['supplierpms:supply:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="供货id" align="center" prop="supplyId" />
      <el-table-column label="供应商id" align="center" prop="sdId" /> -->
      <el-table-column label="供应商编码" align="center" prop="sdCode" />
      <el-table-column label="供应商名称" align="center" prop="sbiName" />
      <el-table-column label="生命周期" align="center" prop="cycle" />
      <el-table-column label="公司" align="center" prop="companiesName" />
      <el-table-column label="创建人" align="center" prop="founder" />
      <el-table-column label="创建人部门" align="center" prop="createdByEpartment" />
      <el-table-column label="创建时间" align="center" prop="creationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.creationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后更新人" align="center" prop="lastUpdatedBy" />
      <el-table-column label="最后更新时间" align="center" prop="lastUpdateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['supplierpms:supply:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['supplierpms:supply:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改供货管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="105px">
        <!-- <el-form-item label="公司id" prop="companiesId">
          <el-input v-model="form.companiesId" placeholder="请输入公司id">
          </el-input>
        </el-form-item> -->
        <el-form-item label="公司" prop="companiesName">
          <el-input v-model="form.companiesName" placeholder="请输入公司">
            <template v-slot:suffix>
              <el-popover placement="right" width="340" trigger="click">
                <el-table :data="companiesList" @row-click="handleRowClickfrom">
                  <el-table-column width="156" property="companiesId" label="公司id"></el-table-column>
                  <el-table-column width="156" property="companiesName" label="公司名称"></el-table-column>
                </el-table>
                <i class="el-icon-search" slot="reference"></i>
              </el-popover>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="供应商名称" prop="sbiName">
          <el-input v-model="form.sbiName" placeholder="请输入供应商名称">
             <template v-slot:suffix>
             <el-popover placement="right" width="340" trigger="click">
                <el-table :data="supplyCanList" @row-click="hanSupplierClickfrom">
                  <el-table-column width="156" property="sdCode" label="供应商编号"></el-table-column>
                  <el-table-column width="156" property="sbiName" label="供应商名称"></el-table-column>
                </el-table>
                <i class="el-icon-search" slot="reference"></i>
              </el-popover>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="创建人" prop="founder">
          <el-input v-model="form.founder" placeholder="请输入创建人" />
        </el-form-item>
        <!-- <el-form-item label="创建时间" prop="creationTime">
          <el-date-picker clearable v-model="form.creationTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item> -->
        <el-form-item label="最后更新人" prop="lastUpdatedBy">
          <el-input v-model="form.lastUpdatedBy" placeholder="请输入最后更新人" />
        </el-form-item>
        <!-- <el-form-item label="最后更新时间" prop="lastUpdateTime">
          <el-date-picker clearable v-model="form.lastUpdateTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择最后更新时间">
          </el-date-picker>
        </el-form-item> -->
        <el-form-item label="备注" prop="notes">
          <el-input v-model="form.notes" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="创建人部门" prop="createdByEpartment">
          <el-input v-model="form.createdByEpartment" placeholder="请输入创建人部门" />
        </el-form-item>
        <el-divider content-position="center">供应商物料信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini"
              @click="handleAddSupplierMaterialList">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini"
              @click="handleDeleteSupplierMaterialList">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="supplierMaterialList" :row-class-name="rowSupplierMaterialListIndex"
          @selection-change="handleSupplierMaterialListSelectionChange" ref="supplierMaterialList"
          @row-click="handleSupplierMaterialListe">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料编码" prop="materialCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialCode" placeholder="请输入物料编码" />
              <i class="el-icon-search" id="serachOne" @click="showMaterial()"></i>
              <el-dialog :visible.sync="dialogMaterial" title="物料管理-浏览框" :modal="false">
                <!-- 这里是物料管理的内容 -->
                <el-table v-loading="loading" :data="materialList" @row-click="handleRowClickMaterial">
                  <el-table-column type="selection" width="55" align="center" />
                  <el-table-column label="物料编码" align="center" prop="materialCode" />
                  <el-table-column label="物料名称" align="center" prop="materialName" />
                  <el-table-column label="品类编码" align="center" prop="categoryCode" />
                  <el-table-column label="物料品类" align="center" prop="categoryName" />
                </el-table>
                <pagination v-show="mtotal > 0" :total="mtotal" :page.sync="ququeryParams.pageNum"
                  :limit.sync="ququeryParams.pageSize" @pagination="getList3" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="物料名称" prop="materialName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialName" placeholder="请输入物料名称" />
            </template>
          </el-table-column>
          <el-table-column label="品类编码" prop="categoryCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.categoryCode" placeholder="品类编码" />
            </template>
          </el-table-column>
          <el-table-column label="品类名称" prop="categoryName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.categoryName" placeholder="请输入品类名称" />
            </template>
          </el-table-column>
          <el-table-column label="生产厂家" prop="manufacturer" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.manufacturer" placeholder="请输入生产厂家" />
            </template>
          </el-table-column>
          <el-table-column label="供货能力评级" prop="supplyCapacity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.supplyCapacity" placeholder="请输入供货能力评级" />
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="notes" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.notes" placeholder="请输入备注" />
            </template>
          </el-table-column>
          <el-table-column label="文件上传" prop="upload" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.upload" placeholder="请输入文件上传" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSupply, getSupply, delSupply, addSupply, updateSupply, companies, listMaterial } from "@/api/supplierpms/supply";
import { canSupplier } from "@/api/supplierpms/details";
export default {
  name: "Supply",
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
      // 供货管理表格数据
      supplyList: [],
      // 能新增的供应商数据、
      supplyCanList: [],
      // 供应商物料表格数据
      supplierMaterialList: [],
      // 供应商物料表选中数据
      checkedSupplierMaterialList: [],
      //公司数据
      companiesList: [],

      deliveryIndex: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sdId: null,
        companiesId: null,
        companiesName: null,
        founder: null,
        creationTime: null,
        lastUpdatedBy: null,
        lastUpdateTime: null,
        notes: null,
        createdByEpartment: null,
        sbiName: null
      },
      // 物料基本信息
      materialList: [],
      mtotal: 0,
      ququeryParams: {
        pageNum: 1,
        pageSize: 10,
        companiesName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companiesName: [
          { required: true, message: "公司不能为空", trigger: "blur" }
        ],
        sbiName: [
          { required: true, message: "供应商不能为空", trigger: "blur" }
        ],
      },
      dialogMaterial: false, // 用于标记物料信息是否可见
    };
  },
  created() {
    this.getList();
    this.getCompanies();
    this.getList3();
  },
  methods: {
    /**查询公司和供应商数据 */
    getCompanies() {
      companies().then(response => {
        this.companiesList = response.rows;
      });
      canSupplier().then(response => {
        this.supplyCanList = response.rows;
      });
    },
    /** 查询供货管理列表 */
    getList() {
      this.loading = true;
      listSupply(this.queryParams).then(response => {
        this.supplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询物料维护列表 */
    getList3() {
      this.loading = true;
      listMaterial(this.ququeryParams).then(response => {
        this.materialList = response.rows;
        this.mtotal = response.total;
        this.loading = false;
      });
    },
    showMaterial() {
      this.dialogMaterial = true;
      //this.getList3()
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        supplyId: null,
        sdId: null,
        companiesId: null,
        companiesName: null,
        founder: null,
        creationTime: null,
        lastUpdatedBy: null,
        lastUpdateTime: null,
        notes: null,
        createdByEpartment: null,
        sbiName: null
      };
      this.supplierMaterialList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if (this.queryParams.companiesName === null || this.queryParams.companiesName === "") {
        this.queryParams.companiesId = null;
      }
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
      this.ids = selection.map(item => item.supplyId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加供货管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const supplyId = row.supplyId || this.ids
      getSupply(supplyId).then(response => {
        console.log(response.data.supplierMaterialList);
        this.supplierMaterialList = response.data.supplierMaterialList;
        this.form = response.data;
        this.open = true;
        this.title = "修改供货管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.supplierMaterialList = this.supplierMaterialList;
          if (this.form.supplyId != null) {
            updateSupply(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSupply(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /**公司搜索按钮 */
    handleRowClickque(row) {
      this.queryParams.companiesId = row.companiesId;
      this.queryParams.companiesName = row.companiesName;
    },
    /**新增公司搜索按钮 */
    handleRowClickfrom(row) {
      //console.log(row.companiesId);
      this.form.companiesId = row.companiesId;
      this.form.companiesName = row.companiesName;
    },
    //**新增供应商搜索按钮 */
    hanSupplierClickfrom(row) {
      //console.log(row);
      this.form.sdId = row.sdId;
      this.form.sbiName = row.sbiName;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const supplyIds = row.supplyId || this.ids;
      this.$modal.confirm('是否确认删除供货管理编号为"' + supplyIds + '"的数据项？').then(function () {
        return delSupply(supplyIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('supplierpms/supply/export', {
        ...this.queryParams
      }, `supply_${new Date().getTime()}.xlsx`)
    },
    /** 供应商物料序号 */
    rowSupplierMaterialListIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 供应商物料添加按钮操作 */
    handleAddSupplierMaterialList() {
      let obj = {};
      obj.materialCode = "";
      obj.categoryCode = "";
      obj.materialName = "";
      obj.categoryName = "";
      obj.manufacturer = "";
      obj.supplyCapacity = "";
      obj.notes = "";
      obj.upload = "";
      // console.log(this.supplierMaterialList);
      this.supplierMaterialList.push(obj);
    },
    /** 供应商物料删除按钮操作 */
    handleDeleteSupplierMaterialList() {
      if (this.checkedSupplierMaterialList.length == 0) {
        this.$modal.msgError("请先选择要删除的供应商物料数据");
      } else {
        const supplierMaterialList = this.supplierMaterialList;
        const checkedSupplierMaterialList = this.checkedSupplierMaterialList;
        this.supplierMaterialList = supplierMaterialList.filter(function (item) {
          return checkedSupplierMaterialList.indexOf(item.index) == -1
        });
      }
    },
    /** 物料添加 */
    handleSupplierMaterialListe(row) {
      this.deliveryIndex = this.supplierMaterialList.indexOf(row); // 获取点击的行索引
    },
    /** 复选框选中数据 */
    handleSupplierMaterialListSelectionChange(selection) {
      this.checkedSupplierMaterialList = selection.map(item => item.index)
    },
    // 物料框架+数据
    handleRowClickMaterial(row) {
      console.log(row);
      this.supplierMaterialList[this.deliveryIndex].materialCode = row.materialCode;// 物料编码
      this.supplierMaterialList[this.deliveryIndex].materialName = row.materialName;// 物料名称
      this.supplierMaterialList[this.deliveryIndex].categoryCode = row.categoryCode;// 品类
      this.supplierMaterialList[this.deliveryIndex].categoryName = row.categoryName;// 物料品类
      this.dialogMaterial = false; // 关闭对话框
    },
  }
};
</script>
<style>
#serachOne {
  position: absolute;
  right: 15px;
  top: 21.5px;
}</style>
