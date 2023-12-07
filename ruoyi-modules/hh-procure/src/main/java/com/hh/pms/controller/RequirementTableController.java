package com.hh.pms.controller;

import java.util.List;

import com.ruoyi.system.api.domain.RequirementTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hh.pms.service.IRequirementTableService;
import com.ruoyi.common.core.web.controller.BaseController;

/**
 * 需求类型Controller
 * 
 * @author ruoyi
 * @date 2023-12-01
 */
@RestController
@RequestMapping("/table")
public class RequirementTableController extends BaseController
{
    @Autowired
    private IRequirementTableService requirementTableService;

    /**
     * 查询需求类型列表
     */
    @GetMapping("/listRequirementTableName")
    public List<RequirementTable> list()
    {
       return requirementTableService.listRequirementTableName();
    }

}
