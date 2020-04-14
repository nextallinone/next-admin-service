package studio.clouthin.next.miniadmin.framework.dict.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictItemQueryParameter;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictItermMergeRequest;
import studio.clouthin.next.miniadmin.framework.dict.models.DictItem;

public interface DictItemService {

    Page<DictItem> page(DictItemQueryParameter parameter, Pageable pageable);

    DictItem create(DictItermMergeRequest dictItermMergeRequest);

    DictItem update(String id, DictItermMergeRequest dictItermMergeRequest);

    DictItem deleteById(String id);

}
