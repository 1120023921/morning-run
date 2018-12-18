package cn.doublehh.sport.service.impl;

import cn.doublehh.sport.model.Grade;
import cn.doublehh.sport.dao.GradeMapper;
import cn.doublehh.sport.vo.AttendanceGradeDetailParam;
import cn.doublehh.sport.vo.GradeView;
import cn.doublehh.sport.model.Item;
import cn.doublehh.sport.model.Semester;
import cn.doublehh.sport.service.GradeService;
import cn.doublehh.sport.service.ItemService;
import cn.doublehh.sport.service.SemesterService;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.doublehh.sport.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-16
 */
@Service
@Slf4j
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private SemesterService semesterService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private TSUserService tsUserService;

    @Override
    public Map<String, List<GradeView>> getGradeByJobNumberAndType(String jobNumber, String type) {
        log.info("GradeViewServiceImpl [getGradeByJobNumberAndType] 获取成绩 jobNumber=" + jobNumber + " type=" + type);
        List<GradeView> gradeViewList = gradeMapper.getGrade(jobNumber, type);
        transferGrade(gradeViewList);
        Map<String, List<GradeView>> listMap = gradeViewList.stream().collect(Collectors.groupingBy(GradeView::getSemester));
        return sortMapByKey(listMap);
    }

    @Override
    public Map<String, List<GradeView>> getAttendanceVo(String jobNumber, String type) {
        log.info("GradeViewServiceImpl [getGradeByJobNumberAndType] 获取体教考勤 jobNumber=" + jobNumber + " type=" + type);
        List<GradeView> gradeViewList = gradeMapper.getAttendanceGrade(jobNumber, type);
        transferGrade(gradeViewList);
        Map<String, List<GradeView>> listMap = gradeViewList.stream().collect(Collectors.groupingBy(GradeView::getSemester));
        return sortMapByKey(listMap);
    }

    @Override
    public List<GradeView> getAttendanceGradeDetail(AttendanceGradeDetailParam attendanceGradeDetailParam) {
        log.info("GradeViewServiceImpl [getAttendanceGradeDetail] 获取体教考勤详细信息 attendanceGradeDetailParam=" + attendanceGradeDetailParam);
        List<GradeView> attendanceGradeDetail = gradeMapper.getAttendanceGradeDetail(attendanceGradeDetailParam);
        transferGrade(attendanceGradeDetail);
        return attendanceGradeDetail;
    }

    /**
     * 补全成绩信息
     *
     * @param gradeViewList
     * @return
     */
    public List<GradeView> transferGrade(List<GradeView> gradeViewList) {
        gradeViewList.forEach(gradeView -> {
            //获取学期信息
            Semester semester = semesterService.getSemester(gradeView.getSemesterId());
            gradeView.setSemester(semester.getSemester());
            gradeView.setWeight(semester.getWeight());
            //获取项目信息
            Item item = itemService.getItem(gradeView.getType(), gradeView.getItemNumber());
            gradeView.setItemName(item.getItemName());
            //获取学生学习
            TSUser tsUser = tsUserService.getUserWithRolesByUid(gradeView.getJobNumber());
            gradeView.setName(tsUser.getName());
            //转换考勤机时间
            gradeView.setGradeCreateTime(Utils.transferDateTimeForDevice(gradeView.getGradeCreateTime()));
        });
        return gradeViewList;
    }

    public static <T> Map<String, List<T>> sortMapByKey(Map<String, List<T>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, List<T>> sortMap = new TreeMap<String, List<T>>(new GradeServiceImpl.MapKeyComparator());
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
