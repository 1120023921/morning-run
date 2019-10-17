package cn.doublehh.sport.model;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 胡昊
 * @since 2019-10-17
 */
public class Eye implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 左眼裸力视力
     */
    private String leftNaked;

    /**
     * 右眼裸眼视力
     */
    private String rightNaked;

    /**
     * 左眼串镜
     */
    private String leftGlass;

    /**
     * 右眼串镜
     */
    private String rightGlass;

    /**
     * 左眼屈光不正
     */
    private String leftAmetropia;

    /**
     * 右眼屈光不正
     */
    private String rightAmetropia;

    /**
     * 状态 0未审核 1通过 2拒绝
     */
    private Integer status;

    /**
     * 用户id
     */
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeftNaked() {
        return leftNaked;
    }

    public void setLeftNaked(String leftNaked) {
        this.leftNaked = leftNaked;
    }

    public String getRightNaked() {
        return rightNaked;
    }

    public void setRightNaked(String rightNaked) {
        this.rightNaked = rightNaked;
    }

    public String getLeftGlass() {
        return leftGlass;
    }

    public void setLeftGlass(String leftGlass) {
        this.leftGlass = leftGlass;
    }

    public String getRightGlass() {
        return rightGlass;
    }

    public void setRightGlass(String rightGlass) {
        this.rightGlass = rightGlass;
    }

    public String getLeftAmetropia() {
        return leftAmetropia;
    }

    public void setLeftAmetropia(String leftAmetropia) {
        this.leftAmetropia = leftAmetropia;
    }

    public String getRightAmetropia() {
        return rightAmetropia;
    }

    public void setRightAmetropia(String rightAmetropia) {
        this.rightAmetropia = rightAmetropia;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Eye{" +
                "id=" + id +
                ", leftNaked=" + leftNaked +
                ", rightNaked=" + rightNaked +
                ", leftGlass=" + leftGlass +
                ", rightGlass=" + rightGlass +
                ", leftAmetropia=" + leftAmetropia +
                ", rightAmetropia=" + rightAmetropia +
                ", status=" + status +
                ", userId=" + userId +
                "}";
    }
}
