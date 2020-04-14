package studio.clouthin.next.miniadmin.framework.auth.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String oldPass;

    private String newPass;
}
