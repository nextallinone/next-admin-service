package studio.clouthin.next.miniadmin.framework.dict.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictMergeRequest;
import studio.clouthin.next.miniadmin.framework.dict.dto.request.DictQueryParameter;
import studio.clouthin.next.miniadmin.framework.dict.models.Dict;

public interface DictService {

    Page<Dict> page(DictQueryParameter parameter, Pageable pageable);

    Dict create(DictMergeRequest dictMergeRequest);

    Dict update(String id, DictMergeRequest dictMergeRequest);

    Dict deleteById(String id);

}
