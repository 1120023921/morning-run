/**
 *
 */
package cn.doublehh.system.controller;

//import cn.doublehh.pojo.PhoneCodePojo;
//import cn.doublehh.pojo.RegisterPojo;
//import cn.doublehh.pojo.ResultBean;
//import cn.doublehh.system.model.User;

import cn.doublehh.common.pojo.ErrorCode;
import cn.doublehh.common.utils.MD5Utils;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserRoleService;
import cn.doublehh.system.service.TSUserService;
//import cn.doublehh.system.service.UserRoleService;
//import cn.doublehh.system.service.UserService;
//import cn.doublehh.utils.alimessage.ALiSms;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
//import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Lay
 */
@Api(tags = "系统管理")
@RestController
public class SystemController {

    private static final String NO_PREMISSION = "NO_PREMISSION";
    private static final String USER_NOT_FOUNFD = "用户不存在";
    private static final String USER_LOCKED = "用户已锁定";
    private static final String LOGIN_INFO_ERROR = "登录名或密码错误";
    private static final String TEL_EXIST = "手机号已注册";
    private static final String NO_CHECKCODE = "请先获取验证码";
    private static final String CHECKCODE_ERROR = "验证码错误";
    private static final String CHECKCODE_FAIL = "验证码发送失败";
    private static final String TEL_EMPTY = "手机号为空";

    @Autowired
    private TSUserService userService;
    @Autowired
    private TSUserRoleService userRoleService;

    @ApiOperation(value = "无权限", notes = "无权限", httpMethod = "POST")
    @RequestMapping(value = "/403", method = RequestMethod.POST)
    public R error403() {
        ErrorCode errorCode = new ErrorCode();
        errorCode.setCode(ErrorCode.FORBIDDEN);
        errorCode.setMsg(ErrorCode.FORBIDDEN_MSG);
        return R.restResult(null, errorCode);
    }

    @ApiOperation(value = "未登陆", notes = "未登陆", httpMethod = "POST")
    @RequestMapping(value = "/401", method = RequestMethod.POST)
    public R error401() {
        ErrorCode errorCode = new ErrorCode();
        errorCode.setCode(ErrorCode.UNAUTHORIZED);
        errorCode.setMsg(ErrorCode.UNAUTHORIZED_MSG);
        return R.restResult(null, errorCode);
    }

    /**
     * 发送手机验证码
     *
     * @param phoneCodePojo
     * @param session
     * @return
     */
//    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码", httpMethod = "POST")
//    @RequestMapping(value = "/sendCode",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResultBean sendCode(@RequestBody PhoneCodePojo phoneCodePojo, HttpSession session) {
//        ResultBean resultBean = new ResultBean();
//        if (StringUtils.isEmpty(phoneCodePojo.getPhoneNumber())) {
//            resultBean.setCode(ResultBean.NOT_FOUND);
//            resultBean.setMsg(TEL_EMPTY);
//            return resultBean;
//        }
//        Random random = new Random();
//        int max = 999999;
//        int min = 100000;
//        int phoneCode = random.nextInt(max) % (max - min + 1) + min;
//        try {
//            SendSmsResponse sendResult = ALiSms.sendSms(phoneCodePojo.getPhoneNumber(), "广俊化工", "SMS_117475116", "{\"code\":" + phoneCode + "}");
//            if ("OK".equals(sendResult.getCode())) {
//                session.setAttribute(phoneCodePojo.getCodeType(), phoneCode);
//                resultBean.setCode(ResultBean.OK);
//                resultBean.setMsg(ResultBean.OK_MSG);
//            } else {
//                resultBean.setCode(ResultBean.INTERNAL_SERVER_ERROR);
//                resultBean.setMsg(CHECKCODE_FAIL);
//            }
//        } catch (ClientException e) {
//            e.printStackTrace();
//            resultBean.setCode(ResultBean.INTERNAL_SERVER_ERROR);
//            resultBean.setMsg(e.getMessage());
//        }
//        return resultBean;
//    }

