package studio.clouthin.next.miniadmin.framework.audit.service.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import studio.clouthin.next.shared.annotations.FQuery;

import java.util.Date;
import java.util.List;

/**
 * 日志查询类
 */
@Data
public class AuditLogQueryCriteria {

    @FQuery(blurry = "username,description,address,requestIp,method,params")
    private String blurry;

    @FQuery
    private String logType;

    @FQuery(type = FQuery.Type.BETWEEN)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<Date> createdAt;
}
