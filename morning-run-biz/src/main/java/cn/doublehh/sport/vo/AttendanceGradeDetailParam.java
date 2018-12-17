package cn.doublehh.sport.vo;

import lombok.Data;

/**
 * @author 胡昊
 * Description: 查询考勤成绩详情参数封装类
 * Date: 2018/12/17
 * Time: 21:39
 * Create: DoubleH
 */
@Data
public class AttendanceGradeDetailParam {
    private String jobNumber;
    private String semesterId;
    private String type;
    private String itemNumber;
}
