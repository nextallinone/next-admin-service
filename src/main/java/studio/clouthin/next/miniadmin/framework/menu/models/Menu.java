package studio.clouthin.next.miniadmin.framework.menu.models;

import lombok.Data;

@Data
public class Menu {

    private String id;

    private String mpid;

    private String name;

    private String icon;

    private String route;

    private Boolean group = Boolean.FALSE;

    private Boolean open = Boolean.FALSE;
}
