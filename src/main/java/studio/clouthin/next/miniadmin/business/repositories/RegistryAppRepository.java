package studio.clouthin.next.miniadmin.business.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import studio.clouthin.next.miniadmin.business.models.RegistryApp;

@Repository
public interface RegistryAppRepository extends JpaRepository<RegistryApp, String>, JpaSpecificationExecutor<RegistryApp> {
}
