package com.hh.pms.service;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.ContractManagement;
import com.ruoyi.system.api.domain.OrderManager;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购合同管理Service接口
 *
 * @author ruoyi
 * @date 2023-12-14
 */
public interface IContractManagementService
{
    /**
     * 查询采购合同管理
     *
     * @param contractManagementId 采购合同管理主键
     * @return 采购合同管理
     */
    public ContractManagement selectContractManagementByContractManagementId(Long contractManagementId,Long bothSides);

    /**
     * 查询采购合同管理列表
     *
     * @param contractManagement 采购合同管理
     * @return 采购合同管理集合
     */
    public List<ContractManagement> selectContractManagementList(ContractManagement contractManagement);

    /**
     * 新增采购合同管理
     *
     * @param contractManagement 采购合同管理
     * @return 结果
     */
    public int insertContractManagement(ContractManagement contractManagement);

    /**
     * 修改采购合同管理
     *
     * @param contractManagement 采购合同管理
     * @return 结果
     */
    public int updateContractManagement(ContractManagement contractManagement);

    /**
     * 批量删除采购合同管理
     *
     * @param contractManagementIds 需要删除的采购合同管理主键集合
     * @return 结果
     */
    public int deleteContractManagementByContractManagementIds(Long[] contractManagementIds);

    /**
     * 删除采购合同管理信息
     *
     * @param contractManagementId 采购合同管理主键
     * @return 结果
     */
    public int deleteContractManagementByContractManagementId(Long contractManagementId);

    /**
     * 查询采购合同管理（详情查询）
     *
     * @param contractManagementId 采购合同管理主键
     * @return 采购合同管理
     */
    public ContractManagement selectByContractManagementId(@Param("contractManagementId")Long contractManagementId);

    // 合同转订单
    int addContractManagementOrder(OrderManager orderManager);

}
