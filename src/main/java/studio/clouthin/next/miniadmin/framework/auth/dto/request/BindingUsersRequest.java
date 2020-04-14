package studio.clouthin.next.miniadmin.framework.auth.dto.request;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BindingUsersRequest {

    Set<String> userIds = Sets.newHashSet();

}
