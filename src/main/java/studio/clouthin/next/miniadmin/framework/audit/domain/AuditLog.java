package studio.clouthin.next.miniadmin.framework.audit.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import studio.clouthin.next.shared.models.StringIdentifier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Data
@Table(name = "audit_log")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AuditLog extends StringIdentifier {


    /**
     * 操作用户
     */
    private String username;

    /**
     * 描述
     */
    private String description;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数
     */
    @Column(columnDefinition = "text")
    private String params;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 异常详细
     */
    @Column(name = "exception_detail", columnDefinition = "text")
    private byte[] exceptionDetail;

    /**
     * 创建日期
     */
    @CreatedDate
    private Date createdAt;

    public AuditLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
