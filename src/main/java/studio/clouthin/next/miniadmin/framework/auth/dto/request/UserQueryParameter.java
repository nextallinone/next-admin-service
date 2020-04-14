package studio.clouthin.next.miniadmin.framework.auth.dto.request;

import lombok.Data;
import studio.clouthin.next.shared.annotations.FQuery;

@Data
public class UserQueryParameter {

    @FQuery(blurry = "username,mobile,nickName")
    private String keyword;

    @FQuery(type = FQuery.Type.EQUAL)
    private Boolean enabled;

}
