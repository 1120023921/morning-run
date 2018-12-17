package cn.doublehh.sport.service;

import cn.doublehh.sport.model.Grade;
import cn.doublehh.sport.vo.GradeView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-16
 */
public interface GradeService extends IService<Grade> {
    /**
     * 分类获取学生成绩
     *
     * @param jobNumber 学号
     * @param type      类别名称
     * @return
     */
    Map<String, List<GradeView>> getGradeByJobNumberAndType(String jobNumber, String type);

    /**
     * 根据测试类型和学号获取考勤次数
     *
     * @param type
     * @param jobNumber
     * @return
     */
    Map<String, List<GradeView>> getAttendanceVo(String jobNumber, String type);
}
