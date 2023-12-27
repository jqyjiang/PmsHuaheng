<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="合同编号：" prop="contractCode">
        <el-input
          v-model="queryParams.contractCode"
          placeholder="请输入合同编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="签订日期：" prop="signingDate">
        <el-date-picker clearable
                        v-model="queryParams.signingDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择签订日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="合同状态：" prop="contractstatusId">
        <el-select v-model="queryParams.contractstatusId" placeholder="请选择合同状态" clearable>
          <el-option
            v-for="dict in dict.type.contract_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商：" prop="sdId">
        <el-input
          v-model="supplierListqueryParams.sbiName"
          placeholder=""
          clearable
          @keyup.enter.native="handleQuery"
        />
        <i class="el-icon-search" id="serachOne3" @click="showDialogSupplierList()"></i>
        <el-dialog :visible.sync="dialogSupplierList" title="供应商-浏览框">
          <!-- 这里是供应商的内容 -->
          <el-table v-loading="loading" :data="supplierList" @row-click="handleRowClickSupplierList">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="供应商编码" align="center" prop="sdCode"/>
            <el-table-column label="供应商名称" align="center" prop="sbiName"/>
          </el-table>
          <pagination v-show="supplierListTotal > 0" :total="supplierListTotal"
                      :page.sync="supplierListqueryParams.pageNum"
                      :limit.sync="supplierListqueryParams.pageSize" @pagination="getListSupplierList"/>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogSupplierList = false">取消</el-button>
          </div>
        </el-dialog>
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
          size="mini"
          @click="handleAdd"
          v-hasPermi="['procure:management:add']"
        >发起采购合同
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['procure:management:edit']"
        >生成采购订单
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['procure:management:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['procure:management:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="相关项目" align="center" prop="relatedProjects"/>
      <el-table-column label="合同名称" align="center" prop="contractName"/>
      <el-table-column label="合同编号" align="center" prop="contractCode"/>
      <el-table-column label="供应商" align="center" prop="supplierDetails.sbiName"/>
      <el-table-column label="负责人" align="center" prop="head"/>
      <el-table-column label="签订日期" align="center" prop="signingDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="付款比例(%)" align="center" prop="paymentProportion">
        <template slot-scope="scope">
          <el-progress :percentage="scope.row.paymentProportion" :stroke-width="20" text-inside/>
        </template>
      </el-table-column>
      <el-table-column label="合同状态" align="center" prop="contractstatusId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contract_status" :value="scope.row.contractstatusId"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleSelectAll(scope.row.contractManagementId)"
            v-hasPermi="['procure:management:queryInfo']"
          >查看详情
          </el-button>
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

    <!-- 生成采购合同对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="申请人" prop="applicant">
          <el-input v-model="form.applicant" placeholder="请输入申请人" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="申请日期" prop="applicationDate" style="float: right;margin-left: 540px;margin-top: -58px">
          <el-date-picker clearable
                          v-model="form.applicationDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择申请日期"
                          readonly style="width: 300px">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请部门" prop="department" >
          <el-input v-model="form.department" placeholder="请输入申请部门" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="申请公司" prop="companies">
          <el-input v-model="form.companies" placeholder="请输入申请公司" style="width: 300px"/>
        </el-form-item>

        <el-form-item label="是否主子合同" prop="isMainContract">
          <el-radio-group v-model="form.isMainContract">
            <el-radio
              v-for="dict in dict.type.is_main_contract"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="预算相关合同" prop="budgetRelatedContracts"
                      style="float: right;margin-left: 540px;margin-top: -116px">
          <el-select v-model="form.budgetRelatedContracts" placeholder="请选择预算相关合同" style="width: 300px">
            <el-option
              v-for="dict in dict.type.budget_related_contracts"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="合同名称" prop="contractName">
          <el-input v-model="form.contractName" placeholder="请输入合同名称" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="项目相关合同" prop="projectRelatedContracts"
                      style="float: right;margin-left: 540px;margin-top: -232px">
          <el-select v-model="form.projectRelatedContracts" placeholder="请选择项目相关合同" style="width: 300px">
            <el-option
              v-for="dict in dict.type.project_related_contracts"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="合同状态" prop="contractstatusId" style="float: right;margin-left: 540px;margin-top: -116px">
          <el-select v-model="form.contractstatusId" placeholder="请选择合同状态" style="width: 300px">
            <el-option
              v-for="dict in dict.type.contract_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="密集" prop="dense" style="float: right;margin-left: 540px;margin-top: -58px">
          <el-select v-model="form.dense" placeholder="请选择密集" style="width: 300px">
            <el-option
              v-for="dict in dict.type.dense"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime">
          <el-date-picker clearable
                          v-model="form.beginTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择开始时间" style="width: 300px">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime" style="float: right;margin-left: 540px;margin-top: -58px">
          <el-date-picker clearable
                          v-model="form.endTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择结束时间" style="width: 300px">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="合同金额" prop="contractPrice">
          <el-input v-model="form.contractPrice" placeholder="请输入合同金额" style="width: 300px" readonly/>
        </el-form-item>
        <el-form-item label="大写" prop="capitalization" style="float: right;margin-left: 540px;margin-top: -58px">
          <el-input v-model="form.capitalization" readonly style="border: none;width: 300px"/>
        </el-form-item>
        <el-form-item label="印花税金额" prop="stampDutyAmount">
          <el-input v-model="form.stampDutyAmount" placeholder="请输入印花税金额" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="负责人" prop="head" style="float: right;margin-left: 540px;margin-top: -58px">
          <el-input v-model="form.head" placeholder="请输入负责人" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="合同情况说明" prop="contractDescription">
          <el-input v-model="form.contractDescription" placeholder="请输入合同情况说明" />
        </el-form-item>
        <el-form-item label="采购清单" prop="purchasingList">
          <el-input v-model="orderCode" placeholder="" readonly/>
          <i class="el-icon-search" id="serachOne2" @click="showManager()"></i>
          <el-dialog :visible.sync="dialogManager" title="ERP采购清单" :modal="false">
            <el-table v-loading="loading" :data="managerList" @row-click="handleSelectionChangeManager">
              <el-table-column type="selection" width="55" align="center"/>
              <el-table-column label="订单编号" align="center" prop="orderCode"/>
              <el-table-column label="联系人" align="center" prop="contacts"/>
              <el-table-column label="下单日期" align="center" prop="createTime"/>
            </el-table>
            <pagination v-show="managerTotal > 0" :total="managerTotal" :page.sync="managerqueryParams.pageNum"
                        :limit.sync="managerqueryParams.pageSize" @pagination="getListManager"/>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogManager = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
      </el-form>

      <el-divider content-position="center">合同标的物信息</el-divider>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteProductsList">删除</el-button>
        </el-col>
      </el-row>
      <el-table :data="ProductsList" :row-class-name="rowProductsListIndex"
                @selection-change="handleProductsListSelectionChange" ref="supplier">
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="序号" align="center" prop="index" width="50"/>
        <el-table-column label="产品服务名称" prop="productName" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.productName" placeholder=""/>
          </template>
        </el-table-column>
        <el-table-column label="框架合同行编号" prop="productCode" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.productCode" placeholder="" readonly/>
          </template>
        </el-table-column>
        <el-table-column label="规格型号" prop="specifications" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.specifications" placeholder="" readonly/>
          </template>
        </el-table-column>
        <el-table-column label="单位" prop="unit" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.unit" placeholder="" readonly/>
          </template>
        </el-table-column>
        <el-table-column label="含税单价" prop="price" width="150">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.price" controls-position="right"
                             @change="changeInput" :min="0" readonly></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="taxRate" width="150">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.number" controls-position="right"
                             @change="changeInput" :min="0"  readonly></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="小计" prop="materialName" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.subtotal" placeholder="" readonly/>
          </template>
        </el-table-column>
      </el-table>

      <el-divider content-position="center">签署执行状态</el-divider>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="签署方数" prop="signatories">
          <el-select v-model="form.signatories" placeholder="请选择签署方数" @change="selectChangeSignatories"
                     style="width: 300px">
            <el-option
              v-for="dict in dict.type.signatories"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="我方主体" prop="ourEntity">
          <el-input v-model="ourEntity" placeholder="请输入我方主体" style="width: 300px;"/>
          <i class="el-icon-search" id="serachOne" @click="showCompanies"></i>
          <el-dialog :visible.sync="dialogCompanies" title="公司" :modal="false">
            <el-table v-loading="loading" :data="companiesList" @row-click="handleSelectionChangeCompanies">
              <el-table-column type="selection" width="55" align="center"/>
              <el-table-column label="企业名称" align="center" prop="companiesName"/>
              <el-table-column label="是否集团" align="center" prop="isgroup"/>
              <el-table-column label="公司编码" align="center" prop="companiesCode"/>
            </el-table>
            <pagination v-show="ctotal > 0" :total="ctotal" :page.sync="comqueryParams.pageNum"
                        :limit.sync="comqueryParams.pageSize" @pagination="getListCompanies"/>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogCompanies = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="乙方供应商" prop="supplierB">
          <el-input v-model="supplierB" placeholder="请输入乙方供应商" style="width: 300px"/>
          <i class="el-icon-search" id="serachOne" @click="showDialogSupplierListB()"></i>
          <el-dialog :visible.sync="dialogSupplierListB" title="供应商-浏览框" :modal="false">
            <!-- 这里是供应商的内容 -->
            <el-table v-loading="loading" :data="supplierList" @row-click="handleRowClickSupplierListB">
              <el-table-column type="selection" width="55" align="center"/>
              <el-table-column label="供应商编码" align="center" prop="sdCode"/>
              <el-table-column label="供应商名称" align="center" prop="sbiName"/>
            </el-table>

            <pagination v-show="supplierListTotal > 0" :total="supplierListTotal"
                        :page.sync="supplierListqueryParams.pageNum"
                        :limit.sync="supplierListqueryParams.pageSize" @pagination="getListSupplierListB"/>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogSupplierListB = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="乙方联系人" prop="personB" style="float: right;margin-left: 540px;margin-top: -174px">
          <el-input v-model="form.personB" placeholder="请输入乙方联系人" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="乙方联系方式" prop="phoneB" style="float: right;margin-left: 540px;margin-top: -117px">
          <el-input v-model="form.phoneB" placeholder="请输入乙方联系方式" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="乙方开户行" prop="bankB" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.bankB" placeholder="请输入乙方开户行" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="乙方开户行账号" prop="accountOpeningB">
          <el-input v-model="form.accountOpeningB" placeholder="请输入乙方开户行账号" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="合同方金额" prop="contractAmount"
                      style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.contractAmount" placeholder="请输入合同方金额" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="已支付金额" prop="paidAmount">
          <el-input v-model="form.paidAmount" placeholder="请输入已支付金额" style="width: 300px"  @blur="alreadyPaidAmount"/>
        </el-form-item>
        <el-form-item label="锁定金额" prop="lockInAmount" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.lockInAmount" placeholder="请输入锁定金额" @blur="alreadyPaidAmount" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="剩余金额" prop="remainingAmount">
          <el-input v-model="form.remainingAmount" placeholder="请输入剩余金额" style="width: 300px"/>
        </el-form-item>
      </el-form>


      <!--   丙方   -->
      <div v-if="showForm">
        <el-divider content-position="center">丙方</el-divider>
        <el-form ref="form" :model="form" :rules="rules" label-width="110px">
          <el-form-item label="丙方供应商" prop="supplierC">
            <el-input v-model="supplierC" placeholder="请输入丙方供应商" style="width: 300px"/>
            <i class="el-icon-search" id="serachOne" @click="showDialogSupplierListC()"></i>
            <el-dialog :visible.sync="dialogSupplierListC" title="供应商-浏览框" :modal="false">
              <!-- 这里是供应商的内容 -->
              <el-table v-loading="loading" :data="supplierList" @row-click="handleRowClickSupplierListC">
                <el-table-column type="selection" width="55" align="center"/>
                <el-table-column label="供应商编码" align="center" prop="sdCode"/>
                <el-table-column label="供应商名称" align="center" prop="sbiName"/>
              </el-table>

              <pagination v-show="supplierListTotal > 0" :total="supplierListTotal"
                          :page.sync="supplierListqueryParams.pageNum"
                          :limit.sync="supplierListqueryParams.pageSize" @pagination="getListSupplierListC"/>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogSupplierListC = false">取消</el-button>
              </div>
            </el-dialog>
          </el-form-item>
          <el-form-item label="丙方联系人" prop="personB" style="float: right;margin-left: 540px;margin-top: -59px">
            <el-input v-model="personC"  placeholder="请输入丙方联系人" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="丙方联系方式" prop="phoneB">
            <el-input v-model="phoneC" placeholder="请输入丙方联系方式" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="丙方开户行" prop="bankB" style="float: right;margin-left: 540px;margin-top: -59px">
            <el-input v-model="bankC" placeholder="请输入丙方开户行" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="丙方开户行账号" prop="accountOpeningB">
            <el-input v-model="accountOpeningC" placeholder="请输入丙方开户行账号" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="合同方金额" prop="contractAmount"
                        style="float: right;margin-left: 540px;margin-top: -59px">
            <el-input v-model="contractAmount" placeholder="请输入合同方金额" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="已支付金额" prop="paidAmount">
            <el-input v-model="paidAmountC" placeholder="请输入已支付金额" @blur="alreadyPaidAmountC" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="锁定金额" prop="lockInAmount" style="float: right;margin-left: 540px;margin-top: -59px">
            <el-input v-model="lockInAmountC" placeholder="请输入锁定金额" @blur="alreadyPaidAmountC" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="剩余金额" prop="remainingAmount">
            <el-input v-model="remainingAmountC" placeholder="请输入剩余金额" readonly style="width: 300px"/>
          </el-form-item>
        </el-form>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 合同转订单对话框 -->
    <el-dialog :title="title" :visible.sync="openOrder" width="1000px" append-to-body>
      <el-divider content-position="center">基本信息</el-divider>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="订单号" prop="applicant">
          <el-input v-model="form.orderCode" placeholder="" readonly style="width: 300px"/>
        </el-form-item>
        <el-form-item label="订单类型" prop="orderType" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.orderSource" placeholder="" style="width: 300px;">
          </el-input>
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierB" >
          <el-input v-model="form.supplierB" placeholder="请输入供应商名称" style="width: 300px"/>
          <i class="el-icon-search" id="serachOne4" @click="showDialogSupplierListOrder()"></i>
          <el-dialog :visible.sync="dialogSupplierListD" title="供应商-浏览框" :modal="false">
            <!-- 这里是供应商的内容 -->
            <el-table v-loading="loading" :data="supplierList" @row-click="handleRowClickSupplierListOrder">
              <el-table-column type="selection" width="55" align="center"/>
              <el-table-column label="供应商编码" align="center" prop="sdCode"/>
              <el-table-column label="供应商名称" align="center" prop="sbiName"/>
              <el-table-column label="供应商名称" align="center" prop="sdId"/>
            </el-table>
            <pagination v-show="supplierListTotal > 0" :total="supplierListTotal"
                        :page.sync="supplierListqueryParams.pageNum"
                        :limit.sync="supplierListqueryParams.pageSize" @pagination="showDialogSupplierListOrder"/>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogSupplierListC = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="供应商联系人" prop="personB" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.personB" readonly placeholder="请输入供应商联系人" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="供应商联系方式" prop="phoneB" >
          <el-input v-model="form.phoneB" readonly placeholder="请输入供应商联系方式" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="采购负责人" prop="head" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.head" readonly placeholder="请输入负责人" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="币种" prop="currencyId" >
          <el-input v-model="currencyName" readonly  placeholder="请输入币种" style="width: 300px"/>
          <i class="el-icon-search" id="serachOne4" @click="showDiagCurrency"></i>
          <el-dialog :visible.sync="dialogCurrency" title="币种定义" :modal="false">
            <el-table v-loading="loading" :data="currencyList" @row-click="handleSelectionChangeCurrency">
              <el-table-column type="selection" width="55" align="center"/>
              <el-table-column label="币种编码" align="center" prop="currencyCode"/>
              <el-table-column label="币种名称" align="center" prop="currencyName"/>
              <el-table-column label="国家/地区" align="center" prop="countryRegion"/>
              <el-table-column label="财务精度" align="center" prop="financialAccuracy"/>
              <el-table-column label="精度" align="center" prop="accuracy"/>
              <el-table-column label="货币符号" align="center" prop="currencySymbol"/>
            </el-table>
            <pagination v-show="currencytotal > 0" :total="currencytotal" :page.sync="cqueryParams.pageNum"
                        :limit.sync="cqueryParams.pageSize" @pagination="getListCurrency"/>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogCurrency = false">取消</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item label="关联合同" prop="contractCode" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.contractCode" placeholder="请输入关联合同" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="关联ERP清单" prop="purchasingList" >
          <el-input v-model="form.purchasingList" placeholder="" readonly  style="width: 300px"/>
        </el-form-item>
      </el-form>


      <el-divider content-position="center">订单明细</el-divider>
      <el-table :data="ProductsList" :row-class-name="rowProductsListIndex"
                @selection-change="handleProductsListSelectionChange" ref="supplier">
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="序号" align="center" prop="index" width="50"/>
        <el-table-column label="产品服务名称" prop="productName" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.productName" placeholder=""/>
          </template>
        </el-table-column>
        <el-table-column label="框架合同行编号" prop="productCode" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.productCode" placeholder="" readonly/>
          </template>
        </el-table-column>
        <el-table-column label="规格型号" prop="specifications" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.specifications" placeholder="" readonly/>
          </template>
        </el-table-column>
        <el-table-column label="单位" prop="unit" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.unit" placeholder="" readonly/>
          </template>
        </el-table-column>
        <el-table-column label="含税单价" prop="price" width="150">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.price" controls-position="right"
                             @change="changeInput" :min="0" readonly></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="taxRate" width="150">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.number" controls-position="right"
                             @change="changeInput" :min="0"  readonly></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="小计" prop="materialName" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.subtotal" placeholder="" readonly/>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormOrder">提 交</el-button>
        <el-button @click="cancelOrder">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看详情对话框 -->
    <el-dialog :title="title" :visible.sync="openSelectAll" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="申请人" prop="applicant">
          <el-input v-model="form.applicant" placeholder="请输入申请人" style="width: 300px" readonly/>
        </el-form-item>
        <el-form-item label="申请日期" prop="applicationDate" style="float: right;margin-left: 540px;margin-top: -58px">
          <el-date-picker clearable
                          v-model="form.applicationDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择申请日期"
                          readonly style="width: 300px" readonly>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请部门" prop="department" >
          <el-input v-model="form.department" placeholder="请输入申请部门" style="width: 300px;" readonly/>
        </el-form-item>
        <el-form-item label="申请公司" prop="companies">
          <el-input v-model="form.companies" placeholder="请输入申请公司" style="width: 300px" readonly/>
        </el-form-item>
        <el-form-item label="是否主子合同" prop="isMainContract">
          <el-radio-group v-model="form.isMainContract" >
            <el-radio
              v-for="dict in dict.type.is_main_contract"
              :key="dict.value"
              :label="parseInt(dict.value)"
              readonly
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="预算相关合同" prop="budgetRelatedContracts"
                      style="float: right;margin-left: 540px;margin-top: -116px">
          <el-select v-model="form.budgetRelatedContracts" placeholder="请选择预算相关合同" style="width: 300px" >
            <el-option
              v-for="dict in dict.type.budget_related_contracts"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
              readonly
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同名称" prop="contractName">
          <el-input v-model="form.contractName" placeholder="请输入合同名称" style="width: 300px" readonly/>
        </el-form-item>
        <el-form-item label="项目相关合同" prop="projectRelatedContracts"
                      style="float: right;margin-left: 540px;margin-top: -232px">
          <el-select v-model="form.projectRelatedContracts"  placeholder="请选择项目相关合同" style="width: 300px" >
            <el-option
              v-for="dict in dict.type.project_related_contracts"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
              readonly
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同状态" prop="contractstatusId" style="float: right;margin-left: 540px;margin-top: -116px">
          <el-select v-model="form.contractstatusId" placeholder="请选择合同状态" style="width: 300px" >
            <el-option
              v-for="dict in dict.type.contract_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
              readonly
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="密集" prop="dense" style="float: right;margin-left: 540px;margin-top: -58px">
          <el-select v-model="form.dense" placeholder="请选择密集" style="width: 300px" >
            <el-option
              v-for="dict in dict.type.dense"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime">
          <el-date-picker clearable
                          v-model="form.beginTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择开始时间" style="width: 300px" readonly>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime" style="float: right;margin-left: 540px;margin-top: -58px">
          <el-date-picker clearable
                          v-model="form.endTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择结束时间" style="width: 300px" readonly>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="合同金额" prop="contractPrice">
          <el-input v-model="form.contractPrice" placeholder="请输入合同金额" style="width: 300px" readonly/>
        </el-form-item>
        <el-form-item label="大写" prop="capitalization" style="float: right;margin-left: 540px;margin-top: -58px">
          <el-input v-model="form.capitalization" readonly style="border: none;width: 300px" readonly/>
        </el-form-item>
        <el-form-item label="印花税金额" prop="stampDutyAmount">
          <el-input v-model="form.stampDutyAmount" placeholder="请输入印花税金额" style="width: 300px" readonly/>
        </el-form-item>
        <el-form-item label="负责人" prop="head" style="float: right;margin-left: 540px;margin-top: -58px">
          <el-input v-model="form.head" placeholder="请输入负责人" style="width: 300px" readonly/>
        </el-form-item>
        <el-form-item label="合同情况说明" prop="contractDescription">
          <el-input v-model="form.contractDescription" placeholder="请输入合同情况说明"  readonly/>
        </el-form-item>
        <el-form-item label="采购清单" prop="purchasingList">
          <el-input v-model="orderCode" placeholder="" readonly/>
        </el-form-item>
      </el-form>
      <el-divider content-position="center">合同标的物信息</el-divider>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteProductsList">删除</el-button>
        </el-col>
      </el-row>
      <el-table :data="ProductsList" :row-class-name="rowProductsListIndex"
                @selection-change="handleProductsListSelectionChange" ref="supplier">
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="序号" align="center" prop="index" width="50"/>
        <el-table-column label="产品服务名称" prop="productName" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.productName" placeholder=""/>
          </template>
        </el-table-column>
        <el-table-column label="框架合同行编号" prop="productCode" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.productCode" placeholder="" readonly/>
          </template>
        </el-table-column>
        <el-table-column label="规格型号" prop="specifications" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.specifications" placeholder="" readonly/>
          </template>
        </el-table-column>
        <el-table-column label="单位" prop="unit" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.unit" placeholder="" readonly/>
          </template>
        </el-table-column>
        <el-table-column label="含税单价" prop="price" width="150">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.price" controls-position="right"
                             @change="changeInput" :min="0" readonly></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="taxRate" width="150">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.number" controls-position="right"
                             @change="changeInput" :min="0"  readonly></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="小计" prop="materialName" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.subtotal" placeholder="" readonly/>
          </template>
        </el-table-column>
      </el-table>
      <el-divider content-position="center">签署执行状态</el-divider>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="签署方数" prop="signatories">
          <el-select v-model="form.signatories" readonly placeholder="请选择签署方数" @change="selectChangeSignatories"
                     style="width: 300px">
            <el-option
              v-for="dict in dict.type.signatories"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="我方主体" prop="ourEntity">
          <el-input v-model="ourEntity" readonly placeholder="请输入我方主体" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="乙方供应商" prop="supplierB">
          <el-input v-model="supplierB" readonly placeholder="请输入乙方供应商" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="乙方联系人" prop="personB" style="float: right;margin-left: 540px;margin-top: -174px">
          <el-input v-model="form.personB" readonly placeholder="请输入乙方联系人" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="乙方联系方式" prop="phoneB" style="float: right;margin-left: 540px;margin-top: -117px">
          <el-input v-model="form.phoneB" readonly placeholder="请输入乙方联系方式" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="乙方开户行" prop="bankB" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.bankB" readonly placeholder="请输入乙方开户行" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="乙方开户行账号" prop="accountOpeningB">
          <el-input v-model="form.accountOpeningB" readonly placeholder="请输入乙方开户行账号" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="合同方金额" prop="contractAmount"
                      style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.contractAmount" readonly placeholder="请输入合同方金额" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="已支付金额" prop="paidAmount">
          <el-input v-model="form.paidAmount" readonly placeholder="请输入已支付金额" style="width: 300px"  @blur="alreadyPaidAmount"/>
        </el-form-item>
        <el-form-item label="锁定金额" prop="lockInAmount" style="float: right;margin-left: 540px;margin-top: -59px">
          <el-input v-model="form.lockInAmount" readonly placeholder="请输入锁定金额" @blur="alreadyPaidAmount" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="剩余金额" prop="remainingAmount">
          <el-input v-model="form.remainingAmount" readonly placeholder="请输入剩余金额" style="width: 300px"/>
        </el-form-item>
      </el-form>
      <!--   丙方   -->
      <div v-if="showForm">
        <el-divider content-position="center">丙方</el-divider>
        <el-form ref="form" :model="form" :rules="rules" label-width="110px">
          <el-form-item label="丙方供应商" prop="supplierC">
            <el-input v-model="supplierC" placeholder="请输入丙方供应商" readonly style="width: 300px"/>
          </el-form-item>
          <el-form-item label="丙方联系人" prop="personB" style="float: right;margin-left: 540px;margin-top: -59px">
            <el-input v-model="personC"  placeholder="请输入丙方联系人" readonly style="width: 300px"/>
          </el-form-item>
          <el-form-item label="丙方联系方式" prop="phoneB">
            <el-input v-model="phoneC" placeholder="请输入丙方联系方式" readonly style="width: 300px"/>
          </el-form-item>
          <el-form-item label="丙方开户行" prop="bankB" style="float: right;margin-left: 540px;margin-top: -59px">
            <el-input v-model="bankC" placeholder="请输入丙方开户行" readonly style="width: 300px"/>
          </el-form-item>
          <el-form-item label="丙方开户行账号" prop="accountOpeningB">
            <el-input v-model="accountOpeningC" readonly placeholder="请输入丙方开户行账号" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="合同方金额" prop="contractAmount"
                        style="float: right;margin-left: 540px;margin-top: -59px">
            <el-input v-model="contractAmount" readonly placeholder="请输入合同方金额" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="已支付金额" prop="paidAmount">
            <el-input v-model="paidAmountC" readonly placeholder="请输入已支付金额" @blur="alreadyPaidAmountC" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="锁定金额" prop="lockInAmount" style="float: right;margin-left: 540px;margin-top: -59px">
            <el-input v-model="lockInAmountC" readonly placeholder="请输入锁定金额" @blur="alreadyPaidAmountC" style="width: 300px"/>
          </el-form-item>
          <el-form-item label="剩余金额" prop="remainingAmount">
            <el-input v-model="remainingAmountC" placeholder="请输入剩余金额" readonly style="width: 300px"/>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormSelectAll">确 定</el-button>
        <el-button @click="cancelSelectAll">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  addManagement,
  delManagement,
  getManagement,
  listManagement,
  listSupplier,
  updateManagement,
  getManagementById
} from "@/api/procure/management";
import {listProducts} from "@/api/pms/products";
import {addManager, listManager, updateManager} from "@/api/pms/manager"
import {listCompanies, listCurrency} from "@/api/procure/requirement";
import {addStatus, updateStatus} from "@/api/procure/status";

