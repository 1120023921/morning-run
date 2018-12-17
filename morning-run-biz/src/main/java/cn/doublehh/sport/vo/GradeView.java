package cn.doublehh.sport.vo;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-01
 */
public class GradeView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 学号
     */
    private String jobNumber;

    /**
     * 考勤机编号
     */
    private String deviceNumber;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 成绩
     */
    private String grade;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 项目
     */
    private String itemNumber;

    /**
     * 类别
     */
    private String type;

    /**
     * 学期
     */
    private String semester;

    /**
     * 权重
     */
    private Integer weight;


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

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "GradeView{" +
        "id=" + id +
        ", jobNumber=" + jobNumber +
        ", deviceNumber=" + deviceNumber +
        ", createTime=" + createTime +
        ", grade=" + grade +
        ", itemName=" + itemName +
        ", name=" + name +
        ", itemNumber=" + itemNumber +
        ", type=" + type +
        ", semester=" + semester +
        ", weight=" + weight +
        "}";
    }
}
