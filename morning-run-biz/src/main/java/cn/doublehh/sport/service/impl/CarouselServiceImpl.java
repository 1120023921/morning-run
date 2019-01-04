package cn.doublehh.sport.service.impl;

import cn.doublehh.sport.model.Carousel;
import cn.doublehh.sport.dao.CarouselMapper;
import cn.doublehh.sport.service.CarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 胡昊
 * @since 2019-01-03
 */
@Slf4j
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Transactional
    @Override
    public Boolean insertCarousel(Carousel carousel) {
        log.info("CarouselServiceImpl [insertCarousel] 新增轮播 carousel=" + carousel);
        carousel.setId(UUID.randomUUID().toString());
        carousel.setCreateTime(LocalDateTime.now());
        carousel.setUpdateTime(LocalDateTime.now());
        carousel.setVersion(1);
        carousel.setIsValid(1);
        return save(carousel);
    }

    @Override
    public Boolean updateCarousel(Carousel carousel) {
        log.info("CarouselServiceImpl [insertCarousel] 更新轮播 carousel=" + carousel);
        Carousel carouselDb = getById(carousel.getId());
        carouselDb.setTitle(carousel.getTitle());
        carouselDb.setPic(carousel.getPic());
        carouselDb.setUrl(carousel.getUrl());
        carouselDb.setWeight(carousel.getWeight());
        carouselDb.setUpdateTime(LocalDateTime.now());
        carouselDb.setVersion(carouselDb.getVersion() + 1);
        return updateById(carouselDb);
    }

    @Override
    public Integer getNewWeight() {
        Integer weight = carouselMapper.getNewWeight();
        if (null == weight) {
            return 1;
        }
        return weight + 1;
    }
}
