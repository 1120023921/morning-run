package cn.doublehh.sport;

import cn.doublehh.common.constant.WechatConstant;
import cn.doublehh.common.utils.EncryptUtil;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author 胡昊
 * Description:
 * Date: 2018/7/29
 * Time: 14:17
 * Create: DoubleH
 */
@Controller
@RequestMapping("/wx/auth/{appid}")
public class AuthController {

    static Map<String, String> openidMap = new HashMap<>();
    @Autowired
    private TSUserService tsUserService;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatConstant wechatConstant;

    @RequestMapping("/login")
    public String login(@PathVariable String appid, @RequestParam String code, String state) throws WxErrorException {
        try {
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
            TSUser user = tsUserService.getUserByWechatOpenId(wxMpUser.getOpenId());
            //微信未绑定跳转绑定页面
            if (user == null) {
                String token = UUID.randomUUID().toString();
                openidMap.put(token, wxMpUser.getOpenId());
                return "redirect:" + wechatConstant.getDomainWeb() + "/#/bindInfo?token=" + token;
            } else {
                return "redirect:" + wechatConstant.getDomainWeb() + "/#/?jobNumber=" + EncryptUtil.encode(user.getUid());
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "/error";
    }
}
