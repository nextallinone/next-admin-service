package studio.clouthin.next.miniadmin.framework.auth.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RoleMergeRequest {

    private String id;

    @NotEmpty(message = "角色名称不能为空")
    private String name;

    @NotEmpty(message = "角色描述不能为空")
    private String remark;
}
