package cn.doublehh.sport;


import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.sport.model.Semester;
import cn.doublehh.sport.service.SemesterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-17
 */
@RestController
@RequestMapping("/semester")
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R<List<Semester>> findAll() {
        List<Semester> semesterList = semesterService.list(new QueryWrapper<Semester>().eq("is_valid", 1));
        semesterList = semesterList.stream().sorted(Comparator.comparing(Semester::getSemester).reversed()).collect(Collectors.toList());
        return R.restResult(semesterList, ErrorCodeInfo.SUCCESS);
    }
}

