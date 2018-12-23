package cn.doublehh.sport;


import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.sport.model.Grade;
import cn.doublehh.sport.model.GradeParams;
import cn.doublehh.sport.vo.AttendanceGradeDetailParam;
import cn.doublehh.sport.vo.GradeView;
import cn.doublehh.sport.service.GradeService;
import cn.doublehh.system.service.TSUserService;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
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
    @Autowired
    private TSUserService tsUserService;

    /**
     * 获取体质测试成绩
     *
     * @param gradeParams 查询条件对象
     * @return 体质测试成绩列表
     */
    @RequestMapping(value = "/getGradeByJobNumberAndType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Map<String, List<GradeView>>> getGradeByJobNumberAndType(@RequestBody GradeParams gradeParams) {
        Map<String, List<GradeView>> gradeList = gradeService.getGradeByJobNumberAndType(gradeParams.getJobNumber(), gradeParams.getType());
        return R.restResult(gradeList, ErrorCodeInfo.SUCCESS);

    }

    /**
     * 获取体教考勤成绩
     *
     * @param gradeParams 查询条件对象
     * @return 体教考勤成绩列表
     */
    @RequestMapping(value = "/getAttendanceVo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Map<String, List<GradeView>>> getAttendanceVo(@RequestBody GradeParams gradeParams) {
        Map<String, List<GradeView>> attendanceList = gradeService.getAttendanceVo(gradeParams.getJobNumber(), gradeParams.getType());
        return R.restResult(attendanceList, ErrorCodeInfo.SUCCESS);
    }

    /**
     * 获取体教考勤详细信息
     *
     * @param attendanceGradeDetailParam 查询考勤成绩详情参数封装类
     * @return 体教考勤详细信息
     */
    @RequestMapping(value = "/getAttendanceGradeDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<List<GradeView>> getAttendanceGradeDetail(@RequestBody AttendanceGradeDetailParam attendanceGradeDetailParam) {
        List<GradeView> attendanceGradeDetail = gradeService.getAttendanceGradeDetail(attendanceGradeDetailParam);
        attendanceGradeDetail = attendanceGradeDetail.stream().sorted(Comparator.comparing(GradeView::getGradeCreateTime).reversed()).collect(Collectors.toList());
        return R.restResult(attendanceGradeDetail, ErrorCodeInfo.SUCCESS);
    }

//    @RequestMapping("/showGrade")
//    public String login(@PathVariable String appid, @RequestParam String code, String state, ModelMap map) throws WxErrorException {
//        WxMpService wxMpService = WxMpConfiguration.getMpServices().get(appid);
//        try {
//            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
//            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
//            TSUser user = tsUserService.getUserByWechatOpenId(wxMpUser.getOpenId());
//            //微信未绑定跳转绑定页面
//            if (user == null) {
//                map.put("openid", wxMpUser.getOpenId());
//                return "bindInfo";
//            } else {
//                map.put("grade", gradeViewService.pageMaps(new Page<GradeView>(1, -1), new QueryWrapper<GradeView>().eq("job_number", user.getUid())).getRecords());
//                return "showGrade";
//            }
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
//        return "/error";
//    }

    @RequestMapping("/test")
    public R<Boolean> test() throws IOException {
        File file = new File("C:\\Users\\11200\\Desktop\\体育考勤及后台数据三个\\1819");
        File[] files = file.listFiles();
        BufferedReader reader;
        Grade grade;
        boolean result;
        String line;
        List<Grade> gradeList = new ArrayList<>();
        FileWriter writer = new FileWriter("D:/2.sql");
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

    @RequestMapping(value = "/importGrade", method = RequestMethod.POST)
    @Transactional()
    public R<Boolean> importGrade(MultipartFile[] multipartFiles, String semester) {
        Assert.hasText(semester, "学期不能为空");
        Assert.notEmpty(multipartFiles, "文件不能为空");
        BufferedReader reader;
        String line;
        List<Grade> gradeList = new ArrayList<>();
        Grade grade;
        boolean result = false;
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
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
            } catch (Exception e) {
                e.printStackTrace();
                log.error("GradeViewController [importGrade]：导入学生成绩失败" + e.getMessage());
                return R.failed("导入学生成绩失败");
            }
        }
        try {
            result = gradeService.saveBatch(gradeList);
            return R.restResult(result, ErrorCodeInfo.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("GradeViewController [importGrade]：导入学生成绩失败" + e.getMessage());
            return R.failed("导入学生成绩失败");
        }
    }
}

