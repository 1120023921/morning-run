package cn.doublehh.sport.service.impl;

import cn.doublehh.common.constant.WechatConstant;
import cn.doublehh.sport.dto.GradeEnumForExport;
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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.doublehh.sport.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
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
        log.info("GradeViewServiceImpl [getGradeByJobNumberAndType] 获取体测成绩 jobNumber=" + jobNumber + " type=" + type);
        List<GradeView> gradeViewList = gradeMapper.getGrade(jobNumber, type);
        transferGrade(gradeViewList);
        if (type.equals("01")) {
            return gradeViewList.stream().collect(Collectors.groupingBy(gradeView -> gradeView.getGradeCreateTime().substring(0, 4)));
        } else {
            return gradeViewList.stream().collect(Collectors.groupingBy(gradeView -> gradeView.getSemester()));
        }
    }

    @Override
    public Map<String, List<GradeView>> getAttendanceVo(String jobNumber, String type) {
        log.info("GradeViewServiceImpl [getGradeByJobNumberAndType] 获取体教考勤 jobNumber=" + jobNumber + " type=" + type);
        List<GradeView> gradeViewList = gradeMapper.getAttendanceGrade(jobNumber, type);
        transferGrade(gradeViewList);
        return gradeViewList.stream().collect(Collectors.groupingBy(GradeView::getSemester));
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
                //添加成绩到临时表
                List<Grade> oldGradeTmpList = gradeMapper.getGradeTmpOld(grade);
                if (!CollectionUtils.isEmpty(oldGradeTmpList)) {
                    oldGradeTmpList.forEach(oldGradeTmp -> {
                        oldGradeTmp.setIsValid(0);
                        gradeMapper.deleteOldGradeTmp(oldGradeTmp);
                    });
                }
                gradeMapper.insertTmpGrade(grade);
            }
            reader.close();
        } catch (IOException e) {
            log.error("成绩上传输入流异常", e);
            return false;
        }
        return true;
    }

    @Scheduled(cron = "00 00 06 ? * *")
    @Override
    public synchronized Boolean updateGradeFromTmp() {
        log.info("GradeViewServiceImpl [updateGradeFromTmp] 从临时表中更新成绩数据");
        List<Grade> tmpGradeList = gradeMapper.getTmpGradeList();
        tmpGradeList.forEach(tmpGrade -> {
            //判断该学生在该学年该项目是否已经有记录（考勤除外）
            if (!"01".equals(tmpGrade.getType())) {
                List<Grade> oldGradeList = list(new QueryWrapper<Grade>().eq("job_number", tmpGrade.getJobNumber()).eq("semester_id", tmpGrade.getSemesterId())
                        .eq("type", tmpGrade.getType()).eq("item_number", tmpGrade.getItemNumber()));
                if (!CollectionUtils.isEmpty(oldGradeList)) {
                    oldGradeList.forEach(oldGrade -> {
                        oldGrade.setUpdateTime(LocalDateTime.now());
                        oldGrade.setVersion(oldGrade.getVersion() + 1);
                        oldGrade.setIsValid(0);
                        updateById(oldGrade);
                    });
                }
            }
        });
        //开始添加数据
        Boolean insertResult = gradeMapper.insertGradeFromTmp();
        if (!insertResult) {
            log.info("GradeViewServiceImpl [updateGradeFromTmp] 从临时表中想成绩表添加数据失败");
        }
        //删除临时表中数据
        Boolean deleteResult = gradeMapper.deleteGradeTmp();
        if (!deleteResult) {
            log.info("GradeViewServiceImpl [updateGradeFromTmp] 删除临时表数据失败");
        }
        //发送成绩更新提醒
        sendUploadGradeMsg(tmpGradeList);
        return insertResult;
    }

    /**
     * 转换考勤时间
     *
     * @param gradeViewList 成绩列表
     */
    private void transferGrade(List<GradeView> gradeViewList) {
        gradeViewList.forEach(gradeView -> {
            //转换考勤机时间
            gradeView.setGradeCreateTime(Utils.transferDateTimeForDevice(gradeView.getGradeCreateTime()));
        });
    }

    @Async
    @Override
    public synchronized List<Grade> sendUploadGradeMsg(List<Grade> gradeList) {
        log.info("GradeViewServiceImpl [sendUploadGradeMsg] 发送新成绩上传提醒");
        List<Grade> result = new LinkedList<>();
        gradeList = gradeList.stream().collect(collectingAndThen(
                toCollection(() -> new TreeSet<>(comparing(Grade::getJobNumber))), ArrayList::new));
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
                templateMessage.addData(new WxMpTemplateData("keyword1", grade.getJobNumber(), "#173177"));
                templateMessage.addData(new WxMpTemplateData("keyword2", tsUser.getName(), "#173177"));
                templateMessage.addData(new WxMpTemplateData("keyword3", LocalDateTime.now().format(df), "#173177"));
                try {
                    Thread.sleep(1000L);
                    wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
                } catch (WxErrorException | InterruptedException e) {
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

    @Override
    public XSSFWorkbook exportGradeBySemester(String semesterId) {
        log.info("GradeViewServiceImpl [exportGradeBySemester] 导出学生成绩 semesterId=" + semesterId);
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("学生成绩");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("学号");
        row.createCell(1).setCellValue("姓名");
        for (int i = 2; i < GradeEnumForExport.values().length + 2; i++) {
            row.createCell(i).setCellValue(GradeEnumForExport.getItemName(i));
        }
        List<GradeView> exportList = gradeMapper.getExportList(semesterId);
        Map<String, List<GradeView>> exportListGroup = exportList.stream().collect(Collectors.groupingBy(GradeView::getJobNumber));
        int i = 0;
        for (Map.Entry<String, List<GradeView>> entry : exportListGroup.entrySet()) {
            row = sheet.createRow(1 + i++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue().get(0).getName());
            for (GradeView gradeView : entry.getValue()) {
                row.createCell(GradeEnumForExport.getIndex(gradeView.getItemName() == null ? "" : gradeView.getItemName())).setCellValue(transferName(gradeView.getGrade(), gradeView.getItemName() == null ? "" : gradeView.getItemName()));
            }
        }
        return wb;
    }

    /**
     * 转换成绩
     *
     * @return 转换后的成绩
     */
    private String transferName(String grade, String itemName) {
        try {
            if (itemName.indexOf("跑") > -1) {
                return grade.substring(3, 4) + '\'' + grade.substring(4, 6) + '\'' + '\'' + grade.substring(6, 8);
            } else {
                if (grade.indexOf("-") > -1) {
                    grade = grade.substring(grade.indexOf("-"));
                }
                if (grade.indexOf(".") > -1) {
                    return String.valueOf(Double.valueOf(grade));
                }
                return String.valueOf(Integer.valueOf(grade));
            }
        } catch (Exception e) {
            return grade;
        }
    }

    public static <T> Map<String, List<T>> sortMapByKey(Map<String, List<T>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, List<T>> sortMap = new TreeMap<>(new GradeServiceImpl.MapKeyComparator());
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
