<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="质检编号" prop="qualityCode">
        <el-input
          v-model="queryParams.qualityCode"
          placeholder="请输入质检编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收货编号" prop="receiptNoteNo">
        <el-input
          v-model="queryParams.receiptNoteNo"
          placeholder="请输入收货编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="orName">
        <el-input
          v-model="queryParams.orName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司" prop="company">
        <el-input
          v-model="queryParams.company"
          placeholder="请输入公司"
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
          v-hasPermi="['pms:order:add']"
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
          v-hasPermi="['pms:order:edit']"
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
          v-hasPermi="['pms:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pms:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderQuanlityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="质检单Id" align="center" prop="qualityId" />
      <el-table-column label="采购订单编号" align="center" prop="orderCode" />
      <el-table-column label="质检编号" align="center" prop="qualityCode" />
      <el-table-column label="收货编号" align="center" prop="receiptNoteNo" />
      <el-table-column label="发货单号" align="center" prop="deliveryNoteNo" />
      <el-table-column label="创建人" align="center" prop="founder" />
      <el-table-column label="到货日期" align="center" prop="receivedDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.receivedDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检验日期" align="center" prop="qualityDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.qualityDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检验单类型" align="center" prop="inspectOrderType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inspect_order_type" :value="scope.row.inspectOrderType"/>
        </template>
      </el-table-column>
      <el-table-column label="供应商名称" align="center" prop="supplier" />
      <el-table-column label="物料编号" align="center" prop="orCode" />
      <el-table-column label="物料名称" align="center" prop="orName" />
      <el-table-column label="物料单位" align="center" prop="materialUnit" />
      <el-table-column label="物料品类" align="center" prop="materialCategory" />
      <el-table-column label="收货数量" align="center" prop="receivedQuantity" />
      <el-table-column label="送检数量" align="center" prop="inspectionQuantity" />
      <el-table-column label="质检类型" align="center" prop="qualityType" />
      <el-table-column label="检验类型" align="center" prop="inspectType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inspect_type" :value="scope.row.inspectType"/>
        </template>
      </el-table-column>
      <el-table-column label="检验开始日期" align="center" prop="inspectStartDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectStartDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检验结束日期" align="center" prop="inspectEndDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectEndDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="质检损耗数量" align="center" prop="qualifiedNumber" />
      <el-table-column label="检验合格数量" align="center" prop="inspectQualifiedNumber" />
      <el-table-column label="检验不合格品数量" align="center" prop="inspectNonConformingNumber" />
      <el-table-column label="不合格品比例" align="center" prop="nonConforming" />
      <el-table-column label="检测结果" align="center" prop="decectionResultName">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.decection_result_name" :value="scope.row.decectionResultName"/>
        </template>
      </el-table-column>
      <el-table-column label="处理措施" align="center" prop="handlingMeasuresName">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.handling_measures_name" :value="scope.row.handlingMeasuresName"/>
        </template>
      </el-table-column>
      <el-table-column label="说明" align="center" prop="remarks" />
      <el-table-column label="检验员" align="center" prop="inspecter" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.quality_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="库存组织" align="center" prop="inventory">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inventory" :value="scope.row.inventory"/>
        </template>
      </el-table-column>
      <el-table-column label="公司" align="center" prop="company" />
    
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改质检单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="采购订单编号" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入采购订单编号" />
        </el-form-item>
        <el-form-item label="质检编号" prop="qualityCode">
          <el-input v-model="form.qualityCode" placeholder="请输入质检编号" />
        </el-form-item>
        <el-form-item label="收货编号" prop="receiptNoteNo">
          <el-input v-model="form.receiptNoteNo" placeholder="请输入收货编号" />
        </el-form-item>
        <el-form-item label="发货单号" prop="deliveryNoteNo">
          <el-input v-model="form.deliveryNoteNo" placeholder="请输入发货单号" />
        </el-form-item>
        <el-form-item label="创建人" prop="founder">
          <el-input v-model="form.founder" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="到货日期" prop="receivedDate">
          <el-date-picker clearable
            v-model="form.receivedDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择到货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检验日期" prop="qualityDate">
          <el-date-picker clearable
            v-model="form.qualityDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择检验日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplier">
          <el-input v-model="form.supplier" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="物料编号" prop="orCode">
          <el-input v-model="form.orCode" placeholder="请输入物料编号" />
        </el-form-item>
        <el-form-item label="物料名称" prop="orName">
          <el-input v-model="form.orName" placeholder="请输入物料名称" />
        </el-form-item>
        <el-form-item label="物料单位" prop="materialUnit">
          <el-input v-model="form.materialUnit" placeholder="请输入物料单位" />
        </el-form-item>
        <el-form-item label="物料品类" prop="materialCategory">
          <el-input v-model="form.materialCategory" placeholder="请输入物料品类" />
        </el-form-item>
        <el-form-item label="收货数量" prop="receivedQuantity">
          <el-input v-model="form.receivedQuantity" placeholder="请输入收货数量" />
        </el-form-item>
        <el-form-item label="送检数量" prop="inspectionQuantity">
          <el-input v-model="form.inspectionQuantity" placeholder="请输入送检数量" />
        </el-form-item>
        <el-form-item label="检验开始日期" prop="inspectStartDate">
          <el-date-picker clearable
            v-model="form.inspectStartDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择检验开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检验结束日期" prop="inspectEndDate">
          <el-date-picker clearable
            v-model="form.inspectEndDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择检验结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="质检损耗数量" prop="qualifiedNumber">
          <el-input v-model="form.qualifiedNumber" placeholder="请输入质检损耗数量" />
        </el-form-item>
        <el-form-item label="检验合格数量" prop="inspectQualifiedNumber">
          <el-input v-model="form.inspectQualifiedNumber" placeholder="请输入检验合格数量" />
        </el-form-item>
        <el-form-item label="检验不合格品数量" prop="inspectNonConformingNumber">
          <el-input v-model="form.inspectNonConformingNumber" placeholder="请输入检验不合格品数量" />
        </el-form-item>
        <el-form-item label="不合格品比例" prop="nonConforming">
          <el-input v-model="form.nonConforming" placeholder="请输入不合格品比例" />
        </el-form-item>
        <el-form-item label="检测结果" prop="decectionResultName">
          <el-input v-model="form.decectionResultName" placeholder="请输入检测结果" />
        </el-form-item>
        <el-form-item label="处理措施" prop="handlingMeasuresName">
          <el-input v-model="form.handlingMeasuresName" placeholder="请输入处理措施" />
        </el-form-item>
        <el-form-item label="说明" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入说明" />
        </el-form-item>
        <el-form-item label="检验员" prop="inspecter">
          <el-input v-model="form.inspecter" placeholder="请输入检验员" />
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/pms/order";

