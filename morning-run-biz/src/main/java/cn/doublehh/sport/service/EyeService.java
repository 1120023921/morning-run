package cn.doublehh.sport.service;

import cn.doublehh.sport.model.Eye;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2019-10-17
 */
public interface EyeService extends IService<Eye> {

    /**
     * @param eye 视力信息
     * @return 新增结果
     * @author 胡昊
     * @date 2019/10/17 19:08
     */
    Boolean insertEye(Eye eye);

    IPage<Eye> queryEye(Integer pageNum, Integer pageSize, Eye eye);

    Boolean reviewEye(Eye eye);

    Eye getUserEye(String jobNumber);
}
