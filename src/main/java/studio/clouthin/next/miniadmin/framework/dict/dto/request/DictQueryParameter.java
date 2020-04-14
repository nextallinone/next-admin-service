package studio.clouthin.next.miniadmin.framework.dict.dto.request;

import lombok.Data;
import studio.clouthin.next.shared.annotations.FQuery;
import studio.clouthin.next.shared.dto.request.AbstractPageQueryParameter;

@Data
public class DictQueryParameter extends AbstractPageQueryParameter {

    @FQuery(blurry = "name,remark")
    private String searchKeywords;

}
