package cn.doublehh.system.dao;

import cn.doublehh.system.model.TSResource;
import cn.doublehh.system.model.TSRoleResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色资源关系表 Mapper 接口
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
public interface TSRoleResourceMapper extends BaseMapper<TSRoleResource> {
    /**
     * 添加资源到角色
     *
     * @param resourceIds
     * @param roleId
     * @return
     */
    Integer addResourcesToRole(@Param("resourceIds") String[] resourceIds, @Param("roleId") String roleId);

    /**
     * 从角色中移除资源
     *
     * @param resourceIds
     * @param roleId
     * @return
     */
    Integer deleteResourcesFromRole(@Param("resourceIds") String[] resourceIds, @Param("roleId") String roleId);

    /**
     * 根据角色id获取资源
     *
     * @param roleId
     * @return
     */
    List<TSResource> getResourcesByRoleId(@Param("roleId") String roleId);
}
