package studio.clouthin.next.miniadmin.framework.auth.dto.response;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import studio.clouthin.next.miniadmin.framework.auth.models.Role;

@Data
public class RoleSummary {

    private String id;

    private String name;

    private String remark;

    private Boolean checked;

    public static RoleSummary from(Role obj) {
        if (obj == null) {
            return null;
        }
        RoleSummary result = new RoleSummary();
        BeanUtils.copyProperties(obj, result);
        return result;
    }
}
