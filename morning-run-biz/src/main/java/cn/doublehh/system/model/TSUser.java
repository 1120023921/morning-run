package cn.doublehh.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
public class TSUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String uid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机
     */
    private String tel;

    /**
     * 邮件
     */
    private String mail;

    /**
     * 状态
     */
    private String enabled;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 修改者
     */
    private String updateBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 密码
     */
    private String password;

    /**
     * 公司
     */
    private String company;

    /**
     * 用户头像
     */
    private String headerUrl;

    /**
     * 用户类型 1厂商 2代理商 3普通用户
     */
    private Integer userType;

    /**
     * 用户地址
     */
    private String address;

    /**
     * QQ
     */
    private String qqNumber;

    /**
     * 微信号
     */
    private String wechatNumber;

    /**
     * 微信openid
     */
    private String wechatOpenid;

    /**
     * QQ openid
     */
    private String qqOpenid;

    /**
     * 等级
     */
    private Integer grade;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<TSRole> roles = new ArrayList<TSRole>();

    public List<TSRole> getRoles() {
        return roles;
    }

    public void setRoles(List<TSRole> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public String getWechatOpenid() {
        return wechatOpenid;
    }

    public void setWechatOpenid(String wechatOpenid) {
        this.wechatOpenid = wechatOpenid;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "TSUser{" +
        "id=" + id +
        ", uid=" + uid +
        ", name=" + name +
        ", sex=" + sex +
        ", tel=" + tel +
        ", mail=" + mail +
        ", enabled=" + enabled +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", createBy=" + createBy +
        ", updateBy=" + updateBy +
        ", remark=" + remark +
        ", password=" + password +
        ", company=" + company +
        ", headerUrl=" + headerUrl +
        ", userType=" + userType +
        ", address=" + address +
        ", qqNumber=" + qqNumber +
        ", wechatNumber=" + wechatNumber +
        ", wechatOpenid=" + wechatOpenid +
        ", qqOpenid=" + qqOpenid +
        ", grade=" + grade +
        "}";
    }
}
