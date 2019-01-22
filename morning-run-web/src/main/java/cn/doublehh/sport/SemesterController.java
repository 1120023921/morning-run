package cn.doublehh.sport;


import cn.doublehh.common.annotation.NeedPermission;
import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.sport.model.Semester;
import cn.doublehh.sport.service.SemesterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取所有学期信息列表
     *
     * @return 学期信息列表
     */
    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R<List<Semester>> findAll() {
        List<Semester> semesterList = semesterService.list(new QueryWrapper<Semester>().eq("is_valid", 1));
        semesterList = semesterList.stream().sorted(Comparator.comparing(Semester::getWeight).reversed()).collect(Collectors.toList());
        return R.restResult(semesterList, ErrorCodeInfo.SUCCESS);
    }

    /**
     * 新增学期信息
     *
     * @param semester 学期信息对象
     * @return 新增结果
     */
    @NeedPermission(roleIds = {"admin"})
    @PostMapping(value = "/insertSemester", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R insertSemester(@RequestBody Semester semester) {
        return R.restResult(semesterService.insertSemester(semester), ErrorCodeInfo.SUCCESS);
    }

    /**
     * 更新学期信息
     *
     * @param semester 学期信息对象
     * @return 新增结果
     */
    @NeedPermission(roleIds = {"admin"})
    @PatchMapping(value = "/updateSemester", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R updateSemester(@RequestBody Semester semester) {
        return R.restResult(semesterService.updateSemester(semester), ErrorCodeInfo.SUCCESS);
    }

    /**
     * 根据id查询学期信息
     *
     * @param id 学期id
     * @return 学期信息
     */
    @GetMapping(value = "/getSemesterById")
    public R getSemesterById(String id) {
        return R.restResult(semesterService.getSemester(id), ErrorCodeInfo.SUCCESS);
    }

    /**
     * 根据id删除学期信息
     *
     * @param id 学期id
     * @return 删除结果
     */
    @NeedPermission(roleIds = {"admin"})
    @DeleteMapping(value = "/deleteSemesterById/{id}")
    public R deleteSemesterById(@PathVariable("id") String id) {
        return R.restResult(semesterService.deleteSemester(id), ErrorCodeInfo.SUCCESS);
    }
}

