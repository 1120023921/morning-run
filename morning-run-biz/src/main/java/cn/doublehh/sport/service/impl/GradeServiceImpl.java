package cn.doublehh.sport.service.impl;

import cn.doublehh.common.constant.WechatConstant;
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
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

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
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatConstant wechatConstant;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    @Override
    @Transactional
    public Boolean uploadGrade(BufferedReader reader, List<Grade> gradeList, String semester) {
        log.info("GradeViewServiceImpl [uploadGrade] 读取文件流上传至数据库");
        String line;
        Grade grade;
        try {
            while ((line = reader.readLine()) != null) {
                String[] gradeInfo = line.split("\\|");
                //跳过第一行和不标准数据
                if (gradeInfo.length < 6 || gradeInfo[0].equals("sno")) {
                    continue;
                }
                grade = new Grade();
                grade.setIsValid(1);
                grade.setVersion(1);
                grade.setId(UUID.randomUUID().toString());
                grade.setJobNumber(gradeInfo[0]);
                grade.setItemNumber(gradeInfo[1]);
                grade.setType(gradeInfo[2]);
                grade.setGrade(gradeInfo[3]);
                grade.setGradeCreateTime(gradeInfo[4]);
                grade.setDeviceNumber(gradeInfo[5]);
                grade.setCreateTime(LocalDateTime.now());
                grade.setUpdateTime(LocalDateTime.now());
                grade.setSemesterId(semester);
                gradeList.add(grade);
            }
            reader.close();
        } catch (IOException e) {
            log.error("成绩上传输入流异常", e);
            return false;
        }
        return true;
    }

    /**
     * 补全成绩信息
     *
     * @param gradeViewList 成绩列表
     * @return
     */
    private void transferGrade(List<GradeView> gradeViewList) {
        gradeViewList.forEach(gradeView -> {
            //获取学期信息
            Semester semester = semesterService.getSemester(gradeView.getSemesterId());
            gradeView.setSemester(semester.getSemester());
            gradeView.setWeight(semester.getWeight());
            //获取项目信息
            Item item = itemService.getItem(gradeView.getType(), gradeView.getItemNumber());
            gradeView.setItemName(item.getItemName());
            //获取学生信息
            TSUser tsUser = tsUserService.getUserByUid(gradeView.getJobNumber());
            gradeView.setName(tsUser.getName());
            //转换考勤机时间
            gradeView.setGradeCreateTime(Utils.transferDateTimeForDevice(gradeView.getGradeCreateTime()));
        });
    }

    @Override
    public List<Grade> sendUploadGradeMsg(List<Grade> gradeList) {
        log.info("GradeViewServiceImpl [sendUploadGradeMsg] 发送新成绩上传提醒");
        List<Grade> result = new LinkedList<>();
        gradeList = gradeList.stream().collect(collectingAndThen(
                toCollection(() -> new TreeSet<Grade>(comparing(Grade::getJobNumber))), ArrayList::new));
        gradeList.forEach(grade -> {
            TSUser tsUser = tsUserService.getUserByUid(grade.getJobNumber());
//            Assert.notNull(tsUser, "uid对应的用户不存在");
            if (null != tsUser) {
                WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                        .toUser(tsUser.getWechatOpenid())
                        .templateId(wechatConstant.getUploadGradeMsgId())
                        .url(wechatConstant.getAuthUrl())
                        .build();
                templateMessage.addData(new WxMpTemplateData("first", "您的体育成绩有更新", "#FF0000"));
                templateMessage.addData(new WxMpTemplateData("jobNumber", grade.getJobNumber(), "#173177"));
                templateMessage.addData(new WxMpTemplateData("name", tsUser.getName(), "#173177"));
                templateMessage.addData(new WxMpTemplateData("updateTime", LocalDateTime.now().format(df), "#173177"));
                try {
                    wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
                } catch (WxErrorException e) {
                    result.add(grade);
                }
            } else {
                result.add(grade);
            }
        });
        if (!CollectionUtils.isEmpty(result)) {
            log.error("成绩更新推送消息发送失败", result);
        }
        return result;
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