    /**
     * 用户登录
     *
     * @param user
     * @param session
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R login(@RequestBody TSUser user, HttpSession session) {
        ErrorCode errorCode = new ErrorCode();
//		String sysCheckCode = (String) session.getAttribute("loginRand");
//		if(!sysCheckCode.equals(checkCode)) {
//			result.setSuccess(false);
//			result.setMsg("验证码错误");
//			return result;
//		}
        user.setPassword(MD5Utils.generateToken(user.getPassword()));
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUid(), user.getPassword());
        TSUser userWithRoles = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            userWithRoles = userService.getUserWithRolesByUid(user.getUid());
            userWithRoles.setPassword(null);
            session.setAttribute("user", userWithRoles);
            errorCode.setCode(ErrorCode.OK);
            errorCode.setMsg(ErrorCode.OK_MSG);
        } catch (UnknownAccountException e) {
            errorCode.setCode(ErrorCode.NOT_FOUND);
            errorCode.setMsg(USER_NOT_FOUNFD);
        } catch (LockedAccountException e) {
            errorCode.setCode(ErrorCode.FORBIDDEN);
            errorCode.setMsg(USER_LOCKED);
        } catch (AuthenticationException e) {
            errorCode.setCode(ErrorCode.FORBIDDEN);
            errorCode.setMsg(LOGIN_INFO_ERROR);
        }
        return R.restResult(userWithRoles, errorCode);
    }

    /**
     * 用户注册
     *
     * @param registerPojo
     * @return
     */
//    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
//    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResultBean register(@RequestBody RegisterPojo registerPojo, HttpSession session) {
//        ResultBean resultBean = new ResultBean();
//        if (StringUtils.isEmpty(registerPojo.getTel())) {
//            resultBean.setCode(ResultBean.NOT_FOUND);
//            resultBean.setMsg(TEL_EMPTY);
//            return resultBean;
//        }
//        if (null != userService.getUserByTel(registerPojo.getTel())) {
//            resultBean.setCode(ResultBean.UNPROCESABLE_ENTITY);
//            resultBean.setMsg(TEL_EXIST);
//            return resultBean;
//        }
//        Integer mPhoneCode = (Integer) session.getAttribute("register");
//        if (mPhoneCode == null) {
//            resultBean.setCode(ResultBean.NOT_FOUND);
//            resultBean.setMsg(NO_CHECKCODE);
//            return resultBean;
//        }
//        if (!registerPojo.getCode().equals(mPhoneCode.toString())) {
//            resultBean.setCode(ResultBean.FORBIDDEN);
//            resultBean.setMsg(CHECKCODE_ERROR);
//            return resultBean;
//        }
//        session.removeAttribute("register");
//        String id = UUID.randomUUID().toString();
//        User user = new User();
//        BeanUtils.copyProperties(registerPojo, user);
//        user.setId(id);
//        user.setPassword(MD5Utils.generateToken(user.getPassword()));
//        if ((userService.insertUser(user) & userRoleService.addRolesToUser(new String[]{"2"}, id)) > 0) {
//            resultBean.setCode(ResultBean.OK);
//            resultBean.setMsg(ResultBean.OK_MSG);
//            session.removeAttribute("register");
//            return resultBean;
//        } else {
//            resultBean.setCode(ResultBean.UNPROCESABLE_ENTITY);
//            resultBean.setMsg(ResultBean.UNPROCESABLE_ENTITY_MSG);
//            return resultBean;
//        }
//    }

    /**
     * 注销
     *
     * @return
     */
//    @ApiOperation(value = "用户注销", notes = "用户注销", httpMethod = "GET")
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public ResultBean logout() {
//        ResultBean resultBean = new ResultBean();
//        try {
//            Subject subject = SecurityUtils.getSubject();
//            subject.logout();
//            resultBean.setCode(ResultBean.OK);
//            resultBean.setMsg(ResultBean.OK_MSG);
//            return resultBean;
//        } catch (Exception e) {
//            resultBean.setCode(ResultBean.INTERNAL_SERVER_ERROR);
//            resultBean.setMsg(e.getMessage());
//            return resultBean;
//        }
//    }
}
