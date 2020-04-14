package studio.clouthin.next.miniadmin.framework.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import studio.clouthin.next.miniadmin.framework.auth.models.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

    Set<Role> findByUsers_Id(String id);

    Role findByName(String name);
}
