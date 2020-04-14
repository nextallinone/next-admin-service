package studio.clouthin.next.miniadmin.framework.dict.dto.response;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import studio.clouthin.next.miniadmin.framework.dict.models.Dict;

@Data
public class DictSummary {

    private String id;

    private String name;

    private String remark;

    public static DictSummary from(Dict obj) {
        if (obj == null) {
            return null;
        }
        DictSummary result = new DictSummary();
        BeanUtils.copyProperties(obj, result);
        return result;
    }

}
