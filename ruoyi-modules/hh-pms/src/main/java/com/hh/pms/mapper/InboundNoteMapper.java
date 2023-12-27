package com.hh.pms.mapper;

import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.InboundMaterial;
import com.ruoyi.system.api.domain.InboundNote;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 订单入库管理Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
public interface InboundNoteMapper 
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
     * 删除订单入库管理
     * 
     * @param id 订单入库管理主键
     * @return 结果
     */
    public int deleteInboundNoteById(Long id);

    /**
     * 批量删除订单入库管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInboundNoteByIds(Long[] ids);

    /**
     * 批量删除入库单物料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInboundMaterialByIds(Long[] ids);
    
    /**
     * 批量新增入库单物料
     * 
     * @param inboundMaterialList 入库单物料列表
     * @return 结果
     */
    public int batchInboundMaterial(List<InboundMaterial> inboundMaterialList);
    

    /**
     * 通过订单入库管理主键删除入库单物料信息
     * 
     * @param id 订单入库管理ID
     * @return 结果
     */
    public int deleteInboundMaterialById(Long id);

    /**
     * 查询入库单下的物料
     * @param orderCode
     * @return
     */
    List<InboundMaterial> selectInboundNoteByOrderCode(@Param("orderCode") String orderCode,@Param("orName")String orName);

    @Select("select stockInNoteNo from inbound_note where to_days(create_time)= to_days(#{orderTime}) group by stockInNoteNo order by create_time DESC limit 1")
    String selectOrderCode(@Param("orderTime") Date date);
}
