package studio.clouthin.next.miniadmin.framework.audit.service.dto.response;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import studio.clouthin.next.miniadmin.framework.audit.domain.AuditLog;

import java.util.Date;

@Data
public class AuditLogSummary {

    private String id;

    private String description;

    private String requestIp;

    private Long time;

    private String address;

    private String browser;

    private Date createdAt;

    private String username;

    private String logType;

    public static AuditLogSummary from(AuditLog obj) {
        if (obj == null) {
            return null;
        }
        AuditLogSummary result = new AuditLogSummary();
        BeanUtils.copyProperties(obj, result);
        return result;
    }
}
