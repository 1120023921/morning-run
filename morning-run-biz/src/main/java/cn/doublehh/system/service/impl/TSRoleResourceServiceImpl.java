package cn.doublehh.system.service.impl;

import cn.doublehh.system.model.TSRoleResource;
import cn.doublehh.system.dao.TSRoleResourceMapper;
import cn.doublehh.system.service.TSRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色资源关系表 服务实现类
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
@Service
public class TSRoleResourceServiceImpl extends ServiceImpl<TSRoleResourceMapper, TSRoleResource> implements TSRoleResourceService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TSRoleResourceMapper tsRoleResourceMapper;

    @Override
    public Integer addResourcesToRole(String[] resourceIds, String roleId) {
        return tsRoleResourceMapper.addResourcesToRole(resourceIds, roleId);
    }

    @Override
    public Integer deleteResourcesFromRole(String[] resourceIds, String roleId) {
        return tsRoleResourceMapper.deleteResourcesFromRole(resourceIds, roleId);
    }
}
