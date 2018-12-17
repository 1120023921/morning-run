package cn.doublehh.sport.service.impl;

import cn.doublehh.sport.model.Semester;
import cn.doublehh.sport.dao.SemesterMapper;
import cn.doublehh.sport.service.SemesterService;
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
 * @since 2018-12-17
 */
@Service
@Slf4j
public class SemesterServiceImpl extends ServiceImpl<SemesterMapper, Semester> implements SemesterService {

    @Autowired
    private SemesterMapper semesterMapper;

    @Override
    public Semester getSemester(String id) {
        log.info("SemesterServiceImpl [getSemester] 根据id获取学期信息 id=" + id);
        return semesterMapper.selectById(id);
    }
}
