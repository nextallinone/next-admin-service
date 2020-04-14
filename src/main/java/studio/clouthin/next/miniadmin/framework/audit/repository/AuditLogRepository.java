package studio.clouthin.next.miniadmin.framework.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import studio.clouthin.next.miniadmin.framework.audit.domain.AuditLog;


@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, String>, JpaSpecificationExecutor<AuditLog> {

    /**
     * 获取一个时间段的IP记录
     *
     * @param date1 startTime
     * @param date2 entTime
     * @return IP数目
     */
    @Query(value = "select count(*) FROM (select request_ip FROM audit_log where created_at between ?1 and ?2 GROUP BY request_ip) as s", nativeQuery = true)
    Long findIp(String date1, String date2);

    /**
     * 根据日志类型删除信息
     *
     * @param logType 日志类型
     */
    @Query(nativeQuery = true, value = "delete from audit_log where log_type = ?1")
    @Modifying
    void deleteByLogType(String logType);
}
