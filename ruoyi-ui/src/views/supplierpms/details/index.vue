<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="邀请时间" prop="sdTime">
        <el-date-picker clearable v-model="queryParams.sdTime" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择邀请时间">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="供应商名称" prop="sbiName">
        <el-input v-model="queryParams.sbiName" placeholder="请输入供应商名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="注册状态" prop="sdStatus">
        <el-select v-model="queryParams.sdStatus" placeholder="请选择注册状态" clearable>
          <el-option v-for="dict in dict.type.registration" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="注册时间" prop="sdRt">
        <el-date-picker clearable v-model="queryParams.sdRt" type="date" value-format="yyyy-MM-dd" placeholder="请选择注册时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 展示信息 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['supplierpms:supplier:add']">新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['supplierpms:supplier:edit']">修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['supplierpms:supplier:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['supplierpms:supplier:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="邀请时间" align="center" prop="sdTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sdTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="供应商名称" align="center" prop="sbiName" />
      <el-table-column label="工商注册号" align="center" prop="sdUscc" />
      <el-table-column label="行业" align="center" prop="sdIndustry" />
      <el-table-column label="服务范围" align="center" prop="sbiServe">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.service_scope" :value="scope.row.sbiServe ? scope.row.sbiServe.split(',') : []" />
        </template>
      </el-table-column>
      <el-table-column label="注册时间" align="center" prop="sdRt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sdRt, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="注册状态" align="center" prop="sdStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.registration" :value="scope.row.sdStatus" />
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="sdHead" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">

          <el-button v-if="scope.row.sdStatus === '0'" size="mini" type="text" @click="authentication(scope.row)"
            icon="el-icon-coordinate" v-hasPermi="['supplierpms:supplier:remove']">企业认证</el-button>
          <!-- <el-button v-if="scope.row.sdStatus === '0'" type="text" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['supplierpms:details:edit']">修改</el-button> -->
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['supplierpms:supplier:remove']">删除</el-button>

        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改供应商详细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="邀请时间" prop="sdTime">
          <el-date-picker clearable v-model="form.sdTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择邀请时间"
            disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="供应商详细编码" prop="sdCode">
          <el-input v-model="form.sdCode" placeholder="请输入供应商详细编码" disabled />
        </el-form-item>
        <el-form-item label="供应商名称" prop="sbiName">
          <el-input v-model="form.sbiName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="简称" prop="sbiAbbreviation">
          <el-input v-model="form.sbiAbbreviation" placeholder="请输入简称" />
        </el-form-item>
        <el-form-item label="服务范围" prop="sbiServe">
          <el-checkbox-group v-model="form.sbiServe">
            <el-checkbox v-for="dict in dict.type.service_scope" :key="dict.value" :label="dict.value">
              {{ dict.label }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="行业" prop="sdIndustry">
          <el-input v-model="form.sdIndustry" placeholder="请输入行业" />
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="sdUscc">
          <el-input v-model="form.sdUscc" placeholder="请输入统一社会信用代码" />
        </el-form-item>
        <el-form-item label="负责人" prop="sdHead">
          <el-input v-model="form.sdHead" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="负责人手机" prop="sdHeadPhone">
          <el-input v-model="form.sdHeadPhone" placeholder="请输入负责人手机" />
        </el-form-item>
        <el-form-item label="企业性质" prop="sdEn">
          <el-input v-model="form.sdEn" placeholder="请输入企业性质" />
        </el-form-item>
        <el-form-item label="主要联系人姓名" prop="sdPcn">
          <el-input v-model="form.sdPcn" placeholder="请输入主要联系人姓名" />
        </el-form-item>
        <el-form-item label="主要联系人邮箱" prop="sdPce">
          <el-input v-model="form.sdPce" placeholder="请输入主要联系人邮箱" />
        </el-form-item>
        <el-form-item label="主要联系人手机号" prop="sdPcp">
          <el-input v-model="form.sdPcp" placeholder="请输入主要联系人手机号" />
        </el-form-item>
        <el-link type="info" @click="toggleDropdown" class="dropbtn" style="margin-bottom: 20px">更多信息(选填点我)</el-link>
        <div v-show="dropdownOpen" class="dropdown-content">
          <!-- <ul>
            <li v-for="item in tableData" :key="item.name">{{ item.name }}</li>
          </ul> -->
          <el-form-item label="主要联系人性别" prop="sdPcg">
            <el-input v-model="form.sdPcg" placeholder="请输入主要联系人性别" />
          </el-form-item>
          <el-form-item label="主要联系人职务" prop="sdMct">
            <el-input v-model="form.sdMct" placeholder="请输入主要联系人职务" />
          </el-form-item>
          <el-form-item label="国家地区" prop="sdCountry">
            <el-input v-model="form.sdCountry" placeholder="请输入国家地区" />
          </el-form-item>
          <el-form-item label="所在区域" prop="sdRegion">
            <el-input v-model="form.sdRegion" placeholder="请输入所在区域" />
          </el-form-item>
          <el-form-item label="网址" prop="sdWebsite">
            <el-input v-model="form.sdWebsite" placeholder="请输入网址" />
          </el-form-item>
          <el-form-item label="简介" prop="sdBrief">
            <el-input v-model="form.sdBrief" placeholder="请输入简介" />
          </el-form-item>
          <el-form-item label="法定代表" prop="sdLegal">
            <el-input v-model="form.sdLegal" placeholder="请输入法定代表" />
          </el-form-item>
          <el-form-item label="注册信息" prop="sdRi">
            <el-input v-model="form.sdRi" placeholder="请输入注册信息" />
          </el-form-item>
          <el-form-item label="工商注册号" prop="sdBr">
            <el-input v-model="form.sdBr" placeholder="请输入工商注册号" />
          </el-form-item>
          <el-form-item label="注册资本" prop="sdRc">
            <el-input v-model="form.sdRc" placeholder="请输入注册资本" />
          </el-form-item>
          <el-form-item label="注册时间" prop="sdRt">
            <el-date-picker clearable v-model="form.sdRt" type="date" value-format="yyyy-MM-dd" placeholder="请选择注册时间">
            </el-date-picker>
          </el-form-item>
          <!-- <el-form-item label="注册状态" prop="sdStatus">
            <el-radio-group v-model="form.sdStatus">
              <el-radio v-for="dict in dict.type.registration_status" :key="dict.value" :label="dict.value">{{ dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item> -->
          <el-form-item label="单位电话" prop="sdPhone">
            <el-input v-model="form.sdPhone" placeholder="请输入单位电话" />
          </el-form-item>
          <el-form-item label="单位邮箱" prop="sdEmail">
            <el-input v-model="form.sdEmail" placeholder="请输入单位邮箱" />
          </el-form-item>
          <el-form-item label="传真" prop="sdFax">
            <el-input v-model="form.sdFax" placeholder="请输入传真" />
          </el-form-item>
          <el-form-item label="省" prop="sdSave">
            <el-input v-model="form.sdSave" placeholder="请输入省" />
          </el-form-item>
          <el-form-item label="市" prop="sdCity">
            <el-input v-model="form.sdCity" placeholder="请输入市" />
          </el-form-item>
          <el-form-item label="区(县)" prop="sdCounty">
            <el-input v-model="form.sdCounty" placeholder="请输入区(县)" />
          </el-form-item>
          <el-form-item label="结算方式" prop="sdSm">
            <el-input v-model="form.sdSm" placeholder="请输入结算方式" />
          </el-form-item>
          <el-form-item label="开户行" prop="sdBank">
            <el-input v-model="form.sdBank" placeholder="请输入开户行" />
          </el-form-item>
          <el-form-item label="币种" prop="sdCurrency">
            <el-input v-model="form.sdCurrency" placeholder="请输入币种" />
          </el-form-item>
          <el-form-item label="账号" prop="sdAccount">
            <el-input v-model="form.sdAccount" placeholder="请输入账号" />
          </el-form-item>
          <!-- <el-form-item label="证件编码" prop="idCode">
            <el-input v-model="form.idCode" placeholder="请输入证件编码" />
          </el-form-item>
          <el-form-item label="证件名称" prop="certificateName">
            <el-input v-model="form.certificateName" placeholder="请输入证件名称" />
          </el-form-item> -->
          <!-- <el-form-item label="存档日期" prop="filingDate">
            <el-date-picker clearable v-model="form.filingDate" type="date" value-format="yyyy-MM-dd"
              placeholder="请选择存档日期">
            </el-date-picker>
          </el-form-item> -->
          <!-- <el-form-item label="营业执照" prop="filing">
            <image-upload v-model="form.filing" />
          </el-form-item> -->
          <!-- <el-form-item label="数据状态" prop="dataTatus">
            <el-input v-model="form.dataTatus" placeholder="请输入数据状态" />
          </el-form-item> -->
        </div>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <!-- <el-button type="primary" >企业认证</el-button> -->
        <!-- <el-button @click=" drawer = true" type="primary" style="margin-left: 16px">
          企业认证
        </el-button> -->
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-drawer title="企业认证" :visible.sync="drawer" :with-header="false">
      <div class="your-container">
        <el-form id="dd" :rules="rules" ref="form" :model="form" label-width="120px">
          <el-form-item label="企业名称">
            <el-input v-model="form.sbiName"></el-input>
          </el-form-item>
          <el-form-item label="联系人">
            <el-input v-model="form.sdPcn"></el-input>
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="form.sdPcp"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="form.sdPce"></el-input>
          </el-form-item>
          <!-- <el-form-item label="文件" prop="filing">
            <file-upload v-model="form.filing" />
          </el-form-item> -->
          <!-- <el-table-column label="营业执照" align="center" prop="filing" width="100">
            <template slot-scope="scope">
              <image-preview :src="scope.row.filing" :width="50" :height="50" />
            </template>
          </el-table-column> -->
          <el-form-item label="营业执照" prop="filing">
            <image-upload v-model="form.filing" />
          </el-form-item>
          <!-- <el-form-item label="营业执照">
            <el-upload action="/api/upload" :on-success="handleUploadSuccess" :before-upload="beforeUpload"
              :file-list="fileList" list-type="picture-card" :limit="1">
              <i class="el-icon-plus"></i>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2MB</div>
            </el-upload>
          </el-form-item> -->
          <el-form-item>
            <el-button type="primary" @click="authentications">提交认证</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {
  listDetails,
  getDetails,
  delDetails,
  addDetails,
  updateDetails,
} from "@/api/supplierpms/details";

export default {
  name: "Supplier",
  dicts: ["service_scope", "registration"],
  data() {
    return {
      //展示
      drawer: false,
      //隐藏数据
      dropdownOpen: false,
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
      // 供应商详细表格数据
      supplierList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sdTime: null,
        sdCode: null,
        sbiName: null,
        sbiAbbreviation: null,
        sbiServe: null,
        sdIndustry: null,
        sdCountry: null,
        sdRegion: null,
        sdWebsite: null,
        sdBrief: null,
        sdLegal: null,
        sdRi: null,
        sdBr: null,
        sdRc: null,
        sdRt: null,
        sdStatus: null,
        sdPhone: null,
        sdEmail: null,
        sdFax: null,
        sdSave: null,
        sdCity: null,
        sdCounty: null,
        sdHead: null,
        sdHeadPhone: null,
        sdUscc: null,
        sdEn: null,
        sdPcn: null,
        sdPcg: null,
        sdMct: null,
        sdPce: null,
        sdPcp: null,
        sdSm: null,
        sdBank: null,
        sdCurrency: null,
        sdAccount: null,
        idCode: null,
        certificateName: null,
        filingDate: null,
        filing: null,
        dataTatus: null,
      },
      fileList: [],
      // 表单参数
      form: {
        companyName: '',
        contact: '',
        phone: '',
        email: '',
        businessLicense: ''
      },

      // 表单校验
      rules: {
        // sdTime: [
        //   { required: true, message: "邀请时间不能为空", trigger: "blur" },
        // ],
        // sdCode: [
        //   { required: true, message: "供应商详细编码不能为空", trigger: "blur" }
        // ],
        sbiName: [
          { required: true, message: "供应商名称不能为空", trigger: "blur" },
        ],
        sbiServe: [
          { required: true, message: "服务范围不能为空", trigger: "blur" },
        ],
        sdIndustry: [
          { required: true, message: "行业不能为空", trigger: "blur" },
        ],
        sdHead: [
          { required: true, message: "负责人不能为空", trigger: "blur" },
        ],
        sdHeadPhone: [
          { required: true, message: "负责人手机不能为空", trigger: "blur" },
        ],
        sdUscc: [
          {
            required: true,
            message: "统一社会信用代码不能为空",
            trigger: "blur",
          },
        ],
        sdEn: [
          { required: true, message: "企业性质不能为空", trigger: "blur" },
        ],
        sdPcn: [
          {
            required: true,
            message: "主要联系人姓名不能为空",
            trigger: "blur",
          },
        ],
        sdPce: [
          {
            required: true,
            message: "主要联系人邮箱不能为空",
            trigger: "blur",
          },
        ],
        sdPcp: [
          {
            required: true,
            message: "主要联系人手机号不能为空",
            trigger: "blur",
          },
        ],
        filing: [
          { required: true, message: "文件不能为空", trigger: "blur" }
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 隐藏数据按钮方法
    toggleDropdown() {
      this.dropdownOpen = !this.dropdownOpen;
    },
    /** 查询供应商详细列表 */
    getList() {
      this.loading = true;
      listDetails(this.queryParams).then((response) => {
        this.supplierList = response.rows;
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
        sdId: null,
        sdTime: null,
        sdCode: null,
        sbiName: null,
        sbiAbbreviation: null,
        sbiServe: [],
        sdIndustry: null,
        sdCountry: null,
        sdRegion: null,
        sdWebsite: null,
        sdBrief: null,
        sdLegal: null,
        sdRi: null,
        sdBr: null,
        sdRc: null,
        sdRt: null,
        sdStatus: null,
        sdPhone: null,
        sdEmail: null,
        sdFax: null,
        sdSave: null,
        sdCity: null,
        sdCounty: null,
        sdHead: null,
        sdHeadPhone: null,
        sdUscc: null,
        sdEn: null,
        sdPcn: null,
        sdPcg: null,
        sdMct: null,
        sdPce: null,
        sdPcp: null,
        sdSm: null,
        sdBank: null,
        sdCurrency: null,
        sdAccount: null,
        idCode: null,
        certificateName: null,
        filingDate: null,
        filing: null,
        dataTatus: null,
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
      this.ids = selection.map((item) => item.sdId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加供应商详细";
    },
    /** 修改按钮操作 */
    // handleUpdate(row) {
    //   this.reset();
    //   const sdId = row.sdId || this.ids;
    //   getDetails(sdId).then((response) => {
    //     this.form = response.data;
    //     this.form.sbiServe = this.form.sbiServe.split(",");
    //     this.open = true;
    //     this.title = "修改供应商详细";
    //   });
    // },
    /** 企业认证按钮 */
    authentication(row) {
      // this.$refs["companyForm"].validate((valid) => {});
      this.drawer = true;
      this.reset();
      const sdId = row.sdId || this.ids;
      //this.form.sdId=row.sdId;
      getDetails(sdId).then((response) => {
        this.form = response.data;
      });
    },
    authentications() {
      this.$refs["form"].validate((valid) => {
        console.log("ggg"+valid);
        if (valid) {
          this.form.sdStatus="1";
          this.form.sdRt = new Date();
          updateDetails(this.form).then((response) => {
            this.$modal.msgSuccess("提交成功");
            this.drawer = false;
            this.getList();
          });
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.sbiServe = this.form.sbiServe.join(",");
          if (this.form.sdId != null) {
            updateDetails(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDetails(this.form).then((response) => {
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
      const sdIds = row.sdId || this.ids;
      this.$modal
        .confirm('是否确认删除供应商详细编号为"' + sdIds + '"的数据项？')
        .then(function () {
          return delDetails(sdIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "supplierpms/supplier/export",
        {
          ...this.queryParams,
        },
        `supplier_${new Date().getTime()}.xlsx`
      );
    },
    beforeUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!(isJPG || isPNG)) {
        this.$message.error('只能上传jpg/png文件');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('文件大小不能超过2MB');
        return false;
      }
      return true;
    },
    handleUploadSuccess(response, file) {
      this.form.businessLicense = response.url;
    },
    submitCertification() {
      if (this.form.sdId != null) {
        updateDetails(this.form).then((response) => {
          this.$modal.msgSuccess("修改成功");
          this.open = false;
          this.getList();
        });
      }
    }
    // submitForm() {
    //   this.$refs.companyForm.validate(valid => {
    //     if (valid) {
    //       // 发送认证请求
    //       console.log(this.form);
    //     } else {
    //       console.log('表单验证失败');
    //     }
    //   });
    // }
  },
};
</script>
