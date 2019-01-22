package cn.doublehh.sport;


import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.sport.model.LogInfo;
import cn.doublehh.sport.service.LogInfoService;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2019-01-21
 */
@RestController
@RequestMapping("/logInfo")
public class LogInfoController {

    @Autowired
    private LogInfoService logInfoService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取当天访问量
     *
     * @return 当天访问量
     */
    @GetMapping(value = "/getTodayLogNum", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R getTodayLogNum() {
        Object todayLogNum = redisTemplate.opsForValue().get("todayLogNum");
        if (null != todayLogNum) {
            return R.restResult(todayLogNum, ErrorCodeInfo.SUCCESS);
        } else {
            int num = 0;
            List<LogInfo> logInfoList = logInfoService.getTodayLog();
            if (!CollectionUtils.isEmpty(logInfoList)) {
                num = logInfoList.size();
                redisTemplate.opsForValue().set("todayLogNum", num, 600L, TimeUnit.SECONDS);
            }
            return R.restResult(num, ErrorCodeInfo.SUCCESS);
        }
    }

    /**
     * 获取所有访问量
     *
     * @return 当天访问量
     */
    @GetMapping(value = "/getAllLogNum", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R getAllLogNum() {
        Object todayLogNum = redisTemplate.opsForValue().get("allLogNum");
        if (null != todayLogNum) {
            return R.restResult(todayLogNum, ErrorCodeInfo.SUCCESS);
        } else {
            int num = 0;
            List<LogInfo> logInfoList = logInfoService.findAll();
            if (!CollectionUtils.isEmpty(logInfoList)) {
                num = logInfoList.size();
                redisTemplate.opsForValue().set("allLogNum", num, 600L, TimeUnit.SECONDS);
            }
            return R.restResult(num, ErrorCodeInfo.SUCCESS);
        }
    }
}

