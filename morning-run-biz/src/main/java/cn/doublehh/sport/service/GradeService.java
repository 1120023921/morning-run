package cn.doublehh.sport.service;

import cn.doublehh.sport.model.Grade;
import cn.doublehh.sport.vo.AttendanceGradeDetailParam;
import cn.doublehh.sport.vo.GradeView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.BufferedReader;
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

    /**
     * 获取考勤详细信息
     *
     * @param attendanceGradeDetailParam 查询考勤成绩详情参数封装类
     * @return
     */
    List<GradeView> getAttendanceGradeDetail(AttendanceGradeDetailParam attendanceGradeDetailParam);

    /**
     * 上传成绩
     *
     * @param reader    成绩文件的输入流
     * @param gradeList 返回待插入的数据集合
     * @param semester  学期
     * @return
     */
    Boolean uploadGrade(BufferedReader reader, List<Grade> gradeList, String semester);

    /**
     * 上传成绩消息提醒
     *
     * @param gradeList 成绩列表
     * @return 发送失败的列表
     */
    List<Grade> sendUploadGradeMsg(List<Grade> gradeList);
}
