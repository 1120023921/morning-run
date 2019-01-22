package cn.doublehh.sport.service;

import cn.doublehh.sport.model.Semester;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-17
 */
public interface SemesterService extends IService<Semester> {

    /**
     * 根据id获取学期信息
     *
     * @param id
     * @return
     */
    Semester getSemester(String id);

    /**
     * 获取最大权重
     *
     * @return 最大权重
     */
    Integer getNewWeight();

    /**
     * 新增学期
     *
     * @param semester 学期对象信息
     * @return 新增结果
     */
    Boolean insertSemester(Semester semester);

    /**
     * 更新学期信息
     *
     * @param semester 学期对象信息
     * @return 更新结果
     */
    Boolean updateSemester(Semester semester);

    /**
     * 删除学期信息
     *
     * @param id 学期id
     * @return 删除结果
     */
    Boolean deleteSemester(String id);
}
