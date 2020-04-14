package studio.clouthin.next.miniadmin.framework.audit.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studio.clouthin.next.miniadmin.framework.audit.log.AuditIgnored;
import studio.clouthin.next.miniadmin.framework.audit.service.AuditLogService;
import studio.clouthin.next.miniadmin.framework.audit.service.dto.request.AuditLogQueryCriteria;
import studio.clouthin.next.miniadmin.framework.audit.service.dto.response.AuditLogDetail;
import studio.clouthin.next.miniadmin.framework.audit.service.dto.response.AuditLogSummary;
import studio.clouthin.next.shared.utils.SecurityUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auditLogs")
@Api(tags = "监控：日志管理")
public class AuditLogController {

    @Autowired
    private AuditLogService logService;


    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, AuditLogQueryCriteria criteria) throws IOException {
        criteria.setLogType("INFO");
        logService.download(logService.queryAll(criteria), response);
    }

    @ApiOperation("导出错误数据")
    @GetMapping(value = "/error/download")
    public void errorDownload(HttpServletResponse response, AuditLogQueryCriteria criteria) throws IOException {
        criteria.setLogType("ERROR");
        logService.download(logService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("日志查询")
    public Page<AuditLogSummary> getLogs(AuditLogQueryCriteria criteria, Pageable pageable) {
//        criteria.setLogType("INFO");
        return logService.queryAll(criteria, pageable);
    }

    @GetMapping(value = "/user")
    @ApiOperation("用户日志查询")
    public Page<AuditLogSummary> getUserLogs(AuditLogQueryCriteria criteria, Pageable pageable) {
        criteria.setLogType("INFO");
        criteria.setBlurry(SecurityUtils.getUsername());
        return logService.queryAllByUser(criteria, pageable);
    }

    @GetMapping(value = "/error")
    @ApiOperation("错误日志查询")
    @AuditIgnored
    public Page<AuditLogSummary> getErrorLogs(AuditLogQueryCriteria criteria, Pageable pageable) {
        criteria.setLogType("ERROR");
        return logService.queryAll(criteria, pageable);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("日志异常详情查询")
    public AuditLogDetail getErrorLogs(@PathVariable String id) {
        return logService.findByErrDetail(id);
    }

    @DeleteMapping(value = "/del/error")
    @ApiOperation("删除所有ERROR日志")
    public ResponseEntity<Object> delAllByError() {
        logService.delAllByError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/info")
    @ApiOperation("删除所有INFO日志")
    public ResponseEntity<Object> delAllByInfo() {
        logService.delAllByInfo();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
