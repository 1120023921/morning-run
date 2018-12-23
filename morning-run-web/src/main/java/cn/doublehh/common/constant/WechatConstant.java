package cn.doublehh.common.constant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author 胡昊
 * Description: 微信常量
 * Date: 2018/12/23
 * Time: 23:29
 * Create: DoubleH
 */
@Slf4j
public class WechatConstant {

    @Value("${wx.url.authUrl}")
    public static String authUrl;
    @Value("${wx.domain}")
    public static String domain;
    @Value("${server.servlet.context-path}")
    public static String contextPath;
    @Value("${wx.mp.configs.appId}")
    public static String appId;

    public static String getAuthUrl() {
        try {
            return String.format(authUrl, appId, URLEncoder.encode(domain + contextPath + "/wx/auth/" + appId + "/login", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("WechatConstant [getAuthUrl] redirect encode失败", e);
            return null;
        }
    }
}
