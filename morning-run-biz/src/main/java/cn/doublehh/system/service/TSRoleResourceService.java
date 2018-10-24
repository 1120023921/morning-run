package cn.doublehh.system.service;

import cn.doublehh.system.model.TSRoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色资源关系表 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
public interface TSRoleResourceService extends IService<TSRoleResource> {
    /**
     * 添加资源到角色
     *
     * @param resourceIds
     * @param roleId
     * @return
     */
    Integer addResourcesToRole(String[] resourceIds, String roleId);

    /**
     * 从角色中移除资源
     *
     * @param resourceIds
     * @param roleId
     * @return
     */
    Integer deleteResourcesFromRole(String[] resourceIds, String roleId);
}
