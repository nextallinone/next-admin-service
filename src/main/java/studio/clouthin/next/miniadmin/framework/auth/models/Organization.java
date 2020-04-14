package studio.clouthin.next.miniadmin.framework.auth.models;

import lombok.Getter;
import lombok.Setter;
import studio.clouthin.next.shared.models.AbstractModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "security_organization")
public class Organization extends AbstractModel {


    /**
     * 组织机构名称.
     */
    @Column(length = 128, nullable = false)
    private String name;

    /**
     * 组织机构代码.
     */
    @Column(length = 64, nullable = false)
    private String code;

    /**
     * 父级ID.
     */
    @Column(length = 44)
    private String parentId;

    /**
     * 用户
     */
    @ManyToMany
    @JoinTable(name = "users_orgs", joinColumns =
            {@JoinColumn(name = "org_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users;

    /**
     * 说明.
     */
    @Column(length = 256)
    private String description;


}
