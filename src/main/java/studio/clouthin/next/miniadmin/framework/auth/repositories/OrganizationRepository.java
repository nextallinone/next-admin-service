package studio.clouthin.next.miniadmin.framework.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import studio.clouthin.next.miniadmin.framework.auth.models.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, String>, JpaSpecificationExecutor<Organization> {


}
