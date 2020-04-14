package studio.clouthin.next.miniadmin.framework.menu.services;

import studio.clouthin.next.miniadmin.framework.menu.models.Menu;

import java.util.List;
import java.util.Set;

public interface MenuService {

    List<Menu> getAllMenus();

    void createRoleMenus(Set<String> roleIds, final long currentTimeMillis);

    List<String> getGrantMenusIdsByUserId(String userId);

    List<String> getGrantMenusIdsByRoleId(String roleId);
}
