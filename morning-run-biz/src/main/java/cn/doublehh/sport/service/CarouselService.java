package cn.doublehh.sport.service;

import cn.doublehh.sport.model.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2019-01-03
 */
public interface CarouselService extends IService<Carousel> {

    /**
     * 新建轮播
     *
     * @param carousel 轮播对象
     * @return 插入结果
     */
    Boolean insertCarousel(Carousel carousel);

    /**
     * 更新轮播
     *
     * @param carousel 轮播对象
     * @return 更新结果
     */
    Boolean updateCarousel(Carousel carousel);

    /**
     * 获取最大权重
     *
     * @return 最大权重
     */
    Integer getNewWeight();
}
