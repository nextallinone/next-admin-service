package studio.clouthin.next.miniadmin.framework.dict.models;

import lombok.Data;
import studio.clouthin.next.shared.models.AbstractModel;

import javax.persistence.*;

/**
 * 字典条目
 */
@Entity
@Data
@Table(name = "dict_item")
public class DictItem extends AbstractModel {
    /**
     * 字典标签
     */
    @Column(name = "label", nullable = false)
    private String label;

    /**
     * 字典值
     */
    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dict_id")
    private Dict dict;

    @Column(name = "sort")
    private String sort = "999";
}
