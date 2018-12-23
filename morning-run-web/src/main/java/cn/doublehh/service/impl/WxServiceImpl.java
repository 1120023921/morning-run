package cn.doublehh.service.impl;

import cn.doublehh.common.constant.WechatConstant;
import cn.doublehh.service.IWxService;
import cn.doublehh.sport.model.Grade;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import cn.doublehh.wx.mp.config.WxMpConfiguration;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 胡昊
 * Description: 微信service实现
 * Date: 2018/12/24
 * Time: 0:22
 * Create: DoubleH
 */
@Service
@Slf4j
public class WxServiceImpl implements IWxService {

    @Autowired
    private TSUserService tsUserService;
    @Value("${wx.templateId.uploadGradeMsgId}")
    private String uploadGradeMsgId;
    @Value("${wx.url.authUrl}")
    public String authUrl;
    @Value("${wx.domain}")
    public String domain;
    @Value("${server.servlet.context-path}")
    public String contextPath;
    @Value("${wx.mp.configs[0].appId}")
    public String appId;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<Grade> sendUploadGradeMsg(List<Grade> gradeList) {
        log.info("GradeViewServiceImpl [sendUploadGradeMsg] 发送新成绩上传提醒");
        WxMpService wxMpService = WxMpConfiguration.getMpServices().get(appId);
        List<Grade> result = new LinkedList<>();
        gradeList.forEach(grade -> {
            TSUser tsUser = tsUserService.getUserByUid(grade.getJobNumber());
            Assert.notNull(tsUser, "uid对应的用户不存在");
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                    .toUser(tsUser.getWechatOpenid())
                    .templateId(uploadGradeMsgId)
                    .url(getAuthUrl())
                    .build();
            templateMessage.addData(new WxMpTemplateData("first", "您的体育成绩有更新", "#FF0000"));
            templateMessage.addData(new WxMpTemplateData("jobNumber", grade.getJobNumber(), "#173177"));
            templateMessage.addData(new WxMpTemplateData("name", tsUser.getName(), "#173177"));
            templateMessage.addData(new WxMpTemplateData("updateTime", LocalDateTime.now().format(df), "#173177"));
            try {
                wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            } catch (WxErrorException e) {
                result.add(grade);
            }
        });
        if (!CollectionUtils.isEmpty(result)) {
            log.error("成绩更新推送消息发送失败", result);
        }
        return result;
    }

    private String getAuthUrl() {
        try {
            return String.format(authUrl, appId, URLEncoder.encode(domain + contextPath + "/wx/auth/" + appId + "/login", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("WechatConstant [getAuthUrl] redirect encode失败", e);
            return null;
        }
    }
}
