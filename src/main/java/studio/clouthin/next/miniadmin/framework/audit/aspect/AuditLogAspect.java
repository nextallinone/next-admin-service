package studio.clouthin.next.miniadmin.framework.audit.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import studio.clouthin.next.miniadmin.framework.audit.domain.AuditLog;
import studio.clouthin.next.miniadmin.framework.audit.service.AuditLogService;
import studio.clouthin.next.shared.utils.RequestHolder;
import studio.clouthin.next.shared.utils.SecurityUtils;
import studio.clouthin.next.shared.utils.StringUtils;
import studio.clouthin.next.shared.utils.ThrowableUtil;

import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
@Slf4j
public class AuditLogAspect {

    private final AuditLogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public AuditLogAspect(AuditLogService logService) {
        this.logService = logService;
    }

    /**
     * 配置切入点
     */
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
    @Pointcut("execution(@org.springframework.web.bind.annotation.* * *(..))")
    public void logPointcut() {
    }


    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        AuditLog log = new AuditLog("INFO", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), joinPoint, log);
        return result;
    }


    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
        AuditLog log = new AuditLog("ERROR", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), (ProceedingJoinPoint) joinPoint, log);
    }

    public String getUsername() {
        try {
            return SecurityUtils.getUsername();
        } catch (Exception e) {
            return "";
        }
    }
}
