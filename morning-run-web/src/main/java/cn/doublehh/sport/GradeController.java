package cn.doublehh.sport;


import cn.doublehh.common.annotation.NeedPermission;
import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.common.utils.DesUtil;
import cn.doublehh.sport.model.Grade;
import cn.doublehh.sport.model.GradeParams;
import cn.doublehh.sport.vo.AttendanceGradeDetailParam;
import cn.doublehh.sport.vo.GradeView;
import cn.doublehh.sport.service.GradeService;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-16
 */
@RestController
@RequestMapping("/grade")
@Slf4j
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 获取体质测试成绩
     *
     * @param gradeParams 查询条件对象
     * @return 体质测试成绩列表
     */
    @Cacheable(value = "GradeController:getGradeByJobNumberAndType")
    @RequestMapping(value = "/getGradeByJobNumberAndType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Map<String, List<GradeView>>> getGradeByJobNumberAndType(@RequestBody GradeParams gradeParams) {
        Map<String, List<GradeView>> gradeList = gradeService.getGradeByJobNumberAndType(DesUtil.decrypt(gradeParams.getJobNumber()), gradeParams.getType());
        return R.restResult(gradeList, ErrorCodeInfo.SUCCESS);
    }

    /**
     * 教师查询学生成绩
     *
     * @param gradeParams 查询条件对象
     * @return 体质测试成绩列表
     */
    @NeedPermission(roleIds = {"admin", "teacher"})
    @RequestMapping(value = "/getGradeByJobNumberAndTypeByTeacher", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Map<String, List<GradeView>>> getGradeByJobNumberAndTypeByTeacher(@RequestBody GradeParams gradeParams) {
        Map<String, List<GradeView>> gradeList = gradeService.getGradeByJobNumberAndType(gradeParams.getJobNumber(), gradeParams.getType());
        return R.restResult(gradeList, ErrorCodeInfo.SUCCESS);
    }

    /**
     * 获取体教考勤成绩
     *
     * @param gradeParams 查询条件对象
     * @return 体教考勤成绩列表
     */
    @Cacheable(value = "GradeController:getAttendanceVo")
    @RequestMapping(value = "/getAttendanceVo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Map<String, List<GradeView>>> getAttendanceVo(@RequestBody GradeParams gradeParams) {
        Map<String, List<GradeView>> attendanceList = gradeService.getAttendanceVo(DesUtil.decrypt(gradeParams.getJobNumber()), gradeParams.getType());
        return R.restResult(attendanceList, ErrorCodeInfo.SUCCESS);
    }

    /**
     * 教师获取体教考勤成绩
     *
     * @param gradeParams 查询条件对象
     * @return 体教考勤成绩列表
     */
    @NeedPermission(roleIds = {"admin", "teacher"})
    @RequestMapping(value = "/getAttendanceVoByTeacher", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Map<String, List<GradeView>>> getAttendanceVoByTeacher(@RequestBody GradeParams gradeParams) {
        Map<String, List<GradeView>> attendanceList = gradeService.getAttendanceVo(gradeParams.getJobNumber(), gradeParams.getType());
        return R.restResult(attendanceList, ErrorCodeInfo.SUCCESS);
    }

    /**
     * 获取体教考勤详细信息
     *
     * @param attendanceGradeDetailParam 查询考勤成绩详情参数封装类
     * @return 体教考勤详细信息
     */
    @Cacheable(value = "GradeController:getAttendanceGradeDetail", key = "#attendanceGradeDetailParam.jobNumber+#attendanceGradeDetailParam.semesterId+#attendanceGradeDetailParam.type+#attendanceGradeDetailParam.itemNumber")
    @RequestMapping(value = "/getAttendanceGradeDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<List<GradeView>> getAttendanceGradeDetail(@RequestBody AttendanceGradeDetailParam attendanceGradeDetailParam) {
        attendanceGradeDetailParam.setJobNumber(DesUtil.decrypt(attendanceGradeDetailParam.getJobNumber()));
        List<GradeView> attendanceGradeDetail = gradeService.getAttendanceGradeDetail(attendanceGradeDetailParam);
        attendanceGradeDetail = attendanceGradeDetail.stream().sorted(Comparator.comparing(GradeView::getGradeCreateTime).reversed()).collect(Collectors.toList());
        return R.restResult(attendanceGradeDetail, ErrorCodeInfo.SUCCESS);
    }

    /**
     * 教师获取体教考勤详细信息
     *
     * @param attendanceGradeDetailParam 查询考勤成绩详情参数封装类
     * @return 体教考勤详细信息
     */
    @NeedPermission(roleIds = {"admin", "teacher"})
    @RequestMapping(value = "/getAttendanceGradeDetailByTeacher", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<List<GradeView>> getAttendanceGradeDetailByTeacher(@RequestBody AttendanceGradeDetailParam attendanceGradeDetailParam) {
        attendanceGradeDetailParam.setJobNumber(attendanceGradeDetailParam.getJobNumber());
        List<GradeView> attendanceGradeDetail = gradeService.getAttendanceGradeDetail(attendanceGradeDetailParam);
        attendanceGradeDetail = attendanceGradeDetail.stream().sorted(Comparator.comparing(GradeView::getGradeCreateTime).reversed()).collect(Collectors.toList());
        return R.restResult(attendanceGradeDetail, ErrorCodeInfo.SUCCESS);
    }

    /**
     * 将txt转换成sql
     *
     * @return 转换结果
     */
    public R<Boolean> transferTxt2Sql() throws IOException {
        File file = new File("C:\\Users\\11200\\Desktop\\体育考勤及后台数据三个\\1819");
        File[] files = file.listFiles();
        BufferedReader reader;
        String line;
        FileWriter writer = new FileWriter("D:/2.sql");
        assert files != null;
        for (File file1 : files) {
            FileInputStream fileInputStream = new FileInputStream(file1);
            try {
                reader = new BufferedReader(new InputStreamReader(fileInputStream));
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    String[] gradeInfo = line.split("\\|");
                    //跳过第一行
                    if (gradeInfo.length < 4 || gradeInfo[0].equals("sno")) {
                        continue;
                    }
                    writer.write("INSERT INTO `grade` VALUES ('" + UUID.randomUUID().toString() + "', '" + gradeInfo[0] + "', '" + gradeInfo[1] + "', '" + gradeInfo[2] + "', '" + gradeInfo[3] + "', '" + gradeInfo[4] + "', '" + gradeInfo[5] + "', '2', '2018-12-16 22:04:33', '2018-12-16 22:04:33', 1, 1);\r\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("GradeViewController [importGrade]：导入学生成绩失败" + e.getMessage());
                return R.failed("导入学生成绩失败");
            }
        }
        writer.flush();
        writer.close();
        return R.restResult(null, ErrorCodeInfo.SUCCESS);
    }

    /**
     * 成绩导入
     *
     * @param multipartFiles 成绩文件
     * @param semester       学期
     * @return 导入结果
     */
    @CacheEvict(value = {"GradeController:getGradeByJobNumberAndType", "GradeController:getAttendanceVo", "GradeController:getAttendanceGradeDetail"}, allEntries = true)
    @NeedPermission(roleIds = {"admin", "teacher"})
    @RequestMapping(value = "/importGrade", method = RequestMethod.POST)
    public R importGrade(MultipartFile[] multipartFiles, String semester) {
        Assert.hasText(semester, "学期不能为空");
        Assert.notEmpty(multipartFiles, "文件不能为空");
        BufferedReader reader;
        List<Grade> gradeList = new ArrayList<>();
        boolean result;
        for (MultipartFile multipartFile : multipartFiles) {
            String fileType = multipartFile.getOriginalFilename().substring(Objects.requireNonNull(multipartFile.getOriginalFilename()).indexOf('.') + 1);
            if (!"txt".equals(fileType)) {
                throw new RuntimeException("请选择txt格式的文件上传");
            }
            try {
                reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
                gradeService.uploadGrade(reader, gradeList, semester);
            } catch (Exception e) {
                log.error("GradeViewController [importGrade]：获取文件输入流失败" + e.getMessage());
                return R.restResult("获取文件输入流失败", ErrorCodeInfo.FAILED);
            }
        }
        try {
//            result = gradeService.saveBatch(gradeList);
            //成绩全部导入成功开始发送消息推送
            new Thread(() -> gradeService.sendUploadGradeMsg(gradeList)).run();
            return R.restResult(true, ErrorCodeInfo.SUCCESS);
        } catch (Exception e) {
            log.error("GradeViewController [importGrade]：导入学生成绩失败" + e.getMessage());
            return R.restResult("导入学生成绩失败", ErrorCodeInfo.FAILED);
        }
    }

    /**
     * 导出学生成绩
     *
     * @param semesterId 学期id
     * @return 导出的Excel
     */
    @GetMapping(value = "/exportGrade")
    public void exportGrade(String semesterId, HttpServletResponse response) {
        try {
            XSSFWorkbook xssfWorkbook = gradeService.exportGradeBySemester(semesterId);
            OutputStream output = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("学生成绩信息.xlsx", "UTF-8"));
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            xssfWorkbook.write(output);
            output.close();
        } catch (IOException e) {
            log.error("导出成绩Excel失败", e);
        }
    }
}

