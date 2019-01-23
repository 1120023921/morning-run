package cn.doublehh.system.service.impl;

import cn.doublehh.system.model.TSUserRole;
import cn.doublehh.system.dao.TSUserRoleMapper;
import cn.doublehh.system.service.TSUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 人员角色关系表 服务实现类
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
@Slf4j
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

    @Override
    public List<String> getUserIdByRoleId(String roleId) {
        log.info("TSUserRoleServiceImpl [getUserIdByRoleId] 根据角色获取用户信息roleId=" + roleId);
        List<TSUserRole> tsUserRoleList = list(new QueryWrapper<TSUserRole>().eq("role_id", roleId));
        if (!CollectionUtils.isEmpty(tsUserRoleList)) {
            return tsUserRoleList.stream().map(TSUserRole::getUserId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
