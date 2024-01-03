<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考评名称" prop="evaluationName">
        <el-input v-model="queryParams.evaluationName" placeholder="请输入考评名称" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['supplierpms:performance:add']">新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-s-check" size="mini" :disabled="single" @click="InterviewReview"
          v-hasPermi="['supplierpms:performance:edit']">审核</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['supplierpms:performance:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['supplierpms:performance:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="performanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="考评编码" align="center" prop="evaluationCode" />
      <el-table-column label="考评名称" align="center" prop="evaluationName" />
      <!-- <el-table-column label="考评模板编码" align="center" prop="evaluationTemplateCode" /> -->
      <el-table-column label="考评模板名称" align="center" prop="evaluationTemplateName" />
      <el-table-column label="考评周期" align="center" prop="evaluationCycle">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.cycle" :value="scope.row.evaluationCycle" />
        </template>
      </el-table-column>
      <el-table-column label="考评日期从" align="center" prop="evaluationDateFrom" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.evaluationDateFrom, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考评日期至" align="center" prop="evaluationDateTo" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.evaluationDateTo, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考评负责人" align="center" prop="evaluationHead" />
      <el-table-column label="考评状态" align="center" prop="evaluationState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.assessment_status" :value="scope.row.evaluationState" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['supplierpms:performance:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['supplierpms:performance:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改绩效考评对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="720px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="考评名称" prop="evaluationName">
          <el-input v-model="form.evaluationName" placeholder="请输入考评名称" />
        </el-form-item>
        <el-form-item label="考评模板编码" prop="evaluationTemplateCode">
          <el-input v-model="form.evaluationTemplateCode" placeholder="请输入考评模板编码">
            <template v-slot:suffix>
              <el-popover placement="right" width="340" trigger="click">
                <el-table :data="templateList" @row-click="hanSupplierClickfrom">
                  <el-table-column width="156" property="scoringTemplateCode" label="评分模板编号"></el-table-column>
                  <el-table-column width="156" property="scoringTemplateName" label="评分模板名称"></el-table-column>
                  <el-table-column width="156" property="templateState" label="模板状态">
                    <template slot-scope="scope">
                      <dict-tag :options="dict.type.release" :value="scope.row.templateState" />
                    </template>
                  </el-table-column>
                  <el-table-column width="156" property="templateType" label="模板类型">
                    <template slot-scope="scope">
                      <dict-tag :options="dict.type.template_type" :value="scope.row.templateType" />
                    </template>
                  </el-table-column>
                </el-table>
                <i class="el-icon-search" slot="reference"></i>
              </el-popover>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="考评模板名称" prop="evaluationTemplateName">
          <el-input v-model="form.evaluationTemplateName" placeholder="请输入考评模板名称" />
        </el-form-item>
        <el-form-item label="考评方式" prop="evaluationMethod">
          <el-select v-model="form.evaluationMethod" placeholder="请选择考评方式">
            <el-option v-for="dict in dict.type.evaluation_method" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考评负责人" prop="evaluationHead">
          <el-input v-model="form.evaluationHead" placeholder="请输入考评负责人" readonly />
        </el-form-item>
        <el-form-item label="分部" prop="branch">
          <el-input v-model="form.branch" placeholder="请输入分部" />
        </el-form-item>
        <el-form-item label="部门" prop="contactsDepartment">
          <el-input v-model="form.contactsDepartment" placeholder="请输入部门" />
        </el-form-item>
        <el-form-item label="考评周期" prop="evaluationCycle">
          <el-select v-model="form.evaluationCycle" placeholder="请选择考评周期">
            <el-option v-for="dict in dict.type.cycle" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考评日期从" prop="evaluationDateFrom">
          <el-date-picker clearable v-model="form.evaluationDateFrom" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择考评日期从">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="考评日期至" prop="evaluationDateTo">
          <el-date-picker clearable v-model="form.evaluationDateTo" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择考评日期至">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="考评说明" prop="evaluationDescription">
          <el-input v-model="form.evaluationDescription" placeholder="请输入考评说明" />
        </el-form-item>
        <!-- <el-form-item label="创建时间" prop="creationTime">
          <el-date-picker clearable v-model="form.creationTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item> -->
        <!-- <el-form-item label="考评附件" prop="assessmentAttachment">
          <el-input v-model="form.assessmentAttachment" placeholder="请输入考评附件" />
        </el-form-item> -->
        <div class="menuList">
          <el-button size="mini" :class="[showidx === 1 ? 'is-active' : '']" @click="showidx = 1">参评供应商</el-button>
          <el-button size="mini" :class="[showidx === 2 ? 'is-active' : '']" @click="showidx = 2">评分明细</el-button>
          <el-button size="mini" :class="[showidx === 3 ? 'is-active' : '']" @click="showidx = 3">评分汇总</el-button>
        </div>
        <div v-if="showidx == 1">
          <el-divider content-position="center">参评供应商</el-divider>
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddAttendSupplier">添加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" icon="el-icon-delete" size="mini"
                @click="handleDeleteAttendSupplier">删除</el-button>
            </el-col>
          </el-row>
          <el-table :data="attendSupplierList" :row-class-name="rowAttendSupplierIndex"
            @selection-change="handleAttendSupplierSelectionChange" ref="attendSupplier"
            @row-click="handleSupplierSupplierListe">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="序号" align="center" prop="index" width="50" />
            <el-table-column label="供应商编码" prop="supplierCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.supplierCode" placeholder="请输入供应商编码" />
                <i class="el-icon-search" id="serachOne" @click="showMaterial()"></i>
                <el-dialog :visible.sync="dialogMaterial" title="供应商管理-浏览框" :modal="false">
                  <!-- 这里是供应商管理的内容 -->
                  <el-table v-loading="loading" :data="supplyCanList" @row-click="handleRowClickSupplierAttend">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="供应商编码" align="center" prop="sdCode" />
                    <el-table-column label="供应商名称" align="center" prop="sbiName" />
                  </el-table>
                  <pagination v-show="mtotal > 0" :total="mtotal" :page.sync="ququeryParams.pageNum"
                    :limit.sync="ququeryParams.pageSize" @pagination="getList" />
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogMaterial = false">取消</el-button>
                  </div>
                </el-dialog>
              </template>
            </el-table-column>
            <el-table-column label="供应商名称" prop="supplierName" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.supplierName" placeholder="请输入供应商名称" />
              </template>
            </el-table-column>
            <el-table-column label="品类编码" prop="categoryCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.categoryCode" placeholder="请输入品类编码" />
                <i class="el-icon-search" id="serachOne" @click="showMaterial1()"></i>
                <el-dialog :visible.sync="dialogMaterial1" title="品类管理-浏览框" :modal="false">
                  <!-- 这里是品类管理的内容 -->
                  <el-table v-loading="loading" :data="categoryList" @row-click="handleRowClickCategoryAttend">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="品类编码" align="center" prop="categoryCode" />
                    <el-table-column label="物料品类" align="center" prop="categoryName" />
                  </el-table>
                  <pagination v-show="mtotal1 > 0" :total="mtotal1" :page.sync="ququeryParams.pageNum"
                    :limit.sync="ququeryParams.pageSize" @pagination="getList" />
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogMaterial1 = false">取消</el-button>
                  </div>
                </el-dialog>
              </template>
            </el-table-column>
            <el-table-column label="品类名称" prop="categoryName" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.categoryName" placeholder="请输入品类名称" />
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div v-if="showidx == 2">
          <el-divider content-position="center">评分明细信息</el-divider>
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddScoringDetails">添加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" icon="el-icon-delete" size="mini"
                @click="handleDeleteScoringDetails">删除</el-button>
            </el-col>
          </el-row>
          <el-table :data="scoringDetailsList" :row-class-name="rowScoringDetailsIndex"
            @selection-change="handleScoringDetailsSelectionChange" ref="scoringDetails"
            @row-click="handleSupplierCategoryListe">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="序号" align="center" prop="index" width="50" />
            <el-table-column label="供应商编码" prop="supplierCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.supplierCode" placeholder="请输入供应商编码" />
                <i class="el-icon-search" id="serachOne" @click="showMaterial()"></i>
                <el-dialog :visible.sync="dialogMaterial" title="供应商管理-浏览框" :modal="false">
                  <!-- 这里是供应商管理的内容 -->
                  <el-table v-loading="loading" :data="attendSupplierList" @row-click="handleRowClickSupplierScorin">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="供应商编码" align="center" prop="supplierCode" />
                    <el-table-column label="供应商名称" align="center" prop="supplierName" />
                  </el-table>
                  <pagination v-show="mtotal > 0" :total="mtotal" :page.sync="ququeryParams.pageNum"
                    :limit.sync="ququeryParams.pageSize" @pagination="getList" />
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogMaterial = false">取消</el-button>
                  </div>
                </el-dialog>
              </template>
            </el-table-column>
            <el-table-column label="供应商名称" prop="supplierName" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.supplierName" placeholder="请输入供应商名称" />
              </template>
            </el-table-column>
            <el-table-column label="品类编码" prop="categoryCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.categoryCode" placeholder="请输入品类编码" />
                <i class="el-icon-search" id="serachOne" @click="showMaterial1()"></i>
                <el-dialog :visible.sync="dialogMaterial1" title="品类管理-浏览框" :modal="false">
                  <!-- 这里是品类管理的内容 -->
                  <el-table v-loading="loading" :data="categoryList" @row-click="handleRowClickCategoryscoring">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="品类编码" align="center" prop="categoryCode" />
                    <el-table-column label="物料品类" align="center" prop="categoryName" />
                  </el-table>
                  <pagination v-show="mtotal1 > 0" :total="mtotal1" :page.sync="ququeryParams.pageNum"
                    :limit.sync="ququeryParams.pageSize" @pagination="getList" />
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogMaterial1 = false">取消</el-button>
                  </div>
                </el-dialog>
              </template>
            </el-table-column>
            <el-table-column label="品类名称" prop="categoryName" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.categoryName" placeholder="请输入品类名称" />
              </template>
            </el-table-column>
            <el-table-column label="指标描述" prop="indicatorDescription" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.indicatorDescription" placeholder="请输入指标描述" />
              </template>
            </el-table-column>
            <el-table-column label="指标编码" prop="indicatorCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.indicatorCode" placeholder="请输入指标编码" />
              </template>
            </el-table-column>
            <el-table-column label="评分标准" prop="scoringCriteria" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.scoringCriteria" placeholder="请输入评分标准" />
              </template>
            </el-table-column>
            <el-table-column label="权重(%)" prop="weight" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.weight" placeholder="请输入权重(%)" />
              </template>
            </el-table-column>
            <el-table-column label="评分方式" prop="scoringMethod" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.scoringMethod" placeholder="请输入评分方式" />
              </template>
            </el-table-column>
            <el-table-column label="评分人" prop="rater" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.rater" placeholder="请输入评分人" readonly />
              </template>
            </el-table-column>
            <el-table-column label="指标类型" prop="indicatorType" width="150">
              <template slot-scope="scope">
                <el-select v-model="scope.row.indicatorType" placeholder="请选择指标类型">
                  <el-option v-for="dict in dict.type.indicator_type" :key="dict.value" :label="dict.label"
                    :value="dict.value"></el-option>
                </el-select>
              </template>
            </el-table-column>
            <!-- <el-table-column label="评分状态" prop="ratingStatus" width="150">
              <template slot-scope="scope">
                <el-select v-model="scope.row.ratingStatus" placeholder="请选择评分状态">
                  <el-option v-for="dict in dict.type.rating_status" :key="dict.value" :label="dict.label"
                    :value="dict.value"></el-option>
                </el-select>
              </template>
            </el-table-column> -->
            <el-table-column label="得分" prop="score" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.score" placeholder="请输入得分" />
              </template>
            </el-table-column>
            <el-table-column label="选择项" prop="selectivity" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.selectivity" placeholder="请输入选择项" />
              </template>
            </el-table-column>
            <el-table-column label="分值从" prop="scoreFrom" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.scoreFrom" placeholder="请输入分值从" />
              </template>
            </el-table-column>
            <el-table-column label="分值至" prop="scoreTo" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.scoreTo" placeholder="请输入分值至" />
              </template>
            </el-table-column>
            <el-table-column label="反馈备注" prop="feedback" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.feedback" placeholder="请输入反馈备注" />
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div v-if="showidx == 3">
          <el-divider content-position="center">参评供应商</el-divider>
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddAttendSupplier">添加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" icon="el-icon-delete" size="mini"
                @click="handleDeleteAttendSupplier">删除</el-button>
            </el-col>
          </el-row>
          <el-table :data="attendSupplierList" :row-class-name="rowAttendSupplierIndex"
            @selection-change="handleAttendSupplierSelectionChange" ref="attendSupplier"
            @row-click="handleSupplierSupplierListe">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="序号" align="center" prop="index" width="50" />
            <el-table-column label="供应商编码" prop="supplierCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.supplierCode" placeholder="请输入供应商编码" />
                <i class="el-icon-search" id="serachOne" @click="showMaterial()"></i>
                <el-dialog :visible.sync="dialogMaterial" title="供应商管理-浏览框" :modal="false">
                  <!-- 这里是供应商管理的内容 -->
                  <el-table v-loading="loading" :data="supplyCanList" @row-click="handleRowClickSupplierAttend">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="供应商编码" align="center" prop="sdCode" />
                    <el-table-column label="供应商名称" align="center" prop="sbiName" />
                  </el-table>
                  <pagination v-show="mtotal > 0" :total="mtotal" :page.sync="ququeryParams.pageNum"
                    :limit.sync="ququeryParams.pageSize" @pagination="getList" />
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogMaterial = false">取消</el-button>
                  </div>
                </el-dialog>
              </template>
            </el-table-column>
            <el-table-column label="供应商名称" prop="supplierName" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.supplierName" placeholder="请输入供应商名称" />
              </template>
            </el-table-column>
            <el-table-column label="品类编码" prop="categoryCode" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.categoryCode" placeholder="请输入品类编码" />
                <i class="el-icon-search" id="serachOne" @click="showMaterial1()"></i>
                <el-dialog :visible.sync="dialogMaterial1" title="品类管理-浏览框" :modal="false">
                  <!-- 这里是品类管理的内容 -->
                  <el-table v-loading="loading" :data="categoryList" @row-click="handleRowClickCategoryAttend">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="品类编码" align="center" prop="categoryCode" />
                    <el-table-column label="物料品类" align="center" prop="categoryName" />
                  </el-table>
                  <pagination v-show="mtotal1 > 0" :total="mtotal1" :page.sync="ququeryParams.pageNum"
                    :limit.sync="ququeryParams.pageSize" @pagination="getList" />
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogMaterial1 = false">取消</el-button>
                  </div>
                </el-dialog>
              </template>
            </el-table-column>
            <el-table-column label="品类名称" prop="categoryName" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.categoryName" placeholder="请输入品类名称" />
              </template>
            </el-table-column>
            <el-table-column label="汇总得分" prop="summaryScore" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.summaryScore" placeholder="请输入汇总得分" />
              </template>
            </el-table-column>
            <el-table-column label="得分" prop="score" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.score" placeholder="请输入得分" />
              </template>
            </el-table-column>
            <!-- <el-table-column label="等级" prop="grade" width="150">
              <template slot-scope="scope">
                <el-select v-model="scope.row.grade" placeholder="请选择等级">
                  <el-option v-for="dict in dict.type.grade" :key="dict.value" :label="dict.label"
                    :value="dict.value"></el-option>
                </el-select>
              </template>
            </el-table-column> -->
            <el-table-column label="考评排名" prop="evaluationRanking" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.evaluationRanking" placeholder="请输入考评排名" />
              </template>
            </el-table-column>
            <el-table-column label="建议策略" prop="strategy" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.strategy" placeholder="请输入建议策略" />
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="form.evaluationState == 2" @click="InterviewReview">审核</el-button>
        <el-button type="primary" @click="submitForm" v-if="form.evaluationState == 1">确 定</el-button>
        <el-button type="primary" @click="submitForm" v-if="form.evaluationState == 2">确 定</el-button>
        <!-- <el-button type="primary" @click="submitForm" v-if="form.evaluationId != null">确 定</el-button> -->
        <el-button type="primary" @click="saveForm" v-if="form.evaluationId == null">保 存</el-button>
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
import { listPerformance, getPerformance, delPerformance, addPerformance, updatePerformance, listCategory } from "@/api/supplierpms/performance";
import { canSupplier } from "@/api/supplierpms/details";
import { listTemplate } from "@/api/supplierpms/template";
export default {
  name: "Performance",
  dicts: ['cycle', 'assessment_status', 'indicator_type', 'rating_status', 'grade', 'evaluation_method', 'template_type', 'indicator_type', 'rating_status', 'release'],
  data() {
    return {
      dialogVisible: false,
      templateList: [],
      deliveryIndex: null,
      dialogMaterial: false,
      dialogMaterial1: false,
      showidx: 1,
      mtotal: 0,
      mtotal1: 0,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 参评供应商表选中数据
      checkedAttendSupplier: [],
      // 评分明细表选中数据
      checkedScoringDetails: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 品类
      categoryList: [],
      // 供应商数据
      supplyCanList: [],
      // 总条数
      total: 0,
      // 绩效考评表格数据
      performanceList: [],
      // 参评供应商表格数据
      attendSupplierList: [],
      // 评分明细表格数据
      scoringDetailsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        evaluationCode: null,
        evaluationName: null,
        evaluationState: null,
        evaluationTemplateCode: null,
        evaluationTemplateName: null,
        evaluationMethod: null,
        evaluationHead: null,
        branch: null,
        contactsDepartment: null,
        evaluationCycle: null,
        evaluationDateFrom: null,
        evaluationDateTo: null,
        evaluationDescription: null,
        creationTime: null,
        assessmentAttachment: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      ququeryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      userName: null,
    };
  },
  created() {
    this.getList();
    this.getCompanies();
    this.userName = this.$store.state.user.name;
  },
  methods: {
    /** 查询绩效考评列表 */
    getList() {
      this.loading = true;
      listPerformance(this.queryParams).then(response => {
        this.performanceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listTemplate().then(response => {
        console.log(response.rows);
        this.templateList = response.rows;
      });
    },
    /**审核按钮 */
    InterviewReview() {
      this.dialogVisible = true
    },
    /** 查询品类和供应商数据 */
    getCompanies() {
      listCategory().then(response => {
        this.categoryList = response.rows;
        this.mtotal1 = response.total;
      });
      canSupplier().then(response => {
        this.supplyCanList = response.rows;
        this.mtotal = response.total;
      });
    },
    review(determine) {
      this.dialogVisible = false;
      if (determine) {
        this.form.evaluationState = 3
        updatePerformance(this.form).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        });
      } else {
        this.form.evaluationState = 4
        updatePerformance(this.form).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        });
      }
    },
    /**点击放大镜 */
    showMaterial1() {
      this.dialogMaterial1 = true;
    },
    /**点击放大镜 */
    showMaterial() {
      this.dialogMaterial = true;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        evaluationId: null,
        evaluationCode: null,
        evaluationName: null,
        evaluationState: null,
        evaluationTemplateCode: null,
        evaluationTemplateName: null,
        evaluationMethod: null,
        evaluationHead: null,
        branch: null,
        contactsDepartment: null,
        evaluationCycle: null,
        evaluationDateFrom: null,
        evaluationDateTo: null,
        evaluationDescription: null,
        creationTime: null,
        assessmentAttachment: null
      };
      this.attendSupplierList = [];
      this.scoringDetailsList = [];
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
      this.ids = selection.map(item => item.evaluationId)
      this.single = selection.length !== 1
      this.multiple = !selection.length

    },
    hanSupplierClickfrom(row) {
      this.form.evaluationTemplateId = row.scoringTemplateId
      this.form.evaluationTemplateCode = row.scoringTemplateCode
      this.form.evaluationTemplateName = row.scoringTemplateName
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.evaluationHead = this.userName;
      this.open = true;
      this.title = "添加绩效考评";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const evaluationId = row.evaluationId || this.ids
      getPerformance(evaluationId).then(response => {
        this.form = response.data;
        this.form.evaluationHead = this.userName;
        console.log(response.data);
        this.attendSupplierList = response.data.attendSupplierList;
        this.scoringDetailsList = response.data.scoringDetailsList
        this.open = true;
        this.title = "修改绩效考评";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log(this.attendSupplierList);
          console.log(this.scoringDetailsList);
          this.form.attendSupplierList = this.attendSupplierList;
          this.form.scoringDetailsList = this.scoringDetailsList;
          updatePerformance(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log(this.attendSupplierList);
          console.log(this.scoringDetailsList);
          this.form.attendSupplierList = this.attendSupplierList;
          this.form.scoringDetailsList = this.scoringDetailsList;
          addPerformance(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const evaluationIds = row.evaluationId || this.ids;
      this.$modal.confirm('是否确认删除绩效考评编号为"' + evaluationIds + '"的数据项？').then(function () {
        return delPerformance(evaluationIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 参评供应商序号 */
    rowAttendSupplierIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    rowScoringDetailsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 参评供应商添加按钮操作 */
    handleAddAttendSupplier() {
      let obj = {};
      obj.supplierCode = "";
      obj.supplierName = "";
      obj.categoryCode = "";
      obj.categoryName = "";
      obj.summaryScore = "";
      obj.score = "";
      obj.grade = "";
      obj.evaluationRanking = "";
      obj.strategy = "";
      this.attendSupplierList.push(obj);
    },
    /** 复选框选中数据 */
    handleAttendSupplierSelectionChange(selection) {
      this.checkedAttendSupplier = selection.map(item => item.index)
      this.checkedScoringDetails = selection.map(item => item.index)
    },
    /** 复选框选中数据 */
    handleScoringDetailsSelectionChange(selection) {
      this.checkedAttendSupplier = selection.map(item => item.index)
      this.checkedScoringDetails = selection.map(item => item.index)
    },
    /** 评分明细添加按钮操作 */
    handleAddScoringDetails() {
      let obj = {};
      obj.supplierCode = "";
      obj.supplierName = "";
      obj.categoryCode = "";
      obj.categoryName = "";
      obj.indicatorDescription = "";
      obj.indicatorCode = "";
      obj.scoringCriteria = "";
      obj.weight = "";
      obj.scoringMethod = "";
      obj.rater = this.userName;
      obj.indicatorType = "";
      obj.ratingStatus = "";
      obj.score = "";
      obj.selectivity = "";
      obj.scoreFrom = "";
      obj.scoreTo = "";
      obj.feedback = "";
      this.scoringDetailsList.push(obj);
    },
    /** 评分明细删除按钮操作 */
    handleDeleteScoringDetails() {
      if (this.checkedScoringDetails.length == 0) {
        this.$modal.msgError("请先选择要删除的评分明细数据");
      } else {
        const scoringDetailsList = this.scoringDetailsList;
        const checkedScoringDetails = this.checkedScoringDetails;
        this.scoringDetailsList = scoringDetailsList.filter(function (item) {
          return checkedScoringDetails.indexOf(item.index) == -1
        });
      }
    },
    /** 参评供应商删除按钮操作 */
    handleDeleteAttendSupplier() {
      if (this.checkedAttendSupplier.length == 0) {
        this.$modal.msgError("请先选择要删除的参评供应商数据");
      } else {
        const attendSupplierList = this.attendSupplierList;
        const checkedAttendSupplier = this.checkedAttendSupplier;
        this.attendSupplierList = attendSupplierList.filter(function (item) {
          return checkedAttendSupplier.indexOf(item.index) == -1
        });
      }
    },
    /**审核按钮 */
    handleExamine() {

    },
    /** 物料添加 */
    handleSupplierSupplierListe(row) {
      this.deliveryIndex = this.attendSupplierList.indexOf(row); // 获取点击的行索引
    },
    /** 物料添加 */
    handleSupplierCategoryListe(row) {
      this.deliveryIndex = this.scoringDetailsList.indexOf(row); // 获取点击的行索引
    },
    // 物料框架+数据
    handleRowClickSupplierAttend(row) {
      this.attendSupplierList[this.deliveryIndex].supplierCode = row.sdCode;// 物料编码
      this.attendSupplierList[this.deliveryIndex].supplierName = row.sbiName;// 物料名称
      // this.scoringDetailsList[this.deliveryIndex].supplierCode = row.sdCode;// 物料编码
      // this.scoringDetailsList[this.deliveryIndex].supplierName = row.sbiName;// 物料名称
      this.dialogMaterial = false; // 关闭对话框
    },
    // 物料框架+数据
    handleRowClickSupplierScorin(row) {
      // this.attendSupplierList[this.deliveryIndex].supplierCode = row.sdCode;// 物料编码
      // this.attendSupplierList[this.deliveryIndex].supplierName = row.sbiName;// 物料名称
      this.scoringDetailsList[this.deliveryIndex].supplierCode = row.supplierCode;// 物料编码
      this.scoringDetailsList[this.deliveryIndex].supplierName = row.supplierName;// 物料名称
      this.dialogMaterial = false; // 关闭对话框
    },
    handleRowClickCategoryAttend(row) {
      this.attendSupplierList[this.deliveryIndex].categoryCode = row.categoryCode;// 品类
      this.attendSupplierList[this.deliveryIndex].categoryName = row.categoryName;// 物料品类
      // this.scoringDetailsList[this.deliveryIndex].categoryCode = row.categoryCode;// 品类
      // this.scoringDetailsList[this.deliveryIndex].categoryName = row.categoryName;// 物料品类
      this.dialogMaterial1 = false; // 关闭对话框
    },
    handleRowClickCategoryscoring(row) {
      // this.attendSupplierList[this.deliveryIndex].categoryCode = row.categoryCode;// 品类
      // this.attendSupplierList[this.deliveryIndex].categoryName = row.categoryName;// 物料品类
      this.scoringDetailsList[this.deliveryIndex].categoryCode = row.categoryCode;// 品类
      this.scoringDetailsList[this.deliveryIndex].categoryName = row.categoryName;// 物料品类
      this.dialogMaterial1 = false; // 关闭对话框
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('supplierpms/performance/export', {
        ...this.queryParams
      }, `performance_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
.menuList {
  width: 480px;
  height: 42px;
}

#serachOne {
  position: absolute;
  right: 15px;
  top: 21.5px;
}
</style>
