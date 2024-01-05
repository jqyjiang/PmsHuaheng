<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="考察标题" prop="soiTitle">
        <el-input v-model="queryParams.soiTitle" placeholder="请输入考察标题" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="供应商编码" prop="sdName">
        <el-input v-model="queryParams.sdName" placeholder="请输入供应商名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="审批状态" prop="examine">
        <el-select v-model="queryParams.examine" placeholder="请选择审批状态" clearable>
          <el-option v-for="dict in dict.type.examine_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['supplierpms:inspection:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['supplierpms:inspection:edit']">审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['supplierpms:inspection:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['supplierpms:inspection:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inspectionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="考察编码" align="center" prop="soiCode" />
      <el-table-column label="考察标题" align="center" prop="soiTitle" />
      <el-table-column label="供应商名称" align="center" prop="sdName" />
      <el-table-column label="公司" align="center" prop="company" />
      <el-table-column label="评分方式" align="center" prop="scoringMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.scoring_method" :value="scope.row.scoringMethod" />
        </template>
      </el-table-column>
      <el-table-column label="考察结果" align="center" prop="inspectionResults">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inspection_results" :value="scope.row.inspectionResults" />
        </template>
      </el-table-column>
      <el-table-column label="考察类型" align="center" prop="soiType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inspection_type" :value="scope.row.soiType" />
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" prop="examine">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.examine_status" :value="scope.row.examine"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['supplierpms:inspection:edit']"></el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['supplierpms:inspection:remove']">删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改现场考察对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="考察标题" prop="soiTitle">
          <el-input v-model="form.soiTitle" placeholder="请输入考察标题" />
        </el-form-item>
        <!-- <el-form-item label="公司" prop="company">
          <el-input v-model="form.company" placeholder="请输入公司" />
        </el-form-item> -->
        <el-form-item label="公司" prop="company">
          <el-input v-model="form.company" placeholder="请输入公司">
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
          <el-input v-model="form.sdName" placeholder="请输入供应商名称">
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
        <el-form-item label="考察日期从" prop="inspectionDateFrom">
          <el-date-picker clearable v-model="form.inspectionDateFrom" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择考察日期从">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="考察日期至" prop="inspectionDateTo">
          <el-date-picker clearable v-model="form.inspectionDateTo" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择考察日期至">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="考察类型" prop="soiType">
          <el-select v-model="form.soiType" placeholder="请选择考察类型">
            <el-option v-for="dict in dict.type.inspection_type" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评分方式" prop="scoringMethod">
          <el-select v-model="form.scoringMethod" placeholder="请选择评分方式">
            <el-option v-for="dict in dict.type.scoring_method" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商联系人" prop="supplierContact">
          <el-input v-model="form.supplierContact" placeholder="请输入供应商联系人" />
        </el-form-item>
        <el-form-item label="供应商联系电话" prop="supplierContactPhone">
          <el-input v-model="form.supplierContactPhone" placeholder="请输入供应商联系电话" />
        </el-form-item>
        <el-form-item label="供应商联系邮箱" prop="supplierContactEmail">
          <el-input v-model="form.supplierContactEmail" placeholder="请输入供应商联系邮箱" />
        </el-form-item>
        <el-form-item label="实际考察地址" prop="actualAddress">
          <el-input v-model="form.actualAddress" placeholder="请输入实际考察地址" />
        </el-form-item>
        <el-form-item label="考察结果" prop="inspectionResults">
          <el-select v-model="form.inspectionResults" placeholder="请选择考察结果">
            <el-option v-for="dict in dict.type.inspection_results" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="最终得分" prop="finalScore">
          <el-input v-model="form.finalScore" placeholder="请输入最终得分" />
        </el-form-item>
        <!-- <el-form-item label="等级" prop="level">
          <el-input v-model="form.level" placeholder="请输入等级" />
        </el-form-item> -->
        <el-form-item label="综合意见" prop="comprehensiveOpinions">
          <el-input v-model="form.comprehensiveOpinions" placeholder="请输入综合意见" />
        </el-form-item>
        <el-form-item label="申请人" prop="applicant">
          <el-input v-model="form.applicant" placeholder="请输入申请人" />
        </el-form-item>
        <!-- <el-form-item label="审批状态" prop="examine">
          <el-select v-model="form.examine" placeholder="请选择审批状态">
            <el-option v-for="dict in dict.type.examine_status" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item> -->
        <el-divider content-position="center">考察物料信息</el-divider>
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
        <el-table :data="supplierMaterialListList" :row-class-name="rowSupplierMaterialListIndex"
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
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="form.examine == 2" @click="InterviewReview">审核</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
      <!-- <span>这是一段信息</span> -->
      <el-button type="primary" @click="review(true)">审核通过</el-button>
      <el-button @click="review(false)">审核不通过</el-button>
      <span slot="footer" class="dialog-footer">

      </span>
    </el-dialog>
  </div>
</template>

<script>
import { listInspection, getInspection, delInspection, addInspection, updateInspection } from "@/api/supplierpms/inspection";
import { listSupply, getSupply, delSupply, addSupply, updateSupply, companies, listMaterial } from "@/api/supplierpms/supply";
import { canSupplier } from "@/api/supplierpms/details";
export default {
  name: "Inspection",
  dicts: ['examine_status', 'inspection_type', 'inspection_results', 'scoring_method'],
  data() {
    return {
      dialogVisible: false,
      deliveryIndex: null,
      dialogMaterial: false, // 用于标记物料信息是否可见
      //公司数据
      companiesList: [],
      // 能新增的供应商数据
      supplyCanList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedSupplierMaterialList: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 现场考察表格数据
      inspectionList: [],
      // 供应商物料表格数据
      supplierMaterialListList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        soiTitle: null,
        soiType: null,
        company: null,
        inspectionDateFrom: null,
        inspectionDateTo: null,
        scoringMethod: null,
        sdName: null,
        supplierContact: null,
        supplierContactPhone: null,
        supplierContactEmail: null,
        actualAddress: null,
        inspectionResults: null,
        finalScore: null,
        level: null,
        comprehensiveOpinions: null,
        applicant: null,
        examine: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 物料基本信息
      materialList: [],
      mtotal: 0,
      ququeryParams: {
        pageNum: 1,
        pageSize: 10,
        companiesName: null,
      },
    };
  },
  created() {
    this.getList();
    this.getList3();
    this.getCompanies();
    this.userName = this.$store.state.user.name;
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
    /** 查询现场考察列表 */
    getList() {
      this.loading = true;
      listInspection(this.queryParams).then(response => {
        this.inspectionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /**审核按钮 */
    InterviewReview() {
      this.dialogVisible = true
    },
    review(determine) {
      this.dialogVisible = false;
      if (determine) {
        this.form.examine = 3
        updateInspection(this.form).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        });
      } else {
        this.form.examine = 5
        updateInspection(this.form).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        });
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        soiId: null,
        soiTitle: null,
        soiType: null,
        company: null,
        inspectionDateFrom: null,
        inspectionDateTo: null,
        scoringMethod: null,
        sdName: null,
        supplierContact: null,
        supplierContactPhone: null,
        supplierContactEmail: null,
        actualAddress: null,
        inspectionResults: null,
        finalScore: null,
        level: null,
        comprehensiveOpinions: null,
        applicant: null,
        examine: null
      };
      this.supplierMaterialListList = [];
      this.resetForm("form");
    },
    showMaterial() {
      this.dialogMaterial = true;
      //this.getList3()
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
    /**新增公司搜索按钮 */
    handleRowClickfrom(row) {
      //console.log(row.companiesId);
      //this.form.companiesId = row.companiesId;
      this.form.company = row.companiesName;
    },
    //**新增供应商搜索按钮 */
    hanSupplierClickfrom(row) {
      //console.log(row);
      //this.form.sdId = row.sdId;
      this.form.sdName = row.sbiName;
      this.form.supplierContact = row.sdPcn;
      this.form.supplierContactEmail = row.sdPce;
      this.form.supplierContactPhone = row.sdPcp;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.soiId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.form.applicant = this.userName;
      this.title = "添加现场考察";
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const soiId = row.soiId || this.ids
      getInspection(soiId).then(response => {
        this.form = response.data;
        this.supplierMaterialListList = response.data.supplierMaterialListList;
        this.open = true;
        this.title = "修改现场考察";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.supplierMaterialListList = this.supplierMaterialListList;
          console.log(this.form);
          if (this.form.soiId != null) {
            updateInspection(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInspection(this.form).then(response => {
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
      const soiIds = row.soiId || this.ids;
      this.$modal.confirm('是否确认删除现场考察编号为"' + soiIds + '"的数据项？').then(function () {
        return delInspection(soiIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
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
      obj.supplyId = "";
      obj.manufacturer = "";
      obj.supplyCapacity = "";
      obj.notes = "";
      obj.upload = "";
      this.supplierMaterialListList.push(obj);
    },
    /** 供应商物料删除按钮操作 */
    handleDeleteSupplierMaterialList() {
      if (this.checkedSupplierMaterialList.length == 0) {
        this.$modal.msgError("请先选择要删除的供应商物料数据");
      } else {
        const supplierMaterialListList = this.supplierMaterialListList;
        const checkedSupplierMaterialList = this.checkedSupplierMaterialList;
        this.supplierMaterialListList = supplierMaterialListList.filter(function (item) {
          return checkedSupplierMaterialList.indexOf(item.index) == -1
        });
      }
    },
    /** 物料添加 */
    handleSupplierMaterialListe(row) {
      this.deliveryIndex = this.supplierMaterialListList.indexOf(row); // 获取点击的行索引
    },
    // 物料框架+数据
    handleRowClickMaterial(row) {
      console.log(row);
      this.supplierMaterialListList[this.deliveryIndex].materialCode = row.materialCode;// 物料编码
      this.supplierMaterialListList[this.deliveryIndex].materialName = row.materialName;// 物料名称
      this.supplierMaterialListList[this.deliveryIndex].categoryCode = row.categoryCode;// 品类
      this.supplierMaterialListList[this.deliveryIndex].categoryName = row.categoryName;// 物料品类
      this.dialogMaterial = false; // 关闭对话框
    },
    /** 复选框选中数据 */
    handleSupplierMaterialListSelectionChange(selection) {
      this.checkedSupplierMaterialList = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('supplierpms/inspection/export', {
        ...this.queryParams
      }, `inspection_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
#serachOne {
  position: absolute;
  right: 15px;
  top: 21.5px;
}
</style>
