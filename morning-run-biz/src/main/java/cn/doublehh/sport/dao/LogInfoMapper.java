package cn.doublehh.sport.dao;

import cn.doublehh.sport.model.LogInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 胡昊
 * @since 2019-01-21
 */
public interface LogInfoMapper extends BaseMapper<LogInfo> {

    /**
     * 获取当天日志
     *
     * @return 日志列表
     */
    List<LogInfo> getTodayLog(@Param("today") String today, @Param("tomorrow") String tomorrow);

    /**
     * 获取当天日志数量
     *
     * @return 日志数量
     */
    Integer getTodayLogNum(@Param("today") String today, @Param("tomorrow") String tomorrow);

    /**
     * 获取所有日志数
     *
     * @return 日志数
     */
    Integer getAllLogNum();
}
