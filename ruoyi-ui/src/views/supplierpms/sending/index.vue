<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="供应商名称" prop="sbiName">
        <el-input v-model="queryParams.sbiName" placeholder="请输入供应商名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="送样单标题" prop="sampleName">
        <el-input v-model="queryParams.sampleName" placeholder="请输入送样单标题" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="送样状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择送样状态" clearable>
          <el-option v-for="dict in dict.type.sample_delivery_status" :key="dict.value" :label="dict.label"
            :value="dict.value" />
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
          v-hasPermi="['supplierpms:sending:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['supplierpms:sending:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['supplierpms:sending:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['supplierpms:sending:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sendingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="送样单号" align="center" prop="sampleCode" />
      <el-table-column label="送样单标题" align="center" prop="sampleName" />
      <el-table-column label="供应商名称" align="center" prop="sbiName" />
      <el-table-column label="公司" align="center" prop="company" />
      <el-table-column label="送样类型" align="center" prop="sampleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sample_type" :value="scope.row.sampleType" />
        </template>
      </el-table-column>
      <el-table-column label="当前操作人" align="center" prop="currentOperator" />
      <el-table-column label="状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sample_delivery_status" :value="scope.row.state" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleOperate(scope.row)"
            v-hasPermi=" ['supplierpms:sending:edit'] "
            v-if="scope.row.state === 1">状态操作</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleOperate(scope.row)"
            v-hasPermi=" ['supplierpms:sending:edit'] "
            v-if="scope.row.state === 2">状态操作</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleOperate(scope.row)"
            v-hasPermi=" ['supplierpms:sending:edit'] "
            v-if="scope.row.state === 3">状态操作</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleOperate(scope.row)"
            v-hasPermi=" ['supplierpms:sending:edit'] "
            v-if="scope.row.state === 5">状态操作</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleClose(scope.row)"
            v-hasPermi=" ['supplierpms:sending:edit'] " v-if="scope.row.state === 8">关闭订单</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleClose(scope.row)"
            v-hasPermi=" ['supplierpms:sending:edit'] " v-if="scope.row.state === 6" >关闭订单</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleClose(scope.row)"
            v-hasPermi=" ['supplierpms:sending:edit'] " v-if="scope.row.state === 7" >关闭订单</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi=" ['supplierpms:sending:edit'] " v-if="scope.row.state != 4">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi=" ['supplierpms:sending:remove'] " v-if="scope.row.state != 4" >删除</el-button>
          <el-dialog title="提示" :visible.sync=" dialogVisible " width="30%">
            <!-- <span>这是一段信息</span> -->
            <el-button type="primary" @click="review(scope.row,true)">审核通过</el-button>  
            <el-button @click="review(scope.row,false)">审核不通过</el-button>
            <span slot="footer" class="dialog-footer">
            </span>
          </el-dialog>
        </template>

      </el-table-column>

    </el-table>

    <pagination v-show=" total > 0 " :total=" total " :page.sync=" queryParams.pageNum " :limit.sync=" queryParams.pageSize "
      @pagination=" getList " />

    <!-- 添加或修改送样对话框 -->
    <el-dialog :title=" title " :visible.sync=" open " width="550px" append-to-body>
      <el-form ref="form" :model=" form " :rules=" rules " label-width="100px">
        <!-- <el-form-item label="送样编号" prop="sampleCode">
          <el-input v-model="form.sampleCode" placeholder="请输入送样编号" />
        </el-form-item> -->
        <el-form-item label="送样单标题" prop="sampleName">
          <el-input v-model=" form.sampleName " placeholder="请输入送样单标题" />
        </el-form-item>
        <el-form-item label="公司" prop="company">
          <el-input v-model=" form.company " placeholder="请输入公司">
            <template v-slot:suffix>
              <el-popover placement="right" width="340" trigger="click">
                <el-table :data=" companiesList " @row-click=" handleRowClickfrom ">
                  <el-table-column width="156" property="companiesId" label="公司id"></el-table-column>
                  <el-table-column width="156" property="companiesName" label="公司名称"></el-table-column>
                </el-table>
                <i class="el-icon-search" slot="reference"></i>
              </el-popover>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="采购组织" prop="purchasing">
          <el-input v-model=" form.purchasing " placeholder="请输入采购组织" />
        </el-form-item>
        <el-form-item label="采购员" prop="purchasingBy">
          <el-input v-model=" form.purchasingBy " placeholder="请输入采购员" />
        </el-form-item>
        <el-form-item label="送样类型" prop="sampleType">
          <el-select v-model=" form.sampleType " placeholder="请选择送样类型">
            <el-option v-for=" dict  in  dict.type.sample_type " :key=" dict.value " :label=" dict.label "
              :value=" parseInt(dict.value) "></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="状态" prop="state">
          <el-select v-model="form.state" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.sample_delivery_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item> -->
        <!-- <el-form-item label="采方附件" prop="acquirerAttachments">
          <el-input v-model="form.acquirerAttachments" placeholder="请输入采方附件" />
        </el-form-item> -->
        <el-form-item label="送样单说明" prop="sampleDeliveryNote">
          <el-input v-model=" form.sampleDeliveryNote " placeholder="请输入送样单说明" />
        </el-form-item>
        <!-- <el-form-item label="发布时间" prop="releaseTime">
          <el-date-picker clearable
            v-model="form.releaseTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发布时间">
          </el-date-picker>
        </el-form-item> -->
        <el-form-item label="供应商名称" prop="sbiName">
          <el-input v-model=" form.sbiName " placeholder="请输入供应商名称">
            <template v-slot:suffix>
              <el-popover placement="right" width="340" trigger="click">
                <el-table :data=" supplyCanList " @row-click=" hanSupplierClickfrom ">
                  <el-table-column width="156" property="sdCode" label="供应商编号"></el-table-column>
                  <el-table-column width="156" property="sbiName" label="供应商名称"></el-table-column>
                </el-table>
                <i class="el-icon-search" slot="reference"></i>
              </el-popover>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="接样人" prop="currentOperator">
          <el-input v-model=" form.currentOperator " placeholder="请输入接样人" />
        </el-form-item>
        <el-form-item label="联系人" prop="contacts">
          <el-input v-model=" form.contacts " placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="telephone">
          <el-input v-model=" form.telephone " placeholder="请输入联系电话" />
        </el-form-item>
        <el-divider content-position="center">供应商物料信息</el-divider>
        <el-row :gutter=" 10 " class="mb8">
          <el-col :span=" 1.5 ">
            <el-button type="primary" icon="el-icon-plus" size="mini"
              @click=" handleAddSupplierMaterialList ">添加</el-button>
          </el-col>
          <el-col :span=" 1.5 ">
            <el-button type="danger" icon="el-icon-delete" size="mini"
              @click=" handleDeleteSupplierMaterialList ">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data=" supplierMaterialListList " :row-class-name=" rowSupplierMaterialListIndex "
          @selection-change=" handleSupplierMaterialListSelectionChange " ref="supplierMaterialList"
          @row-click=" handleSupplierMaterialListe">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料编码" prop="materialCode" width="150">
            <template slot-scope="scope">
              <el-input v-model=" scope.row.materialCode " placeholder="请输入物料编码" />
              <i class="el-icon-search" id="serachOne" @click="showMaterial()"></i>
              <el-dialog :visible.sync=" dialogMaterial " title="物料管理-浏览框" :modal=" false ">
                <!-- 这里是物料管理的内容 -->
                <el-table v-loading=" loading " :data=" materialList " @row-click=" handleRowClickMaterial ">
                  <el-table-column type="selection" width="55" align="center" />
                  <el-table-column label="物料编码" align="center" prop="materialCode" />
                  <el-table-column label="物料名称" align="center" prop="materialName" />
                  <el-table-column label="品类编码" align="center" prop="categoryCode" />
                  <el-table-column label="物料品类" align="center" prop="categoryName" />
                </el-table>
                <pagination v-show=" mtotal > 0 " :total=" mtotal " :page.sync=" ququeryParams.pageNum "
                  :limit.sync=" ququeryParams.pageSize " @pagination=" getList3 " />
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogMaterial = false">取消</el-button>
                </div>
              </el-dialog>
            </template>
          </el-table-column>
          <el-table-column label="物料名称" prop="materialName" width="150">
            <template slot-scope="scope">
              <el-input v-model=" scope.row.materialName " placeholder="请输入物料名称" />
            </template>
          </el-table-column>
          <el-table-column label="品类编码" prop="categoryCode" width="150">
            <template slot-scope="scope">
              <el-input v-model=" scope.row.categoryCode " placeholder="品类编码" />
            </template>
          </el-table-column>
          <el-table-column label="品类名称" prop="categoryName" width="150">
            <template slot-scope="scope">
              <el-input v-model=" scope.row.categoryName " placeholder="请输入品类名称" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click=" submitForm ">确 定</el-button>
        <el-button @click=" cancel ">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSending, getSending, delSending, addSending, updateSending, updataState } from "@/api/supplierpms/sending";
