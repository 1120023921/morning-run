package cn.doublehh.sport.dao;

import cn.doublehh.sport.model.Carousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 胡昊
 * @since 2019-01-03
 */
public interface CarouselMapper extends BaseMapper<Carousel> {

    /**
     * 获取最大权重
     * @return
     */
    Integer getNewWeight();
}
