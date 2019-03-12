//package cn.doublehh.system.security.realm;
//
//import cn.doublehh.system.dao.TSRoleResourceMapper;
//import cn.doublehh.system.dao.TSUserMapper;
//import cn.doublehh.system.dao.TSUserRoleMapper;
//import cn.doublehh.system.model.TSResource;
//import cn.doublehh.system.model.TSRole;
//import cn.doublehh.system.model.TSUser;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
//
///**
// * @Author wangzy
// * @Date : 2017/8/11
// * @Description :
// */
//public class SimpleRealm extends AuthorizingRealm {
//
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    private TSUserMapper userMapper;
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    private TSUserRoleMapper userRoleMapper;
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    private TSRoleResourceMapper roleResourceMapper;
//
//    /**
//     * 获取身份相关信息
//     *
//     * @param principalCollection
//     * @return
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        String uid = (String) principalCollection.getPrimaryPrincipal();
//        SimpleAuthorizationInfo simpleAuthenticationInfo = new SimpleAuthorizationInfo();
//        List<TSRole> roles = userRoleMapper.getRolesByUid(uid);
//        for (TSRole role : roles) {
//            if (!"Y".equals(role.getEnabled())) {
//                continue;
//            }
//            // 基于Role的权限信息
//            simpleAuthenticationInfo.addRole(role.getRoid());
//            // 基于允许的权限信息
//            List<TSResource> resources = roleResourceMapper.getResourcesByRoleId(role.getId());
//            for (TSResource resource : resources) {
//                if ("Y".equals(resource.getEnabled()) && !StringUtils.isEmpty(resource.getValue())) {
//                    simpleAuthenticationInfo.addStringPermission(resource.getValue());
//                }
//            }
//        }
//        return simpleAuthenticationInfo;
//    }
//
//    /**
//     * 获取身份验证相关信息
//     *
//     * @param authenticationToken
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        String uid = (String) authenticationToken.getPrincipal();
//        TSUser user = userMapper.getUserWithRolesByUid(uid);
//        if (user == null) {
//            throw new UnknownAccountException();
//        }
//        if (!"Y".equals(user.getEnabled())) {
//            throw new LockedAccountException();
//        }
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUid(), user.getPassword(), getName());
//        return authenticationInfo;
//    }
//
//}
