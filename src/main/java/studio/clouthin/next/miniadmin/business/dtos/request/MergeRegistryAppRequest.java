package studio.clouthin.next.miniadmin.business.dtos.request;

import lombok.Data;

@Data
public class MergeRegistryAppRequest {

    private String name;

    private String remark;

    private String redirectUrl;

    private String authUrl;

}
