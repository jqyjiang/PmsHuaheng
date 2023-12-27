package com.hh.pms.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hh.pms.mapper.*;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.pms.service.IQualityOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 质检单Service业务层处理
 *
 * @author ruoyi
 * @date 2023-12-25
 */
@Service
public class QualityOrderServiceImpl implements IQualityOrderService {
    @Autowired
    private QualityOrderMapper qualityOrderMapper;

    @Autowired
    private QualityTaskListMapper qualityTaskListMapper;

    @Autowired
    private OrderExecutionDetailsMapper orderExecutionDetailsMapper;

    @Autowired
    private OrderMaterialMapper orderMaterialMapper;

    @Autowired
    private InboundNoteMapper inboundNoteMapper;


    /**
     * 查询质检单
     *
     * @param qualityId 质检单主键
     * @return 质检单
     */
    @Override
    public QualityOrder selectQualityOrderByQualityId(Long qualityId) {
        return qualityOrderMapper.selectQualityOrderByQualityId(qualityId);
    }

    /**
     * 查询质检单列表
     *
     * @param qualityOrder 质检单
     * @return 质检单
     */
    @Override
    public List<QualityOrder> selectQualityOrderList(QualityOrder qualityOrder) {
        return qualityOrderMapper.selectQualityOrderList(qualityOrder);
    }

    /**
     * 新增质检单
     *
     * @param qualityOrder 质检单
     * @return 结果
     */
    @Override
    @Transactional
    public int insertQualityOrder(QualityOrder qualityOrder) {
        //一共有四种状态  如果是接受和让步接收的状态 那就全部接受  如果是挑选入库那么就要 选合格品入库
        InboundNote inboundNote = new InboundNote();
        InboundMaterial inboundMaterial = new InboundMaterial();
        BigDecimal multiply = BigDecimal.ZERO; //得到入库总金额的变量
        BigDecimal noTaxPrice = BigDecimal.ZERO;//得到不含税单价的变量
        qualityOrder.setCreateTime(DateUtils.getNowDate());
        String orderCode = createOrderCode(DateUtils.getNowDate());
        qualityOrder.setQualityCode(orderCode);
        qualityOrder.setStatus(4l);
        qualityTaskListMapper.updateQualityStatus(4l, qualityOrder.getReceiptNoteNo());
        //质检完成的物料需要调整质检状态  订单执行明细表下面的质检状态 1l:已收货未质检 2l:已收货待入库
        orderExecutionDetailsMapper.updateOrderDetailQualityStatus(qualityOrder.getOrderCode(), qualityOrder.getReceiptNoteNo(), qualityOrder.getDeliveryNoteNo(), 2l);
        //根据订单编号查询物料信息表
        List<OrderMaterial> orderCodeMaterial = orderMaterialMapper.findByOrderCodeMaterial(qualityOrder.getOrderCode());
        if (!orderCodeMaterial.isEmpty()) {
            Optional<OrderMaterial> optionalOrderMaterial = orderCodeMaterial.stream()
                    .filter(order -> qualityOrder.getOrderCode().equals(order.getOrderCode()) && qualityOrder.getOrName().equals(order.getOrName()))
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

                if (tax.compareTo(BigDecimal.valueOf(0)) == 0) {
                    //tax 为空 拿到它的不含税单价计算
                    multiply = noTaxPrice.multiply(qualityOrder.getInspectQualifiedNumber());
                } else {
                    //tax不为空 拿含税单价计算
                    BigDecimal taxPrice = filteredOrderMaterial.getTaxPrice();
                    multiply = taxPrice.multiply(qualityOrder.getInspectQualifiedNumber());
                }
                // 使用 filteredOrderMaterial 进行后续操作
            }
        }

        //TODO 入库人 入库单号 入库日期 入库备注 附件 可以在前端展示this.$store.state.user.name

