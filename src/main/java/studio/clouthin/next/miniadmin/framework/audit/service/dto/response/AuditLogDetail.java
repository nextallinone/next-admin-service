package studio.clouthin.next.miniadmin.framework.audit.service.dto.response;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import studio.clouthin.next.miniadmin.framework.audit.domain.AuditLog;

@Data
public class AuditLogDetail extends AuditLogSummary {

    private String params;

    private String exception;

    private String method;

    public static AuditLogDetail from(AuditLog obj) {
        if (obj == null) {
            return null;
        }
        AuditLogDetail result = new AuditLogDetail();
        BeanUtils.copyProperties(obj, result);
        byte[] details = obj.getExceptionDetail();
        result.setException(new String(ObjectUtil.isNotNull(details) ? details : "".getBytes()));
        return result;
    }
}
