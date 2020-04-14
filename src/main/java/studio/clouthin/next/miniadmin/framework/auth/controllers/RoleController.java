package studio.clouthin.next.miniadmin.framework.auth.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.BindingUsersRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.RoleMergeRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.RoleQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.UserQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.PermissionSummary;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.RoleSummary;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.UserSummary;
import studio.clouthin.next.miniadmin.framework.auth.supports.RoleSupport;
import studio.clouthin.next.miniadmin.framework.menu.dto.request.BindingMenuRequest;
import studio.clouthin.next.miniadmin.framework.menu.dto.response.MenuSummary;
import studio.clouthin.next.miniadmin.framework.menu.exceptions.ResourceLoadException;
import studio.clouthin.next.miniadmin.framework.menu.supports.MenuRestSupport;
import studio.clouthin.next.shared.utils.StringUtils;

import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    @Autowired
    private MenuRestSupport menuRestSupport;

    @Autowired
    private RoleSupport roleSupport;

    @GetMapping(value = "")
    public Page<RoleSummary> page(RoleQueryParameter queryRequest, Pageable pageable) {
        return roleSupport.page(queryRequest, pageable);
    }

    @PostMapping("")
    public RoleSummary create(@RequestBody RoleMergeRequest roleMergeRequest) {
        return roleSupport.create(roleMergeRequest);
    }

    @PutMapping("/{id}")
    public RoleSummary update(@PathVariable String id, @RequestBody RoleMergeRequest roleMergeRequest) {
        return roleSupport.update(id, roleMergeRequest);
    }

    @ApiOperation("绑定角色菜单")
    @PostMapping("/bindMenusByRole/{roleId}")
    public void bindByRole(@PathVariable String roleId,
                           @RequestBody BindingMenuRequest bindingMenuRequest) {
        menuRestSupport.bindByRole(roleId, bindingMenuRequest);
    }


    @ApiOperation("角色已绑菜单")
    @GetMapping(value = "/getMenusByRole/{roleId}")
    public List<MenuSummary> getMenusByRole(@PathVariable String roleId) {
        if (StringUtils.isBlank(roleId)) {
            throw new ResourceLoadException("[roleId]不能为空");
        }
        return menuRestSupport.getByRole(roleId);
    }

    @ApiOperation("角色已绑资源")
    @GetMapping(value = "/getPermissionsByRole/{roleId}")
    public List<PermissionSummary> getPermissions(@PathVariable String roleId) {
        if (StringUtils.isBlank(roleId)) {
            throw new ResourceLoadException("[roleId]不能为空");
        }
        return roleSupport.getPermissions(roleId);
    }


    @ApiOperation("角色已绑用户")
    @GetMapping(value = "/getUsersByRole/{roleId}")
    public Page<UserSummary> getUsersByRole(@PathVariable String roleId,
                                            UserQueryParameter queryRequest,
                                            Pageable pageable) {
        if (StringUtils.isBlank(roleId)) {
            throw new ResourceLoadException("[roleId]不能为空");
        }
        return roleSupport.findUsersByRoleId(roleId, queryRequest, pageable);
    }


    @ApiOperation("绑定用户")
    @PostMapping("/bindUsersByRole/{roleId}")
    public void bindUsersByRole(@PathVariable String roleId,
                                @RequestBody BindingUsersRequest bindingUsersRequest) {
        roleSupport.bindUsers(roleId, bindingUsersRequest);
    }


    @ApiOperation("解绑用户")
    @PostMapping("/unBindUsersByRole/{roleId}")
    public void unBindUsersByRole(@PathVariable String roleId,
                                  @RequestBody BindingUsersRequest bindingUsersRequest) {
        roleSupport.unbindUsers(roleId, bindingUsersRequest);
    }
}
