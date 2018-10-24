package cn.doublehh.common.controller;

import cn.doublehh.common.pojo.ErrorCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 胡昊
 * Description:
 * Date: 2018/9/5
 * Time: 13:44
 * Create: DoubleH
 */
public class BaseController<T> extends ApiController {

    @Autowired
    private IService<T> baseService;

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R insert(@RequestBody T entity) {
        ErrorCode<T> errorCode = new ErrorCode<>();
        try {
            Field id = entity.getClass().getDeclaredField("id");
            if (null != id && StringUtils.isEmpty(id.get(entity)) && "class java.lang.String".equals(id.getGenericType().toString())) {
                Method setId = entity.getClass().getMethod("setId", String.class);
                setId.invoke(entity, UUID.randomUUID().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Field createTime = entity.getClass().getDeclaredField("createTime");
            if (null != createTime) {
                if ("class java.util.Date".equals(createTime.getGenericType().toString())) {
                    Method setCreateTime = entity.getClass().getMethod("setCreateTime", Date.class);
                    setCreateTime.invoke(entity, new Date());
                } else if ("class java.time.LocalDateTime".equals(createTime.getGenericType().toString())) {
                    Method setCreateTime = entity.getClass().getMethod("setCreateTime", LocalDateTime.class);
                    setCreateTime.invoke(entity, LocalDateTime.now());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (baseService.save(entity)) {
            errorCode.setCode(ErrorCode.CREATED);
            errorCode.setMsg(ErrorCode.CREATED_MSG);
        } else {
            errorCode.setCode(ErrorCode.UNPROCESABLE_ENTITY);
            errorCode.setMsg(ErrorCode.UNPROCESABLE_ENTITY_MSG);
        }
        return R.restResult(null, errorCode);
    }

    /**
     * 根据主键批量删除
     *
     * @param keys
     * @return
     */
    @ApiOperation(value = "根据主键批量删除", notes = "根据主键批量删除", httpMethod = "DELETE")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public R delete(@RequestBody List<Serializable> keys) {
        ErrorCode errorCode = new ErrorCode();
        if (baseService.removeByIds(keys)) {
            errorCode.setCode(ErrorCode.NO_CONTENT);
            errorCode.setMsg(ErrorCode.NO_CONTENT_MSG);
        } else {
            errorCode.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
            errorCode.setMsg(ErrorCode.DELETE_FAIL);
        }
        return R.restResult(null, errorCode);
    }

    /**
     * 差量更新
     *
     * @param entity
     * @return
     */
    @ApiOperation(value = "差量更新", notes = "差量更新", httpMethod = "PATCH")
    @RequestMapping(value = "/update", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public R update(@RequestBody T entity) {
        ErrorCode errorCode = new ErrorCode();
        try {
            Field updateTime = entity.getClass().getDeclaredField("updateTime");
            if (null != updateTime) {
                if ("class java.util.Date".equals(updateTime.getGenericType().toString())) {
                    Method setCreateTime = entity.getClass().getMethod("setUpdateTime", Date.class);
                    setCreateTime.invoke(entity, new Date());
                } else if ("class java.time.LocalDateTime".equals(updateTime.getGenericType().toString())) {
                    Method setCreateTime = entity.getClass().getMethod("setUpdateTime", LocalDateTime.class);
                    setCreateTime.invoke(entity, LocalDateTime.now());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (baseService.updateById(entity)) {
            errorCode.setCode(ErrorCode.OK);
            errorCode.setMsg(ErrorCode.OK_MSG);
        } else {
            errorCode.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
            errorCode.setMsg(ErrorCode.UPDATE_FAIL);
        }
        return R.restResult(null, errorCode);
    }

    /**
     * 按条件分页获取
     *
     * @param entity
     * @param pageNum
     * @param pageSize
     * @param descs
     * @param ascs
     * @return
     */
    @ApiOperation(value = "按条件分页获取", notes = "按条件分页获取", httpMethod = "POST")
    @RequestMapping(value = "/select", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R select(@RequestBody T entity, Integer pageNum, Integer pageSize, String[] descs, String[] ascs) {
        ErrorCode errorCode = new ErrorCode();
        Page<T> page = new Page<>(pageNum, pageSize);
        page.setAsc(ascs);
        page.setDesc(descs);
        IPage<T> result = baseService.page(page, new QueryWrapper<T>().setEntity(entity));
        errorCode.setCode(ErrorCode.OK);
        errorCode.setMsg(ErrorCode.OK_MSG);
        return R.restResult(result, errorCode);
    }
}
