package com.hh.pms.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hh.pms.mapper.ExecutionStatusMapper;
import com.hh.pms.mapper.OrderManagerMapper;
import com.hh.pms.model.OrderMaterialClient;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.ContractManagement;
import com.ruoyi.system.api.domain.ExecutionStatus;
import com.ruoyi.system.api.domain.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.ContractManagementMapper;
import com.hh.pms.service.IContractManagementService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 采购合同管理Service业务层处理
 *
 * @author ruoyi
 * @date 2023-12-14
 */
@Service
public class ContractManagementServiceImpl implements IContractManagementService {
    @Autowired
    private ContractManagementMapper contractManagementMapper;

    @Autowired
    private ExecutionStatusMapper executionStatusMapper;

    /**
     * 查询采购合同管理
     *
     * @param contractManagementId 采购合同管理主键
     * @return 采购合同管理
     */
    @Override
    public ContractManagement selectContractManagementByContractManagementId(Long contractManagementId,Long bothSides) {
        return contractManagementMapper.selectContractManagementByContractManagementId(contractManagementId,bothSides);
    }

    /**
     * 查询采购合同管理列表
     *
     * @param contractManagement 采购合同管理
     * @return 采购合同管理
     */
    @Override
    public List<ContractManagement> selectContractManagementList(ContractManagement contractManagement) {
        return contractManagementMapper.selectContractManagementList(contractManagement);
    }

    /**
     * 新增采购合同管理
     *
     * @param contractManagement 采购合同管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertContractManagement(ContractManagement contractManagement) {
        ExecutionStatus executionStatus = new ExecutionStatus();
        // 合同编号生成
        Date date = new Date();
        contractManagement.setSigningDate(date);
        String contract_Code = createContractCode(date);
        contractManagement.setContractCode(contract_Code);
        // 相关项目
        contractManagement.setRelatedProjects("上海亘岩集团电子档案项目");
        // 关联主合同
        contractManagement.setRelatedMainContract("维森+2020办公设备采购合同");
        // 项目总预算/项目可用预算
        contractManagement.setTotalProjectBudget(BigDecimal.valueOf(10000000));
        contractManagement.setProjectAvailableBudget(10000000L);
        // 成本中心
        contractManagement.setCostCenter("维森集团行政共享中心项目");
        // 采购订单编号
        String purchase_order_code=createPurchaseOrderCode(date);
        contractManagement.setPurchaseOrderCode(purchase_order_code);
        // 付款比例=(已付款金额/合同总金额)*100%
        List<ExecutionStatus> list = contractManagement.getExecutionStatuses();
        BigDecimal paidAmount = BigDecimal.ZERO;
        BigDecimal contractPrice = BigDecimal.ZERO;
        if (list.size() == 2) {
            for (ExecutionStatus exe : list) {
                BigDecimal amount = exe.getPaidAmount();
                paidAmount = paidAmount.add(amount); // 已付款金额
                BigDecimal amount2 = exe.getContractAmount();
                contractPrice = contractPrice.add(amount2);  // 合同金额
            }
            contractManagement.setPaymentProportion(paidAmount.divide(contractPrice,4).multiply(BigDecimal.valueOf(100)));  // 付款比例
            list.get(0).setBothSides(2L);
            // 添加签署执行状态表(一个合同可能对应多个签署方)
        } else {
            BigDecimal paidAmount1 = list.get(0).getPaidAmount();
            BigDecimal contractAmount = list.get(0).getContractAmount();
            contractManagement.setPaymentProportion(paidAmount1.divide(contractAmount,2,BigDecimal.ROUND_CEILING).multiply(BigDecimal.valueOf(100)));
            list.get(0).setBothSides(1L);
        }
        System.out.println("显示：" + list);
        // 添加合同
        int insertContract = contractManagementMapper.insertContractManagement(contractManagement);
        Long contractManagementId = contractManagement.getContractManagementId();
        System.out.println("添加合同-----:" + insertContract);
        list.forEach(item->{
            item.setContractManagementId(contractManagementId);
            item.setCurrencyName("人民币");
        });
        //  添加签署执行状态
        int i2 = executionStatusMapper.insertExecutionStatusList(list);
        System.out.println("i2-----:" + i2);
        return insertContract;
    }

    /**
     * 修改采购合同管理
     *
     * @param contractManagement 采购合同管理
     * @return 结果
     */
    @Override
    public int updateContractManagement(ContractManagement contractManagement) {
        return contractManagementMapper.updateContractManagement(contractManagement);
    }

