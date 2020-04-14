package studio.clouthin.next.miniadmin.framework.auth.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.RoleMergeRequest;
import studio.clouthin.next.miniadmin.framework.auth.models.Role;
import studio.clouthin.next.miniadmin.framework.auth.models.User;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.RoleQueryParameter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 */
public interface RoleService {


    /**
     * 获取用户权限信息
     *
     * @param user 用户信息
     * @return 权限信息
     */
    Collection<GrantedAuthority> mapToGrantedAuthorities(User user);


    /**
     * 获取用户绑定角色
     *
     * @param userId
     * @return
     */
    Set<Role> getRoleByUserId(String userId);


    Page<Role> page(RoleQueryParameter parameter, Pageable pageable);

    Page<Role> pageByUser(String userId, RoleQueryParameter parameter, Pageable pageable);


    Role create(RoleMergeRequest roleMergeRequest);

    Role update(String roleId, RoleMergeRequest roleMergeRequest);

    Role findByName(String name);

    Role findById(String id);

    List<Role> findByIds(Set<String> ids);

    Role save(Role role);

    List<Role> findAll();
}
