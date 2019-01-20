package cn.doublehh.system.controller;


import cn.doublehh.common.annotation.NeedPermission;
import cn.doublehh.common.controller.BaseController;
import cn.doublehh.common.pojo.ErrorCode;
import cn.doublehh.system.model.TSRole;
import cn.doublehh.system.service.TSRoleResourceService;
import cn.doublehh.system.service.TSUserRoleService;
import cn.doublehh.system.vo.UserRolePojo;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/tSRole")
public class TSRoleController extends BaseController<TSRole> {

    @Autowired
    private TSUserRoleService tsUserRoleService;
    @Autowired
    private TSRoleResourceService tsRoleResourceService;

    private static final String ROLE_ADD_USER_FAIL = "角色分配用户失败";
    private static final String ROLE_ADD_RESOURCE_FAIL = "角色分配资源失败";
    private static final String DELETE_USERS_FROM_ROLE = "从角色中移除用户失败";
    private static final String DELETE_RESOURCES_FROM_ROLE = "从角色中移除资源失败";

    /**
     * 给角色分配用户
     *
     * @param userRolePojo
     * @return
     */
    @NeedPermission(roleIds = {"admin", "teacher"})
    @ApiOperation(value = "给角色分配用户", notes = "给角色分配用户", httpMethod = "POST")
    @RequestMapping(value = "/addUsersToRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public R addUsersToRole(@RequestBody UserRolePojo userRolePojo) {
        ErrorCode errorCode = new ErrorCode();
        if (tsUserRoleService.addUsersToRole(userRolePojo.getUserIds(), userRolePojo.getRoleId()) > 0) {
            errorCode.setCode(ErrorCode.OK);
            errorCode.setMsg(ErrorCode.OK_MSG);
        } else {
            errorCode.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
            errorCode.setMsg(ROLE_ADD_USER_FAIL);
        }
        return R.restResult(null, errorCode);
    }

    /**
     * 从角色中移除用户
     *
     * @param userRolePojo
     * @return
     */
    @NeedPermission(roleIds = {"admin", "teacher"})
    @ApiOperation(value = "从角色中移除用户", notes = "从角色中移除用户", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteUsersFromRole", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public R deleteUsersFromRole(@RequestBody UserRolePojo userRolePojo) {
        ErrorCode errorCode = new ErrorCode();
        if (tsUserRoleService.deleteUsersFromRole(userRolePojo.getUserIds(), userRolePojo.getRoleId()) > 0) {
            errorCode.setCode(ErrorCode.NO_CONTENT);
            errorCode.setMsg(ErrorCode.NO_CONTENT_MSG);
        } else {
            errorCode.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
            errorCode.setMsg(DELETE_USERS_FROM_ROLE);
        }
        return R.restResult(null, errorCode);
    }

    /**
     * 给角色分配资源
     *
     * @param userRolePojo
     * @return
     */
    @NeedPermission(roleIds = {"admin", "teacher"})
    @ApiOperation(value = "给角色分配资源", notes = "给角色分配资源", httpMethod = "POST")
    @RequestMapping(value = "/addResourcesToRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public R addResourcesToRole(@RequestBody UserRolePojo userRolePojo) {
        ErrorCode errorCode = new ErrorCode();
        if (tsRoleResourceService.addResourcesToRole(userRolePojo.getResourceIds(), userRolePojo.getRoleId()) > 0) {
            errorCode.setCode(ErrorCode.OK);
            errorCode.setMsg(ErrorCode.OK_MSG);
        } else {
            errorCode.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
            errorCode.setMsg(ROLE_ADD_RESOURCE_FAIL);
        }
        return R.restResult(null, errorCode);
    }

    /**
     * 从角色中移除资源
     *
     * @param userRolePojo
     * @return
     */
    @NeedPermission(roleIds = {"admin", "teacher"})
    @ApiOperation(value = "从角色中移除资源", notes = "从角色中移除资源", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteResourcesFromRole", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public R deleteResourcesFromRole(@RequestBody UserRolePojo userRolePojo) {
        ErrorCode errorCode = new ErrorCode();
        if (tsRoleResourceService.deleteResourcesFromRole(userRolePojo.getResourceIds(), userRolePojo.getRoleId()) > 0) {
            errorCode.setCode(ErrorCode.OK);
            errorCode.setMsg(ErrorCode.OK_MSG);
        } else {
            errorCode.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
            errorCode.setMsg(DELETE_RESOURCES_FROM_ROLE);
        }
        return R.restResult(null, errorCode);
    }
}

