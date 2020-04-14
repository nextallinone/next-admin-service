package studio.clouthin.next.miniadmin.framework.auth.dto.response;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import studio.clouthin.next.miniadmin.framework.auth.models.Organization;

@Data
public class OrganizationSummary {

    private String id;

    private String name;

    private String code;

    private String parentId;

    private String description;

    private Boolean checked;

    public static OrganizationSummary from(Organization obj) {
        if (obj == null) {
            return null;
        }
        OrganizationSummary result = new OrganizationSummary();
        BeanUtils.copyProperties(obj, result);
        return result;
    }
}
