package com.hh.pms.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hh.pms.mapper.InboundNoteMapper;
import com.hh.pms.mapper.OrderMaterialMapper;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.QualityTaskListMapper;

import com.hh.pms.service.IQualityTaskListService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 到货质检Service业务层处理
 *
 * @author yangtao
 * @date 2023-12-21
 */
@Service
public class QualityTaskListServiceImpl implements IQualityTaskListService {
    @Autowired
    private QualityTaskListMapper qualityTaskListMapper;

    @Autowired
    private OrderMaterialMapper orderMaterialMapper;

    @Autowired
    private InboundNoteMapper inboundNoteMapper;

    /**
     * 查询到货质检
     *
     * @param qualityTaskListId 到货质检主键
     * @return 到货质检
     */
    @Override
    public QualityTaskList selectQualityTaskListByQualityTaskListId(Long qualityTaskListId) {
        return qualityTaskListMapper.selectQualityTaskListByQualityTaskListId(qualityTaskListId);
    }

    /**
     * 查询到货质检列表
     *
     * @param qualityTaskList 到货质检
     * @return 到货质检
     */
    @Override
    public List<QualityTaskList> selectQualityTaskListList(QualityTaskList qualityTaskList) {
        return qualityTaskListMapper.selectQualityTaskListList(qualityTaskList);
    }

    /**
     * 新增到货质检
     *
     * @param qualityTaskList 到货质检
     * @return 结果
     */
    @Override
    public int insertQualityTaskList(QualityTaskList qualityTaskList) {
        return qualityTaskListMapper.insertQualityTaskList(qualityTaskList);
    }

    /**
     * 修改到货质检
     *
     * @param qualityTaskList 到货质检
     * @return 结果
     */
    @Override
    public int updateQualityTaskList(QualityTaskList qualityTaskList) {
        return qualityTaskListMapper.updateQualityTaskList(qualityTaskList);
    }

    /**
     * 批量删除到货质检
     *
     * @param qualityTaskListIds 需要删除的到货质检主键
     * @return 结果
     */
    @Override
    public int deleteQualityTaskListByQualityTaskListIds(Long[] qualityTaskListIds) {
        return qualityTaskListMapper.deleteQualityTaskListByQualityTaskListIds(qualityTaskListIds);
    }

    /**
     * 删除到货质检信息
     *
     * @param qualityTaskListId 到货质检主键
     * @return 结果
     */
    @Override
    public int deleteQualityTaskListByQualityTaskListId(Long qualityTaskListId) {
        return qualityTaskListMapper.deleteQualityTaskListByQualityTaskListId(qualityTaskListId);
    }

    @Override
    public List<QualityOrder> selectQualityOrdergetExemptList(QualityOrder qualityOrder) {
        return qualityTaskListMapper.selectQualityOrdergetExemptList(qualityOrder);
    }

    @Override
    @Transactional
    public int updateQualityTaskExemopt(QualityTaskList qualityTaskList) {
        InboundNote inboundNote = new InboundNote();
        InboundMaterial inboundMaterial = new InboundMaterial();
        BigDecimal multiply = BigDecimal.ZERO; //得到入库总金额的变量
        BigDecimal noTaxPrice = BigDecimal.ZERO;//得到不含税单价的变量
        qualityTaskList.setQualityStatus(3l);//免检状态
        //根据订单编号查询物料信息表
        List<OrderMaterial> orderCodeMaterial = orderMaterialMapper.findByOrderCodeMaterial(qualityTaskList.getOrderCode());
        if (!orderCodeMaterial.isEmpty()) {
            Optional<OrderMaterial> optionalOrderMaterial = orderCodeMaterial.stream()
                    .filter(order -> qualityTaskList.getOrderCode().equals(order.getOrderCode()) && qualityTaskList.getOrName().equals(order.getOrName()))
                    .findFirst();
            if (optionalOrderMaterial.isPresent()) {
                OrderMaterial filteredOrderMaterial = optionalOrderMaterial.get();
                BigDecimal tax = filteredOrderMaterial.getTax();
                noTaxPrice = filteredOrderMaterial.getNoTaxPrice();

                //添加入库物料的部分字段
                inboundMaterial.setMaterialCategory(filteredOrderMaterial.getMaterialCategory());//物料品类
                inboundMaterial.setConsignee(filteredOrderMaterial.getConsignee());//收货人
                inboundMaterial.setReceivingPhone(filteredOrderMaterial.getReceivingPhone());//收货人联系电话
                inboundMaterial.setReceivedQuantity(filteredOrderMaterial.getRequireNumber());//需求数量
                inboundMaterial.setDeliveryDate(filteredOrderMaterial.getRequireTime());//需求时间
                inboundMaterial.setLineNumber(filteredOrderMaterial.getLineNumber());//行号
                inboundMaterial.setOrderCode(filteredOrderMaterial.getOrderCode());//订单号
                inboundMaterial.setRemarks(filteredOrderMaterial.getRemarks());//备注
                inboundMaterial.setMaterialUnit(filteredOrderMaterial.getMaterialUnit());//单位

                if (tax.compareTo(BigDecimal.valueOf(0)) == 0) {
                    //tax 为空 拿到它的不含税单价计算
                    multiply = noTaxPrice.multiply(qualityTaskList.getReceiveQuantity());
                } else {
                    //tax不为空 拿含税单价计算
                    BigDecimal taxPrice = filteredOrderMaterial.getTaxPrice();
                    multiply = taxPrice.multiply(qualityTaskList.getReceiveQuantity());
                }
                // 使用 filteredOrderMaterial 进行后续操作
            }
        }

        //TODO 入库人 入库单号 入库日期 入库备注 附件 可以在前端展示this.$store.state.user.name

        //TODO 为入库基本信息添加部分字段的数据
        //只接受合格产品
        inboundNote.setTaxAmount(multiply);//入库含税总金额
        inboundNote.setCreatedTime(DateUtils.getNowDate());//创建时间
        inboundNote.setStockInNoTaxAmount(noTaxPrice.multiply(qualityTaskList.getReceiveQuantity()));//入库不含税总金额
        inboundNote.setStockInAmount(multiply.subtract(noTaxPrice.multiply(qualityTaskList.getReceiveQuantity())));//入库税额
        inboundNote.setStockInQuantity(qualityTaskList.getReceiveQuantity());//将合格数量作为入库数量

        //添加物料基本信息
        inboundMaterial.setStockInQuantity(qualityTaskList.getReceiveQuantity());//添加物料入库数量

        //TODO 为入库物料明细表添加数据 qualityOrder与入库物料明细表有相同字段 订单编号物料编号 物料名称 物料品类 物料单位 质检处理措施 质检合格数量 库存组织
        //添加 物料名称 编码 单位 品类 收货数量
        inboundMaterial.setOrCode(qualityTaskList.getOrCode());
        inboundMaterial.setOrName(qualityTaskList.getOrName());
        inboundMaterial.setHandlingMeasuresName(3l); //免检状态
        inboundMaterial.setInventory("1");//库存组织
        //inboundNoteMapper.insertInboundNote(inboundNote);//插入入库基本信息表
        List<InboundMaterial> materialList = new ArrayList<>();
        materialList.add(inboundMaterial);
        inboundNoteMapper.batchInboundMaterial((materialList));
        return qualityTaskListMapper.updateQualityTaskExemopt(qualityTaskList);
    }
}
