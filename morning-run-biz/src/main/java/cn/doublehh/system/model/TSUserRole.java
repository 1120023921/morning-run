package cn.doublehh.system.model;

import java.io.Serializable;

/**
 * <p>
 * 人员角色关系表
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
public class TSUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户名
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 用户类型
     */
    private String userType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "TSUserRole{" +
        "id=" + id +
        ", userId=" + userId +
        ", roleId=" + roleId +
        ", userType=" + userType +
        "}";
    }
}
