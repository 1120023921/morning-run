package cn.doublehh.sport.vo;

import lombok.Data;

/**
 * @author 胡昊
 * Description: 体教考勤Vo
 * Date: 2018/12/16
 * Time: 13:43
 * Create: DoubleH
 */
@Data
public class AttendanceVo {
    private String itemName;
    private Integer grade;
    private String type;
    private String itemNumber;
    private String semester;
}
