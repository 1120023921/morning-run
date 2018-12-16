package cn.doublehh.sport;


import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.sport.model.Grade;
import cn.doublehh.sport.service.GradeService;
import com.baomidou.mybatisplus.extension.api.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-16
 */
@RestController
@RequestMapping("/wx/auth/{appid}/grade")
public class GradeController {

    private static final Logger logger = LoggerFactory.getLogger(GradeViewController.class);
    @Autowired
    private GradeService gradeService;

    @RequestMapping(value = "/importGrade", method = RequestMethod.POST)
    public R<Boolean> importGrade(MultipartFile[] multipartFiles, String semester) {
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
                    //跳过第一行
                    if (gradeInfo[0].equals("sno")) {
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
                logger.error("GradeViewController [importGrade]：导入学生成绩失败" + e.getMessage());
                return R.failed("导入学生成绩失败");
            }
        }
        try {
            result = gradeService.saveBatch(gradeList);
            return R.restResult(result, ErrorCodeInfo.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("GradeViewController [importGrade]：导入学生成绩失败" + e.getMessage());
            return R.failed("导入学生成绩失败");
        }
    }
}

