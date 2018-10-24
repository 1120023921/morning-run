package cn.doublehh.common.service.impl;

import cn.doublehh.common.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 胡昊
 * Description:
 * Date: 2018/8/9
 * Time: 10:44
 * Create: DoubleH
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    public Integer insert(T entity) {
        try {
            Field id = entity.getClass().getDeclaredField("id");
            if (null != id && "class java.lang.String".equals(id.getGenericType().toString())) {
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
//            e.printStackTrace();
        }
        return baseMapper.insert(entity);
    }

    @Override
    public Integer delete(List keys) {
        return baseMapper.deleteBatchIds(keys);
    }

    @Override
    public Integer update(T entity) {
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
        return baseMapper.updateById(entity);
    }

    @Override
    public IPage<T> select(T entity, Integer pageNum, Integer pageSize, boolean listMode, String[] descs, String[] ascs) {
        Page page = new Page(pageNum, pageSize);
        IPage iPage = null;
        if (null != descs && descs.length > 0) {
            iPage = baseMapper.selectPage(page, new QueryWrapper<T>().setEntity(entity).orderByDesc(descs));
        } else if (null != ascs && ascs.length > 0) {
            iPage = baseMapper.selectPage(page, new QueryWrapper<T>().setEntity(entity).orderByAsc(ascs));
        } else {
            iPage = baseMapper.selectPage(page, new QueryWrapper<T>().setEntity(entity));
        }
        return iPage;
    }
}
