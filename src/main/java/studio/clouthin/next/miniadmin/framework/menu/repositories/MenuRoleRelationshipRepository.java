package studio.clouthin.next.miniadmin.framework.menu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.clouthin.next.miniadmin.framework.menu.models.MenuRoleRelationship;

import java.util.List;
import java.util.Set;

public interface MenuRoleRelationshipRepository extends JpaRepository<MenuRoleRelationship, String> {

    List<MenuRoleRelationship> findListByMenuId(String menuId);

    List<MenuRoleRelationship> findListByRoleId(String roleId);

    List<MenuRoleRelationship> findListByRoleIdIn(Set<String> roleIds);

}
