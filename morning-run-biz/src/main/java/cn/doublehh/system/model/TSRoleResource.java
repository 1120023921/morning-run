package cn.doublehh.system.model;

import java.io.Serializable;

/**
 * <p>
 * 角色资源关系表
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
public class TSRoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 资源id
     */
    private String resourceId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "TSRoleResource{" +
        "id=" + id +
        ", roleId=" + roleId +
        ", resourceId=" + resourceId +
        "}";
    }
}
