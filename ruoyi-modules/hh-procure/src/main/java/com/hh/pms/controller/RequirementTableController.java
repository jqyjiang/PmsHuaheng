package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.hh.pms.domain.RequirementTable;
import com.hh.pms.service.IRequirementTableService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

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
