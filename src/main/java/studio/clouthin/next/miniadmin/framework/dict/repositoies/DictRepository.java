package studio.clouthin.next.miniadmin.framework.dict.repositoies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import studio.clouthin.next.miniadmin.framework.dict.models.Dict;


@Repository
public interface DictRepository extends JpaRepository<Dict, String>, JpaSpecificationExecutor<Dict> {


}
