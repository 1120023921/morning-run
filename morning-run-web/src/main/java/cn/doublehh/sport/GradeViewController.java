package cn.doublehh.sport;


import cn.doublehh.common.controller.BaseController;
import cn.doublehh.sport.model.GradeView;
import cn.doublehh.sport.service.GradeViewService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * <p>
 * VIEW 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2018-10-25
 */
@Controller
@RequestMapping("/gradeView")
public class GradeViewController extends BaseController<GradeView> {

    @Autowired
    private GradeViewService gradeViewService;

    /**
     * 跳转展示成绩信息界面
     *
     * @param jobNumber
     * @param map
     * @return
     */
    @RequestMapping("/showGrade")
    public String getGradeByJobNumber(String jobNumber, ModelMap map) {
        IPage<Map<String, Object>> grade = gradeViewService.pageMaps(new Page<GradeView>(1,-1),new QueryWrapper<GradeView>().eq("job_number", jobNumber));
        map.put("grade", grade.getRecords() );
        return "showGrade";
    }
}

