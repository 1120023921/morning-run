package cn.doublehh.system.service.impl;

import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.dao.TSUserMapper;
import cn.doublehh.system.service.TSUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TSUserServiceImpl extends ServiceImpl<TSUserMapper, TSUser> implements TSUserService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TSUserMapper tsUserMapper;

    @Override
    public TSUser getUserWithRolesByUid(String uid) {
        return tsUserMapper.getUserWithRolesByUid(uid);
    }
}
