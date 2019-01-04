package cn.doublehh.sport.model;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 胡昊
 * @since 2019-01-03
 */
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 图片路径
     */
    private String pic;

    /**
     * 图片标题
     */
    private String title;

    /**
     * 跳转链接
     */
    private String url;

    /**
     * 权重
     */
    private Integer weight;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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
        return "Carousel{" +
        "id=" + id +
        ", pic=" + pic +
        ", title=" + title +
        ", url=" + url +
        ", weight=" + weight +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", version=" + version +
        ", isValid=" + isValid +
        "}";
    }
}
