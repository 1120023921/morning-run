package cn.doublehh.system.service.impl;

import cn.doublehh.system.model.TSUserRole;
import cn.doublehh.system.dao.TSUserRoleMapper;
import cn.doublehh.system.service.TSUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人员角色关系表 服务实现类
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
@Service
public class TSUserRoleServiceImpl extends ServiceImpl<TSUserRoleMapper, TSUserRole> implements TSUserRoleService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TSUserRoleMapper tsUserRoleMapper;

    @Autowired
    private TSUserRoleService tsUserRoleService;

    @Override
    public Integer addUsersToRole(String[] userIds, String roleId) {
        return tsUserRoleMapper.addUsersToRole(userIds, roleId);
    }

    @Override
    public Integer deleteUsersFromRole(String[] userIds, String roleId) {
        return tsUserRoleMapper.deleteUsersFromRole(userIds, roleId);
    }

    @Override
    public Integer addRolesToUser(String[] roleIds, String uesrId) {
        return tsUserRoleMapper.addRolesToUser(roleIds, uesrId);
    }

    @Override
    public Integer deleteRolesFromUser(String[] roleIds, String uesrId) {
        return tsUserRoleMapper.deleteRolesFromUser(roleIds, uesrId);
    }
}
