package studio.clouthin.next.miniadmin.framework.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import studio.clouthin.next.miniadmin.framework.auth.models.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {


    User findByUsername(String username);


}
