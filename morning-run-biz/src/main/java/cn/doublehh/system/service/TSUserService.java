package cn.doublehh.system.service;

import cn.doublehh.system.model.TSUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
public interface TSUserService extends IService<TSUser> {
    /**
     * 根据id获取用户信息（带角色信息）
     *
     * @param uid
     * @return
     */
    TSUser getUserWithRolesByUid(String uid);
}
