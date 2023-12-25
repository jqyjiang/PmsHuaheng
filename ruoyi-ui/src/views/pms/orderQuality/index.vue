<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="收货单号" prop="receiptNoteNo">
        <el-input
          v-model="queryParams.receiptNoteNo"
          placeholder="请输入收货单号"
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
          v-hasPermi="['pms:orderQuality:add']"
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
          v-hasPermi="['pms:orderQuality:edit']"
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
          v-hasPermi="['pms:orderQuality:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pms:orderQuality:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderQualityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="质检任务单Id" align="center" prop="qualityTaskListId" />
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
          <dict-tag :options="dict.type.quality_status" :value="scope.row.qualityStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pms:orderQuality:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pms:orderQuality:remove']"
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

    <!-- 添加或修改到货质检对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="采购订单编号" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入采购订单编号" />
        </el-form-item>
        <el-form-item label="收货单号" prop="receiptNoteNo">
          <el-input v-model="form.receiptNoteNo" placeholder="请输入收货单号" />
        </el-form-item>
        <el-form-item label="物料名称" prop="orName">
          <el-input v-model="form.orName" placeholder="请输入物料名称" />
        </el-form-item>
        <el-form-item label="物料编号" prop="orCode">
          <el-input v-model="form.orCode" placeholder="请输入物料编号" />
        </el-form-item>
        <el-form-item label="公司" prop="company">
          <el-input v-model="form.company" placeholder="请输入公司" />
        </el-form-item>
        <el-form-item label="收货数量" prop="receiveQuantity">
          <el-input v-model="form.receiveQuantity" placeholder="请输入收货数量" />
        </el-form-item>
        <el-form-item label="收货日期" prop="receiveDate">
          <el-date-picker clearable
            v-model="form.receiveDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择收货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="收货人" prop="consignee">
          <el-input v-model="form.consignee" placeholder="请输入收货人" />
        </el-form-item>
        <el-form-item label="状态" prop="qualityStatus">
          <el-select v-model="form.qualityStatus" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.quality_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
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
import { listOrderQuality, getOrderQuality, delOrderQuality, addOrderQuality, updateOrderQuality } from "@/api/pms/orderQuality";

export default {
  name: "OrderQuality",
  dicts: ['quality_status'],
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
        qualityTaskListId: null,
        orderCode: null,
        receiptNoteNo: null,
        orName: null,
        orCode: null,
        company: null,
        receiveQuantity: null,
        receiveDate: null,
        consignee: null,
        qualityStatus: null
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
      this.single = selection.length!==1
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
        this.open = true;
        this.title = "修改到货质检";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.qualityTaskListId != null) {
            updateOrderQuality(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrderQuality(this.form).then(response => {
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
      const qualityTaskListIds = row.qualityTaskListId || this.ids;
      this.$modal.confirm('是否确认删除到货质检编号为"' + qualityTaskListIds + '"的数据项？').then(function() {
        return delOrderQuality(qualityTaskListIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
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
