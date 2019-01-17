package cn.doublehh.advice;

import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.common.utils.DesUtil;
import cn.doublehh.system.model.TSRole;
import cn.doublehh.system.model.TSUser;
import cn.doublehh.system.service.TSUserService;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 胡昊
 * Description: 权限拦截器
 * Date: 2019/1/11
 * Time: 20:48
 * Create: DoubleH
 */
@Aspect
@Component
@Slf4j
public class PermissionAdvice {

    @Autowired
    private TSUserService tsUserService;

    @Pointcut("@annotation(cn.doublehh.common.annotation.NeedPermission)")
    public void permission() {

    }

    @Around("permission()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("权限判断：" + proceedingJoinPoint.getSignature().getName());
        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            String user = request.getHeader("User");
            TSUser tsUser = tsUserService.getUserWithRolesByUid(DesUtil.decrypt(user));
            if (null == tsUser) {
                log.warn(ErrorCodeInfo.Unauthorized.getMsg() + " " + request.getRemoteAddr() + " " + request.getRequestURI() + " User-Agent: " + request.getHeader("User-Agent"));
                return R.restResult(ErrorCodeInfo.Unauthorized, ErrorCodeInfo.Unauthorized);
            }
            List<TSRole> roles = tsUser.getRoles().stream().filter(tsRole -> "admin".equals(tsRole.getRoid())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(roles)) {
                log.warn(ErrorCodeInfo.Forbidden.getMsg() + " " + request.getRemoteAddr() + " " + request.getRequestURI() + " User-Agent: " + request.getHeader("User-Agent"));
                return R.restResult(ErrorCodeInfo.Forbidden, ErrorCodeInfo.Forbidden);
            }
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return R.restResult(null, ErrorCodeInfo.FAILED);
    }
}
