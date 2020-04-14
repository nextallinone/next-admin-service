package studio.clouthin.next.miniadmin.framework.auth.dto.response;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import studio.clouthin.next.miniadmin.framework.auth.models.User;

import java.util.Date;

@Data
public class UserSummary {

    private String id;

    private String username;

    private String nickName;

    private String email;

    private String mobile;

    private Boolean enabled;

    private Date createdAt;

    public static UserSummary from(User obj) {
        if (obj == null) {
            return null;
        }
        UserSummary result = new UserSummary();
        BeanUtils.copyProperties(obj, result);
        return result;
    }
}
