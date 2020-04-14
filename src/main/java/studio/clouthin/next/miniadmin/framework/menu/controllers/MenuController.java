package studio.clouthin.next.miniadmin.framework.menu.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.clouthin.next.miniadmin.framework.menu.dto.response.MenuSummary;
import studio.clouthin.next.miniadmin.framework.menu.supports.MenuRestSupport;
import studio.clouthin.next.shared.utils.SecurityUtils;

import java.util.List;

@Api("菜单管理")
@RestController
@RequestMapping(value = "/menus")
public class MenuController {

    @Autowired
    private MenuRestSupport menuRestSupport;

    @ApiOperation("用户菜单列表")
    @GetMapping(value = "/mine")
    public List<MenuSummary> getGrantedMenus() {
        return menuRestSupport.getUserGrantedMenus(SecurityUtils.getUserDetails().getUsername());
    }

    @ApiOperation("所有菜单列表")
    @GetMapping(value = "/all")
    public List<MenuSummary> allMenus() {
        return menuRestSupport.getAllMenus();
    }


}
