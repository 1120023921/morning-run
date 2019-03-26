package cn.doublehh.sport;


import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.sport.model.LogInfo;
import cn.doublehh.sport.service.LogInfoService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2019-01-21
 */
@Slf4j
@RestController
@RequestMapping("/logInfo")
public class LogInfoController {

    @Autowired
    private LogInfoService logInfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RestTemplate restTemplate;

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
            int num = logInfoService.getTodayLogNum();
//            List<LogInfo> logInfoList = logInfoService.getTodayLog();
//            if (!CollectionUtils.isEmpty(logInfoList)) {
//                num = logInfoList.size();
//                redisTemplate.opsForValue().set("todayLogNum", num, 3600L, TimeUnit.SECONDS);
//            }
            redisTemplate.opsForValue().set("todayLogNum", num, 3600L, TimeUnit.SECONDS);
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
            int num = logInfoService.getAllLogNum();
//            List<LogInfo> logInfoList = logInfoService.findAll();
//            if (!CollectionUtils.isEmpty(logInfoList)) {
//                num = logInfoList.size();
//                redisTemplate.opsForValue().set("allLogNum", num, 3600L, TimeUnit.SECONDS);
//            }
            redisTemplate.opsForValue().set("allLogNum", num, 3600L, TimeUnit.SECONDS);
            return R.restResult(num, ErrorCodeInfo.SUCCESS);
        }
    }

    //    @Scheduled(cron = "00 00 04 ? * *")
    @Async
    @GetMapping(value = "/syncLogInfo")
    public R syncLogInfo() {
        log.info("LogInfoController [syncLogInfo] 开始处理所有未处理日志");
        AtomicReference<Integer> success = new AtomicReference<>(0);
        List<LogInfo> logInfoList = logInfoService.list(new QueryWrapper<LogInfo>().eq("is_valid", 1).eq("is_sync", 0));
        logInfoList.forEach(logInfo -> {
            try {
                Thread.sleep(5000);
                String response = restTemplate.getForObject("http://ip.taobao.com/service/getIpInfo.php?ip=" + logInfo.getIp(), String.class);
                JSONObject result = JSONObject.parseObject(response);
                if (result.getInteger("code") == 0) {
                    JSONObject data = result.getJSONObject("data");
                    String country = data.getString("country");
                    String region = data.getString("region");
                    String city = data.getString("city");
                    String isp = data.getString("isp");
                    logInfo.setAddress(country + " " + region + " " + city + " " + isp);
                    logInfo.setIsSync(1);
                    logInfoService.updateById(logInfo);
                    success.getAndSet(success.get() + 1);
                }
            } catch (Exception e) {

            }
        });
        return R.restResult(success.get() + "/" + logInfoList.size() + "日志处理完成", ErrorCodeInfo.SUCCESS);
    }
}

