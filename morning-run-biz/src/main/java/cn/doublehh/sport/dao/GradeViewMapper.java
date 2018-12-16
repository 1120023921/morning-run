package cn.doublehh.sport.dao;

import cn.doublehh.sport.model.GradeView;
import cn.doublehh.sport.vo.AttendanceVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-01
 */
public interface GradeViewMapper extends BaseMapper<GradeView> {

    /**
     * 根据测试类型和学号获取考勤次数
     *
     * @param type
     * @param jobNumber
     * @return
     */
    List<AttendanceVo> getAttendanceVo(@Param("jobNumber") String jobNumber, @Param("type") String type);
}
