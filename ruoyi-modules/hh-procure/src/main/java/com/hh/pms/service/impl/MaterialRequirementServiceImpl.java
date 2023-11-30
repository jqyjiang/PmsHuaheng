package com.hh.pms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.MaterialRequirementMapper;
import com.hh.pms.domain.MaterialRequirement;
import com.hh.pms.service.IMaterialRequirementService;

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
    public int insertMaterialRequirement(MaterialRequirement materialRequirement)
    {
        materialRequirement.setCreateTime(DateUtils.getNowDate());
        return materialRequirementMapper.insertMaterialRequirement(materialRequirement);
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
            String requirementCode=requirement;
            requirementCode=requirement.substring(10,13);
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
}
