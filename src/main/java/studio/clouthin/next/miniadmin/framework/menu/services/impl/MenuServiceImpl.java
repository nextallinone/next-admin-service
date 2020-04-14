package studio.clouthin.next.miniadmin.framework.menu.services.impl;

import cn.hutool.core.lang.UUID;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import studio.clouthin.next.miniadmin.framework.auth.models.Role;
import studio.clouthin.next.miniadmin.framework.auth.services.RoleService;
import studio.clouthin.next.miniadmin.framework.menu.models.Menu;
import studio.clouthin.next.miniadmin.framework.menu.models.MenuRoleRelationship;
import studio.clouthin.next.miniadmin.framework.menu.repositories.MenuRoleRelationshipRepository;
import studio.clouthin.next.miniadmin.framework.menu.services.MenuService;
import studio.clouthin.next.miniadmin.framework.menu.exceptions.ResourceLoadException;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService, InitializingBean {

    @Autowired
    private MenuRoleRelationshipRepository menuRoleRepository;

    @Autowired
    private RoleService roleService;

    ObjectMapper objectMapper = new ObjectMapper();

    private String RESOURCE_FILE = "menu.json";

    private List<Menu> menuList = Lists.newArrayList();

    private List<Menu> load(InputStream inputStream) {
        try {
            List<Menu> parsedData = objectMapper.readValue(inputStream,
                    new TypeReference<List<Menu>>() {
                    });
            return parsedData;
        } catch (Exception e) {
            throw new ResourceLoadException("加载资源出错", e);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(RESOURCE_FILE);
        menuList = load(Menu.class.getClassLoader().getResourceAsStream(RESOURCE_FILE));
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuList;
    }

    @Override
    @Transactional
    public void createRoleMenus(Set<String> roleIds, long currentTimeMillis) {
        final List<MenuRoleRelationship> baseRoleMenus = menuRoleRepository
                .findListByRoleIdIn(roleIds);
        final List<MenuRoleRelationship> roleMenus = baseRoleMenus
                .stream()
                .map(bean -> {
                    MenuRoleRelationship roleMenu = new MenuRoleRelationship();
                    roleMenu.setId(UUID.fastUUID().toString());
                    roleMenu.setMenuId(bean.getMenuId());
                    roleMenu.setRoleId(currentTimeMillis + bean.getRoleId().substring(13));
                    return roleMenu;
                })
                .collect(Collectors.toList());
        menuRoleRepository.saveAll(roleMenus);
    }

    @Override
//    @Cacheable
    public List<String> getGrantMenusIdsByUserId(String userId) {
        Set<Role> roles = roleService.getRoleByUserId(userId);
        return menuRoleRepository.findListByRoleIdIn(
                roles.stream()
                        .map(Role::getId)
                        .collect(Collectors.toSet()))
                .stream()
                .map(MenuRoleRelationship::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getGrantMenusIdsByRoleId(String roleId) {
        return menuRoleRepository.findListByRoleId(roleId)
                .stream()
                .map(MenuRoleRelationship::getMenuId)
                .collect(Collectors.toList());
    }
}
