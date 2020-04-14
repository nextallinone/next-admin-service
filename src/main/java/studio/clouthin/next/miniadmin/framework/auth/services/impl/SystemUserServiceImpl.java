package studio.clouthin.next.miniadmin.framework.auth.services.impl;

import cn.hutool.core.lang.UUID;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.UserQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.exceptions.SystemUserException;
import studio.clouthin.next.miniadmin.framework.auth.models.Organization;
import studio.clouthin.next.miniadmin.framework.auth.models.Role;
import studio.clouthin.next.miniadmin.framework.auth.models.User;
import studio.clouthin.next.miniadmin.framework.auth.repositories.UserRepository;
import studio.clouthin.next.miniadmin.framework.auth.services.SystemUserService;
import studio.clouthin.next.shared.utils.JPAQueryHelper;
import studio.clouthin.next.shared.utils.StringUtils;

import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Service
@Transactional
@Slf4j
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;


//	private SystemPasswordMatcher systemPasswordMatcher = new DefaultSystemPasswordMatcher();

    @Autowired
    private UserRepository systemUserRepository;

    @Override
    public Page<User> findAll(UserQueryParameter queryRequest, Pageable pageable) {
        return systemUserRepository
                .findAll((root, criteriaQuery, criteriaBuilder) ->
                        JPAQueryHelper.getPredicate(
                                root,
                                queryRequest,
                                criteriaBuilder), pageable);
    }

    @Override
    public Page<User> findByRoleId(String roleId, UserQueryParameter queryRequest, Pageable pageable) {
        return systemUserRepository
                .findAll((root, criteriaQuery, criteriaBuilder) -> {
                            List<Predicate> predicates = Lists.newArrayList();
                            SetJoin<User, Role> roleSetJoin = root.joinSet("roles");
                            predicates.add(criteriaBuilder.equal(roleSetJoin.get("id"), roleId));

//                            return JPAQueryHelper.getPredicate(
//                                    root,
//                                    queryRequest,
//                                    criteriaBuilder);
                            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
                        }
                        , pageable);
    }

    @Override
    public Page<User> findByOrgId(String orgId, UserQueryParameter queryRequest, Pageable pageable) {
        return systemUserRepository
                .findAll((root, criteriaQuery, criteriaBuilder) -> {
                            List<Predicate> predicates = Lists.newArrayList();
                            ListJoin<User, Organization> organizationSetJoin = root.joinList("organizations");
                            predicates.add(criteriaBuilder.equal(organizationSetJoin.get("id"), orgId));

//                            return JPAQueryHelper.getPredicate(
//                                    root,
//                                    queryRequest,
//                                    criteriaBuilder);
                            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
                        }
                        , pageable);
    }

    @Override
    public User findById(String userId) {
        return systemUserRepository.findById(userId).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return systemUserRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllByIdIn(Set<String> userIds) {
        return systemUserRepository.findAllById(userIds);
    }


    @Override
    public User createUser(User user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new SystemUserException("用户名不能为空");
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            throw new SystemUserException("邮箱不能为空");
        }
        if (StringUtils.isEmpty(user.getMobile())) {
            throw new SystemUserException("手机号不能为空");
        }

        User existedUser = systemUserRepository.findByUsername(user.getUsername());
        if (existedUser != null) {
            throw new SystemUserException(String.format("重复的用户名'%s'!",
                    user.getUsername()));
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            log.warn(String.format("The password not provided for user[username=%s], we will generate a random one ",
                    user.getUsername()));
            user.setPassword(UUID.randomUUID().toString().replace("-", ""));
        }

        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        user.setCreatedAt(new Date());

        return systemUserRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (StringUtils.isEmpty(user.getId())) {
            throw new SystemUserException("用户id不能为空");
        }
        User existedUser = findById(user.getId());
        if (existedUser == null) {
            throw new SystemUserException("不存在的用户!");
        }

        // TODO now only update the three attributes
        existedUser.setEmail(user.getEmail());
        existedUser.setMobile(user.getMobile());
        existedUser.setNickName(user.getNickName());

        return systemUserRepository.save(existedUser);
    }

    @Override
    public void deleteUser(User sysUser) {
        if ("administrator".equalsIgnoreCase(sysUser.getUsername()) || "admin".equalsIgnoreCase(sysUser.getUsername())) {
            throw new SystemUserException("不能删除系统管理员!");
        }
        systemUserRepository.delete(sysUser);
    }

    @Override
    public User enable(String userId) {
        User existedUser = findById(userId);
        if (existedUser == null) {
            throw new SystemUserException("不存在的用户!");
        }

        existedUser.setEnabled(true);
        return systemUserRepository.save(existedUser);
    }

    @Override
    public User disable(String userId) {
        User existedUser = findById(userId);
        if (existedUser == null) {
            throw new SystemUserException("不存在的用户!");
        }

        existedUser.setEnabled(false);
        return systemUserRepository.save(existedUser);
    }

    @Override
    public User lock(String userId) {
        User existedUser = findById(userId);
        if (existedUser == null) {
            throw new SystemUserException("不存在的用户!");
        }

//        existedUser.setLocked(true);
        return systemUserRepository.save(existedUser);
    }

    @Override
    public User unlock(String userId) {
        User existedUser = findById(userId);
        if (existedUser == null) {
            throw new SystemUserException("不存在的用户!");
        }

//        existedUser.setLocked(false);
        return systemUserRepository.save(existedUser);
    }

    @Override
    public User changePassword(String username,
                               String oldPassword,
                               String newPassword,
                               boolean needVerify) {
        User existedUser = findByUsername(username);
        if (existedUser == null) {
            throw new SystemUserException("不存在的用户!");
        }

        return changePassword(existedUser, oldPassword, newPassword, needVerify);
    }

    @Override
    public User changePassword(User systemUser,
                               String oldPassword,
                               String newPassword,
                               boolean needVerify) {
        if (needVerify) {
            Preconditions.checkState(passwordEncoder.matches(oldPassword, systemUser.getPassword()),
                    "原密码输入错误!");
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        systemUser.setPassword(encodedPassword);
        return systemUserRepository.save(systemUser);
    }

    @Override
    public List<User> saveAll(Iterable<User> users) {
        return systemUserRepository.saveAll(users);
    }

    @Override
    public User save(User user) {
        return systemUserRepository.save(user);
    }

}
