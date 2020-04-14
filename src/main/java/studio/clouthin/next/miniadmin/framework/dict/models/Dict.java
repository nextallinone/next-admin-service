package studio.clouthin.next.miniadmin.framework.dict.models;

import lombok.Data;
import studio.clouthin.next.shared.models.AbstractModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 字典表
 */
@Entity
@Data
@Table(name = "dict")
public class Dict extends AbstractModel {

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    private String name;

    private String remark;

    @OneToMany(mappedBy = "dict", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<DictItem> dictItems;

}
