package cn.doublehh.sport.model;

import java.io.Serializable;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author 胡昊
 * @since 2018-10-25
 */
public class GradeView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * id
     */
    private String id;

    /**
     * 学号
     */
    private String jobNumber;

    /**
     * 项目
     */
    private Integer itemNumber;

    /**
     * 类别
     */
    private Integer type;

    /**
     * 成绩
     */
    private String grade;

    /**
     * 时间
     */
    private Long createTime;

    /**
     * 编号
     */
    private Integer gradeNumber;

    /**
     * 项目名称
     */
    private String itemName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getGradeNumber() {
        return gradeNumber;
    }

    public void setGradeNumber(Integer gradeNumber) {
        this.gradeNumber = gradeNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "GradeView{" +
        "name=" + name +
        ", id=" + id +
        ", jobNumber=" + jobNumber +
        ", itemNumber=" + itemNumber +
        ", type=" + type +
        ", grade=" + grade +
        ", createTime=" + createTime +
        ", gradeNumber=" + gradeNumber +
        ", itemName=" + itemName +
        "}";
    }
}
