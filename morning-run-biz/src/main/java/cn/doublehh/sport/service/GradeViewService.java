package cn.doublehh.sport.service;

import cn.doublehh.sport.model.GradeView;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.ui.ModelMap;

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

    IPage<Map<String, Object>> getGradeByJobNumber(String jobNumber);
}
