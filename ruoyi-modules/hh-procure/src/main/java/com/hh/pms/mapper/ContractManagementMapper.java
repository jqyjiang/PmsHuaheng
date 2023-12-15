package com.hh.pms.mapper;

import com.ruoyi.system.api.domain.ContractManagement;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 采购合同管理Mapper接口
 *
 * @author ruoyi
 * @date 2023-12-14
 */
public interface ContractManagementMapper
{
    /**
     * 查询采购合同管理
     *
     * @param contractManagementId 采购合同管理主键
     * @return 采购合同管理
     */
    public ContractManagement selectContractManagementByContractManagementId(Long contractManagementId);

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
     * 删除采购合同管理
     *
     * @param contractManagementId 采购合同管理主键
     * @return 结果
     */
    public int deleteContractManagementByContractManagementId(Long contractManagementId);

    /**
     * 批量删除采购合同管理
     *
     * @param contractManagementIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContractManagementByContractManagementIds(Long[] contractManagementIds);


    /**
     * 向合同表查询是否存在某天
     *
     * @param contractTime
     * @return
     */

    @Select("SELECT contract_code FROM contract_management  WHERE TO_DAYS(signing_date)=TO_DAYS(#{contractTime}) GROUP BY contract_code ORDER BY signing_date DESC LIMIT 1")
    String selectContractCode(Date contractTime);
}
