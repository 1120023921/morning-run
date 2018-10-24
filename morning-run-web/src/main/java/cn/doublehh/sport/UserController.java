package cn.doublehh.sport;

import cn.doublehh.common.pojo.ErrorCode;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import cn.doublehh.wx.mp.config.WxMpConfiguration;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @author 胡昊
 * Description:微信用户
 * Date: 2018/10/10
 * Time: 20:13
 * Create: DoubleH
 */
@Api(tags = "微信用户信息管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TSUserService tsUserService;

    public String getUserInfo(String code, String state) throws WxErrorException {
        WxMpService wxMpService = WxMpConfiguration.getMpServices().get("wxbe3c1744c0f71ab4");
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        return JSONObject.toJSONString(wxMpUser);
    }

    /**
     * 绑定微信openid
     *
     * @param jobNumber
     * @param name
     * @param openid
     * @return
     */
    @ApiOperation(value = "绑定微信openid", httpMethod = "PATCH")
    @RequestMapping(value = "/addUserOpenId", method = RequestMethod.PATCH)
    public R addUserOpenId(String jobNumber, String name, String openid) {
        ErrorCode errorCode = new ErrorCode();
        TSUser tsUser = tsUserService.getOne(new QueryWrapper<TSUser>().eq("uid", jobNumber).eq("name", name));
        if (null == tsUser) {
            errorCode.setCode(ErrorCode.NOT_FOUND);
            errorCode.setMsg("用户信息不存在，请检查后重新输入");
            return R.restResult(null, errorCode);
        }
        tsUser.setWechatOpenid(openid);
        tsUser.setUpdateTime(LocalDateTime.now());
        boolean res = tsUserService.updateById(tsUser);
        if (res) {
            errorCode.setCode(ErrorCode.OK);
            errorCode.setMsg(ErrorCode.OK_MSG);
            return R.restResult(null, errorCode);
        } else {
            errorCode.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
            errorCode.setMsg(ErrorCode.UPDATE_FAIL);
            return R.restResult(null, errorCode);
        }
    }

}
