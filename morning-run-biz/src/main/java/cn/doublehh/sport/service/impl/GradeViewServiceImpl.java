package cn.doublehh.sport.service.impl;

import cn.doublehh.sport.model.GradeView;
import cn.doublehh.sport.dao.GradeViewMapper;
import cn.doublehh.sport.service.GradeViewService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Map;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author 胡昊
 * @since 2018-11-19
 */
@Service
public class GradeViewServiceImpl extends ServiceImpl<GradeViewMapper, GradeView> implements GradeViewService {

    @Override
    public IPage<Map<String, Object>> getGradeByJobNumber(String jobNumber) {
        IPage<Map<String, Object>> grade = pageMaps(new Page<GradeView>(1, -1), new QueryWrapper<GradeView>().eq("job_number", jobNumber));
        return grade;
    }
}
