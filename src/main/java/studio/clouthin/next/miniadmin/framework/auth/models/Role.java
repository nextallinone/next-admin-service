package studio.clouthin.next.miniadmin.framework.auth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import studio.clouthin.next.shared.models.AbstractModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

/**
 * 角色
 */
@Entity
@Table(name = "security_role")
@Getter
@Setter
public class Role extends AbstractModel {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String ROLE_USER = "ROLE_USER";


    @Column(nullable = false, length = 44)
    @NotBlank
    private String name;


    @Column
    private String remark;

    /**
     * 权限
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "roles_resources_relations", joinColumns = {
            @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "resourceId", referencedColumnName = "id", nullable = false)})
    private List<Resource> resources = Lists.newArrayList();


    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


}
