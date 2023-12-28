package com.hh.pms.service.impl;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import com.hh.pms.mapper.ScoringTemplateMapper;
import com.ruoyi.system.api.domain.AttendSupplier;
import com.ruoyi.system.api.domain.PerformanceAppraisal;
import com.ruoyi.system.api.domain.ScoringDetails;
import com.ruoyi.system.api.domain.ScoringTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.hh.pms.mapper.PerformanceAppraisalMapper;
import com.hh.pms.service.IPerformanceAppraisalService;

/**
 * 绩效考评Service业务层处理
 *
 * @author ruoyi
 * @date 2023-12-22
 */
@Service
public class PerformanceAppraisalServiceImpl implements IPerformanceAppraisalService {
    @Autowired
    private PerformanceAppraisalMapper performanceAppraisalMapper;

    @Autowired
    private ScoringTemplateMapper scoringTemplateMapper;

    /**
     * 查询绩效考评
     *
     * @param evaluationId 绩效考评主键
     * @return 绩效考评
     */
    @Override
    public PerformanceAppraisal selectPerformanceAppraisalByEvaluationId(Long evaluationId) {
        return performanceAppraisalMapper.selectPerformanceAppraisalByEvaluationId(evaluationId);
    }

    /**
     * 查询绩效考评列表
     *
     * @param performanceAppraisal 绩效考评
     * @return 绩效考评
     */
    @Override
    public List<PerformanceAppraisal> selectPerformanceAppraisalList(PerformanceAppraisal performanceAppraisal) {
        return performanceAppraisalMapper.selectPerformanceAppraisalList(performanceAppraisal);
    }

    /**
     * 新增绩效考评
     *
     * @param performanceAppraisal 绩效考评
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPerformanceAppraisal(PerformanceAppraisal performanceAppraisal) throws CloneNotSupportedException {
        Calendar c = Calendar.getInstance();//可以用set()对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        //month都需要+1才表示当前月份
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour_of_day = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String nowTime = year + "" + (month + 1) + "" + date + "" + hour_of_day + "" + minute;
        performanceAppraisal.setEvaluationCode("JXKP" + nowTime + Math.round(Math.random() * 10000));
        performanceAppraisal.setEvaluationState(1L);
        int rows = performanceAppraisalMapper.insertPerformanceAppraisal(performanceAppraisal);
        insertAttendSupplier(performanceAppraisal);
        insertScoringDetails(performanceAppraisal);
        return rows;
    }

    /**
     * 修改绩效考评
     *
     * @param performanceAppraisal 绩效考评
     * @return 结果
     */
    @Transactional
    @Override
    public int updatePerformanceAppraisal(PerformanceAppraisal performanceAppraisal) throws CloneNotSupportedException {
        if (performanceAppraisal.getEvaluationState().equals(1L) ){
            System.out.println(performanceAppraisal);
            List<AttendSupplier> attendSupplierList = performanceAppraisal.getAttendSupplierList();
            List<ScoringDetails> scoringDetailsList = performanceAppraisal.getScoringDetailsList();

            for (AttendSupplier attendSupplier : attendSupplierList) {
                double num = 0;
                for (ScoringDetails scoringDetails : scoringDetailsList) {
                    if (attendSupplier.getSupplierCode().equals(scoringDetails.getSupplierCode())){
                        num += scoringDetails.getWeight() * 0.01 * scoringDetails.getScore();
                    }
                }
                attendSupplier.setSummaryScore(num);
                attendSupplier.setScore(num);
            }
            attendSupplierList.sort(Comparator.comparingDouble(AttendSupplier::getScore).reversed());
            System.out.println(attendSupplierList);
            // 为每个对象添加排行属性
            for (int i = 0; i < attendSupplierList.size(); i++) {
                attendSupplierList.get(i).setEvaluationRanking(i + 1);
            }
            performanceAppraisal.setEvaluationState(2L);
        }
//        else{
//            //performanceAppraisal.setEvaluationState(2L);
//            performanceAppraisalMapper.deleteAttendSupplierByEvaluationId(performanceAppraisal.getEvaluationId());
//            insertAttendSupplier(performanceAppraisal);
//            performanceAppraisalMapper.deleteScoringDetailsByEvaluationId(performanceAppraisal.getEvaluationId());
//            insertScoringDetails(performanceAppraisal);
//        }
        performanceAppraisalMapper.deleteAttendSupplierByEvaluationId(performanceAppraisal.getEvaluationId());
        insertAttendSupplier(performanceAppraisal);
        performanceAppraisalMapper.deleteScoringDetailsByEvaluationId(performanceAppraisal.getEvaluationId());
        insertScoringDetails(performanceAppraisal);
        return performanceAppraisalMapper.updatePerformanceAppraisal(performanceAppraisal);
    }

