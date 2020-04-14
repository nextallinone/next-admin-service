package studio.clouthin.next.miniadmin.framework.auth.supports.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.BindingOrgsRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.BindingRolesRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.SystemUserMergeRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.UserQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.OrganizationSummary;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.RoleSummary;
import studio.clouthin.next.miniadmin.framework.auth.models.Organization;
import studio.clouthin.next.miniadmin.framework.auth.models.Role;
import studio.clouthin.next.miniadmin.framework.auth.models.User;
import studio.clouthin.next.miniadmin.framework.auth.services.OrganizationService;
import studio.clouthin.next.miniadmin.framework.auth.services.RoleService;
import studio.clouthin.next.miniadmin.framework.auth.services.SystemUserService;
import studio.clouthin.next.miniadmin.framework.auth.supports.SysUserSupport;
import studio.clouthin.next.shared.utils.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Transactional
public class SysUserSupportImpl implements SysUserSupport {


    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public Page<User> listSysUsers(UserQueryParameter queryRequest, Pageable pageable) {
        Preconditions.checkState(queryRequest != null, "参数不能为空!");
        return systemUserService.findAll(queryRequest, pageable);
    }

    @Override
    public User getUserDetail(String id) {
        Preconditions.checkState(!StringUtils.isEmpty(id), "参数不能为空!");
        return systemUserService.findById(id);
    }

    @Override
    public User createSysUser(SystemUserMergeRequest user) {
//        Preconditions.checkArgument(systemUserService.findByUsername(user.getUsername()) == null,
//                String.format("用户名[%s]已存在.",
//                        user.getUsername()));

        User sysUser = new User();
//        sysUser.setId(UUID.fastUUID().toString());
        sysUser.setEnabled(true);
        sysUser.setEmail(user.getEmail());
        sysUser.setUsername(user.getUsername());
        sysUser.setNickName(user.getNickName());
        sysUser.setPassword(user.getPassword());
        sysUser.setMobile(user.getMobile());
        Role userRole = roleService.findByName(Role.ROLE_USER);
        sysUser.setRoles(Sets.newHashSet(userRole));
        sysUser.setCreatedAt(new Date());
        return systemUserService.createUser(sysUser);
    }

    @Override
    public User updateSysUser(String userId, User user) {
        User systemUser = systemUserService.findById(userId);
        Preconditions.checkArgument(systemUser != null,
                String.format("用户id[%s]不存在.", userId));
        systemUser.setNickName(user.getNickName());
        systemUser.setEmail(user.getEmail());
        systemUser.setMobile(user.getMobile());
        return systemUserService.updateUser(systemUser);
    }

    @Override
    public User changePassword(String username, String newPassword) {
        return systemUserService.changePassword(username,
                null,
                newPassword,
                false);
    }

    @Override
    public User changePassword(String username, String oldPassword, String newPassword) {
        return systemUserService.changePassword(username,
                oldPassword,
                newPassword,
                true);
    }

    @Override
    public User enableUser(String id) {
        Preconditions.checkState(!StringUtils.isEmpty(id), "参数id不能为空!");
        return systemUserService.enable(id);
    }

    @Override
    public User disableUser(String id) {
        Preconditions.checkState(!StringUtils.isEmpty(id), "参数id不能为空!");
        return systemUserService.disable(id);
    }

    @Override
    public void deleteSysUser(String id) {
        Preconditions.checkState(!StringUtils.isEmpty(id), "参数id不能为空!");
        User systemUser = systemUserService.findById(id);
        Preconditions.checkArgument(systemUser != null,
                String.format("用户id[%s]不存在.", id));
        systemUserService.deleteUser(systemUser);
    }

    @Override
    public void bindOrgs(String id, BindingOrgsRequest bindingOrgsRequest) {
        User user = systemUserService.findById(id);
        List<Organization> bindOrgs = organizationService.findByIds(bindingOrgsRequest.getOrgIds());

        // 解绑
        List<Organization> unbindOrgs = user.getOrganizations().stream()
                .filter(organization -> !bindOrgs.contains(organization))
                .map(organization -> {
                    organization.getUsers().remove(user);
                    return organization;
                }).collect(Collectors.toList());
        organizationService.saveAll(unbindOrgs);

        // 绑定
        bindOrgs.forEach(org -> {
            org.getUsers().remove(user);
            org.getUsers().add(user);
        });
        organizationService.saveAll(bindOrgs);
    }

    @Override
    public void bindRoles(String id, BindingRolesRequest bindingRolesRequest) {
        User user = systemUserService.findById(id);
        List<Role> bindRoles = roleService.findByIds(bindingRolesRequest.getRoleIds());
        user.getRoles().clear();
        user.getRoles().addAll(bindRoles);
        systemUserService.save(user);
    }

    @Override
    public List<RoleSummary> getRolesByUser4Bind(String userId) {
        User user = systemUserService.findById(userId);
        return roleService.findAll()
                .stream()
                .map(role -> {
                    RoleSummary summary = RoleSummary.from(role);
                    if (user.getRoles().contains(role)) {
                        summary.setChecked(true);
                    }
                    return summary;
                }).collect(Collectors.toList());
    }

    @Override
    public List<OrganizationSummary> getOrgsByUser4Bind(String userId) {
        User user = systemUserService.findById(userId);
        return organizationService.findAll()
                .stream()
                .map(organization -> {
                    OrganizationSummary organizationSummary = OrganizationSummary.from(organization);
                    if (user.getOrganizations().contains(organization)) {
                        organizationSummary.setChecked(true);
                    }
                    return organizationSummary;
                }).collect(Collectors.toList());
    }

}
