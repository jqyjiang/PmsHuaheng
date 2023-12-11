package com.hh.pms.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.ContractManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.ContractManagementMapper;
import com.hh.pms.service.IContractManagementService;

/**
 * 采购合同管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
@Service
public class ContractManagementServiceImpl implements IContractManagementService 
{
    @Autowired
    private ContractManagementMapper contractManagementMapper;

    /**
     * 查询采购合同管理
     * 
     * @param contractManagementId 采购合同管理主键
     * @return 采购合同管理
     */
    @Override
    public ContractManagement selectContractManagementByContractManagementId(Long contractManagementId)
    {
        return contractManagementMapper.selectContractManagementByContractManagementId(contractManagementId);
    }

    /**
     * 查询采购合同管理列表
     * 
     * @param contractManagement 采购合同管理
     * @return 采购合同管理
     */
    @Override
    public List<ContractManagement> selectContractManagementList(ContractManagement contractManagement)
    {
        return contractManagementMapper.selectContractManagementList(contractManagement);
    }

    /**
     * 新增采购合同管理
     * 
     * @param contractManagement 采购合同管理
     * @return 结果
     */
    @Override
    public int insertContractManagement(ContractManagement contractManagement)
    {
        return contractManagementMapper.insertContractManagement(contractManagement);
    }

    /**
     * 修改采购合同管理
     * 
     * @param contractManagement 采购合同管理
     * @return 结果
     */
    @Override
    public int updateContractManagement(ContractManagement contractManagement)
    {
        return contractManagementMapper.updateContractManagement(contractManagement);
    }

    /**
     * 批量删除采购合同管理
     * 
     * @param contractManagementIds 需要删除的采购合同管理主键
     * @return 结果
     */
    @Override
    public int deleteContractManagementByContractManagementIds(Long[] contractManagementIds)
    {
        return contractManagementMapper.deleteContractManagementByContractManagementIds(contractManagementIds);
    }

    /**
     * 删除采购合同管理信息
     * 
     * @param contractManagementId 采购合同管理主键
     * @return 结果
     */
    @Override
    public int deleteContractManagementByContractManagementId(Long contractManagementId)
    {
        return contractManagementMapper.deleteContractManagementByContractManagementId(contractManagementId);
    }
}
