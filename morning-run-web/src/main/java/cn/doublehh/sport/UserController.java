package cn.doublehh.sport;

import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import cn.doublehh.wx.mp.config.WxMpConfiguration;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 绑定微信openid
     *
     * @param jobNumber 学号
     * @param password  密码（身份证后六位）
     * @param token     openid的token
     * @return
     */
    @ApiOperation(value = "绑定微信openid", httpMethod = "PATCH")
    @RequestMapping(value = "/addUserOpenId", method = RequestMethod.PATCH)
    public R addUserOpenId(String jobNumber, String password, String token) {
        TSUser tsUser = tsUserService.getOne(new QueryWrapper<TSUser>().eq("uid", jobNumber).eq("password", password));
        if (null == tsUser) {
            throw new RuntimeException("输入信息有误，请检查后重新输入");
        }
        String openid = AuthController.openidMap.get(token);
        if (StringUtils.isEmpty(openid)) {
            throw new RuntimeException("无效token");
        }
        tsUser.setWechatOpenid(openid);
        tsUser.setUpdateTime(LocalDateTime.now());
        boolean res = tsUserService.updateById(tsUser);
        if (res) {
            return R.restResult(null, ErrorCodeInfo.SUCCESS);
        } else {
            throw new RuntimeException("绑定用户openid失败");
        }
    }
}
