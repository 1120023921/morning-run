package cn.doublehh.system.service;

import cn.doublehh.system.model.TSUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

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

    /**
     * 根据微信openid获取用户信息
     *
     * @param openid
     * @return
     */
    TSUser getUserByWechatOpenId(String openid);

    /**
     * 根据uid获取用户信息
     *
     * @param uid
     * @return
     */
    TSUser getUserByUid(String uid);

    /**
     * 导入学生信息
     *
     * @param in 文件输入流
     * @return 导入结果
     */
    Boolean importStudentInfo(InputStream in);

    /**
     * 根据角色获取用户
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    List<TSUser> getUserByRoleId(String roleId);
}
