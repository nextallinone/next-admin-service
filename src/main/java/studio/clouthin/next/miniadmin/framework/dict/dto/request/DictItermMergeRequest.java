package studio.clouthin.next.miniadmin.framework.dict.dto.request;

import lombok.Data;

@Data
public class DictItermMergeRequest {

    private String dictId;

    private String label;

    private String value;

    private String sort;
}
