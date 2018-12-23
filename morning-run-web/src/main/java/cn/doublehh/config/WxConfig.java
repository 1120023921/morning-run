package cn.doublehh.config;

import cn.doublehh.common.constant.WechatConstant;
import cn.doublehh.wx.mp.config.WxMpConfiguration;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 胡昊
 * Description:
 * Date: 2018/12/24
 * Time: 0:11
 * Create: DoubleH
 */
@Configuration
public class WxConfig {

    @Value("${wx.url.authUrl}")
    public String authUrl;
    @Value("${wx.domain}")
    public String domain;
    @Value("${server.servlet.context-path}")
    public String contextPath;
    @Value("${wx.mp.configs[0].appId}")
    public String appId;

//    @Bean
//    public WxMpService getWxMpService() {
//        WxMpService wxMpService = WxMpConfiguration.getMpServices().get("wxbe3c1744c0f71ab4");
//        return wxMpService;
//    }
}
