package studio.clouthin.next.miniadmin.framework.auth.services.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.RoleMergeRequest;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.RoleQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.models.Role;
import studio.clouthin.next.miniadmin.framework.auth.models.User;
import studio.clouthin.next.miniadmin.framework.auth.repositories.RoleRepository;
import studio.clouthin.next.miniadmin.framework.auth.services.RoleService;
import studio.clouthin.next.shared.utils.JPAQueryHelper;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author vanish
 */
@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Collection<GrantedAuthority> mapToGrantedAuthorities(User user) {
//        Set<Role> roles = roleRepository.findByUsers_Id(user.getId());
//        Set<String> permissions = roles.stream()
//                .filter(role -> !role.getResources().isEmpty())
//                .flatMap(role -> role.getResources().stream())
//                .map(resource -> resource.getUrl())
//                .collect(Collectors.toSet());
//        permissions.addAll(
//                roles.stream().flatMap(role -> role.getMenus().stream())
//                        .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
//                        .map(Menu::getPermission).collect(Collectors.toSet())
//        );
        Set<String> permissions = Sets.newConcurrentHashSet();

        //TODO 0 is admin
        permissions.add(Role.ROLE_USER);
//        if (user.getId().equals("0")) {
//            permissions.add(Role.ROLE_ADMIN);
//        }
        permissions.addAll(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Role> getRoleByUserId(String userId) {
        return roleRepository.findByUsers_Id(userId);
    }

    @Override
    public Page<Role> page(RoleQueryParameter parameter, Pageable pageable) {
        return roleRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                JPAQueryHelper.getPredicate(
                        root,
                        parameter,
                        criteriaBuilder), pageable);
    }

    @Override
    public Page<Role> pageByUser(String userId, RoleQueryParameter parameter, Pageable pageable) {
        return roleRepository
                .findAll((root, criteriaQuery, criteriaBuilder) -> {
                            List<Predicate> predicates = Lists.newArrayList();
                            SetJoin<Role, User> userSetJoin = root.joinSet("users");
                            predicates.add(criteriaBuilder.equal(userSetJoin.get("id"), userId));

//                            return JPAQueryHelper.getPredicate(
//                                    root,
//                                    queryRequest,
//                                    criteriaBuilder);
                            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
                        }
                        , pageable);
    }

    @Override
    public Role create(RoleMergeRequest roleMergeRequest) {
        Role role = new Role();
        Preconditions.checkState(roleRepository.findByName(roleMergeRequest.getName()) == null, "角色名称重复");
//        role.setId(UUID.fastUUID().toString());
        role.setName(roleMergeRequest.getName());
        role.setRemark(roleMergeRequest.getRemark());
        return roleRepository.save(role);
    }

    @Override
    public Role update(String roleId, RoleMergeRequest roleMergeRequest) {
        Role role = roleRepository.findById(roleMergeRequest.getId()).get();
        role.setRemark(roleMergeRequest.getRemark());
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> findByIds(Set<String> ids) {
        return roleRepository.findAllById(ids);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