    /**
     * 批量删除采购合同管理
     *
     * @param contractManagementIds 需要删除的采购合同管理主键
     * @return 结果
     */
    @Override
    public int deleteContractManagementByContractManagementIds(Long[] contractManagementIds) {
        return contractManagementMapper.deleteContractManagementByContractManagementIds(contractManagementIds);
    }

    /**
     * 删除采购合同管理信息
     *
     * @param contractManagementId 采购合同管理主键
     * @return 结果
     */
    @Override
    public int deleteContractManagementByContractManagementId(Long contractManagementId) {
        return contractManagementMapper.deleteContractManagementByContractManagementId(contractManagementId);
    }

    @Override
    public ContractManagement selectByContractManagementId(Long contractManagementId) {
        System.out.println("-----"+contractManagementMapper.selectByContractManagementId(contractManagementId));
        return contractManagementMapper.selectByContractManagementId(contractManagementId);
    }

    @Override
    public int addContractManagementOrder(OrderManager orderManager) {
        //先写订单生成
        orderManager.setCreateTime(DateUtils.getNowDate());
//        String orderCode = createOrderCode(DateUtils.getNowDate());
//        orderManager.setOrderCode(orderCode);
//        orderManager.setOrderState(1l);
        return 0;
    }


    /**
     * 合同编号生成方法
     *
     * @param date 传入当前日期
     * @return 订单编号
     */
    public String createContractCode(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        String contract = contractManagementMapper.selectContractCode(date);
        if (contract != null) {
            //如果这个时间存在,说明今天已经有需求生成了
            String contractCode = contract;
            contractCode = contract.substring(14,16);
            int num = Integer.valueOf(contractCode);
            num++;
            if (num < 10) {
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code = "WS-CG" + newBidDate + idNum;
                return code;
            } else if (num < 100) {
                String idNum = String.format("%03d", num);//num<100,说明是两位数，前面要补一个0
                String code = "WS-CG" + newBidDate + idNum;
                return code;
            } else {
                String idNum = String.valueOf(num);
                String code = "WS-CG" + newBidDate + idNum;  //date = 20201111+124
                return code;
            }
        } else {
            //如果今天时间不存在
            String code = "WS-CG" + newBidDate + "001";
            return code;
        }
    }

    /**
     * 采购订单编号生成方法
     *
     * @param date 传入当前日期
     * @return 订单编号
     */
    public String createPurchaseOrderCode(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        String contract = contractManagementMapper.selectPurchaseOrderCode(date);
        if (contract != null) {
            //如果这个时间存在,说明今天已经有需求生成了
            String contractCode = contract;
            contractCode = contract.substring(14,15);
            int num = Integer.valueOf(contractCode);
            num++;
            if (num < 10) {
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code = "CGDD" + newBidDate + idNum;
                return code;
            } else if (num < 100) {
                String idNum = String.format("%03d", num);//num<100,说明是两位数，前面要补一个0
                String code = "CGDD" + newBidDate + idNum;
                return code;
            } else {
                String idNum = String.valueOf(num);
                String code = "CGDD" + newBidDate + idNum;  //date = 20201111+124
                return code;
            }
        } else {
            //如果今天时间不存在
            String code = "CGDD" + newBidDate + "001";
            return code;
        }
    }

}
