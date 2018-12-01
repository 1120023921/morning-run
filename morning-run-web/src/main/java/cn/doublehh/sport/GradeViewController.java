package cn.doublehh.sport;


import cn.doublehh.common.controller.BaseController;
import cn.doublehh.sport.model.GradeView;
import cn.doublehh.sport.service.GradeViewService;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import cn.doublehh.wx.mp.config.WxMpConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <p>
 * VIEW 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2018-10-25
 */
@Controller
@RequestMapping("/wx/auth/{appid}/gradeView/")
public class GradeViewController extends BaseController<GradeView> {

    @Autowired
    private TSUserService tsUserService;
    @Autowired
    private GradeViewService gradeViewService;

    @RequestMapping("/showGrade")
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
                map.put("grade", gradeViewService.pageMaps(new Page<GradeView>(1, -1), new QueryWrapper<GradeView>().eq("job_number", user.getUid())).getRecords());
                return "showGrade";
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "/error";
    }
}

