package studio.clouthin.next.miniadmin.framework.menu.supports.impl;

import cn.hutool.core.lang.UUID;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.clouthin.next.miniadmin.framework.menu.dto.request.BindingMenuRequest;
import studio.clouthin.next.miniadmin.framework.menu.dto.response.MenuSummary;
import studio.clouthin.next.miniadmin.framework.menu.models.MenuRoleRelationship;
import studio.clouthin.next.miniadmin.framework.menu.repositories.MenuRoleRelationshipRepository;
import studio.clouthin.next.miniadmin.framework.menu.services.MenuService;
import studio.clouthin.next.miniadmin.framework.menu.supports.MenuRestSupport;
import studio.clouthin.next.shared.utils.SecurityUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vanish
 */
@Service
@Transactional
@SuppressWarnings("unchecked")
public class MenuRestSupportImpl implements MenuRestSupport {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRoleRelationshipRepository menuRoleRelationshipRepository;


    @Override
//    @Cacheable
    public List<MenuSummary> getUserGrantedMenus(String userId) {
        if (SecurityUtils.isAdmin()) {
            return getAllMenus().stream().collect(Collectors.toList());
        } else {
            List<String> existsMenuIds = menuService.getGrantMenusIdsByUserId(userId);
            return menuService
                    .getAllMenus()
                    .stream()
                    .filter(menu -> existsMenuIds.contains(menu.getId()))
                    .map(MenuSummary::from)
                    .collect(Collectors.toList());
        }

    }

    @Override
    public List<MenuSummary> getAllMenus() {
        return menuService.getAllMenus().stream().map(MenuSummary::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuSummary> getByRole(String roleId) {
        List<String> existsMenuIds = menuService.getGrantMenusIdsByRoleId(roleId);
        return menuService
                .getAllMenus()
                .stream()
                .map(menu -> {
                    MenuSummary summary = MenuSummary.from(menu);
                    if (existsMenuIds.contains(menu.getId())) {
                        summary.setChecked(true);
                    }
                    return summary;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void bindByRole(String roleId, BindingMenuRequest request) {
        List<String> menuIds = request.getMenuIds();
        List<MenuRoleRelationship> exists = menuRoleRelationshipRepository.findListByRoleId(roleId);
        menuRoleRelationshipRepository.deleteAll(exists);
        List<MenuRoleRelationship> targets = Lists.newArrayList();
        menuIds.forEach(menuId -> {
            MenuRoleRelationship menuRoleRelationship = new MenuRoleRelationship();
            menuRoleRelationship.setMenuId(menuId);
            menuRoleRelationship.setRoleId(roleId);
            menuRoleRelationship.setId(UUID.fastUUID().toString());
            targets.add(menuRoleRelationship);
        });
        menuRoleRelationshipRepository.saveAll(targets);
    }


}
