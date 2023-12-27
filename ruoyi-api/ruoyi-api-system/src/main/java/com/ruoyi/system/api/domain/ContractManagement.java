package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 采购合同管理对象 contract_management
 *
 * @author ruoyi
 * @date 2023-12-14
 */
public class ContractManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合同管理ID */
    private Long contractManagementId;

    /** 合同名称 */
    @Excel(name = "合同名称")
    private String contractName;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractCode;

    /** 项目相关合同 */
    @Excel(name = "项目相关合同")
    private String projectRelatedContracts;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applicant;

    /** 申请日期 */
    @Excel(name = "申请日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applicationDate;

    /** 申请部门 */
    @Excel(name = "申请部门")
    private String department;

    /** 申请公司 */
    @Excel(name = "申请公司")
    private String companies;

    /** 相关项目 */
    @Excel(name = "相关项目")
    private String relatedProjects;

    /** 是否主子合同 */
    @Excel(name = "是否主子合同")
    private Long isMainContract;

    /** 关联主合同 */
    @Excel(name = "关联主合同")
    private String relatedMainContract;

    /** 预算相关合同 */
    @Excel(name = "预算相关合同")
    private String budgetRelatedContracts;

    /** 成本中心 */
    @Excel(name = "成本中心")
    private String costCenter;

    /** 负责人 */
    @Excel(name = "负责人")
    private String head;

    /** 签订日期 */
    @Excel(name = "签订日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signingDate;

    /** 付款比例(%) */
    @Excel(name = "付款比例(%)")
    private BigDecimal paymentProportion;

    /** 合同状态ID */
    @Excel(name = "合同状态ID")
    private Long contractstatusId;

    /** 供应商 */
    @Excel(name = "供应商")
    private Long sdId;

    /** 项目总预算 */
    @Excel(name = "项目总预算")
    private BigDecimal totalProjectBudget;

    /** 项目可用预算 */
    @Excel(name = "项目可用预算")
    private Long projectAvailableBudget;

    /** 类型 */
    @Excel(name = "类型")
    private Long contractType;

    /** 密集 */
    @Excel(name = "密集")
    private String dense;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;

    /** 结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 合同金额 */
    @Excel(name = "合同金额")
    private BigDecimal contractPrice;

    /** 大写 */
    @Excel(name = "大写")
    private String capitalization;

    /** 印花税金额 */
    @Excel(name = "印花税金额")
    private String stampDutyAmount;

    /** 合同情况说明 */
    @Excel(name = "合同情况说明")
    private String contractDescription;

    /** 采购清单 */
    @Excel(name = "采购清单")
    private String purchasingList;

    /** 采购订单号 */
    @Excel(name = "采购订单号")
    private String purchaseOrderCode;

    public void setContractManagementId(Long contractManagementId)
    {
        this.contractManagementId = contractManagementId;
    }

    public Long getContractManagementId()
    {
        return contractManagementId;
    }
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }

    public String getContractName()
    {
        return contractName;
    }
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }

    public String getContractCode()
    {
        return contractCode;
    }
    public void setProjectRelatedContracts(String projectRelatedContracts)
    {
        this.projectRelatedContracts = projectRelatedContracts;
    }

    public String getProjectRelatedContracts()
    {
        return projectRelatedContracts;
    }
    public void setApplicant(String applicant)
    {
        this.applicant = applicant;
    }

    public String getApplicant()
    {
        return applicant;
    }
    public void setApplicationDate(Date applicationDate)
    {
        this.applicationDate = applicationDate;
    }

    public Date getApplicationDate()
    {
        return applicationDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCompanies(String companies)
    {
        this.companies = companies;
    }

    public String getCompaniesId()
    {
        return companies;
    }
    public void setRelatedProjects(String relatedProjects)
    {
        this.relatedProjects = relatedProjects;
    }

    public String getRelatedProjects()
    {
        return relatedProjects;
    }
    public void setIsMainContract(Long isMainContract)
    {
        this.isMainContract = isMainContract;
    }

    public Long getIsMainContract()
    {
        return isMainContract;
    }
    public void setRelatedMainContract(String relatedMainContract)
    {
        this.relatedMainContract = relatedMainContract;
    }

    public String getRelatedMainContract()
    {
        return relatedMainContract;
    }
    public void setBudgetRelatedContracts(String budgetRelatedContracts)
    {
        this.budgetRelatedContracts = budgetRelatedContracts;
    }

    public String getBudgetRelatedContracts()
    {
        return budgetRelatedContracts;
    }
    public void setCostCenter(String costCenter)
    {
        this.costCenter = costCenter;
    }

    public String getCostCenter()
    {
        return costCenter;
    }
    public void setHead(String head)
    {
        this.head = head;
    }

    public String getHead()
    {
        return head;
    }
    public void setSigningDate(Date signingDate)
    {
        this.signingDate = signingDate;
    }

    public Date getSigningDate()
    {
        return signingDate;
    }
    public void setPaymentProportion(BigDecimal paymentProportion)
    {
        this.paymentProportion = paymentProportion;
    }

    public BigDecimal getPaymentProportion()
    {
        return paymentProportion;
    }
    public void setContractstatusId(Long contractstatusId)
    {
        this.contractstatusId = contractstatusId;
    }

    public Long getContractstatusId()
    {
        return contractstatusId;
    }
    public void setSdId(Long sdId)
    {
        this.sdId = sdId;
    }

    public Long getSdId()
    {
        return sdId;
    }
    public void setTotalProjectBudget(BigDecimal totalProjectBudget)
    {
        this.totalProjectBudget = totalProjectBudget;
    }

    public BigDecimal getTotalProjectBudget()
    {
        return totalProjectBudget;
    }
    public void setProjectAvailableBudget(Long projectAvailableBudget)
    {
        this.projectAvailableBudget = projectAvailableBudget;
    }

    public Long getProjectAvailableBudget()
    {
        return projectAvailableBudget;
    }
    public void setContractType(Long contractType)
    {
        this.contractType = contractType;
    }

    public Long getContractType()
    {
        return contractType;
    }
    public void setDense(String dense)
    {
        this.dense = dense;
    }

    public String getDense()
    {
        return dense;
    }
    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }

    public Date getBeginTime()
    {
        return beginTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }
    public void setContractPrice(BigDecimal contractPrice)
    {
        this.contractPrice = contractPrice;
    }

    public BigDecimal getContractPrice()
    {
        return contractPrice;
    }
    public void setCapitalization(String capitalization)
    {
        this.capitalization = capitalization;
    }

    public String getCapitalization()
    {
        return capitalization;
    }
    public void setStampDutyAmount(String stampDutyAmount)
    {
        this.stampDutyAmount = stampDutyAmount;
    }

    public String getStampDutyAmount()
    {
        return stampDutyAmount;
    }
    public void setContractDescription(String contractDescription)
    {
        this.contractDescription = contractDescription;
    }

    public String getContractDescription()
    {
        return contractDescription;
    }
    public void setPurchasingList(String purchasingList)
    {
        this.purchasingList = purchasingList;
    }

    public String getPurchasingList()
    {
        return purchasingList;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    @Override
    public String toString() {
        return "ContractManagement{" +
                "contractManagementId=" + contractManagementId +
                ", contractName='" + contractName + '\'' +
                ", contractCode='" + contractCode + '\'' +
                ", projectRelatedContracts='" + projectRelatedContracts + '\'' +
                ", applicant='" + applicant + '\'' +
                ", applicationDate=" + applicationDate +
                ", department='" + department + '\'' +
                ", companies='" + companies + '\'' +
                ", relatedProjects='" + relatedProjects + '\'' +
                ", isMainContract=" + isMainContract +
                ", relatedMainContract='" + relatedMainContract + '\'' +
                ", budgetRelatedContracts='" + budgetRelatedContracts + '\'' +
                ", costCenter='" + costCenter + '\'' +
                ", head='" + head + '\'' +
                ", signingDate=" + signingDate +
                ", paymentProportion=" + paymentProportion +
                ", contractstatusId=" + contractstatusId +
                ", sdId=" + sdId +
                ", totalProjectBudget=" + totalProjectBudget +
                ", projectAvailableBudget=" + projectAvailableBudget +
                ", contractType=" + contractType +
                ", dense='" + dense + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", contractPrice=" + contractPrice +
                ", capitalization='" + capitalization + '\'' +
                ", stampDutyAmount='" + stampDutyAmount + '\'' +
                ", contractDescription='" + contractDescription + '\'' +
                ", purchasingList='" + purchasingList + '\'' +
                ", purchaseOrderCode='" + purchaseOrderCode + '\'' +
                ", supplierDetails=" + supplierDetails +
                ", orderManager=" + orderManager +
                ", executionStatuses=" + executionStatuses +
                ", products=" + products +
                '}';
    }


    // 供应商
    private SupplierDetails supplierDetails;
    //private ExecutionStatus executionStatus;
    //private Products productse;
    private OrderManager orderManager;

    public SupplierDetails getSupplierDetails() {
        return supplierDetails;
    }

    public void setSupplierDetails(SupplierDetails supplierDetails) {
        this.supplierDetails = supplierDetails;
    }

    public String getCompanies() {
        return companies;
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    // 状态(乙方)
    private List<ExecutionStatus> executionStatuses;

    public List<ExecutionStatus> getExecutionStatuses() {
        return executionStatuses;
    }

    public void setExecutionStatuses(List<ExecutionStatus> executionStatuses) {
        this.executionStatuses = executionStatuses;
    }


    // 产品
    private List<Products> products;

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }







}
