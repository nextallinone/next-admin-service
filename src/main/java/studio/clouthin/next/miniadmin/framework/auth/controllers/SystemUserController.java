package studio.clouthin.next.miniadmin.framework.auth.controllers;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.*;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.OrganizationSummary;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.RoleSummary;
import studio.clouthin.next.miniadmin.framework.auth.dto.response.UserSummary;
import studio.clouthin.next.miniadmin.framework.auth.models.User;
import studio.clouthin.next.miniadmin.framework.auth.supports.SysUserSupport;
import studio.clouthin.next.shared.utils.SecurityUtils;

import java.util.List;

@Api(tags = "人员管理")
@RestController
@RequestMapping("/sysusers")
public class SystemUserController {

    @Autowired
    private SysUserSupport sysUserSupport;

    @Value("${rsa.private_key}")
    private String privateKey;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<UserSummary> listSysUsers(UserQueryParameter queryRequest, Pageable pageable) {
        return sysUserSupport.listSysUsers(queryRequest, pageable).map(UserSummary::from);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserDetail(@PathVariable String id) {
        return sysUserSupport.getUserDetail(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteSysUser(@PathVariable String id) {
        sysUserSupport.deleteSysUser(id);
        return true;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public User createSysUser(@RequestBody SystemUserMergeRequest sysUser) {
        return sysUserSupport.createSysUser(sysUser);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User updateSysUser(@PathVariable String id,
                              @RequestBody User user) {
        return sysUserSupport.updateSysUser(id, user);
    }

//    @RequestMapping(value = "/api/sysusers/{id}/password", method = RequestMethod.POST)
//    public User changePassword(@PathVariable String id,
//                                     @RequestBody ChangePasswordRequest request) {
//        return sysUserSupport.changePassword(id, request.getNewPassword());
//    }

    @RequestMapping(value = "/{id}/enable", method = RequestMethod.POST)
    public User enableUser(@PathVariable String id) {
        return sysUserSupport.enableUser(id);
    }

    @RequestMapping(value = "/{id}/disable", method = RequestMethod.POST)
    public User disableUser(@PathVariable String id) {
        return sysUserSupport.disableUser(id);
    }

    @ApiOperation("绑定组织机构")
    @PostMapping("/bindOrgsByUser/{userId}")
    public void bindOrgs(@PathVariable String userId,
                         @RequestBody BindingOrgsRequest bindingOrgsRequest) {
        sysUserSupport.bindOrgs(userId, bindingOrgsRequest);
    }

    @ApiOperation("用户绑定的角色")
    @GetMapping("/getRolesByUser/{userId}")
    public List<RoleSummary> getRolesByUser(@PathVariable String userId) {
        return sysUserSupport.getRolesByUser4Bind(userId);
    }

    @ApiOperation("绑定角色")
    @PostMapping("/bindRolesByUser/{userId}")
    public void bindRoles(@PathVariable String userId,
                          @RequestBody BindingRolesRequest bindingRolesRequest) {
        sysUserSupport.bindRoles(userId, bindingRolesRequest);
    }

    @ApiOperation("用户绑定的组织")
    @GetMapping("/getOrgsByUser/{userId}")
    public List<OrganizationSummary> getOrgsByUser(@PathVariable String userId) {
        return sysUserSupport.getOrgsByUser4Bind(userId);
    }


    @ApiOperation("修改密码")
    @PostMapping(value = "/changeMyPassword")
    public void changeMyPassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String oldPass = new String(rsa.decrypt(changePasswordRequest.getOldPass(), KeyType.PrivateKey));
        String newPass = new String(rsa.decrypt(changePasswordRequest.getNewPass(), KeyType.PrivateKey));
        sysUserSupport.changePassword(SecurityUtils.getUsername(), oldPass, newPass);
    }

    @ApiOperation("重置密码")
    @PostMapping(value = "/{username}/updatePassword")
    public void updatePass(@PathVariable String username,
                           @RequestBody ChangePasswordRequest changePasswordRequest) {
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String newPass = new String(rsa.decrypt(changePasswordRequest.getNewPass(), KeyType.PrivateKey));
        sysUserSupport.changePassword(username, newPass);
    }
}