import { listSupply, getSupply, delSupply, addSupply, updateSupply, companies, listMaterial } from "@/api/supplierpms/supply";
import { canSupplier } from "@/api/supplierpms/details";
export default {
  name: "Sending",
  dicts: ['sample_type', 'sample_delivery_status'],
  data() {
    return {
      dialogVisible: false,
      deliveryIndex: null,
      dialogMaterial: false,
      // 物料基本信息
      materialList: [],
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
      // 送样表格数据
      sendingList: [],
      // 供应商物料表格数据
      supplierMaterialListList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      mtotal: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sampleCode: null,
        sampleName: null,
        company: null,
        purchasing: null,
        purchasingBy: null,
        sampleType: null,
        state: null,
        acquirerAttachments: null,
        sampleDeliveryNote: null,
        releaseTime: null,
        sbiName: null,
        currentOperator: null,
        contacts: null,
        telephone: null
      },
      ququeryParams: {
        pageNum: 1,
        pageSize: 10,
        companiesName: null,
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
    /** 查询送样列表 */
    getList() {
      this.loading = true;
      listSending(this.queryParams).then(response => {
        this.sendingList = response.rows;
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        sampleId: null,
        sampleCode: null,
        sampleName: null,
        company: null,
        purchasing: null,
        purchasingBy: null,
        sampleType: null,
        state: null,
        acquirerAttachments: null,
        sampleDeliveryNote: null,
        releaseTime: null,
        sbiName: null,
        currentOperator: null,
        contacts: null,
        telephone: null
      };
      this.supplierMaterialListList = [];
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
    /** 关闭订单 */
    handleClose(row) {
      row.state = 4
      updataState(row).then(response => {
        this.$modal.msgSuccess("提交成功");
        this.open = false;
        this.getList();
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.sampleId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加送样";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sampleId = row.sampleId || this.ids
      getSending(sampleId).then(response => {
        this.form = response.data;
        this.supplierMaterialListList = response.data.supplierMaterialListList;
        this.open = true;
        this.title = "修改送样";
      });
    },
    /** 物料添加 */
    handleSupplierMaterialListe(row) {
      this.deliveryIndex = this.supplierMaterialListList.indexOf(row); // 获取点击的行索引
    },
    /**点击放大镜 */
    showMaterial() {
      this.dialogMaterial = true;
    },
    /**状态操作 */
    handleOperate(row) {
      this.dialogVisible = true
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
    /**新增公司搜索按钮 */
    handleRowClickfrom(row) {
      //console.log(row.companiesId);
      //this.form.companiesId = row.companiesId;
      this.form.company = row.companiesName;
    },
    /**审核修改状态 */
    review(row, determine) {
      this.reset();
      this.dialogVisible = false;
      if (determine) {
        if (row.state == 2) {
          row.state = 3
        } else if (row.state == 3) {
          row.state = 6
        } else if (row.state == 5) {
          row.state = 8
        }

        updataState(row).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        });
      } else {
        if (row.state == 2) {
          row.state = 4
        } else if (row.state == 3) {
          row.state = 5
        } else if (row.state == 5) {
          row.state = 7
        }
        updataState(row).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        });
      }
    },
    //**新增供应商搜索按钮 */
    hanSupplierClickfrom(row) {
      //console.log(row);
      //this.form.sdId = row.sdId;
      this.form.sbiName = row.sbiName;
      this.form.contacts = row.sdPcn;
      // this.form.supplierContactEmail = row.sdPce;
      this.form.telephone = row.sdPcp;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.supplierMaterialListList = this.supplierMaterialListList;
          if (this.form.sampleId != null) {
            updateSending(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSending(this.form).then(response => {
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
      const sampleIds = row.sampleId || this.ids;
      this.$modal.confirm('是否确认删除送样编号为"' + sampleIds + '"的数据项？').then(function () {
        return delSending(sampleIds);
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
      obj.soiId = "";
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
    /** 复选框选中数据 */
    handleSupplierMaterialListSelectionChange(selection) {
      this.checkedSupplierMaterialList = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('supplierpms/sending/export', {
        ...this.queryParams
      }, `sending_${new Date().getTime()}.xlsx`)
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
