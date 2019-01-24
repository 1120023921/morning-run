package cn.doublehh.sport.service;

import cn.doublehh.sport.model.LogInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import cz.mallat.uasparser.UserAgentInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2019-01-21
 */
public interface LogInfoService extends IService<LogInfo> {

    /**
     * 新增访问日志
     *
     * @param userAgentInfo UA信息
     * @param logInfo       日志对象
     * @return 新增结果
     */
    Boolean addLogInfo(UserAgentInfo userAgentInfo, LogInfo logInfo);

    /**
     * 获取当天日志
     *
     * @return 日志列表
     */
    List<LogInfo> getTodayLog();

    /**
     * 获取所有日志
     *
     * @return 日志列表
     */
    List<LogInfo> findAll();

    /**
     * 获取所有未处理的日志
     *
     * @return 日志列表
     */
    List<LogInfo> getAllNoSync();
}