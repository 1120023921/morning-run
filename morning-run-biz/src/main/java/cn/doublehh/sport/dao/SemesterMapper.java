package cn.doublehh.sport.dao;

import cn.doublehh.sport.model.Semester;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-17
 */
public interface SemesterMapper extends BaseMapper<Semester> {

    /**
     * 获取最大权重
     *
     * @return 最大权重
     */
    Integer getNewWeight();
}
