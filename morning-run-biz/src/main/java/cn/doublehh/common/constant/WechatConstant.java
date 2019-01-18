package cn.doublehh.common.constant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class WechatConstant {

    @Value("${wx.url.authUrl}")
    private String authUrl;
    @Value("${wx.domain}")
    private String domain;
    @Value("${wx.domainNoPort}")
    private String domainNoPort;
    @Value("${wx.domain-web}")
    private String domainWeb;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${wx.mp.configs[0].appId}")
    private String appId;
    @Value("${wx.mp.configs[0].secret}")
    private String secret;
    @Value("${wx.templateId.uploadGradeMsgId}")
    private String uploadGradeMsgId;

    /**
     * 获取认证URL
     *
     * @return
     */
    public String getAuthUrl() {
        try {
            return String.format(authUrl, appId, URLEncoder.encode(domain + contextPath + "/wx/auth/" + appId + "/login", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("WechatConstant [getAuthUrl] redirect encode失败", e);
            return null;
        }
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public String getUploadGradeMsgId() {
        return uploadGradeMsgId;
    }

    public void setUploadGradeMsgId(String uploadGradeMsgId) {
        this.uploadGradeMsgId = uploadGradeMsgId;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getDomainWeb() {
        return domainWeb;
    }

    public void setDomainWeb(String domainWeb) {
        this.domainWeb = domainWeb;
    }
}