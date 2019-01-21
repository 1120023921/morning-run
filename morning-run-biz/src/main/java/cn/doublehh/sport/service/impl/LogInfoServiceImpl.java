package cn.doublehh.sport.service.impl;

import cn.doublehh.sport.model.LogInfo;
import cn.doublehh.sport.dao.LogInfoMapper;
import cn.doublehh.sport.service.LogInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cz.mallat.uasparser.UserAgentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 胡昊
 * @since 2019-01-21
 */
@Slf4j
@Service
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {

    @Transactional
    @Override
    public Boolean addLogInfo(UserAgentInfo userAgentInfo, LogInfo logInfo) {
        log.info("LogInfoServiceImpl [addLogInfo] 添加访问日志 userAgentInfo=" + userAgentInfo);
        logInfo.setId(UUID.randomUUID().toString());
        logInfo.setBrowser(userAgentInfo.getUaFamily());
        logInfo.setBrowserVersion(userAgentInfo.getBrowserVersionInfo());
        logInfo.setDevice(userAgentInfo.getDeviceType());
        logInfo.setOperateSystem(userAgentInfo.getOsName());
        logInfo.setCreateTime(LocalDateTime.now());
        logInfo.setUpdateTime(LocalDateTime.now());
        logInfo.setVersion(1);
        logInfo.setIsValid(1);
        logInfo.setIsSync(0);
        return save(logInfo);
    }
}
