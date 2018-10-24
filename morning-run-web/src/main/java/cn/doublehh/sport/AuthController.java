package cn.doublehh.sport;

import cn.doublehh.wx.mp.config.WxMpConfiguration;
import com.alibaba.fastjson.JSONObject;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 胡昊
 * Description:
 * Date: 2018/7/29
 * Time: 14:17
 * Create: DoubleH
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(String code, String state) throws WxErrorException {
        WxMpService wxMpService = WxMpConfiguration.getMpServices().get("wxbe3c1744c0f71ab4");
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        return JSONObject.toJSONString(wxMpUser);
    }
}
