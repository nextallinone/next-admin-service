package studio.clouthin.next.miniadmin.framework.dict.dto.response;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import studio.clouthin.next.miniadmin.framework.dict.models.DictItem;

@Data
public class DictItemSummary {

    private String id;

    private String label;

    private String value;

    private String sort;

    public static DictItemSummary from(DictItem obj) {
        if (obj == null) {
            return null;
        }
        DictItemSummary result = new DictItemSummary();
        BeanUtils.copyProperties(obj, result);
        return result;
    }

}
