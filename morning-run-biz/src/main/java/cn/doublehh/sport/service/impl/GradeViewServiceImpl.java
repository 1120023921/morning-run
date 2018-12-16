package cn.doublehh.sport.service.impl;

import cn.doublehh.sport.model.GradeView;
import cn.doublehh.sport.dao.GradeViewMapper;
import cn.doublehh.sport.service.GradeViewService;
import cn.doublehh.sport.vo.AttendanceVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    @Autowired
    private GradeViewMapper gradeViewMapper;

    @Override
    public Map<String, List<GradeView>> getGradeByJobNumberAndType(String jobNumber, String type) {
        QueryWrapper<GradeView> queryWrapper = new QueryWrapper<GradeView>()
                .eq("job_number", jobNumber)
                .eq("type", type);
        List<GradeView> gradeList = list(queryWrapper);
        Map<String, List<GradeView>> listMap = gradeList.stream().collect(Collectors.groupingBy(GradeView::getSemester));
        return sortMapByKey(listMap);
    }

    @Override
    public Map<String, List<AttendanceVo>> getAttendanceVo(String jobNumber, String type) {
        List<AttendanceVo> attendanceVoList = gradeViewMapper.getAttendanceVo(jobNumber, type);
        Map<String, List<AttendanceVo>> listMap = attendanceVoList.stream().collect(Collectors.groupingBy(AttendanceVo::getSemester));
        return sortMapByKey(listMap);
    }

    public static <T> Map<String, List<T>> sortMapByKey(Map<String, List<T>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, List<T>> sortMap = new TreeMap<String, List<T>>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    private static class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            return str2.compareTo(str1);
        }
    }
}
