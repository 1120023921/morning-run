package cn.doublehh.sport.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 胡昊
 * Description:获取成绩参数
 * Date: 2018/12/1
 * Time: 15:46
 * Create: DoubleH
 */
@Data
public class GradeParams implements Serializable {
    private String jobNumber;
    private String type;
}
