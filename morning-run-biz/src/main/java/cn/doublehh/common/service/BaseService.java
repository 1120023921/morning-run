package cn.doublehh.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author 胡昊
 * Description:
 * Date: 2018/8/9
 * Time: 10:41
 * Create: DoubleH
 */
public interface BaseService<T> {

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    Integer insert(T entity);

    /**
     * 按主键批量删除
     *
     * @param keys
     * @return
     */
    Integer delete(List keys);

    /**
     * 根据主键差量更新
     *
     * @param entity
     * @return
     */
    Integer update(T entity);

    /**
     * 按条件分页查询
     *
     * @param entity
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<T> select(T entity, Integer pageNum, Integer pageSize, boolean listMode, String[] descs, String[] ascs);
}
