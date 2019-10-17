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
    Long getTodayLog(@Param("today") String today, @Param("tomorrow") String tomorrow);
}
