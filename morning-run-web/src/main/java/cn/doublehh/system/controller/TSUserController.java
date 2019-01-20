package cn.doublehh.system.controller;


import cn.doublehh.common.annotation.NeedPermission;
import cn.doublehh.common.controller.BaseController;
import cn.doublehh.common.pojo.ErrorCode;
import cn.doublehh.common.utils.DesUtil;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserRoleService;
import cn.doublehh.system.service.TSUserService;
import cn.doublehh.system.vo.UserRolePojo;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2018-09-06
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/tSUser")
public class TSUserController extends BaseController<TSUser> {

    @Autowired
    private TSUserService tsUserService;
    @Autowired
    private TSUserRoleService tsUserRoleService;

    private static final String USER_ADD_ROLE_FAIL = "用户分配角色失败";
    private static final String DELETE_ROLES_FROM_USER = "从用户中移除角色失败";

    /**
     * 给用户分配角色
     *
     * @param userRolePojo
     * @return
     */
    @NeedPermission(roleIds = {"admin", "teacher"})
    @ApiOperation(value = "给用户分配角色", notes = "给用户分配角色", httpMethod = "POST")
    @RequestMapping(value = "/addRolesToUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public R addRolesToUser(@RequestBody UserRolePojo userRolePojo) {
        ErrorCode errorCode = new ErrorCode();
        if (tsUserRoleService.addRolesToUser(userRolePojo.getRoleIds(), userRolePojo.getUserId()) > 0) {
            errorCode.setCode(ErrorCode.OK);
            errorCode.setMsg(ErrorCode.OK_MSG);
        } else {
            errorCode.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
            errorCode.setMsg(USER_ADD_ROLE_FAIL);
        }
        return R.restResult(null, errorCode);
    }

    /**
     * 从用户中移除角色
     *
     * @param userRolePojo
     * @return
     */
    @NeedPermission(roleIds = {"admin", "teacher"})
    @ApiOperation(value = "从用户中移除角色", notes = "从用户中移除角色", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteRolesFromUser", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public R deleteRolesFromUser(@RequestBody UserRolePojo userRolePojo) {
        ErrorCode errorCode = new ErrorCode();
        if (tsUserRoleService.deleteRolesFromUser(userRolePojo.getRoleIds(), userRolePojo.getUserId()) > 0) {
            errorCode.setCode(ErrorCode.OK);
            errorCode.setMsg(ErrorCode.OK_MSG);
        } else {
            errorCode.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
            errorCode.setMsg(DELETE_ROLES_FROM_USER);
        }
        return R.restResult(null, errorCode);
    }

    @ApiOperation(value = "获取用户信息带角色信息")
    @RequestMapping(value = "/getTSUserWithRoles/{userId}", method = RequestMethod.GET)
    public R getTSUserWithRoles(@PathVariable("userId") String userId) {
        TSUser user = tsUserService.getUserWithRolesByUid(DesUtil.decrypt(userId));
        TSUser resUser = new TSUser();
        resUser.setUid(user.getUid());
        resUser.setName(user.getName());
        resUser.setRoles(user.getRoles());
        ErrorCode errorCode = new ErrorCode();
        errorCode.setCode(ErrorCode.OK);
        errorCode.setMsg(ErrorCode.OK_MSG);
        return R.restResult(resUser, errorCode);
    }
}

