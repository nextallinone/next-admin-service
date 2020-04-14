package studio.clouthin.next.miniadmin.framework.audit.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import studio.clouthin.next.miniadmin.framework.audit.service.dto.request.AuditLogQueryCriteria;
import studio.clouthin.next.miniadmin.framework.audit.service.dto.response.AuditLogDetail;
import studio.clouthin.next.miniadmin.framework.audit.service.dto.response.AuditLogSummary;
import studio.clouthin.next.miniadmin.framework.audit.domain.AuditLog;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public interface AuditLogService {

    /**
     * 分页查询
     *
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return /
     */
    Page<AuditLogSummary> queryAll(AuditLogQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     *
     * @param criteria 查询条件
     * @return /
     */
    List<AuditLog> queryAll(AuditLogQueryCriteria criteria);

    /**
     * 查询用户日志
     *
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return -
     */
    Page<AuditLogSummary> queryAllByUser(AuditLogQueryCriteria criteria, Pageable pageable);

    /**
     * 保存日志数据
     *
     * @param username  用户
     * @param browser   浏览器
     * @param ip        请求IP
     * @param joinPoint /
     * @param log       日志实体
     */
    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, AuditLog log) throws Throwable;

    /**
     * 查询异常详情
     *
     * @param id 日志ID
     * @return Object
     */
    AuditLogDetail findByErrDetail(String id);

    /**
     * 导出日志
     *
     * @param logs     待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<AuditLog> logs, HttpServletResponse response) throws IOException;

    /**
     * 删除所有错误日志
     */
    void delAllByError();

    /**
     * 删除所有INFO日志
     */
    void delAllByInfo();
}
