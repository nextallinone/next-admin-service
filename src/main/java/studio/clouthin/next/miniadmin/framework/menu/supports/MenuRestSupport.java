package studio.clouthin.next.miniadmin.framework.menu.supports;

import studio.clouthin.next.miniadmin.framework.menu.dto.request.BindingMenuRequest;
import studio.clouthin.next.miniadmin.framework.menu.dto.response.MenuSummary;

import java.util.List;

public interface MenuRestSupport {

    /**
     * 获取用户菜单
     * @param userId
     * @return
     */
    List<MenuSummary> getUserGrantedMenus(String userId);

    /**
     * 获取所有菜单
     * @return
     */
    List<MenuSummary> getAllMenus();

    /**
     * 获取角色菜单
     * @param roleId
     * @return
     */
    List<MenuSummary> getByRole(String roleId);


    /**
     * 绑定菜单
     * @param roleId
     * @param request
     */
    void bindByRole(String roleId, BindingMenuRequest request);

}
