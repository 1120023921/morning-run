package cn.doublehh.system.service.impl;

import cn.doublehh.common.utils.ExcelUtil;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.dao.TSUserMapper;
import cn.doublehh.system.model.TSUserRole;
import cn.doublehh.system.service.TSUserRoleService;
import cn.doublehh.system.service.TSUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

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
    @Autowired
    private TSUserRoleService tsUserRoleService;

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

    @Override
    @Transactional
    public Boolean importStudentInfo(InputStream in) {
        log.info("TSUserServiceImpl [importStudentInfo] 导入学生信息");
        //待插入用户列表
        List<TSUser> tsUserList = new ArrayList<>();
        //待插入用户角色列表
        List<TSUserRole> tsUserRoleList = new ArrayList<>();
        try {
            List<List<String>> readResult = ExcelUtil.readXlsx(in);
            readResult.forEach(row -> {
                //导入excel不完整9列补全信息
                if (row.size() < 9) {
                    for (int i = row.size(); i < 9; i++) {
                        row.add("");
                    }
                }
                String id = UUID.randomUUID().toString();
                TSUser tsUser = new TSUser();
                tsUser.setId(id);
                tsUser.setUid(row.get(0));
                tsUser.setName(row.get(1));
                tsUser.setPassword(row.get(2));
                tsUser.setSex("男".equals(row.get(3)) ? "1" : "女".equals(row.get(3)) ? "2" : "");
                tsUser.setTel(row.get(4));
                tsUser.setMail(row.get(5));
                tsUser.setQqNumber(row.get(6));
                tsUser.setWechatNumber(row.get(7));
                tsUser.setCreateTime(LocalDateTime.now());
                tsUser.setUpdateTime(LocalDateTime.now());
                tsUserList.add(tsUser);
                TSUserRole tsUserRole = new TSUserRole();
                tsUserRole.setId(UUID.randomUUID().toString());
                tsUserRole.setUserId(id);
                tsUserRole.setRoleId(row.get(8));
                tsUserRoleList.add(tsUserRole);
            });
            if (!saveBatch(tsUserList)) {
                throw new RuntimeException("导入学生信息失败");
            }
            if (!tsUserRoleService.saveBatch(tsUserRoleList)) {
                throw new RuntimeException("导入初始角色分配失败");
            }
            return true;
        } catch (Exception e) {
            log.error("TSUserServiceImpl [importStudentInfo] 读取导入Excel失败", e);
            return false;
        }
    }

    @Override
    public List<TSUser> getUserByRoleId(String roleId) {
        log.info("TSUserServiceImpl [getUserByRoleId] 根据角色获取用户信息 roleId=" + roleId);
        List<TSUser> userList = tsUserMapper.getUserByRoleId(roleId);
        if (!CollectionUtils.isEmpty(userList)) {
            return userList;
        }
        return new ArrayList<>();
    }
}
