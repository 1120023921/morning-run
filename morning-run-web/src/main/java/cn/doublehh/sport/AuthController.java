package cn.doublehh.sport;

import cn.doublehh.common.constant.WechatConstant;
import cn.doublehh.common.utils.DesUtil;
import cn.doublehh.sport.model.LogInfo;
import cn.doublehh.sport.service.LogInfoService;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
@Slf4j
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
    @Autowired
    private LogInfoService logInfoService;

    @RequestMapping("/login")
    public String login(@PathVariable String appid, @RequestParam String code, String state, HttpServletRequest request) throws WxErrorException {
        try {
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
            TSUser user = tsUserService.getUserByWechatOpenId(wxMpUser.getOpenId());
            String ua = request.getHeader("User-Agent");
            LogInfo logInfo = new LogInfo();
            logInfo.setIp(request.getRemoteAddr());
            logInfo.setLanguage(request.getHeader("Accept-Language"));
            logInfo.setUri(request.getRequestURI());
            logInfo.setUserAgent(ua);
            UserAgentInfo userAgentInfo = new UASparser(OnlineUpdater.getVendoredInputStream()).parse(ua);
            //微信未绑定跳转绑定页面
            if (user == null) {
                logInfo.setUserId(wxMpUser.getOpenId());
                logInfo.setDescription("绑定");
                logInfoService.addLogInfo(userAgentInfo, logInfo);
                String token = UUID.randomUUID().toString();
                openidMap.put(token, wxMpUser.getOpenId());
                return "redirect:" + wechatConstant.getDomainWeb() + "/#/bindInfo?token=" + token;
            } else {
                logInfo.setUserId(user.getUid());
                logInfo.setDescription("登录");
                logInfoService.addLogInfo(userAgentInfo, logInfo);
                return "redirect:" + wechatConstant.getDomainWeb() + "/#/?jobNumber=" + DesUtil.encrypt(user.getUid());
            }
        } catch (WxErrorException e) {
            log.error("AuthController [login] 微信认证失败", e);
            throw new RuntimeException("微信认证失败");
        } catch (IOException e) {
            log.error("AuthController [login] AU解析异常", e);
            throw new RuntimeException("AU解析异常");
        }
    }
}
