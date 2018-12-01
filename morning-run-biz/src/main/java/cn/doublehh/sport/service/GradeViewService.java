package cn.doublehh.sport.service;

import cn.doublehh.sport.model.GradeView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2018-11-19
 */
public interface GradeViewService extends IService<GradeView> {

    /**
     * 分类获取学生成绩
     *
     * @param jobNumber 学号
     * @param type      类别名称
     * @return
     */
    Map<String, List<GradeView>> getGradeByJobNumberAndType(String jobNumber, String type);
}
