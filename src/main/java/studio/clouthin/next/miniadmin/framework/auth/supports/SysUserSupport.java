package studio.clouthin.next.miniadmin.framework.auth.supports;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.BindingOrgsRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.BindingRolesRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.UserQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.RoleSummary;
import studio.clouthin.next.miniadmin.framework.auth.models.User;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.SystemUserMergeRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.OrganizationSummary;

import java.util.List;

/**
 *
 */
public interface SysUserSupport {

    Page<User> listSysUsers(UserQueryParameter queryRequest, Pageable pageable);

    User getUserDetail(String id);

    User createSysUser(SystemUserMergeRequest user);

    User updateSysUser(String userId, User user);

    User changePassword(String username, String newPassword);

    User changePassword(String username, String oldPassword, String newPassword);

    User enableUser(String id);

    User disableUser(String id);

    void deleteSysUser(String id);

    void bindOrgs(String id, BindingOrgsRequest bindingOrgsRequest);

    void bindRoles(String id, BindingRolesRequest bindingRolesRequest);


    List<RoleSummary> getRolesByUser4Bind(String userId);

    List<OrganizationSummary> getOrgsByUser4Bind(String userId);

}
