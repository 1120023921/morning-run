package cn.doublehh.sport;


import cn.doublehh.common.controller.BaseController;
import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.sport.model.Grade;
import cn.doublehh.sport.model.GradeParams;
import cn.doublehh.sport.model.GradeView;
import cn.doublehh.sport.service.GradeService;
import cn.doublehh.sport.service.GradeViewService;
import cn.doublehh.sport.vo.AttendanceVo;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import cn.doublehh.wx.mp.config.WxMpConfiguration;
import com.alibaba.druid.wall.violation.ErrorCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 成绩查询
 *
 * @author 胡昊
 * @since 2018-10-25
 */
@RestController
@RequestMapping("/wx/auth/{appid}/gradeView/")
public class GradeViewController extends BaseController<GradeView> {

    private static final Logger logger = LoggerFactory.getLogger(GradeViewController.class);
    @Autowired
    private TSUserService tsUserService;
    @Autowired
    private GradeViewService gradeViewService;
    @Autowired
    private GradeService gradeService;

    /**
     * 获取体质测试成绩
     *
     * @param gradeParams 查询条件对象
     * @return
     */
    @RequestMapping(value = "/getGradeByJobNumberAndType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Map<String, List<GradeView>>> getGradeByJobNumberAndType(@RequestBody GradeParams gradeParams) {
        Map<String, List<GradeView>> gradeList = gradeViewService.getGradeByJobNumberAndType(gradeParams.getJobNumber(), gradeParams.getType());
        return R.restResult(gradeList, ErrorCodeInfo.SUCCESS);

    }

    /**
     * 获取体教考勤成绩
     *
     * @param gradeParams 查询条件对象
     * @return
     */
    @RequestMapping(value = "/getAttendanceVo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Map<String, List<AttendanceVo>>> getAttendanceVo(@RequestBody GradeParams gradeParams) {
        Map<String, List<AttendanceVo>> attendanceList = gradeViewService.getAttendanceVo(gradeParams.getJobNumber(), gradeParams.getType());
        return R.restResult(attendanceList, ErrorCodeInfo.SUCCESS);
    }

//    @RequestMapping("/showGrade")
//    public String login(@PathVariable String appid, @RequestParam String code, String state, ModelMap map) throws WxErrorException {
//        WxMpService wxMpService = WxMpConfiguration.getMpServices().get(appid);
//        try {
//            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
//            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
//            TSUser user = tsUserService.getUserByWechatOpenId(wxMpUser.getOpenId());
//            //微信未绑定跳转绑定页面
//            if (user == null) {
//                map.put("openid", wxMpUser.getOpenId());
//                return "bindInfo";
//            } else {
//                map.put("grade", gradeViewService.pageMaps(new Page<GradeView>(1, -1), new QueryWrapper<GradeView>().eq("job_number", user.getUid())).getRecords());
//                return "showGrade";
//            }
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
//        return "/error";
//    }
}

