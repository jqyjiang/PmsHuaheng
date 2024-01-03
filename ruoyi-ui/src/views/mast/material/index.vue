<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料编码" prop="materialCode">
        <el-input
          v-model="queryParams.materialCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
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
          v-hasPermi="['mast:material:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mast:material:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="物料序号" align="center" prop="materialId" /> -->
      <el-table-column label="物料编码" align="center" prop="materialCode" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="基本计算单位" align="center" prop="calculationUnit" >
        <template slot-scope="scope">
          <span v-for="item in accountList">
            <template v-if="scope.row.calculationUnit===item.unitId">
              {{item.meteringUnit}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="采购员" align="center" prop="purchaser" />
      <el-table-column label="最后更新人" align="center" prop="lUpdated" />
      <el-table-column label="最后更新时间" align="center" prop="lUpdateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lUpdateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="来源系统" align="center" prop="sourceSystem" />
      <el-table-column label="是否启用" align="center" prop="enable">
       <template slot-scope="scope">
        <el-checkbox
         :value="enableStatus[scope.$index]"
         :disabled="true"
        ></el-checkbox>
       </template>
      </el-table-column>
      <!-- <el-table-column label="主品类" align="center" prop="mCategory" /> -->
      <el-table-column label="主品类" align="center" prop="mCategory" >
        <template slot-scope="scope">
          <span v-for="item in categoryList">
            <template v-if="scope.row.mCategory === item.categoryid">
              {{item.categoryName}}
            </template>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mast:material:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mast:material:remove']"
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

    <!-- 添加或修改物料维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="物料编码" prop="materialCode">
          <el-input v-model="form.materialCode" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="物料名称" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请输入物料名称" />
        </el-form-item>
        <!-- <el-form-item label="基本计算单位" prop="calculationUnit">
          <el-select v-model="form.calculationUnit" placeholder="请选择基本计算单位" >
            <el-option
              v-for="dict in accountList"
              :key="dict.unitId"
              :label="dict.meteringUnit"
              :value="dict.unitId"
            />
          </el-select>
        </el-form-item> -->

        <el-form-item v-if="false" label="计算单位" prop="calculationUnit">
          <el-input v-model="form.calculationUnit" placeholder="请输入计算单位" />
        </el-form-item>
        <el-form-item label="基本计算单位" prop="metering_unit">
          <el-input v-model="form.metering_unit" placeholder="请选择基本计算单位"/>
          <i class="el-icon-search" id="serachOne2" @click="showAccount()"></i>
              <el-dialog :visible.sync="dialogAccount" title="计算单位-浏览框" :modal="false">
                <el-table :data="accountList1" v-loading="loading" @row-click="handleRowClickAccount">
                  <el-table-column label="计算单位" align="center" prop="meteringUnit" />
                  <el-table-column label="计算单位编码" align="center" prop="calculationUnitCode" />
                </el-table>
                <pagination
                 v-show="total3>0"
                  :total="total3"
                  :page.sync="queryParams3.pageNum"
                  :limit.sync="queryParams3.pageSize"
                   @pagination="getList3" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogAccount = false">取消</el-button>
                </div>
              </el-dialog>
        </el-form-item>

        <el-form-item label="采购员" prop="purchaser">
          <el-input v-model="form.purchaser" placeholder="请输入采购员" />
        </el-form-item>
        <el-form-item label="最后更新人" prop="lUpdated">
          <el-input v-model="form.lUpdated" placeholder="请输入最后更新人" />
        </el-form-item>
        <!-- <el-form-item label="最后更新时间" prop="lUpdateTime">
          <el-date-picker clearable
            v-model="form.lUpdateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最后更新时间">
          </el-date-picker>
        </el-form-item> -->
        <el-form-item label="来源系统" prop="sourceSystem">
          <el-select v-model="form.sourceSystem" placeholder="请输入来源系统" >
            <el-option value="SRM">SRM</el-option>
            <el-option value="SAP">SAP</el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="主品类" prop="mCategory">
          <el-input v-model="form.mCategory" placeholder="请输入主品类" />
        </el-form-item> -->

        <el-form-item v-if="false" label="主品类" prop="mCategory">
          <el-input v-model="form.mCategory" placeholder="请输入计算单位" />
        </el-form-item>
        <el-form-item label="主品类" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请选择主品类"/>
          <i class="el-icon-search" id="serachOne2" @click="showCategory()"></i>
              <el-dialog :visible.sync="dialogCategory" title="主品类-浏览框" :modal="false">
                <el-table :data="categoryList1" v-loading="loading" @row-click="handleRowClickCategory">
                  <el-table-column label="品类名称" align="center" prop="categoryName" />
                  <el-table-column label="品类编码" align="center" prop="categoryCode" />
                </el-table>
                <pagination v-show="catotal > 0" :total="catotal" :page.sync="caqueryParams.pageNum"
                  :limit.sync="caqueryParams.pageSize" @pagination="getList4" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogCategory = false">取消</el-button>
                </div>
              </el-dialog>
        </el-form-item>

        <el-form-item label="规格" prop="specifications">
          <el-input v-model="form.specifications" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <!-- <el-form-item label="默认税种/税率" prop="categoriesTaxes">
          <el-input v-model="form.categoriesTaxes" placeholder="请输入默认税种/税率" />
        </el-form-item> -->

        <el-form-item label="默认税种/税率" prop="categoriesTaxes">
          <el-input v-model="form.categoriesTaxes" placeholder="请选择默认税种/税率"/>
          <i class="el-icon-search" id="serachOne2" @click="showRate()"></i>
              <el-dialog :visible.sync="dialogRate" title="默认税种/税率-浏览框" :modal="false">
                <el-table :data="rateList" v-loading="loading" @row-click="handleRowClickRate">
                  <el-table-column label="税种代码" align="center" prop="taxCode" />
                  <el-table-column label="描述" align="center" prop="describes" />
                  <el-table-column label="税率(%)" align="center" prop="taxRate" />
                  <el-table-column label="启用" align="center" prop="enable" />
                </el-table>
                <pagination v-show="total5 > 0" :total="total5" :page.sync="queryParams5.pageNum"
                  :limit.sync="queryParams5.pageSize" @pagination="getList5" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogRate = false">取消</el-button>
                </div>
              </el-dialog>
        </el-form-item>

        <el-form-item label="物料图片" prop="image">
          <image-upload v-model="form.image"/>
        </el-form-item>
        <el-form-item label="毛重" prop="gWeight">
          <el-input v-model="form.gWeight" placeholder="请输入毛重" />
        </el-form-item>
        <el-form-item label="净重" prop="nWeight">
          <el-input v-model="form.nWeight" placeholder="请输入净重" />
        </el-form-item>
        <!-- <el-form-item label="重量单位" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入重量单位" />
        </el-form-item> -->

        <el-form-item v-if="false" label="重量单位" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入重量单位" />
        </el-form-item>
        <el-form-item label="基本重量单位" prop="weightName">
          <el-input v-model="form.weightName" placeholder="请选择基本重量单位"/>
          <i class="el-icon-search" id="serachOne2" @click="showAccount1()"></i>
              <el-dialog :visible.sync="dialogAccount1" title="重量单位-浏览框" :modal="false">
                <el-table :data="accountList1" v-loading="loading" @row-click="handleRowClickAccount1">
                  <el-table-column label="重量单位" align="center" prop="meteringUnit" />
                  <el-table-column label="重量单位编码" align="center" prop="calculationUnitCode" />
                </el-table>
                <pagination v-show="total6 > 0" :total="total6" :page.sync="queryParams6.pageNum"
                  :limit.sync="queryParams6.pageSize" @pagination="getList6" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogAccount1 = false">取消</el-button>
                </div>
              </el-dialog>
        </el-form-item>

        <el-form-item label="体积" prop="volume">
          <el-input v-model="form.volume" placeholder="请输入体积" />
        </el-form-item>
        <!-- <el-form-item label="体积单位" prop="vUnit">
          <el-input v-model="form.vUnit" placeholder="请输入体积单位" />
        </el-form-item> -->

        <el-form-item v-if="false" label="体积单位" prop="vUnit">
          <el-input v-model="form.vUnit" placeholder="请输入重量单位" />
        </el-form-item>
        <el-form-item label="体积单位" prop="vUnitName">
          <el-input v-model="form.vUnitName" placeholder="请选择体积单位"/>
          <i class="el-icon-search" id="serachOne2" @click="showAccount2()"></i>
              <el-dialog :visible.sync="dialogAccount2" title="体积单位-浏览框" :modal="false">
                <el-table :data="accountList1" v-loading="loading" @row-click="handleRowClickAccount2">
                  <el-table-column label="体积单位" align="center" prop="meteringUnit" />
                  <el-table-column label="体积单位编码" align="center" prop="calculationUnitCode" />
                </el-table>
                <pagination v-show="total7 > 0" :total="total7" :page.sync="queryParams7.pageNum"
                  :limit.sync="queryParams7.pageSize" @pagination="getList7" />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogAccount2 = false">取消</el-button>
                </div>
              </el-dialog>
        </el-form-item>

        <el-form-item label="物料ABC属性" prop="abcAttribute">
          <el-select v-model="form.abcAttribute" placeholder="请选择物料ABC属性" >
          <el-option value="A">A</el-option>
          <el-option value="B">B</el-option>
          <el-option value="C">C</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="启用" prop="enable">
        <el-checkbox v-model="form.enable" ></el-checkbox>
        </el-form-item>
        <el-form-item label="是否免检" prop="avoidInspect">
        <el-checkbox v-model="form.avoidInspect" ></el-checkbox>
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
import { listMaterial, getMaterial, delMaterial, addMaterial, updateMaterial ,listAccount ,listCategory,listAccount1,listCategory1,listRate} from "@/api/mast/material";

export default {
  name: "Material",
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
      catotal:0,
      total3:0,
      total5:0,
      total6:0,
      total7:0,
      //------------
      // rowCategoryIndex:10,

      // 物料维护表格数据
      materialList: [],
      accountList: [],
      enableStatus: [], // 用于存储复选框选中状态的数组
      avStatus:[],
      categoryList: [],
      accountList1:[],
      categoryList1:[],
      rateList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数物料
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
        enable: null,
      },
      // 查询计算单位物料
      queryParams3: {
        pageNum: 1,
        pageSize: 10,
      },
      // 查询参数品类
      caqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 查询参数税率
      queryParams5: {
        pageNum: 1,
        pageSize: 10,
      },
      // 查询参数重量单位
      queryParams6: {
        pageNum: 1,
        pageSize: 10,
      },
      // 查询参数体积单位
      queryParams7: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      dialogAccount:false,
      dialogCategory:false,
      dialogRate:false,
      dialogAccount1:false,
      dialogAccount2:false,
    };
  },
  created() {
    this.getList();
    this.getList1();
    this.getList2();
    this.getList3();
    this.getList5();
    this.getList6();
    this.getList7();
  },
  methods: {
    /** 查询物料维护列表 */
    getList() {
      this.loading = true;
      listMaterial(this.queryParams).then(response => {
        this.materialList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.enableStatus = this.materialList.map((account) => account.enable === 1);
        this.avStatus=this.materialList.map((account)=>account.avoidInspect===1);
      });
    },
     /** 查询计算单位定义列表 */
     getList1() {
      listAccount(this.queryParams).then(response => {
        this.accountList = response.rows;

      });
    },
    /** 查询计算单位定义列表 弹框*/
    getList3() {
      listAccount1(this.queryParams3).then(response => {
        this.accountList1 = response.rows;
        this.total3 = response.total;
      });
    },
      /** 查询品类列表 */
    getList2() {
      listCategory(this.queryParams).then(response => {
        this.categoryList = response.rows;

      });
    },
     /** 查询品类列表 弹框*/
     getList4() {
      listCategory1(this.caqueryParams).then(response => {
        this.categoryList1 = response.rows;
        this.catotal=response.total;
        console.info(this.catotal);
      });
    },
    /** 查询税率列表 弹框*/
    getList5() {
      listRate(this.queryParams5).then(response => {
        this.rateList = response.rows;
        this.total5=response.total;
      });
    },
    /**重量单位 */
    getList6() {
      listAccount1(this.queryParams6).then(response => {
        this.accountList1 = response.rows;
        this.total6=response.total;
      });
    },
    /**体积单位 */
    getList7() {
      listAccount1(this.queryParams7).then(response => {
        this.accountList1 = response.rows;
        this.total7=response.total;
      });
    },

    //计量单位
    showAccount() {
      this.dialogAccount = true;
      this.getList3();
    },
    //体积单位
    showAccount2(){
      this.dialogAccount2 = true;
      this.getList7();
    },
    //重量单位
    showAccount1(){
      this.dialogAccount1 = true;
      this.getList6();
    },
    //主品类
    showCategory() {
      this.dialogCategory = true;
      this.getList4();
    },
    //税率/税种
    showRate(){
      this.dialogRate = true;
      this.getList5();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        materialId: null,
        materialCode: null,
        materialName: null,
        calculationUnit: null,
        purchaser: null,
        lUpdated: null,
        lUpdateTime: null,
        sourceSystem: null,
        enable: null,
        mCategory: null,
        specifications: null,
        model: null,
        brand: null,
        categoriesTaxes: null,
        image: null,
        gWeight: null,
        nWeight: null,
        weight: null,
        volume: null,
        vUnit: null,
        abcAttribute: null,
        avoidInspect: null
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
      this.ids = selection.map(item => item.materialId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加物料维护";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const materialId = row.materialId || this.ids
      getMaterial(materialId).then(response => {
        if(response.data.enable==1){
          response.data.enable=true
        }else{
          response.data.enable=false
        }
        if(response.data.avoidInspect==1){
          response.data.avoidInspect=true
        }else{
          response.data.avoidInspect=false
        }
        this.form = response.data;
        this.open = true;
        this.title = "修改物料维护";
      });
    },

    //计算单位
    handleRowClickAccount(row){
       // 修改数据的属性值
      this.form.metering_unit = row.meteringUnit;
      this.form.calculationUnit = row.unitId;
      this.dialogAccount = false; // 关闭对话框
    },
    //重量单位
    handleRowClickAccount1(row){
       // 修改数据的属性值
      this.form.weightName = row.weightName;
      this.form.weight = row.unitId;
      this.dialogAccount1 = false; // 关闭对话框
    },
    //体积单位
    handleRowClickAccount2(row){
       // 修改数据的属性值
      this.form.vUnitName = row.meteringUnit;
      this.form.vUnit = row.unitId;
      this.dialogAccount2 = false; // 关闭对话框
    },
    //品类
    handleRowClickCategory(row){
       // 修改数据的属性值
      this.form.categoryName = row.categoryName;
      this.form.mCategory = row.categoryid;
      this.dialogCategory = false; // 关闭对话框
    },
    //税率
    handleRowClickRate(row){
       // 修改数据的属性值
      this.form.categoriesTaxes = row.taxCode;
      this.dialogRate = false; // 关闭对话框
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.materialId != null) {
            if(this.form.enable==true){
              this.form.enable=1
            }else{
              this.form.enable=0
            }
            if(this.form.avoidInspect==true){
              this.form.avoidInspect=1
            }else{
              this.form.avoidInspect=0
            }
            updateMaterial(this.form).then(response => {
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
            if(this.form.avoidInspect==true){
              this.form.avoidInspect=1
            }else{
              this.form.avoidInspect=0
            }
            addMaterial(this.form).then(response => {
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
      const materialIds = row.materialId || this.ids;
      this.$modal.confirm('是否确认删除物料维护编号为"' + materialIds + '"的数据项？').then(function() {
        return delMaterial(materialIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mast/material/export', {
        ...this.queryParams
      }, `material_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
#serachOne2 {
  position: absolute;
  right: 15px;
  top: 12.5px;
}
</style>
