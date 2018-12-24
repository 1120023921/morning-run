package cn.doublehh.config;

import cn.doublehh.common.constant.WechatConstant;
import cn.doublehh.wx.mp.config.WxMpConfiguration;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 胡昊
 * Description:
 * Date: 2018/12/24
 * Time: 0:11
 * Create: DoubleH
 */
@Configuration
@Component
public class WxConfig {

    @Autowired
    private WechatConstant wechatConstant;

    @Bean
    public WxMpService getWxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    /**
     * 微信公众号配置 bean注册
     *
     * @return WxMpConfigStorage
     * @time 下午6:08:41
     * @version V1.0
     */
    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        // 设置开发者的id和密钥
        wxMpConfigStorage.setAppId(wechatConstant.getAppId());
        wxMpConfigStorage.setSecret(wechatConstant.getSecret());
        return wxMpConfigStorage;
    }
}
