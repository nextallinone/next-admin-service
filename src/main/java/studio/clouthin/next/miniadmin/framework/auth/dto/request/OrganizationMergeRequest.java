package studio.clouthin.next.miniadmin.framework.auth.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrganizationMergeRequest {

    private String id;

    @NotEmpty
    private String parentId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String code;

    private String description;
}
