package cn.doublehh.system.service;

import cn.doublehh.system.model.TSUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 人员角色关系表 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
public interface TSUserRoleService extends IService<TSUserRole> {
    /**
     * 添加用户到角色
     *
     * @param userIds
     * @param roleId
     * @return
     */
    Integer addUsersToRole(String[] userIds, String roleId);

    /**
     * 从角色中移除用户
     *
     * @param userIds
     * @param roleId
     * @return
     */
    Integer deleteUsersFromRole(String[] userIds, String roleId);

    /**
     * 添加角色到用户
     *
     * @param roleIds
     * @param uesrId
     * @return
     */
    Integer addRolesToUser(String[] roleIds, String uesrId);

    /**
     * 从用户中移除角色
     *
     * @param roleIds
     * @param uesrId
     * @return
     */
    Integer deleteRolesFromUser(String[] roleIds, String uesrId);

    /**
     * 根据角色获取用户id列表
     *
     * @param roleId
     * @return 用户id列表
     */
    List<String> getUserIdByRoleId(String roleId);
}
