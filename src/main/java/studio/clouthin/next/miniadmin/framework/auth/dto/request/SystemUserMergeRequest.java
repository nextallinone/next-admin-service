package studio.clouthin.next.miniadmin.framework.auth.dto.request;

import lombok.Data;

@Data
public class SystemUserMergeRequest {

    private String username;

    private String nickName;

    private String mobile;

    private String email;

    private String password;

}
