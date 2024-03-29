package com.hh.pms.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hh.pms.mapper.*;
import com.hh.pms.model.OrderMaterialClient;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.service.IMaterialRequirementService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 采购需求申请Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-24
 */
@Service
public class MaterialRequirementServiceImpl implements IMaterialRequirementService
{
    @Autowired
    private MaterialRequirementMapper materialRequirementMapper;

    @Autowired
    private MaterialPoolMapper materialPoolMapper;

    @Autowired
    private MaterialInformationMapper materialInformationMapper;

    @Autowired
    private OrderMaterialClient orderMaterialClient;

    @Autowired
    private ProcurementTaskMapper procurementTaskMapper;

    /**
     * 查询采购需求申请
     *
     * @param requirementId 采购需求申请主键
     * @return 采购需求申请
     */
    @Override
    public MaterialRequirement selectMaterialRequirementByRequirementId(Long requirementId)
    {
        return materialRequirementMapper.selectMaterialRequirementByRequirementId(requirementId);
    }

    /**
     * 查询采购需求申请列表
     *
     * @param materialRequirement 采购需求申请
     * @return 采购需求申请
     */
    @Override
    public List<MaterialRequirement> selectMaterialRequirementList(MaterialRequirement materialRequirement)
    {
        return materialRequirementMapper.selectMaterialRequirementList(materialRequirement);
    }

    /**
     * 新增采购需求申请
     *
     * @param materialRequirement 采购需求申请
     * @return 结果
     */
    @Override
    @Transactional
    public int insertMaterialRequirement(MaterialRequirement materialRequirement)
    {
        // 需求编号生成
        Date date = new Date();
        materialRequirement.setCreateTime(date);
        String requirement_code=createRequirementCode(date);
        materialRequirement.setRequirementCode(requirement_code);
        // 任务单号生成
        String task_code=createTaskcode(date);
        materialRequirement.setTaskCode(task_code);
        long nowTime = System.currentTimeMillis();
        materialRequirement.setRequirementTitle("采购需求审批流程"+nowTime);
        //保存采购需求申请
        int i1 = materialRequirementMapper.insertMaterialRequirement(materialRequirement);
        // 添加物料基础表   需求池数据
        List<MaterialInformation> materialInformationsList=materialRequirement.getMaterialInformations();
        materialInformationsList.forEach(item->{
            item.setRequirementCode(requirement_code);
            item.setStatus(1L);
        });
        int i=materialRequirementMapper.insertRequirementInformations(materialInformationsList);
        // 添加物料ID
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();
        BigDecimal num=BigDecimal.ZERO;
        BigDecimal jine=BigDecimal.ZERO;
        for (MaterialInformation item : materialInformationsList){
            num= item.getMustNumber();
            num=num.add(item.getMustNumber());
            jine=item.getReferencePrice();
            jine=jine.add(item.getReferencePrice());
            if (stringBuilder.length()>0){
                stringBuilder.append(",");
                stringBuilder1.append(",");
            }
            stringBuilder.append(item.getMiId());
            stringBuilder1.append(item.getMaterialName());
        }
        String miIdString=stringBuilder.toString();
        System.out.println(miIdString);
        String mName=stringBuilder1.toString();
        ProcurementTask procurementTask = new ProcurementTask();
        procurementTask.setTaskCode(task_code);
        procurementTask.setTaskTotal(jine.multiply(num));
        procurementTask.setProcurementStrategy("待转订单,待询价");// 采购策略
        procurementTask.setRequirementId(materialRequirement.getRequirementId()); // 需求申请id
        procurementTask.setCompaniesId(null==materialRequirement.getCompaniesId()?"":materialRequirement.getCompaniesId().toString());
        procurementTask.setPurchaser(materialInformationsList.get(0).getPurchaser());//采购员
        procurementTask.setCurrencyId(materialInformationsList.get(0).getCurrencyId());//币种ID
        procurementTask.setTaskNumber(num);// 任务总数量
        procurementTask.setAcceptanceStrategy("转订单"); // 受理策略
        procurementTask.setAcceptedQuantity(BigDecimal.valueOf(0.00));
        procurementTask.setTaskOccupied(BigDecimal.valueOf(0.00));
        procurementTask.setTaskAccepted(num);
        procurementTask.setTaskStatus(Long.valueOf("4"));//定义'状态'类型 待受理1  受理完成2  关闭3   不展示4
        procurementTask.setDescriptionName(mName);
        procurementTask.setRequirementCode(requirement_code);
        int i2 = procurementTaskMapper.insertProcurementTask(procurementTask);
        // 查询采购需求池信息
        //给采购任务表添加数据 每一个需求创建一个 需求任务对象
        int miId = materialInformationMapper.selectMaxMiId();
        MaterialInformation materialInformation1 = materialInformationMapper.selectMaterialInformationByMiId(miId);


        // 添加order_material表信息
        OrderMaterial orderMaterial=new OrderMaterial();
        orderMaterial.setOrCode(materialInformation1.getMaterialCode());
        orderMaterial.setOrName(materialInformation1.getMaterialName());
        orderMaterial.setMaterialCategory(materialInformation1.getMaterialCategory());
        orderMaterial.setMaterialSpecification(materialInformation1.getMaterialSpecification());
        orderMaterial.setMaterialModel(materialInformation1.getMaterialModel());
        orderMaterial.setMaterialUnit(materialInformation1.getMaterialUnit());
        orderMaterial.setRequireNumber(num);
        orderMaterial.setRequireTime(materialInformation1.getMustDate());
        orderMaterial.setOrderCode(task_code);
        AjaxResult ajaxResult = orderMaterialClient.insertOrderMaterial(orderMaterial);
        System.out.println("----------------------------:"+ajaxResult);
        return i1;
    }

