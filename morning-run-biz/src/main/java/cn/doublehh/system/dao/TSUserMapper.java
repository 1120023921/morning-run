package cn.doublehh.system.dao;

import cn.doublehh.system.model.TSUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
public interface TSUserMapper extends BaseMapper<TSUser> {
    /**
     * 根据id获取用户信息（带角色信息）
     *
     * @param uid
     * @return
     */
    TSUser getUserWithRolesByUid(@Param("uid") String uid);

    /**
     * 根据角色获取用户
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    List<TSUser> getUserByRoleId(@Param("roleId") String roleId);
}
