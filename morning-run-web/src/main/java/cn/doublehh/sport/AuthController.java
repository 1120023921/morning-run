package cn.doublehh.sport;

import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import cn.doublehh.wx.mp.config.WxMpConfiguration;
import com.alibaba.fastjson.JSONObject;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 胡昊
 * Description:
 * Date: 2018/7/29
 * Time: 14:17
 * Create: DoubleH
 */
@Controller
@RequestMapping("/auth/{appid}")
public class AuthController {

    @Autowired
    private TSUserService tsUserService;

    @RequestMapping("/login")
    public String login(@PathVariable String appid, @RequestParam String code, String state, ModelMap map) throws WxErrorException {
        WxMpService wxMpService = WxMpConfiguration.getMpServices().get(appid);
        try {
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
            TSUser user = tsUserService.getUserByWechatOpenId(wxMpUser.getOpenId());
            //微信未绑定跳转绑定页面
            if (user == null) {
                map.put("openid", wxMpUser.getOpenId());
                return "bindInfo";
            } else {

            }
        }catch (WxErrorException e){
            e.printStackTrace();
        }
        return "/error";
    }
}
