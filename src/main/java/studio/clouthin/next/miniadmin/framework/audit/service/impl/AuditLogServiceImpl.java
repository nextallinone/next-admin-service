package studio.clouthin.next.miniadmin.framework.audit.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import studio.clouthin.next.miniadmin.framework.audit.domain.AuditLog;
import studio.clouthin.next.miniadmin.framework.audit.log.AuditIgnored;
import studio.clouthin.next.miniadmin.framework.audit.repository.AuditLogRepository;
import studio.clouthin.next.miniadmin.framework.audit.service.AuditLogService;
import studio.clouthin.next.miniadmin.framework.audit.service.dto.request.AuditLogQueryCriteria;
import studio.clouthin.next.miniadmin.framework.audit.service.dto.response.AuditLogDetail;
import studio.clouthin.next.miniadmin.framework.audit.service.dto.response.AuditLogSummary;
import studio.clouthin.next.shared.utils.FileUtil;
import studio.clouthin.next.shared.utils.JPAQueryHelper;
import studio.clouthin.next.shared.utils.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository logRepository;


    @Override
    public Page<AuditLogSummary> queryAll(AuditLogQueryCriteria criteria, Pageable pageable) {
        return logRepository.findAll(
                ((root, criteriaQuery, cb) ->
                        JPAQueryHelper.getPredicate(root, criteria, cb)), pageable)
                .map(AuditLogSummary::from);
    }

    @Override
    public List<AuditLog> queryAll(AuditLogQueryCriteria criteria) {
        return logRepository.findAll(((root, criteriaQuery, cb) -> JPAQueryHelper.getPredicate(root, criteria, cb)));
    }

    @Override
    public Page<AuditLogSummary> queryAllByUser(AuditLogQueryCriteria criteria, Pageable pageable) {
        return logRepository.findAll(
                ((root, criteriaQuery, cb) ->
                        JPAQueryHelper.getPredicate(root, criteria, cb)), pageable)
                .map(AuditLogSummary::from);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, AuditLog log) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = (method.getDeclaringClass() != null ? AopUtils.getTargetClass(method.getDeclaringClass()) : null);
        if (targetClass != null && targetClass.isAnnotationPresent(AuditIgnored.class)) {
            joinPoint.proceed();
        }

        Api classAuditMetadata = (Api) targetClass.getAnnotation(Api.class);
        ApiOperation methodAuditMetadata = method.getAnnotation(ApiOperation.class);
        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null && methodAuditMetadata != null) {
            StringBuilder descption = new StringBuilder("");
            if (classAuditMetadata != null) {
                descption.append(classAuditMetadata.tags());
                descption.append("->");
            }
            descption.append(methodAuditMetadata.value());
            log.setDescription(descption.toString());
        }
        assert log != null;
        log.setRequestIp(ip);

        String loginPath = "login";
        if (loginPath.equals(signature.getName())) {
            try {
                assert argValues != null;
                username = new JSONObject(argValues[0]).get("username").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        logRepository.save(log);
    }

    @Override
    public AuditLogDetail findByErrDetail(String id) {
        AuditLog log = logRepository.findById(id).orElseGet(AuditLog::new);
        return AuditLogDetail.from(log);
    }

    @Override
    public void download(List<AuditLog> logs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AuditLog log : logs) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", log.getUsername());
            map.put("IP", log.getRequestIp());
            map.put("IP来源", log.getAddress());
            map.put("描述", log.getDescription());
            map.put("浏览器", log.getBrowser());
            map.put("请求耗时/毫秒", log.getTime());
            map.put("异常详情", new String(ObjectUtil.isNotNull(log.getExceptionDetail()) ? log.getExceptionDetail() : "".getBytes()));
            map.put("创建日期", log.getCreatedAt());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        logRepository.deleteByLogType("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        logRepository.deleteByLogType("INFO");
    }
}
