package studio.clouthin.next.miniadmin.framework.menu.dto.request;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BindingMenuRequest {

    List<String> menuIds = Lists.newArrayList();

}