    /**
     * 批量删除绩效考评
     *
     * @param evaluationIds 需要删除的绩效考评主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePerformanceAppraisalByEvaluationIds(Long[] evaluationIds) {
        performanceAppraisalMapper.deleteAttendSupplierByEvaluationIds(evaluationIds);
        performanceAppraisalMapper.deleteScoringDetailsByEvaluationIds(evaluationIds);
        return performanceAppraisalMapper.deletePerformanceAppraisalByEvaluationIds(evaluationIds);
    }

    /**
     * 删除绩效考评信息
     *
     * @param evaluationId 绩效考评主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePerformanceAppraisalByEvaluationId(Long evaluationId) {
        performanceAppraisalMapper.deleteAttendSupplierByEvaluationId(evaluationId);
        performanceAppraisalMapper.deleteScoringDetailsByEvaluationId(evaluationId);
        return performanceAppraisalMapper.deletePerformanceAppraisalByEvaluationId(evaluationId);
    }

    /**
     * 新增参评供应商信息
     *
     * @param performanceAppraisal 绩效考评对象
     */
    public void insertAttendSupplier(PerformanceAppraisal performanceAppraisal) {
        List<AttendSupplier> attendSupplierList = performanceAppraisal.getAttendSupplierList();
        Long evaluationId = performanceAppraisal.getEvaluationId();
        if (StringUtils.isNotNull(attendSupplierList)) {
            List<AttendSupplier> list = new ArrayList<AttendSupplier>();
            for (AttendSupplier attendSupplier : attendSupplierList) {
                attendSupplier.setEvaluationId(evaluationId);
                list.add(attendSupplier);
            }
            if (list.size() > 0) {
                performanceAppraisalMapper.batchAttendSupplier(list);
            }
        }
    }

    /**
     * 新增评分明细信息
     *
     * @param performanceAppraisal 绩效考评对象
     */
    public void insertScoringDetails(PerformanceAppraisal performanceAppraisal) throws CloneNotSupportedException {
        List<ScoringDetails> scoringDetailsList = performanceAppraisal.getScoringDetailsList();
        Long evaluationId = performanceAppraisal.getEvaluationId();
        scoringDetailsList.clear();
        if (StringUtils.isEmpty(scoringDetailsList) && scoringDetailsList.size() == 0) {
            //参选的供应商
            List<AttendSupplier> attendSupplierList = performanceAppraisal.getAttendSupplierList();

            Long evaluationTemplateId = performanceAppraisal.getEvaluationTemplateId();
            ScoringTemplate scoringTemplate = scoringTemplateMapper.selectScoringTemplateByScoringTemplateId(evaluationTemplateId);
            //参选供应商虚需要的指标
            List<ScoringDetails> scoringDetailsList1 = scoringTemplate.getScoringDetailsList();
            for (AttendSupplier attendSupplier : attendSupplierList) {
                for (ScoringDetails scoringDetails : scoringDetailsList1) {
//                System.out.println(attendSupplier);
//                System.out.println(scoringDetails);
//                scoringDetails.setScoringDetailsId(null);
                    ScoringDetails newScoringDetails = scoringDetails.clone();
                    newScoringDetails.setSupplierCode(attendSupplier.getSupplierCode());
                    newScoringDetails.setSupplierName(attendSupplier.getSupplierName());
                    newScoringDetails.setCategoryCode(attendSupplier.getCategoryCode());
                    newScoringDetails.setCategoryName(attendSupplier.getCategoryName());
                    scoringDetailsList.add(newScoringDetails);
                }
            }
            System.out.println(scoringDetailsList);
        }
//        else{
//            //Long scoringTemplateId = performanceAppraisal.getEvaluationTemplateId();
//            List<ScoringDetails> list = new ArrayList<ScoringDetails>();
//            for (ScoringDetails scoringDetails : scoringDetailsList) {
//                scoringDetails.setEvaluationId(evaluationId);
//                list.add(scoringDetails);
//            }
//            if (list.size() > 0) {
//                performanceAppraisalMapper.batchScoringDetails(list);
//            }
//        }
        List<ScoringDetails> list = new ArrayList<ScoringDetails>();
        for (ScoringDetails scoringDetails : scoringDetailsList) {
            scoringDetails.setEvaluationId(evaluationId);
            list.add(scoringDetails);
        }
        if (list.size() > 0) {
            performanceAppraisalMapper.batchScoringDetails(list);
        }
    }
}
