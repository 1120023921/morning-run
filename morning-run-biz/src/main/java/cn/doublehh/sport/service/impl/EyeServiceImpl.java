package cn.doublehh.sport.service.impl;

import cn.doublehh.sport.model.Eye;
import cn.doublehh.sport.dao.EyeMapper;
import cn.doublehh.sport.service.EyeService;
import cn.doublehh.system.service.TSUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 胡昊
 * @since 2019-10-17
 */
@Service
public class EyeServiceImpl extends ServiceImpl<EyeMapper, Eye> implements EyeService {

    @Autowired
    private TSUserService userService;

    @Override
    public Boolean insertEye(Eye eye) {
        if (StringUtils.isEmpty(eye.getId())) {
            eye.setId(UUID.randomUUID().toString());
            eye.setName(userService.getUserByUid(eye.getUserId()).getName());
            return save(eye);
        } else {
            eye.setStatus(0);
            return updateById(eye);
        }
    }

    @Override
    public IPage<Eye> queryEye(Integer pageNum, Integer pageSize, Eye eye) {
        return page(new Page<>(pageNum, pageSize), new QueryWrapper<>(eye));
    }

    @Override
    public Boolean reviewEye(Eye eye) {
        return updateById(eye);
    }

    @Override
    public Eye getUserEye(String jobNumber) {
        return getOne(new QueryWrapper<Eye>().eq("user_id", jobNumber));
    }
}
