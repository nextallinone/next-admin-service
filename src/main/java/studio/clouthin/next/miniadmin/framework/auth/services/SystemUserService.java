package studio.clouthin.next.miniadmin.framework.auth.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studio.clouthin.next.miniadmin.framework.auth.dto.request.UserQueryParameter;
import studio.clouthin.next.miniadmin.framework.auth.models.User;

import java.util.List;
import java.util.Set;

/**
 *
 */
public interface SystemUserService {

    Page<User> findAll(UserQueryParameter queryRequest, Pageable pageable);

    Page<User> findByRoleId(String roleId, UserQueryParameter queryRequest, Pageable pageable);

    Page<User> findByOrgId(String orgId, UserQueryParameter queryRequest, Pageable pageable);

    User findById(String userId);

    User findByUsername(String username);

    List<User> findAllByIdIn(Set<String> userIds);

//    User findByUsername(String username);

//    Page<User> findByUsernameLike(UserQueryParameter queryParameter);

//    User getAccountByUsername(String username);

    User createUser(User sysUser);

    User updateUser(User sysUser);

    void deleteUser(User sysUser);

    User enable(String userId);

    User disable(String userId);

    User lock(String userId);

    User unlock(String userId);

    User changePassword(String username,
                        String oldPassword,
                        String newPassword,
                        boolean needVerify);

    User changePassword(User User,
                        String oldPassword,
                        String newPassword,
                        boolean needVerify);

    List<User> saveAll(Iterable<User> users);

    User save(User user);


}
