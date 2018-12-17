package cn.doublehh.sport;

import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import cn.doublehh.wx.mp.config.WxMpConfiguration;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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

    @Autowired
    private TSUserService tsUserService;

    @RequestMapping("/login")
    public String login(@PathVariable String appid, @RequestParam String code, String state) throws WxErrorException {
        WxMpService wxMpService = WxMpConfiguration.getMpServices().get(appid);
        try {
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
            TSUser user = tsUserService.getUserByWechatOpenId(wxMpUser.getOpenId());
            //微信未绑定跳转绑定页面
            if (user == null) {
//                map.put("openid", wxMpUser.getOpenId());
                return "bindInfo";
            } else {
//                map.put("gradess", gradeViewService.getGradeByJobNumber(user.getUid()).getRecords());
                return "redirect:http://192.168.199.217:8081/#/?jobNumber=" + user.getUid();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "/error";
    }
}
