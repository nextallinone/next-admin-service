package studio.clouthin.next.miniadmin.framework.auth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import studio.clouthin.next.shared.models.AbstractModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends AbstractModel {


    @NotBlank
    @Column(unique = true, length = 44)
    private String username;

    /**
     * 用户昵称
     */
    @NotBlank
    @Column(length = 44)
    private String nickName;

    /**
     * 性别
     */
    @Column(length = 10)
    private String sex;

    @NotBlank
    @Email
    @Column(length = 88)
    private String email;

    @NotBlank
    @Column(length = 20)
    private String mobile;

    @NotNull
    private Boolean enabled;

    @Column(name = "passwd")
    private String password;


    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns =
            {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;


    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private List<Organization> organizations;

}