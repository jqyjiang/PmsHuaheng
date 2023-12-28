<template>
  <div class="app-container">
    <div class="tab-container">
      <div @click="handleTabClick('质检任务')">质检任务</div>
      <div @click="handleTabClick('质检单')">质检单</div>
      <div @click="handleTabClick('免检单')">免检单</div>
    </div>

    <div v-if="activeTab === '质检任务'">
      <!-- 质检任务内容 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="收货单号" prop="receiptNoteNo">
          <el-input v-model="queryParams.receiptNoteNo" placeholder="请输入收货单号" clearable
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="物料名称" prop="orName">
          <el-input v-model="queryParams.orName" placeholder="请输入物料名称" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="公司" prop="company">
          <el-input v-model="queryParams.company" placeholder="请输入公司" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['pms:orderQuality:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['pms:orderQuality:edit']">修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['pms:orderQuality:remove']">删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
            v-hasPermi="['pms:orderQuality:export']">导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="orderQualityList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="采购订单编号" align="center" prop="orderCode" />
        <el-table-column label="收货单号" align="center" prop="receiptNoteNo" />
        <el-table-column label="物料名称" align="center" prop="orName" />
        <el-table-column label="物料编号" align="center" prop="orCode" />
        <el-table-column label="公司" align="center" prop="company" />
        <el-table-column label="收货数量" align="center" prop="receiveQuantity" />
        <el-table-column label="收货日期" align="center" prop="receiveDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.receiveDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="收货人" align="center" prop="consignee" />
        <el-table-column label="状态" align="center" prop="qualityStatus">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.quality_status" :value="scope.row.qualityStatus" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
              v-hasPermi="['pms:orderQuality:edit']">质检</el-button><!--修改-->
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleExempt(scope.row)"
              v-hasPermi="['pms:orderQuality:remove']">免检</el-button><!--删除-->
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />
    </div>

    <div v-else-if="activeTab === '质检单'">
      <!-- 质检单内容 -->
      <order></order>
    </div>
    <div v-else-if="activeTab === '免检单'">
      <!-- 免检单内容 -->
      <el-form :model="squeryParams" ref="squeryParams" size="small" :inline="true" v-show="showSearch"
        label-width="68px">
        <el-form-item label="收货单号" prop="receiptNoteNo">
          <el-input v-model="squeryParams.receiptNoteNo" placeholder="请输入收货单号" clearable
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="物料名称" prop="orName">
          <el-input v-model="squeryParams.orName" placeholder="请输入物料名称" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="公司" prop="company">
          <el-input v-model="squeryParams.company" placeholder="请输入公司" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="exemptList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="物料编号" align="center" prop="orCode" />
        <el-table-column label="物料名称" align="center" prop="orName" />
        <el-table-column label="公司" align="center" prop="company" />
        <el-table-column label="采购订单编号" align="center" prop="orderCode" />
        <el-table-column label="收货单号" align="center" prop="receiptNoteNo" />
        <el-table-column label="收货数量" align="center" prop="receiveQuantity" />
        <el-table-column label="收货日期" align="center" prop="receiveDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.receiveDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="收货人" align="center" prop="consignee" />
        <el-table-column label="状态" align="center" prop="qualityStatus">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.quality_status" :value="scope.row.qualityStatus" />
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="stotal > 0" :total="stotal" :page.sync="squeryParams.pageNum"
        :limit.sync="squeryParams.pageSize" @pagination="getLists" />
    </div>


    <!-- 添加或修改质检单对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
        <div style="display: grid; grid-template-columns: 1fr 1fr; column-gap: 20px;">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <!-- 第一排表单项 -->
            <el-form-item label="采购订单编号" prop="orderCode">
              <el-input v-model="form.orderCode" placeholder="请输入采购订单编号" />
            </el-form-item>
            <el-form-item label="质检单号" prop="qualityCode">
              <el-input v-model="form.qualityCode" placeholder="请输入质检编号" />
            </el-form-item>
            <el-form-item label="收货单号" prop="receiptNoteNo">
              <el-input v-model="form.receiptNoteNo" placeholder="请输入收货编号" />
            </el-form-item>
            <el-form-item label="发货单号" prop="deliveryNoteNo">
              <el-input v-model="form.deliveryNoteNo" placeholder="请输入发货单号" />
            </el-form-item>
            <el-form-item label="创建人" prop="founder">
              <el-input v-model="form.founder" placeholder="请输入创建人" />
            </el-form-item>
            <el-form-item label="到货日期" prop="receivedDate">
              <el-date-picker clearable v-model="form.receivedDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择到货日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="检验日期" prop="qualityDate">
              <el-date-picker clearable v-model="form.qualityDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择检验日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="检验单类型" prop="inspectOrderType">
              <el-select v-model="form.inspectOrderType" placeholder="请选择检验单类型">
                <el-option v-for="dict in dict.type.inspect_order_type" :key="dict.value" :label="dict.label"
                  :value="parseInt(dict.value)"></el-option>
              </el-select>
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
            <el-form-item label="公司" prop="company">
              <el-input v-model="form.company" placeholder="请输入公司" />
            </el-form-item>
            <!-- 其他表单项省略 -->
          </el-form>

          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <!-- 第二排表单项 -->
            <el-form-item label="到货日期" prop="receivedDate">
              <el-date-picker clearable v-model="form.receivedDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择到货日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="检验类型" prop="inspectType">
              <el-select v-model="form.inspectType" placeholder="请选择检验类型">
                <el-option v-for="dict in dict.type.inspect_type" :key="dict.value" :label="dict.label"
                  :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="检验开始日期" prop="inspectStartDate">
              <el-date-picker clearable v-model="form.inspectStartDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择检验开始日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="检验结束日期" prop="inspectEndDate">
              <el-date-picker clearable v-model="form.inspectEndDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择检验结束日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="送检数量" prop="inspectionQuantity">
              <el-input v-model="form.inspectionQuantity" placeholder="请输入送检数量" />
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
              <el-input :value="nonConformingRatio" disabled />
            </el-form-item>
            <el-form-item label="检测结果" prop="decectionResultName">
              <el-select v-model="form.decectionResultName" placeholder="请选择检测结果">
                <el-option v-for="dict in dict.type.decection_result_name" :key="dict.value" :label="dict.label"
                  :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="处理措施" prop="handlingMeasuresName">
              <el-select v-model="form.handlingMeasuresName" placeholder="请选择处理措施">
                <el-option v-for="dict in dict.type.handling_measures_name" :key="dict.value" :label="dict.label"
                  :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="说明" prop="remarks">
              <el-input v-model="form.remarks" placeholder="请输入说明" />
            </el-form-item>
            <el-form-item label="检验员" prop="inspecter">
              <el-input v-model="form.inspecter" placeholder="请输入检验员" />
            </el-form-item>
            <el-form-item label="库存组织" prop="inventory">
              <el-select v-model="form.inventory" placeholder="请选择库存组织">
                <el-option v-for="dict in dict.type.inventory" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
            <!-- 其他表单项省略 -->
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      </el-dialog>
  </div>
</template>

<script>
import { listOrderQuality, getOrderQuality, delOrderQuality, addOrderQuality, updateOrderQuality, updateExempt, getExemptList } from "@/api/pms/orderQuality";
import { listOrderQuanlity } from '@/api/pms/orderDetail'
import { getCategory } from '@/api/pms/materials'
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/pms/order";
import order from '../order/index.vue'

export default {
  name: "OrderQuality",
  dicts: ['quality_status', 'decection_result_name', 'quality_status', 'inspect_type', 'handling_measures_name', 'inventory', 'inspect_order_type'],
  components: {
    order
  },
  data() {
    return {
      activeTab: '质检任务',
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
      // 到货质检表格数据
      orderQualityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        receiptNoteNo: null,
        orName: null,
        company: null,
      },
      // 查询参数
      squeryParams: {
        pageNum: 1,
        pageSize: 10,
        receiptNoteNo: null,
        orName: null,
        company: null,
      },
      stotal: 0,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //免检单列表数据
      exemptList: []
    };
  },
  created() {
    this.getList();
  },
  computed: {
    nonConformingRatio() {
      const { inspectQualifiedNumber, inspectNonConformingNumber } = this.form;

      if (inspectQualifiedNumber !== undefined && inspectNonConformingNumber !== undefined && inspectNonConformingNumber >= 0) {
        const ratio = inspectNonConformingNumber / (inspectQualifiedNumber + inspectNonConformingNumber) * 100;

        if (ratio === 0) {
          return '0.00%';
        } else {
          return isNaN(ratio) ? '' : ratio.toFixed(2) + '%';
        }
      } else {
        return '';
      }
    }

  },
  watch: {
    'form.inspectQualifiedNumber': function (newValue) {
      // 在检验合格数量变化时自动计算不合格品数量
      this.updateInspectNonConformingNumber();

      // 在检验合格数量变化时更新不合格品比例
      this.updateNonConformingRatio();
    },
    'form.inspectionQuantity': function (newValue) {
      // 在送检数量变化时自动计算不合格品数量
      this.updateInspectNonConformingNumber();
    }
  },
  methods: {
    updateInspectNonConformingNumber() {
      const { inspectionQuantity, inspectQualifiedNumber } = this.form;
      if (inspectionQuantity && inspectQualifiedNumber) {
        const nonConformingNumber = inspectionQuantity - inspectQualifiedNumber;
        this.form.inspectNonConformingNumber = isNaN(nonConformingNumber) ? '' : nonConformingNumber;
      }
    },
    updateNonConformingRatio() {
      const { inspectQualifiedNumber, inspectNonConformingNumber } = this.form;
      if (inspectQualifiedNumber && inspectNonConformingNumber) {
        const ratio = inspectNonConformingNumber / inspectQualifiedNumber * 100;
        this.form.nonConforming = isNaN(ratio) ? '' : ratio.toFixed(2) + '%';
      }
    },
    handleTabClick(tab) {
      this.activeTab = tab;
    },
    //查询免检单
    getLists() {
      this.loading = true;
      getExemptList(this.squeryParams).then(response => {
        this.exemptList = response.rows;
        this.stotal = response.total;
        this.loading = false;
      });
    },
    /** 查询到货质检列表 */
    getList() {
      this.loading = true;
      listOrderQuality(this.queryParams).then(response => {
        this.orderQualityList = response.rows;
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
        status: null,
        inventory: null,
        company: null
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
      this.ids = selection.map(item => item.qualityTaskListId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加到货质检";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const qualityTaskListId = row.qualityTaskListId || this.ids
      getOrderQuality(qualityTaskListId).then(response => {
        this.form = response.data;
        this.form.receivedDate = response.data.receiveDate
        listOrderQuanlity(this.form.orderCode, this.form.receiptNoteNo).then(response => {
          this.form.deliveryNoteNo = response.data.deliveryNoteNo
          this.form.founder = response.data.receiver
          this.form.supplier = response.data.supplier
          this.form.materialUnit = response.data.materialUnit
          this.form.inspectionQuantity = response.data.receiveQuantity
          this.form.receivedQuantity = response.data.receiveQuantity
          getCategory(this.form.orName, this.form.orderCode).then(response => {
            this.form.materialCategory = response.data.materialCategory
            this.form.inspecter = this.$store.state.user.name
            this.open = true;
            this.title = "质量检验流程";
          })
        })
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // if (this.form.qualityTaskListId != null) {
          //   updateOrderQuality(this.form).then(response => {
          //     this.$modal.msgSuccess("修改成功");
          //     this.open = false;
          //     this.getList();
          //   });
          // } else {
          //   addOrderQuality(this.form).then(response => {
          //     this.$modal.msgSuccess("新增成功");
          //     this.open = false;
          //     this.getList();
          //   });
          // }
          addOrder(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /**
     * 免检操作
     */
    handleExempt(row) {
      console.log(row);
      updateExempt(row).then(response => {
        this.$modal.msgSuccess(response.msg)
        this.getLists();
        this.getList();
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const qualityTaskListIds = row.qualityTaskListId || this.ids;
      this.$modal.confirm('是否确认删除到货质检编号为"' + qualityTaskListIds + '"的数据项？').then(function () {
        return delOrderQuality(qualityTaskListIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pms/orderQuality/export', {
        ...this.queryParams
      }, `orderQuality_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
.tab-container {
  display: flex;

}

.tab-container div {
  margin-right: 10px;
  /* 调整右侧距离 */
}

.tab-container div:last-child {
  margin-right: 0;
  /* 最后一个选项不设置右侧距离 */
}
</style>