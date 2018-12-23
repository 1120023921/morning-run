package cn.doublehh.system.service.impl;

import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.dao.TSUserMapper;
import cn.doublehh.system.service.TSUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
@Service
@Slf4j
public class TSUserServiceImpl extends ServiceImpl<TSUserMapper, TSUser> implements TSUserService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TSUserMapper tsUserMapper;

    @Override
    public TSUser getUserWithRolesByUid(String uid) {
        log.info("TSUserServiceImpl [getUserWithRolesByUid] 获取用户信息（带角色信息）uid=" + uid);
        return tsUserMapper.getUserWithRolesByUid(uid);
    }

    @Override
    public TSUser getUserByWechatOpenId(String openid) {
        log.info("TSUserServiceImpl [getUserByWechatOpenId] 根据openid查询用户信息");
        return tsUserMapper.selectOne(new QueryWrapper<TSUser>().eq("wechat_openid", openid));
    }

    @Override
    public TSUser getUserByUid(String uid) {
        log.info("TSUserServiceImpl [getUserByUid] 获取用户信息uid=" + uid);
        return tsUserMapper.selectOne(new QueryWrapper<TSUser>().eq("uid", uid));
    }
}
