package studio.clouthin.next.miniadmin.framework.auth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.clouthin.next.miniadmin.framework.auth.models.Organization;
import studio.clouthin.next.miniadmin.framework.auth.repositories.OrganizationRepository;
import studio.clouthin.next.miniadmin.framework.auth.services.OrganizationService;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Override
    public Organization findById(String id) {
        return organizationRepository.findById(id).orElse(null);
    }

    @Override
    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public List<Organization> findByIds(Set<String> ids) {
        return organizationRepository.findAllById(ids);
    }

    @Override
    public List<Organization> saveAll(Iterable<Organization> organizations) {
        return organizationRepository.saveAll(organizations);
    }
}
