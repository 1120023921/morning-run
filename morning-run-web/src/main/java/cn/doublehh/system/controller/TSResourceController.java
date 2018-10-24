package cn.doublehh.system.controller;


import cn.doublehh.common.controller.BaseController;
import cn.doublehh.system.model.TSResource;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
@Api(tags = "资源管理")
@RestController
@RequestMapping("/tSResource")
public class TSResourceController extends BaseController<TSResource> {

}

