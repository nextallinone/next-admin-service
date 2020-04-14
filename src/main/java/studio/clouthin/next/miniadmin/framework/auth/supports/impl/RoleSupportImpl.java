package studio.clouthin.next.miniadmin.framework.auth.supports.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.BindingUsersRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.RoleMergeRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.RoleQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.UserQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.PermissionSummary;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.RoleSummary;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.UserSummary;
import studio.clouthin.next.miniadmin.framework.auth.models.Resource;
import studio.clouthin.next.miniadmin.framework.auth.models.Role;
import studio.clouthin.next.miniadmin.framework.auth.models.User;
import studio.clouthin.next.miniadmin.framework.auth.repositories.ResourceRepository;
import studio.clouthin.next.miniadmin.framework.auth.services.RoleService;
import studio.clouthin.next.miniadmin.framework.auth.services.SystemUserService;
import studio.clouthin.next.miniadmin.framework.auth.supports.RoleSupport;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleSupportImpl implements RoleSupport {

    @Autowired
    private RoleService roleService;

    @Autowired
    private SystemUserService userService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Page<RoleSummary> page(RoleQueryParameter parameter, Pageable pageable) {
        return roleService
                .page(parameter, pageable)
                .map(RoleSummary::from);
    }

    @Override
    public RoleSummary create(RoleMergeRequest roleMergeRequest) {
        return RoleSummary.from(roleService.create(roleMergeRequest));
    }

    @Override
    public RoleSummary update(String roleId, RoleMergeRequest roleMergeRequest) {
        return RoleSummary.from(roleService.update(roleId, roleMergeRequest));
    }

    @Override
    public Page<UserSummary> findUsersByRoleId(String roleId,
                                               UserQueryParameter queryRequest,
                                               Pageable pageable) {
        return userService
                .findByRoleId(roleId, queryRequest, pageable)
                .map(UserSummary::from);
    }

    @Override
    public void bindUsers(String roleId, BindingUsersRequest request) {
        Role role = roleService.findById(roleId);
        List<User> bindUsers = userService.findAllByIdIn(request.getUserIds());
        bindUsers.forEach(user ->
                user.getRoles().add(role)
        );
        userService.saveAll(bindUsers);
    }

    @Override
    public void unbindUsers(String roleId, BindingUsersRequest request) {
        Role role = roleService.findById(roleId);
        List<User> unbindUsers = userService.findAllByIdIn(request.getUserIds());
        unbindUsers.forEach(user ->
                user.getRoles().remove(role)
        );
        userService.saveAll(unbindUsers);
    }

    @Override
    public List<PermissionSummary> getPermissions(String roleId) {
        List<String> existsPermissions = roleService.
                findById(roleId)
                .getResources()
                .stream()
                .map(Resource::getId)
                .collect(Collectors.toList());

        return resourceRepository.findAll()
                .stream()
                .map(resource -> {
                    PermissionSummary summary = PermissionSummary.from(resource);
                    if (existsPermissions.contains(resource.getId())) {
                        summary.setChecked(true);
                    }
                    return summary;
                })
                .collect(Collectors.toList());
    }


}
