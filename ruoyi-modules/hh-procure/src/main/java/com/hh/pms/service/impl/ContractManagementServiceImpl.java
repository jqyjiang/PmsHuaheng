package com.hh.pms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @date 2023-12-14
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
        // 合同编号生成
        Date date = new Date();
        contractManagement.setSigningDate(date);
        String contract_Code=createContractCode(date);
        contractManagement.setContractCode(contract_Code);
        // 相关项目
        contractManagement.setRelatedProjects("上海亘岩集团电子档案项目");
        // 关联主合同
        contractManagement.setRelatedMainContract("维森+2020办公设备采购合同");
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

    /**
     * 合同编号生成方法
     *
     * @param date 传入当前日期
     * @return 订单编号
     */
    public String createContractCode(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        String contract = contractManagementMapper.selectContractCode(date);
        if (contract!=null){
            //如果这个时间存在,说明今天已经有需求生成了
            String contractCode = contract;
            contractCode=contract.substring(13,16);
            int num = Integer.valueOf(contractCode);
            num++;
            if (num < 10) {
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code = "WSCG" + newBidDate + idNum;
                return code;
            } else if (num < 100) {
                String idNum = String.format("%02d", num);//num<100,说明是两位数，前面要补一个0
                String code = "WSCG" + newBidDate + idNum;
                return code;
            } else {
                String idNum = String.valueOf(num);
                String code = "WSCG" + newBidDate + idNum;  //date = 20201111+124
                return code;
            }
        }else {
            //如果今天时间不存在
            String code = "WSCG" + newBidDate + "001";
            return code;
        }
    }
}
