package cn.doublehh.system.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 胡昊
 * Description:用户角色资源封装类
 * Date: 2018/7/15
 * Time: 17:24
 * Create: DoubleH
 */
public class UserRolePojo {

    @ApiModelProperty(value = "用户id", name = "userId", example = "4b585647-7c7c-4f1f-a9b7-46b242a504b5")
    private String userId;
    @ApiModelProperty(value = "用户id数组", name = "userIds")
    private String[] userIds;
    @ApiModelProperty(value = "角色id", name = "roleId", example = "4b585647-7c7c-4f1f-a9b7-46b242a504b5")
    private String roleId;
    @ApiModelProperty(value = "角色id数组", name = "roleIds")
    private String[] roleIds;
    @ApiModelProperty(value = "资源id数组", name = "resourceIds")
    private String[] resourceIds;

    public String[] getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String[] resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }
}
