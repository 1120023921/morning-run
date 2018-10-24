package cn.doublehh.system.model;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
public class TSResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 资源名
     */
    private String reid;

    /**
     * 资源中文名
     */
    private String name;

    /**
     * 类型
     */
    private String type;

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
     * 父资源ID
     */
    private String pid;

    /**
     * 资源值
     */
    private String value;

    /**
     * 是否叶子节点
     */
    private String leaf;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    @Override
    public String toString() {
        return "TSResource{" +
        "id=" + id +
        ", reid=" + reid +
        ", name=" + name +
        ", type=" + type +
        ", enabled=" + enabled +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", createBy=" + createBy +
        ", updateBy=" + updateBy +
        ", remark=" + remark +
        ", pid=" + pid +
        ", value=" + value +
        ", leaf=" + leaf +
        "}";
    }
}
