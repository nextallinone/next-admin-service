package studio.clouthin.next.miniadmin.business.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studio.clouthin.next.miniadmin.business.dtos.request.MergeRegistryAppRequest;
import studio.clouthin.next.miniadmin.business.dtos.request.RegistryAppCriteria;
import studio.clouthin.next.miniadmin.business.models.RegistryApp;

public interface RegistryAppService {

    RegistryApp create(MergeRegistryAppRequest mergeRegistryAppRequest);

    RegistryApp update(String id, MergeRegistryAppRequest mergeRegistryAppRequest, int version);

    Page<RegistryApp> queryAll(RegistryAppCriteria criteria, Pageable pageable);

    void delete(String id);
}