        //TODO 为入库基本信息添加部分字段的数据
        if (qualityOrder.getHandlingMeasuresName() == 3) {
            //只接受合格产品
            inboundNote.setTaxAmount(multiply);//入库含税总金额
            inboundNote.setCreatedTime(DateUtils.getNowDate());//创建时间
            inboundNote.setStockInNoTaxAmount(noTaxPrice.multiply(qualityOrder.getInspectQualifiedNumber()));//入库不含税总金额
            inboundNote.setStockInAmount(multiply.subtract(noTaxPrice.multiply(qualityOrder.getInspectQualifiedNumber())));//入库税额
            inboundNote.setStockInQuantity(qualityOrder.getInspectQualifiedNumber());//将合格数量作为入库数量

            //添加物料基本信息
            inboundMaterial.setStockInQuantity(qualityOrder.getInspectQualifiedNumber());//添加物料入库数量

        } else if (qualityOrder.getHandlingMeasuresName() == 4 || qualityOrder.getHandlingMeasuresName() == 2) {
            //全部接收
            inboundNote.setTaxAmount(multiply);//入库含税总金额
            inboundNote.setCreatedTime(DateUtils.getNowDate());//创建时间
            inboundNote.setStockInNoTaxAmount(noTaxPrice.multiply(qualityOrder.getReceivedQuantity()));//入库不含税总金额
            inboundNote.setStockInAmount(multiply.subtract(noTaxPrice.multiply(qualityOrder.getReceivedQuantity())));//入库税额
            inboundNote.setStockInQuantity(qualityOrder.getReceivedQuantity());//将收货数量作为入库数量

            //添加物料基本信息
            inboundMaterial.setStockInQuantity(qualityOrder.getReceivedQuantity());//添加物料入库数量

        } else {
            //退回供应商
            inboundNote.setCreatedTime(DateUtils.getNowDate());//创建时间
            inboundNote.setReturnTaxExcludedAmount(noTaxPrice.multiply(qualityOrder.getReceivedQuantity()));//入库不含税总金额
            inboundNote.setReturnTaxIncludedAmount(multiply.subtract(noTaxPrice.multiply(qualityOrder.getReceivedQuantity())));//入库税额
            inboundNote.setReturnQuantity(qualityOrder.getReceivedQuantity());//将收货数量作为入库数量

            //添加物料基本信息
            inboundMaterial.setReturnExchangeQuantity(qualityOrder.getReceivedQuantity());//添加物料退货数量
        }

        //inboundNote.setReturnQuantity(qualityOr);
        //TODO 为入库物料明细表添加数据 qualityOrder与入库物料明细表有相同字段 订单编号物料编号 物料名称 物料品类 物料单位 质检处理措施 质检合格数量 库存组织
        BeanUtils.copyProperties(qualityOrder, inboundMaterial);//添加 物料名称 编码 单位 品类 收货数量
        inboundMaterial.setHandlingMeasuresName(qualityOrder.getHandlingMeasuresName()); //添加处理措施
        inboundMaterial.setInventory(qualityOrder.getInventory());//库存组织
        inboundNoteMapper.insertInboundNote(inboundNote);//插入入库基本信息表
        List<InboundMaterial> materials = new ArrayList<>();
        materials.add(inboundMaterial);
        inboundNoteMapper.batchInboundMaterial(materials);
        //TODO 入库物料明细表


        return qualityOrderMapper.insertQualityOrder(qualityOrder);
    }

    /**
     * 修改质检单
     *
     * @param qualityOrder 质检单
     * @return 结果
     */
    @Override
    public int updateQualityOrder(QualityOrder qualityOrder) {
        return qualityOrderMapper.updateQualityOrder(qualityOrder);
    }

    /**
     * 批量删除质检单
     *
     * @param qualityIds 需要删除的质检单主键
     * @return 结果
     */
    @Override
    public int deleteQualityOrderByQualityIds(Long[] qualityIds) {
        return qualityOrderMapper.deleteQualityOrderByQualityIds(qualityIds);
    }

    /**
     * 删除质检单信息
     *
     * @param qualityId 质检单主键
     * @return 结果
     */
    @Override
    public int deleteQualityOrderByQualityId(Long qualityId) {
        return qualityOrderMapper.deleteQualityOrderByQualityId(qualityId);
    }

    /**
     * 质检单号生成方法
     *
     * @param date 传入当前日期
     * @return 订单编号
     */
    public String createOrderCode(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        String order = qualityOrderMapper.selectQualityCode(date);
        if (order != null) {
            //如果这个时间存在,说明今天已经有订单生成了
            String orderCode = order;
            orderCode = orderCode.substring(11, 14);
            int num = Integer.valueOf(orderCode);
            num++;
            if (num < 10) {
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code = "IQC" + newBidDate + idNum;
                return code;
            } else if (num < 100) {
                String idNum = String.format("%03d", num);//num<100,说明是两位数，前面要补一个0
                String code = "IQC" + newBidDate + idNum;
                return code;
            } else {
                String idNum = String.valueOf(num);
                String code = "IQC" + newBidDate + idNum;  //date = 20201111+124
                return code;
            }
        } else {
            //如果今天时间不存在
            String code = "IQC" + newBidDate + "001";
            return code;
        }
    }
}
