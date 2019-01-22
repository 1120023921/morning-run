package cn.doublehh.sport.service.impl;

import cn.doublehh.sport.model.Semester;
import cn.doublehh.sport.dao.SemesterMapper;
import cn.doublehh.sport.service.SemesterService;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @Override
    public Integer getNewWeight() {
        log.info("SemesterServiceImpl [getSemester] 获取最大权重");
        Integer weight = semesterMapper.getNewWeight();
        if (null == weight) {
            return 1;
        }
        return weight + 1;
    }

    @Transactional
    @Override
    public Boolean insertSemester(Semester semester) {
        log.info("SemesterServiceImpl [insertSemester] 新增学期信息 semester=" + semester);
        semester.setId(UUID.randomUUID().toString());
        semester.setCreateTime(LocalDateTime.now());
        semester.setUpdateTime(LocalDateTime.now());
        semester.setWeight(getNewWeight());
        semester.setVersion(1);
        semester.setIsValid(1);
        if (!save(semester)) {
            throw new RuntimeException("SemesterServiceImpl [insertSemester] 新增学期失败");
        }
        return true;
    }

    @Override
    public Boolean updateSemester(Semester semester) {
        log.info("SemesterServiceImpl [updateSemester] 更新学期信息 semester=" + semester);
        Semester semesterDb = getById(semester.getId());
        semesterDb.setSemester(semester.getSemester());
        semesterDb.setWeight(semester.getWeight());
        semesterDb.setUpdateTime(LocalDateTime.now());
        semesterDb.setVersion(semesterDb.getVersion() + 1);
        if (!updateById(semester)) {
            throw new RuntimeException("SemesterServiceImpl [updateSemester] 更新学期信息失败");
        }
        return true;
    }

    @Override
    public Boolean deleteSemester(String id) {
        log.info("SemesterServiceImpl [deleteSemester] 删除学期信息 id=" + id);
        Semester semesterDb = getById(id);
        semesterDb.setVersion(semesterDb.getVersion() + 1);
        semesterDb.setUpdateTime(LocalDateTime.now());
        semesterDb.setIsValid(0);
        if (!updateById(semesterDb)) {
            throw new RuntimeException("SemesterServiceImpl [deleteSemester] 删除学期信息失败");
        }
        return true;
    }
}
