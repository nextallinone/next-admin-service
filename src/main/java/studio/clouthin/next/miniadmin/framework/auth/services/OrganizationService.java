package studio.clouthin.next.miniadmin.framework.auth.services;

import studio.clouthin.next.miniadmin.framework.auth.models.Organization;

import java.util.List;
import java.util.Set;

public interface OrganizationService {

    List<Organization> findAll();

    Organization findById(String id);

    Organization save(Organization organization);

    List<Organization> findByIds(Set<String> ids);

    List<Organization> saveAll(Iterable<Organization> organizations);
}