    /**
     * 修改采购需求申请
     *
     * @param materialRequirement 采购需求申请
     * @return 结果
     */
    @Override
    public int updateMaterialRequirement(MaterialRequirement materialRequirement)
    {
        return materialRequirementMapper.updateMaterialRequirement(materialRequirement);
    }

    /**
     * 批量删除采购需求申请
     *
     * @param requirementIds 需要删除的采购需求申请主键
     * @return 结果
     */
    @Override
    public int deleteMaterialRequirementByRequirementIds(Long[] requirementIds)
    {
        return materialRequirementMapper.deleteMaterialRequirementByRequirementIds(requirementIds);
    }

    /**
     * 删除采购需求申请信息
     *
     * @param requirementId 采购需求申请主键
     * @return 结果
     */
    @Override
    public int deleteMaterialRequirementByRequirementId(Long requirementId)
    {
        return materialRequirementMapper.deleteMaterialRequirementByRequirementId(requirementId);
    }

    @Override
    public int insertRequirementInformations(List<MaterialInformation> materialInformations) {
        return materialRequirementMapper.insertRequirementInformations(materialInformations);
    }

    /**
     * 查询采购需求池列表
     *
     * @param materialPool 采购需求池
     * @return 采购需求池
     */
    @Override
    public List<MaterialPool> selectMaterialList(MaterialPool materialPool)
    {
        return materialPoolMapper.selectMaterialList(materialPool);
    }

    //采购需求类型 统计分析
    @Override
    public List<MaterialRequirement> selectDemandType() {
        return materialRequirementMapper.selectDemandType();
    }

    @Override
    public int insertProcurementTask(List<ProcurementTask> procurementTasks) {
        return materialRequirementMapper.insertProcurementTask(procurementTasks);
    }

    /**
     * 需求编号生成方法
     *
     * @param date 传入当前日期
     * @return 订单编号
     */
    public String createRequirementCode(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        String requirement = materialRequirementMapper.selectRequirementCode(date);
        if (requirement!=null){
            //如果这个时间存在,说明今天已经有需求生成了
            String requirementCode = requirement;
            requirementCode=requirement.substring(13,16);
            int num = Integer.valueOf(requirementCode);
            num++;
            if (num < 10) {
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code = "SCXCG" + newBidDate + idNum;
                return code;
            } else if (num < 100) {
                String idNum = String.format("%02d", num);//num<100,说明是两位数，前面要补一个0
                String code = "SCXCG" + newBidDate + idNum;
                return code;
            } else {
                String idNum = String.valueOf(num);
                String code = "SCXCG" + newBidDate + idNum;  //date = 20201111+124
                return code;
            }
        }else {
            //如果今天时间不存在
            String code = "SCXCG" + newBidDate + "001";
            return code;
        }
    }


    /**
     * 任务单号生成方法
     *
     * @param date 传入当前日期
     * @return 任务单号
     */
    public String createTaskcode(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        String task = materialRequirementMapper.selectRequirementCode(date);
        if (task!=null){
            //如果这个时间存在,说明今天已经有需求生成了
            String taskCode=task;
            taskCode=task.substring(13,16);
            int num = Integer.valueOf(taskCode);
            num++;
            if (num < 10) {
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code = "RW" + newBidDate + idNum;
                return code;
            } else if (num < 100) {
                String idNum = String.format("%02d", num);//num<100,说明是两位数，前面要补一个0
                String code = "RW" + newBidDate + idNum;
                return code;
            } else {
                String idNum = String.valueOf(num);
                String code = "RW" + newBidDate + idNum;  //date = 20201111+124
                return code;
            }
        }else {
            //如果今天时间不存在
            String code = "RW" + newBidDate + "001";
            return code;
        }
    }

}
