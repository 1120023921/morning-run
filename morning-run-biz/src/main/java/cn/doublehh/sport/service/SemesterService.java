package cn.doublehh.sport.service;

import cn.doublehh.sport.model.Semester;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
