package studio.clouthin.next.miniadmin.framework.auth.dto.request;

import lombok.Data;
import studio.clouthin.next.shared.annotations.FQuery;

@Data
public class RoleQueryParameter {

    @FQuery(blurry = "name,remark")
    private String searchKeywords;
}
