package cn.doublehh.sport.model;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-16
 */
public class Grade implements Serializable {

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
     * 项目
     */
    private String itemNumber;

    /**
     * 类别
     */
    private String type;

    /**
     * 成绩
     */
    private String grade;

    /**
     * 时间
     */
    private String gradeCreateTime;

    /**
     * 考勤机编号
     */
    private String deviceNumber;

    /**
     * 学期id
     */
    private String semesterId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 有效值 0无效1有效
     */
    private Integer isValid;


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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeCreateTime() {
        return gradeCreateTime;
    }

    public void setGradeCreateTime(String gradeCreateTime) {
        this.gradeCreateTime = gradeCreateTime;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "Grade{" +
        "id=" + id +
        ", jobNumber=" + jobNumber +
        ", itemNumber=" + itemNumber +
        ", type=" + type +
        ", grade=" + grade +
        ", gradeCreateTime=" + gradeCreateTime +
        ", deviceNumber=" + deviceNumber +
        ", semesterId=" + semesterId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", version=" + version +
        ", isValid=" + isValid +
        "}";
    }
}
