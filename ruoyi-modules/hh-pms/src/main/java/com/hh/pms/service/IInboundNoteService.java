package com.hh.pms.service;

import java.util.List;

import com.ruoyi.system.api.domain.InboundMaterial;
import com.ruoyi.system.api.domain.InboundNote;

/**
 * 订单入库管理Service接口
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
public interface IInboundNoteService 
{
    /**
     * 查询订单入库管理
     * 
     * @param id 订单入库管理主键
     * @return 订单入库管理
     */
    public InboundNote selectInboundNoteById(Long id);

    /**
     * 查询订单入库管理列表
     * 
     * @param inboundNote 订单入库管理
     * @return 订单入库管理集合
     */
    public List<InboundNote> selectInboundNoteList(InboundNote inboundNote);

    /**
     * 新增订单入库管理
     * 
     * @param inboundNote 订单入库管理
     * @return 结果
     */
    public int insertInboundNote(InboundNote inboundNote);

    /**
     * 修改订单入库管理
     * 
     * @param inboundNote 订单入库管理
     * @return 结果
     */
    public int updateInboundNote(InboundNote inboundNote);

    /**
     * 批量删除订单入库管理
     * 
     * @param ids 需要删除的订单入库管理主键集合
     * @return 结果
     */
    public int deleteInboundNoteByIds(Long[] ids);

    /**
     * 删除订单入库管理信息
     * 
     * @param id 订单入库管理主键
     * @return 结果
     */
    public int deleteInboundNoteById(Long id);

    /**
     * 查询入库单下的物料
     * @param orderCode
     * @return
     */
    List<InboundMaterial> selectInboundNoteByOrderCode(String orderCode,String orName);
}