export default {
  name: "Order",
  dicts: [ 'decection_result_name', 'quality_status', 'inspect_type', 'handling_measures_name', 'inventory', 'inspect_order_type','order_handle'],
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
      // 质检单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderCode: null,
        qualityCode: null,
        receiptNoteNo: null,
        deliveryNoteNo: null,
        founder: null,
        receivedDate: null,
        qualityDate: null,
        inspectOrderType: null,
        supplier: null,
        orCode: null,
        orName: null,
        materialUnit: null,
        materialCategory: null,
        receivedQuantity: null,
        inspectionQuantity: null,
        qualityType: null,
        inspectType: null,
        inspectStartDate: null,
        inspectEndDate: null,
        qualifiedNumber: null,
        inspectQualifiedNumber: null,
        inspectNonConformingNumber: null,
        nonConforming: null,
        decectionResultName: null,
        handlingMeasuresName: null,
        remarks: null,
        inspecter: null,
        status: null
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
    /** 查询质检单列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderQuanlityList = response.rows;
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
        qualityId: null,
        orderCode: null,
        qualityCode: null,
        receiptNoteNo: null,
        deliveryNoteNo: null,
        founder: null,
        createTime: null,
        receivedDate: null,
        qualityDate: null,
        inspectOrderType: null,
        supplier: null,
        orCode: null,
        orName: null,
        materialUnit: null,
        materialCategory: null,
        receivedQuantity: null,
        inspectionQuantity: null,
        qualityType: null,
        inspectType: null,
        inspectStartDate: null,
        inspectEndDate: null,
        qualifiedNumber: null,
        inspectQualifiedNumber: null,
        inspectNonConformingNumber: null,
        nonConforming: null,
        decectionResultName: null,
        handlingMeasuresName: null,
        remarks: null,
        inspecter: null,
        status: null
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
      this.ids = selection.map(item => item.qualityId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加质检单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const qualityId = row.qualityId || this.ids
      getOrder(qualityId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改质检单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.qualityId != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
      const qualityIds = row.qualityId || this.ids;
      this.$modal.confirm('是否确认删除质检单编号为"' + qualityIds + '"的数据项？').then(function() {
        return delOrder(qualityIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pms/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
