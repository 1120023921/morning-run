package cn.doublehh.sport.service;

import cn.doublehh.sport.model.GradeView;
import cn.doublehh.sport.vo.AttendanceVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2018-11-19
 */
public interface GradeViewService extends IService<GradeView> {

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
    Map<String, List<AttendanceVo>> getAttendanceVo(String jobNumber, String type);
}
