package cn.doublehh.sport.dao;

import cn.doublehh.sport.model.Grade;
import cn.doublehh.sport.vo.AttendanceGradeDetailParam;
import cn.doublehh.sport.vo.GradeView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-16
 */
public interface GradeMapper extends BaseMapper<Grade> {

    /**
     * 获取测试成绩
     *
     * @param jobNumber 学号
     * @param type      项目类型
     * @return
     */
    List<GradeView> getGrade(@Param("jobNumber") String jobNumber, @Param("type") String type);

    /**
     * 获取测试成绩
     *
     * @param jobNumber  学号
     * @param type       项目类型
     * @param semesterId 学期id
     * @return
     */
    List<GradeView> getGradeBySemester(@Param("jobNumber") String jobNumber, @Param("type") String type, @Param("semesterId") String semesterId);

    /**
     * 获取考勤成绩
     *
     * @param jobNumber 学号
     * @param type      项目类型
     * @return
     */
    List<GradeView> getAttendanceGrade(@Param("jobNumber") String jobNumber, @Param("type") String type);

    /**
     * 根据学期获取考勤成绩
     *
     * @param jobNumber  学号
     * @param type       项目类型
     * @param semesterId 学期id
     * @return
     */
    List<GradeView> getAttendanceGradeBySemester(@Param("jobNumber") String jobNumber, @Param("type") String type, @Param("semesterId") String semesterId);

    /**
     * 获取考勤详细信息
     *
     * @param attendanceGradeDetailParam 查询考勤成绩详情参数封装类
     * @return
     */
    List<GradeView> getAttendanceGradeDetail(AttendanceGradeDetailParam attendanceGradeDetailParam);
}
