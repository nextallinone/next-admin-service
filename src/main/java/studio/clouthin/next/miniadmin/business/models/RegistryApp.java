package studio.clouthin.next.miniadmin.business.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import studio.clouthin.next.shared.models.AbstractModel;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * 注册应用
 */
@Entity
@Data
@Table(name = "t_registry_app")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RegistryApp extends AbstractModel {

    private String name;

    private String remark;

    private String redirectUrl;

    private String authUrl;
}
