package cn.doublehh.sport;


import cn.doublehh.common.annotation.NeedPermission;
import cn.doublehh.common.constant.CosClientConstant;
import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.sport.model.Carousel;
import cn.doublehh.sport.service.CarouselService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2019-01-03
 */
@Slf4j
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private COSClient cosClient;
    @Autowired
    private CosClientConstant cosClientConstant;

    /**
     * 新增轮播
     *
     * @param pic      轮播图片
     * @param carousel 轮播信息
     * @return 新增结果
     */
    @CacheEvict(value = {"CarouselController:findAll", "CarouselController:getCarouselById"}, allEntries = true)
    @NeedPermission(roleIds = {"admin", "teacher"})
    @PostMapping(value = "/uploadCarousel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R uploadCarousel(@RequestParam("uploadPic") MultipartFile pic, Carousel carousel) {
        Assert.notNull(pic, "图片不能为空");
        Assert.notNull(carousel, "轮播信息不能为空");
        try {
            InputStream inputStream = pic.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(pic.getContentType());
            objectMetadata.setContentLength(pic.getSize());
            String originalFilename = pic.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.indexOf("."));
            cosClient.putObject(cosClientConstant.getBucketName(), "carousel/" + fileName, inputStream, objectMetadata);
            carousel.setPic(cosClientConstant.getResourcesUrl() + "carousel/" + fileName);
            carousel.setWeight(carouselService.getNewWeight());
            return R.restResult(carouselService.insertCarousel(carousel), ErrorCodeInfo.SUCCESS);
        } catch (IOException e) {
            log.error("获取文件输入流失败", e);
            return R.restResult("获取文件输入流失败", ErrorCodeInfo.FAILED);
        }
    }

    /**
     * 更新轮播信息
     *
     * @param pic      轮播图片
     * @param carousel 轮播信息
     * @return 更新结果
     */
    @CacheEvict(value = {"CarouselController:findAll", "CarouselController:getCarouselById"}, allEntries = true)
    @NeedPermission(roleIds = {"admin", "teacher"})
    @PatchMapping(value = "/updateCarousel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R updateCarousel(@RequestParam(required = false, value = "uploadPic") MultipartFile pic, Carousel carousel) {
        Assert.notNull(carousel, "轮播信息不能为空");
        try {
            if (null != pic) {
                InputStream inputStream = pic.getInputStream();
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(pic.getContentType());
                objectMetadata.setContentLength(pic.getSize());
                String originalFilename = pic.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.indexOf("."));
                cosClient.putObject(cosClientConstant.getBucketName(), "carousel/" + fileName, inputStream, objectMetadata);
                //删除原来的图片
                cosClient.deleteObject(cosClientConstant.getBucketName(), carousel.getPic().substring(carousel.getPic().indexOf("carousel/")));
                carousel.setPic(cosClientConstant.getResourcesUrl() + "carousel/" + fileName);
            }
            return R.restResult(carouselService.updateCarousel(carousel), ErrorCodeInfo.SUCCESS);
        } catch (IOException e) {
            log.error("获取文件输入流失败", e);
            return R.restResult("获取文件输入流失败", ErrorCodeInfo.FAILED);
        }
    }

    /**
     * 获取所有轮播信息
     *
     * @return 返回轮播信息列表
     */
    @Cacheable(value = "CarouselController:findAll")
    @GetMapping(value = "/findAll")
    public R findAll() {
        List<Carousel> carouselList = carouselService.list(new QueryWrapper<Carousel>().eq("is_valid", 1).orderByDesc("weight"));
        return R.restResult(carouselList, ErrorCodeInfo.SUCCESS);
    }

    /**
     * 根据id查询轮播信息
     *
     * @param id 轮播id
     * @return 轮播信息
     */
    @Cacheable(value = "CarouselController:getCarouselById")
    @GetMapping(value = "/getCarouselById")
    public R getCarouselById(String id) {
        return R.restResult(carouselService.getById(id), ErrorCodeInfo.SUCCESS);
    }

    /**
     * 删除轮播信息
     *
     * @param id 轮播id
     * @return 删除结果
     */
    @CacheEvict(value = {"CarouselController:findAll", "CarouselController:getCarouselById"}, allEntries = true)
    @NeedPermission(roleIds = {"admin", "teacher"})
    @DeleteMapping(value = "/deleteCarouselById/{id}")
    public R deleteCarouselById(@PathVariable("id") String id) {
        Carousel carousel = carouselService.getById(id);
        cosClient.deleteObject(cosClientConstant.getBucketName(), carousel.getPic().substring(carousel.getPic().indexOf("carousel/")));
        return R.restResult(carouselService.deleteCarousel(id), ErrorCodeInfo.SUCCESS);
    }
}

