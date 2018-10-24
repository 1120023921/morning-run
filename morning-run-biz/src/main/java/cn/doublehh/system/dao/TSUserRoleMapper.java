package cn.doublehh.system.dao;

import cn.doublehh.system.model.TSRole;
import cn.doublehh.system.model.TSUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 人员角色关系表 Mapper 接口
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
public interface TSUserRoleMapper extends BaseMapper<TSUserRole> {
    /**
     * 添加用户到角色
     *
     * @param userIds
     * @param roleId
     * @return
     */
    Integer addUsersToRole(@Param("userIds") String[] userIds, @Param("roleId") String roleId);

    /**
     * 从角色中移除用户
     *
     * @param userIds
     * @param roleId
     * @return
     */
    Integer deleteUsersFromRole(@Param("userIds") String[] userIds, @Param("roleId") String roleId);

    /**
     * 添加角色到用户
     *
     * @param roleIds
     * @param uesrId
     * @return
     */
    Integer addRolesToUser(@Param("roleIds") String[] roleIds, @Param("userId") String uesrId);

    /**
     * 从用户中移除角色
     *
     * @param roleIds
     * @param uesrId
     * @return
     */
    Integer deleteRolesFromUser(@Param("roleIds") String[] roleIds, @Param("userId") String uesrId);

    /**
     * 获取用户所有角色
     *
     * @param uid
     * @return
     */
    List<TSRole> getRolesByUid(@Param("uid") String uid);
}
