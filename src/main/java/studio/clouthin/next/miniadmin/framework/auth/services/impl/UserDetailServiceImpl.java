package studio.clouthin.next.miniadmin.framework.auth.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import studio.clouthin.next.miniadmin.framework.auth.models.User;
import studio.clouthin.next.miniadmin.framework.auth.repositories.UserRepository;
import studio.clouthin.next.miniadmin.framework.auth.services.RoleService;
import studio.clouthin.next.shared.exceptions.BadRequestException;
import studio.clouthink.next.ssointerceptor.auth.dtos.vo.JwtUser;

@Service("userDetailsService")
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    private RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new BadRequestException("账号不存在");
        } else {
            if (!user.getEnabled()) {
                throw new BadRequestException("账号未激活");
            }
            return createJwtUser(user);
        }
    }

    private UserDetails createJwtUser(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getNickName(),
                user.getSex(),
                user.getPassword(),
                user.getEmail(),
                user.getMobile(),
                roleService.mapToGrantedAuthorities(user),
                user.getEnabled(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
