package studio.clouthin.next.miniadmin.framework.auth.dto.response;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import studio.clouthin.next.miniadmin.framework.auth.models.Resource;

@Data
public class PermissionSummary {

    private String id;

    private String name;

    private Boolean checked = Boolean.FALSE;

    public static PermissionSummary from(Resource obj) {
        if (obj == null) {
            return null;
        }
        PermissionSummary result = new PermissionSummary();
        BeanUtils.copyProperties(obj, result);
        return result;
    }
}
