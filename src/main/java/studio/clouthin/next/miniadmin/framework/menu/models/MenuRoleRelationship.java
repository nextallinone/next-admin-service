package studio.clouthin.next.miniadmin.framework.menu.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单、角色绑定关系
 */
@Entity
@Table(name = "t_menu_role_relationship")
@Getter
@Setter
public class MenuRoleRelationship {

    @Id
    @Column(length = 40)
    protected String id;

    @Column(length = 40)
    @NotEmpty
    private String menuId;

    @Column(length = 40)
    @NotEmpty
    private String roleId;

}
