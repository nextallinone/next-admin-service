package studio.clouthin.next.miniadmin.framework.auth.supports;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.BindingUsersRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.RoleMergeRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.RoleSummary;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.UserSummary;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.RoleQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.UserQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.PermissionSummary;

import java.util.List;

public interface RoleSupport {

    Page<RoleSummary> page(RoleQueryParameter parameter, Pageable pageable);

    RoleSummary create(RoleMergeRequest roleMergeRequest);

    RoleSummary update(String roleId, RoleMergeRequest roleMergeRequest);

    Page<UserSummary> findUsersByRoleId(String roleId,
                                        UserQueryParameter queryRequest,
                                        Pageable pageable);

    void bindUsers(String roleId, BindingUsersRequest request);

    void unbindUsers(String roleId, BindingUsersRequest request);

    List<PermissionSummary> getPermissions(String roleId);

}