export default {
  name: "Management",
  dicts: ['is_main_contract', 'budget_related_contracts', 'dense', 'project_related_contracts', 'contract_status', 'signatories', 'order_type'],
  data() {
    return {
      showForm: false,//默认情况下隐藏
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
      // 采购合同管理表格数据
      managementList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 合同转订单弹出层
      openOrder:false,
      // 查看详情弹出层
      openSelectAll:false,
      // 进度条展示
      paymentProportion: 0,
      // 供应商基本信息
      supplierList: [],
      // 供应商基本信息总条数
      supplierListTotal: 0,
      //供应商查询参数
      supplierListqueryParams: {
        pageNum: 1,
        pageSize: 10,
        sdCode: null,
        sbiName: null,
        phone: '',
        people: '',
      },
      // 合同标的物信息表格数据
      ProductsList: [],
      // 合同标的物查询参数
      productqueryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 合同标的物列表
      productList: [],
      // 合同标的物总数
      productTotal: 0,
      // 采购清单列表
      managerList: [],

      // 采购清单总数
      managerTotal: 0,
      managerqueryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 采购清单定义
      orderCode: '',
      // 乙方供应商定义
      supplierB: '',
      // 丙方供应商定义
      supplierC: '',
      personC: '',
      bankC: '',
      accountOpeningC: '',
      phoneC: '',
      // 公司表表格数据
      companiesList: [],
      // 查询参数
      comqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 公司定义总条数
      ctotal: 0,
      // 公司名称定义
      ourEntity: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractName: null,
        contractCode: null,
        projectRelatedContracts: null,
        applicant: null,
        applicationDate: null,
        department: null,
        companies: null,
        relatedProjects: null,
        isMainContract: null,
        relatedMainContract: null,
        budgetRelatedContracts: null,
        costCenter: null,
        head: null,
        signingDate: null,
        paymentProportion: null,
        contractstatusId: null,
        sdId: null,
        totalProjectBudget: null,
        projectAvailableBudget: null,
        contractType: null,
        dense: null,
        beginTime: null,
        endTime: null,
        contractPrice: null,
        capitalization: null,
        stampDutyAmount: null,
        contractDescription: null,
        purchasingList: null
      },
      // 签署执行状态集合(乙方)
      statusList: [],
      // 币种表格数据
      currencyList: [],
      // 币种定义总条数
      currencytotal: 0,
      // 币种名称定义
      currencyName: '',
      cqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {
        contractPrice: '', // 合同金额
      },
      contractAmount: 0, // 新增的合同金额变量
      paidAmountC: null,
      lockInAmountC:null,
      remainingAmountC:null,
      bothSides:null,
      // 表单校验
      rules: {
        applicant: [
          {required: true, message: "申请人不能为空", trigger: "blur"}
        ],
        contractstatusId:[
          {required: true, message: "合同状态不能为空", trigger: "blur"}
        ],
        paidAmount:[
          {required: true, message: "已支付金额不能为空", trigger: "blur"}
        ],
        paidAmountC:[
          {required: true, message: "已支付金额不能为空", trigger: "blur"}
        ],
        phoneB:[
          {required: true, message: "联系方式不能为空", trigger: "blur"}
        ],
        lockInAmount:[
          {required: true, message: "锁定金额不能为空", trigger: "blur"}
        ]
      },
      dialogSupplierList: false, // 用于标记供应商列表是否展示
      dialogProducts: false, // 用于标记合同标的物信息列表是否展示
      dialogManager: false, // 用于采购清单列表是否展示
      dialogSupplierListB: false, // 用于标记供应商列表是否展示
      dialogSupplierListC: false, // 用于标记供应商列表是否展示
      dialogSupplierListD: false, // 用于标记供应商列表是否展示
      dialogCompanies: false, //用于公司信息是否可见
      dialogCurrency: false, //用于币种信息是否可见（需求申请）
      bothSidesList:[], // 三方数组
    };
  },
  created() {
    this.getList();
    this.getListProducts();
  },
  methods: {
    /** 查询采购合同管理列表 */
    getList() {
      this.loading = true;
      listManagement(this.queryParams).then(response => {
        this.managementList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 生成合同取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 合同转订单取消按钮
    cancelOrder() {
      this.openOrder = false;
      // this.reset();
    },
    // 查看详情取消按钮
    cancelSelectAll() {
      this.openSelectAll = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        // 合同表
        orderCode:null,
        contractManagementId: null,
        contractName: null,
        contractCode: null,
        projectRelatedContracts: null,
        applicant: null,
        applicationDate: null,
        department: null,
        companies: null,
        relatedProjects: null,
        isMainContract: null,
        relatedMainContract: null,
        budgetRelatedContracts: null,
        costCenter: null,
        head: null,
        signingDate: null,
        paymentProportion: null,
        contractstatusId: null,
        sdId: null,
        totalProjectBudget: null,
        projectAvailableBudget: null,
        contractType: null,
        dense: null,
        beginTime: null,
        endTime: null,
        contractPrice: null,
        capitalization: null,
        stampDutyAmount: null,
        contractDescription: null,
        purchasingList: null,
        // 签署状态
        executionId: null,
        signatories: null,
        ourEntity: null,
        supplierB: null,
        personB: null,
        phoneB: null,
        bankB: null,
        accountOpeningB: null,
        contractAmount: null,
        currencyName: null,
        paidAmount: null,
        lockInAmount: null,
        remainingAmount: null,
        paidAmountC: null,
        lockInAmountC: null,
        remainingAmountC: null,
        bothSides:null,
        // 订单
        orderId:null,
      };
      this.statusList=[];
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
      this.ids = selection.map(item => item.contractManagementId)
      this.bothSides = selection.map(item => item.executionStatus.bothSides)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.form.signatories = 1;
      this.form.contractPrice = 0.00;
      this.form.dense = 1;
      this.form.isMainContract = 1;
      this.form.budgetRelatedContracts = 1;
      this.form.projectRelatedContracts = 1;
      this.form.contractName = '一般采购合同';
      this.form.applicationDate = new Date(); // 申请日期（当天时间）
      this.form.capitalization = '零元整';
      this.title = "添加采购合同管理";
      this.ProductsList=[];  //清空合同标的物信息
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contractManagementId = row.contractManagementId || this.ids
      const bothSides=this.bothSides
      getManagement(contractManagementId,bothSides).then(response => {
        this.form = response.data;
        this.ProductsList=[]  //清空合同标的物信息
        // 多条数据
        for (let i = 0; i < this.productList.length; i++) {
          const innerElement = this.productList[i];
          if (innerElement.orderCode === response.data.purchasingList) {
            this.ProductsList.push(innerElement)
          }
        }
        this.form.supplierB=response.data.executionStatus.supplierB;
        this.form.personB=response.data.executionStatus.personB;
        this.form.phoneB=response.data.executionStatus.phoneB;
        this.form.orderId=response.data.orderManager.orderId;
        this.openOrder = true;
        this.form.orderSource='合同订单';
        this.currencyName="人民币";
        this.title = "采购订单审批";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.statusList=[];
      this.form.executionStatuses=[];
       let obj={
         signatories:this.form.signatories,
         contractAmount:this.form.contractAmount,
         paidAmount:this.form.paidAmount,
         ourEntity:this.ourEntity,
         supplierB:this.supplierB,
         personB:this.form.personB,
         phoneB:this.form.phoneB,
         bankB:this.form.bankB,
         accountOpeningB:this.form.accountOpeningB,
         lockInAmount:this.form.lockInAmount,
         remainingAmount:this.form.remainingAmount,
         purchasingList:this.orderCode,
       };
        this.statusList.push(obj);
        if(this.form.signatories==2||this.form.signatories=='2'){
          let obj1={
            signatories:this.form.signatories,
            contractAmount:this.contractAmount,
            paidAmount:this.paidAmountC,
            ourEntity:this.ourEntity,
            supplierB:this.supplierC,
            personB:this.personC,
            phoneB:this.phoneC,
            bankB:this.bankC,
            accountOpeningB:this.accountOpeningC,
            lockInAmount:this.lockInAmountC,
            remainingAmount:this.remainingAmountC,
            purchasingList:this.orderCode,
          };
          this.statusList.push(obj1);
        }
        this.$refs["form"].validate(valid => {
        this.form.executionStatuses=this.statusList;
      });
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.statusList=this.statusList;
          if (this.form.contractManagementId != null) {
            updateManagement(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.purchasingList = this.orderCode;
            addManagement(this.form).then(response => {
              this.$modal.msgSuccess("采购合同发起成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 合同转订单提交 */
    submitFormOrder(){
      this.$refs["form"].validate(valid => {
        console.log(this.form.orderId)
        this.form.supplier=this.form.supplierDetails.sdId
        this.form.contacts=this.form.personB
        this.form.phone=this.form.phoneB
        this.form.orderSource=4
        this.form.taxTotal=this.form.contractPrice
        console.log(this.form.contractPrice)
        if (valid) {
          if (this.form.orderId != null) {
            addManager(this.form).then(response => {
              this.$modal.msgSuccess("生成采购订单成功");
              this.openOrder = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const contractManagementIds = row.contractManagementId || this.ids;
      this.$modal.confirm('是否确认删除采购合同管理编号为"' + contractManagementIds + '"的数据项？').then(function () {
        return delManagement(contractManagementIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('procure/management/export', {
        ...this.queryParams
      }, `management_${new Date().getTime()}.xlsx`)
    },
    /** 查询供应商 */
    showDialogSupplierList() {
      this.dialogSupplierList = true;
      this.getListSupplierList();
    },
    /** 查询供应商详细列表 */
    getListSupplierList() {
      this.loading = true;
      listSupplier(this.supplierListqueryParams).then(response => {
        this.supplierList = response.rows;
        this.stotal = response.total;
        this.loading = false;
      });
    },
    /** 供应商名称查询（填充） */
    handleRowClickSupplierList(row) {
      this.supplierListqueryParams.sbiName = row.sbiName; // 将供应商名称填充到输入框中
      this.dialogSupplierList = false; // 关闭对话框
    },
    /** 合同标的物信息序号 */
    rowProductsListIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    //计算小计并累加到合同金额 输入框改变值是触发事件
    changeInput(){
      let total=0
      for (let i = 0; i < this.ProductsList.length; i++) {
        var products=this.ProductsList[i]
        // 数量
        let number=products.number === ''||products.number+""=== 'undefined' ?0:products.number
        // 单价
        let price=products.price === '' || products.price=== null|| products.price+""=== 'undefined'?0:products.price
        let num=Number(number)*Number(price)
        this.ProductsList[i].subtotal=num
        total=total+Number(num)
      }
      this.form.contractPrice=total // 合同金额
      this.form.contractAmount=total // 合同方金额
      var capital="零元整"
      if (total!=0||total!="0"){
        capital= this.convertCurrency(total);
      }
      this.form.capitalization=capital
    },
    /** 查询产品信息列表 */
    getListProducts() {
      this.loading = true;
      listProducts(this.productqueryParams).then(response => {
        this.productList = response.rows;
        this.producttotal = response.total;
        this.loading = false;
      });
    },
    //控制产品信息的显示
    showProduct() {
      this.dialogProducts = true;
      this.getListProducts();
    },
    /** 产品信息复选框选中数据 */
    handleProductsListSelectionChange(selection) {
      this.checkedProducts = selection.map(item => item.index)
    },
    /** 产品信息删除按钮操作 */
    handleDeleteProductsList() {
      if (this.checkedProducts.length === 0) {
        this.$modal.msgError("请先选择要删除的产品数据");
      } else {
        const ProductsList = this.ProductsList;
        const checkedProducts = this.checkedProducts;
        this.ProductsList = ProductsList.filter(function (item) {
          return checkedProducts.indexOf(item.index) === -1
        });
        //重新计算’合同金额‘
        this.changeInput()
      }
    },
    /** 查询采购订单管理列表 */
    getListManager() {
      this.loading = true;
      listManager(this.managerqueryParams).then(response => {
        this.managerList = response.rows;
        this.managerTotal = response.total;
        this.loading = false;
      });
    },
    //控制采购订单信息的显示
    showManager() {
      this.dialogManager = true;
      this.getListManager();
    },
    // 选中采购清单
    handleSelectionChangeManager(row) {
      this.ProductsList=[]  //清空合同标的物信息
      this.form.orderId = row.orderId;
      for (let i = 0; i < this.managerList.length; i++) {
        const innerElement = this.managerList[i];
        if (innerElement.orderId === row.orderId) {
          this.orderCode = innerElement.orderCode
        }
      }
      // 多条数据
      for (let i = 0; i < this.productList.length; i++) {
        const innerElement = this.productList[i];
        if (innerElement.orderCode === this.orderCode) {
          this.ProductsList.push(innerElement)
        }
      }
      this.dialogManager = false; // 关闭对话框
      //重新计算’合同金额‘
      this.changeInput()
    },
    // 三方（双方）
    selectChangeSignatories(row) {
      this.form.signatories = row;
      if (this.form.signatories === 2) {
        this.showForm = true;
      } else {
        this.showForm = false;
      }
    },
    /** 乙方查询供应商 */
    showDialogSupplierListB() {
      this.dialogSupplierListB = true;
      this.getListSupplierList();
    },
    /** 查询供应商详细列表 */
    getListSupplierListB() {
      this.loading = true;
      listSupplier(this.supplierListqueryParams).then(response => {
        this.supplierList = response.rows;
        this.stotal = response.total;
        this.loading = false;
      });
    },
    /** 乙方供应商（填充） */
    handleRowClickSupplierListB(row) {
      this.form.sdId = row.sdId;
      this.supplierB = row.sbiName;
      this.dialogSupplierListB = false; // 关闭对话框
    },
    /** 查询供应商详细列表 */
    getListSupplierListC() {
      this.loading = true;
      listSupplier(this.supplierListqueryParams).then(response => {
        this.supplierList = response.rows;
        this.stotal = response.total;
        this.loading = false;
      });
    },
    /** 丙方查询供应商 */
    showDialogSupplierListC() {
      this.dialogSupplierListC = true;
      this.getListSupplierList();
    },
    /** 丙方供应商（填充） */
    handleRowClickSupplierListC(row) {
      this.form.sdId = row.sdId;
      this.supplierC = row.sbiName;  // 供应商
      this.personC = row.sdPcn; //联系人
      this.bankC = row.sdBank; //开户行
      this.accountOpeningC = row.sdAccount;
      this.phoneC = row.sdPcp; //联系人
      this.dialogSupplierListC = false; // 关闭对话框
    },
    // 公司
    showCompanies() {
      this.dialogCompanies = true;
      this.getListCompanies();
    },
    /** 查询公司表列表 */
    getListCompanies() {
      this.loading = true;
      listCompanies(this.queryParams).then(response => {
        this.companiesList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 选中公司
    handleSelectionChangeCompanies(row) {
      this.form.companiesId = row.companiesId;
      for (let i = 0; i < this.companiesList.length; i++) {
        const innerElement = this.companiesList[i];
        if (innerElement.companiesId === row.companiesId) {
          this.ourEntity = innerElement.companiesName
        }
      }
      this.dialogCompanies = false; // 关闭对话框
    },
    // 将数字转换为中文大写金额
    convertCurrency(money) {
      //汉字的数字
      var cnNums = new Array("零","壹", "贰", "叁", "肆", "伍","陆","柒","捌","玖");
      //基本单位
      var cnIntRadice = new Array("", "拾", "佰", "仟");
      //对应整数部分扩展单位
      var cnIntUnits = new Array("", "万", "亿", "兆");
      //对应小数部分单位
      var cnDecUnits = new Array("角", "分", "毫", "厘");
      //整数金额时后面跟的字符
      var cnInteger = "整";
      //整型完以后的单位
      var cnIntLast = "元";
      //最大处理的数字
      var maxNum = 999999999999999.9999;
      //金额整数部分
      var integerNum;
      //金额小数部分
      var decimalNum;
      //输出的中文金额字符串
      var chineseStr = "";
      //分离金额后用的数组，预定义
      var parts;
      if (money == "") {
        return "";
      }
      money = parseFloat(money);
      if (money >= maxNum) {
        //超出最大处理数字
        return "";
      }
      if (money == 0) {
        chineseStr = cnNums[0] + cnIntLast + cnInteger;
        return chineseStr;
      }
      //转换为字符串
      money = money.toString();
      if (money.indexOf(".") == -1) {
        integerNum = money;
        decimalNum = "";
      } else {
        parts = money.split(".");
        integerNum = parts[0];
        decimalNum = parts[1].substr(0, 4);
      }
      //获取整型部分转换
      if (parseInt(integerNum, 10) > 0) {
        var zeroCount = 0;
        var IntLen = integerNum.length;
        for (var i = 0; i < IntLen; i++) {
          var n = integerNum.substr(i, 1);
          var p = IntLen - i - 1;
          var q = p / 4;
          var m = p % 4;
          if (n == "0") {
            zeroCount++;
          } else {
            if (zeroCount > 0) {
              chineseStr += cnNums[0];
            }
            //归零
            zeroCount = 0;
            chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
          }
          if (m == 0 && zeroCount < 4) {
            chineseStr += cnIntUnits[q];
          }
        }
        chineseStr += cnIntLast;
      }
      //小数部分
      if (decimalNum != "") {
        var decLen = decimalNum.length;
        for (var i = 0; i < decLen; i++) {
          var n = decimalNum.substr(i, 1);
          if (n != "0") {
            chineseStr += cnNums[Number(n)] + cnDecUnits[i];
          }
        }
      }
      if (chineseStr == "") {
        chineseStr += cnNums[0] + cnIntLast + cnInteger;
      } else if (decimalNum == "") {
        chineseStr += cnInteger;
      }
      return chineseStr;
    },
    //计算已支付金额
    alreadyPaidAmount(){
      const contract_amount=this.form.contractAmount;
      const paid_amount=this.form.paidAmount;
      const lock_in_amount=this.form.lockInAmount;
      if (this.form.lockInAmount===""){
        this.form.remainingAmount=contract_amount - paid_amount;
      }else {
        this.form.remainingAmount=contract_amount - paid_amount-lock_in_amount;
      }
    },
    //计算已支付金额(丙)
    alreadyPaidAmountC(){
      const contract_amount=this.contractAmount;
      const paid_amount=this.paidAmountC;
      const lock_in_amount=this.lockInAmountC;
      if (this.lockInAmountC===""){
        this.remainingAmountC=contract_amount - paid_amount;
      }else {
        this.remainingAmountC=contract_amount - paid_amount-lock_in_amount;
      }
    },
    /** 生合同转订单 供应商（填充） */
    handleRowClickSupplierListOrder(row) {
      this.form.sdId=row.sdId;
      this.form.supplierB = row.sbiName;
      this.form.personB=row.sdPcn;
      this.form.phoneB=row.sdPcp;
      this.dialogSupplierListD = false; // 关闭对话框
    },
    /** 丙方查询供应商 */
    showDialogSupplierListOrder() {
      this.dialogSupplierListD = true;
      this.getListSupplierList();
    },
    // 币种
    showDiagCurrency() {
      this.dialogCurrency = true;
      this.getListCurrency();
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
    // 选中币种
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
    // 查看详情
    handleSelectAll(contractManagementId){
      this.openSelectAll=true;
      //清空合同标的物信息
      this.ProductsList=[]
      getManagementById(contractManagementId).then(response => {
        this.form = response.data;
        this.bothSidesList=response.data.executionStatuses
        console.log(response.data)
        // 多条数据(产品)
        for (let i = 0; i < this.productList.length; i++) {
          const innerElement = this.productList[i];
          if (innerElement.orderCode === response.data.purchasingList) {
            this.ProductsList.push(innerElement)
          }
        }
        this.orderCode=response.data.orderManager.orderCode;
        this.form.companies=response.data.companiesId;
        this.title = "采购合同详情表";
          if (this.bothSidesList.length===1){
            this.showForm = false;
            this.form.signatories=this.bothSidesList[0].signatories;
            this.supplierB=this.bothSidesList[0].supplierB;
            this.form.personB=this.bothSidesList[0].personB;
            this.form.phoneB=this.bothSidesList[0].phoneB;
            this.ourEntity=this.bothSidesList[0].ourEntity;
            this.form.bankB=this.bothSidesList[0].bankB;
            this.form.accountOpeningB=this.bothSidesList[0].accountOpeningB;
            this.form.contractAmount=this.bothSidesList[0].contractAmount;
            this.form.paidAmount=this.bothSidesList[0].paidAmount;
            this.form.lockInAmount=this.bothSidesList[0].lockInAmount;
            this.form.remainingAmount=this.bothSidesList[0].remainingAmount;
            this.bothSides=this.bothSidesList[0].bothSides;
          }else {
            this.showForm = true;
            this.form.signatories=this.bothSidesList[0].signatories;
            this.supplierB=this.bothSidesList[0].supplierB;
            this.form.personB=this.bothSidesList[0].personB;
            this.form.phoneB=this.bothSidesList[0].phoneB;
            this.ourEntity=this.bothSidesList[0].ourEntity;
            this.form.bankB=this.bothSidesList[0].bankB;
            this.form.accountOpeningB=this.bothSidesList[0].accountOpeningB;
            this.form.contractAmount=this.bothSidesList[0].contractAmount;
            this.form.paidAmount=this.bothSidesList[0].paidAmount;
            this.form.lockInAmount=this.bothSidesList[0].lockInAmount;
            this.form.remainingAmount=this.bothSidesList[0].remainingAmount;
            this.bothSides=this.bothSidesList[1].bothSides;
            this.supplierC=this.bothSidesList[1].supplierB;
            this.personC=this.bothSidesList[1].personB;
            this.phoneC=this.bothSidesList[1].phoneB;
            this.bankC=this.bothSidesList[1].bankB;
            this.accountOpeningC=this.bothSidesList[1].accountOpeningB;
            this.contractAmount=this.bothSidesList[1].contractAmount;
            this.paidAmountC=this.bothSidesList[1].paidAmount;
            this.lockInAmountC=this.bothSidesList[1].lockInAmount;
            this.remainingAmountC=this.bothSidesList[1].remainingAmount;
          }
      });
    },
    // 查看详情 提交
    submitFormSelectAll(){
      this.openSelectAll=false;
    },
  }
};
</script>
<style>
#serachOne {
  position: absolute;
  right: 560px;
  top: 10px;
}

#serachOne1 {
  position: absolute;
  right: 15px;
  top: 21.5px;
}

#serachOne2 {
  position: absolute;
  right: 15px;
  top: 13px;
}

#serachOne3 {
  position: absolute;
  right: 10px;
  top: 10px;
}
#serachOne4 {
  position: absolute;
  right: 562px;
  top: 10px;
}
</style>
