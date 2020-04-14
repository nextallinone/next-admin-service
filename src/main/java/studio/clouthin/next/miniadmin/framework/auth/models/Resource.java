package studio.clouthin.next.miniadmin.framework.auth.models;

import lombok.Getter;
import lombok.Setter;
import studio.clouthin.next.shared.models.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限资源
 */
@Entity
@Table(name = "security_resource")
@Getter
@Setter
public class Resource extends AbstractModel {


    /**
     * 资源名称.
     */
    @Column(length = 128, nullable = false)
    private String name;

    /**
     * 资源链接.
     */
    @Column(length = 128, nullable = false)
    private String url;


}
