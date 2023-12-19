<template>
  <div class="app-container">
    <div style="background-color: #f9f9f9; border: 1px solid #eaeaea; padding: 10px;">
      <div style="display: flex; align-items: center; justify-content: space-between; ">
        <div>
          <!-- 左边的图片和文字 -->
          <img :src="headerImages" alt="采购订单管理"
            style="width: 40px; height: 40px;margin-right: 10px; vertical-align: middle;">
          <span style="font-size: 18px; line-height: 40px; vertical-align: middle;">采购订单管理</span>
        </div>

        <div>
          <!-- 右边的四个个按钮 -->
          <el-button type="primary" size="small" @click="invoice">新建发货单</el-button>
          <el-button type="primary" size="small" @click="drawer1 = true">审核</el-button>
          <el-button type="primary" size="small" label="rtl" @click="drawer = true">需求转订单</el-button>
          <el-button type="primary" size="small">合同转订单</el-button>
          <el-button type="primary" size="small" @click="orderCancelbutton">关闭订单</el-button>
        </div>
      </div>
    </div>
    <div>
      <span @click="showWholeOrder" :class="{ active: isWholeOrder }">整单</span>
      <span @click="showOrderDetail" :class="{ active: !isWholeOrder }">订单明细</span>
    </div>
    <div class="order-info" v-show="isWholeOrder">
      <div style="background-color: #ffffff; border: 1px solid #eaeaea; padding: 10px;">
        <el-form :model="formData" label-width="40px">
          <el-row>
            <!-- 添加“全部”选项 -->
            <div style="margin-top: 20px;">
              <el-col :span="1.3" :inline="true" size="small">
                <el-link :class="{ 'selected': selectedRoute === null }" type="primary" @click="selectRoute(null)">
                  全部({{ this.runNumber.length }})
                </el-link>
              </el-col>
            </div>

            <!-- 循环显示其他选项 -->
            <el-col v-for="route in typeRunList" :key="route.ortId" :span="1.3" :inline="true" size="small">
              <el-form-item>
                <el-link :class="{ 'selected': selectedRoute === route }" type="primary"
                  @click="selectRoute(route.ortId)">
                  {{ route.ortName }} ({{ getTotalCount(route.ortId) }})
                </el-link>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <el-drawer title="需求任务转订单" :visible.sync="drawer" :direction="direction" :width="60">
        <el-button type="primary" @click="transferToOrder">确定</el-button>
        <el-table v-loading="loading" :data="taskList" @selection-change="SelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="任务单号" align="center" prop="taskCode" />
          <el-table-column label="采购策略" align="center" prop="procurementStrategy" />
          <el-table-column label="任务总金额" align="center" prop="taskTotal" />
          <el-table-column label="需求编号-行号" align="center" prop="requirementId" />
          <el-table-column label="公司" align="center" prop="companiesId" />
          <el-table-column label="采购员" align="center" prop="purchaser" />
          <el-table-column label="物料名称" align="center" prop="materialId" />
          <el-table-column label="币种" align="center" prop="currencyId" />
          <el-table-column label="受理策略" align="center" prop="acceptanceStrategy" />
          <el-table-column label="任务总数量" align="center" prop="taskNumber" />
          <el-table-column label="已受理数量" align="center" prop="acceptedQuantity" />
          <el-table-column label="占用任务数量" align="center" prop="taskOccupied" />
          <el-table-column label="待受理数量" align="center" prop="taskAccepted" />
        </el-table>
        <pagination v-show="rwtotal > 0" :total="rwtotal" :page.sync="rwqueryParams.pageNum"
          :limit.sync="rwqueryParams.pageSize" @pagination="getList5" />
      </el-drawer>
      <div style="background-color: #ffffff; border: 1px solid #eaeaea; padding: 10px; margin-bottom: 10px;">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="108px"
          style="position: relative; top: 15px;">
          <el-form-item label="供应商名称:" prop="supplier">
            <el-input v-model="squeryParams.sbiName" placeholder="" clearable @keyup.enter.native="handleQuery"
              icon="el-icon-search" />
            <i class="el-icon-search" id="serachOne" @click="showDiagSupplie()"></i>
            <el-dialog :visible.sync="dialogVisible" title="供应商名称">
              <!-- 这里是供应商的内容 -->

              <el-table v-loading="loading" :data="detailsList" @row-click="handleRowClick">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="供应商详细编码" align="center" prop="sdCode" />
                <el-table-column label="供应商名称" align="center" prop="sbiName" />
              </el-table>

              <pagination v-show="stotal > 0" :total="stotal" :page.sync="squeryParams.pageNum"
                :limit.sync="squeryParams.pageSize" @pagination="getList1" />
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
              </div>
            </el-dialog>
          </el-form-item>
          <el-form-item label="订单号:" prop="orderCode">
            <el-input v-model="queryParams.orderCode" placeholder="请输入订单号" clearable @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="订单来源:" prop="orderSource">
            <el-select v-model="queryParams.orderSource" placeholder="请选择订单来源" clearable>
              <el-option v-for="dict in dict.type.order_source" :key="dict.value" :label="dict.label"
                :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="采购审批状态:" prop="orderState">
            <el-select v-model="queryParams.orderState" placeholder="请选择采购审批状态" clearable>
              <el-option v-for="dict in dict.type.order_state" :key="dict.value" :label="dict.label"
                :value="dict.value" />
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['pms:manager:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['pms:manager:edit']">修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['pms:manager:remove']">删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
            v-hasPermi="['pms:manager:export']">导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="managerList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="关联合同名称" align="center" prop="contractName" />
        <!-- <el-table-column label="采购订单id" align="center" prop="orderId" /> -->
        <el-table-column label="订单号" align="center" prop="orderCode" width="150px">
          <template slot-scope="scope">
            <span @click="redirectToOtherPage(scope.row.orderId)">{{ scope.row.orderCode }}</span>
          </template>
        </el-table-column>
        <el-table-column label="供应商名称" align="center" prop="supplierDetails.sbiName" />
        <el-table-column label="物料信息" align="center" prop="materialId">
          <template slot-scope="scope">
            <span>{{ scope.row ? getFormattedMaterialName(scope.row) : '' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="订单执行状态" align="center" prop="orderTypeRunning.ortName">
          <template slot-scope="scope">
            <div v-html="getFormattedOrderTypeRunning(scope.row)"></div>
          </template>
        </el-table-column>
        <el-table-column label="需求总数量" align="center" prop="totalDemand">
          <template slot-scope="scope">
            <span>
              {{ scope.row.totalDemand !== null && !isNaN(scope.row.totalDemand)
                ? parseFloat(scope.row.totalDemand).toFixed(2)
                : '0.00' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="采购员" align="center" prop="purchaser" />
        <el-table-column label="含税总金额" align="center" prop="taxTotal">
          <template slot-scope="scope">
            <span>
              {{ scope.row.taxTotal !== null && !isNaN(scope.row.taxTotal)
                ? parseFloat(scope.row.taxTotal).toFixed(2)
                : '0.00' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="订单来源" align="center" prop="orderSource">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.order_source" :value="scope.row.orderSource" />
          </template>
        </el-table-column>
        <el-table-column label="采购订单创建日期" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <div>
              <div>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</div>
              <div>{{ parseTime(scope.row.createTime, '{h}:{i}:{s}') }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="采购审批状态" align="center" prop="orderState">
          <template slot-scope="scope">
            <span :class="getStatusClass(scope.row.orderState)">
              <dict-tag :options="dict.type.order_state" :value="scope.row.orderState" />
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
              v-hasPermi="['pms:manager:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
              v-hasPermi="['pms:manager:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />

      <!-- 添加或修改采购订单管理对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="采购订单编号" prop="orderCode">
            <el-input v-model="form.orderCode" placeholder="请输入采购订单编号" disabled />
          </el-form-item>
          <el-form-item label="公司" prop="company">
            <el-input v-model="form.company" placeholder="请输入公司" />
          </el-form-item>
          <el-form-item label="订单类型" prop="orderType">
            <el-select v-model="form.orderType" placeholder="请选择订单类型">
              <el-option v-for="dict in dict.type.order_type" :key="dict.value" :label="dict.label"
                :value="parseInt(dict.value)"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="订单来源" prop="orderSource">
            <el-select v-model="form.orderSource" placeholder="请选择订单来源">
              <el-option v-for="dict in dict.type.order_source" :key="dict.value" :label="dict.label"
                :value="dict.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="采购组织" prop="purOrganization">
            <el-select v-model="form.purOrganization" placeholder="请选择采购组织">
              <el-option v-for="dict in dict.type.procure" :key="dict.value" :label="dict.label"
                :value="parseInt(dict.value)"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="附件" prop="annex">
            <file-upload v-model="form.annex" />
          </el-form-item>
          <el-form-item label="是否自提" prop="isSelfPickup">
            <el-select v-model="form.isSelfPickup" placeholder="请选择是否自提">
              <el-option v-for="dict in dict.type.self_pickup" :key="dict.value" :label="dict.label"
                :value="parseInt(dict.value)"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="自提车牌号" prop="licensePlateNumber">
            <el-input v-model="form.licensePlateNumber" placeholder="请输入自提车牌号" :disabled="!isSelfPickupSelected" />
          </el-form-item>
          <el-form-item label="自提司机联系方式" prop="concatInfomation">
            <el-input v-model="form.concatInfomation" placeholder="请输入自提司机联系方式" :disabled="!isSelfPickupSelected" />
          </el-form-item>
          <el-form-item label="关联合同编号" prop="contractCode">
            <el-input v-model="form.contractCode" placeholder="请输入关联合同编号" disabled />
          </el-form-item>
          <el-form-item label="关联合同名称" prop="contractName">
            <el-input v-model="form.contractName" placeholder="请输入关联合同名称" />
          </el-form-item>
          <el-form-item label="订单说明" prop="orderDescription">
            <el-input v-model="form.orderDescription" placeholder="请输入订单说明" />
          </el-form-item>
          <el-form-item label="需求总数量" prop="totalDemand">
            <el-input :value="isCalculationNeeded ? totalDemands : (number ? number.toFixed(2) : '')" disabled
              placeholder="请输入需求总数量" />
          </el-form-item>
          <el-form-item label="含税总金额(元)" prop="taxTotal">
            <el-input :value="isCalculationNeeded ? calculateTotalAmount : (taxTotal ? taxTotal.toFixed(2) : '')" disabled
              placeholder="请输入含税总金额" />
          </el-form-item>
          <el-form-item label="采购员" prop="purchaser">
            <el-input v-model="form.purchaser" placeholder="请输入采购员" />
          </el-form-item>
          <el-form-item label="币种" prop="currencyId">
            <el-input v-model="currencyName" placeholder="请输入币种" />
            <i class="el-icon-search" id="serachOne" @click="showDiagCurrency"></i>
            <el-dialog :visible.sync="dialogCurrency" title="币种定义" :modal="false">
              <el-form :model="queryParams" ref="cqueryForm" size="small" :inline="true" v-show="showSearch"
                label-width="68px">
                <el-form-item label="币种编码" prop="currencyCode">
                  <el-input v-model="cqueryParams.currencyCode" placeholder="请输入币种编码" clearable
                    @keyup.enter.native="handleQuery2" />
                </el-form-item>
                <el-form-item label="币种名称" prop="currencyName">
                  <el-input v-model="cqueryParams.currencyName" placeholder="请输入币种名称" clearable
                    @keyup.enter.native="handleQuery2" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery2">搜索</el-button>
                </el-form-item>
              </el-form>
              <el-table v-loading="loading" :data="currencyList" @row-click="handleSelectionChangeCurrency">
                <el-table-column type="selection" width="55" align="center" />
                <!-- <el-table-column label="币种ID" align="center" prop="currencyId" /> -->
                <el-table-column label="币种编码" align="center" prop="currencyCode" />
                <el-table-column label="币种名称" align="center" prop="currencyName" />
                <el-table-column label="国家/地区" align="center" prop="countryRegion" />
                <el-table-column label="财务精度" align="center" prop="financialAccuracy" />
                <el-table-column label="精度" align="center" prop="accuracy" />
                <el-table-column label="货币符号" align="center" prop="currencySymbol" />
              </el-table>
              <pagination v-show="ctotal > 0" :total="ctotal" :page.sync="cqueryParams.pageNum"
                :limit.sync="cqueryParams.pageSize" @pagination="getListCurrency" />
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogCurrency = false">取消</el-button>
              </div>
            </el-dialog>
          </el-form-item>
          <el-form-item label="供应商名称" prop="supplier">
            <el-input v-model="supplyName" placeholder="请输入供应商名称" />
            <i class="el-icon-search" id="serachOne" @click="showDiagSupplie1()"></i>
            <el-dialog :visible.sync="dialogVisible1" title="供应商名称" :modal="false">
              <!-- 这里是供应商的内容 -->
              <el-table v-loading="loading" :data="detailsList" @row-click="handleRowClick1">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="供应商详细编码" align="center" prop="sdCode" />
                <el-table-column label="供应商名称" align="center" prop="sbiName" />
              </el-table>

              <pagination v-show="stotal > 0" :total="stotal" :page.sync="squeryParams.pageNum"
                :limit.sync="squeryParams.pageSize" @pagination="getList1" />
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible1 = false">取消</el-button>
              </div>
            </el-dialog>
          </el-form-item>
          <el-form-item label="联系人" prop="contacts">
            <el-input v-model="form.contacts" placeholder="请输入联系人" />
          </el-form-item>
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入联系电话" />
          </el-form-item>
          <el-form-item label="供应商发票方式" prop="invoiceMethod">
            <el-select v-model="form.invoiceMethod" placeholder="请选择供应商发票方式">
              <el-option v-for="dict in dict.type.invoice_method" :key="dict.value" :label="dict.label"
                :value="parseInt(dict.value)"></el-option>
            </el-select>
          </el-form-item>
          <el-divider content-position="center">订单物料明细信息</el-divider>
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddOrderMaterial">添加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteOrderMaterial">删除</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="primary" @click="showBatchAddDialog">批量维护</el-button>
            </el-col>
          </el-row>
          <el-table :data="orderMaterialList" :row-class-name="rowOrderMaterialIndex"
            @selection-change="handleOrderMaterialSelectionChange" ref="orderMaterial" @row-click="clickRowMaterial">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="序号" align="center" prop="index" width="50" />
            <el-table-column label="物料编码" prop="orCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.orCode" placeholder="" />
                <i class="el-icon-search" id="serachOne1" @click="showMaterial()"></i>
                <el-dialog :visible.sync="dialogMaterial" title="物料管理-浏览框" :modal="false">
                  <!-- 这里是物料管理的内容 -->
                  <el-table v-loading="loading" :data="materialList" @row-click="handleRowClickMaterial">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="物料编码" align="center" prop="materialCode" />
                    <el-table-column label="物料名称" align="center" prop="materialName" />
                  </el-table>
                  <pagination v-show="mtotal > 0" :total="mtotal" :page.sync="mqueryParams.pageNum"
                    :limit.sync="mqueryParams.pageSize" @pagination="getList2" />
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogMaterial = false">取消</el-button>
                  </div>
                </el-dialog>
              </template>
            </el-table-column>
            <el-table-column label="物料名称" prop="orName" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.orName" placeholder="请输入物料名称" />
              </template>
            </el-table-column>
            <el-table-column label="品类编码" prop="categoryCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.categoryCode" placeholder="请输入品类编码" />
                <i class="el-icon-search" id="serachOne1" @click="showMaterial1()"></i>
                <el-dialog :visible.sync="dialogMaterial1" title="品类对象-浏览框" :modal="false">
                  <!-- 这里是品类的内容 -->
                  <el-table :data="categoryList" v-loading="loading" @row-click="handleRowClickMaterial1">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="品类名称" align="center" prop="categoryName" />
                    <el-table-column label="品类代码" align="center" prop="categoryCode" />
                    <el-table-column label="上级品类" align="center" prop="superiorCategory" />
                  </el-table>
                  <pagination v-show="catotal > 0" :total="catotal" :page.sync="caqueryParams.pageNum"
                    :limit.sync="caqueryParams.pageSize" @pagination="getListCategory" />
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogMaterial1 = false">取消</el-button>
                  </div>
                </el-dialog>
              </template>
            </el-table-column>
            <el-table-column label="物料品类" prop="materialCategory" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.materialCategory" placeholder="请输入物料品类" />
              </template>
            </el-table-column>
            <el-table-column label="物料规格" prop="materialSpecification" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.materialSpecification" placeholder="请输入物料规格" />
              </template>
            </el-table-column>
            <el-table-column label="物料型号" prop="materialModel" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.materialModel" placeholder="请输入物料型号" />
              </template>
            </el-table-column>
            <el-table-column label="物料单位" prop="materialUnit" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.materialUnit" placeholder="请输入物料单位" />
              </template>
            </el-table-column>
            <el-table-column label="需求数量" prop="requireNumber" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.requireNumber" placeholder="请输入需求数量" />
              </template>
            </el-table-column>
            <el-table-column label="需求日期" prop="requireTime" width="240">
              <template slot-scope="scope">
                <el-date-picker clearable v-model="scope.row.requireTime" type="date" value-format="yyyy-MM-dd"
                  placeholder="请选择需求日期" />
              </template>
            </el-table-column>
            <el-table-column label="历史最低价" prop="lowerPrice" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.lowerPrice" placeholder="请输入历史最低价" />
              </template>
            </el-table-column>
            <el-table-column label="最新价格" prop="newPrice" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.newPrice" placeholder="请输入最新价格" />
              </template>
            </el-table-column>
            <el-table-column label="不含税单价" prop="noTaxPrice" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.noTaxPrice" placeholder="请输入不含税单价" />
              </template>
            </el-table-column>
            <el-table-column label="税率代码" prop="taxCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.taxCode" placeholder="请输入税率代码" />
                <i class="el-icon-search" id="serachOne1" @click="showMaterial2()"></i>
                <el-dialog :visible.sync="dialogMaterial2" title="税率-浏览框" :modal="false">
                  <!-- 这里是品类的内容 -->
                  <el-table :data="rateList" v-loading="loading" @row-click="handleRowClickMaterial2">
                    <el-table-column type="selection" width="55" align="center" />
                    <!-- <el-table-column label="税种序号" align="center" prop="taxTypeId" /> -->
                    <el-table-column label="税种代码" align="center" prop="taxCode" />
                    <el-table-column label="描述" align="center" prop="describes" />
                    <el-table-column label="税率" align="center" prop="taxRate" :formatter="formatTaxRate" />
                    <el-table-column label="是否启用" align="center" prop="enable"></el-table-column>
                  </el-table>
                  <pagination v-show="rtotal > 0" :total="rtotal" :page.sync="rqueryParams.pageNum"
                    :limit.sync="rqueryParams.pageSize" @pagination="getListRate" />
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogMaterial2 = false">取消</el-button>
                  </div>
                </el-dialog>
              </template>
            </el-table-column>
            <el-table-column label="税率" prop="tax" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.tax" placeholder="请输入税率" />
              </template>
            </el-table-column>
            <el-table-column label="含税单价" prop="taxPrice" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.taxPrice" placeholder="请输入含税单价" />
              </template>
            </el-table-column>
            <el-table-column label="行含税金额" prop="lineTaxAmount" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.lineTaxAmount" placeholder="请输入行含税金额" />
              </template>
            </el-table-column>
            <el-table-column label="收货人" prop="consignee" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.consignee" placeholder="请输入收货人" />
              </template>
            </el-table-column>
            <el-table-column label="收货电话" prop="receivingPhone" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.receivingPhone" placeholder="请输入收货电话" />
              </template>
            </el-table-column>
            <el-table-column label="收货地址" prop="receivingAddress" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.receivingAddress" placeholder="请输入收货地址" />
              </template>
            </el-table-column>
            <el-table-column label="备注" prop="remarks" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.remarks" placeholder="请输入备注" />
              </template>
            </el-table-column>
          </el-table>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
      <!-- 批量新增对话框 -->
      <el-dialog :visible.sync="batchAddDialogVisible" title="批量维护">
        <el-form :model="forms" ref="forms">
          <el-form-item label="需求日期" prop="batchRequireTime">
            <el-date-picker v-model="forms.batchRequireTime" type="date" value-format="yyyy-MM-dd"
              placeholder="请选择需求日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="需求数量" prop="batchRequireNumber">
            <el-input v-model="forms.batchRequireNumber" placeholder="请输入需求数量"></el-input>
          </el-form-item>
          <el-form-item label="收货人" prop="batchConsignee">
            <el-input v-model="forms.batchConsignee" placeholder="请输入收货人"></el-input>
          </el-form-item>
          <el-form-item label="收货地址" prop="batchReceivingAddress">
            <el-input v-model="forms.batchReceivingAddress" placeholder="请输入收货地址"></el-input>
          </el-form-item>
          <el-form-item label="收货电话" prop="batchReceivingPhone">
            <el-input v-model="forms.batchReceivingPhone" placeholder="请输入收货电话"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button @click="cancelBatchAdd">取消</el-button>
          <el-button type="primary" @click="doBatchAdd">确定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--
  订单执行明细
-->
    <div class="order-info" v-show="!isWholeOrder">
      <Mingxi></Mingxi>
    </div>
    <!--
      新建送货单
    -->
    <el-dialog title="新建送货单" :visible.sync="invoiceOrder">
      <!--新建送货单-->


      <el-form ref="invoiceForm" :model="invoiceForm" :rules="rules" label-width="80px">
        <!-- <el-form-item label="订单编号" prop="orderCode">
          <el-input v-model="invoiceForm.orderCode" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="订单状态" prop="orderStatus">
          <el-select v-model="invoiceForm.orderStatus" placeholder="请选择订单状态">
            <el-option v-for="dict in dict.type.om_state" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="总需求量" prop="totalDemand">
          <el-input v-model="invoiceForm.totalDemand" placeholder="请输入总需求量" />
        </el-form-item> -->
        <el-divider content-position="center">订单物料明细信息</el-divider>
        <el-table :data="invoiceOrderMaterialList" :row-class-name="rowOrderMaterialIndex" @row-click="clickRowMaterial1">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料编码" prop="orCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orCode" disabled />
            </template>
          </el-table-column>
          <el-table-column label="物料名称" prop="orName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orName" disabled />
            </template>
          </el-table-column>
          <el-table-column label="品类编码" prop="categoryCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.categoryCode" disabled />
            </template>
          </el-table-column>
          <el-table-column label="物料品类" prop="materialCategory" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialCategory" disabled />
            </template>
          </el-table-column>
          <el-table-column label="物料规格" prop="materialSpecification" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialSpecification" disabled />
            </template>
          </el-table-column>
          <el-table-column label="物料型号" prop="materialModel" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialModel" disabled />
            </template>
          </el-table-column>
          <el-table-column label="物料单位" prop="materialUnit" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialUnit" disabled />
            </template>
          </el-table-column>
          <el-table-column label="需求数量" prop="requireNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.requireNumber" disabled />
            </template>
          </el-table-column>
          <el-table-column label="需求日期" prop="requireTime" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.requireTime" type="date" value-format="yyyy-MM-dd" disabled />
            </template>
          </el-table-column>
          <el-table-column label="历史最低价" prop="lowerPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.lowerPrice" disabled />
            </template>
          </el-table-column>
          <el-table-column label="最新价格" prop="newPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.newPrice" disabled />
            </template>
          </el-table-column>
          <el-table-column label="不含税单价" prop="noTaxPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.noTaxPrice" disabled />
            </template>
          </el-table-column>
          <el-table-column label="税率代码" prop="taxCode" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.taxCode" disabled />
            </template>
          </el-table-column>
          <el-table-column label="税率" prop="tax" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.tax" disabled />
            </template>
          </el-table-column>
          <el-table-column label="含税单价" prop="taxPrice" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.taxPrice" />
            </template>
          </el-table-column>
          <el-table-column label="行含税金额" prop="lineTaxAmount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.lineTaxAmount" />
            </template>
          </el-table-column>
          <el-table-column label="收货人" prop="consignee" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.consignee" />
            </template>
          </el-table-column>
          <el-table-column label="收货电话" prop="receivingPhone" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.receivingPhone" disabled />
            </template>
          </el-table-column>
          <el-table-column label="收货地址" prop="receivingAddress" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.receivingAddress" disabled />
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remarks" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remarks" disabled />
            </template>
          </el-table-column>
        </el-table>
        <el-divider content-position="center">订单物料发货执行</el-divider>

        <el-form-item label="订单编号" prop="orderCode">
          <el-input v-model="invoiceForm.orderCode" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="税后总金额" prop="taxTotal">
          <el-input v-model="invoiceForm.taxTotal" placeholder="请输入税后总金额" />
        </el-form-item>
        <el-form-item label="总需求量" prop="totalDemand">
          <el-input v-model="invoiceForm.totalDemand" placeholder="请输入总需求量" />
        </el-form-item>
        <el-form-item label="发货数量" prop="deliveryQuantity">
          <el-input v-model="invoiceForm.deliveryQuantity" placeholder="请输入发货数量" />
        </el-form-item>
        <el-form-item label="订单行号" prop="orderLineNo">
          <el-input v-model="invoiceForm.orderLineNo" placeholder="请输入订单行号" />
        </el-form-item>
        <el-form-item label="物料编码" prop="materialCode">
          <el-input v-model="invoiceForm.materialCode" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="物料名称" prop="materialName">
          <el-input v-model="invoiceForm.materialName" placeholder="请输入物料名称" />
        </el-form-item>
        <el-form-item label="物料单位" prop="materialUnit">
          <el-input v-model="invoiceForm.materialUnit" placeholder="请输入物料单位" />
        </el-form-item>
        <el-form-item label="需求数量" prop="requiredQuantity">
          <el-input v-model="invoiceForm.requiredQuantity" placeholder="请输入需求数量" />
        </el-form-item>
        <el-form-item label="需求日期" prop="requiredDate">
          <el-date-picker clearable v-model="invoiceForm.requiredDate" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择需求日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发货单号" prop="deliveryNoteNo">
          <el-input v-model="invoiceForm.deliveryNoteNo" placeholder="请输入发货单号" disabled />
        </el-form-item>
        <el-form-item label="发货单行号" prop="deliveryNoteLineNo">
          <el-input v-model="invoiceForm.deliveryNoteLineNo" placeholder="请输入发货单行号" disabled />
        </el-form-item>
        <!-- <el-form-item label="已发货数量" prop="deliveredQuantity">
            <el-input v-model="invoiceForm.deliveredQuantity" placeholder="请输入已发货数量" />
          </el-form-item> -->
        <el-form-item label="供应商" prop="supplier">
          <el-input v-model="invoiceForm.supplier" placeholder="请输入供应商" />
        </el-form-item>
        <el-form-item label="交货日期" prop="deliveryDate">
          <el-date-picker clearable v-model="invoiceForm.deliveryDate" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择交货日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="invoicesubmitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listManager, getManager, delManager, addManager, updateManager, listSupplier, listMaterial, listOrderMaterial, listCurrency, listCategory, listRate, listTypeRun, managerList, getNumber, updateManagerState } from "@/api/pms/manager";
import { listMaterials, findTaskMaterial, findByOrderCodeMaterial, deleteMaterial, addMaterials } from "@/api/pms/materials";
import { listTask } from "@/api/procure/task";
import { addOrderDetails, updateOrderDetails } from "@/api/pms/orderDetail";
import Mingxi from '../../components/icons/Pms/index.vue'
export default {
  name: "Manager",
  components: {
    Mingxi
  },
  dicts: ['self_pickup', 'order_state', 'order_type', 'order_source', 'procure', 'supplier_invoice', 'invoice_method', 'om_state'],
  data() {
    return {
      invoiceOrderMaterialList: [],//显示新建送货单的物料基本信息
      invoiceOrder: false,//显示新建送货单的对话框
      isWholeOrder: true, // 默认显示整单信息
      // 查询参数
      rwqueryParams: {
        pageNum: 1,
        pageSize: 10,
        taskCode: null,
      },
      rwtotal: 0,
      headerImages: require('../../../assets/images/order_main_header1.png'),
      // 遮罩层
      loading: true,
      taskList: [],
      //用于展示执行状态的个数
      runNumber: [],
      // 选中数组
      ids: [],
      //供应商名称
      supplyName: '',
      //控制是否启用计算属性计算需求总数量和含税金额
      isCalculationNeeded: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //订单物料明细对比数据
      listOrderMaterial: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //选中税率数组
      rateId: [],
      rtotal: 0,
      // 币种表格数据
      currencyList: [],
      // 币种定义查询参数
      cqueryParams: {
        pageNum: 1,
        pageSize: 10,
        currencyCode: null,
        currencyName: null,
      },
      // 查询参数
      ormqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      materialqueryParams: {
        pageNum: 1,
        pageSize: 10,
        orName: null,
        supply: null,
        orderSource: null,
        orderCode: null
      },
      // 币种定义总条数
      ctotal: 0,
      // 采购订单管理表格数据
      managerList: [],
      // 供应商列表表格数据
      detailsList: [],
      // 订单物料明细表格数据
      orderMaterialList: [],
      // 物料基本信息
      materialList: [],
      //订单物料基本信息表明细
      materiaslList: [],
      //供应商总条数
      stotal: 0,
      //物料基本信息总条数
      mtotal: 0,
      //品类对象列表
      categoryList: [],
      // 品类对象查询参数
      caqueryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryCode: null,
        categoryName: null,
        superiorCategory: null,
      },
      //品类对象总数
      catotal: 0,
      //供应商查询参数
      squeryParams: {
        pageNum: 1,
        pageSize: 10,
        sdCode: null,
        sbiName: null,
        phone: '',
        people: '',
      },
      //物料基本信息查询参数
      mqueryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
      },
      // 税率表格数据
      rateList: [],
      // 查询参数
      rqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      //添加物料基本信息
      materialInfo: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 币种定义名称
      currencyName: '',
      //执行状态列表
      typeRunList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderCode: null,
        orderSource: null,
        orderState: null,
        supplier: null,
        //根据执行状态Id查询
        orId: null,
      },

      forms: {
        batchRequireTime: '',
        batchRequireNumber: '',
        batchConsignee: '',
        batchReceivingAddress: '',
        batchReceivingPhone: ''
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
      },
      dialogVisible: false, // 用于标记供应商弹窗是否可见
      dialogVisible1: false, // 用于标记供应商弹窗是否可见
      dialogMaterial: false, //用于标记物料信息表是否可见
      dialogCurrency: false, //用于标记物料信息表是否可见
      dialogMaterial1: false,
      dialogMaterial2: false,
      // 批量新增对话框的可见性
      batchAddDialogVisible: false,
      // 批量新增数据列表
      batchAddDataList: [],
      checkedOrderMaterials: null,
      //需求总数修改
      number: 0,
      //含税总金额
      taxTotal: 0,
      //执行状态查询
      formData: {},
      selectedRoute: null,
      ormtotal: 0,
      drawer: false,
      direction: 'rtl',
      checkedOrderMaterials: [],
      //关闭订单的集合或对象
      orderCancel: [],
      //获取到订单明细表格下标
      materialIndex: 0,
      //获取新建送货单的订单信息
      invoiceList: [],
      //新建送货单参数
      invoiceForm: {

      },

      materialIndex1: 0,

    };
  },
  watch: {
    'orderMaterialList': {
      deep: true,
      handler(newVal, oldVal) {
        newVal.forEach((item) => {
          this.calculateLineTaxAmount(item);
          this.calculateTaxPrice(item);
        });
      }
    }
  },
  created() {
    this.getListMaterial();
    this.getlistNumber();
    this.getList();
    this.getlistOrderMaterials();
    this.getlistTypeRun();
    this.getList5();
  },
  computed: {
    //计算总需求量
    totalDemands() {
      let sum = 0;
      this.orderMaterialList.forEach(item => {
        if (item.requireNumber !== '') {
          sum += parseFloat(item.requireNumber);
        }
      });
      const total = isNaN(sum) ? 0.00 : sum.toFixed(2);
      this.form.totalDemand = total; // 将计算得到的总价赋值给 form.totalDemand
      return total;
    },
    //计算税后总金额
    calculateTotalAmount() {
      let sum = 0;
      for (let i = 0; i < this.orderMaterialList.length; i++) {
        const item = this.orderMaterialList[i];
        if (item.tax !== '' && item.tax !== 0) {
          const basePrice = parseFloat(item.newPrice);
          const taxRate = parseFloat(item.tax);
          if (taxRate !== 0) { // 如果税率不为0，则继续计算
            const taxIncludedPrice = basePrice * (1 + taxRate);
            const requireNumber = parseFloat(item.requireNumber);
            const afterTaxAmount = taxIncludedPrice * requireNumber;
            sum += afterTaxAmount;
          } else {
            const requireNumber = parseFloat(item.requireNumber);
            const basePrice = parseFloat(item.noTaxPrice);
            const afterTaxAmount = basePrice * requireNumber;
            sum += afterTaxAmount;
          }
        }
      }
      const totalAfterTax = isNaN(sum) ? 0.00 : sum.toFixed(2);
      this.form.taxTotal = totalAfterTax; // 将总税后金额赋值给 this.form.taxTotal
      return totalAfterTax;
    },
    isSelfPickupSelected() {
      return this.form.isSelfPickup === 1; // 根据选择的值判断是否自提被选中
    }
  },
  methods: {
    //新建送货单提交按钮
    invoicesubmitForm() {
      this.invoiceForm.orderMaterials = this.invoiceOrderMaterialList
      updateOrderDetails(this.invoiceForm).then(response => {
        this.$message.success(response.msg)
        this.invoiceOrder = false;
      })
    },
    redirectToOtherPage(orderCode) {
      // 根据你的路由规则跳转到相应的页面
      // 示例：this.$router.push('/otherpage')
      this.$router.push({
        name: 'orderInfo',
        params: {
          orderCode: orderCode
        }
      })
    },
    //新建送货单
    invoice() {
      if (this.invoiceList.length === 1) {
        //审批状态必须为通过,不然不让新建送货单
        if (this.invoiceList.some(element => element.orderState === 3)) {
          this.invoiceOrder = true
          //this.invoiceForm = this.invoiceList[0]
          this.invoiceForm.supplier = this.invoiceList[0].supplierDetails.sbiName
          this.invoiceForm.totalDemand = this.invoiceList[0].totalDemand.toFixed(2)
          this.invoiceForm.taxTotal = this.invoiceList[0].taxTotal.toFixed(2)
          this.invoiceForm.orderCode = this.invoiceList[0].orderCode
          console.log("这是invoiceList的内容:" + this.invoiceList[0].supplierDetails.sbiName)
          findByOrderCodeMaterial(this.invoiceList[0].orderCode).then(response => {
            this.invoiceOrderMaterialList = response.data;
          })
        } else {
          this.$notify({
          title: '警告',
          message: '审批状态为通过',
          type: 'warning'
        });
        }
      } else {
        this.$notify({
          title: '警告',
          message: '请选择一条数据,暂不支持合单',
          type: 'warning'
        });
      }
    },
    //关闭订单
    orderCancelbutton() {
      if (this.orderCancel.length === 1) {
        if (this.orderCancel.some(element => element.orderState === 3)) {
          // 如果数组中有元素的'orderState'等于3，则执行下一步操作
          //nextStep();
          this.orderCancel.forEach(element => {
            element.orId = 6
          })
          updateManagerState(this.orderCancel[0])
          this.getList();
        } else {
          // 如果数组中没有元素的'orderState'等于3，则提示错误
          // alert("订单状态不正确，无法执行该操作。");
          this.$notify({
            title: '警告',
            message: '订单状态不正确，无法执行该操作。',
            type: 'warning'
          });
        }
      } else {
        this.$notify({
          title: '警告',
          message: '请选择一条数据',
          type: 'warning'
        });
      }
    },
    /**
     * 计算行含税金额
     * @param {} row
     */
    calculateLineTaxAmount(row) {
      if (row.noTaxPrice === 0 || row.requireNumber === 0 || row.tax === 0) {
        row.lineTaxAmount = 0;
      } else {
        row.lineTaxAmount = (row.noTaxPrice * row.requireNumber * (1 + row.tax * 0.01)).toFixed(2);
      }
    },
    /**
     * 计算含税单价
     * @param {*} row
     */
    calculateTaxPrice(row) {
      if (row.noTaxPrice === 0 || row.tax === 0) {
        row.taxPrice = 0;
      } else {
        row.taxPrice = (row.noTaxPrice * (1 + row.tax * 0.01)).toFixed(2);
      }
    },
    /** 查询我的需求任务列表 */
    getList5() {
      this.loading = true;
      listTask(this.rwqueryParams).then(response => {
        this.taskList = response.rows;
        this.rwtotal = response.total;
        this.loading = false;
      });
    },
    calculateTotal(prop) {
      const total = this.materiaslList.reduce((sum, item) => {
        const value = Number(item[prop]);
        if (!isNaN(value)) {
          return sum + value;
        } else {
          return sum;
        }
      }, 0);

      return total.toFixed(2); // 保留两位小数
    },
    getListMaterial() {
      this.loading = true;
      listMaterials(this.materialqueryParams).then(response => {
        this.materiaslList = response.rows;
        this.ormtotal = response.total;
        this.loading = false;
      });
    },
    showWholeOrder() {
      this.isWholeOrder = true;
    },
    showOrderDetail() {
      this.isWholeOrder = false;
    },
    //控制订单执行状态的颜色
    getFormattedOrderTypeRunning(row) {
      if (row.orderTypeRunning !== null) {
        // 访问 row.orderTypeRunning 的属性并执行相应逻辑
        const statusClass = this.getOrderTypeRunningClass(row.orderTypeRunning.ortName);
        return `<span class="${statusClass}">${row.orderTypeRunning.ortName}</span>`;
      }
    },
    //控制订单审批状态的颜色
    getStatusClass(orderState) {
      if (orderState === 1) {
        return 'status-pending'; // 新建样式类
      } else if (orderState === 2) {
        return 'status-canceled'; // 审批中样式类
      } else if (orderState === 3) {
        return 'status-approved'; // 审批通过样式类
      } else if (orderState === 4) {
        return 'status-rejected'; // 审批拒绝样式类
      }
    },
    getOrderTypeRunningClass(orderTypeRunning) {
      // 根据 orderTypeRunning 的值返回相应的样式类
      // 示例中仅给出了一个样式类的判断条件
      if (orderTypeRunning === '订单已确认') {
        return 'status-type1'; // 类型1样式类
      } else if (orderTypeRunning === '订单待确认') {
        return 'status-type2'; // 其他情况无样式类
      } else if (orderTypeRunning === '订单已关闭') {
        return 'status-type3'; // 其他情况无样式类
      } else if (orderTypeRunning === '执行中') {
        return 'status-type4'; // 其他情况无样式类
      } else if (orderTypeRunning === '订单已完成') {
        return 'status-type5'; // 其他情况无样式类
      } else if (orderTypeRunning === '订单已拒绝') {
        return 'status-type6'; // 其他情况无样式类
      } else if (orderTypeRunning === '订单审批') {
        return 'status-type7'; // 其他情况无样式类
      }
    },
    selectRoute(route) {
      if (this.selectedRoute === route) {
        // 如果点击的是已选中的路线，则取消选中状态
        // this.selectedRoute = null;
        this.queryParams.orId = route
        this.getList();
      } else {
        // 否则更新选中状态为当前路线
        this.selectedRoute = route;
      }
    },
    /**
     * 计算各个执行状态的总数
     * @param {} ortId
     */
    getTotalCount(ortId) {
      if (ortId === null) {
        // 计算“全部”选项的总数
        return this.runNumber.length;
      } else {
        // 计算其他选项的总数
        const count = this.runNumber.filter(item => item.orderState === ortId).length;
        return count;
      }
    },
    /**
     * 需求转订单选择对应的需求订单号转成采购订货单
     */
    SelectionChange(selection) {
      if (selection.length === 0) {
        // 没有选择任何任务
        this.$notify({
          title: '警告',
          message: '请至少选择一项任务',
          type: 'warning'
        });
      } else {
        for (let i = 0; i < selection.length; i++) {
          const selectedTask = selection[i];
          if (selectedTask.taskAccepted === 0.00 || selectedTask.taskAccepted === null || selectedTask.taskAccepted === '') {
            // 说明该条需求订单已经没有可改为采购申请单的必要了
            // 给出提示，重新选择
            this.$notify({
              title: '警告',
              message: '存在代理数量为0的任务，请重新选择',
              type: 'warning'
            });
          } else {
            // 执行转换为采购申请单的操作
            this.transferToOrder(selectedTask);
          }
        }
      }
    },
    /**
     * 需求转订单里面的确定按钮
     * @param {} row
     */
    transferToOrder(row) {
      this.open = true;
      this.title = "需求订单转采购订单";
      console.log("点击确定得到的数据:", row);
      // 这里可以执行转换为采购申请单的操作
      console.log("这是返回的orderMaterialList:" + findTaskMaterial(row.taskCode).data)
      this.getList6(row.taskCode)
      this.orderMaterialList = findTaskMaterial(row.taskCode).data
      //首先查询根据任务订单号查询该任务订单号下面的物料明细
      //查询之后复制给this.orderMaterialList以便展示数据
      //给几个字段加上默认值 状态  订单类型 订单审批状态
      this.form.orderSource = "3"
      this.form.orderState = 1
      this.form.orderType = 4
    },
    /** 物料明细复选框选中数据 */
    handleOrderMaterialSelectionChange(selection) {
      let checkedOrderMaterial = selection.map(item => item)
      this.checkedOrderMaterials = checkedOrderMaterial
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

    // 执行批量新增
    doBatchAdd() {
      if (this.checkedOrderMaterials.length === 0) {
        this.$message.warning('请至少选择一条记录');
        return;
      }
      // 对选中的行数据进行修改
      this.checkedOrderMaterials.forEach(row => {
        row.requireTime = this.forms.batchRequireTime;
        row.requireNumber = this.forms.batchRequireNumber;
        row.consignee = this.forms.batchConsignee;
        row.receivingAddress = this.forms.batchReceivingAddress;
        row.receivingPhone = this.forms.batchReceivingPhone;
      });
      // 清空批量修改的值
      this.batchRequireTime = '';
      this.batchRequireNumber = '';
      this.batchConsignee = '';
      this.batchReceivingAddress = '';
      this.batchReceivingPhone = '';
      //this.$refs.orderMaterial.clearSelection(); // 清空选中的行数据
      this.$message.success('批量修改成功');
      this.batchAddDialogVisible = false;
    },
    formatTaxRate(row, column) {
      const value = row[column.property];
      if (value === null || value === undefined) {
        return ''; // 处理空值情况
      }
      return parseFloat(value).toFixed(2); // 使用 toFixed 方法保留两位小数
    },
    /** 查询采购订单管理列表 */
    getList() {
      this.loading = true;
      listManager(this.queryParams).then(response => {
        this.managerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询采购订单管理列表 */
    getlistTypeRun() {
      this.loading = true;
      listTypeRun().then(response => {
        this.typeRunList = response;
        this.loading = false;
      });
    },
    /** 查询采购订单管理列表 */
    getlistNumber() {
      this.loading = true;
      getNumber().then(response => {
        this.runNumber = response;
        this.loading = false;
      });
    },
    /** 查询品类对象列表 */
    getListCategory() {
      this.loading = true;
      listCategory(this.caqueryParams).then(response => {
        this.categoryList = response.rows;
        this.catotal = response.total;
        this.loading = false;
      })
    },
    /** 查询税率对象列表 */
    getListRate() {
      this.loading = true;
      listRate(this.rqueryParams).then(response => {
        this.rateList = response.rows;
        this.rtotal = response.total;
        this.loading = false;
      })
    },
    /** 查询币种定义列表 */
    getListCurrency() {
      this.loading = true;
      listCurrency(this.cqueryParams).then(response => {
        // 过滤出状态为1的元素并重新赋值给currencyList
        this.currencyList = response.rows;
        this.ctotal = response.total;
        this.loading = false;
      });
    },
    /**
     * 订单物料明细查询
     */
    getlistOrderMaterials() {
      this.loading = true;
      listOrderMaterial().then(response => {
        this.listOrderMaterial = response.rows;
        this.loading = false;
      });
    },
    /**
     * 查询orName物料名称
     * @param {*} orderMaterialList
     */
    getFormattedMaterialName(row) {
      if (!row.orderCode || typeof row.orderCode !== 'string') {
        return '';
      }
      const names = [];
      for (let i = 0; i < this.listOrderMaterial.length; i++) {
        const innerElement = this.listOrderMaterial[i];
        if (innerElement.orderCode === row.orderCode) {
          names.push(innerElement.orName)
        }
      }
      return names.join(' ');
    },
    /**
     * 供应商列表
     */
    getList1() {
      this.loading = true;
      listSupplier(this.squeryParams).then(response => {
        this.detailsList = response.rows;
        this.stotal = response.total;
        this.loading = false;
      });
    },
    /**
     * 物料信息列表
     */
    getList2() {
      this.loading = true;
      listMaterial(this.mqueryParams).then(response => {
        this.materialList = response.rows;
        this.mtotal = response.total;
        this.loading = false;
      });
    },
    /**
     * 物料信息列表
     */
    getList6(taskCode) {
      this.loading = true;
      findTaskMaterial(taskCode).then(response => {
        this.orderMaterialList = response.data;
        this.loading = false;
      });
    },
    showDiagSupplie() {
      this.dialogVisible = true;
      this.getList1();
    },
    showDiagSupplie1() {
      this.dialogVisible1 = true;
      this.getList1();
    },
    showDiagCurrency() {
      this.dialogCurrency = true;
      this.getListCurrency();
    },
    showMaterial() {
      this.dialogMaterial = true;
      this.getList2();
    },
    showMaterial1() {
      this.dialogMaterial1 = true;
      this.getListCategory();
    },
    showMaterial2() {
      this.dialogMaterial2 = true;
      this.getListRate();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.invoiceOrder = false;
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        orderCode: null,
        company: null,
        orderType: null,
        orderSource: null,
        purOrganization: null,
        annex: null,
        isSelfPickup: null,
        licensePlateNumber: null,
        concatInfomation: null,
        contractCode: null,
        contractName: null,
        orderDescription: null,
        totalDemand: null,
        purchaser: null,
        orderState: null,
        orderStatus: null,
        currencyId: null,
        supplier: null,
        contacts: null,
        phone: null,
        invoiceMethod: null,
        createTime: null,
        materialId: null
      };
      this.orderMaterialList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 币种定义搜索按钮操作 */
    handleQuery2() {
      this.cqueryParams.pageNum = 1;
      this.getListCurrency();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 币种定义重置按钮操作 */
    resetQuery() {
      this.resetForm("cqueryForm");
      // this.handleQuery2();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      //关闭按钮赋值
      this.orderCancel = selection
      //为新建送货单赋值
      this.invoiceList = selection
      console.log(this.invoiceList)
      // console.log("这是orderCancel:" + this.orderCancel)
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 订单物料明细多选框选中数据
    handleSelectionChange1(selection) {
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleSelectionChangeCurrency(row) {
      this.form.currencyId = row.currencyId;
      for (let i = 0; i < this.currencyList.length; i++) {
        const innerElement = this.currencyList[i];
        if (innerElement.currencyId === row.currencyId) {
          this.currencyName = innerElement.currencyName
        }
      }
      this.dialogCurrency = false; // 关闭对话框
    },
    handleRowClick1(row) {
      this.form.supplier = row.sdId; // 将供应商名称填充到输入框中
      for (let i = 0; i < this.detailsList.length; i++) {
        const innerElement = this.detailsList[i];
        if (innerElement.sdId === row.sdId) {
          this.supplyName = innerElement.sbiName
          this.form.phone = innerElement.sdPcp
          this.form.contacts = innerElement.sdPcn
        }
      }
      this.dialogVisible1 = false; // 关闭对话框
    },
    //点击整单供应商列表列数据
    handleRowClick(row) {
      this.squeryParams.sbiName = row.sbiName; // 将供应商名称填充到输入框中
      this.dialogVisible = false; // 关闭对话框
    },
    //点击订单明细供应商列表列数据
    handleRowClick2(row) {
      this.materialqueryParams.sbiName = row.sbiName; // 将供应商名称填充到输入框中
      this.dialogVisible = false; // 关闭对话框
    },
    clickRowMaterial(row, event, column) {
      this.materialIndex = this.orderMaterialList.indexOf(row); // 获取点击的行索引
    },
    clickRowMaterial1(row, event, column) {
      this.materialIndex1 = this.invoiceOrderMaterialList.indexOf(row); // 获取点击的行索引
      console.log(row)
      //点击哪一行赋值给invoiceForm
      this.invoiceForm.materialCode = row.orCode
      this.invoiceForm.materialName = row.orName
      this.invoiceForm.materialUnit = row.materialUnit
      this.invoiceForm.requiredQuantity = row.requireNumber
      this.invoiceForm.requiredDate = row.requireTime
      this.invoiceForm.orderLineNo = row.orderCode + "-" + row.lineNumber
      this.$refs.invoiceForm.validateField('materialCode');
      this.$refs.invoiceForm.validateField('materialName');
      this.$refs.invoiceForm.validateField('materialUnit');
      this.$refs.invoiceForm.validateField('requiredQuantity');
      this.$refs.invoiceForm.validateField('requiredDate');
      this.$refs.invoiceForm.validateField('orderLineNo');
      this.$forceUpdate();
      console.log(this.invoiceForm.requiredQuantity)
      console.log("这是invoiceForm:" + this.invoiceForm.requiredDate);
    },
    /**
     * 为物料编码等赋值
     * @param {*} row
     */
    handleRowClickMaterial(row) {
      // 修改某一条数据的属性值
      this.orderMaterialList[this.materialIndex].orCode = row.materialCode;
      this.orderMaterialList[this.materialIndex].orName = row.materialName;
      this.orderMaterialList[this.materialIndex].categoryCode = row.categoryName;
      this.orderMaterialList[this.materialIndex].materialCategory = row.categoryName;
      this.orderMaterialList[this.materialIndex].materialSpecification = row.specifications;
      this.orderMaterialList[this.materialIndex].materialModel = row.model;
      this.orderMaterialList[this.materialIndex].materialUnit = row.metering_unit;
      this.dialogMaterial = false; // 关闭对话框
    },
    handleRowClickMaterial1(row) {
      // 修改第某一条数据的属性值
      this.orderMaterialList[this.materialIndex].categoryCode = row.categoryName;
      this.orderMaterialList[this.materialIndex].materialCategory = row.categoryName;
      this.dialogMaterial1 = false; // 关闭对话框
    },
    handleRowClickMaterial2(row) {
      // 修改某一条数据的属性值
      this.orderMaterialList[this.materialIndex].taxCode = row.taxCode;
      this.orderMaterialList[this.materialIndex].tax = row.taxRate;
      this.dialogMaterial2 = false; // 关闭对话框
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.number = 0.00
      this.taxTotal = 0.00
      this.open = true;
      this.title = "添加采购订单管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      findByOrderCodeMaterial(row.orderCode).then(response => {
        this.orderMaterialList = response.data;
      })
      const orderId = row.orderId || this.ids
      //修改订单管理表的数据
      getManager(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购订单管理";
        this.isCalculationNeeded = false
        this.number = response.data.totalDemand
        this.taxTotal = response.data.taxTotal
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.orderMaterialList = this.orderMaterialList;
          if (this.form.orderId != null) {
            updateManager(this.form).then(response => {
              //this.$modal.msgSuccess("修改成功");
              this.open = false;
              //修改物料基础表的数据
              //先删除原有数据后插入
              deleteMaterial(this.form.orderCode);
              addMaterials(this.form).then(response => {
                this.$notify({
                  title: '成功',
                  message: '操作成功完成！',
                  type: 'success'
                });
              })
              this.getList();
              this.getList5();
            });
          } else {
            addManager(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getlistNumber();
              // 导航到 SubmitResult 组件，并传递提交的内容作为参数
              this.$router.push({
                name: 'SubmitResult',
                params: {
                  formData: response.data, // 传递提交的数据
                }
              });
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除采购订单管理编号为"' + orderIds + '"的数据项？').then(function () {
        return delManager(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 订单物料明细序号 */
    rowOrderMaterialIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
      row.lineNumber = row.index
    },
    /** 订单物料明细添加按钮操作 */
    handleAddOrderMaterial() {
      let obj = {};
      obj.lineNumber = "",
        obj.orCode = "";
      obj.orName = "";
      obj.categoryCode = "";
      obj.materialCategory = "";
      obj.materialSpecification = "";
      obj.materialModel = "";
      obj.materialUnit = "";
      obj.requireNumber = "";
      obj.requireTime = "";
      obj.lowerPrice = "";
      obj.newPrice = "";
      obj.noTaxPrice = "";
      obj.taxCode = "";
      obj.tax = "";
      obj.taxPrice = "";
      obj.lineTaxAmount = "";
      obj.consignee = "";
      obj.receivingPhone = "";
      obj.receivingAddress = "";
      obj.remarks = "";
      this.orderMaterialList.push(obj);
    },
    /** 订单物料明细删除按钮操作 */
    handleDeleteOrderMaterial() {
      if (this.checkedOrderMaterials.length == 0) {
        console.log(this.checkedOrderMaterials)
        this.$modal.msgError("请先选择要删除的订单物料明细数据");
      } else {
        const orderMaterialList = this.orderMaterialList;
        const checkedOrderMaterial = this.checkedOrderMaterials;
        this.orderMaterialList = orderMaterialList.filter(function (item) {
          return checkedOrderMaterial.indexOf(item.index) == -1
        });
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pms/manager/export', {
        ...this.queryParams
      }, `manager_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
.order-info {
  margin-top: 20px;
}

.underline {
  width: 100%;
  height: 1px;
  background-color: #ccc;
  margin-top: 10px;
  display: none;
}

.active+.underline {
  display: block;
}

span {
  cursor: pointer;
}

span.active {
  font-weight: bold;
}

#serachOne {
  position: absolute;
  right: 10px;
  top: 10px;
}

#serachOne1 {
  position: absolute;
  right: 15px;
  top: 21.5px;
}

.el-drawer.rtl {
  width: 85% !important;
  /* 修改宽度为85% */
}

.el-form-item__label {
  padding-right: 0;
}


.el-form-item {
  margin-bottom: 10px;
}

.selected {
  position: relative;
}

.selected::after {
  content: "";
  position: absolute;
  left: 0;
  right: 0;
  bottom: -2px;
  /* 控制下划线距离文字的位置 */
  border-bottom: 1px solid #000;
  /* 下划线样式 */
}

.status-pending {
  display: inline-block;
  width: 85px;
  border: 1px solid #ff9800;
  background-color: #ffe0b2;
  border-radius: 5px;
  color: #ff9800;
  /* 橙色字体 */
}

.status-canceled {
  display: inline-block;
  width: 85px;
  border: 1px solid #9e9e9e;
  background-color: #eeeeee;
  border-radius: 5px;
  color: #9e9e9e;
  /* 灰色字体 */
}

.status-approved {
  display: inline-block;
  width: 85px;
  border: 1px solid #4caf50;
  background-color: #c8e6c9;
  border-radius: 5px;
  color: #4caf50;
  /* 绿色字体 */
}

.status-rejected {
  border: 1px solid #f44336;
  background-color: #ffcdd2;
  display: inline-block;
  width: 85px;
  border-radius: 5px;
  color: #f44336;
  /* 红色字体 */
}

.status-type1 {
  display: inline-block;
  width: 85px;
  border-radius: 5px;
  border: 1px solid #2196f3;
  background-color: #e3f2fd;
  color: #2196f3;
  /* 蓝色字体 */
}

.status-type2 {
  display: inline-block;
  width: 85px;
  border-radius: 5px;
  border: 1px solid #4caf50;
  background-color: #e8f5e9;
  color: #4caf50;
  /* 绿色字体 */
}

.status-type3 {
  display: inline-block;
  width: 85px;
  border-radius: 5px;
  border: 1px solid #f44336;
  background-color: #ffebee;
  color: #f44336;
  /* 红色字体 */
}

.status-type4 {
  display: inline-block;
  width: 85px;
  border-radius: 5px;
  border: 1px solid #ff9800;
  background-color: #fff3e0;
  color: #ff9800;
  /* 橙色字体 */
}

.status-type5 {
  display: inline-block;
  width: 85px;
  border-radius: 5px;
  border: 1px solid #9c27b0;
  background-color: #f3e5f5;
  color: #9c27b0;
  /* 紫色字体 */
}

.status-type6 {
  width: 85px;
  display: inline-block;
  border-radius: 5px;
  border: 1px solid #673ab7;
  background-color: #ede7f6;
  color: #673ab7;
  /* 深紫色字体 */
}

.status-type7 {
  width: 85px;
  display: inline-block;
  border: 1px solid #ff5722;
  background-color: #ffccbc;
  border-radius: 5px;
  color: #ff5722;
  /* 橙红色字体 */
}
</style>
