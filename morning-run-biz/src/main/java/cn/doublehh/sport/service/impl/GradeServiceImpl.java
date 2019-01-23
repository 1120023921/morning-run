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
        return gradeViewList.stream().collect(Collectors.groupingBy(gradeView -> gradeView.getGradeCreateTime().substring(0, 4)));
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
        List<TSUser> userList = tsUserService.getUserByRoleId("2");
        List<Item> itemList = itemService.list(new QueryWrapper<>());
        for (int i = 0; i < 1000; i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(userList.get(i).getUid());
            row.createCell(1).setCellValue(userList.get(i).getName());
            List<GradeView> gradeViewList = getGradeByJobNumberAndTypeAndSemester(userList.get(i).getUid(), "02", semesterId);
            Row finalRow = row;
            System.out.println(i);
            gradeViewList.forEach(gradeView -> {
                Item itemRes = itemList.stream().filter(item -> item.getType().equals(gradeView.getType()) && item.getItemNumber().equals(gradeView.getItemNumber())).collect(Collectors.toList()).get(0);
                finalRow.createCell(GradeEnumForExport.getIndex(itemRes.getItemName())).setCellValue(transferName(gradeView.getGrade(), itemRes.getItemName()));
            });
            List<GradeView> attendanceVoList = getAttendanceVoBySemester(userList.get(i).getUid(), "01", semesterId);
            attendanceVoList.forEach(gradeView -> {
                Item itemRes = itemList.stream().filter(item -> item.getType().equals(gradeView.getType()) && item.getItemNumber().equals(gradeView.getItemNumber())).collect(Collectors.toList()).get(0);
                finalRow.createCell(GradeEnumForExport.getIndex(itemRes.getItemName())).setCellValue(transferName(gradeView.getGrade(), itemRes.getItemName()));
            });
        }
        return wb;
    }

    /**
     * 根据学期统计学生考勤次数
     *
     * @param jobNumber  学号
     * @param type       项目类型
     * @param semesterId 学期id
     * @return
     */
    private List<GradeView> getAttendanceVoBySemester(String jobNumber, String type, String semesterId) {
        log.info("GradeViewServiceImpl [getAttendanceVoBySemester] 获取体教考勤 jobNumber=" + jobNumber + " type=" + type + " semesterId=" + semesterId);
        List<GradeView> gradeViewList = gradeMapper.getAttendanceGradeBySemester(jobNumber, type, semesterId);
//        transferGrade(gradeViewList);
        return gradeViewList;
    }

    /**
     * 根据学期获取学生成绩
     *
     * @param jobNumber  学号
     * @param type       项目类型
     * @param semesterId 学期id
     * @return 成绩列表
     */
    private List<GradeView> getGradeByJobNumberAndTypeAndSemester(String jobNumber, String type, String semesterId) {
        log.info("GradeViewServiceImpl [getGradeByJobNumberAndTypeAndSemester] 获取成绩 jobNumber=" + jobNumber + " type=" + type + " semesterId=" + semesterId);
        List<GradeView> gradeViewList = gradeMapper.getGradeBySemester(jobNumber, type, semesterId);
//        transferGrade(gradeViewList);
        return gradeViewList;
    }

    /**
     * 转换成绩
     *
     * @return 转换后的成绩
     */
    private String transferName(String grade, String itemName) {
        if (itemName.indexOf("跑") > -1) {
            return grade.substring(3, 4) + '\'' + grade.substring(4, 6) + '\'' + '\'' + grade.substring(6, 8);
        } else {
            if (grade.indexOf(".") > -1) {
                return String.valueOf(Double.valueOf(grade));
            }
            return String.valueOf(Integer.valueOf(grade));
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
