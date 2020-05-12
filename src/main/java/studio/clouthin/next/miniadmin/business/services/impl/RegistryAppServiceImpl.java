package studio.clouthin.next.miniadmin.business.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.clouthin.next.miniadmin.business.dtos.request.MergeRegistryAppRequest;
import studio.clouthin.next.miniadmin.business.dtos.request.RegistryAppCriteria;
import studio.clouthin.next.miniadmin.business.models.RegistryApp;
import studio.clouthin.next.miniadmin.business.repositories.RegistryAppRepository;
import studio.clouthin.next.miniadmin.business.services.RegistryAppService;
import studio.clouthin.next.shared.utils.JPAQueryHelper;

@Service
@Transactional
public class RegistryAppServiceImpl implements RegistryAppService {

    @Autowired
    private RegistryAppRepository registryAppRepository;

    @Override
    public RegistryApp create(MergeRegistryAppRequest mergeRegistryAppRequest) {
        RegistryApp registryApp = new RegistryApp();
        registryApp.setName(mergeRegistryAppRequest.getName());
        registryApp.setRemark(mergeRegistryAppRequest.getRemark());
        registryApp.setAuthUrl(mergeRegistryAppRequest.getAuthUrl());
        registryApp.setRedirectUrl(mergeRegistryAppRequest.getRedirectUrl());
        return registryAppRepository.saveAndFlush(registryApp);
    }

    @Override
    public RegistryApp update(String id, MergeRegistryAppRequest mergeRegistryAppRequest, int version) {
        RegistryApp registryApp = registryAppRepository.findById(id).get();
        registryApp.setName(mergeRegistryAppRequest.getName());
        registryApp.setRemark(mergeRegistryAppRequest.getRemark());
        registryApp.setAuthUrl(mergeRegistryAppRequest.getAuthUrl());
        registryApp.setRedirectUrl(mergeRegistryAppRequest.getRedirectUrl());
        return registryAppRepository.saveAndFlush(registryApp);
    }

    @Override
    public Page<RegistryApp> queryAll(RegistryAppCriteria criteria, Pageable pageable) {
        return registryAppRepository
                .findAll((
                        (root, criteriaQuery, cb) ->
                                JPAQueryHelper.getPredicate(root, criteria, cb)), pageable);
    }

    @Override
    public void delete(String id) {
        registryAppRepository.deleteById(id);
    }
}
