package studio.clouthin.next.miniadmin.framework.menu.dto.response;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import studio.clouthin.next.miniadmin.framework.menu.models.Menu;

@Data
public class MenuSummary {

    private String id;

    private String icon;

    private String name;

    private String mpid;

    private String route;

    //判断是否选中
    private Boolean checked = Boolean.FALSE;

    private Boolean group;

    private Boolean open;

    public static MenuSummary from(Menu obj) {
        if (obj == null) {
            return null;
        }
        MenuSummary result = new MenuSummary();
        BeanUtils.copyProperties(obj, result);
        return result;
    }

}
